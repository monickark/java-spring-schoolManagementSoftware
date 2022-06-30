package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class FeeDueList implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(FeeDueList.class);
	// Properties
	    
		private String sFeeDmdSeqId;
		private String feeCategory;
		private int feeDueAmt;
		private String feePaymentTerm ;
		private String admissionNumber="";
		private String instId="";
		private String branchId="";
		private int rowId;
		public String getsFeeDmdSeqId() {
			return sFeeDmdSeqId;
		}
		public void setsFeeDmdSeqId(String sFeeDmdSeqId) {
			this.sFeeDmdSeqId = sFeeDmdSeqId;
		}
		public String getFeeCategory() {
			return feeCategory;
		}
		public void setFeeCategory(String feeCategory) {
			this.feeCategory = feeCategory;
		}
		public int getFeeDueAmt() {
			return feeDueAmt;
		}
		public void setFeeDueAmt(int feeDueAmt) {
			this.feeDueAmt = feeDueAmt;
		}
		public String getFeePaymentTerm() {
			return feePaymentTerm;
		}
		public void setFeePaymentTerm(String feePaymentTerm) {
			this.feePaymentTerm = feePaymentTerm;
		}
		public String getAdmissionNumber() {
			return admissionNumber;
		}
		public void setAdmissionNumber(String admissionNumber) {
			this.admissionNumber = admissionNumber;
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
		
		public int getRowId() {
			return rowId;
		}
		public void setRowId(int rowId) {
			this.rowId = rowId;
		}
		@Override
		public String toString() {
			return "FeeDueList [sFeeDmdSeqId=" + sFeeDmdSeqId
					+ ", feeCategory=" + feeCategory + ", feeDueAmt="
					+ feeDueAmt + ", feePaymentTerm=" + feePaymentTerm
					+ ", admissionNumber=" + admissionNumber + ", instId="
					+ instId + ", branchId=" + branchId + ", rowId=" + rowId
					+ "]";
		}
		

}
