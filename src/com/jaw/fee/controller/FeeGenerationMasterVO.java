package com.jaw.fee.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class FeeGenerationMasterVO implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(FeeGenerationMasterVO.class);
	// Properties
	private String acTerm="";
	private String courseMasterId ="";
	private String feeCategory="";
	private String termId ="";
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getCourseMasterId() {
		return courseMasterId;
	}
	public void setCourseMasterId(String courseMasterId) {
		this.courseMasterId = courseMasterId;
	}
	public String getFeeCategory() {
		return feeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	@Override
	public String toString() {
		return "FeeGenerationMasterVO [acTerm=" + acTerm + ", courseMasterId="
				+ courseMasterId + ", feeCategory=" + feeCategory + ", termId="
				+ termId + "]";
	}

}
