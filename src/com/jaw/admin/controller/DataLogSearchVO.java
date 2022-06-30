package com.jaw.admin.controller;
import java.io.Serializable;
public class DataLogSearchVO implements Serializable{
	private String pageNo = "10";
	private String instId = "";
	private String branchId ="";
	private String fromDate ="";
	private String toDate ="";	
	private String userId ="";
	private String linkId ="";
	private String tableName ="";
	private String typeOfOperation ="";	
	private String keepstat = "";	
	public String getTableName() {
		return tableName;
	}	
	public String getKeepstat() {
		return keepstat;
	}
	public void setKeepstat(String keepstat) {
		this.keepstat = keepstat;
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
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
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

}
