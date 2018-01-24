package com.incture.pmc.workbox.services;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.ODataServicesUtil;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;
import com.incture.pmc.workbox.dao.DeviceManagementDao;
import com.incture.pmc.workbox.dto.DeviceManagementDto;
import com.incture.pmc.workbox.dto.ResponseMessage;
import com.incture.pmc.workbox.dto.TaskCustomAttributeDto;
import com.incture.pmc.workbox.dto.UserDetailDto;
import com.incture.pmc.workbox.dto.WorKBoxDetailDto;
import com.incture.pmc.workbox.dto.WorkBoxAppDataDto;
import com.incture.pmc.workbox.dto.WorkBoxDto;
import com.incture.pmc.workbox.dto.WorkboxRequestDto;
import com.incture.pmc.workbox.dto.WorkboxResponseDto;




/**
 * Session Bean implementation class WorkBoxBean
 */

@Stateless
public class WorkBoxBean implements WorkBoxBeanLocal {


	@EJB
	EntityManagerProviderLocal em;

	@EJB
	ConsumeODataFacadeLocal oDataLocal;

	@EJB
	ConsumeODataXpathFacadeLocal oDataXpathLocal;

	public WorkBoxBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public WorkboxResponseDto getWorkBoxDetailByUser(WorkboxRequestDto requestDto) {

	//	System.err.println("[PMC][WorkBoxBean][getWorkBoxDetailByUser] initiated with" + requestDto.getUserId());
		WorkboxResponseDto workboxResponseDto = new WorkboxResponseDto();
		ResponseMessage message = new ResponseMessage();
		if (!ServicesUtil.isEmpty(requestDto.getUserId())) {
			ResponseMessage returnMessage  = oDataXpathLocal.getDataFromECC(requestDto.getUserId().toUpperCase(),requestDto.getScode());
		//	ResponseMessage returnMessage  = oDataLocal.getDataFromECC(requestDto.getUserId().toUpperCase(),requestDto.getScode());

			if(!returnMessage.getStatus().equals("SUCCESS")){
				message.setStatus("FAILURE");
				message.setStatusCode("1");
				message.setMessage("ERROR WHILE FETCHING THE DATA");
				workboxResponseDto.setResponseMessage(message);
				return workboxResponseDto;
			}

			String query = "SELECT pe.NAME AS PROCESS_NAME,pe.PROCESS_ID AS PROCESS_ID,te.EVENT_ID AS TASK_ID,te.STATUS AS TASK_STATUS, te.DESCRIPTION AS DESCRIPTION, te.PRIORITY AS TASK_PRIORITY,pe.STARTED_AT AS CREATED_AT,pe.STARTED_BY AS CREATED_BY,tw.TASK_OWNER AS POTENTIAL_OWNERS,te.FORWARDED_BY AS FORWARDING_USER,te.CUR_PROC AS FORWARDED_USER,te.COMP_DEADLINE AS COMPLETION_DEADLINE,ca.STARTDEADLINE AS START_DEADLINE,ca.EXPIRYDATE AS EXPIRYDATE,ca.ATTRIBUTE AS ATTRIBUTE,ca.COMMENTS AS COMMENTS,ca.ATTACHMENTS AS ATTACHMENTS,ca.ISESCALATED AS ISESCALATED,ca.SAP_ORIGIN AS SAP_ORIGIN,pe.PRC_INST_ID AS PRC_INST_ID FROM TASK_EVNT te LEFT JOIN TASK_CUST_ATTR_TBL ca ON ca.TASK_ID = te.EVENT_ID  LEFT JOIN PRC_EVENTS pe ON te.PROCESS_ID = pe.PROCESS_ID LEFT JOIN TASK_OWNERS tw ON te.EVENT_ID = tw.EVENT_ID WHERE ((te.STATUS  = 'RESERVED' AND te.CUR_PROC = '"
					+ requestDto.getUserId().toUpperCase() + "'  AND tw.TASK_OWNER = '" + requestDto.getUserId().toUpperCase() + "') OR ((te.STATUS = 'READY' OR te.STATUS = 'IN_PROGRESS') AND tw.TASK_OWNER = '" + requestDto.getUserId().toUpperCase() + "'))";

			if (!ServicesUtil.isEmpty(requestDto.getTaskType())) {
				query = query + " AND pe.PRC_INST_ID = '" + requestDto.getTaskType() + "'";
			}
			else{
				query = query + " AND pe.PRC_INST_ID IN ('TS76308026','TS14007970','TS00407862','TS91000610','TS91000879','TS91000728','TS10008126','TS12300097','TS91000199','TS91000695_WS91000198_0000000073','TS21000231','TS91000634','TS01200196','TS91000758','TS01200212','TS14008026','TS91000695','TS91000743')";
			//	query = query + " AND pe.PRC_INST_ID IN ('Access Request Approval ','Purchase Order','Release for Payment: Level 1','Travel Plan Request Approval','SLCM Course registration workflow','Phone Service Approval','Shopping Cart','Leave Request','Claims','VMS','Stipend Approval for GA','Travel Plan Request Approval - Student','Training Approval','New Claims')";
				
			}

			query = query + " ORDER BY CREATED_AT DESC"; 

		//	System.err.println("[PMC][WorkBoxBean][getWorkBoxDetailByUser]" + query);
			Query q = em.getEntityManager().createNativeQuery(query, "workBoxResults");

			if (!ServicesUtil.isEmpty(requestDto.getMaxCount()) && requestDto.getMaxCount() > 0 && !ServicesUtil.isEmpty(requestDto.getSkipCount())
					&& 	requestDto.getSkipCount() >= 0) {
				int first = requestDto.getSkipCount();
				int last = 	requestDto.getMaxCount();
				q.setFirstResult(first);
				q.setMaxResults(last);
			} 

			List<Object[]> resultList = q.getResultList();
			if (ServicesUtil.isEmpty(resultList)) {
				try {
					throw new NoResultFault("NO RECORD FOUND");
				} catch (NoResultFault e) {
					System.err.println("NO RESULT FOUND");
					message.setStatus("NO RESULT FOUND FOR THE USER");
					message.setStatusCode("1");
					workboxResponseDto.setResponseMessage(message);
					return workboxResponseDto;
				}
			} else {
				final DateFormat dateFormatter = new SimpleDateFormat(PMCConstant.PMC_DATE_FORMATE);
				List<WorkBoxDto> workBoxDtos = new ArrayList<WorkBoxDto>();
		//		System.err.println("[PMC][WorkBoxBean][getWorkBoxDetailByUser]" + resultList.size());
				for (Object[] obj : resultList) {
					WorkBoxDto workBoxDto = new WorkBoxDto();
					WorkBoxAppDataDto workBoxAppDataDto = new WorkBoxAppDataDto();
					TaskCustomAttributeDto taskCustomAttributeDto = new TaskCustomAttributeDto();

					workBoxAppDataDto.setTaskDefinitionName(obj[0] == null ? null : (String) obj[0]);
				//	workBoxAppDataDto.setTaskDefinitionId(obj[1] == null ? null : (String) obj[1]);
					workBoxAppDataDto.setInstanceId(obj[2] == null ? null : (String) obj[2]);
					workBoxAppDataDto.setTaskStatus(obj[3] == null ? null : (String) obj[3]);
					workBoxAppDataDto.setDescription(obj[4] == null ? null : (String) obj[4]);
					workBoxAppDataDto.setTaskPriority(obj[5] == null ? null : (String) obj[5]);
					workBoxAppDataDto.setCreatedOn(obj[6] == null ? null : (ServicesUtil.resultAsDate(obj[6])));
					workBoxAppDataDto.setCreatedOnString(obj[6] == null ? null : dateFormatter.format((ServicesUtil.resultAsDate(obj[6]))));
					workBoxAppDataDto.setCreatedBy(obj[7] == null ? null : (String) obj[7]);
					workBoxAppDataDto.setPotentialOwners(obj[8] == null ? null : (String) obj[8]);
					workBoxAppDataDto.setForwardingUser(obj[9] == null ? null : (String) obj[9]);
					workBoxAppDataDto.setForwardedUser(obj[10] == null ? null : (String) obj[10]);
					workBoxAppDataDto.setCompletionDeadLine(obj[11] == null ? null : (ServicesUtil.resultAsDate(obj[11])));
					workBoxAppDataDto.setTaskDefinitionId(obj[19] == null ? null : (String) obj[19]);
					

					taskCustomAttributeDto.setInstanceId(obj[2] == null ? null : (String) obj[2]);
					taskCustomAttributeDto.setStartDeadLine(obj[12] == null ? null : (ServicesUtil.resultAsDate(obj[12])));
					taskCustomAttributeDto.setExpiryDate(obj[13] == null ? null : (ServicesUtil.resultAsDate(obj[13])));
					taskCustomAttributeDto.setAttribute(obj[14] == null ? null : (String) obj[14]);
					taskCustomAttributeDto.setActionList(obj[15] == null ? null : (String) obj[15]);
					taskCustomAttributeDto.setActionList(obj[16] == null ? null :(String) obj[16]+","+ taskCustomAttributeDto.getActionList());
					taskCustomAttributeDto.setSapOrigin(obj[18] == null ? null : (String) obj[18]);
					if (obj[17] != null){
						taskCustomAttributeDto.setEscalated(obj[17].equals(1) ? true : false);
					}

					/*	Uncomment to  Get the details along with tasks
					if(!ServicesUtil.isEmpty(taskCustomAttributeDto.getSapOrigin())&& !ServicesUtil.isEmpty(workBoxAppDataDto.getInstanceId())){
						workBoxDto.setDetailDto(oDataLocal.getTaskDetails(workBoxAppDataDto.getInstanceId(),taskCustomAttributeDto.getSapOrigin()));
					}
					 */
					taskCustomAttributeDto.setActionURL("http://sthcigwdq1.kaust.edu.sa:8005/sap/opu/odata/IWPGW/TASKPROCESSING;mo;v=2");
					workBoxDto.setAppData(workBoxAppDataDto);
					workBoxDto.setTaskCustomAttribute(taskCustomAttributeDto);
					workBoxDtos.add(workBoxDto);
				}
				workboxResponseDto.setWorkBoxDtos(workBoxDtos);
				message.setStatus("Success");
				message.setStatusCode("0");
				message.setMessage("Process Details Fetched Successfully");
				workboxResponseDto.setResponseMessage(message);
				return workboxResponseDto;
			}

		}

		message.setStatus("FAILURE");
		message.setStatusCode("1");
		message.setMessage("NO USER PROVIDED");
		workboxResponseDto.setResponseMessage(message);
		return workboxResponseDto;
	}

