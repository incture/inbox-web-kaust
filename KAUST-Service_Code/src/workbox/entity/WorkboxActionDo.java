package com.incture.pmc.workbox.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: ProcessEventsDo
 *
 */
@Entity
@Table(name = "TASK_ACTION")
public class WorkboxActionDo implements BaseDo, Serializable {

	
	private static final long serialVersionUID = 1L;

	public WorkboxActionDo() {
		super();
	}

	@Id
	@Column(name = "ACTION_ID", length = 50)
	private String actionId = UUID.randomUUID().toString().replaceAll("-", "");

	@Column(name = "ACTION_NAME", length = 100)
	private String action;

	@Column(name = "APPLICATION_TYPE", length = 200)
	private String applicationType;
	
	@Column(name = "ACTION_URL", length = 255)
	private String actionURL;
	
	@Column(name = "PROC_INSTCE_ID", length = 255)
	private String processInstId;
	
	
	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getActionURL() {
		return actionURL;
	}

	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}

	public String getProcessInstId() {
		return processInstId;
	}

	public void setProcessInstId(String processInstId) {
		this.processInstId = processInstId;
	}

	
	@Override
	public String toString() {
		return "WorkboxActionDo [actionId=" + actionId + ", action=" + action + ", applicationType=" + applicationType
				+ ", actionURL=" + actionURL + ", processInstId=" + processInstId + "]";
	}

	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
