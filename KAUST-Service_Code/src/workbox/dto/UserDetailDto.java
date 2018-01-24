package com.incture.pmc.workbox.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDetailDto {

	private String uniqueName;
	private String displayName;
	private String department;
	private String email;
	
	public String getUniqueName() {
		return uniqueName;
	}
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "UserDetailDto [uniqueName=" + uniqueName + ", displayName=" + displayName + ", department=" + department
				+ ", email=" + email + "]";
	}
	

	
}
