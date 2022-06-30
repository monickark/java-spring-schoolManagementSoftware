package com.jaw.communication.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class SMSDetailsKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSDetailsKey.class);
	
	// Properties
	 private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm ;
	private String smsReqstId;
	private int smsSrlNo ;
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
	public String getSmsReqstId() {
		return smsReqstId;
	}
	public void setSmsReqstId(String smsReqstId) {
		this.smsReqstId = smsReqstId;
	}
	public int getSmsSrlNo() {
		return smsSrlNo;
	}
	public void setSmsSrlNo(int smsSrlNo) {
		this.smsSrlNo = smsSrlNo;
	}
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}
	@Override
	public String toString() {
		return "SMSDetailsKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", smsReqstId=" + smsReqstId + ", smsSrlNo=" + smsSrlNo + "]";
	}
	// method for create SMS Details key Record for Audit
		public String toStringForAuditSMSDetailsKeyRecord() {
		
			StringBuffer stringBuffer = new StringBuffer()			
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("SMS_RQST_ID=").append(getSmsReqstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("SMS_SRL_NO=").append(getSmsSrlNo()).append(AuditConstant.AUDIT_REC_SEPERATOR);
			

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}
}
