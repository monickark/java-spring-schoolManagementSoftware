package com.jaw.admin.dao;
import java.io.Serializable;
public class BranchMasterKey implements Serializable{
	private Integer dbTs;
	private String instId;
	private String branchId;	
	
	public String getInstId() {
		return instId;
	}
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
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
	

}
