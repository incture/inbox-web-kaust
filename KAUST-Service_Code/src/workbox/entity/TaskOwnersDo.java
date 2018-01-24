package com.incture.pmc.workbox.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: ProcessEventsDo
 *
 */
@Entity
@Table(name = "TASK_OWNERS")
public class TaskOwnersDo implements BaseDo, Serializable {

	/**
	 * 
	 */
	public TaskOwnersDo() {
		super();
	}

	private static final long serialVersionUID = 8966817427208717661L;

	@EmbeddedId
	private TaskOwnersDoPK taskOwnersDoPK;

	@Column(name = "IS_PROCESSED")
	private Boolean isProcessed;
	
	
	@Column(name = "TASK_OWNER_DISP", length = 100)
	private String taskOwnerDisplayName;

	public TaskOwnersDoPK getTaskOwnersDoPK() {
		return taskOwnersDoPK;
	}

	public void setTaskOwnersDoPK(TaskOwnersDoPK taskOwnersDoPK) {
		this.taskOwnersDoPK = taskOwnersDoPK;
	}

	public Boolean getIsProcessed() {
		return isProcessed;
	}

	public void setIsProcessed(Boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	@Override
	public String toString() {
		return "TaskOwnersDo [taskOwnersDoPK=" + taskOwnersDoPK + ", isProcessed=" + isProcessed + ", taskOwnerDisplayName=" + taskOwnerDisplayName + "]";
	}

	public String getTaskOwnerDisplayName() {
		return taskOwnerDisplayName;
	}

	public void setTaskOwnerDisplayName(String taskOwnerDisplayName) {
		this.taskOwnerDisplayName = taskOwnerDisplayName;
	}

	@Override
	public Object getPrimaryKey() {
		return taskOwnersDoPK;
	}

}