	@Override
	public List<String> getProcessNames() {
		List<String> list = new ArrayList<String>(); 
		list.add("Access Request Approval dialog"); // TS76308026
		list.add("Purchase Order"); // TS14007970
		list.add("Release for Payment: Level 1"); // TS00407862
		list.add("Travel Plan Request Approval"); // TS91000610
	//	list.add("Process Request by Employee");
		list.add("SLCM Course registration workflow"); //TS91000879
		list.add("Phone Service Approval"); // TS91000728
		list.add("Shopping Cart"); // TS10008126
		list.add("Leave Request");// TS12300097
		list.add("Claims"); //TS91000199
		list.add("New Claims"); //TS91000758
		list.add("VMS"); // TS91000695_WS91000198_0000000073
		list.add("Stipend Approval for GA");//TS21000231
		list.add("Travel Plan Request Approval - Student");//TS91000634
		list.add("Training Approval"); // TS01200196
		
		return list;
	}



	@Override
	public List<String> getProcessNamesFromDb() {
		com.incture.pmc.workbox.dao.ProcessEventsDao dao = new com.incture.pmc.workbox.dao.ProcessEventsDao(em.getEntityManager());
		return dao.getAllProcessName();
	}


	@Override
	public WorKBoxDetailDto getTaskDetails(WorkboxRequestDto requestDto) {
		return oDataLocal.getTaskDetails(requestDto.getInstanceId(), requestDto.getSapOrigin(),requestDto.getUserId(),requestDto.getScode());
	}

