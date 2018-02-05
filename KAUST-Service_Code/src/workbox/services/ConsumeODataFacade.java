package com.incture.pmc.workbox.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataDeltaFeed;
import org.apache.olingo.odata2.core.ep.entry.ODataEntryImpl;

import com.incture.pmc.util.ODataServicesUtil;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;
import com.incture.pmc.workbox.dao.ProcessEventsDao;
import com.incture.pmc.workbox.dao.TaskCustomAttributeDao;
import com.incture.pmc.workbox.dao.TaskEventsDao;
import com.incture.pmc.workbox.dao.TaskOwnersDao;
import com.incture.pmc.workbox.dto.ResponseMessage;
import com.incture.pmc.workbox.dto.WorKBoxDetailDto;
import com.incture.pmc.workbox.dto.WorkBoxAttachmentsDto;
import com.incture.pmc.workbox.dto.WorkBoxCommentsDto;
import com.incture.pmc.workbox.entity.TaskCustomAttributeDo;

/**
 * Session Bean implementation class ConsumeODataFacade
 */
@Stateless
public class ConsumeODataFacade implements ConsumeODataFacadeLocal {

	@EJB
	EntityManagerProviderLocal em;


	public ConsumeODataFacade() {
	}

	@Override
	public WorKBoxDetailDto getTaskDetails(String instanceId,String sapOrigin,String processor,String password) {

		WorKBoxDetailDto detailErrorDto = new WorKBoxDetailDto();
		detailErrorDto.setMessage(new ResponseMessage());
		detailErrorDto.getMessage().setStatus("FAILURE");
		detailErrorDto.getMessage().setStatusCode("1");

		//	System.err.println("[PMC][ConsumeODataFacade][getTaskDetails] method invoked with [instanceId]" + instanceId+"[sapOrigin]"+sapOrigin);
		String serviceUrl = "http://sthcigwdq1.kaust.edu.sa:8005/sap/opu/odata/IWPGW/TASKPROCESSING;mo;v=2";
		String usedFormatXml = PMCConstant.APPLICATION_ATOM_XML;
		try {
			Edm edm = ODataServicesUtil.readEdm(serviceUrl,processor,password);
			ODataEntry entryExpanded = ODataServicesUtil.readEntry(edm, serviceUrl, usedFormatXml, "TaskCollection", "SAP__Origin='"+sapOrigin+"',InstanceID='"+instanceId+"'", "Description,CustomAttributeData,UIExecutionLink,Attachments,Comments");
			WorKBoxDetailDto detailDto = convertToDetailDto(entryExpanded);
			detailDto.setMessage(new ResponseMessage());
			detailDto.getMessage().setMessage("Details Fetched succesfully");
			detailDto.getMessage().setStatus("SUCCESS");
			detailDto.getMessage().setStatusCode("0");
			if (!ServicesUtil.isEmpty(detailDto)) {
				detailDto.setActionURL("http://sthcigwdq1.kaust.edu.sa:8005/sap/opu/odata/IWPGW/TASKPROCESSING;mo;v=2 ");
				TaskCustomAttributeDao attrdao =  new TaskCustomAttributeDao(em.getEntityManager());
				TaskCustomAttributeDo attrEntity =attrdao.getAttributeInstance(instanceId);
				if (!ServicesUtil.isEmpty(attrEntity)&&!ServicesUtil.isEmpty(attrEntity.getActionList())){
					if(!ServicesUtil.isEmpty(attrEntity.getActions())){
						detailDto.setActionList(attrEntity.getActions()+","+detailDto.getActionList());
					}
					else{
						String actions = getDecisionOptions(sapOrigin,instanceId);
						 if(!actions.equals("FAILURE") && !ServicesUtil.isEmpty(actions)){
							 if (!new TaskCustomAttributeDao(em.getEntityManager()).updateAttributeInstance(instanceId,actions).equals("FAILURE")) {
								 detailDto.setActionList(actions+","+detailDto.getActionList());
							 }
						 }
					}
				}

				return detailDto;
			}
		} catch (Exception e) {
			System.err.println("[PMC][ConsumeODataFacade][getTaskDetails][error] " + e.getMessage());
		}
		detailErrorDto.getMessage().setMessage("Failed to get the Details of this instance");	
		return detailErrorDto;
	}

