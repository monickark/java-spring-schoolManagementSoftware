package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class SubjectMasterKey implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(SubjectMasterKey.class);	
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String sub_Id;
	private String subType;
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
	public String getSub_Id() {
		return sub_Id;
	}
	public void setSub_Id(String sub_Id) {
		this.sub_Id = sub_Id;
	}
	
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	
	@Override
	public String toString() {
		return "SubjectMasterKey [logger=" + logger + ", dbTs=" + dbTs
				+ ", instId=" + instId + ", branchId=" + branchId + ", sub_Id="
				+ sub_Id + ", subType=" + subType + "]";
	}
	// method for create StudentGroupMasterKey String for Audit
	public String toStringForAuditSubjectMasterKey() {

		StringBuffer stringBuffer = new StringBuffer()
		.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("SUB_ID=").append(getSub_Id()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		

		logger.debug("String formed for audit is :" + stringBuffer.toString());

		return stringBuffer.toString();
	}
}
