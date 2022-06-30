package com.jaw.core.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class SpecialClassSearchVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(SpecialClassSearchVO.class);

	// Properties
	
	private String acTerm="";
	private String scDate="";
	private String studentGrpId="";
	private String crslId="" ;
	
	
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getScDate() {
		return scDate;
	}
	public void setScDate(String scDate) {
		this.scDate = scDate;
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
	@Override
	public String toString() {
		return "SpecialClassSearchVO [ acTerm="
				+ acTerm + ", scDate=" + scDate + ", studentGrpId="
				+ studentGrpId + ", crslId=" + crslId + "]";
	}
}
