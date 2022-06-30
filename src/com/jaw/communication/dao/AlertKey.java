package com.jaw.communication.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class AlertKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(AlertKey.class);
	
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String alertSerialNo;
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
	public String getAlertSerialNo() {
		return alertSerialNo;
	}
	public void setAlertSerialNo(String alertSerialNo) {
		this.alertSerialNo = alertSerialNo;
	}
	@Override
	public String toString() {
		return "AlertKey [dbTs=" + dbTs + ", instId=" + instId + ", branchId="
				+ branchId + ", alertSerialNo=" + alertSerialNo + "]";
	}
	// method for create AlertKey String for Audit
		public String toStringForAuditAlertKey() {

			StringBuffer stringBuffer = new StringBuffer()
			
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("ALERT_SER_NO=").append(getAlertSerialNo()).append(AuditConstant.AUDIT_REC_SEPERATOR);
			

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}
}
