package com.incture.pmc.workbox.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.incture.pmc.util.ODataServicesUtil;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;
import com.incture.pmc.workbox.dao.DeviceManagementDao;
import com.incture.pmc.workbox.dao.ProcessEventsDao;
import com.incture.pmc.workbox.dao.TaskCustomAttributeDao;
import com.incture.pmc.workbox.dao.TaskEventsDao;
import com.incture.pmc.workbox.dao.TaskOwnersDao;
import com.incture.pmc.workbox.dto.ProcessEventsDto;
import com.incture.pmc.workbox.dto.ResponseMessage;
import com.incture.pmc.workbox.dto.TaskCustomAttributeDto;
import com.incture.pmc.workbox.dto.TaskEventsDto;
import com.incture.pmc.workbox.dto.TaskOwnersDto;
import com.incture.pmc.workbox.dto.UserDetailDto;
import com.incture.pmc.workbox.dto.WorkboxRequestDto;
import com.incture.pmc.workbox.entity.TaskCustomAttributeDo;
import com.incture.pmc.workbox.entity.TaskEventsDo;
import com.incture.pmc.workbox.entity.TaskOwnersDo;

/**
 * Session Bean implementation class ConsumeODataFacade
 */
@Stateless
public class ConsumeODataXpathFacade implements ConsumeODataXpathFacadeLocal {

	@EJB
	EntityManagerProviderLocal em;


	public ConsumeODataXpathFacade() {
	}

	@Override
	public ResponseMessage getDataFromECC(String processor,String scode) {
		//	System.err.println("[PMC][ConsumeODataFacade][Xpath][Xpath][getDataFromECC] method invoked with [processor]" + processor);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setMessage("Data Consumed Successfully");
		responseMessage.setStatus("SUCCESS");
		responseMessage.setStatusCode("0");
		try{
		//	final long startTime = System.nanoTime();
			NodeList nodeList = ODataServicesUtil.xPathOdata("http://sthcigwdq1.kaust.edu.sa:8005/sap/opu/odata/IWPGW/TASKPROCESSING;mo;v=2/TaskCollection", PMCConstant.APPLICATION_XML, PMCConstant.HTTP_METHOD_GET, "/feed /entry ",processor,scode);
			if(nodeList.getLength()>0){
				List<String> instanceList =  new ArrayList<String>();
				int i;
				for (i = 0; i < nodeList.getLength(); i++) {
					Node nNode = nodeList.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						String returnedValue = convertToDto(nNode ,processor ,scode);
						if (returnedValue.equals("FAILURE")) {
							responseMessage.setMessage("Data consumption failed");
							responseMessage.setStatus("FAILURE");
							responseMessage.setStatusCode("1");
							break;
						}
						else if(!returnedValue.equals("FAILURE") && !returnedValue.equals("SUCCESS")){
							instanceList.add(returnedValue);	
						}
					}
				}
				if (new TaskOwnersDao(em.getEntityManager()).deleteNonExistingTasks(instanceList,processor).equals("FAILURE")) {
					responseMessage.setMessage("Data consumption failed as it failed to delete owners");
					responseMessage.setStatus("FAILURE");
					responseMessage.setStatusCode("1");
					return responseMessage;
				}
				//	final long duration = System.nanoTime() - startTime;
				//	System.err.println("[PMC][ConsumeODataFacade][Xpath][getDataFromECC][no of entries]" + i+ "[timeTaken]"+ duration);
			}
		}
		catch (Exception e) {
			System.err.println("[PMC][ConsumeODataFacade][Xpath][getDataFromECC][error] " + e.getMessage());
			responseMessage.setMessage("Data Consumption failed because - " + e.getMessage());
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("1");
		}
		;

		if (!new DeviceManagementDao(em.getEntityManager()).updateCountOfUser(processor.toUpperCase(), 0).equals("SUCCESS")) {
				System.err.println("[PMC][ConsumeODataFacade][Xpath][getDataFromECC][error] failed to update count to 0");
		}

