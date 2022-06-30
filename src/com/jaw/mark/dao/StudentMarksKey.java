package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class StudentMarksKey implements Serializable {

	// Logging
	Logger logger = Logger.getLogger(StudentMarksKey.class);

	// Properties	
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String mkslSeqId;
	private String studentAdmisNo ;
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
	public String getMkslSeqId() {
		return mkslSeqId;
	}
	public void setMkslSeqId(String mkslSeqId) {
		this.mkslSeqId = mkslSeqId;
	}
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	@Override
	public String toString() {
		return "StudentMarksKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", mkslSeqId=" + mkslSeqId
				+ ", studentAdmisNo=" + studentAdmisNo + "]";
	}	
	// method for create StudentGroupMasterKey String for Audit
	public String toStringForAuditStudentMarksKey() {

		StringBuffer stringBuffer = new StringBuffer()		
		.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("MKSL_SEQ_ID=").append( getMkslSeqId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("STUDENT_ADMIS_NO=").append(getStudentAdmisNo()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		logger.debug("String formed for audit is :" + stringBuffer.toString());

		return stringBuffer.toString();
	}
}
