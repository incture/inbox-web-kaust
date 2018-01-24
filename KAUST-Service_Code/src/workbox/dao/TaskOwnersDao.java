package com.incture.pmc.workbox.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.incture.pmc.util.ExecutionFault;
import com.incture.pmc.util.InvalidInputFault;
import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.ServicesUtil;
import com.incture.pmc.workbox.dto.ExistingDataDto;
import com.incture.pmc.workbox.dto.TaskOwnersDto;
import com.incture.pmc.workbox.entity.TaskOwnersDo;
import com.incture.pmc.workbox.entity.TaskOwnersDoPK;

/**
 * The <code>TaskOwnersDao</code> converts Do to Dto and vice-versa <code>Data
 * Access Objects <code>
 * 
 * @author INC00609
 * @version 1.0
 * @since 2017-21-09
 */
public class TaskOwnersDao extends BaseDao<TaskOwnersDo, TaskOwnersDto> {

	public TaskOwnersDao(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public TaskOwnersDto exportDto(TaskOwnersDo fromDo) {
		TaskOwnersDto outputDto = new TaskOwnersDto();
		if (!ServicesUtil.isEmpty(fromDo.getTaskOwnersDoPK().getEventId()))
			outputDto.setEventId(fromDo.getTaskOwnersDoPK().getEventId().trim());
		if (!ServicesUtil.isEmpty(fromDo.getTaskOwnersDoPK().getTaskOwner()))
			outputDto.setTaskOwner(fromDo.getTaskOwnersDoPK().getTaskOwner().trim());
		if (!ServicesUtil.isEmpty(fromDo.getTaskOwnerDisplayName()))
			outputDto.setTaskOwnerDisplayName(fromDo.getTaskOwnerDisplayName().trim());
		if (!ServicesUtil.isEmpty(fromDo.getIsProcessed()))
			outputDto.setIsProcessed(fromDo.getIsProcessed());

		return outputDto;
	}

	@Override
	protected TaskOwnersDo importDto(TaskOwnersDto fromDto) throws InvalidInputFault, ExecutionFault, NoResultFault {
		TaskOwnersDo outputDo = new TaskOwnersDo();
		outputDo.setTaskOwnersDoPK(new TaskOwnersDoPK());
		if (!ServicesUtil.isEmpty(fromDto.getEventId().trim()))
			outputDo.getTaskOwnersDoPK().setEventId(fromDto.getEventId().trim());
		if (!ServicesUtil.isEmpty(fromDto.getTaskOwner()))
			outputDo.getTaskOwnersDoPK().setTaskOwner(fromDto.getTaskOwner().trim());
		if (!ServicesUtil.isEmpty(fromDto.getTaskOwnerDisplayName()))
			outputDo.setTaskOwnerDisplayName(fromDto.getTaskOwnerDisplayName().trim());
		if (!ServicesUtil.isEmpty(fromDto.getIsProcessed()))
			outputDo.setIsProcessed(fromDto.getIsProcessed());

		return outputDo;
	}

	public String createTaskOwnerInstance(TaskOwnersDto dto) {

		//	System.err.println("[PMC][ConsumeODataFacade][createTaskOwnerInstance]initiated with " + dto);
		try {
			create(dto);
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][ConsumeODataFacade][createTaskOwnerInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	public String updateTaskOwnerInstance(TaskOwnersDto dto) {
		//	System.err.println("[PMC][ConsumeODataFacade][updateTaskOwnerInstance]initiated with " + dto);
		try {
			update(dto);
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][ConsumeODataFacade][updateTaskOwnerInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public String allOwnersInstance() {
		//	System.err.println("[PMC][TaskOwnersDao][allOwnersInstance]initiated ");
		Query query = this.getEntityManager().createQuery("select te from TaskOwnersDo te");
		List<TaskOwnersDo> processDos = (List<TaskOwnersDo>) query.getResultList();
		int i = 0;
		try {
			for (TaskOwnersDo entity : processDos) {
				//System.err.println("[PMC][TaskOwnersDao][allOwnersInstance][i]"+i+"[entity]" +entity);
				delete(exportDto(entity));
				i++;
			}
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][TaskOwnersDao][allOwnersInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	public String deleteInstance(TaskOwnersDo entity) {
		//	System.err.println("[PMC][TaskOwnersDao][deleteInstance]initiated "+entity);
		try {
			delete(exportDto(entity));
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][TaskOwnersDao][allOwnersInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	@SuppressWarnings("unchecked")
	public List<TaskOwnersDo> getOwnerInstances(String instanceId) {
		Query query = this.getEntityManager()
				.createQuery("select to from TaskOwnersDo to where to.taskOwnersDoPK.eventId =:instanceId");
		query.setParameter("instanceId", instanceId);

		List<TaskOwnersDo>  dos =	(List<TaskOwnersDo>) query.getResultList();

		//		System.err.println("[PMC][TaskOwnersDao][getOwnerInstanceCount][dos] " +dos+"[instanceId]"+instanceId);
		if(dos.size()>0){
			return  dos;
		}
		else{
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String deleteInstanceByOwner(String owner) {
		//	System.err.println("[PMC][TaskOwnersDao][deleteInstanceByOwner]initiated with "+owner);
		Query query = this.getEntityManager().createQuery("select te from TaskOwnersDo te where te.taskOwnersDoPK.taskOwner = :owner ");
		query.setParameter("owner", owner);
		List<TaskOwnersDo> processDos = (List<TaskOwnersDo>) query.getResultList();
		//	System.err.println("[PMC][TaskOwnersDao][deleteInstanceByOwner][processDos] "+processDos);
		try {
			if(!ServicesUtil.isEmpty(processDos)){
				for (TaskOwnersDo entity : processDos) {
					delete(exportDto(entity));
					//this.getEntityManager().remove(entity);

				}
				this.getEntityManager().flush();
			}
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][TaskOwnersDao][deleteInstanceByOwner][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<TaskOwnersDo> getInstanceByOwner(String owner) {
		//	System.err.println("[PMC][TaskOwnersDao][getInstanceByOwner]initiated with "+owner);
		try {
			Query query = this.getEntityManager().createQuery("select te from TaskOwnersDo te where te.taskOwnersDoPK.taskOwner = :owner ");
			query.setParameter("owner", owner);
			List<TaskOwnersDo> processDos = (List<TaskOwnersDo>) query.getResultList();
			//	System.err.println("[PMC][TaskOwnersDao][getInstanceByOwner][processDos] "+processDos);
			if(!ServicesUtil.isEmpty(processDos)){
				return processDos;
			}
		} catch (Exception e) {
			System.err.println("[PMC][TaskOwnersDao][getInstanceByOwner][error] " + e.getMessage());
		}
		return null;
	}

	public String deleteNonExistingTasks( List<String> instanceList,String processor) {
		//	System.err.println("[PMC][ConsumeODataFacade][Xpath][Xpath][deleteNonExistingTasks] method invoked with [processor]" + processor+"[instanceList]"+instanceList);
		List<TaskOwnersDo> doList = getInstanceByOwner(processor);
		//	System.err.println("[PMC][ConsumeODataFacade][Xpath][Xpath][deleteNonExistingTasks] method invoked with [doList]" + doList.size()+"[instanceListLength]"+instanceList.size());
		if(!ServicesUtil.isEmpty(doList)){
			for(TaskOwnersDo entity :doList){
				if(!instanceList.contains(entity.getTaskOwnersDoPK().getEventId())){
					if(deleteInstance(entity).equals("FAILURE")){
						return "FAILURE";
					}
				}
			}
		}
		return "SUCCESS";
	}

	public int getExistingInstanceCount(String instanceIds,String userId) {
		String queryString = "select count(te) from TaskOwnersDo te where te.taskOwnersDoPK.eventId IN ("+instanceIds+") and te.taskOwnersDoPK.taskOwner = '"+userId.toUpperCase()+"'";
		Query query = this.getEntityManager()
				.createQuery(queryString);
		Long count = (Long) query.getSingleResult();
		return count.intValue();
	}

	@SuppressWarnings("unchecked")
	public ExistingDataDto getExistingData(Object[] userList){


		ExistingDataDto responseDto = new ExistingDataDto();
		Map<String,Integer> totalCountMap = new HashMap<String, Integer>();
		Map<String,List<String>> existingInstanceMap = new HashMap<String, List<String>>();

		String userString = ServicesUtil.getStringFromList(userList);
		String queryString = "select te.EVENT_ID AS EVENT_ID,te.TASK_OWNER AS TASK_OWNER, ts.STATUS AS STATUS from TASK_OWNERS te  join TASK_EVNT ts on te.EVENT_ID = ts.EVENT_ID left join PRC_EVENTS pe on ts.PROCESS_ID = pe.PROCESS_ID  where  te.TASK_OWNER  IN ("+userString+") and pe.PRC_INST_ID IN ('TS76308026','TS14007970','TS00407862','TS91000610','TS91000879','TS91000728','TS10008126','TS12300097','TS91000199','TS91000695_WS91000198_0000000073','TS21000231','TS91000634','TS01200196','TS91000758','TS01200212','TS14008026','TS91000695','TS91000743')";

		//	String queryString = "select te.taskOwnersDoPK.eventId ,te.taskOwnersDoPK.taskOwner , ts.status from TaskOwnersDo te join TaskEventsDo. ts on te.taskOwnersDoPK.eventId = ts.taskEventsDoPK.eventId where te.taskOwnersDoPK.taskOwner IN ("+userString+")";
		Query query = this.getEntityManager()
				.createNativeQuery(queryString,"existingDataResults");
		List<Object[]> queryResponse = (List<Object[]>) query.getResultList();
		if(queryResponse.size()>0){
			for(Object[] obj : queryResponse){
				String user = (String) obj[1];

				if(existingInstanceMap.containsKey(user)){
					existingInstanceMap.get(user).add((String) obj[0]);
				}
				else{
					List<String> instanceList = new ArrayList<String>();
					instanceList.add((String) obj[0]);
					existingInstanceMap.put(user, instanceList);
				}
				if(((String) obj[2]).equals("READY") || ((String) obj[2]).equals("RESERVED")){
					if(totalCountMap.containsKey(user)){
						totalCountMap.replace(user, totalCountMap.get(user)+1);
					}
					else{
						totalCountMap.put(user, 1);
					}
				}
			}
		}
		responseDto.setExistingInstanceMap(existingInstanceMap);
		responseDto.setTotalCountMap(totalCountMap);

		System.err.println("[PMC][responseDto]"+responseDto+queryResponse.size());
		return responseDto;
	} 
}
