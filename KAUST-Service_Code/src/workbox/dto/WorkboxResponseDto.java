package com.incture.pmc.workbox.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkboxResponseDto {
	private List<WorkBoxDto> workBoxDtos;
	private ResponseMessage responseMessage;

	public List<WorkBoxDto> getWorkBoxDtos() {
		return workBoxDtos;
	}

	public void setWorkBoxDtos(List<WorkBoxDto> workBoxDtos) {
		this.workBoxDtos = workBoxDtos;
	}

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	@Override
	public String toString() {
		return "WorkboxResponseDto [workBoxDtos=" + workBoxDtos + ", responseMessage=" + responseMessage + "]";
	}
}
