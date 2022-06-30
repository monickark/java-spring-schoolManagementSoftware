package com.jaw.fee.controller;

public class FeeReportListVO {
	private String studentName = "";
	private String feeDueAmt;
	private double feePaidAmt;
	private String feePaymentDate = "";
	private int rowId;
	private String feeAmount = "";
	private String admissionNumber = "";
	private String stGrpId = "";
	private String stGrpName = "";
	private String feeConcessionAmount = "";
	private String prevYearPendingAmt = "";

	private String menuProfile = "";
	
	public String getMenuProfile() {
		return menuProfile;
	}

	public void setMenuProfile(String menuProfile) {
		this.menuProfile = menuProfile;
	}

	public String getPrevYearPendingAmt() {
		return prevYearPendingAmt;
	}

	public void setPrevYearPendingAmt(String prevYearPendingAmt) {
		this.prevYearPendingAmt = prevYearPendingAmt;
	}

	public String getFeeConcessionAmount() {
		return feeConcessionAmount;
	}

	public void setFeeConcessionAmount(String feeConcessionAmount) {
		this.feeConcessionAmount = feeConcessionAmount;
	}

	public String getStGrpId() {
		return stGrpId;
	}

	public void setStGrpId(String stGrpId) {
		this.stGrpId = stGrpId;
	}

	public String getStGrpName() {
		return stGrpName;
	}

	public void setStGrpName(String stGrpName) {
		this.stGrpName = stGrpName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getFeeDueAmt() {
		return feeDueAmt;
	}

	public void setFeeDueAmt(String feeDueAmt) {
		this.feeDueAmt = feeDueAmt;
	}

	public double getFeePaidAmt() {
		return feePaidAmt;
	}

	public void setFeePaidAmt(double feePaidAmt) {
		this.feePaidAmt = feePaidAmt;
	}

	public String getFeePaymentDate() {
		return feePaymentDate;
	}

	public void setFeePaymentDate(String feePaymentDate) {
		this.feePaymentDate = feePaymentDate;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getAdmissionNumber() {
		return admissionNumber;
	}

	public void setAdmissionNumber(String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}

	@Override
	public String toString() {
		return "FeeReportListVO [studentName=" + studentName + ", feeDueAmt="
				+ feeDueAmt + ", feePaidAmt=" + feePaidAmt
				+ ", feePaymentDate=" + feePaymentDate + ", rowId=" + rowId
				+ ", feeAmount=" + feeAmount + ", admissionNumber="
				+ admissionNumber + ", stGrpId=" + stGrpId + ", stGrpName="
				+ stGrpName + ", feeConcessionAmount=" + feeConcessionAmount
				+ ", prevYearPendingAmt=" + prevYearPendingAmt
				+ ", menuProfile=" + menuProfile + "]";
	}


}

