package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class FeePaidDetailList implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(FeePaidDetailList.class);
	// Properties	
		
		private String feeType;
		private int feePaidAmt;
		private int rowId;
		public String getFeeType() {
			return feeType;
		}
		public void setFeeType(String feeType) {
			this.feeType = feeType;
		}
		public int getFeePaidAmt() {
			return feePaidAmt;
		}
		public void setFeePaidAmt(int feePaidAmt) {
			this.feePaidAmt = feePaidAmt;
		}
		public int getRowId() {
			return rowId;
		}
		public void setRowId(int rowId) {
			this.rowId = rowId;
		}

}
