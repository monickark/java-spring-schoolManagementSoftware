package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class CourseTermLinking implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(CourseTermLinking.class);
	
	// Properties	
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String courseMasterId ;
	private String termId ;
	private int termSerialOrder;
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
	public String getCourseMasterId() {
		return courseMasterId;
	}
	public void setCourseMasterId(String courseMasterId) {
		this.courseMasterId = courseMasterId;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
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
	public int getTermSerialOrder() {
		return termSerialOrder;
	}
	public void setTermSerialOrder(int termSerialOrder) {
		this.termSerialOrder = termSerialOrder;
	}
	@Override
	public String toString() {
		return "CourseTermLinking [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", courseMasterId="
				+ courseMasterId + ", termId=" + termId + ", termSerialOrder="
				+ termSerialOrder + ", delFlag=" + delFlag + ", rModId="
				+ rModId + ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + "]";
	}
	

	// method for create Course Term Linking Record for Audit
	public String toStringForAuditCourseTermLinkingRecord() {
		StringBuffer stringBuffer = new StringBuffer()
		.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("COURSEMASTER_ID=").append(getCourseMasterId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("TERM_ID=").append(getTermId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("TRM_SRL_ORDER=").append(getTermSerialOrder()).append(AuditConstant.AUDIT_REC_SEPERATOR)			
		.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

		logger.debug("String formed for audit is :" + stringBuffer.toString());

		return stringBuffer.toString();
	}

}
