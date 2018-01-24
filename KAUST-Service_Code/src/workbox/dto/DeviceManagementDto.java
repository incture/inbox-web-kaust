package com.incture.pmc.workbox.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.util.EnOperation;
import com.incture.pmc.util.InvalidInputFault;

@XmlRootElement
public class DeviceManagementDto extends BaseDto{

	private String uniqueId ;
	private String userId;
	private String deviceId;
	private String deviceIdOld;
	private int taskCount;
	private Date lastViewed;
	
	public String getDeviceIdOld() {
		return deviceIdOld;
	}

	public void setDeviceIdOld(String deviceIdOld) {
		this.deviceIdOld = deviceIdOld;
	}

	public int getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}

	public Date getLastViewed() {
		return lastViewed;
	}

	public void setLastViewed(Date lastViewed) {
		this.lastViewed = lastViewed;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "DeviceManagementDto [uniqueId=" + uniqueId + ", userId=" + userId + ", deviceId=" + deviceId
				+ ", deviceIdOld=" + deviceIdOld + ", taskCount=" + taskCount + ", lastViewed=" + lastViewed + "]";
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
}
