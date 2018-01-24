package com.incture.pmc.workbox.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: DeviceManagementDo
 *
 */
@Entity
@Table(name = "WB_DEVICE_MGMT")
public class DeviceManagementDo implements BaseDo, Serializable {

	
	private static final long serialVersionUID = 1L;

	public DeviceManagementDo() {
		super();
	}

	@Id
	@Column(name = "UNIQUE_ID", length = 32)
	private String uniqueId = UUID.randomUUID().toString().replaceAll("-", "");

	@Column(name = "USER_ID", length = 200)
	private String userId;

	@Column(name = "DEVICE_ID", length = 100)
	private String deviceId;
	
	@Column(name = "TASK_COUNT")
	private int taskCount;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_VIEWED")
	private Date lastViewed;

	public Date getLastViewed() {
		return lastViewed;
	}

	public void setLastViewed(Date lastViewed) {
		this.lastViewed = lastViewed;
	}

	@Override
	public Object getPrimaryKey() {
		return uniqueId;
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

	public int getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Override
	public String toString() {
		return "DeviceManagementDo [uniqueId=" + uniqueId + ", userId=" + userId + ", deviceId=" + deviceId
				+ ", taskCount=" + taskCount + ", lastViewed=" + lastViewed + "]";
	}


	
}
