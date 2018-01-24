package com.incture.pmc.workbox.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkBoxAttachmentsDto {

	private Date attachedOn;
	private String fileName;
	private String attachId;
	private String attachBy;
	private String attachByName;
	private String mimeType;
	private int fileSize;
	private String fileDisplayName;
	

	public Date getAttachedOn() {
		return attachedOn;
	}
	public void setAttachedOn(Date attachedOn) {
		this.attachedOn = attachedOn;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAttachId() {
		return attachId;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	public String getAttachBy() {
		return attachBy;
	}
	public void setAttachBy(String attachBy) {
		this.attachBy = attachBy;
	}
	public String getAttachByName() {
		return attachByName;
	}
	public void setAttachByName(String attachByName) {
		this.attachByName = attachByName;
	}
	public String getMimeType() {
		return mimeType;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getFileDisplayName() {
		return fileDisplayName;
	}
	public void setFileDisplayName(String fileDisplayName) {
		this.fileDisplayName = fileDisplayName;
	}
	
	@Override
	public String toString() {
		return "WorkBoxAttachmentsDto [attachedOn=" + attachedOn + ", fileName=" + fileName + ", attachId=" + attachId
				+ ", attachBy=" + attachBy + ", attachByName=" + attachByName + ", mimeType=" + mimeType + ", fileSize="
				+ fileSize + ", fileDisplayName=" + fileDisplayName + "]";
	}
	
}
