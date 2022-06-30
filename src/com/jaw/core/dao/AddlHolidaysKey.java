package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class AddlHolidaysKey implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(AddlHolidaysKey.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm ;
	private String ahId;
	private String studentGrpId ;
	private String holFromDate ;
	private String holToDate ;
		
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
	@Override
	public String toString() {
		return "AddlHolidaysKey [logger=" + logger + ", dbTs=" + dbTs
				+ ", instId=" + instId + ", branchId=" + branchId + ", acTerm="
				+ acTerm + ", ahId=" + ahId + ", studentGrpId=" + studentGrpId
				+ ", holFromDate=" + holFromDate + ", holToDate=" + holToDate
				+ "]";
	}
	
	// method for create Addl Holidays Record for Audit
			public String toStringForAuditAddlHolidaysKeyRecord() {
				StringBuffer stringBuffer = new StringBuffer()
				
				.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("AH_ID=").append(getAhId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("STUDENTGRP_ID=").append(getStudentGrpId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("HOL_FROM_DATE=").append(getHolFromDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("HOL_TO_DATE=").append(getHolToDate()).append(AuditConstant.AUDIT_REC_SEPERATOR);

				logger.debug("String formed for audit is :" + stringBuffer.toString());

				return stringBuffer.toString();
			}
}
