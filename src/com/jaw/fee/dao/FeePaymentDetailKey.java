package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class FeePaymentDetailKey implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(FeePaymentDetailKey.class);
	// Properties	
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm ;
	private String feePaymentTerm ;
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
	public String getFeePaymentTerm() {
		return feePaymentTerm;
	}
	public void setFeePaymentTerm(String feePaymentTerm) {
		this.feePaymentTerm = feePaymentTerm;
	}
	@Override
	public String toString() {
		return "FeePaymentDetailsKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", feePaymentTerm=" + feePaymentTerm + "]";
	}
	// method for create FeePaymentDetailKey String for Audit
	public String toStringForAuditFeePaymentDetailKey() {

		StringBuffer stringBuffer = new StringBuffer()
		.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("FEE_PMT_TERM=").append(getFeePaymentTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR);

		logger.debug("String formed for audit is :" + stringBuffer.toString());

		return stringBuffer.toString();
	}

}
