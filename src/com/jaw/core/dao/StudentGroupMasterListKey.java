package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class StudentGroupMasterListKey implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(StudentGroupMasterListKey.class);
	 
	// Properties
	 private String instId;
	private String branchId;
	private String courseMasterId="" ;

	public String getCourseMasterId() {
		return courseMasterId;
	}

	public void setCourseMasterId(String courseMasterId) {
		this.courseMasterId = courseMasterId;
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

	@Override
	public String toString() {
		return "StudentGroupMasterListKey [instId=" + instId + ", branchId="
				+ branchId + ", courseMasterId=" + courseMasterId + "]";
	}
}
