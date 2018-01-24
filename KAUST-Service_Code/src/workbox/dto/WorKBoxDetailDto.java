package com.incture.pmc.workbox.dto;

import java.util.ArrayList;
import java.util.List;

public class WorKBoxDetailDto {

	private String description;
	private String masterDataCollection;
	private String headerCollection;
	private String headerInformationDetail;
	private String moreDetailInformation;
	private String itemAttachmentCollection;
	private String itemCollectionHeader;
	private String additionalTabHeaderNote;
	private String informationTabContentTop;
	private List<String> itemCollectionData = new ArrayList<String>();
	private String actionURL;
	private String actionList;
	private Integer commentCount;
	private Integer attachmentCount;
	private ResponseMessage message ;
	private List<WorkBoxCommentsDto> commentCollection;
	private List<WorkBoxAttachmentsDto> attachmentCollection;

	public List<WorkBoxCommentsDto> getCommentCollection() {
		return commentCollection;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public Integer getAttachmentCount() {
		return attachmentCount;
	}
	public void setAttachmentCount(Integer attachmentCount) {
		this.attachmentCount = attachmentCount;
	}
	public void setCommentCollection(List<WorkBoxCommentsDto> commentCollection) {
		this.commentCollection = commentCollection;
	}
	public String getMasterDataCollection() {
		return masterDataCollection;
	}
	public void setMasterDataCollection(String masterDataCollection) {
		this.masterDataCollection = masterDataCollection;
	}
	public String getHeaderInformationDetail() {
		return headerInformationDetail;
	}
	public void setHeaderInformationDetail(String headerInformationDetail) {
		this.headerInformationDetail = headerInformationDetail;
	}
	public String getMoreDetailInformation() {
		return moreDetailInformation;
	}
	public void setMoreDetailInformation(String moreDetailInformation) {
		this.moreDetailInformation = moreDetailInformation;
	}
	public String getItemAttachmentCollection() {
		return itemAttachmentCollection;
	}
	public void setItemAttachmentCollection(String itemAttachmentCollection) {
		this.itemAttachmentCollection = itemAttachmentCollection;
	}
	public String getItemCollectionHeader() {
		return itemCollectionHeader;
	}
	public void setItemCollectionHeader(String itemCollectionHeader) {
		this.itemCollectionHeader = itemCollectionHeader;
	}
	public String getAdditionalTabHeaderNote() {
		return additionalTabHeaderNote;
	}
	public void setAdditionalTabHeaderNote(String additionalTabHeaderNote) {
		this.additionalTabHeaderNote = additionalTabHeaderNote;
	}
	public void setItemCollectionData(List<String> itemCollectionData) {
		this.itemCollectionData = itemCollectionData;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHeaderCollection() {
		return headerCollection;
	}
	public void setHeaderCollection(String headerCollection) {
		this.headerCollection = headerCollection;
	}
	public List<String> getItemCollectionData() {
		return itemCollectionData;
	}
	public String getInformationTabContentTop() {
		return informationTabContentTop;
	}
	public void setInformationTabContentTop(String informationTabContentTop) {
		this.informationTabContentTop = informationTabContentTop;
	}
	public String getActionURL() {
		return actionURL;
	}
	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}
	public String getActionList() {
		return actionList;
	}
	public void setActionList(String actionList) {
		this.actionList = actionList;
	}
	public ResponseMessage getMessage() {
		return message;
	}
	public void setMessage(ResponseMessage message) {
		this.message = message;
	}
	public List<WorkBoxAttachmentsDto> getAttachmentCollection() {
		return attachmentCollection;
	}
	public void setAttachmentCollection(List<WorkBoxAttachmentsDto> attachmentCollection) {
		this.attachmentCollection = attachmentCollection;
	}

	@Override
	public String toString() {
		return "WorKBoxDetailDto [description=" + description + ", masterDataCollection=" + masterDataCollection
				+ ", headerCollection=" + headerCollection + ", headerInformationDetail=" + headerInformationDetail
				+ ", moreDetailInformation=" + moreDetailInformation + ", itemAttachmentCollection="
				+ itemAttachmentCollection + ", itemCollectionHeader=" + itemCollectionHeader
				+ ", additionalTabHeaderNote=" + additionalTabHeaderNote + ", informationTabContentTop="
				+ informationTabContentTop + ", itemCollectionData=" + itemCollectionData + ", actionURL=" + actionURL
				+ ", actionList=" + actionList + ", commentCount=" + commentCount + ", attachmentCount="
				+ attachmentCount + ", message=" + message + ", commentCollection=" + commentCollection
				+ ", attachmentCollection=" + attachmentCollection + "]";
	}

}
