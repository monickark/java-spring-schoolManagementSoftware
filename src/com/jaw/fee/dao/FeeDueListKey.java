package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class FeeDueListKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(FeeDueListKey.class);
	// Properties	  
		private String instId;
		private String branchId;
		private String sFeeDmdSeqId;
		private String feePaymentTerm ;
		private String feeDemandStatus="" ;		
		private String admissionNumber="";
		private String academicTerm = "";
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
		public String getsFeeDmdSeqId() {
			return sFeeDmdSeqId;
		}
		public void setsFeeDmdSeqId(String sFeeDmdSeqId) {
			this.sFeeDmdSeqId = sFeeDmdSeqId;
		}
		public String getFeePaymentTerm() {
			return feePaymentTerm;
		}
		public void setFeePaymentTerm(String feePaymentTerm) {
			this.feePaymentTerm = feePaymentTerm;
		}
		public String getFeeDemandStatus() {
			return feeDemandStatus;
		}
		public void setFeeDemandStatus(String feeDemandStatus) {
			this.feeDemandStatus = feeDemandStatus;
		}
		public String getAdmissionNumber() {
			return admissionNumber;
		}
		public void setAdmissionNumber(String admissionNumber) {
			this.admissionNumber = admissionNumber;
		}
		public String getAcademicTerm() {
			return academicTerm;
		}
		public void setAcademicTerm(String academicTerm) {
			this.academicTerm = academicTerm;
		}
		@Override
		public String toString() {
			return "FeeDueListKey [logger=" + logger + ", instId=" + instId
					+ ", branchId=" + branchId + ", sFeeDmdSeqId="
					+ sFeeDmdSeqId + ", feePaymentTerm=" + feePaymentTerm
					+ ", feeDemandStatus=" + feeDemandStatus
					+ ", admissionNumber=" + admissionNumber
					+ ", academicTerm=" + academicTerm + "]";
		}
		
}
