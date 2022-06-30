package com.jaw.communication.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;
import com.jaw.core.dao.AcademicCalendarKey;

public class NoticeBoardKey implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(NoticeBoardKey.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String noticeSerialNo;
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
	public String getNoticeSerialNo() {
		return noticeSerialNo;
	}
	public void setNoticeSerialNo(String noticeSerialNo) {
		this.noticeSerialNo = noticeSerialNo;
	}
	@Override
	public String toString() {
		return "NoticeBoardKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", noticeSerialNo="
				+ noticeSerialNo + "]";
	}
	// method for create NoticeBoardKey String for Audit
	public String toStringForAuditNoticeBoardKey() {

		StringBuffer stringBuffer = new StringBuffer()
		
		.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("NOTICE_SER_NO=").append(getNoticeSerialNo()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		

		logger.debug("String formed for audit is :" + stringBuffer.toString());

		return stringBuffer.toString();
	}
}
