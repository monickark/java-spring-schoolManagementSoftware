package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class CourseSubLinkKey implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(CourseSubLinkKey.class);
	
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String crslId;
	private String courseMasterId;
	private String termId = "";
	
	public String getCourseMasterId() {
		return courseMasterId;
	}
	
	public void setCourseMasterId(String courseMasterId) {
		this.courseMasterId = courseMasterId;
	}
	
	public String getTermId() {
		return termId;
	}
	
	public void setTermId(String termId) {
		this.termId = termId;
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
	
	public String getCrslId() {
		return crslId;
	}
	
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	
	@Override
	public String toString() {
		return "CourseSubLinkKey [getCourseMasterId()=" + getCourseMasterId() + ", getTermId()="
				+ getTermId() + ", getDbTs()=" + getDbTs() + ", getInstId()=" + getInstId()
				+ ", getBranchId()=" + getBranchId() + ", getCrslId()=" + getCrslId() + "]";
	}
	
	public String toStringForDBKey() {
		StringBuffer stringBuffer = new StringBuffer().append("DB_TS=")
				.append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_ID=")
				.append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("CRSL_ID=")
				.append(getCrslId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		return stringBuffer.toString();
	}
}
