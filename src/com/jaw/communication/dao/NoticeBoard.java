package com.jaw.communication.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;
import com.jaw.core.dao.AddlHolidays;

public class NoticeBoard implements Serializable{
	
	// Logging
	 Logger logger = Logger.getLogger(NoticeBoard.class);
	
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm ;
	private String noticeSerialNo;
	private String noticeType ;
	private String noticeName ;
	private String noticeDesc ;
	private String fromDate;
	private String toDate;
	private String informParent;
	private String isImportant;
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
	public String getNoticeSerialNo() {
		return noticeSerialNo;
	}
	public void setNoticeSerialNo(String noticeSerialNo) {
		this.noticeSerialNo = noticeSerialNo;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getNoticeName() {
		return noticeName;
	}
	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}
	public String getNoticeDesc() {
		return noticeDesc;
	}
	public void setNoticeDesc(String noticeDesc) {
		this.noticeDesc = noticeDesc;
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
	public String getInformParent() {
		return informParent;
	}
	public void setInformParent(String informParent) {
		this.informParent = informParent;
	}
	public String getIsImportant() {
		return isImportant;
	}
	public void setIsImportant(String isImportant) {
		this.isImportant = isImportant;
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
		return "NoticeBoard [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", noticeSerialNo=" + noticeSerialNo + ", noticeType="
				+ noticeType + ", noticeName=" + noticeName + ", noticeDesc="
				+ noticeDesc + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", informParent=" + informParent + ", isImportant="
				+ isImportant + ", delFlag=" + delFlag + ", rModId=" + rModId
				+ ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + "]";
	}
	// method for create Notice Board Record for Audit
		public String toStringForAuditNoticeBoardRecord() {
			StringBuffer stringBuffer = new StringBuffer()			
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("NOTICE_SER_NO=").append(getNoticeSerialNo()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("NOTICE_TYPE=").append(getNoticeType()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("NOTICE_NAME=").append(getNoticeName()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("NOTICE_DESC=").append(getNoticeDesc()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("FROM_DATE=").append(getFromDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("TO_DATE=").append(getToDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INFORM_PARENT=").append(getInformParent()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("AS_IMPORTANT=").append(getIsImportant()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}
		
}
