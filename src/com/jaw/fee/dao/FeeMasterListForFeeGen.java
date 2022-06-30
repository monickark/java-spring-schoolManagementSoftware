package com.jaw.fee.dao;

public class FeeMasterListForFeeGen {
	private String feeCategory;
	private String feeTerm;
	private String feeType;
	private int feeAmt;
	private String feePaymentTerm ;
	private String subjectType ;
	private String subjectId ;
	private String courseVariant = "";
	public String getFeeCategory() {
		return feeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}
	public String getFeeTerm() {
		return feeTerm;
	}
	public void setFeeTerm(String feeTerm) {
		this.feeTerm = feeTerm;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public int getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(int feeAmt) {
		this.feeAmt = feeAmt;
	}
	public String getFeePaymentTerm() {
		return feePaymentTerm;
	}
	public void setFeePaymentTerm(String feePaymentTerm) {
		this.feePaymentTerm = feePaymentTerm;
	}
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	
	public String getCourseVariant() {
		return courseVariant;
	}
	public void setCourseVariant(String courseVariant) {
		this.courseVariant = courseVariant;
	}
	@Override
	public String toString() {
		return "FeeMasterListForFeeGen [feeCategory=" + feeCategory
				+ ", feeTerm=" + feeTerm + ", feeType=" + feeType + ", feeAmt="
				+ feeAmt + ", feePaymentTerm=" + feePaymentTerm
				+ ", subjectType=" + subjectType + ", subjectId=" + subjectId
				+ ", courseVariant=" + courseVariant + "]";
	}
	
}
