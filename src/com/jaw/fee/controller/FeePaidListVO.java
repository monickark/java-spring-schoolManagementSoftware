package com.jaw.fee.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class FeePaidListVO implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(FeePaidListVO.class);
	// Properties
	
		private String sFeeDmdSeqId;
		private String feeCategory;
		private int feePaidAmt;
		private String feePaymentTerm ;		
		private String feePaymentSrlNo ;
		private String feePaymentDate ;
		private String feePaymentSts ;
		private String feeReceiptCatgry ;
		private String feeReceiptNum ;
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
		public int getFeePaidAmt() {
			return feePaidAmt;
		}
		public void setFeePaidAmt(int feePaidAmt) {
			this.feePaidAmt = feePaidAmt;
		}
		public String getFeePaymentTerm() {
			return feePaymentTerm;
		}
		public void setFeePaymentTerm(String feePaymentTerm) {
			this.feePaymentTerm = feePaymentTerm;
		}
		public String getFeePaymentSrlNo() {
			return feePaymentSrlNo;
		}
		public void setFeePaymentSrlNo(String feePaymentSrlNo) {
			this.feePaymentSrlNo = feePaymentSrlNo;
		}
		public String getFeePaymentDate() {
			return feePaymentDate;
		}
		public void setFeePaymentDate(String feePaymentDate) {
			this.feePaymentDate = feePaymentDate;
		}
		public String getFeePaymentSts() {
			return feePaymentSts;
		}
		public void setFeePaymentSts(String feePaymentSts) {
			this.feePaymentSts = feePaymentSts;
		}
		public String getFeeReceiptCatgry() {
			return feeReceiptCatgry;
		}
		public void setFeeReceiptCatgry(String feeReceiptCatgry) {
			this.feeReceiptCatgry = feeReceiptCatgry;
		}
		public String getFeeReceiptNum() {
			return feeReceiptNum;
		}
		public void setFeeReceiptNum(String feeReceiptNum) {
			this.feeReceiptNum = feeReceiptNum;
		}
		public int getRowId() {
			return rowId;
		}
		public void setRowId(int rowId) {
			this.rowId = rowId;
		}
		@Override
		public String toString() {
			return "FeePaidListVO [sFeeDmdSeqId=" + sFeeDmdSeqId
					+ ", feeCategory=" + feeCategory + ", feePaidAmt="
					+ feePaidAmt + ", feePaymentTerm=" + feePaymentTerm
					+ ", feePaymentSrlNo=" + feePaymentSrlNo
					+ ", feePaymentDate=" + feePaymentDate + ", feePaymentSts="
					+ feePaymentSts + ", feeReceiptCatgry=" + feeReceiptCatgry
					+ ", feeReceiptNum=" + feeReceiptNum + ", rowId=" + rowId
					+ "]";
		}

}
