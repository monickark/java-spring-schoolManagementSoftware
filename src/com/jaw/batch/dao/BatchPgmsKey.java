package com.jaw.batch.dao;
import java.io.Serializable;
public class BatchPgmsKey implements Serializable{
	
	private Integer dbTs;
	private String instId ;
	private String branchId;
	private String batchPgmId;
	private String batchName = "";
	private String dataType = "";	
	
	public Integer getDbTs() {
		return dbTs;
	}
	@Override
	public String toString() {
		return "BatchPgmsKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", batchPgmId=" + batchPgmId
				+ ", batchName=" + batchName + ", dataType=" + dataType + "]";
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
	public String getBatchPgmId() {
		return batchPgmId;
	}
	public void setBatchPgmId(String batchPgmId) {
		this.batchPgmId = batchPgmId;
	}
	
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	

}
