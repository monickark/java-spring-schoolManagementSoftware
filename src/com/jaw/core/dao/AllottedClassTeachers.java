package com.jaw.core.dao;

import java.io.Serializable;

import com.jaw.common.constants.AuditConstant;

public class AllottedClassTeachers implements Serializable {
	private String stGroupId="";
	private String stBatchId="";
	private String staffId = "";
	private String stGroupName = "";
	private String stBatchName = "";
	private String staffName = "";
	public String getStGroupId() {
		return stGroupId;
	}
	public void setStGroupId(String stGroupId) {
		this.stGroupId = stGroupId;
	}
	public String getStBatchId() {
		return stBatchId;
	}
	public void setStBatchId(String stBatchId) {
		this.stBatchId = stBatchId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStGroupName() {
		return stGroupName;
	}
	public void setStGroupName(String stGroupName) {
		this.stGroupName = stGroupName;
	}
	public String getStBatchName() {
		return stBatchName;
	}
	public void setStBatchName(String stBatchName) {
		this.stBatchName = stBatchName;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	
}
