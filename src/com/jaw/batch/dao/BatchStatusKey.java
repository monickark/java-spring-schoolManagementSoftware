package com.jaw.batch.dao;
import java.io.Serializable;

//Database key VO for BatchStatus
public class BatchStatusKey implements Serializable{
	private Integer dbTs;
	private String instId ;
	private String branchId ;
	private String batchSrlNo;
	private String batchPgmId = "";
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
	public String getBatchPgmId() {
		return batchPgmId;
	}
	public void setBatchPgmId(String batchPgmId) {
		this.batchPgmId = batchPgmId;
	}

}
