package com.jaw.admin.dao;
import java.io.Serializable;
public class EventLogKey implements Serializable{
	
	private String instId = "";
	private String branchId ="";
	private String fromDate ="";
	private String toDate ="";
	private String userId ="";
	private String linkId ="";
	private String eventType ="";
	private String auditFlg = "";
	
	public String getAuditFlg() {
		return auditFlg;
	}
	public void setAuditFlg(String auditFlg) {
		this.auditFlg = auditFlg;
	}
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}	
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
}
