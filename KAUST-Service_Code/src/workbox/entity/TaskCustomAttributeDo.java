package com.incture.pmc.workbox.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: TaskCustomAttributeDo
 *
 */
@Entity
@Table(name = "TASK_CUST_ATTR_TBL")
public class TaskCustomAttributeDo implements BaseDo, Serializable {

	
	private static final long serialVersionUID = 1L;

	public TaskCustomAttributeDo() {
		super();
	}

	@Id
	@Column(name = "CUSTOM_ID", length = 50)
	private String customId;

	@Column(name = "PROCESS_ID", length = 70 )
	private String processInstanceId;
	
	@Column(name = "TASK_ID", length = 64 )
	private String instanceId;

	@Column(name = "STARTDEADLINE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDeadLine;
	
	@Column(name = "EXPIRYDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;
	
	@Column(name = "COMMENTS", length = 200)
	private String actionList;

	@Column(name = "ATTACHMENTS", length = 100)
	private String actions;
	
	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	@Column(name = "ISESCALATED", length = 50)
	private boolean isEscalated;
	
	@Column(name = "SAP_ORIGIN", length = 50)
	private String sapOrigin;

	@Column(name = "ATTRIBUTE")
	private String attribute;

	

	
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getSapOrigin() {
		return sapOrigin;
	}

	public void setSapOrigin(String sapOrigin) {
		this.sapOrigin = sapOrigin;
	}

	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public Date getStartDeadLine() {
		return startDeadLine;
	}

	public void setStartDeadLine(Date startDeadLine) {
		this.startDeadLine = startDeadLine;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	
	public String getActionList() {
		return actionList;
	}

	public void setActionList(String actionList) {
		this.actionList = actionList;
	}

	public boolean isEscalated() {
		return isEscalated;
	}

	public void setEscalated(boolean isEscalated) {
		this.isEscalated = isEscalated;
	}


	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	
	@Override
	public Object getPrimaryKey() {
			return customId;
	}
	@Override
	public String toString() {
		return "TaskCustomAttributeDo [customId=" + customId + ", processInstanceId=" + processInstanceId
				+ ", instanceId=" + instanceId + ", startDeadLine=" + startDeadLine + ", expiryDate=" + expiryDate
				+ ", actionList=" + actionList + ", actions=" + actions + ", isEscalated=" + isEscalated
				+ ", sapOrigin=" + sapOrigin + ", attribute=" + attribute + "]";
	}



}