	private static WorKBoxDetailDto convertToCustomAttributeDto(ODataEntry createdEntry , WorKBoxDetailDto detailDto) {
		Map<String, Object> properties = createdEntry.getProperties();
		Set<Entry<String, Object>> entries = properties.entrySet();
		String name = "" ,value = "";
		for (Entry<String, Object> entry : entries) {

			if(entry.getKey().equals("Name")){
				name = (String) entry.getValue();
			}
			else if(entry.getKey().equals("Value")){
				value = (String) entry.getValue();
			}
		}
		if(!ServicesUtil.isEmpty(name) && !ServicesUtil.isEmpty(value)){
			if(name.equals("MasterDataCollection")){
				detailDto.setMasterDataCollection(value);
			}
			else if(name.equals("HeaderCollection")){
				detailDto.setHeaderCollection(value);
			}
			else if(name.equals("HeaderInformationDetail")){
				detailDto.setHeaderInformationDetail(value);
			}
			else if(name.equals("MoreDetailInformation")){
				detailDto.setMoreDetailInformation(value);
			}
			else if(name.equals("ItemAttachmentCollection")){
				detailDto.setItemAttachmentCollection(value);
			}
			else if(name.equals("ItemCollectionData")){
				detailDto.getItemCollectionData().add(value);
			}
			else if(name.equals("ItemCollectionHeader")){
				detailDto.setItemCollectionHeader(value);
			}
			else if(name.equals("AdditionalTabHeaderNote")){
				detailDto.setAdditionalTabHeaderNote(value);
			}
			else if(name.equals("informationTabContentTop")){
				detailDto.setInformationTabContentTop(value);
			}
		}
		return detailDto ;
	}

