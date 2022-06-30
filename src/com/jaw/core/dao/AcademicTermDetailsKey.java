package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class AcademicTermDetailsKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(AcademicTermDetailsKey.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acYear;
	private String acTerm;
	private String acTermSts;
	private String promoteStsId;
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
	public String getAcYear() {
		return acYear;
	}
	public void setAcYear(String acYear) {
		this.acYear = acYear;
	}
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getAcTermSts() {
		return acTermSts;
	}
	public void setAcTermSts(String acTermSts) {
		this.acTermSts = acTermSts;
	}
	
	
	// method for create Academic Term Key String for Audit
	public String toStringForAuditAcademicCalendarKey() {

		StringBuffer stringBuffer = new StringBuffer()
		
		.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("AC_YEAR=").append(getAcYear()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		logger.debug("String formed for audit is :" + stringBuffer.toString());

		return stringBuffer.toString();
	}
	public String getPromoteStsId() {
		return promoteStsId;
	}
	public void setPromoteStsId(String promoteStsId) {
		this.promoteStsId = promoteStsId;
	}
	@Override
	public String toString() {
		return "AcademicTermDetailsKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acYear=" + acYear + ", acTerm="
				+ acTerm + ", acTermSts=" + acTermSts + ", promoteStsId="
				+ promoteStsId + "]";
	}
}
