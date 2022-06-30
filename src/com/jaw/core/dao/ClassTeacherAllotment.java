package com.jaw.core.dao;

import java.io.Serializable;

import com.jaw.common.constants.AuditConstant;

public class ClassTeacherAllotment implements Serializable {
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm="";
	private String caSeqId="";
	private String stGroup="";
	private String stBatchId="";
	private String staffId = "";
	private String ttgId = "";
	private String delFlag = "";
	private String rModId = "";
	private String rModTime;
	private String rCreId = "";
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
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getCaSeqId() {
		return caSeqId;
	}
	public void setCaSeqId(String caSeqId) {
		this.caSeqId = caSeqId;
	}
	public String getStGroup() {
		return stGroup;
	}
	public void setStGroup(String stGroup) {
		this.stGroup = stGroup;
	}
	public String getStBatchId() {
		return stBatchId;
	}
	public void setStBatchId(String stBatchId) {
		this.stBatchId = stBatchId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getTtgId() {
		return ttgId;
	}
	public void setTtgId(String ttgId) {
		this.ttgId = ttgId;
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
		return "ClassTeacherAllotment [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", caSeqId=" + caSeqId + ", stGroup=" + stGroup
				+ ", stBatchId=" + stBatchId + ", staffId=" + staffId
				+ ", ttgId=" + ttgId + ", delFlag=" + delFlag + ", rModId="
				+ rModId + ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + "]";
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
		result.append("CA_SEQ_ID=").append(getCaSeqId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("STUDENT_BATCH_ID=").append(getStBatchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("STAFF_ID=").append(getStaffId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("TTG_ID=").append(getTtgId())
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
