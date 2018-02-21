package com.incture.pmc.workbox.services;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;

import com.incture.pmc.util.ServicesUtil;
import com.incture.pmc.workbox.dao.DeviceManagementDao;
import com.incture.pmc.workbox.dao.TaskOwnersDao;
import com.incture.pmc.workbox.dto.DescriptionDto;
import com.incture.pmc.workbox.dto.DescriptionResponseDto;
import com.incture.pmc.workbox.dto.ExistingDataDto;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

//import com.sap.mw.*;

import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;

/**
 * Session Bean implementation class NotificationFacade
 */
@Stateless
public class NotificationFacade implements NotificationFacadeLocal {

	@EJB
	EntityManagerProviderLocal em;

	@Resource
	EJBContext context;

	public NotificationFacade() {
	}

	/*@Override
	public void sendNotification() {

		DeviceManagementDao dao = new DeviceManagementDao(em.getEntityManager());
		DbDeviceListResponseDto dbData = dao.getAllDeviceData();
		if(!ServicesUtil.isEmpty(dbData) && dbData.getDeviceListDtos().size()>0){
			List<DeviceListDto> dataDtoList = getDataFromRFC(dbData.getDeviceListDtos());
			int index =0;
			DeviceListDto deviceDto = null;
			long nano = System.currentTimeMillis();
			long mainNano = System.currentTimeMillis();
			System.err.println("start for loop "+nano);
			if(!ServicesUtil.isEmpty(dataDtoList)){
				for(DeviceListDto dto : dataDtoList){
					index = dbData.getLocationMap().get(dto.getUser());
					deviceDto =  dbData.getDeviceListDtos().get(index);
					int currentCount = deviceDto.getCount() + dto.getCount() - new TaskOwnersDao(em.getEntityManager()).getExistingInstanceCount(ServicesUtil.getStringFromList(dto.getStringList()),dto.getUser());
					dao.updateCountOfUser(dto.getUser(), currentCount);
					if(currentCount>0){
						nano =  System.currentTimeMillis();
						pushNotificationToIOS(currentCount , "You have "+currentCount+" New Task(s)" ,deviceDto.getStringList());
						nano =  System.currentTimeMillis() - nano ;
						System.err.println("[pushNotificationToIOS] end in "+ nano);
					}
				}
				mainNano =  System.currentTimeMillis() - mainNano ;
				System.err.println("end for loop "+mainNano);
			}
		}
	}*/



	@Override
	public void sendNotification() {

		DeviceManagementDao dao = new DeviceManagementDao(em.getEntityManager());
		Map<String,ExistingDataDto> deviceData = dao.getAllDeviceData();
		ExistingDataDto deviceDataOfUser = null;
		if(!ServicesUtil.isEmpty(deviceData)){
			DescriptionResponseDto rfcData = getDataFromRFC(deviceData.keySet().toArray());
			List<String> usersList = new ArrayList<String>();
			if(!ServicesUtil.isEmpty(rfcData.getInstanceList())){
				Map<String,ExistingDataDto> existingDataDto = new TaskOwnersDao(em.getEntityManager()).getExistingData(rfcData.getUserCountMap().keySet().toArray()); 
				for(DescriptionDto descDto : rfcData.getInstanceList()){
					String user = descDto.getUserId().toUpperCase();
					List<String> instanceList =  existingDataDto.get(user).getStringList();
					if( !ServicesUtil.isEmpty(instanceList) ){
						deviceDataOfUser = deviceData.get(user);
						if( !instanceList.contains(descDto.getInstanceId()))
						{
							deviceData.get(user).setCount(deviceDataOfUser.getCount()+1);
							//	existingDataDto.getTotalCountMap().replace(user, existingDataDto.getTotalCountMap().get(user)+1);
						}
						pushNotificationToIOS(existingDataDto.get(user).getCount() + deviceDataOfUser.getCount() , descDto.getDescription() ,deviceDataOfUser.getStringList());
						
					}
					else{
						deviceDataOfUser = new ExistingDataDto();
						List<String> stList = new ArrayList<String>();
						stList.add(descDto.getInstanceId());
						deviceDataOfUser.setCount(1);
						deviceDataOfUser.setStringList(stList);
						existingDataDto.put(user, deviceDataOfUser);
						pushNotificationToIOS(1 , descDto.getDescription() ,deviceDataOfUser.getStringList());

					}
					if(!usersList.contains(user))
						usersList.add(user);
				}
				
				for(String user :usersList){
					if(deviceData.get(user).getCount()>0)
					dao.updateCountOfUser(user, deviceData.get(user).getCount());
				}
			}

		}
	}




