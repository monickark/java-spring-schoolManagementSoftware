package com.jaw.batch.controller;
import java.io.Serializable;

public class ExportTemplateVO implements Serializable{
	
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String dataType = "";
	private String batchName = "";		
	public String getBatchName() {
		return batchName;
	}	
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	
}
