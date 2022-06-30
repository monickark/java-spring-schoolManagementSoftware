package com.jaw.fee.dao;

import com.jaw.common.constants.AuditConstant;

/**
 * @author Gritz Horizons Ltd 1
 * 
 */
public class FeeMaster {

	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String acTerm = "";
	private String feeCategory = "";
	private String feeTerm = "";
	private String feeType = "";
	private String course = "";

	private String term = "";
	private int feeAmt;
	private String delFlag;
	private String rModId;
	private String rModTime;
	private String rCreId;
	private String rCreTime;

	private String courseVariant = "";

	public String getCourseVariant() {
		return courseVariant;
	}
	public void setCourseVariant(String courseVariant) {
		this.courseVariant = courseVariant;
	}

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

	public int getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(int feeAmt) {
		this.feeAmt = feeAmt;
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
		return "FeeMaster [dbTs=" + dbTs + ", instId=" + instId + ", branchId="
				+ branchId + ", acTerm=" + acTerm + ", feeCategory="
				+ feeCategory + ", feeTerm=" + feeTerm + ", feeType=" + feeType
				+ ", course=" + course + ", term=" + term + ", feeAmt="
				+ feeAmt + ", delFlag=" + delFlag + ", rModId=" + rModId
				+ ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + ", courseVariant=" + courseVariant
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
		result.append("AC_TERM=").append(getAcTerm())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_CATGRY=").append(getFeeCategory())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_TERM=").append(getFeeCategory())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_TYPE=").append(getFeeType())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("COURSE=").append(getCourse())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("TERM=").append(getTerm())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CV_SPEC=").append(getCourseVariant())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_AMT=").append(getFeeAmt())
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
