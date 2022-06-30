package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class CourseMasterKey implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(CourseMasterKey.class);
	  
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String courseMasterId ;
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
	public String getCourseMasterId() {
		return courseMasterId;
	}
	public void setCourseMasterId(String courseMasterId) {
		this.courseMasterId = courseMasterId;
	}
	@Override
	public String toString() {
		return "CourseMasterKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", courseMasterId="
				+ courseMasterId + "]";
	}
	// method for create CourseMasterKey String for Audit
		public String toStringForAuditCourseMasterKey() {

			StringBuffer stringBuffer = new StringBuffer()
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("COURSEMASTER_ID=").append(getCourseMasterId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
			return stringBuffer.toString();
		}
}
