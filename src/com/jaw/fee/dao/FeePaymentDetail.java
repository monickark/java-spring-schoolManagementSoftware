package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;
import com.jaw.core.dao.CourseTermLinking;

public class FeePaymentDetail implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(FeePaymentDetail.class);
	// Properties	
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm ;
	private String feePaymentTerm ;
	private String dueDate ;
	private String feeCategory ;
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
	public String getFeePaymentTerm() {
		return feePaymentTerm;
	}
	public void setFeePaymentTerm(String feePaymentTerm) {
		this.feePaymentTerm = feePaymentTerm;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getFeeCategory() {
		return feeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
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
		return "FeePaymentDetails [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", feePaymentTerm=" + feePaymentTerm + ", dueDate=" + dueDate
				+ ", feeCategory=" + feeCategory + ", delFlag=" + delFlag
				+ ", rModId=" + rModId + ", rModTime=" + rModTime + ", rCreId="
				+ rCreId + ", rCreTime=" + rCreTime + "]";
	}
	// method for create Fee Payment detail Record for Audit
		public String toStringForAuditFeePaymentDetailRecord() {
			StringBuffer stringBuffer = new StringBuffer()
			
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("FEE_PMT_TERM=").append(getFeePaymentTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("DUE_DATE=").append(getDueDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)	
			.append("FEE_CATGRY=").append(getFeeCategory()).append(AuditConstant.AUDIT_REC_SEPERATOR)	
			.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}
}
