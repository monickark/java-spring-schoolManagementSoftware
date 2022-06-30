package com.jaw.common.util.dao;

import java.io.Serializable;

public class ComCodeColumnSearch implements Serializable{
	
	private String instId;
	private String branchId;
	private String tableName;
	
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}	
	public String getTableName() {
		return tableName;
	} 
	@Override
	public String toString() {
		return "ComCodeColumnSearch [instId=" + instId + ", branchId="
				+ branchId + ", tableName=" + tableName + "]";
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	
}
