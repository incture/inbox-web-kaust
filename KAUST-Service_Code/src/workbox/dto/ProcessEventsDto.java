package com.incture.pmc.workbox.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.util.EnOperation;
import com.incture.pmc.util.InvalidInputFault;



/**
 * <h1>ProcessEventsDto Class Implementation</h1> No validation parameters are
 * enforced
 * 
  * @author INC00609
 * @version 1.0
 * @since 2017-21-09
 */
@XmlRootElement
public class ProcessEventsDto extends BaseDto {
	private String processId = UUID.randomUUID().toString().replaceAll("-", "");
	private String requestId;
	private String name;
	private String subject;
	private String status;
	private String startedBy;
	private String startedByDisplayName;
	private Date startedAt;
	private Date completedAt;
	private String processInstanceId;
	private List<TaskEventsDto> taskEvents;
	

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getName() {
		return name;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartedBy() {
		return startedBy;
	}

	public void setStartedBy(String startedBy) {
		this.startedBy = startedBy;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public Date getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}

	public List<TaskEventsDto> getTaskEvents() {
		return taskEvents;
	}

	public void setTaskEvents(List<TaskEventsDto> taskEvents) {
		this.taskEvents = taskEvents;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	

	public String getStartedByDisplayName() {
		return startedByDisplayName;
	}

	public void setStartedByDisplayName(String startedByDisplayName) {
		this.startedByDisplayName = startedByDisplayName;
	}

	@Override
	public String toString() {
		return "ProcessEventsDto [processId=" + processId + ", requestId=" + requestId + ", name=" + name + ", subject="
				+ subject + ", status=" + status + ", startedBy=" + startedBy + ", startedByDisplayName="
				+ startedByDisplayName + ", startedAt=" + startedAt + ", completedAt=" + completedAt
				+ ", processInstanceId=" + processInstanceId + ", taskEvents=" + taskEvents + "]";
	}

	@Override
	public Boolean getValidForUsage() {
		return Boolean.TRUE;
	}

	
	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		// TODO Auto-generated method stub
		
	}

}
