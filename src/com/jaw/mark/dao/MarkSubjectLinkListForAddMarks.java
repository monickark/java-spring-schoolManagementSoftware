package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class MarkSubjectLinkListForAddMarks implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(MarkSubjectLinkListForAddMarks.class);
	
	
	private String examType = "";
	private String subTestId;
	private String crslId;
	private String labBatch;
	private String examDate;
	private String minMark;
	private String maxMark = "";
	private String remarks="";
	private String subPortionDetails="";
	private String mkslId;
	private String markGrade;
	private String status;
	private String examId;
	private String subject;
	private String subjectType;
	
	
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
	public String getSubPortionDetails() {
		return subPortionDetails;
	}
	public void setSubPortionDetails(String subPortionDetails) {
		this.subPortionDetails = subPortionDetails;
	}
	public String getMkslId() {
		return mkslId;
	}
	public void setMkslId(String mkslId) {
		this.mkslId = mkslId;
	}
	public String getMarkGrade() {
		return markGrade;
	}
	public void setMarkGrade(String markGrade) {
		this.markGrade = markGrade;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	@Override
	public String toString() {
		return "MarkSubjectLinkListForAddMarks [examType=" + examType
				+ ", subTestId=" + subTestId + ", crslId=" + crslId
				+ ", labBatch=" + labBatch + ", examDate=" + examDate
				+ ", minMark=" + minMark + ", maxMark=" + maxMark
				+ ", remarks=" + remarks + ", subPortionDetails="
				+ subPortionDetails + ", mkslId=" + mkslId + ", markGrade="
				+ markGrade + ", status=" + status + ", examId=" + examId
				+ ", subject=" + subject + ", subjectType=" + subjectType + "]";
	}
	
	
	

}
