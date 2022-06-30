package com.jaw.core.controller;

import org.apache.log4j.Logger;

public class CourseClassesSearchVO {
	// Logging
	Logger logger = Logger.getLogger(CourseClassesSearchVO.class);
	private String ccId;
	private String acTerm = "";
	private String studentGrpId = "";
	private String termId = "";
	
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
	
	public String getTermId() {
		return termId;
	}
	
	public void setTermId(String termId) {
		this.termId = termId;
	}
	
	@Override
	public String toString() {
		return "CourseClassesSearchVO [ getCcId()=" + getCcId()
				+ ", getAcTerm()=" + getAcTerm() + ", getStudentGrpId()=" + getStudentGrpId()
				+ ", getTermId()=" + getTermId() + "]";
	}
	
}
