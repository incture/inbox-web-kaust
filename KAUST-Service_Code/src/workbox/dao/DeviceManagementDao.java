package com.incture.pmc.workbox.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.incture.pmc.util.ExecutionFault;
import com.incture.pmc.util.InvalidInputFault;
import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.ServicesUtil;
import com.incture.pmc.workbox.dto.DeviceManagementDto;
import com.incture.pmc.workbox.entity.DeviceManagementDo;

/**
 * The <code>DeviceManagementDao</code> converts Do to Dto and vice-versa
 * <code>Data Access Objects <code>
 * 
 * @author INC00609
 * @version 1.0
 * @since 2018-02-01
 */
public class DeviceManagementDao extends BaseDao<DeviceManagementDo, DeviceManagementDto> {
	public DeviceManagementDao(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected DeviceManagementDo importDto(DeviceManagementDto fromDto)
			throws InvalidInputFault, ExecutionFault, NoResultFault {
		DeviceManagementDo outputDo = new DeviceManagementDo();
		if (!ServicesUtil.isEmpty(fromDto.getUniqueId())) {
			outputDo.setUniqueId(fromDto.getUniqueId().trim());
		}
		if (!ServicesUtil.isEmpty(fromDto.getDeviceId())) {
			outputDo.setDeviceId(fromDto.getDeviceId().trim());
		}
		if (!ServicesUtil.isEmpty(fromDto.getUserId())) {
			outputDo.setUserId(fromDto.getUserId().trim());
		}
		return outputDo;
	}

	@Override
	public DeviceManagementDto exportDto(DeviceManagementDo fromDo) {
		DeviceManagementDto outputDto = new DeviceManagementDto();
		if (!ServicesUtil.isEmpty(fromDo.getUniqueId())) {
			outputDto.setUniqueId(fromDo.getUniqueId().trim());
		}
		if (!ServicesUtil.isEmpty(fromDo.getDeviceId())) {
			outputDto.setDeviceId(fromDo.getDeviceId().trim());
		}
		if (!ServicesUtil.isEmpty(fromDo.getUserId())) {
			outputDto.setUserId(fromDo.getUserId().trim());
		}

		return outputDto;
	}


	@SuppressWarnings("unchecked")
	public String allDeviceInstance() {
		//	System.err.println("[PMC][DeviceManagementDao][allDeviceInstance]initiated with");
		Query query = this.getEntityManager().createQuery("select te from DeviceManagementDo te");
		List<DeviceManagementDo> deviceDos = (List<DeviceManagementDo>) query.getResultList();
		int i = 0;
		try {
			for (DeviceManagementDo entity : deviceDos) {
				System.err.println("[PMC][DeviceManagementDao][allDeviceInstance][i]"+i+"[entity]" +entity);
				//	delete(exportDto(entity));
				i++;
			}
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][DeviceManagementDao][allDeviceInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	public String createDeviceInstance(DeviceManagementDto dto) {
		//	System.err.println("[PMC][DeviceManagementDao][createDeviceInstance]initiated with " + dto);
		try {
			create(dto);
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][DeviceManagementDao][createDeviceInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	public String deleteDeviceInstance(DeviceManagementDto dto) {
		//	System.err.println("[PMC][DeviceManagementDao][deleteDeviceInstance]initiated with " + dto);
		try {
			delete(dto);
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][DeviceManagementDao][deleteDeviceInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	public String getAndDeleteInstance(DeviceManagementDto dto) {
		//	System.err.println("[PMC][DeviceManagementDao][getAndDeleteInstance]initiated with " + dto);
		try {
			dto = getInstanceByUserAndId(dto);
			if(!ServicesUtil.isEmpty(dto)){
				return deleteDeviceInstance(dto);
			}
		} catch (Exception e) {
			System.err.println("[PMC][DeviceManagementDao][getAndDeleteInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}


	@SuppressWarnings("unchecked")
	public DeviceManagementDto getInstanceByUserAndId(DeviceManagementDto dto) {
		//	System.err.println("[PMC][DeviceManagementDao][getInstanceByUserAndId]initiated with " + dto);
		try {
			String queryString = "select de from DeviceManagementDo de where de.userId = '"+dto.getUserId()+"' and de.deviceId = '"+dto.getDeviceId()+"'";
			Query query = this.getEntityManager().createQuery(queryString);
			List<DeviceManagementDo> deviceDos = (List<DeviceManagementDo>) query.getResultList();
			for(DeviceManagementDo device : deviceDos){
				return 	exportDto(device);
			}
		} catch (Exception e) {
			System.err.println("[PMC][DeviceManagementDao][getInstanceByUserAndId][error] " + e.getMessage());
		}
		return null;
	}

	public String updateInstance(DeviceManagementDto dto) {
		//	System.err.println("[PMC][DeviceManagementDao][updateInstance]initiated with " + dto);
		try {
			String queryString = "update DeviceManagementDo de set de.deviceId = '"+dto.getDeviceId()+"' where de.userId = '"+dto.getUserId()+"' and de.deviceId = '"+dto.getDeviceIdOld()+"'";
			Query query = this.getEntityManager().createQuery(queryString);
			if(query.executeUpdate() == 0){
				return "FAILURE";
			}
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][DeviceManagementDao][updateInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	public String updateCountOfUser(String user,int count) {
		//	System.err.println("[PMC][DeviceManagementDao][updateCountOfUser]initiated with[user] " + user +"[count]"+count);
		try {
			String queryString = "update DeviceManagementDo de set de.taskCount = "+ count +" where de.userId = '"+user+"'";
			Query query = this.getEntityManager().createQuery(queryString);
			query.executeUpdate();
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][DeviceManagementDao][updateCountOfUser][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	@SuppressWarnings("unchecked")
	/*public DbDeviceListResponseDto getAllDeviceData() {
	//	System.err.println("[PMC][DeviceManagementDao][getAllDeviceData]initiated ");
		try {
			String queryString = "select de from DeviceManagementDo de ";
			Query query = this.getEntityManager().createQuery(queryString);
			return getConvertedData(query.getResultList());
		} catch (Exception e) {
			System.err.println("[PMC][DeviceManagementDao][getAllDeviceData][error] " + e.getMessage());
		}
		return null;
	}
	 */

	public Map<String,List<String>> getAllDeviceData() {
		//	System.err.println("[PMC][DeviceManagementDao][getAllDeviceData]initiated ");
		try {
			String queryString = "select de from DeviceManagementDo de ";
			Query query = this.getEntityManager().createQuery(queryString);
			return getConvertedData(query.getResultList());
		} catch (Exception e) {
			System.err.println("[PMC][DeviceManagementDao][getAllDeviceData][error] " + e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<String> getAllDeviceIds(){
		//	System.err.println("[PMC][DeviceManagementDao][getAllDeviceIds]initiated ");
		try {
			String queryString = "select DISTINCT de.deviceId from DeviceManagementDo de ";
			Query query = this.getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (Exception e) {
			System.err.println("[PMC][DeviceManagementDao][getAllDeviceIds][error] " + e.getMessage());
		}
		return null;
	}

	public Map<String,List<String>> getConvertedData(List<DeviceManagementDo> doList){

		Map<String,List<String>> responseMap = new HashMap<String,List<String>>();
		if(!ServicesUtil.isEmpty(doList)){
			List<String> deviceList = null;
			for(DeviceManagementDo entity : doList)
			{
				String user = entity.getUserId();
				if (responseMap.containsKey(user)) {
					responseMap.get(user).add(entity.getDeviceId());
				} else {
					deviceList = new ArrayList<String>();
					deviceList.add(entity.getDeviceId());
					responseMap.put(user, deviceList);
				}
			}
			System.err.println("[PMC][DeviceManagementDao][getConvertedData][end] with " + responseMap);
			return responseMap;
		}
		return null;
	}

	/*public DbDeviceListResponseDto getConvertedData(List<DeviceManagementDo> doList){

		DbDeviceListResponseDto responseDto = new DbDeviceListResponseDto();
		if(!ServicesUtil.isEmpty(doList)){
			List<DeviceListDto> deviceListDtos = new ArrayList<DeviceListDto>();
			Map<String, Integer> locationMap = new HashMap<String, Integer>();

			DeviceListDto deviceListDto = null;
			for(DeviceManagementDo entity : doList)
			{
				String user = entity.getUserId();
				if (locationMap.containsKey(user)) {
					deviceListDto = deviceListDtos.get(locationMap.get(user));
					deviceListDto.getStringList().add(entity.getDeviceId());
				} else {
					List<String> instanceDtos = new ArrayList<String>();
					instanceDtos.add(entity.getDeviceId());
					deviceListDto = new DeviceListDto();
					deviceListDto.setStringList(instanceDtos);
					deviceListDto.setUser(user);
					deviceListDto.setCount(entity.getTaskCount());
					deviceListDtos.add(deviceListDto);
					locationMap.put(user, deviceListDtos.size() - 1);
				}
			}
			responseDto.setDeviceListDtos(deviceListDtos);
			responseDto.setLocationMap(locationMap);
		//	System.err.println("[PMC][DeviceManagementDao][getConvertedData][end] with " + responseDto);
			return responseDto;
		}
		return null;
	}*/

	/*@SuppressWarnings("unchecked")
	public List<String> getDeviceInstancesByUser(String user) {
		System.err.println("[PMC][DeviceManagementDao][getInstancesByUser]initiated with " + user);
		try {
			String queryString = "select de.deviceId from DeviceManagementDo de where de.userId = '"+user+"'";
			Query query = this.getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (Exception e) {
			System.err.println("[PMC][DeviceManagementDao][getInstancesByUser][error] " + e.getMessage());
		}
		return null;
	}

	 @SuppressWarnings("unchecked")
	public int getCountOfUser(String user) {
		System.err.println("[PMC][DeviceManagementDao][updateCountOfUser]initiated with " + user);
		try {
			String queryString = "select DISTINCT(de.taskCount) from DeviceManagementDo de where de.userId = '"+user+"'";
			Query query = this.getEntityManager().createQuery(queryString);
			List<Integer> response = (List<Integer>) query.getResultList();
			for (Integer res: response){
				return res;
			}
		} catch (Exception e) {
			System.err.println("[PMC][DeviceManagementDao][updateCountOfUser][error] " + e.getMessage());
		}
		return 0;
	}*/

}