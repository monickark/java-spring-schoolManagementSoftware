package com.jaw.fee.controller;


/**
 * @author Gritz Horizons Ltd 1
 *
 */
public class FeeCategoryLinkingListVO {
	private String feeCategory;
	private String feeType;
	private String electiveFeeSubId;
	private String courseVariant;
	private int rowid;
	
	
	public String getCourseVariant() {
		return courseVariant;
	}
	public void setCourseVariant(String courseVariant) {
		this.courseVariant = courseVariant;
	}
	public int getRowid() {
		return rowid;
	}
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	public String getFeeCategory() {
		return feeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getElectiveFeeSubId() {
		return electiveFeeSubId;
	}
	public void setElectiveFeeSubId(String electiveFeeSubId) {
		this.electiveFeeSubId = electiveFeeSubId;
	}
	@Override
	public String toString() {
		return "FeeCategoryLinkingList [feeCategory=" + feeCategory
				+ ", feeType=" + feeType + ", electiveFeeSubId="
				+ electiveFeeSubId + "]";
	}
	
	
}
