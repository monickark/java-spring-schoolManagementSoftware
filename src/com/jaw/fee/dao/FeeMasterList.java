package com.jaw.fee.dao;


/**
 * @author Gritz Horizons Ltd 1
 *
 */
public class FeeMasterList {
	
	private String feeCategory;
	private String feeTerm;
	private String feeType;
	private int feeAmt;
	private String courseVariant = "";
	
	
	public String getCourseVariant() {
		return courseVariant;
	}
	public void setCourseVariant(String courseVariant) {
		this.courseVariant = courseVariant;
	}
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
	@Override
	public String toString() {
		return "FeeMasterList [feeCategory=" + feeCategory + ", feeTerm="
				+ feeTerm + ", feeType=" + feeType + ", feeAmt=" + feeAmt + "]";
	}
	
	
}
