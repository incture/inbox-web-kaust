package com.incture.pmc.workbox.dto;

public class WorkboxRequestDto {
	private String userId;
	private String scode;
	private String taskType;
	private int maxCount;
	private int skipCount;
	private String instanceId;
	private String sapOrigin;
	private String attachmentId;
	private String comments;
	private String decisionKey;
	private String forwardTo;
	private String text;
	private String itemNo;
	private String decisionText;
	
	
	public WorkboxRequestDto(){
		super();
	}

	public WorkboxRequestDto(String userId, String taskType,int maxCount,int skipCount,String scode,String instanceId,String sapOrigin) {
		super();
		this.userId = userId;
		this.taskType = taskType;
		this.maxCount = maxCount;
		this.skipCount = skipCount;
		this.scode = scode;
		this.instanceId = instanceId;
		this.sapOrigin = sapOrigin;
	}


	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getSapOrigin() {
		return sapOrigin;
	}

	public void setSapOrigin(String sapOrigin) {
		this.sapOrigin = sapOrigin;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public int getSkipCount() {
		return skipCount;
	}

	public void setSkipCount(int skipCount) {
		this.skipCount = skipCount;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	
	public String getDecisionKey() {
		return decisionKey;
	}

	public void setDecisionKey(String decisionKey) {
		this.decisionKey = decisionKey;
	}
	
	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public String getForwardTo() {
		return forwardTo;
	}

	public void setForwardTo(String forwardTo) {
		this.forwardTo = forwardTo;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}


	@Override
	public String toString() {
		return "WorkboxRequestDto [userId=" + userId + ", scode=" + scode + ", taskType=" + taskType + ", maxCount="
				+ maxCount + ", skipCount=" + skipCount + ", instanceId=" + instanceId + ", sapOrigin=" + sapOrigin
				+ ", attachmentId=" + attachmentId + ", comments=" + comments + ", decisionKey=" + decisionKey
				+ ", forwardTo=" + forwardTo + ", text=" + text + ", itemNo=" + itemNo + ", decisionText="
				+ decisionText + "]";
	}

	public String getDecisionText() {
		return decisionText;
	}

	public void setDecisionText(String decisionText) {
		this.decisionText = decisionText;
	}

	

}
