package com.jaw.fee.dao;

import com.jaw.common.constants.AuditConstant;

/**
 * @author Gritz Horizons Ltd 1
 *
 */
public class FeeCategoryLinking {
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
	
	
	public String stringForDbAudit() {
		
		StringBuffer result = new StringBuffer();
		result.append("DB_TS=").append(getDbTs())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("BRANCH_ID=").append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_CATGRY=").append(getFeeCategory())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_TYPE=").append(getFeeType())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("ELECT_FEE_SUB_ID=").append(getIsElective())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("delFlg=").append(getDelFlag())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_ID=").append(getrCreId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_TIME=").append(getrCreTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_ID=").append(getrModId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_TIME=").append(getrModTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		return result.toString();
	}

	
	
}
