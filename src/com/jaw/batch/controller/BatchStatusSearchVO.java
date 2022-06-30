package com.jaw.batch.controller;
import java.io.Serializable;
//UI VO class for search 
public class BatchStatusSearchVO implements Serializable{
	
private String instId = "";
private String branchId = "";
private String batchName = "";
private String upldDataType = "";
private String batchSrlNo = "";
private String batchIdFrom = "";
private String batchIdTo = "";
private String execStartDate = "" ;
private String execEndDate = "";
private String batchStatus = "";
private String pageNo = "10";
private String keepstat = "";

public String getKeepstat() {
	return keepstat;
}
public void setKeepstat(String keepstat) {
	this.keepstat = keepstat;
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
public String getBatchName() {
	return batchName;
}
public void setBatchName(String batchName) {
	this.batchName = batchName;
}
public String getUpldDataType() {
	return upldDataType;
}
public void setUpldDataType(String upldDataType) {
	this.upldDataType = upldDataType;
}
public String getBatchSrlNo() {
	return batchSrlNo;
}
public void setBatchSrlNo(String batchSrlNo) {
	this.batchSrlNo = batchSrlNo;
}
public String getBatchIdFrom() {
	return batchIdFrom;
}
@Override
public String toString() {
	return "BatchStatusSearchVO [instId=" + instId + ", branchId=" + branchId
			+ ", batchName=" + batchName + ", upldDataType=" + upldDataType
			+ ", batchSrlNo=" + batchSrlNo + ", batchIdFrom=" + batchIdFrom
			+ ", batchIdTo=" + batchIdTo + ", execStartDate=" + execStartDate
			+ ", execEndDate=" + execEndDate + ", batchStatus=" + batchStatus
			+ ", pageNo=" + pageNo + "]";
}
public void setBatchIdFrom(String batchIdFrom) {
	this.batchIdFrom = batchIdFrom;
}
public String getBatchIdTo() {
	return batchIdTo;
}
public void setBatchIdTo(String batchIdTo) {
	this.batchIdTo = batchIdTo;
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
public String getBatchStatus() {
	return batchStatus;
}
public void setBatchStatus(String batchStatus) {
	this.batchStatus = batchStatus;
}
public String getPageNo() {
	return pageNo;
}
public void setPageNo(String pageNo) {
	this.pageNo = pageNo;
}


}
