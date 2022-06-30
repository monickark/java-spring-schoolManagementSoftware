package com.jaw.fee.dao;

import org.apache.log4j.Logger;

public class FeeCategoryLinkingKey {
	static Logger logger=Logger.getLogger(FeeCategoryLinkingKey.class);
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String feeCategory;
	private String feeType;
	
	
	public Integer getDbTs() {
		return dbTs;
	}


	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}


	public String getInstId() {
		return instId;
	}


	public void setInstId(String instId) {
		this.instId = instId;
	}


	public String getBranchId() {
		return branchId;
	}


	public void setBranchId(String branchId) {
		this.branchId = branchId;
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


	public  String getStringForAudit()
	{
		StringBuffer  stringBuffer=new StringBuffer();
		stringBuffer.append("INST_ID=").append(getInstId()).append("^")
		.append("BRANCH_ID=").append(getBranchId()).append("^")
		.append("FEE_CATGRY=").append(getFeeCategory()).append("^")
		.append("FEE_TYPE=").append(getFeeType()).append("^");
		logger.debug("String formed for audit is :"+stringBuffer.toString());
		return stringBuffer.toString();
	}


	@Override
	public String toString() {
		return "FeeCategoryLinkingKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", feeCategory=" + feeCategory
				+ ", feeType=" + feeType + "]";
	}

}