	@Override
	public ResponseMessage performAction(WorkboxRequestDto requestDto) {
	//	System.err.println("[PMC][WorkBoxBean][Actions]initiated with  " +requestDto);
		ResponseMessage response = ODataServicesUtil.executeAction(requestDto);
		return response;
	}

	@Override
	public List<UserDetailDto> getUsers(WorkboxRequestDto requestDto) {
		return	oDataXpathLocal.getUsers(requestDto);
	}

	@Override
	public InputStream getAttachment(WorkboxRequestDto requestDto) {
	//	System.err.println("[PMC][WorkBoxBean][getAttachment]initiated with  " +requestDto);
		return ODataServicesUtil.getAttachment(requestDto.getInstanceId(), requestDto.getSapOrigin(),requestDto.getUserId(),requestDto.getScode(),requestDto.getAttachmentId());
	}

	@Override
	public ResponseMessage registerDevice(DeviceManagementDto deviceDto) {
		deviceDto.setUserId(deviceDto.getUserId().toUpperCase());
		deviceDto.setTaskCount(0);
		ResponseMessage responseMessage = new ResponseMessage();
		DeviceManagementDao dao = new DeviceManagementDao(em.getEntityManager());
		if(dao.createDeviceInstance(deviceDto).equals("SUCCESS")){
			responseMessage.setMessage("Device Registered Successfully");
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("0");
		}
		else{
			responseMessage.setMessage("Device Registration Failed");
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("1");
		}
		return responseMessage;
	}