	private static WorkBoxCommentsDto convertToCommentsDto(ODataEntry createdEntry) {
		Map<String, Object> properties = createdEntry.getProperties();
		Set<Entry<String, Object>> entries = properties.entrySet();
		WorkBoxCommentsDto dto = new WorkBoxCommentsDto();
		final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Entry<String, Object> entry : entries) {
			String key = entry.getKey();
			if(!(ServicesUtil.isEmpty(key) && ServicesUtil.isEmpty(entry.getValue()))){
				if(key.equals("CreatedAt")){
					try {
						dto.setCommentedOn(dateFormatter.parse(ServicesUtil.calendarFormat((GregorianCalendar) entry.getValue())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				else if(key.equals("Text")){
					dto.setCommentText((String) entry.getValue());
				}
				else if (key.equals("CreatedBy")) {
					dto.setCommentBy((String) entry.getValue());
				} 
				else if (key.equals("CreatedByName")) {
					dto.setCommentByName((String) entry.getValue());
				} 
				else if (key.equals("ID")) {
					dto.setCommentId((String) entry.getValue());
				} 
			}
		}
		return dto ;
	}

	private static WorkBoxAttachmentsDto convertToAttachmentsDto(ODataEntry createdEntry) {
		Map<String, Object> properties = createdEntry.getProperties();
		Set<Entry<String, Object>> entries = properties.entrySet();
		WorkBoxAttachmentsDto dto = new WorkBoxAttachmentsDto();
		final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Entry<String, Object> entry : entries) {
			String key = entry.getKey();
			if(!(ServicesUtil.isEmpty(key) && ServicesUtil.isEmpty(entry.getValue()))){
				if(key.equals("CreatedAt")){
					try {
						dto.setAttachedOn(dateFormatter.parse(ServicesUtil.calendarFormat((GregorianCalendar) entry.getValue())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				else if(key.equals("FileName")){
					dto.setFileName((String) entry.getValue());
				}
				else if (key.equals("CreatedBy")) {
					dto.setAttachBy((String) entry.getValue());
				} 
				else if (key.equals("CreatedByName")) {
					dto.setAttachByName((String) entry.getValue());
				} 
				else if (key.equals("ID")) {
					dto.setAttachId((String) entry.getValue());
				} 
				else if(key.equals("mime_type")){
					dto.setMimeType((String) entry.getValue());
				}
				else if(key.equals("FileSize")){
					dto.setFileSize((Integer) entry.getValue());
				}
				else if(key.equals("FileDisplayName")){
					dto.setFileDisplayName((String) entry.getValue());
				}
			}
		}
		return dto ;
	}

	@SuppressWarnings("unchecked")
	private static WorKBoxDetailDto convertToDetailDto(ODataEntry createdEntry) {
		WorKBoxDetailDto detailDto = new  WorKBoxDetailDto();
		Map<String, Object> properties = createdEntry.getProperties();
		Set<Entry<String, Object>> entries = properties.entrySet();
		for (Entry<String, Object> entry : entries) {
			String key = entry.getKey();
			if(key.equals("CustomAttributeData")) {
				ODataDeltaFeed feed = (ODataDeltaFeed) entry.getValue();
				List<ODataEntry> inlineEntries =  feed.getEntries();
				for (ODataEntry oDataEntry : inlineEntries) {
					detailDto = convertToCustomAttributeDto(oDataEntry,detailDto);
				}
			} else if(key.equals("Description")) {
				ODataEntryImpl imp = (ODataEntryImpl) entry.getValue();
				Map<String, Object> descProperties  = imp.getProperties();
				Set<Entry<String, Object>> descEntries = descProperties.entrySet();
				for (Entry<String, Object> descEntry : descEntries) {
					if(descEntry.getKey().equals("DescriptionAsHtml")){
						detailDto.setDescriptionAsHtml((String) descEntry.getValue());
					}
					else if(descEntry.getKey().equals("Description")){
						detailDto.setDescription((String) descEntry.getValue());
					}
				}
			}else if (key.equals("TaskSupports")){

				HashMap<String,Boolean> map = (HashMap<String,Boolean>)entry.getValue();
				for (Map.Entry<String,Boolean> entry1 : map.entrySet()) {
					if(entry1.getValue()){
						if(entry1.getKey().equals("Comments")||entry1.getKey().equals("Confirm")||entry1.getKey().equals("Claim")||entry1.getKey().equals("Forward")||entry1.getKey().equals("Release")){
							if(!ServicesUtil.isEmpty(detailDto.getActionList()))
								detailDto.setActionList(detailDto.getActionList()+","+entry1.getKey());
							else{
								detailDto.setActionList(entry1.getKey());	
							}
						}
					}
				}
			}else if(key.equals("Comments")) {
				ODataDeltaFeed feed = (ODataDeltaFeed) entry.getValue();
				List<ODataEntry> inlineEntries =  feed.getEntries();
				List<WorkBoxCommentsDto> commentCollection = new ArrayList<WorkBoxCommentsDto>(); 
				int commentCount = 0;
				for (ODataEntry oDataEntry : inlineEntries) {
					WorkBoxCommentsDto dto = convertToCommentsDto(oDataEntry);
					commentCollection.add(dto);
					commentCount++;
				}
				detailDto.setCommentCount((Integer) commentCount);
				detailDto.setCommentCollection(commentCollection);
			}
			else if(key.equals("Attachments")) {
				ODataDeltaFeed feed = (ODataDeltaFeed) entry.getValue();
				List<ODataEntry> inlineEntries =  feed.getEntries();
				List<WorkBoxAttachmentsDto> attachmentCollection = new ArrayList<WorkBoxAttachmentsDto>(); 
				int attachmentCount = 0;
				for (ODataEntry oDataEntry : inlineEntries) {
					WorkBoxAttachmentsDto dto = convertToAttachmentsDto(oDataEntry);
					attachmentCollection.add(dto);
					attachmentCount++;
				}
				detailDto.setAttachmentCount((Integer)attachmentCount);
				detailDto.setAttachmentCollection(attachmentCollection);
			}
		}
		//	System.err.println("[PMC][ConsumeODataFacade][convertToDetailDto][end] with " +detailDto);
		return detailDto;
	}

	private String getDecisionOptions(String sapOrigin,String instanceId){
		String serviceUrl = "http://sthcigwdq1.kaust.edu.sa:8005/sap/opu/odata/IWPGW/TASKPROCESSING;mo;v=2/DecisionOptions?SAP__Origin='"+sapOrigin+"'&InstanceID='"+instanceId+"'";
		System.err.println("[PMC][ConsumeODataFacade][getDecisionOptions] method invoked with [instanceId]" + instanceId+"[sapOrigin]"+sapOrigin+" [url]"+serviceUrl);
		
		try {
			String  actions = ODataServicesUtil.readActions(serviceUrl, PMCConstant.APPLICATION_XML);
			return actions;

		} catch (Exception e) {
			System.err.println("[PMC][ConsumeODataFacade][getDecisionOptions][error]" + e.getMessage());
		}
		return "FAILURE";
	}



	@Override
	public String allProcessInstance() {
		System.err.println("[PMC][ConsumeODataFacade][allProcessInstance]initiated ");
		return new ProcessEventsDao(em.getEntityManager()).allProcessInstance();
	}

	@Override
	public String allAttrInstance() {
		System.err.println("[PMC][ConsumeODataFacade][allAttrInstance]initiated ");
		return new TaskCustomAttributeDao(em.getEntityManager()).allAttrInstance();
	}


	@Override
	public String allOwnersInstance() {
		System.err.println("[PMC][ConsumeODataFacade][allOwnersInstance]initiated ");
		return new TaskOwnersDao(em.getEntityManager()).allOwnersInstance();
	}

	@Override
	public String allTaskInstance() {
		System.err.println("[PMC][ConsumeODataFacade][allTaskInstance]initiated ");
		return new TaskEventsDao(em.getEntityManager()).allTaskInstance();
	}




	/*
	 * UNCOMMENT TO GET DATA FROM ECC AND STORE IN DB USING OLINGO 
	 * 
	 * @Override
	public ResponseMessage getDataFromECC(String processor,String password) {
		System.err.println("[PMC][ConsumeODataFacade][getDataFromECC] method invoked with [processor]" + processor);
		ResponseMessage responseMessage = new ResponseMessage();
		String serviceUrl = "http://sthcigwdq1.kaust.edu.sa:8005/sap/opu/odata/IWPGW/TASKPROCESSING;mo;v=2";
		String usedFormatXml = PMCConstant.APPLICATION_ATOM_XML;
		//this.allOwnersInstance();
		try {
			long odataNano = System.nanoTime();
			System.err.println("[PMC][ConsumeODataFacade][Read Edm] method invoked at " + odataNano );
			Edm edm = ODataServicesUtil.readEdm(serviceUrl,processor,password);
			System.err.println("[PMC][ConsumeODataFacade]Read default EntityContainer: "
					+ edm.getDefaultEntityContainer().getName());

			System.err.println("[PMC][ConsumeODataFacade][Read Feed ] method invoked ");
			ODataFeed feed = ODataServicesUtil.readFeed(edm, serviceUrl, usedFormatXml, "TaskCollection");
			System.err.println("[PMC][ConsumeODataFacade][Time] taken for fetching data " + ( System.nanoTime() -odataNano));
			responseMessage.setMessage("Data Consumed Successfully");
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("0");
			int i = 0;
			odataNano = System.nanoTime();

			List<String> instanceList = new ArrayList<String>();
			for (ODataEntry createdEntry : feed.getEntries()) {
				i++;
				String returnedValue = convertToDto(createdEntry,processor);
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
			System.err.println("[PMC][ConsumeODataFacade][getDataFromECC][no of entries]" + i+ "[timeTaken]"+ ( System.nanoTime() -odataNano));

			if (new TaskOwnersDao(em.getEntityManager()).deleteNonExistingTasks(instanceList,processor).equals("FAILURE")) {
				responseMessage.setMessage("Data consumption failed as it failed to delete owners");
				responseMessage.setStatus("FAILURE");
				responseMessage.setStatusCode("1");
				return responseMessage;
			}
		} catch (Exception e) {
			System.err.println("[PMC][ConsumeODataFacade][getDataFromECC][error] " + e.getMessage());
			responseMessage.setMessage("Data Consumption failed because - " + e.getMessage());
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("1");
		}
		return responseMessage;
	}

	@SuppressWarnings("unchecked")
	private String convertToDto(ODataEntry createdEntry,String processor) {

		Map<String, Object> properties = createdEntry.getProperties();
		Set<Entry<String, Object>> entries = properties.entrySet();
		WorkBoxDto returnDto = new WorkBoxDto();
		WorkBoxAppDataDto dto = new WorkBoxAppDataDto();
		ProcessEventsDto processDto = new ProcessEventsDto();
		TaskEventsDto taskDto = new TaskEventsDto();
		TaskOwnersDto ownersDto = new TaskOwnersDto();
		TaskCustomAttributeDto attributeDto = new TaskCustomAttributeDto();
		String sapOrigin = null;
		for (Entry<String, Object> entry : entries) {
			try {
				String key =  entry.getKey();
				if (!ServicesUtil.isEmpty(entry.getValue()) && !ServicesUtil.isEmpty(key)) {
					final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (key.equals("SAP__Origin")) {
						sapOrigin = (String) entry.getValue();
						if (!(sapOrigin.equals("SRM_WF") || sapOrigin.equals("ECC_WF") || sapOrigin.equals("GRC_WF"))) {
							sapOrigin = "INVALID";
							break;
						}
						else{
							attributeDto.setSapOrigin(sapOrigin);
						}
					}
					else if (key.equals("InstanceID")) {
						dto.setInstanceId((String) entry.getValue());
						taskDto.setEventId((String) entry.getValue());
						ownersDto.setEventId((String) entry.getValue());
						attributeDto.setInstanceId((String) entry.getValue());
					} else if (key.equals("TaskDefinitionID")) {
						dto.setTaskDefinitionId((String) entry.getValue());
						taskDto.setProcessId(processDto.getProcessId());
						attributeDto.setProcessInstanceId((String) entry.getValue());
					} else if (key.equals("CreatedByName")) {
						dto.setCreatedBy((String) entry.getValue());
						processDto.setStartedByDisplayName((String) entry.getValue());
					} else if (key.equals("Description")) {
						dto.setDescription((String) entry.getValue());
						taskDto.setDescription((String) entry.getValue());
//						UnComment to get forwarding user also
//						  }else if (key.equals("ForwardingUserName")) {
//						dto.setForwardingUser((String) entry.getValue());
//						ownersDto.setTaskOwnerDisplayName((String) entry.getValue());
//					} else if (key.equals("ForwardingUser")) {
//						ownersDto.setTaskOwner((String) entry.getValue());
					} else if (key.equals("Status")) {
						dto.setTaskStatus((String) entry.getValue());
						if (!(entry.getValue().toString().equals("COMPLETED"))) {
							processDto.setStatus("INPROGRESS");
						} else {
							processDto.setStatus((String) entry.getValue());
						}
						taskDto.setStatus((String) entry.getValue());
					} else if (key.equals("ProcessorName")) {
						taskDto.setCurrentProcessorDisplayName((String) entry.getValue());
					} else if (key.equals("Priority")) {
						dto.setTaskPriority((String) entry.getValue());
						taskDto.setPriority((String) entry.getValue());
					} else if (key.equals("CreatedBy")) {
						dto.setCreatedBy((String) entry.getValue());
						processDto.setStartedBy((String) entry.getValue());
					} else if (key.equals("TaskDefinitionName")) {
						dto.setTaskDefinitionName((String) entry.getValue());
						processDto.setName((String) entry.getValue());
						taskDto.setProcessName((String) entry.getValue());
					} else if (key.equals("ForwardedUser")) {
						dto.setForwardedUser((String) entry.getValue());
						taskDto.setForwardedBy((String) entry.getValue());
					} else if (key.equals("Processor")) {
						dto.setProcessorDetail(((String) entry.getValue()).toUpperCase());
						taskDto.setCurrentProcessor((String) entry.getValue());
					}else if (key.equals("TaskSupports")){
						HashMap<String,Boolean> map = (HashMap<String,Boolean>)entry.getValue();
						for (Map.Entry<String,Boolean> entry1 : map.entrySet()) {
							if(entry1.getValue()){
								if(entry1.getKey().equals("Comments")||entry1.getKey().equals("Claim")||entry1.getKey().equals("Forward")){
									if(!ServicesUtil.isEmpty(attributeDto.getActionList()))
										attributeDto.setActionList(attributeDto.getActionList()+","+entry1.getKey());
									else{
										attributeDto.setActionList(entry1.getKey());	
									}
								}
							}
						}
					}
					else if (key.equals("IsEscalated")) {
						if ((boolean) entry.getValue().equals("true")) {
							attributeDto.setEscalated(true);
						} else {
							attributeDto.setEscalated(false);
						}
					} else if (key.equals("TaskTitle")) {
						attributeDto.setAttribute((String) entry.getValue());
					} else if (key.equals("ForwardedOn")) {
						dto.setForwardedOn(
								dateFormatter.parse(ServicesUtil.calendarFormat((GregorianCalendar) entry.getValue())));
					} else if (key.equals("StartDeadLine")) {
						dto.setStartDeadLine(ServicesUtil.resultAsDate(entry.getValue()));
						attributeDto.setStartDeadLine(ServicesUtil.resultAsDate(entry.getValue()));
					} else if (key.equals("CreatedOn")) {
						dto.setCreatedOn(dateFormatter.parse(ServicesUtil.calendarFormat((GregorianCalendar) entry.getValue())));
						processDto.setStartedAt(dateFormatter.parse(ServicesUtil.calendarFormat((GregorianCalendar) entry.getValue())));
						taskDto.setCreatedAt(dateFormatter.parse(ServicesUtil.calendarFormat((GregorianCalendar) entry.getValue())));
					} else if (key.equals("CompletionDeadLine")) {
						dto.setCompletionDeadLine(ServicesUtil.resultAsDate(entry.getValue()));
						taskDto.setCompletionDeadLine(ServicesUtil.resultAsDate(entry.getValue()));
					} else if (key.equals("ExpiryDate")) {
						attributeDto.setExpiryDate(ServicesUtil.resultAsDate(entry.getValue()));
					} else if (key.equals("CompletedOn")) {
						taskDto.setCompletedAt(ServicesUtil.resultAsDate(entry.getValue()));
						processDto.setCompletedAt(ServicesUtil.resultAsDate(entry.getValue()));
					} 
				}
			} catch (Exception e) {
				System.err.println("[PMC][ConsumeODataFacade][convertToDto][error] " + e.getMessage());
			}
		}
		System.err.println("[PMC][ConsumeODataFacade][convertToDto][sapOrigin]" + sapOrigin);
		if (sapOrigin.equals("INVALID")) {
			return "SUCCESS";
		} else {
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
				System.err.println("[PMC][ConsumeODataFacade][convertToDto][inUpdateInstance]");
				if(!updateInstance(processDto, taskDto, attributeDto).equals("SUCCESS")){
					return "FAILURE"; 
				}
			} else {
				System.err.println("[PMC][ConsumeODataFacade][convertToDto][inCreateInstance]");
				if(!createInstance(processDto, taskDto,attributeDto).equals("SUCCESS")){
					return "FAILURE";
				}
			}
			if(!saveAndUpdateTaskOwners(ownersDto,taskDto.getStatus()).equals("SUCCESS")){
				return "FAILURE";
			}

		}
		returnDto.setAppData(dto);
		return taskDto.getEventId();
	}

	private String createInstance(ProcessEventsDto processDto, TaskEventsDto taskDto,
			TaskCustomAttributeDto attributeDto) {
		System.err.println("[PMC][ConsumeODataFacade][createInstance][createProcessInstance] " + processDto
				+ "[createTaskInstance]" + taskDto 
				+ "[createTaskAttributeInstance]" + attributeDto+"[status]"+taskDto.getStatus());
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
		System.err.println("[PMC][ConsumeODataFacade][getDecisionOptions] method invoked with [instanceId]" + instanceId+"[sapOrigin]"+sapOrigin);
		String serviceUrl = "http://sthcigwdq1.kaust.edu.sa:8005/sap/opu/odata/IWPGW/TASKPROCESSING;mo;v=2/DecisionOptions?SAP__Origin='"+sapOrigin+"'&InstanceID='"+instanceId+"'";
		String usedFormatXml = PMCConstant.APPLICATION_XML;
		System.err.println("[PMC][ConsumeODataFacade][getDecisionOptions] [url]"+serviceUrl);

		try {
			String  actions = ODataServicesUtil.readActions(serviceUrl, usedFormatXml);
			return actions;

		} catch (Exception e) {
			System.err.println("[PMC][ConsumeODataFacade][getDecisionOptions][error]" + e.getMessage());
		}

		return "FAILURE";

	}

	private String updateInstance(ProcessEventsDto processDto, TaskEventsDto taskDto,
			TaskCustomAttributeDto attributeDto) {
		System.err.println("[PMC][ConsumeODataFacade][updateInstance][ProcessInstance] " + processDto
				+ "[TaskInstance]" + taskDto 
				+ "[TaskAttributeInstance]" + attributeDto);
		if (new ProcessEventsDao(em.getEntityManager()).updateProcessInstance(processDto).equals("FAILURE")) {
			return "FAILURE";
		}
		if (new TaskEventsDao(em.getEntityManager()).updateTaskInstance(taskDto).equals("FAILURE")) {
			return "FAILURE";
		}
		TaskCustomAttributeDao attrdao =  new TaskCustomAttributeDao(em.getEntityManager());
		TaskCustomAttributeDo attrEntity =attrdao.getAttributeInstance(attributeDto.getInstanceId());
		System.err.println("[PMC][ConsumeODataFacade][convertToDto][TaskCustomAttributeDo]" + attrEntity+"[attributeDto.getProcessInstanceId()]"+attributeDto.getProcessInstanceId());
		if(!ServicesUtil.isEmpty(attrEntity)){
			attributeDto.setCustomId(attrEntity.getCustomId());
			attributeDto.setActions(attrEntity.getActions());
			System.err.println("update Dto : " + attributeDto);
			if (attrdao.updateAttrInstance(attributeDto).equals("FAILURE")) {
				return "FAILURE";
			}
		}
		return "SUCCESS";
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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
	}*/




}
