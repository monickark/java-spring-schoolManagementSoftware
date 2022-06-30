package com.jaw.common.util.dao;

import java.io.Serializable;

public class CommonCodeColumnBatchSearch implements Serializable{
	
	private String instId;
	private String branchId;
	private String tableName;
	private String columnName;
	
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}	
	@Override
	public String toString() {
		return "CommonCodeColumnBatchSearch [instId=" + instId + ", branchId="
				+ branchId + ", tableName=" + tableName + ", columnName="
				+ columnName + "]";
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getTableName() {
		return tableName;
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
