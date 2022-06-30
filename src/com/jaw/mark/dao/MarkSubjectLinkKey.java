package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class MarkSubjectLinkKey implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(MarkSubjectLinkKey.class);

	private String subType;
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String MarkSubjectLinkId = "";
	private String acTerm = "";
	private String studentGrpId = "";
	private String examId = "";
	private String examType = "";
	private String subTestId = "";
	private String crslId = "";
	private String labBatch = "";

	public String getMarkSubjectLinkId() {
		return MarkSubjectLinkId;
	}

	public void setMarkSubjectLinkId(String MarkSubjectLinkId) {
		this.MarkSubjectLinkId = MarkSubjectLinkId;
	}

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

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getAcTerm() {
		return acTerm;
	}

	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}

	public String getStudentGrpId() {
		return studentGrpId;
	}

	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}

	public String getCrslId() {
		return crslId;
	}

	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getSubTestId() {
		return subTestId;
	}

	public void setSubTestId(String subTestId) {
		this.subTestId = subTestId;
	}

	public String getLabBatch() {
		return labBatch;
	}

	public void setLabBatch(String labBatch) {
		this.labBatch = labBatch;
	}

	@Override
	public String toString() {
		return "MarkSubjectLinkKey [logger=" + logger + ", subType=" + subType
				+ ", dbTs=" + dbTs + ", instId=" + instId + ", branchId="
				+ branchId + ", MarkSubjectLinkId=" + MarkSubjectLinkId
				+ ", acTerm=" + acTerm + ", studentGrpId=" + studentGrpId
				+ ", examId=" + examId + ", examType=" + examType
				+ ", subTestId=" + subTestId + ", crslId=" + crslId
				+ ", labBatch=" + labBatch + "]";
	}

	public String toStringForDBKey() {
		StringBuffer stringBuffer = new StringBuffer().append("DB_TS=")
				.append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_ID=")
				.append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MKSL_SEQ_ID=").append(getMarkSubjectLinkId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		return stringBuffer.toString();
	}

}
