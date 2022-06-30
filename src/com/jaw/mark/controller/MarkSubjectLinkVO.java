package com.jaw.mark.controller;

import org.apache.log4j.Logger;

public class MarkSubjectLinkVO {
	// Logging
	Logger logger = Logger.getLogger(MarkSubjectLinkVO.class);
	
	private int rowId;
	private Integer dbTs;
	private String MarkSubjectLinkId = "";
	private String acTerm = "";
	private String studentGrpId = "";
	private String examType = "";
	private String subTestId = "";
	private String labBatch = "";
	private String examDate = "";
	private String minMark = "";
	private String maxMark = "";
	private String remarks = "";
	private String subPortionsDetails = "";
	private String status = "";
	private String crslId = "";
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
	public String getCrslId() {
		return crslId;
	}
	
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	
	public int getRowId() {
		return rowId;
	}
	
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	
	public Integer getDbTs() {
		return dbTs;
	}
	
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
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

	@Override
	public String toString() {
		return "MarkSubjectLinkVO [logger=" + logger + ", rowId=" + rowId
				+ ", dbTs=" + dbTs + ", MarkSubjectLinkId=" + MarkSubjectLinkId
				+ ", acTerm=" + acTerm + ", studentGrpId=" + studentGrpId
				+ ", examType=" + examType + ", subTestId=" + subTestId
				+ ", labBatch=" + labBatch + ", examDate=" + examDate
				+ ", minMark=" + minMark + ", maxMark=" + maxMark
				+ ", remarks=" + remarks + ", subPortionsDetails="
				+ subPortionsDetails + ", status=" + status + ", crslId="
				+ crslId + ", startTime=" + startTime + ", duration="
				+ duration + "]";
	}


	
}
