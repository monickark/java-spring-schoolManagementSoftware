package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class StudentExamRemark implements Serializable {
	
	// Logging
	Logger logger = Logger.getLogger(StudentExamRemark.class);
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm = "";
	private String examTypeId = "";
	private String studentAdmisNo = "";
	private String remarks = "";
	private String delFlg = "";
	private String rCreId = "";
	private String rCreTime = "";
	private String rModId = "";
	private String rModTime = "";
	
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
	
	public String getExamTypeId() {
		return examTypeId;
	}
	
	public void setExamTypeId(String examTypeId) {
		this.examTypeId = examTypeId;
	}
	
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getDelFlg() {
		return delFlg;
	}
	
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	
	public String getrCreId() {
		return rCreId;
	}
	
	public void setrCreId(String rCreId) {
		this.rCreId = rCreId;
	}
	
	public String getrCreTime() {
		return rCreTime;
	}
	
	public void setrCreTime(String rCreTime) {
		this.rCreTime = rCreTime;
	}
	
	public String getrModId() {
		return rModId;
	}
	
	public void setrModId(String rModId) {
		this.rModId = rModId;
	}
	
	public String getrModTime() {
		return rModTime;
	}
	
	public void setrModTime(String rModTime) {
		this.rModTime = rModTime;
	}

	@Override
	public String toString() {
		return "StudentExamRemark [logger=" + logger + ", dbTs=" + dbTs
				+ ", instId=" + instId + ", branchId=" + branchId + ", acTerm="
				+ acTerm + ", examTypeId=" + examTypeId + ", studentAdmisNo="
				+ studentAdmisNo + ", remarks=" + remarks + ", delFlg="
				+ delFlg + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ ", rModId=" + rModId + ", rModTime=" + rModTime + "]";
	}


	// method for create Mark Master Record for Audit
			public String toStringForAuditMarkMasterRecord() {
				StringBuffer stringBuffer = new StringBuffer()
				.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("EXAM_TYPE_ID=").append(getExamTypeId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("STUDENT_ADMIS_NO=").append(getStudentAdmisNo()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("REMARKS=").append(getRemarks()).append(AuditConstant.AUDIT_REC_SEPERATOR)	
				.append("DEL_FLG=").append(getDelFlg()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

				logger.debug("String formed for audit is :" + stringBuffer.toString());

				return stringBuffer.toString();
			}
	
}
