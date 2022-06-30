package com.jaw.communication.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class SMSRequest implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSRequest.class);
	
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm ;
	private String smsReqstId;
	private String smsMessage ;
	private String reqstCategory ;
	private String generalGrpList= "";
	private String specificGrpList= "";
	private String specificMembrList= "";
	private String reqstStatus= "";
	private String reqstType ;
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
	public String getSmsMessage() {
		return smsMessage;
	}
	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
	}
	public String getReqstCategory() {
		return reqstCategory;
	}
	public void setReqstCategory(String reqstCategory) {
		this.reqstCategory = reqstCategory;
	}
	public String getGeneralGrpList() {
		return generalGrpList;
	}
	public void setGeneralGrpList(String generalGrpList) {
		this.generalGrpList = generalGrpList;
	}
	public String getSpecificGrpList() {
		return specificGrpList;
	}
	public void setSpecificGrpList(String specificGrpList) {
		this.specificGrpList = specificGrpList;
	}
	public String getSpecificMembrList() {
		return specificMembrList;
	}
	public void setSpecificMembrList(String specificMembrList) {
		this.specificMembrList = specificMembrList;
	}
	public String getReqstStatus() {
		return reqstStatus;
	}
	public void setReqstStatus(String reqstStatus) {
		this.reqstStatus = reqstStatus;
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
	public String getReqstType() {
		return reqstType;
	}
	public void setReqstType(String reqstType) {
		this.reqstType = reqstType;
	}
	
	@Override
	public String toString() {
		return "SMSRequest [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", smsReqstId=" + smsReqstId + ", smsMessage=" + smsMessage
				+ ", reqstCategory=" + reqstCategory + ", generalGrpList="
				+ generalGrpList + ", specificGrpList=" + specificGrpList
				+ ", specificMembrList=" + specificMembrList + ", reqstStatus="
				+ reqstStatus + ", reqstType=" + reqstType + ", delFlag="
				+ delFlag + ", rModId=" + rModId + ", rModTime=" + rModTime
				+ ", rCreId=" + rCreId + ", rCreTime=" + rCreTime + "]";
	}
				// method for create SMS request Record for Audit
				public String toStringForAuditSMSRequestRecord() {
					
					StringBuffer stringBuffer = new StringBuffer()			
					.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("SMS_RQST_ID=").append(getSmsReqstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("SMS_MSG=").append(getSmsMessage()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("RQST_CATEGORY=").append(getReqstCategory()).append(AuditConstant.AUDIT_REC_SEPERATOR)				
					.append("GEN_GRP_LIST=").append(getGeneralGrpList()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("SPECIFIC_GRP_LIST=").append(getSpecificGrpList()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("SPECIFIC_MEMBER_LIST=").append(getSpecificMembrList()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("RQST_STATUS=").append(getReqstStatus()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("RQST_TYPE=").append(getReqstType()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

					logger.debug("String formed for audit is :" + stringBuffer.toString());

					return stringBuffer.toString();
				}
}
