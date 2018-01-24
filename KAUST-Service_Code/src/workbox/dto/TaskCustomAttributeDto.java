package com.incture.pmc.workbox.dto;

import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.util.EnOperation;
import com.incture.pmc.util.InvalidInputFault;

/**
 * <h1>TaskCustomAttributeDto Class Implementation</h1> No validation parameters are
 * enforced
 * 
 * @author INC00609
 * @version 1.0
 * @since 2017-22-09
 */
@XmlRootElement
public class TaskCustomAttributeDto extends BaseDto {

	private String customId= UUID.randomUUID().toString().replaceAll("-", "");
	private String processInstanceId;
	private String instanceId;
	private Date startDeadLine;
	private Date expiryDate;
	private String attribute;
	private String actionList;
	private String actions;
	private boolean isEscalated;
	private String sapOrigin;
	private String actionURL;

	public String getCustomId() {
		return customId;
	}
	public void setCustomId(String customId) {
		this.customId = customId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public boolean isEscalated() {
		return isEscalated;
	}
	public void setEscalated(boolean isEscalated) {
		this.isEscalated = isEscalated;
	}
	public String getSapOrigin() {
		return sapOrigin;
	}
	public void setSapOrigin(String sapOrigin) {
		this.sapOrigin = sapOrigin;
	}
	public String getActionList() {
		return actionList;
	}
	public void setActionList(String actionList) {
		this.actionList = actionList;
	}


	@Override
	public String toString() {
		return "TaskCustomAttributeDto [customId=" + customId + ", processInstanceId=" + processInstanceId
				+ ", instanceId=" + instanceId + ", startDeadLine=" + startDeadLine + ", expiryDate=" + expiryDate
				+ ", attribute=" + attribute + ", actionList=" + actionList + ", actions=" + actions + ", isEscalated="
				+ isEscalated + ", sapOrigin=" + sapOrigin + ", actionURL=" + actionURL + "]";
	}
	@Override
	public Boolean getValidForUsage() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		// TODO Auto-generated method stub

	}
	public String getActions() {
		return actions;
	}
	public void setActions(String actions) {
		this.actions = actions;
	}
	public String getActionURL() {
		return actionURL;
	}
	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}
}
