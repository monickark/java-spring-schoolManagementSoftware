package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class StudentMarks implements Serializable {

	// Logging
	Logger logger = Logger.getLogger(StudentMarks.class);

	// Properties	
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String mkslSeqId;
	private String studentAdmisNo ;		
	private String rollNumber;
	private String studentName;
	private int marksObtd;
	private String gradeObtd="";
	private String subRemarks=" ";
	private String attendance="";
	private String delFlag = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
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
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public String getGradeObtd() {
		return gradeObtd;
	}
	public void setGradeObtd(String gradeObtd) {
		this.gradeObtd = gradeObtd;
	}
	public String getSubRemarks() {
		return subRemarks;
	}
	public void setSubRemarks(String subRemarks) {
		this.subRemarks = subRemarks;
	}
	public String getAttendance() {
		return attendance;
	}
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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
	public int getMarksObtd() {
		return marksObtd;
	}
	public void setMarksObtd(int marksObtd) {
		this.marksObtd = marksObtd;
	}
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}
	@Override
	public String toString() {
		return "StudentMarks [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", mkslSeqId=" + mkslSeqId
				+ ", studentAdmisNo=" + studentAdmisNo + ", rollNumber="
				+ rollNumber + ", studentName=" + studentName + ", marksObtd="
				+ marksObtd + ", gradeObtd=" + gradeObtd + ", subRemarks="
				+ subRemarks + ", attendance=" + attendance + ", delFlag="
				+ delFlag + ", rModId=" + rModId + ", rModTime=" + rModTime
				+ ", rCreId=" + rCreId + ", rCreTime=" + rCreTime + "]";
	}
	// method for create Student Marks Record for Audit
		public String toStringForAuditStudentMarksRecord() {
			StringBuffer stringBuffer = new StringBuffer()			
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("MKSL_SEQ_ID=").append( getMkslSeqId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("STUDENT_ADMIS_NO=").append(getStudentAdmisNo()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("MARKS_OBTD=").append(getMarksObtd()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("GRADE_OBTD=").append(getGradeObtd()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("SUB_REMARKS=").append(getSubRemarks()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("ATTENDANCE=").append(getAttendance()).append(AuditConstant.AUDIT_REC_SEPERATOR)							
			.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}
	
	
	}
