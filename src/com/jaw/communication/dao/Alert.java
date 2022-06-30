package com.jaw.communication.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class Alert implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(Alert.class);
	
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm ;
	private String alertSerialNo;
	private String reqstCategory ;
	private String generalGrpList= "";
	private String specificGrpList= "";
	private String alertMessage ;
	private String fromDate;
	private String toDate;
	private String alertStop;
	private String important;
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

	public String getAlertSerialNo() {
		return alertSerialNo;
	}

	public void setAlertSerialNo(String alertSerialNo) {
		this.alertSerialNo = alertSerialNo;
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

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getAlertStop() {
		return alertStop;
	}

	public void setAlertStop(String alertStop) {
		this.alertStop = alertStop;
	}

	public String getImportant() {
		return important;
	}

	public void setImportant(String important) {
		this.important = important;
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
		return "Alert [dbTs=" + dbTs + ", instId=" + instId + ", branchId="
				+ branchId + ", acTerm=" + acTerm + ", alertSerialNo="
				+ alertSerialNo + ", reqstCategory=" + reqstCategory
				+ ", generalGrpList=" + generalGrpList + ", specificGrpList="
				+ specificGrpList + ", alertMessage=" + alertMessage
				+ ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", alertStop=" + alertStop + ", important=" + important
				+ ", delFlag=" + delFlag + ", rModId=" + rModId + ", rModTime="
				+ rModTime + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ "]";
	}
			
			// method for create Alert Record for Audit
			public String toStringForAuditAlertRecord() {
				StringBuffer stringBuffer = new StringBuffer()			
				.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("ALERT_SER_NO=").append(getAlertSerialNo()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("RQST_CATEGORY=").append(getReqstCategory()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("GEN_GRP_LIST=").append(getGeneralGrpList()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("SPECIFIC_GRP_LIST=").append(getSpecificGrpList()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("ALERT_MESSAGE=").append(getAlertMessage()).append(AuditConstant.AUDIT_REC_SEPERATOR)				
				.append("FROM_DATE=").append(getFromDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("TO_DATE=").append(getToDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("IMPORTANT=").append(getImportant()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("ALERT_STOP=").append(getAlertStop()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

				logger.debug("String formed for audit is :" + stringBuffer.toString());

				return stringBuffer.toString();
			}
}
