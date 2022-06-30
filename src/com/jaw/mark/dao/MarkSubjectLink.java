package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

//CourseClasses Pojo class
public class MarkSubjectLink implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(MarkSubjectLink.class);
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String MarkSubjectLinkId = "";
	private String acTerm = "";
	private String studentGrpId = "";
	private String examId = "";
	private String examType = "";
	private String subTestId = "";
	private String crslId = "";
	private String labBatch = "";
	private String examDate = "";
	private String minMark = "";
	private String maxMark = "";
	private String remarks = "";
	private String subPortionsDetails = "";
	private String status = "";
	private String delFlag = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
	private String startTime="";
	private String duration="";
	


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Logger getLogger() {
		return logger;
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;
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
	
	public String getMarkSubjectLinkId() {
		return MarkSubjectLinkId;
	}
	
	public void setMarkSubjectLinkId(String MarkSubjectLinkId) {
		this.MarkSubjectLinkId = MarkSubjectLinkId;
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
	
	public String getExamType() {
		return examType;
	}
	
	public void setExamType(String examType) {
		this.examType = examType;
	}
	
	public String getSubTestId() {
		return subTestId;
	}
	
	public void setSubTestId(String subTestId) {
		this.subTestId = subTestId;
	}
	
	public String getCrslId() {
		return crslId;
	}
	
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	
	public String getLabBatch() {
		return labBatch;
	}
	
	public void setLabBatch(String labBatch) {
		this.labBatch = labBatch;
	}
	
	public String getExamDate() {
		return examDate;
	}
	
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	
	public String getMinMark() {
		return minMark;
	}
	
	public void setMinMark(String minMark) {
		this.minMark = minMark;
	}
	
	public String getMaxMark() {
		return maxMark;
	}
	
	public void setMaxMark(String maxMark) {
		this.maxMark = maxMark;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getSubPortionsDetails() {
		return subPortionsDetails;
	}
	
	public void setSubPortionsDetails(String subPortionsDetails) {
		this.subPortionsDetails = subPortionsDetails;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
	
	public String getExamId() {
		return examId;
	}
	
	public void setExamId(String examId) {
		this.examId = examId;
	}
	

	
	@Override
	public String toString() {
		return "MarkSubjectLink [logger=" + logger + ", dbTs=" + dbTs
				+ ", instId=" + instId + ", branchId=" + branchId
				+ ", MarkSubjectLinkId=" + MarkSubjectLinkId + ", acTerm="
				+ acTerm + ", studentGrpId=" + studentGrpId + ", examId="
				+ examId + ", examType=" + examType + ", subTestId="
				+ subTestId + ", crslId=" + crslId + ", labBatch=" + labBatch
				+ ", examDate=" + examDate + ", minMark=" + minMark
				+ ", maxMark=" + maxMark + ", remarks=" + remarks
				+ ", subPortionsDetails=" + subPortionsDetails + ", status="
				+ status + ", delFlag=" + delFlag + ", rModId=" + rModId
				+ ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + ", startTime=" + startTime
				+ ", duration=" + duration + "]";
	}

	public String stringForDbAudit() {
		StringBuffer result = new StringBuffer();
		result.append("DB_TS=").append(getDbTs())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("BRANCH_ID=").append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("MKSL_SEQ_ID=").append(getCrslId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("AC_TERM=").append(getAcTerm())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("STUDENTGRP_ID=").append(getStudentGrpId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("EXAM_ID=").append(getExamId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("EXAM_TYPE=").append(getExamType())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("SUB_TEST_ID=").append(getSubTestId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CRSL_ID=").append(getCrslId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("EXAM_DATE=").append(getExamDate())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("MIN_MARK=").append(getMinMark())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		
		result.append("MAX_MARK=").append(getMaxMark())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("REMARKS=").append(getRemarks())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("SUB_PORTION_DTLS=").append(getSubPortionsDetails())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("STATUS=").append(getStatus())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("DEL_FLG=").append(getDelFlag())
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
