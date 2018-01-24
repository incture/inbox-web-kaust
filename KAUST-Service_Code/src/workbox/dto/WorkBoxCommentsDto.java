package com.incture.pmc.workbox.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkBoxCommentsDto {
	
	private Date commentedOn;
	private String commentText;
	private String commentId;
	private String commentBy;
	private String commentByName;
	
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getCommentBy() {
		return commentBy;
	}
	public void setCommentBy(String commentBy) {
		this.commentBy = commentBy;
	}
	public String getCommentByName() {
		return commentByName;
	}
	public void setCommentByName(String commentByName) {
		this.commentByName = commentByName;
	}
	public Date getCommentedOn() {
		return commentedOn;
	}
	public void setCommentedOn(Date commentedOn) {
		this.commentedOn = commentedOn;
	}
}