	@Override
	public ResponseMessage removeDevice(DeviceManagementDto deviceDto) {
		deviceDto.setUserId(deviceDto.getUserId().toUpperCase());
		ResponseMessage responseMessage = new ResponseMessage();
		DeviceManagementDao dao = new DeviceManagementDao(em.getEntityManager());
		if(dao.getAndDeleteInstance(deviceDto).equals("SUCCESS")){
			responseMessage.setMessage("Device Deletion Successful");
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("0");
		}
		else{
			responseMessage.setMessage("Device Deletion Failed");
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("1");
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateDevice(DeviceManagementDto deviceDto) {
		ResponseMessage responseMessage = new ResponseMessage();
		DeviceManagementDao dao = new DeviceManagementDao(em.getEntityManager());
		if(dao.updateInstance(deviceDto).equals("SUCCESS")){
			responseMessage.setMessage("Device Updation Successful");
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("0");
		}
		else{
			responseMessage.setMessage("Device Updation Failed");
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("1");
		}
		return responseMessage;
	}

	@Override
	public String allDeviceInstance() {
	//	System.err.println("[PMC][ConsumeODataFacade][allDeviceInstance]initiated ");
		return new DeviceManagementDao(em.getEntityManager()).allDeviceInstance();
	}
	
	
/*	// Processes Available in both the lists 
    Purchase Order                                                  - TS14007970
    Travel Plan Request Approval                                    - TS91000610
    SLCM Course registration workflow                         		- TS91000879
    Phone Service Approval                               			- TS91000728
    Claims                                                          - TS91000199
    New Claims                                                      - TS91000758
    Shopping Cart                                                   - TS10008126
    Leave Request                                                   - TS12300097
    Stipend Approval for                                            - TS21000231
    Travel Plan Request Approval - Student 							- TS91000634
    Training Approval                                               - TS01200196

//Processes Not Available in KAUST list

    Access Request Approval dialog                                  - TS76308026     
    Release for Payment: Level 1                             	    - TS00407862     
    VMS                                                             - TS91000695_WS91000198_0000000073

//Processes Not Available in Incture list

    Change Phone Service Approval                					 - TS91000743     
    WS14000075_0000000120                                            - TS14008026     
    ( Not Identified )                                               - TS01200212   
    ( Not Identified Assuming it as VMS )       					 - TS91000695 */    




}
