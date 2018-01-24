package com.incture.pmc.workbox.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.util.EnOperation;
import com.incture.pmc.util.InvalidInputFault;

/**
 * <h1>TaskOwnersDto Class Implementation</h1> No validation parameters are
 * enforced
 * 
  * @author INC00609
 * @version 1.0
 * @since 2017-21-09
 */
@XmlRootElement
public class TaskOwnersDto extends BaseDto {

	private String eventId;
	private String taskOwner;
	private Boolean isProcessed;
	private String taskOwnerDisplayName;
	
	

	public String getTaskOwnerDisplayName() {
		return taskOwnerDisplayName;
	}

	public void setTaskOwnerDisplayName(String taskOwnerDisplayName) {
		this.taskOwnerDisplayName = taskOwnerDisplayName;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public Boolean getIsProcessed() {
		return isProcessed;
	}

	public void setIsProcessed(Boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	@Override
	public String toString() {
		return "TaskOwnersDto [eventId=" + eventId + ", taskOwner=" + taskOwner + ", isProcessed=" + isProcessed + "]";
	}

	@Override
	public Boolean getValidForUsage() {
		return Boolean.TRUE;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
	}

}
