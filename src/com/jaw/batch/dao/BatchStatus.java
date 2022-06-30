package com.jaw.batch.dao;
import java.io.Serializable;
import java.io.InputStream;


//Database VO class for BatchStatus
public class BatchStatus implements Serializable{
	
	private Integer dbTs;
	private String instId;
	private String branchId ;
	private String batchSrlNo;
	private String batchName = "";
	@Override
	public String toString() {
		return "BatchStatus [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", batchSrlNo=" + batchSrlNo
				+ ", batchName=" + batchName + ", upldDataType=" + upldDataType
				+ ", batchStatus=" + batchStatus + ", totalNoOfrec="
				+ totalNoOfrec + ", execStartDate=" + execStartDate
				+ ", execEndDate=" + execEndDate + ", reasonForFailure="
				+ reasonForFailure + ", rModId=" + rModId + ", rModTime="
				+ rModTime + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ ", delFlag=" + delFlag + "]";
	}
	private String upldDataType = "";	
	private String batchStatus = "";
	private String totalNoOfrec = "";
	private String execStartDate = "";
	private String execEndDate = "";
	private String reasonForFailure = "";	
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
	private String delFlag = ""; 
	
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
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
	public String getBatchSrlNo() {
		return batchSrlNo;
	}
	public void setBatchSrlNo(String batchSrlNo) {
		this.batchSrlNo = batchSrlNo;
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
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getrModId() {
		return rModId;
	}
	public void setrModId(String rModId) {
		this.rModId = rModId;
	}
	public String getrModTime() {
		return rModTime;
	}
	public void setrModTime(String rModTime) {
		this.rModTime = rModTime;
	}
	public String getrCreId() {
		return rCreId;
	}
	public void setrCreId(String rCreId) {
		this.rCreId = rCreId;
	}
	public String getrCreTime() {
		return rCreTime;
	}
	public void setrCreTime(String rCreTime) {
		this.rCreTime = rCreTime;
	}
	

}
