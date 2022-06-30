package com.jaw.mark.controller;

import org.apache.log4j.Logger;

public class MarkSubjectLinkSearchVO {
	// Logging
	Logger logger = Logger.getLogger(MarkSubjectLinkSearchVO.class);
	
	// Properties
	private String MarkSubjectLinkId;
	
	private String acTerm = "";
	private String studentGrpId = "";
	private String examId = "";
	
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
	
	public String getExamId() {
		return examId;
	}
	
	public void setExamId(String examId) {
		this.examId = examId;
	}

	@Override
	public String toString() {
		return "MarkSubjectLinkSearchVO [logger=" + logger
				+ ", MarkSubjectLinkId=" + MarkSubjectLinkId + ", acTerm="
				+ acTerm + ", studentGrpId=" + studentGrpId + ", examId="
				+ examId + "]";
	}
}
