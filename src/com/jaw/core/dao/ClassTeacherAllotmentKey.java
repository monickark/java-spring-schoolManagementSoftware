package com.jaw.core.dao;

import java.io.Serializable;

import com.jaw.common.constants.AuditConstant;

public class ClassTeacherAllotmentKey implements Serializable {

	private int dbTs;
	private String instId;
	private String branchId;
	private String acTerm = "";
	private String caSeqId = "";
	private String staffId = "";

	public int getDbTs() {
		return dbTs;
	}

	public void setDbTs(int dbTs) {
		this.dbTs = dbTs;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
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

	public String getCaSeqId() {
		return caSeqId;
	}

	public void setCaSeqId(String caSeqId) {
		this.caSeqId = caSeqId;
	}

	@Override
	public String toString() {
		return "ClassTeacherAllotmentKey [instId=" + instId + ", branchId="
				+ branchId + ", acTerm=" + acTerm + ", caSeqId=" + caSeqId
				+ ", staffId=" + staffId + "]";
	}
	public String toStringForDBKey() {
		StringBuffer stringBuffer = new StringBuffer().append("DB_TS=")
				.append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_ID=")
				.append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("CA_SEQ_ID=")
				.append(getCaSeqId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		return stringBuffer.toString();
	}
}
