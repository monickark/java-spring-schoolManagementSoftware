package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;
//AddHolidays Pojo class
public class AddlHolidays implements Serializable{
	
	// Logging
	 Logger logger = Logger.getLogger(AddlHolidays.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm ;
	private String ahId;
	private String studentGrpId ;
	private String holFromDate ;
	private String holToDate ;
	private String ahRemarks;
	private String delFlag = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
	private String studentGrpName;
	
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
	public String getAhId() {
		return ahId;
	}
	public void setAhId(String ahId) {
		this.ahId = ahId;
	}
	public String getStudentGrpId() {
		return studentGrpId;
	}
	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	public String getHolFromDate() {
		return holFromDate;
	}
	public void setHolFromDate(String holFromDate) {
		this.holFromDate = holFromDate;
	}
	public String getHolToDate() {
		return holToDate;
	}
	public void setHolToDate(String holToDate) {
		this.holToDate = holToDate;
	}
	public String getAhRemarks() {
		return ahRemarks;
	}
	public void setAhRemarks(String ahRemarks) {
		this.ahRemarks = ahRemarks;
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
	public String getStudentGrpName() {
		return studentGrpName;
	}
	public void setStudentGrpName(String studentGrpName) {
		this.studentGrpName = studentGrpName;
	}
	
	@Override
	public String toString() {
		return "AddlHolidays [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm + ", ahId="
				+ ahId + ", studentGrpId=" + studentGrpId + ", holFromDate="
				+ holFromDate + ", holToDate=" + holToDate + ", ahRemarks="
				+ ahRemarks + ", delFlag=" + delFlag + ", rModId=" + rModId
				+ ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + ", studentGrpName="
				+ studentGrpName + "]";
	}
		// method for create Addl Holidays Record for Audit
		public String toStringForAuditAddlHolidaysRecord() {
			StringBuffer stringBuffer = new StringBuffer()
			
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("AH_ID=").append(getAhId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("STUDENTGRP_ID=").append(getStudentGrpId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("HOL_FROM_DATE=").append(getHolFromDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("HOL_TO_DATE=").append(getHolToDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("AH_RMKS=").append(getAhRemarks()).append(AuditConstant.AUDIT_REC_SEPERATOR)							
			.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}
		
	
}
