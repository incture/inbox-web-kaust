package com.incture.pmc.workbox.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: ProcessEventsDo
 *
 */
@Entity
@Table(name = "TASK_EVNT")
public class TaskEventsDo implements BaseDo, Serializable {

	/**
	 * 
	 */
	public TaskEventsDo() {
		super();
	}

	private static final long serialVersionUID = -7341365853980611944L;

	@EmbeddedId
	private TaskEventsDoPK taskEventsDoPK;

	@Column(name = "DESCRIPTION", length = 1000)
	private String description;

	@Column(name = "SUBJECT", length = 250)
	private String subject;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "STATUS", length = 20)
	private String status;

	@Column(name = "CUR_PROC", length = 20)
	private String currentProcessor;

	@Column(name = "PRIORITY", length = 20)
	private String priority;

	@Column(name = "CREATED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "COMPLETED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date completedAt;

	@Column(name = "COMP_DEADLINE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date completionDeadLine;

	@Column(name = "CUR_PROC_DISP", length = 100)
	private String currentProcessorDisplayName;

	@Column(name = "PROC_NAME", length = 100)
	private String processName;

	@Column(name = "STATUS_FLAG", length = 20)
	private String statusFlag;

	@Column(name = "TASK_MODE", length = 50)
	private String taskMode;

	@Column(name = "TASK_TYPE", length = 50)
	private String taskType;
	
	@Column(name = "FORWARDED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date forwardedAt;
	
	@Column(name = "FORWARDED_BY", length = 100)
	private String forwardedBy;

	public TaskEventsDoPK getTaskEventsDoPK() {
		return taskEventsDoPK;
	}

	public void setTaskEventsDoPK(TaskEventsDoPK taskEventsDoPK) {
		this.taskEventsDoPK = taskEventsDoPK;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrentProcessor() {
		return currentProcessor;
	}

	public void setCurrentProcessor(String currentProcessor) {
		this.currentProcessor = currentProcessor;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}

	public Date getCompletionDeadLine() {
		return completionDeadLine;
	}

	public void setCompletionDeadLine(Date completionDeadLine) {
		this.completionDeadLine = completionDeadLine;
	}

	public String getCurrentProcessorDisplayName() {
		return currentProcessorDisplayName;
	}

	public void setCurrentProcessorDisplayName(String currentProcessorDisplayName) {
		this.currentProcessorDisplayName = currentProcessorDisplayName;
	}

	@Override
	public Object getPrimaryKey() {
		return taskEventsDoPK;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getTaskMode() {
		return taskMode;
	}

	public void setTaskMode(String taskMode) {
		this.taskMode = taskMode;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	public Date getForwardedAt() {
		return forwardedAt;
	}

	public void setForwardedAt(Date forwardedAt) {
		this.forwardedAt = forwardedAt;
	}

	public String getForwardedBy() {
		return forwardedBy;
	}

	public void setForwardedBy(String forwardedBy) {
		this.forwardedBy = forwardedBy;
	}

	@Override
	public String toString() {
		return "TaskEventsDo [taskEventsDoPK=" + taskEventsDoPK + ", description=" + description + ", subject=" + subject + ", name=" + name + ", status=" + status + ", currentProcessor="
				+ currentProcessor + ", priority=" + priority + ", createdAt=" + createdAt + ", completedAt=" + completedAt + ", completionDeadLine=" + completionDeadLine
				+ ", currentProcessorDisplayName=" + currentProcessorDisplayName + ", processName=" + processName + ", statusFlag=" + statusFlag + ", taskMode=" + taskMode + ", taskType=" + taskType
				+ "]";
	}

}
