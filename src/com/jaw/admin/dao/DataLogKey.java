package com.jaw.admin.dao;
import java.io.Serializable;
public class DataLogKey implements Serializable{
	
	private String instId = "";
	private String branchId = "";
	private String fromDate ="";
	private String toDate =" ";
	private String userId ="";
	private String linkId ="";
	private String tableName ="";
	private String typeOfOperation ="";
	private String auditSrlNo ;
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
	public String getAuditSrlNo() {
		return auditSrlNo;
	}
	public void setAuditSrlNo(String auditSrlNo) {
		this.auditSrlNo = auditSrlNo;
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
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTypeOfOperation() {
		return typeOfOperation;
	}
	public void setTypeOfOperation(String typeOfOperation) {
		this.typeOfOperation = typeOfOperation;
	}
	
}
