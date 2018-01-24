package com.incture.pmc.workbox.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.incture.pmc.util.ExecutionFault;
import com.incture.pmc.util.InvalidInputFault;
import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.ServicesUtil;
import com.incture.pmc.workbox.dto.TaskEventsDto;
import com.incture.pmc.workbox.entity.TaskEventsDo;
import com.incture.pmc.workbox.entity.TaskEventsDoPK;

/**
 * The <code>TaskEventsDao</code> converts Do to Dto and vice-versa <code>Data
 * Access Objects <code>
 * 
 * @author INC00609
 * @version 1.0
 * @since 2017-21-09
 */
public class TaskEventsDao extends BaseDao<TaskEventsDo, TaskEventsDto> {

	public TaskEventsDao(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public TaskEventsDto exportDto(TaskEventsDo fromDo) {
		TaskEventsDto outputDto = new TaskEventsDto();
		TaskEventsDoPK pk = fromDo.getTaskEventsDoPK();
		outputDto.setEventId(pk.getEventId());
		outputDto.setProcessId(pk.getProcessId());
		if (!ServicesUtil.isEmpty(fromDo.getName()))
			outputDto.setName(fromDo.getName().trim());
		if (!ServicesUtil.isEmpty(fromDo.getSubject()))
			outputDto.setSubject(fromDo.getSubject().trim());
		if (!ServicesUtil.isEmpty(fromDo.getDescription()))
			outputDto.setDescription(fromDo.getDescription().trim());
		if (!ServicesUtil.isEmpty(fromDo.getStatus()))
			outputDto.setStatus(fromDo.getStatus().trim());
		if (!ServicesUtil.isEmpty(fromDo.getCurrentProcessor()))
			outputDto.setCurrentProcessor(fromDo.getCurrentProcessor().trim());
		if (!ServicesUtil.isEmpty(fromDo.getCurrentProcessorDisplayName()))
			outputDto.setCurrentProcessorDisplayName(fromDo.getCurrentProcessorDisplayName().trim());
		if (!ServicesUtil.isEmpty(fromDo.getPriority()))
			outputDto.setPriority(fromDo.getPriority().trim());
		if (!ServicesUtil.isEmpty(fromDo.getCreatedAt()))
			outputDto.setCreatedAt(fromDo.getCreatedAt());
		if (!ServicesUtil.isEmpty(fromDo.getCompletedAt()))
			outputDto.setCompletedAt(fromDo.getCompletedAt());
		if (!ServicesUtil.isEmpty(fromDo.getCompletionDeadLine()))
			outputDto.setCompletionDeadLine(fromDo.getCompletionDeadLine());
		if (!ServicesUtil.isEmpty(fromDo.getProcessName()))
			outputDto.setProcessName(fromDo.getProcessName().trim());
		if (!ServicesUtil.isEmpty(fromDo.getStatusFlag()))
			outputDto.setStatusFlag(fromDo.getStatusFlag().trim());
		if (!ServicesUtil.isEmpty(fromDo.getTaskMode()))
			outputDto.setTaskMode(fromDo.getTaskMode().trim());
		if (!ServicesUtil.isEmpty(fromDo.getTaskType()))
			outputDto.setTaskType(fromDo.getTaskType().trim());
		if (!ServicesUtil.isEmpty(fromDo.getForwardedAt()))
			outputDto.setForwardedAt(fromDo.getForwardedAt());
		if (!ServicesUtil.isEmpty(fromDo.getForwardedBy()))
			outputDto.setForwardedBy(fromDo.getForwardedBy().trim());

		return outputDto;
	}

	@Override
	protected TaskEventsDo importDto(TaskEventsDto fromDto) throws InvalidInputFault, ExecutionFault, NoResultFault {
		TaskEventsDo outputDo = new TaskEventsDo();
		TaskEventsDoPK taskEventsDoPk = new TaskEventsDoPK();
		taskEventsDoPk.setEventId(fromDto.getEventId());
		taskEventsDoPk.setProcessId(fromDto.getProcessId());
		outputDo.setTaskEventsDoPK(taskEventsDoPk);
		if (!ServicesUtil.isEmpty(fromDto.getName()))
			outputDo.setName(fromDto.getName().trim());
		if (!ServicesUtil.isEmpty(fromDto.getSubject()))
			outputDo.setSubject(fromDto.getSubject().trim());
		if (!ServicesUtil.isEmpty(fromDto.getDescription()))
			outputDo.setDescription(fromDto.getDescription().trim());
		if (!ServicesUtil.isEmpty(fromDto.getStatus()))
			outputDo.setStatus(fromDto.getStatus().trim());
		if (!ServicesUtil.isEmpty(fromDto.getCurrentProcessor()))
			outputDo.setCurrentProcessor(fromDto.getCurrentProcessor().trim());
		if (!ServicesUtil.isEmpty(fromDto.getCurrentProcessorDisplayName()))
			outputDo.setCurrentProcessorDisplayName(fromDto.getCurrentProcessorDisplayName().trim());
		if (!ServicesUtil.isEmpty(fromDto.getPriority()))
			outputDo.setPriority(fromDto.getPriority().trim());
		if (!ServicesUtil.isEmpty(fromDto.getCreatedAt()))
			outputDo.setCreatedAt(fromDto.getCreatedAt());
		if (!ServicesUtil.isEmpty(fromDto.getCompletedAt()))
			outputDo.setCompletedAt(fromDto.getCompletedAt());
		if (!ServicesUtil.isEmpty(fromDto.getCompletionDeadLine()))
			outputDo.setCompletionDeadLine(fromDto.getCompletionDeadLine());
		if (!ServicesUtil.isEmpty(fromDto.getProcessName()))
			outputDo.setProcessName(fromDto.getProcessName().trim());
		if (!ServicesUtil.isEmpty(fromDto.getStatusFlag()))
			outputDo.setStatusFlag(fromDto.getStatusFlag().trim());
		if (!ServicesUtil.isEmpty(fromDto.getTaskMode()))
			outputDo.setTaskMode(fromDto.getTaskMode().trim());
		if (!ServicesUtil.isEmpty(fromDto.getTaskType()))
			outputDo.setTaskType(fromDto.getTaskType().trim());
		if (!ServicesUtil.isEmpty(fromDto.getForwardedAt()))
			outputDo.setForwardedAt(fromDto.getForwardedAt());
		if (!ServicesUtil.isEmpty(fromDto.getForwardedBy()))
			outputDo.setForwardedBy(fromDto.getForwardedBy().trim());
		return outputDo;
	}

	public String createTaskInstance(TaskEventsDto dto) {
	//	System.err.println("[PMC][TaskEventsDao][createTaskInstance]initiated with " + dto);
		try {
			this.create(dto);
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][TaskEventsDao][createTaskInstance][error] " + e.getMessage());
		}
		return "FAILURE";

	}

