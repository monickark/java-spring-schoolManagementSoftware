package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class FeeReportKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(FeeReportKey.class);
	// Properties	  
		private String instId;
		private String branchId;
		private String admissionNumber="";
		private String studentGroupId="";
		private String academicTerm = "";
		private String fromDate="";
		private String toDate="";

		private String reportType="";
		private String menuProfile = "";
		
		public String getMenuProfile() {
			return menuProfile;
		}

		public void setMenuProfile(String menuProfile) {
			this.menuProfile = menuProfile;
		}
		public String getReportType() {
			return reportType;
		}
		public void setReportType(String reportType) {
			this.reportType = reportType;
		}
		private String feeReceipt = "";

		public String getFeeReceipt() {
			return feeReceipt;
		}

		public void setFeeReceipt(String feeReceipt) {
			this.feeReceipt = feeReceipt;
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
		public String getAdmissionNumber() {
			return admissionNumber;
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
		@Override
		public String toString() {
			return "FeeReportKey [logger=" + logger + ", instId=" + instId
					+ ", branchId=" + branchId + ", admissionNumber="
					+ admissionNumber + ", studentGroupId=" + studentGroupId
					+ ", academicTerm=" + academicTerm + ", fromDate="
					+ fromDate + ", toDate=" + toDate + "]";
		}
		
		
		
}
