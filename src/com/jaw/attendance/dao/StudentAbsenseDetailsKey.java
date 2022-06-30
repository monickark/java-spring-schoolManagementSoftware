package com.jaw.attendance.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class StudentAbsenseDetailsKey implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(StudentAbsenseDetailsKey.class);
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String stamId;
	private String studentAdmisNo;
	
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
	
	public String getStamId() {
		return stamId;
	}
	
	public void setStamId(String stamId) {
		this.stamId = stamId;
	}
	
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	
	@Override
	public String toString() {
		return "StudentAbsenseDetailsKey [logger=" + logger + ", dbTs=" + dbTs + ", instId="
				+ instId + ", branchId=" + branchId + ", stamId=" + stamId
				+ ", studentAdmisNo=" + studentAdmisNo + "]";
	}
	
	public String toStringForDBKey() {
		StringBuffer stringBuffer = new StringBuffer().append("DB_TS=")
				.append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_ID=")
				.append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("STAM_ID=")
				.append(getStamId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		return stringBuffer.toString();
	}
}