		return responseMessage;
	}

	private String convertToDto(Node nNode, String processor ,String scode) {
		Element eElement = (Element) nNode;
		Element mproperties = (Element) eElement.getElementsByTagName("m:properties").item(0);
		String sapOrigin = mproperties.getElementsByTagName("d:SAP__Origin").item(0).getTextContent();
		if ((sapOrigin.equals("SRM_WF") || sapOrigin.equals("ECC_WF") || sapOrigin.equals("GRC_WF"))) {
			TaskEventsDto taskDto = new TaskEventsDto();
			TaskOwnersDto ownersDto = new TaskOwnersDto();
			TaskCustomAttributeDto attributeDto = new TaskCustomAttributeDto();
			ProcessEventsDto processDto = new ProcessEventsDto();

			String eventId =  mproperties.getElementsByTagName("d:InstanceID").item(0).getTextContent();
			taskDto.setEventId(eventId);
			ownersDto.setEventId(eventId);
			attributeDto.setInstanceId(eventId);
			attributeDto.setSapOrigin(sapOrigin);
			taskDto.setProcessId(processDto.getProcessId());
			attributeDto.setProcessInstanceId(mproperties.getElementsByTagName("d:TaskDefinitionID").item(0).getTextContent());
			processDto.setProcessInstanceId(mproperties.getElementsByTagName("d:TaskDefinitionID").item(0).getTextContent());
			processDto.setStartedByDisplayName( mproperties.getElementsByTagName("d:CreatedByName").item(0).getTextContent());
			processDto.setStartedByDisplayName(mproperties.getElementsByTagName("d:CreatedBy").item(0).getTextContent());
			taskDto.setDescription(mproperties.getElementsByTagName("d:Description").item(0).getTextContent());
			ownersDto.setTaskOwnerDisplayName(mproperties.getElementsByTagName("d:ForwardingUserName").item(0).getTextContent());
			ownersDto.setTaskOwner(mproperties.getElementsByTagName("d:ForwardingUser").item(0).getTextContent());
			taskDto.setCurrentProcessorDisplayName(mproperties.getElementsByTagName("d:ProcessorName").item(0).getTextContent());
			taskDto.setPriority(mproperties.getElementsByTagName("d:Priority").item(0).getTextContent());
			processDto.setStartedBy(mproperties.getElementsByTagName("d:CreatedBy").item(0).getTextContent());
			processDto.setName(mproperties.getElementsByTagName("d:TaskDefinitionName").item(0).getTextContent());
			taskDto.setProcessName(mproperties.getElementsByTagName("d:TaskDefinitionName").item(0).getTextContent());
			taskDto.setForwardedBy(mproperties.getElementsByTagName("d:ForwardedUser").item(0).getTextContent());
			taskDto.setCurrentProcessor(mproperties.getElementsByTagName("d:Processor").item(0).getTextContent());
			attributeDto.setAttribute(mproperties.getElementsByTagName("d:TaskTitle").item(0).getTextContent());
			taskDto.setForwardedAt(dateParser(mproperties, "d:ForwardedOn"));
			attributeDto.setStartDeadLine(dateParser(mproperties, "d:StartDeadLine"));
			processDto.setStartedAt(dateParser(mproperties, "d:CreatedOn"));
			taskDto.setCreatedAt(dateParser(mproperties, "d:CreatedOn"));
			taskDto.setCompletionDeadLine(dateParser(mproperties, "d:CompletionDeadLine"));
			attributeDto.setExpiryDate(dateParser(mproperties, "d:ExpiryDate"));
			taskDto.setCompletedAt(dateParser(mproperties, "d:CompletedOn"));
			processDto.setCompletedAt(dateParser(mproperties, "d:CompletedOn"));


			if (mproperties.getElementsByTagName("d:IsEscalated").item(0).getTextContent().equals("true")) {
				attributeDto.setEscalated(true);
			} else {
				attributeDto.setEscalated(false);
			}


			String status = mproperties.getElementsByTagName("d:Status").item(0).getTextContent();
			if (!(status.equals("COMPLETED"))) {
				processDto.setStatus("INPROGRESS");
			} else {
				processDto.setStatus(status);
			}
			taskDto.setStatus(status);

			Element dTaskSupports = (Element) mproperties.getElementsByTagName("d:TaskSupports").item(0);	
			if(dTaskSupports.getElementsByTagName("d:Release").item(0).getTextContent().equals("true")){
				attributeDto.setActionList("Release");	
			}
			if(dTaskSupports.getElementsByTagName("d:Claim").item(0).getTextContent().equals("true")){
				if(!ServicesUtil.isEmpty(attributeDto.getActionList())){
					attributeDto.setActionList(attributeDto.getActionList()+",Claim");
				}
				else{
					attributeDto.setActionList("Claim");	
				}

			}
			if(dTaskSupports.getElementsByTagName("d:Forward").item(0).getTextContent().equals("true")){
				if(!ServicesUtil.isEmpty(attributeDto.getActionList())){
					attributeDto.setActionList(attributeDto.getActionList()+",Forward");
				}
				else{
					attributeDto.setActionList("Forward");	
				}

			}
			if(dTaskSupports.getElementsByTagName("d:Comments").item(0).getTextContent().equals("true")){
				if(!ServicesUtil.isEmpty(attributeDto.getActionList())){
					attributeDto.setActionList(attributeDto.getActionList()+",Comments");
				}
				else{
					attributeDto.setActionList("Comments");	
				}

			}
			/*
			 * UNCOMMENT TO DISPLAY ATTACHMENTS ALSO IN ACTIONS
			 * 
			 * if(dTaskSupports.getElementsByTagName("d:Attachments").item(0).getTextContent().equals("true")){
				if(!ServicesUtil.isEmpty(attributeDto.getActionList())){
					attributeDto.setActionList(attributeDto.getActionList()+",Attachments");
				}
				else{
					attributeDto.setActionList("Attachments");	
				}

			}*/
			ownersDto.setTaskOwnerDisplayName(processor);
			ownersDto.setTaskOwner(processor);
			if (!ServicesUtil.isEmpty(taskDto.getStatus()) && taskDto.getStatus().equals("RESERVED")) {
				ownersDto.setIsProcessed(true);
			} else {
				ownersDto.setIsProcessed(false);
			}
			List<TaskEventsDo> taskEventDos = new TaskEventsDao(em.getEntityManager()).checkIfTaskInstanceExists(taskDto.getEventId());
			if (!ServicesUtil.isEmpty(taskEventDos)) {
				for(TaskEventsDo taskEventDo : taskEventDos){
					taskDto.setProcessId(taskEventDo.getTaskEventsDoPK().getProcessId());
					processDto.setProcessId(taskEventDo.getTaskEventsDoPK().getProcessId());
				}
				//	System.err.println("[PMC][ConsumeODataFacade][Xpath][convertToDto][inUpdateInstance]");
				if(!updateInstance(processDto, taskDto, attributeDto).equals("SUCCESS")){
					return "FAILURE"; 
				}
			} else {
				//	System.err.println("[PMC][ConsumeODataFacade][Xpath][convertToDto][inCreateInstance]");
				if(!createInstance(processDto, taskDto,attributeDto).equals("SUCCESS")){
					return "FAILURE";
				}
			}
			if(!saveAndUpdateTaskOwners(ownersDto,taskDto.getStatus()).equals("SUCCESS")){
				return "FAILURE";
			}
			return taskDto.getEventId();
		}
		else{
			return "SUCCESS";
		}
	}

	private Date dateParser(Element mproperties,String key){

		if(!ServicesUtil.isEmpty(mproperties.getElementsByTagName(key)))
			return ServicesUtil.resultTAsDate(mproperties.getElementsByTagName(key).item(0).getTextContent());
		return null;
	}

	private String createInstance(ProcessEventsDto processDto, TaskEventsDto taskDto,
			TaskCustomAttributeDto attributeDto) {
		/*System.err.println("[PMC][ConsumeODataFacade][Xpath][createInstance][createProcessInstance] " + processDto
				+ "[createTaskInstance]" + taskDto 
				+ "[createTaskAttributeInstance]" + attributeDto+"[status]"+taskDto.getStatus());*/
		if (new ProcessEventsDao(em.getEntityManager()).createProcessInstance(processDto).equals("FAILURE")) {
			return "FAILURE";
		}
		if (new TaskEventsDao(em.getEntityManager()).createTaskInstance(taskDto).equals("FAILURE")) {
			return "FAILURE";
		}

		if(!(taskDto.getStatus().equals("COMPLETED"))){
			String actions = getDecisionOptions(attributeDto.getSapOrigin(),taskDto.getEventId());
			if(!actions.equals("FAILURE")){
				attributeDto.setActions(actions);
			}
			else{
				return "FAILURE";
			}
		}
		if (new TaskCustomAttributeDao(em.getEntityManager()).createAttrInstance(attributeDto).equals("FAILURE")) {
			return "FAILURE";
		}
		return "SUCCESS";
	}

	private String getDecisionOptions(String sapOrigin,String instanceId){
		//	System.err.println("[PMC][ConsumeODataFacade][getDecisionOptions] method invoked with [instanceId]" + instanceId+"[sapOrigin]"+sapOrigin+" [url]"+serviceUrl));

		String serviceUrl = "http://sthcigwdq1.kaust.edu.sa:8005/sap/opu/odata/IWPGW/TASKPROCESSING;mo;v=2/DecisionOptions?SAP__Origin='"+sapOrigin+"'&InstanceID='"+instanceId+"'";
		try {
			String  actions = ODataServicesUtil.readActions(serviceUrl, PMCConstant.APPLICATION_XML);
			return actions;

		} catch (Exception e) {
			System.err.println("[PMC][ConsumeODataFacade][getDecisionOptions][error]" + e.getMessage());
		}
		return "FAILURE";
	}
	
	 /*   
	  *   This method  updates the instances of the ProcessEventsDto,  TaskEventsDto, TaskCustomAttributeDto
	  *   because they already exists in the db with the particular event id
	  */

	private String updateInstance(ProcessEventsDto processDto, TaskEventsDto taskDto,
			TaskCustomAttributeDto attributeDto) {
		/*	System.err.println("[PMC][ConsumeODataFacade][Xpath][updateInstance][ProcessInstance] " + processDto
				+ "[TaskInstance]" + taskDto 
				+ "[TaskAttributeInstance]" + attributeDto);*/
		if (new ProcessEventsDao(em.getEntityManager()).updateProcessInstance(processDto).equals("FAILURE")) {
			return "FAILURE";
		}
		if (new TaskEventsDao(em.getEntityManager()).updateTaskInstance(taskDto).equals("FAILURE")) {
			return "FAILURE";
		}
		TaskCustomAttributeDao attrdao =  new TaskCustomAttributeDao(em.getEntityManager());
		TaskCustomAttributeDo attrEntity =attrdao.getAttributeInstance(attributeDto.getInstanceId());
		//	System.err.println("[PMC][ConsumeODataFacade][Xpath][convertToDto][TaskCustomAttributeDo]" + attrEntity+"[attributeDto.getProcessInstanceId()]"+attributeDto.getProcessInstanceId());
		if(!ServicesUtil.isEmpty(attrEntity)){
			attributeDto.setCustomId(attrEntity.getCustomId());
			attributeDto.setActions(attrEntity.getActions());
			//System.err.println("update Dto : " + attributeDto);
			if (attrdao.updateAttrInstance(attributeDto).equals("FAILURE")) {
				return "FAILURE";
			}
		}
		return "SUCCESS";
	}

	 /*   
	  *   This method  removes any records for which the event id is same but not the task owner  
	  *   updates record for which the event id and the task owner  are same
	  *   else it creates if the record doesnt exists 
	  *   in the Task Owners table 
	  */
	
	
	private String saveAndUpdateTaskOwners(TaskOwnersDto dto ,String status){
		TaskOwnersDao dao = new TaskOwnersDao(em.getEntityManager());
		List<TaskOwnersDo> entities = dao.getOwnerInstances(dto.getEventId());
		boolean isExists = false;
		if(!ServicesUtil.isEmpty(entities)){
			for(TaskOwnersDo entity : entities){
				if(entity.getTaskOwnersDoPK().getEventId().equals(dto.getEventId())&&entity.getTaskOwnersDoPK().getTaskOwner().equals(dto.getTaskOwner())){
					dao.updateTaskOwnerInstance(dto);
					isExists = true;
				}
				else{
					if(dao.deleteInstance(entity).equals("FAILURE")){
						return "FAILURE";	
					}
				}
			}
		}
		if(!isExists){
			if (dao.createTaskOwnerInstance(dto).equals("FAILURE")) {
				return "FAILURE";
			}
		}
		return "SUCCESS";
	}
	
	 /*   
	  *   This method  gets all the users to which a task can be forwarded to ( using xpath services )
	  */

	@Override
	public List<UserDetailDto> getUsers(WorkboxRequestDto requestDto) {
		List<UserDetailDto> instanceList= null;
		try{
			String url = "http://sthcigwdq1.kaust.edu.sa:8005/sap/opu/odata/IWPGW/TASKPROCESSING;mo;v=2/SearchUsers?SAP__Origin=%27"+requestDto.getSapOrigin()+"%27&SearchPattern=%27"+requestDto.getText()+"%27&MaxResults=100";
			NodeList nodeList = ODataServicesUtil.xPathOdata(url, PMCConstant.APPLICATION_XML, PMCConstant.HTTP_METHOD_GET, "/feed /entry ",requestDto.getUserId(),requestDto.getScode());
			if(nodeList.getLength()>0){
				instanceList =  new ArrayList<UserDetailDto>();
				int i;
				for (i = 0; i < nodeList.getLength(); i++) {
					Node nNode = nodeList.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						UserDetailDto userDetail = convertToUserDto(nNode);
						instanceList.add(userDetail);	
					}
				}
			}
		}
		catch (Exception e) {
			System.err.println("[PMC][ConsumeODataFacade][Xpath][getUsers][error] " + e.getMessage());
		}
		return instanceList;
	}
	

	 /*   
	  *   This method  converts the nodes recieved from service which has the details of the user to UserDetailDto  
	  */

	private UserDetailDto convertToUserDto(Node nNode) {
		Element eElement = (Element) nNode;
		Element mproperties = (Element) eElement.getElementsByTagName("m:properties").item(0);
		UserDetailDto userDto = new UserDetailDto();
		userDto.setDepartment(mproperties.getElementsByTagName("d:Department").item(0).getTextContent());
		userDto.setDisplayName(mproperties.getElementsByTagName("d:DisplayName").item(0).getTextContent());
		userDto.setEmail(mproperties.getElementsByTagName("d:Email").item(0).getTextContent());
		userDto.setUniqueName(mproperties.getElementsByTagName("d:UniqueName").item(0).getTextContent());

		return userDto;
	}



}
