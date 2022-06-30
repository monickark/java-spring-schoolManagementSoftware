package com.jaw.attendance.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class StudentAbsenseDetails implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(StudentAbsenseDetails.class);
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String stamId;
	private String absenteeRmks = "";
	private String studentAdmisNo;
	private String isPresent;
	private String delFlag = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
	
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
	
	public String getAbsenteeRmks() {
		return absenteeRmks;
	}
	
	public void setAbsenteeRmks(String absenteeRmks) {
		this.absenteeRmks = absenteeRmks;
	}
	
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	
	public String getIsPresent() {
		return isPresent;
	}
	
	public void setIsPresent(String isPresent) {
		this.isPresent = isPresent;
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
	
	@Override
	public String toString() {
		return "StudentAbsenseDetails [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", stamId=" + stamId
				+ ", absenteeRmks=" + absenteeRmks + ", studentAdmisNo="
				+ studentAdmisNo + ", isPresent=" + isPresent
				+ ", delFlag=" + delFlag + ", rModId=" + rModId
				+ ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + "]";
	}
	
	public String stringForDbAudit() {
		StringBuffer result = new StringBuffer();
		result.append("DB_TS=").append(getDbTs())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("BRANCH_ID=").append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("STAM_ID=").append(getStamId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("STUDENT_ADMIS_NO=").append(getStudentAdmisNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("IS_PRESENT=").append(getIsPresent())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("ABSENTEE_RMKS=").append(getAbsenteeRmks())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("delFlg=").append(getDelFlag())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_ID=").append(getrCreId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_TIME=").append(getrCreTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_ID=").append(getrModId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_TIME=").append(getrModTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		return result.toString();
	}
	
}
