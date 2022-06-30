package com.jaw.fee.controller;

import com.jaw.common.constants.AuditConstant;

/**
 * @author Gritz Horizons Ltd 1
 *
 */
public class FeeMasterSearchVO {
	
	private String acTerm="";
	private String feeTerm="";
	private String feeCategory="";
	private String course="";
	private String term="";
	private String pageSize = "10";
	private String isFeeLocked="";
	
	
	public String getIsFeeLocked() {
		return isFeeLocked;
	}
	public void setIsFeeLocked(String isFeeLocked) {
		this.isFeeLocked = isFeeLocked;
	}
	public String getFeeCategory() {
		return feeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getFeeTerm() {
		return feeTerm;
	}
	public void setFeeTerm(String feeTerm) {
		this.feeTerm = feeTerm;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}                                       
	 
	
	
}
