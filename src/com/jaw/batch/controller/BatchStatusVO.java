package com.jaw.batch.controller;
import java.io.Serializable;
//UI VO class for BatchStatus
public class BatchStatusVO implements Serializable{	
	
	private Integer rowid ; 	
	private String instId = "";
	private String branchId = "";
	private String batchSrlNo = "";
	private String batchName = "";
	private String upldDataType = "";	
	private String batchStatus = "";	
	private String totalNoOfrec = "";
	private String execStartDate = "";	
	private String execEndDate = "";
	private String reasonForFailure = "";
	private String pageNo = "";
	
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public Integer getRowid() {
		return rowid;
	}
	public void setRowid(Integer rowid) {
		this.rowid = rowid;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBatchSrlNo() {
		return batchSrlNo;
	}
	public void setBatchSrlNo(String batchSrlNo) {
		this.batchSrlNo = batchSrlNo;
	}	
	public String getUpldDataType() {
		return upldDataType;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public void setUpldDataType(String upldDataType) {
		this.upldDataType = upldDataType;
	}
	public String getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	public String getTotalNoOfrec() {
		return totalNoOfrec;
	}
	public void setTotalNoOfrec(String totalNoOfrec) {
		this.totalNoOfrec = totalNoOfrec;
	}
	public String getExecStartDate() {
		return execStartDate;
	}
	public void setExecStartDate(String execStartDate) {
		this.execStartDate = execStartDate;
	}
	public String getExecEndDate() {
		return execEndDate;
	}
	public void setExecEndDate(String execEndDate) {
		this.execEndDate = execEndDate;
	}
	public String getReasonForFailure() {
		return reasonForFailure;
	}
	public void setReasonForFailure(String reasonForFailure) {
		this.reasonForFailure = reasonForFailure;
	}
		
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}					

}
