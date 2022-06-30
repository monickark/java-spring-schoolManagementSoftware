package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class TeacherSubjectLinkListKey implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(TeacherSubjectLinkListKey.class);

	// Properties
	private String instId;
	private String branchId;
	private String staffId ;
	private String subId= "" ;
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
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}	

}