	public String pushNotificationToIOS(int count,String message, List<String> deviceList){

		try {
			PushNotificationPayload payload = PushNotificationPayload.complex();
			payload.addAlert(message);
			payload.addBadge(count);
			payload.addSound("default");
			//String[] mydeviceList = (String[]) deviceList.toArray();

			String path = "/usr/sap/POD/J05/j2ee/cert/kaustCert1.p12";
			List < PushedNotification > NOTIFICATIONS = Push.payload(payload, path, "incture", false, deviceList);
			for (PushedNotification NOTIFICATION: NOTIFICATIONS) {
				if (NOTIFICATION.isSuccessful()) {
					System.err.println("[PMC][NotificationFacade][pushNotificationToIOS] sent to "+NOTIFICATION.getDevice().getToken()+"[with payload]"+payload.toString());
					return "SUCCESS";
				} else {
					// ADD CODE HERE TO REMOVE INVALIDTOKEN FROM YOUR DATABASE 
					// dao.getAndDeleteInstance(deviceDto)
					System.err.println("[PMC][NotificationFacade][pushNotificationToIOS][error]"+NOTIFICATION.getException().getMessage());
					ResponsePacket THEERRORRESPONSE = NOTIFICATION.getResponse();
					if (THEERRORRESPONSE != null) {
						System.err.println("[PMC][NotificationFacade][pushNotificationToIOS][error]"+THEERRORRESPONSE.getMessage());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("incture");
			System.err.println("[PMC][NotificationFacade][pushNotificationToIOS][error]" + e.getMessage());
		}
		return "FAILURE";

	}

	/*Map<String,List<String>>
	public List<DeviceListDto> getDataFromRFC(List<DeviceListDto>  userList){

		try{
			List<DeviceListDto> deviceListDtos = new ArrayList<DeviceListDto>();
			JCoDestination destination = getDestination("sthcigwd1.kaust.edu.sa","05","260","PORFCGWUSR","kaust123","GW_RFCDEST");
			if(!ServicesUtil.isEmpty(destination)){
				JCoFunction function = getFunction(destination, "ZCUUTLI0001_WF_DELTA_DETAILS");
				if(!ServicesUtil.isEmpty(function)){
					long earlierDateInMS = ServicesUtil.getEarlierDateInMillis(45 , 0 , 15 , 0 ,0);
					function.getImportParameterList().setValue("IV_DATE",new Date(earlierDateInMS));
					function.getImportParameterList().setValue("IV_TIME",  new Time(earlierDateInMS));
					JCoTable table = function.getTableParameterList().getTable("USERS");
					table.appendRows(userList.size());

					for( DeviceListDto user : userList){
						table.setValue(0, user.getUser().toUpperCase());	
						table.nextRow();
					}
					function.execute(destination);
					deviceListDtos  = getConvertedData(function);
				}
			}
			return deviceListDtos;
		}
		catch (Exception e)
		{
			System.err.println("[PMC][NotificationFacade][getDataFromRFC][error] "+e.getMessage());
		}
		return null;
	}*/


	public DescriptionResponseDto getDataFromRFC(Object[] userList){

		try{
			JCoDestination destination = getDestination("sthcigwd1.kaust.edu.sa","05","260","PORFCGWUSR","kaust123","GW_RFCDEST");
			if(!ServicesUtil.isEmpty(destination)){
				JCoFunction function = getFunction(destination, "ZCUUTLI0001_WF_DELTA_DETAILS");
				if(!ServicesUtil.isEmpty(function)){
					long earlierDateInMS = ServicesUtil.getEarlierDateInMillis(0 , 0 , 15 , 0 ,0);
					function.getImportParameterList().setValue("IV_DATE",new Date(earlierDateInMS));
					function.getImportParameterList().setValue("IV_TIME",  new Time(earlierDateInMS));
					JCoTable table = function.getTableParameterList().getTable("USERS");
					table.appendRows(userList.length);
					for( Object user : userList){
						table.setValue(0, user.toString().toUpperCase());	
						table.nextRow();
					}
					function.execute(destination);
					return getConvertedData(function);
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("[PMC][NotificationFacade][getDataFromRFC][error] "+e.getMessage());
		}
		return null;
	}


	public JCoFunction getFunction(JCoDestination destination ,String functionName){

		JCoFunction function = null;
		try{
			function = destination.getRepository().getFunction(functionName);
		}
		catch(Exception e){
			System.err.println("[PMC][NotificationFacade][getFunction][error]"+e.getMessage());
		}
		return function;
	}


	public JCoDestination getDestination(String host,  String systemNumber , String client ,String userId , String password, String destinationName){

		JCoDestination destination  = null;
		try {
			destination = JCoDestinationManager.getDestination(destinationName);
		} catch (Exception e) {
			System.err.println("[PMC][NotificationFacade][getDestination][error]"+e.getMessage());
		}
		return destination;
	}    

	public DescriptionResponseDto getConvertedData(JCoFunction function){
		List<DescriptionDto> instanceList = new ArrayList<DescriptionDto>();
		Map<String,Integer> countMap = new HashMap<String, Integer>();
		DescriptionResponseDto responseDto = new DescriptionResponseDto();		
		if( function.getExportParameterList().getTable("WORKLIST").getNumRows()>0){
			JCoTable myTable = function.getExportParameterList().getTable("WORKLIST");
			System.err.println("[PMC][WorkList]"+function.getExportParameterList().getTable("WORKLIST"));
			DescriptionDto descDto = null;
			for(int i=0;i<myTable.getNumRows();i++){
				descDto = new DescriptionDto();
				String user = (String) myTable.getValue(0);
				descDto.setDescription((String) myTable.getValue(3));
				descDto.setInstanceId((String) myTable.getValue(1));
				descDto.setUserId(user);
				if(countMap.containsKey(user)){
					countMap.replace(user, countMap.get(user)+1);
				}
				else{
					countMap.put(user, 1);
				}
				instanceList.add(descDto);
				myTable.nextRow();
			}
		}
		responseDto.setInstanceList(instanceList);
		responseDto.setUserCountMap(countMap);
		System.err.println("[PMC][NotificationFacade][getConvertedData][end] with " +responseDto);
		return responseDto;
	}

	@Override
	public String sendPushNotification(String message) {

		DeviceManagementDao dao = new DeviceManagementDao(em.getEntityManager());
		List<String> deviceIdList = dao.getAllDeviceIds();
		if(!ServicesUtil.isEmpty(deviceIdList)){
			pushNotificationToIOS(0 , message ,deviceIdList);
			return "SUCCESS";
		}		
		else {
			return "DEVICE LIST EMPTY";
		}

	}



}
