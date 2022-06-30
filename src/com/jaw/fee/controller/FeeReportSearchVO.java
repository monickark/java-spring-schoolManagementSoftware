package com.jaw.fee.controller;

public class FeeReportSearchVO {
	private String admissionNumber = "";
	private String studentGroupId = "";
	private String academicTerm = "";
	private String fromDate = "";
	private String toDate = "";
	private String reportType = "";
	private String feeReceipt = "";
	private String menuProfile="";
	
	public String getMenuProfile() {
		return menuProfile;
	}

	public void setMenuProfile(String menuProfile) {
		this.menuProfile = menuProfile;
	}

	public String getFeeReceipt() {
		return feeReceipt;
	}

	public void setFeeReceipt(String feeReceipt) {
		this.feeReceipt = feeReceipt;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getAdmissionNumber() {
		return admissionNumber;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public void setAdmissionNumber(String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}

	public String getStudentGroupId() {
		return studentGroupId;
	}

	public void setStudentGroupId(String studentGroupId) {
		this.studentGroupId = studentGroupId;
	}

	public String getAcademicTerm() {
		return academicTerm;
	}

	public void setAcademicTerm(String academicTerm) {
		this.academicTerm = academicTerm;
	}

	@Override
	public String toString() {
		return "FeeDetailsSearchVO [admissionNumber=" + admissionNumber
				+ ", studentGroupId=" + studentGroupId + ", academicTerm="
				+ academicTerm + "]";
	}

}
