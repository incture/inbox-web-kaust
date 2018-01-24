package com.incture.pmc.workbox.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkBoxAppDataDto {
	private String taskDefinitionName;
	private String taskDefinitionId;
	private String instanceId;
	private String taskStatus;
	private String description;
	private String taskPriority;
	private String processorDetail;
	private Date createdOn;
	private String createdOnString;
	private Date completedOn;
	private Date forwardedOn;
	private String createdBy;
	private String potentialOwners;
	private String forwardingUser;
	private String forwardedUser;
	private Date startDeadLine;
	private Date completionDeadLine;
	private String processor;


	public String getTaskDefinitionName() {
		return taskDefinitionName;
	}
	public void setTaskDefinitionName(String taskDefinitionName) {
		this.taskDefinitionName = taskDefinitionName;
	}
	public String getTaskDefinitionId() {
		return taskDefinitionId;
	}
	public void setTaskDefinitionId(String taskDefinitionId) {
		this.taskDefinitionId = taskDefinitionId;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTaskPriority() {
		return taskPriority;
	}
	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}
	public String getProcessorDetail() {
		return processorDetail;
	}
	public void setProcessorDetail(String processorDetail) {
		this.processorDetail = processorDetail;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getPotentialOwners() {
		return potentialOwners;
	}
	public void setPotentialOwners(String potentialOwners) {
		this.potentialOwners = potentialOwners;
	}
	public String getForwardingUser() {
		return forwardingUser;
	}
	public void setForwardingUser(String forwardingUser) {
		this.forwardingUser = forwardingUser;
	}
	public String getForwardedUser() {
		return forwardedUser;
	}
	public void setForwardedUser(String forwardedUser) {
		this.forwardedUser = forwardedUser;
	}
	public Date getStartDeadLine() {
		return startDeadLine;
	}
	public void setStartDeadLine(Date startDeadLine) {
		this.startDeadLine = startDeadLine;
	}
	public Date getCompletionDeadLine() {
		return completionDeadLine;
	}
	public void setCompletionDeadLine(Date completionDeadLine) {
		this.completionDeadLine = completionDeadLine;
	}
	
	public Date getCompletedOn() {
		return completedOn;
	}
	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public Date getForwardedOn() {
		return forwardedOn;
	}
	public void setForwardedOn(Date forwardedOn) {
		this.forwardedOn = forwardedOn;
	}
	
	public String getCreatedOnString() {
		return createdOnString;
	}
	public void setCreatedOnString(String createdOnString) {
		this.createdOnString = createdOnString;
	}
	
	@Override
	public String toString() {
		return "WorkBoxAppDataDto [taskDefinitionName=" + taskDefinitionName + ", taskDefinitionId=" + taskDefinitionId
				+ ", instanceId=" + instanceId + ", taskStatus=" + taskStatus + ", description=" + description
				+ ", taskPriority=" + taskPriority + ", processorDetail=" + processorDetail + ", createdOn=" + createdOn
				+ ", createdOnString=" + createdOnString + ", completedOn=" + completedOn + ", forwardedOn="
				+ forwardedOn + ", createdBy=" + createdBy + ", potentialOwners=" + potentialOwners
				+ ", forwardingUser=" + forwardingUser + ", forwardedUser=" + forwardedUser + ", startDeadLine="
				+ startDeadLine + ", completionDeadLine=" + completionDeadLine + ", processor=" + processor + "]";
	}
}