	public String updateTaskInstance(TaskEventsDto dto) {
	//	System.err.println("[PMC][TaskEventsDao][updateTaskInstance]initiated with " + dto);
		try {
			update(dto);
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][TaskEventsDao][updateTaskInstance][error] " + e.getMessage());
		}
		return "FAILURE";

	}

	@SuppressWarnings("unchecked")
	public String allTaskInstance() {
	//	System.err.println("[PMC][TaskEventsDao][AllTaskInstance]initiated with");
		Query query = this.getEntityManager().createQuery("select te from TaskEventsDo te");
		List<TaskEventsDo> processDos = (List<TaskEventsDo>) query.getResultList();
		int i = 0;
		try {
			for (TaskEventsDo entity : processDos) {
				System.err.println("[PMC][TaskEventsDao][AllTaskInstance][i]"+i+"[entity]" +entity);
				//delete(exportDto(entity));
				i++;
			}
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][TaskEventsDao][AllTaskInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	@SuppressWarnings("unchecked")
	public List<TaskEventsDo> checkIfTaskInstanceExists(String instanceId) {
		Query query = this.getEntityManager()
				.createQuery("select te from TaskEventsDo te where te.taskEventsDoPK.eventId =:instanceId");
		query.setParameter("instanceId", instanceId);
		List<TaskEventsDo> taskEventsDos = (List<TaskEventsDo>) query.getResultList();
		if (taskEventsDos.size() > 0) {
			return taskEventsDos;

		} else {
			return null;
		}

	}

}
