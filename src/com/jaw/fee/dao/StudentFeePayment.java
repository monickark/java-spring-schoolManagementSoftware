package com.jaw.fee.dao;

import com.jaw.common.constants.AuditConstant;

/**
 * @author Gritz Horizons Ltd 1
 * 
 */
public class StudentFeePayment {

	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String studFeeDDId = "";
	private String feePmtSrlNo = "";
	private int feeDueAmtBeforePmt ;
	private int monthlyFeeDueAmtBeforePmt ;
	private int monthlyFeePaidAmt;
	private int fineAmt ;
	private int feePaidAmt ;
	private String feePmtDate = "";
	private String feePmtStatus;

	private String pmtMode = "";
	private String instrumentNo = "";
	private String instrumentDetails = "";
	private String instrumentDate;
	private String feeReceiptNo = "";
	private String receiptCategory = "";
	private String feeReceiptDate;
	private String delFlag;
	private String rModId;
	private String rModTime;
	private String rCreId;
	private String rCreTime;


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


	public String getStudFeeDDId() {
		return studFeeDDId;
	}


	public void setStudFeeDDId(String studFeeDDId) {
		this.studFeeDDId = studFeeDDId;
	}


	public String getFeePmtSrlNo() {
		return feePmtSrlNo;
	}


	public void setFeePmtSrlNo(String feePmtSrlNo) {
		this.feePmtSrlNo = feePmtSrlNo;
	}

	public int getFeeDueAmtBeforePmt() {
		return feeDueAmtBeforePmt;
	}


	public void setFeeDueAmtBeforePmt(int feeDueAmtBeforePmt) {
		this.feeDueAmtBeforePmt = feeDueAmtBeforePmt;
	}
	

	public int getMonthlyFeeDueAmtBeforePmt() {
		return monthlyFeeDueAmtBeforePmt;
	}


	public void setMonthlyFeeDueAmtBeforePmt(int monthlyFeeDueAmtBeforePmt) {
		this.monthlyFeeDueAmtBeforePmt = monthlyFeeDueAmtBeforePmt;
	}


	public int getFineAmt() {
		return fineAmt;
	}


	public void setFineAmt(int fineAmt) {
		this.fineAmt = fineAmt;
	}


	public int getFeePaidAmt() {
		return feePaidAmt;
	}


	public void setFeePaidAmt(int feePaidAmt) {
		this.feePaidAmt = feePaidAmt;
	}


	public String getFeePmtDate() {
		return feePmtDate;
	}


	public void setFeePmtDate(String feePmtDate) {
		this.feePmtDate = feePmtDate;
	}


	public String getFeePmtStatus() {
		return feePmtStatus;
	}


	public void setFeePmtStatus(String feePmtStatus) {
		this.feePmtStatus = feePmtStatus;
	}


	public String getPmtMode() {
		return pmtMode;
	}


	public void setPmtMode(String pmtMode) {
		this.pmtMode = pmtMode;
	}


	public String getInstrumentNo() {
		return instrumentNo;
	}


	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}


	public String getInstrumentDetails() {
		return instrumentDetails;
	}


	public void setInstrumentDetails(String instrumentDetails) {
		this.instrumentDetails = instrumentDetails;
	}


	public String getInstrumentDate() {
		return instrumentDate;
	}


	public void setInstrumentDate(String instrumentDate) {
		this.instrumentDate = instrumentDate;
	}


	public String getFeeReceiptNo() {
		return feeReceiptNo;
	}


	public void setFeeReceiptNo(String feeReceiptNo) {
		this.feeReceiptNo = feeReceiptNo;
	}


	public String getReceiptCategory() {
		return receiptCategory;
	}


	public void setReceiptCategory(String receiptCategory) {
		this.receiptCategory = receiptCategory;
	}


	public String getFeeReceiptDate() {
		return feeReceiptDate;
	}


	public void setFeeReceiptDate(String feeReceiptDate) {
		this.feeReceiptDate = feeReceiptDate;
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
	


	public int getMonthlyFeePaidAmt() {
		return monthlyFeePaidAmt;
	}


	public void setMonthlyFeePaidAmt(int monthlyFeePaidAmt) {
		this.monthlyFeePaidAmt = monthlyFeePaidAmt;
	}


	@Override
	public String toString() {
		return "StudentFeePayment [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", studFeeDDId=" + studFeeDDId
				+ ", feePmtSrlNo=" + feePmtSrlNo + ", feeDueAmtBeforePmt="
				+ feeDueAmtBeforePmt + ", monthlyFeeDueAmtBeforePmt="
				+ monthlyFeeDueAmtBeforePmt + ", monthlyFeePaidAmt="
				+ monthlyFeePaidAmt + ", fineAmt=" + fineAmt + ", feePaidAmt="
				+ feePaidAmt + ", feePmtDate=" + feePmtDate + ", feePmtStatus="
				+ feePmtStatus + ", pmtMode=" + pmtMode + ", instrumentNo="
				+ instrumentNo + ", instrumentDetails=" + instrumentDetails
				+ ", instrumentDate=" + instrumentDate + ", feeReceiptNo="
				+ feeReceiptNo + ", receiptCategory=" + receiptCategory
				+ ", feeReceiptDate=" + feeReceiptDate + ", delFlag=" + delFlag
				+ ", rModId=" + rModId + ", rModTime=" + rModTime + ", rCreId="
				+ rCreId + ", rCreTime=" + rCreTime + "]";
	}


	/*public String stringForDbAudit() {

		StringBuffer result = new StringBuffer();

		result.append("DB_TS=").append(getDbTs())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("BRANCH_ID=").append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("AC_TERM=").append(getAcTerm())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_CATGRY=").append(getFeeCategory())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_TERM=").append(getFeeCategory())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_TYPE=").append(getFeeType())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("COURSE=").append(getCourse())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("TERM=").append(getTerm())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_AMT=").append(getFeeAmt())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("delFlg=").append(getDelFlag())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_ID=").append(getrCreId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_TIME=").append(getrCreTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_ID=").append(getrModId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_TIME=").append(getrModTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		return result.toString();
	}
*/
}
