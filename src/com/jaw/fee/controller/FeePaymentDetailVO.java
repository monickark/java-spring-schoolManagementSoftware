package com.jaw.fee.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.core.controller.CourseTermLinkingVO;

public class FeePaymentDetailVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(FeePaymentDetailVO.class);
	
	// Properties	
	private Integer dbTs;
	private String feePaymentTerm ;
	private String dueDate ;
	private String feeCategory ;
	private String acTerm ;
	private int rowId;
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}
	public String getFeePaymentTerm() {
		return feePaymentTerm;
	}
	public void setFeePaymentTerm(String feePaymentTerm) {
		this.feePaymentTerm = feePaymentTerm;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getFeeCategory() {
		return feeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	@Override
	public String toString() {
		return "FeePaymentDetailVO [dbTs=" + dbTs + ", feePaymentTerm="
				+ feePaymentTerm + ", dueDate=" + dueDate + ", feeCategory="
				+ feeCategory + ", acTerm=" + acTerm + ", rowId=" + rowId + "]";
	}

	
}
