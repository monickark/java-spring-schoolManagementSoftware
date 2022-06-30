package com.jaw.admin.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class SMSConfigurationKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSConfigurationKey.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String propertyType ;
	private String propertyName;
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
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	@Override
	public String toString() {
		return "SMSConfigurationKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", propertyType=" + propertyType
				+ ", propertyName=" + propertyName + "]";
	}
	
	// method for create SMSConfigurationKey String for Audit
	public String toStringForAuditAcademicCalendarKey() {

		StringBuffer stringBuffer = new StringBuffer()
		
		.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("PROPERTY_TYPE=").append(getPropertyType()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("PROPERTY_NAME=").append(getPropertyName()).append(AuditConstant.AUDIT_REC_SEPERATOR);


		logger.debug("String formed for audit is :" + stringBuffer.toString());

		return stringBuffer.toString();
	}
}
