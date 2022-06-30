package com.jaw.fee.dao;


/**
 * @author Gritz Horizons Ltd 1
 *
 */
public class FeeCategoryLinkingList {
	private String feeCategory;
	private String feeType;
	private String electiveFeeSubId;
	
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
