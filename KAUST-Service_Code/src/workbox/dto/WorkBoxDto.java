package com.incture.pmc.workbox.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkBoxDto {
	
	private WorkBoxAppDataDto appData;
	private TaskCustomAttributeDto taskCustomAttribute;
	
	public WorkBoxAppDataDto getAppData() {
		return appData;
	}
	public void setAppData(WorkBoxAppDataDto appData) {
		this.appData = appData;
	}
	public TaskCustomAttributeDto getTaskCustomAttribute() {
		return taskCustomAttribute;
	}
	public void setTaskCustomAttribute(TaskCustomAttributeDto taskCustomAttribute) {
		this.taskCustomAttribute = taskCustomAttribute;
	}
	@Override
	public String toString() {
		return "WorkBoxDto [appData=" + appData + ", taskCustomAttribute=" + taskCustomAttribute + "]";
	}

	
	/*Uncomment to  Get the details along with tasks
	private WorKBoxDetailDto detailDto;

	public WorKBoxDetailDto getDetailDto() {
		return detailDto;
	}
	public void setDetailDto(WorKBoxDetailDto detailDto) {
		this.detailDto = detailDto;
	}*/
}
