package com.incture.pmc.workbox.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.util.EnOperation;
import com.incture.pmc.util.InvalidInputFault;

/**
 * <h1>TaskEventsDto Class Implementation</h1> No validation parameters are
 * enforced
 * 
 * @author INC00609
 * @version 1.0
 * @since 2017-21-09
 */
@XmlRootElement
public class TaskEventsDto extends BaseDto {
	private String requestId;
	private String eventId;
	private String processId;
	private String description;
	private String subject;
	private String name;
	private String status;
	private String currentProcessor;
	private String currentProcessorDisplayName;
	private String priority;
	private Date createdAt;
	private Date completedAt;
	private Date completionDeadLine;
	private String processName;
	private String statusFlag;
	private String taskMode;
	private String taskType;
	private String forwardedBy;
	private Date forwardedAt;
	

	
	public String getCurrentProcessorDisplayName() {
		return currentProcessorDisplayName;
	}

	public void setCurrentProcessorDisplayName(String currentProcessorDisplayName) {
		this.currentProcessorDisplayName = currentProcessorDisplayName;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
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

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	@Override
	public String toString() {
		return "TaskEventsDto [requestId=" + requestId + ", eventId=" + eventId + ", processId=" + processId
				+ ", description=" + description + ", subject=" + subject + ", name=" + name + ", status=" + status
				+ ", currentProcessor=" + currentProcessor + ", currentProcessorDisplayName="
				+ currentProcessorDisplayName + ", priority=" + priority + ", createdAt=" + createdAt + ", completedAt="
				+ completedAt + ", completionDeadLine=" + completionDeadLine + ", processName=" + processName
				+ ", statusFlag=" + statusFlag + ", taskMode=" + taskMode + ", taskType=" + taskType + ", forwardedBy="
				+ forwardedBy + ", forwardedAt=" + forwardedAt + "]";
	}

	@Override
	public Boolean getValidForUsage() {
		return Boolean.TRUE;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
	}

	public String getForwardedBy() {
		return forwardedBy;
	}

	public void setForwardedBy(String forwardedBy) {
		this.forwardedBy = forwardedBy;
	}

	public Date getForwardedAt() {
		return forwardedAt;
	}

	public void setForwardedAt(Date forwardedAt) {
		this.forwardedAt = forwardedAt;
	}

	

}
