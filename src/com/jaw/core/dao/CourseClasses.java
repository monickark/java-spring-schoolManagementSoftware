package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

//CourseClasses Pojo class
public class CourseClasses implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(CourseClasses.class);
	
	// Properties
	
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String ccId = "";
	private String acTerm = "";
	private String studentGrpId = "";
	private String crslId = "";
	private String saNo = "";
	private String clsType = "";
	private String labBatch = "";
	private String staffId = "";
	private String noOfClassesPerWeek = "";
	private String noOfLabClassesPerWeek = "";
	private String duration = "";
	private String subType = "";
	private String delFlag = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
	
	
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getSaNo() {
		return saNo;
	}
	
	public void setSaNo(String saNo) {
		this.saNo = saNo;
	}
	
	public String getClsType() {
		return clsType;
	}
	
	public void setClsType(String clsType) {
		this.clsType = clsType;
	}
	
	public String getLabBatch() {
		return labBatch;
	}
	
	public void setLabBatch(String labBatch) {
		this.labBatch = labBatch;
	}
	
	public String getNoOfClassesPerWeek() {
		return noOfClassesPerWeek;
	}
	
	public void setNoOfClassesPerWeek(String noOfClassesPerWeek) {
		this.noOfClassesPerWeek = noOfClassesPerWeek;
	}
	
	public String getNoOfLabClassesPerWeek() {
		return noOfLabClassesPerWeek;
	}
	
	public void setNoOfLabClassesPerWeek(String noOfLabClassesPerWeek) {
		this.noOfLabClassesPerWeek = noOfLabClassesPerWeek;
	}
	
	
	
	public String getSubType() {
		return subType;
	}
	
	public void setSubType(String subType) {
		this.subType = subType;
	}
	
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
	
	public String getAcTerm() {
		return acTerm;
	}
	
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	
	public String getStudentGrpId() {
		return studentGrpId;
	}
	
	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	
	public String getCrslId() {
		return crslId;
	}
	
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	
	public String getStaffId() {
		return staffId;
	}
	
	public void setStaffId(String staffId) {
		this.staffId = staffId;
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
		return "CourseClasses [logger=" + logger + ", dbTs=" + dbTs
				+ ", instId=" + instId + ", branchId=" + branchId + ", ccId="
				+ ccId + ", acTerm=" + acTerm + ", studentGrpId="
				+ studentGrpId + ", crslId=" + crslId + ", saNo=" + saNo
				+ ", clsType=" + clsType + ", labBatch=" + labBatch
				+ ", staffId=" + staffId + ", noOfClassesPerWeek="
				+ noOfClassesPerWeek + ", noOfLabClassesPerWeek="
				+ noOfLabClassesPerWeek + ", duration=" + duration
				+ ", subType=" + subType + ", delFlag=" + delFlag + ", rModId="
				+ rModId + ", rModTime=" + rModTime + ", rCreId=" + rCreId
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
		result.append("CC_ID=").append(getCcId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("AC_TERM=").append(getAcTerm())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("STUDENTGRP_ID=").append(getStudentGrpId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CRSL_ID=").append(getCrslId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("SA_NO=").append(getSaNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CLS_TYPE=").append(getClsType())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("STAFF_ID=").append(getStaffId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("NO_OF_CLSES_PER_WEEK=").append(getNoOfClassesPerWeek())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("NO_OF_LAB_CLSES_PER_WEEK=").append(getNoOfLabClassesPerWeek())
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
