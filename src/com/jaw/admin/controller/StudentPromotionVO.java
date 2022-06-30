package com.jaw.admin.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;



public class StudentPromotionVO implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(StudentPromotionVO.class);

	// Properties
	
	private String branchId;
	private String fromAcademicTerm;
	private String toAcademicTerm;
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getFromAcademicTerm() {
		return fromAcademicTerm;
	}
	public void setFromAcademicTerm(String fromAcademicTerm) {
		this.fromAcademicTerm = fromAcademicTerm;
	}
	public String getToAcademicTerm() {
		return toAcademicTerm;
	}
	public void setToAcademicTerm(String toAcademicTerm) {
		this.toAcademicTerm = toAcademicTerm;
	}
	@Override
	public String toString() {
		return "StudentPromotionVO [branchId=" + branchId
				+ ", fromAcademicTerm=" + fromAcademicTerm
				+ ", toAcademicTerm=" + toAcademicTerm + "]";
	}

}
