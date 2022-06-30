package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;


public class MarkMaster implements Serializable {

	// Logging
	Logger logger = Logger.getLogger(MarkMaster.class);
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm;
	private String mMSeqId;
	private String studentGrpId = "";
	private String examOrdrWiSG ;
	private String examId;
	private String examTest;
	private String attTermStartDate = "";
	private String attTermEndDate = "";
	private String expRptDate = "";
	private String actRptDate;
	private String status = "";
	private String studentGrpName;
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

	public String getmMSeqId() {
		return mMSeqId;
	}

	public void setmMSeqId(String mMSeqId) {
		this.mMSeqId = mMSeqId;
	}

	public String getStudentGrpId() {
		return studentGrpId;
	}

	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getExamTest() {
		return examTest;
	}

	public void setExamTest(String examTest) {
		this.examTest = examTest;
	}

	public String getAttTermStartDate() {
		return attTermStartDate;
	}

	public void setAttTermStartDate(String attTermStartDate) {
		this.attTermStartDate = attTermStartDate;
	}

	public String getAttTermEndDate() {
		return attTermEndDate;
	}

	public void setAttTermEndDate(String attTermEndDate) {
		this.attTermEndDate = attTermEndDate;
	}

	public String getExpRptDate() {
		return expRptDate;
	}

	public void setExpRptDate(String expRptDate) {
		this.expRptDate = expRptDate;
	}

	public String getActRptDate() {
		return actRptDate;
	}

	public void setActRptDate(String actRptDate) {
		this.actRptDate = actRptDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getExamOrdrWiSG() {
		return examOrdrWiSG;
	}

	public void setExamOrdrWiSG(String examOrdrWiSG) {
		this.examOrdrWiSG = examOrdrWiSG;
	}

	public String getStudentGrpName() {
		return studentGrpName;
	}

	public void setStudentGrpName(String studentGrpName) {
		this.studentGrpName = studentGrpName;
	}

	

	@Override
	public String toString() {
		return "MarkMaster [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", mMSeqId=" + mMSeqId + ", studentGrpId=" + studentGrpId
				+ ", examOrdrWiSG=" + examOrdrWiSG + ", examId=" + examId
				+ ", examTest=" + examTest + ", attTermStartDate="
				+ attTermStartDate + ", attTermEndDate=" + attTermEndDate
				+ ", expRptDate=" + expRptDate + ", actRptDate=" + actRptDate
				+ ", status=" + status + ", studentGrpName=" + studentGrpName
				+ ", delFlag=" + delFlag + ", rModId=" + rModId + ", rModTime="
				+ rModTime + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ "]";
	}

		// method for create Mark Master Record for Audit
		public String toStringForAuditMarkMasterRecord() {
			StringBuffer stringBuffer = new StringBuffer()
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("MM_SEQ_ID=").append(getmMSeqId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("STUDENTGRP_ID=").append(getStudentGrpId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("EXAM_ID=").append(getExamId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("EXAM_ORDR_WI_SG=").append(getExamOrdrWiSG()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("EXAM_TEST=").append(getExamTest()).append(AuditConstant.AUDIT_REC_SEPERATOR)			
			.append("ATT_TERM_START_DATE=").append(getAttTermStartDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("ATT_TERM_END_DATE=").append(getAttTermEndDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("EXP_RPT_DATE=").append(getExpRptDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("ACT_RPT_DATE=").append(getActRptDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("STATUS=").append(getStatus()).append(AuditConstant.AUDIT_REC_SEPERATOR)			
			.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}
}
