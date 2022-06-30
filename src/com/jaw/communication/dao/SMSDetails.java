package com.jaw.communication.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class SMSDetails implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSDetails.class);
	
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm ;
	private String smsReqstId;
	private int smsSrlNo ;
	private String mobileNumList= "" ;
	private int mobileNumCnt=0;
	private int deliveredNumCnt=0;
	private int unDeliveredNumCnt=0;
	private String unDeliveredNumList= "";
	private String detailsStatus= "";
	private String msgGrpId= "";
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
	
	public String getMobileNumList() {
		return mobileNumList;
	}
	public void setMobileNumList(String mobileNumList) {
		this.mobileNumList = mobileNumList;
	}
	public int getMobileNumCnt() {
		return mobileNumCnt;
	}
	public void setMobileNumCnt(int mobileNumCnt) {
		this.mobileNumCnt = mobileNumCnt;
	}
	public int getDeliveredNumCnt() {
		return deliveredNumCnt;
	}
	public void setDeliveredNumCnt(int deliveredNumCnt) {
		this.deliveredNumCnt = deliveredNumCnt;
	}
	public int getUnDeliveredNumCnt() {
		return unDeliveredNumCnt;
	}
	public void setUnDeliveredNumCnt(int unDeliveredNumCnt) {
		this.unDeliveredNumCnt = unDeliveredNumCnt;
	}
	public String getUnDeliveredNumList() {
		return unDeliveredNumList;
	}
	public void setUnDeliveredNumList(String unDeliveredNumList) {
		this.unDeliveredNumList = unDeliveredNumList;
	}
	public String getDetailsStatus() {
		return detailsStatus;
	}
	public void setDetailsStatus(String detailsStatus) {
		this.detailsStatus = detailsStatus;
	}
	public String getMsgGrpId() {
		return msgGrpId;
	}
	public void setMsgGrpId(String msgGrpId) {
		this.msgGrpId = msgGrpId;
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
	public int getSmsSrlNo() {
		return smsSrlNo;
	}
	public void setSmsSrlNo(int smsSrlNo) {
		this.smsSrlNo = smsSrlNo;
	}
	@Override
	public String toString() {
		return "SMSDetails [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", smsReqstId=" + smsReqstId + ", smsSrlNo=" + smsSrlNo
				+ ", mobileNumList=" + mobileNumList + ", mobileNumCnt="
				+ mobileNumCnt + ", deliveredNumCnt=" + deliveredNumCnt
				+ ", unDeliveredNumCnt=" + unDeliveredNumCnt
				+ ", unDeliveredNumList=" + unDeliveredNumList
				+ ", detailsStatus=" + detailsStatus + ", msgGrpId=" + msgGrpId
				+ ", delFlag=" + delFlag + ", rModId=" + rModId + ", rModTime="
				+ rModTime + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ "]";
	}
	
	// method for create SMS Details Record for Audit
	public String toStringForAuditSMSDetailsRecord() {
	
		StringBuffer stringBuffer = new StringBuffer()			
		.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("SMS_RQST_ID=").append(getSmsReqstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("SMS_SRL_NO=").append(getSmsSrlNo()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("MOBILE_NUM_LIST=").append(getMobileNumList()).append(AuditConstant.AUDIT_REC_SEPERATOR)				
		.append("MOBILE_NUM_CNT=").append(getMobileNumCnt()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("DELIVERED_NUM_CNT=").append(getDeliveredNumCnt()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("UNDELIVERED_NUM_CNT=").append(getUnDeliveredNumCnt()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("UNDELIVERED_NUM_LIST=").append(getUnDeliveredNumList()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("DETAILS_STATUS=").append(getDetailsStatus()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("MSG_GRP_ID=").append(getMsgGrpId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

		logger.debug("String formed for audit is :" + stringBuffer.toString());

		return stringBuffer.toString();
	}
}
