package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

//SpecialClass Pojo class
public class SpecialClass implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(SpecialClass.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm;
	private String scItemId;
	private String scDate;
	private String studentGrpId;
	private String crslId = "";
	private String fromTime = "";
	private String toTime = "";
	private String scRmks;
	private String studentGrpName;
	private String subName = "";
	private String delFlag = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";

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

	public String getScItemId() {
		return scItemId;
	}

	public void setScItemId(String scItemId) {
		this.scItemId = scItemId;
	}

	public String getScDate() {
		return scDate;
	}

	public void setScDate(String scDate) {
		this.scDate = scDate;
	}

	public String getStudentGrpId() {
		return studentGrpId;
	}

	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}

	public String getCrslId() {
		return crslId;
	}

	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getScRmks() {
		return scRmks;
	}

	public void setScRmks(String scRmks) {
		this.scRmks = scRmks;
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

	

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getStudentGrpName() {
		return studentGrpName;
	}

	public void setStudentGrpName(String studentGrpName) {
		this.studentGrpName = studentGrpName;
	}

	

	@Override
	public String toString() {
		return "SpecialClass [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", scItemId=" + scItemId + ", scDate=" + scDate
				+ ", studentGrpId=" + studentGrpId + ", crslId=" + crslId
				+ ", fromTime=" + fromTime + ", toTime=" + toTime + ", scRmks="
				+ scRmks + ", studentGrpName=" + studentGrpName + ", subName="
				+ subName + ", delFlag=" + delFlag + ", rModId=" + rModId
				+ ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + "]";
	}

	// method for create Course Master Record for Audit
	public String toStringForAuditSpecialClassRecord() {
		StringBuffer stringBuffer = new StringBuffer()
				
		.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("SC_ITEM_ID=").append(getScItemId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("SC_DATE=").append(getScDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("STUDENTGRP_ID=").append(getStudentGrpId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("CRSL_ID=").append(getCrslId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("FROM_TIME=").append(getFromTime() ).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("TO_TIME=").append(getToTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)	
		.append("SC_RMKS=").append(getScRmks()).append(AuditConstant.AUDIT_REC_SEPERATOR)	
		.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

		logger.debug("String formed for audit is :" + stringBuffer.toString());

		return stringBuffer.toString();
	}
}
