package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class FeeReportList implements Serializable {

	// Logging
	Logger logger = Logger.getLogger(FeeReportList.class);
	// Properties

	private String studentName = "";
	private String feeDueAmt;
	private String feePaidAmt;
	private String feePaymentDate = "";
	private int rowId;
	private String feeConcessionAmount = "";
	private String feeAmount = "";
	private String admissionNumber = "";
	private String stGrpId = "";
	private String stGrpName = "";
	private String prevYearPendingAmt="";
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

	public String getAdmissionNumber() {
		return admissionNumber;
	}

	public void setAdmissionNumber(String admissionNumber) {
		this.admissionNumber = admissionNumber;
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

	public String getFeePaidAmt() {
		return feePaidAmt;
	}

	public void setFeePaidAmt(String feePaidAmt) {
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

}
