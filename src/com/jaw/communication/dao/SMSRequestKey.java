package com.jaw.communication.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class SMSRequestKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSRequestKey.class);
	
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String smsReqstId;
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
	public String getSmsReqstId() {
		return smsReqstId;
	}
	public void setSmsReqstId(String smsReqstId) {
		this.smsReqstId = smsReqstId;
	}
	@Override
	public String toString() {
		return "SMSRequestKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", smsReqstId=" + smsReqstId + "]";
	}
	// method for create SMS Request Key String for Audit
			public String toStringForAuditAlertKey() {

				StringBuffer stringBuffer = new StringBuffer()
				
				.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("SMS_RQST_ID=").append(getSmsReqstId()).append(AuditConstant.AUDIT_REC_SEPERATOR);

				

				logger.debug("String formed for audit is :" + stringBuffer.toString());

				return stringBuffer.toString();
			}
}
