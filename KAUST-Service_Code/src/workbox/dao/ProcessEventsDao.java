package com.incture.pmc.workbox.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.incture.pmc.util.ExecutionFault;
import com.incture.pmc.util.InvalidInputFault;
import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.ServicesUtil;
import com.incture.pmc.workbox.dto.ProcessEventsDto;
import com.incture.pmc.workbox.entity.ProcessEventsDo;

/**
 * The <code>ProcessEventsDao</code> converts Do to Dto and vice-versa
 * <code>Data Access Objects <code>
 * 
 * @author INC00609
 * @version 1.0
 * @since 2017-21-09
 */
public class ProcessEventsDao extends BaseDao<ProcessEventsDo, ProcessEventsDto> {
	public ProcessEventsDao(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected ProcessEventsDo importDto(ProcessEventsDto fromDto)
			throws InvalidInputFault, ExecutionFault, NoResultFault {
		ProcessEventsDo outputDo = new ProcessEventsDo();
		if (!ServicesUtil.isEmpty(fromDto.getProcessId())) {
			outputDo.setProcessId(fromDto.getProcessId().trim());
		}
		if (!ServicesUtil.isEmpty(fromDto.getName())) {
			outputDo.setName(fromDto.getName().trim());
		}
		if (!ServicesUtil.isEmpty(fromDto.getStartedBy())) {
			outputDo.setStartedBy(fromDto.getStartedBy().trim());
		}
		if (!ServicesUtil.isEmpty(fromDto.getStatus())) {
			outputDo.setStatus(fromDto.getStatus().trim());
		}
		if (!ServicesUtil.isEmpty(fromDto.getSubject())) {
			outputDo.setSubject(fromDto.getSubject().trim());
		}
		if (!ServicesUtil.isEmpty(fromDto.getCompletedAt())) {
			outputDo.setCompletedAt(fromDto.getCompletedAt());
		}
		if (!ServicesUtil.isEmpty(fromDto.getStartedAt())) {
			outputDo.setStartedAt(fromDto.getStartedAt());
		}
		if (!ServicesUtil.isEmpty(fromDto.getRequestId())) {
			outputDo.setRequestId(fromDto.getRequestId().trim());
		}
		if (!ServicesUtil.isEmpty(fromDto.getStartedByDisplayName())) {
			outputDo.setStartedByDisplayName(fromDto.getStartedByDisplayName().trim());
		}
		if (!ServicesUtil.isEmpty(fromDto.getProcessInstanceId())) {
			outputDo.setProcessInstanceId(fromDto.getProcessInstanceId().trim());
		}
		
		
		return outputDo;
	}

	@Override
	public ProcessEventsDto exportDto(ProcessEventsDo fromDo) {
		ProcessEventsDto outputDto = new ProcessEventsDto();
		if (!ServicesUtil.isEmpty(fromDo.getProcessId())) {
			outputDto.setProcessId(fromDo.getProcessId().trim());
		}
		if (!ServicesUtil.isEmpty(fromDo.getName())) {
			outputDto.setName(fromDo.getName().trim());
		}
		if (!ServicesUtil.isEmpty(fromDo.getStartedBy())) {
			outputDto.setStartedBy(fromDo.getStartedBy().trim());
		}
		if (!ServicesUtil.isEmpty(fromDo.getStatus())) {
			outputDto.setStatus(fromDo.getStatus().trim());
		}
		if (!ServicesUtil.isEmpty(fromDo.getSubject())) {
			outputDto.setSubject(fromDo.getSubject().trim());
		}
		if (!ServicesUtil.isEmpty(fromDo.getCompletedAt())) {
			outputDto.setCompletedAt(fromDo.getCompletedAt());
		}
		if (!ServicesUtil.isEmpty(fromDo.getStartedAt())) {
			outputDto.setStartedAt(fromDo.getStartedAt());
		}
		if (!ServicesUtil.isEmpty(fromDo.getRequestId())) {
			outputDto.setRequestId(fromDo.getRequestId().trim());
		}
		if (!ServicesUtil.isEmpty(fromDo.getStartedByDisplayName())) {
			outputDto.setStartedByDisplayName(fromDo.getStartedByDisplayName().trim());
		}
		if (!ServicesUtil.isEmpty(fromDo.getProcessInstanceId())) {
			outputDto.setProcessInstanceId(fromDo.getProcessInstanceId().trim());
		}
		return outputDto;
	}

	public String createProcessInstance(ProcessEventsDto dto) {
	//  System.err.println("[PMC][ProcessEventsDao][createProcessInstance]initiated with " + dto);
		try {
			create(dto);
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][ProcessEventsDao][createProcessInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	public String updateProcessInstance(ProcessEventsDto dto) {
	//	System.err.println("[PMC][ProcessEventsDao][updateProcessInstance]initiated with " + dto);
		try {
			update(dto);
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][ProcessEventsDao][updateProcessInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	@SuppressWarnings("unchecked")
	public String allProcessInstance() {
	//	System.err.println("[PMC][ProcessEventsDao][allProcessInstance]initiated");
		Query query = this.getEntityManager().createQuery("select te from ProcessEventsDo te");
		List<ProcessEventsDo> processDos = (List<ProcessEventsDo>) query.getResultList();
		int i = 0;
		try {
			for (ProcessEventsDo entity : processDos) {
				System.err.println("[PMC][ProcessEventsDao][allProcessInstance][i]"+i+"[entity]" +entity);
			//	delete(exportDto(entity));
				i++;
			}
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][ProcessEventsDao][allProcessInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	@SuppressWarnings("unchecked")
	public List<String> getAllProcessName() {
	//	System.err.println("[PMC][ProcessEventsDao][getProcessNames] initiated");
		Query query = this.getEntityManager().createQuery("select DISTINCT p.name from ProcessEventsDo p");
		List<String> processNameList = (List<String>) query.getResultList();
		if (ServicesUtil.isEmpty(processNameList)) {
			return null;
		}
		return processNameList;
	}

}
