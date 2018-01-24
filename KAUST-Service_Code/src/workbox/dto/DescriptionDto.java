package com.incture.pmc.workbox.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DescriptionDto {

	private String userId;
	private String description;
	private String instanceId;
	
	
	
	
	public String getUserId() {
		return userId;
	}




	public void setUserId(String userId) {
		this.userId = userId;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public String getInstanceId() {
		return instanceId;
	}




	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}




	@Override
	public String toString() {
		return "DescriptionDto [userId=" + userId + ", description=" + description + ", instanceId=" + instanceId + "]";
	}
	
	
}
