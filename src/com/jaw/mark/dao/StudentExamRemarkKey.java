package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class StudentExamRemarkKey implements Serializable {
	
	// Logging
	Logger logger = Logger.getLogger(StudentExamRemarkKey.class);
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm = "";
	private String examId = "";
	private String studentAdmisNo = "";
	
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
	
	public String getAcTerm() {
		return acTerm;
	}
	
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	
	public String getExamId() {
		return examId;
	}
	
	public void setExamId(String examId) {
		this.examId = examId;
	}
	
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	
	@Override
	public String toString() {
		return "StudentExamRemarkKey [logger=" + logger + ", dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm + ", examTypeId=" + examId
				+ ", studentAdmisNo=" + studentAdmisNo + "]";
	}
	
	// method for create MarkMasterKey String for Audit
		public String toStringForAuditMarkMasterKey() {

			StringBuffer stringBuffer = new StringBuffer()
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("EXAM_TYPE_ID=").append(getExamId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("STUDENT_ADMIS_NO=").append(getStudentAdmisNo()).append(AuditConstant.AUDIT_REC_SEPERATOR);

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}
	
}
