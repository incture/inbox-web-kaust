package com.incture.pmc.workbox.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.incture.pmc.util.ExecutionFault;
import com.incture.pmc.util.InvalidInputFault;
import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.ServicesUtil;
import com.incture.pmc.workbox.dto.TaskCustomAttributeDto;
import com.incture.pmc.workbox.entity.TaskCustomAttributeDo;

/**
 * The <code>TaskOwnersDao</code> converts Do to Dto and vice-versa <code>Data
 * Access Objects <code>
 * 
 * @author INC00609
 * @version 1.0
 * @since 2017-22-09
 */
public class TaskCustomAttributeDao extends BaseDao<TaskCustomAttributeDo, TaskCustomAttributeDto> {

	public TaskCustomAttributeDao(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public TaskCustomAttributeDto exportDto(TaskCustomAttributeDo fromDo) {
		TaskCustomAttributeDto outputDto = new TaskCustomAttributeDto();
		if (!ServicesUtil.isEmpty(fromDo.getActions()))
			outputDto.setActions(fromDo.getActions().trim());
		if (!ServicesUtil.isEmpty(fromDo.getAttribute()))
			outputDto.setAttribute(fromDo.getAttribute().trim());
		if (!ServicesUtil.isEmpty(fromDo.getActionList()))
			outputDto.setActionList(fromDo.getActionList().trim());
		if (!ServicesUtil.isEmpty(fromDo.getCustomId()))
			outputDto.setCustomId(fromDo.getCustomId().trim());
		if (!ServicesUtil.isEmpty(fromDo.isEscalated()))
			outputDto.setEscalated(fromDo.isEscalated());
		if (!ServicesUtil.isEmpty(fromDo.getExpiryDate()))
			outputDto.setExpiryDate(fromDo.getExpiryDate());
		if (!ServicesUtil.isEmpty(fromDo.getInstanceId()))
			outputDto.setInstanceId(fromDo.getInstanceId().trim());
		if (!ServicesUtil.isEmpty(fromDo.getProcessInstanceId()))
			outputDto.setProcessInstanceId(fromDo.getProcessInstanceId().trim());
		if (!ServicesUtil.isEmpty(fromDo.getStartDeadLine()))
			outputDto.setStartDeadLine(fromDo.getStartDeadLine());
		if (!ServicesUtil.isEmpty(fromDo.getSapOrigin()))
			outputDto.setSapOrigin(fromDo.getSapOrigin().trim());
		return outputDto;
	}

	@Override
	protected TaskCustomAttributeDo importDto(TaskCustomAttributeDto fromDto)
			throws InvalidInputFault, ExecutionFault, NoResultFault {
		TaskCustomAttributeDo outputDo = new TaskCustomAttributeDo();
		if (!ServicesUtil.isEmpty(fromDto.getActions()))
			outputDo.setActions(fromDto.getActions().trim());
		if (!ServicesUtil.isEmpty(fromDto.getAttribute()))
			outputDo.setAttribute(fromDto.getAttribute().trim());
		if (!ServicesUtil.isEmpty(fromDto.getActionList()))
			outputDo.setActionList(fromDto.getActionList().trim());
		if (!ServicesUtil.isEmpty(fromDto.getCustomId()))
			outputDo.setCustomId(fromDto.getCustomId().trim());
		if (!ServicesUtil.isEmpty(fromDto.isEscalated()))
			outputDo.setEscalated(fromDto.isEscalated());
		if (!ServicesUtil.isEmpty(fromDto.getExpiryDate()))
			outputDo.setExpiryDate(fromDto.getExpiryDate());
		if (!ServicesUtil.isEmpty(fromDto.getInstanceId()))
			outputDo.setInstanceId(fromDto.getInstanceId().trim());
		if (!ServicesUtil.isEmpty(fromDto.getProcessInstanceId()))
			outputDo.setProcessInstanceId(fromDto.getProcessInstanceId().trim());
		if (!ServicesUtil.isEmpty(fromDto.getStartDeadLine()))
			outputDo.setStartDeadLine(fromDto.getStartDeadLine());
		if (!ServicesUtil.isEmpty(fromDto.getSapOrigin()))
			outputDo.setSapOrigin(fromDto.getSapOrigin().trim());

		return outputDo;
	}

	public String createAttrInstance(TaskCustomAttributeDto dto) {
		//	System.err.println("[PMC][TaskCustomAttributeDao][createAttrInstance]initiated with " + dto);
		try {
			create(dto);
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][TaskCustomAttributeDao][createAttrInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	public String updateAttrInstance(TaskCustomAttributeDto dto) {
		//	System.err.println("[PMC][TaskCustomAttributeDao][updateAttrInstance]initiated with " + dto);
		try {
			update(dto);
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][TaskCustomAttributeDao][updateAttrInstance][error] " + e.getMessage());
		}
		return "FAILURE";

	}

	@SuppressWarnings("unchecked")
	public String allAttrInstance() {
		//	System.err.println("[PMC][TaskCustomAttributeDao][allAttrInstance]initiated");
		Query query = this.getEntityManager().createQuery("select te from TaskCustomAttributeDo te");
		List<TaskCustomAttributeDo> processDos = (List<TaskCustomAttributeDo>) query.getResultList();
		int i = 0;
		try {
			for (TaskCustomAttributeDo entity : processDos) {
				System.err.println("[PMC][TaskCustomAttributeDao][allAttrInstance][i]"+i+"[entity]" +entity);
				//	delete(exportDto(entity));
				i++;
			}
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][TaskCustomAttributeDao][allAttrInstance][error] " + e.getMessage());
		}
		return "FAILURE";
	}

	@SuppressWarnings("unchecked")
	public TaskCustomAttributeDo getAttributeInstance(String instanceId) {
		Query query = this.getEntityManager()
				.createQuery("select te from TaskCustomAttributeDo te where te.instanceId =:instanceId");
		query.setParameter("instanceId", instanceId);
		List<TaskCustomAttributeDo> attributeDos = (List<TaskCustomAttributeDo>) query.getResultList();

		if (attributeDos.size() > 0) {
			for(TaskCustomAttributeDo entity : attributeDos){
				return entity;
			}
		} else {
			return null;
		}
		return null;
	}

	public String updateAttributeInstance(String instanceId,String actions) {
		try{
			Query query = this.getEntityManager()
					.createQuery("update TaskCustomAttributeDo te set te.actions = '"+actions+"' where te.instanceId =:instanceId");
			query.setParameter("instanceId", instanceId);

			if (query.executeUpdate()> 0) {
				return "SUCCESS";
			}
		}catch(Exception e ){
			System.err.println("[PMC][ConsumeODataFacade][updateAttributeInstance] failed because"+e.getMessage());
		}
		return "FAILURE";
	}

}
