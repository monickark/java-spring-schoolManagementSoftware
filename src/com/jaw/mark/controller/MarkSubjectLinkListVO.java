package com.jaw.mark.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

//CourseClasses Pojo class
public class MarkSubjectLinkListVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(MarkSubjectLinkListVO.class);

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
	private String subType = "";
	private String subId = "";
	private String subName="";
	private int rowId;
	private String requiresLab = "";
	private String markOrGrade = "";
	private String remarks="";
	private String subPortionDetails="";
	private String startTime="";
	private String duration="";
	private String status="";
	


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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
	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSubPortionDetails() {
		return subPortionDetails;
	}

	public void setSubPortionDetails(String subPortionDetails) {
		this.subPortionDetails = subPortionDetails;
	}
	public String getMarkOrGrade() {
		return markOrGrade;
	}

	public void setMarkOrGrade(String markOrGrade) {
		this.markOrGrade = markOrGrade;
	}

	public String getRequiresLab() {
		return requiresLab;
	}

	public void setRequiresLab(String requiresLab) {
		this.requiresLab = requiresLab;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
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

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	@Override
	public String toString() {
		return "MarkSubjectLinkListVO [logger=" + logger
				+ ", MarkSubjectLinkId=" + MarkSubjectLinkId + ", acTerm="
				+ acTerm + ", studentGrpId=" + studentGrpId + ", examId="
				+ examId + ", examType=" + examType + ", subTestId="
				+ subTestId + ", crslId=" + crslId + ", labBatch=" + labBatch
				+ ", examDate=" + examDate + ", minMark=" + minMark
				+ ", maxMark=" + maxMark + ", subType=" + subType + ", subId="
				+ subId + ", subName=" + subName + ", rowId=" + rowId
				+ ", requiresLab=" + requiresLab + ", markOrGrade="
				+ markOrGrade + ", remarks=" + remarks + ", subPortionDetails="
				+ subPortionDetails + ", startTime=" + startTime
				+ ", duration=" + duration + "]";
	}


	

}
