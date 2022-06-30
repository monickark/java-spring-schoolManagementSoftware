package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class CourseClassesKey implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(CourseClassesKey.class);
	
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String ccId;
	
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
	
	public String getCcId() {
		return ccId;
	}
	
	public void setCcId(String ccId) {
		this.ccId = ccId;
	}
	
	@Override
	public String toString() {
		return "CourseClassesKey [getDbTs()=" + getDbTs() + ", getInstId()=" + getInstId()
				+ ", getBranchId()=" + getBranchId() + ", getCcId()=" + getCcId() + "]";
	}
	
	public String toStringForDBKey() {
		StringBuffer stringBuffer = new StringBuffer().append("DB_TS=")
				.append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_ID=")
				.append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("CC_ID=")
				.append(getCcId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		return stringBuffer.toString();
	}
}
