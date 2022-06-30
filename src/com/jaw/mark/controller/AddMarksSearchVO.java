package com.jaw.mark.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class AddMarksSearchVO implements Serializable {

	// Logging
	Logger logger = Logger.getLogger(AddMarksSearchVO.class);

	// Properties	
	private String acTerm;
	private String studentGrpId = "";
	private String examId;
	private String crslId;
	
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
	public String getCrslId() {
		return crslId;
	}
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	@Override
	public String toString() {
		return "AddMarksSearchVO [acTerm=" + acTerm + ", studentGrpId="
				+ studentGrpId + ", examId=" + examId + ", crslId=" + crslId
				+ "]";
	}

}
