package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class StudentGroupMasterKey implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(StudentGroupMasterKey.class);
	 
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String studentGrpId ;
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
	public String getStudentGrpId() {
		return studentGrpId;
	}
	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	@Override
	public String toString() {
		return "StudentGrpMasterKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", studentGrpId=" + studentGrpId
				+ "]";
	}
	
	// method for create StudentGroupMasterKey String for Audit
			public String toStringForAuditStudentGroupMasterKey() {

				StringBuffer stringBuffer = new StringBuffer()
				.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("STUDENTGRP_ID=").append(getStudentGrpId()).append(AuditConstant.AUDIT_REC_SEPERATOR);

				logger.debug("String formed for audit is :" + stringBuffer.toString());

				return stringBuffer.toString();
			}
}
