package com.jaw.mark.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class MarkMasterSearchVO implements Serializable {

	// Logging
	Logger logger = Logger.getLogger(MarkMasterSearchVO.class);

	// Properties	
	private String acTerm;
	private String studentGrpId;
	private String examId;
	private String studentAdmisNo = "";
	
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
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
		return "MarkMasterSearchVO [logger=" + logger + ", acTerm=" + acTerm
				+ ", studentGrpId=" + studentGrpId + ", examId=" + examId
				+ ", studentAdmisNo=" + studentAdmisNo + "]";
	}
	
	
}
