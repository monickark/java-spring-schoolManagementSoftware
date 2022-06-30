package com.jaw.fee.controller;

import java.io.Serializable;


/**
 * @author Gritz Horizons Ltd 1
 * 
 */
public class FeePaymentVO implements Serializable{

	private int feeDueAmtBeforePmt;
	private int monthlyFeeDueAmtBeforePmt;
	private int monthlyFeePaidAmt;
	private int fineAmt;
	private int feePaidAmt;
	private String feePmtDate = "";
	private String feePmtStatus;
	private String pmtMode = "";
	private String instrumentNo = "";
	private String instrumentDetails = "";
	private String instrumentDate;
	private String feeReceiptNo = "";
	private String receiptCategory = "";
	private String isReceiptRequired = "";
	private String feeReceiptDate;
	private String studFeeDDId = "";
	private String isPartialPaymentAllowed = "";
	private String selectFee="";
	
	public String getIsPartialPaymentAllowed() {
		return isPartialPaymentAllowed;
	}

	public void setIsPartialPaymentAllowed(String isPartialPaymentAllowed) {
		this.isPartialPaymentAllowed = isPartialPaymentAllowed;
	}

	public String getIsReceiptRequired() {
		return isReceiptRequired;
	}

	public void setIsReceiptRequired(String isReceiptRequired) {
		this.isReceiptRequired = isReceiptRequired;
	}

	public String getStudFeeDDId() {
		return studFeeDDId;
	}

	public void setStudFeeDDId(String studFeeDDId) {
		this.studFeeDDId = studFeeDDId;
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
	

	public String getSelectFee() {
		return selectFee;
	}

	public void setSelectFee(String selectFee) {
		this.selectFee = selectFee;
	}
	

	public int getMonthlyFeePaidAmt() {
		return monthlyFeePaidAmt;
	}

	public void setMonthlyFeePaidAmt(int monthlyFeePaidAmt) {
		this.monthlyFeePaidAmt = monthlyFeePaidAmt;
	}

	@Override
	public String toString() {
		return "FeePaymentVO [feeDueAmtBeforePmt=" + feeDueAmtBeforePmt
				+ ", monthlyFeeDueAmtBeforePmt=" + monthlyFeeDueAmtBeforePmt
				+ ", monthlyFeePaidAmt=" + monthlyFeePaidAmt + ", fineAmt="
				+ fineAmt + ", feePaidAmt=" + feePaidAmt + ", feePmtDate="
				+ feePmtDate + ", feePmtStatus=" + feePmtStatus + ", pmtMode="
				+ pmtMode + ", instrumentNo=" + instrumentNo
				+ ", instrumentDetails=" + instrumentDetails
				+ ", instrumentDate=" + instrumentDate + ", feeReceiptNo="
				+ feeReceiptNo + ", receiptCategory=" + receiptCategory
				+ ", isReceiptRequired=" + isReceiptRequired
				+ ", feeReceiptDate=" + feeReceiptDate + ", studFeeDDId="
				+ studFeeDDId + ", isPartialPaymentAllowed="
				+ isPartialPaymentAllowed + ", selectFee=" + selectFee + "]";
	}


	
}
