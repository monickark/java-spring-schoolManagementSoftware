package com.jaw.student.admission.dao;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;


public class StudentDetain {
	Logger logger = Logger.getLogger(StudentDetain.class);
	private String instId = "";
	private String branchId = "";
	private String academicYear = "";
	private String dbTs = "";
	private String reasonForUpdating = "";
	private String stuAdmisNo = "";
	private String delFlg = "";
	public String getStuAdmisNo() {
		return stuAdmisNo;
	}	
	public String getReasonForUpdating() {
		return reasonForUpdating;
	}
	public void setReasonForUpdating(String reasonForUpdating) {
		this.reasonForUpdating = reasonForUpdating;
	}
	@Override
	public String toString() {
		return "StudentDetain [instId=" + instId + ", branchId=" + branchId
				+ ", academicYear=" + academicYear + ", dbTs=" + dbTs
				+ ", stuAdmisNo=" + stuAdmisNo + ", stuName=" + stuName
				+ ", detainRemarks=" + detainRemarks + ", stuGrpId=" + stuGrpId
				+ ", stuGrpName=" + stuGrpName + ", rModId=" + rModId
				+ ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + "]";
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
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
	
	public String getDbTs() {
		return dbTs;
	}
	public void setDbTs(String dbTs) {
		this.dbTs = dbTs;
	}
	public void setStuAdmisNo(String stuAdmisNo) {
		this.stuAdmisNo = stuAdmisNo;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getDetainRemarks() {
		return detainRemarks;
	}	
	public void setDetainRemarks(String detainRemarks) {
		this.detainRemarks = detainRemarks;
	}
	public String getStuGrpId() {
		return stuGrpId;
	}
	public String getStuGrpName() {
		return stuGrpName;
	}
	public void setStuGrpName(String stuGrpName) {
		this.stuGrpName = stuGrpName;
	}	
	public void setStuGrpId(String stuGrpId) {
		this.stuGrpId = stuGrpId;
	}
	private String stuName = "";
	private String detainRemarks = "";
	private String stuGrpId = "";
	private String stuGrpName = "";
	
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
	public String getrCreId() {
		return rCreId;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
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
	private String rModId = "";
	private String rModTime = "";
	
	private String rCreId = "";
	private String rCreTime = "";
	
	// method for create Student Detain Record for Audit
		public String toStringForAuditStuDetainRecord() {
		//	DB_TS, INST_ID, BRANCH_ID, AC_TERM, STUDENT_ADMIS_NO, DETAIN_RMKS, DEL_FLG, R_MOD_ID, R_MOD_TIME, R_CRE_ID, R_CRE_TIME
			StringBuffer stringBuffer = new StringBuffer()
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)	
			.append("AC_TERM=").append(getAcademicYear()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("STUDENT_ADMIS_NO=").append(getStuAdmisNo()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("DETAIN_RMKS=").append(getDetainRemarks()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("DEL_FLG=").append(getDelFlg()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)	;		

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}

		// method for create Student Detain String for Audit
		public String toStringForStuDetainKey() {

			StringBuffer stringBuffer = new StringBuffer().append("INST_ID=")					
					.append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("BRANCH_ID=")
					.append(getBranchId())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("AC_TERM=")
					.append(getAcademicYear())
							.append(AuditConstant.AUDIT_REC_SEPERATOR)
							.append("STUDENT_ADMIS_NO=")
							.append(getStuAdmisNo());
			return stringBuffer.toString();
		}
	
	
}
