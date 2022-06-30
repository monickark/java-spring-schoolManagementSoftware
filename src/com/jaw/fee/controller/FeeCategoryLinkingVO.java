package com.jaw.fee.controller;


/**
 * @author Gritz Horizons Ltd 1
 *
 */
public class FeeCategoryLinkingVO {
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String feeCategory;
	private String feeType;
	private String isElective;
	private String delFlag;
	private String rModId;
	private String rModTime;
	private String rCreId;
	private String rCreTime;
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
	public String getIsElective() {
		return isElective;
	}
	public void setIsElective(String isElective) {
		this.isElective = isElective;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getrModId() {
		return rModId;
	}
	public void setrModId(String rModId) {
		this.rModId = rModId;
	}
	public String getrModTime() {
		return rModTime;
	}
	public void setrModTime(String rModTime) {
		this.rModTime = rModTime;
	}
	public String getrCreId() {
		return rCreId;
	}
	public void setrCreId(String rCreId) {
		this.rCreId = rCreId;
	}
	public String getrCreTime() {
		return rCreTime;
	}
	public void setrCreTime(String rCreTime) {
		this.rCreTime = rCreTime;
	}
	@Override
	public String toString() {
		return "FeeCategoryLinking [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", feeCategory=" + feeCategory
				+ ", feeType=" + feeType + ", isElective=" + isElective
				+ ", delFlag=" + delFlag + ", rModId=" + rModId + ", rModTime="
				+ rModTime + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ "]";
	}
	
	
	
}
