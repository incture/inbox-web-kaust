package com.incture.pmc.workbox.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: ProcessEventsDo
 *
 */
@Entity
@SqlResultSetMappings({ @SqlResultSetMapping(name = "workBoxResults", columns = { @ColumnResult(name = "PROCESS_NAME"), @ColumnResult(name = "PROCESS_ID"), @ColumnResult(name = "TASK_ID"),
		@ColumnResult(name = "TASK_STATUS"), @ColumnResult(name = "DESCRIPTION"), @ColumnResult(name = "TASK_PRIORITY"), @ColumnResult(name = "CREATED_AT"), @ColumnResult(name = "CREATED_BY"),
		@ColumnResult(name = "POTENTIAL_OWNERS"), @ColumnResult(name = "FORWARDING_USER"), @ColumnResult(name = "FORWARDED_USER"), @ColumnResult(name = "COMPLETION_DEADLINE"),
		@ColumnResult(name = "START_DEADLINE"), @ColumnResult(name = "EXPIRYDATE"), @ColumnResult(name = "ATTRIBUTE"), @ColumnResult(name = "COMMENTS"), @ColumnResult(name = "ATTACHMENTS"),
		@ColumnResult(name = "ISESCALATED"),	@ColumnResult(name = "SAP_ORIGIN") ,@ColumnResult(name = "PRC_INST_ID")}),
	@SqlResultSetMapping(name = "existingDataResults", columns = { @ColumnResult(name = "EVENT_ID"), @ColumnResult(name = "TASK_OWNER"), @ColumnResult(name = "STATUS")})

})

@Table(name = "PRC_EVENTS")
public class ProcessEventsDo implements BaseDo, Serializable {

	private static final long serialVersionUID = 1L;

	public ProcessEventsDo() {
		super();
	}

	@Id
	@Column(name = "PROCESS_ID", length = 32)
	private String processId = UUID.randomUUID().toString().replaceAll("-", "");

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "SUBJECT", length = 100)
	private String subject;

	@Column(name = "STATUS", length = 100)
	private String status;

	@Column(name = "STARTED_BY", length = 255)
	private String startedBy;

	@Column(name = "STARTED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startedAt;

	@Column(name = "COMPLETED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date completedAt;

	@Column(name = "REQUEST_ID", length = 30)
	private String requestId;

	@Column(name = "STARTED_BY_DISP", length = 100)
	private String startedByDisplayName;

	@Column(name = "PRC_INST_ID", length = 70 )
	private String processInstanceId;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartedBy() {
		return startedBy;
	}

	public void setStartedBy(String startedBy) {
		this.startedBy = startedBy;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public Date getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}

	@Override
	public Object getPrimaryKey() {
		return processId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getStartedByDisplayName() {
		return startedByDisplayName;
	}

	public void setStartedByDisplayName(String startedByDisplayName) {
		this.startedByDisplayName = startedByDisplayName;
	}

	@Override
	public String toString() {
		return "ProcessEventsDo [processId=" + processId + ", name=" + name + ", subject=" + subject + ", status="
				+ status + ", startedBy=" + startedBy + ", startedAt=" + startedAt + ", completedAt=" + completedAt
				+ ", requestId=" + requestId + ", startedByDisplayName=" + startedByDisplayName + ", processInstanceId="
				+ processInstanceId + "]";
	}

}
