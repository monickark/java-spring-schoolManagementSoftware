package com.jaw.admin.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;
import com.jaw.core.dao.AcademicCalendar;

public class SMSConfiguration implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSConfiguration.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String propertyType ;
	private String propertyName;
	private String propertyValue;
	private String propertyDesc ;
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
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getPropertyDesc() {
		return propertyDesc;
	}
	public void setPropertyDesc(String propertyDesc) {
		this.propertyDesc = propertyDesc;
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
		return "SMSConfiguration [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", propertyType=" + propertyType
				+ ", propertyName=" + propertyName + ", propertyValue="
				+ propertyValue + ", propertyDesc=" + propertyDesc
				+ ", delFlag=" + delFlag + ", rModId=" + rModId + ", rModTime="
				+ rModTime + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ "]";
	}
	
	// method for create SMS Configuration Record for Audit
		public String toStringForAuditSMSConfigurationRecord() {
			StringBuffer stringBuffer = new StringBuffer()
			
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("PROPERTY_TYPE=").append(getPropertyType()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("PROPERTY_NAME=").append(getPropertyName()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("PROPERTY_VALUE=").append(getPropertyValue()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("PROPERTY_DESC=").append(getPropertyDesc()).append(AuditConstant.AUDIT_REC_SEPERATOR)										
			.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}
		
}
