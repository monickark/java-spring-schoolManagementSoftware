package com.jaw.admin.dao;
import java.io.Serializable;
public class EventLog implements Serializable{
		
	private String auditSrlNo ;	
	private String userId ="";
	private String linkId ="";
	private String eventType ="";	
	private String rCreTime ="";
	private String remarks ="";
	
	
	public String getAuditSrlNo() {
		return auditSrlNo;
	}
	public void setAuditSrlNo(String auditSrlNo) {
		this.auditSrlNo = auditSrlNo;
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
	public String getrCreTime() {
		return rCreTime;
	}
	public void setrCreTime(String rCreTime) {
		this.rCreTime = rCreTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
