package com.jaw.fee.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;



public class FeePaymentList implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(FeePaymentList.class);
	// Properties
	
		private String feeType;
		private String electiveSpe;
		private int receiptFeeAmt;
		private String feePaidSerialNum;
		private int rowId;
		public String getFeeType() {
			return feeType;
		}
		public void setFeeType(String feeType) {
			this.feeType = feeType;
		}
		public String getElectiveSpe() {
			return electiveSpe;
		}
		public void setElectiveSpe(String electiveSpe) {
			this.electiveSpe = electiveSpe;
		}
		public int getReceiptFeeAmt() {
			return receiptFeeAmt;
		}
		public void setReceiptFeeAmt(int receiptFeeAmt) {
			this.receiptFeeAmt = receiptFeeAmt;
		}
		public int getRowId() {
			return rowId;
		}
		public void setRowId(int rowId) {
			this.rowId = rowId;
		}
		
		public String getFeePaidSerialNum() {
			return feePaidSerialNum;
		}
		public void setFeePaidSerialNum(String feePaidSerialNum) {
			this.feePaidSerialNum = feePaidSerialNum;
		}
		@Override
		public String toString() {
			return "FeePaymentList [logger=" + logger + ", feeType=" + feeType
					+ ", electiveSpe=" + electiveSpe + ", receiptFeeAmt="
					+ receiptFeeAmt + ", feePaidSerialNum=" + feePaidSerialNum
					+ ", rowId=" + rowId + "]";
		}


}
