package com.jaw.fee.dao;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class FeeTermsKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(FeeTermsKey.class);
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String feePaymentTerm ;
	private String[] feeTerm;
	private String feesTerm;
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
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
	public String getFeePaymentTerm() {
		return feePaymentTerm;
	}
	public void setFeePaymentTerm(String feePaymentTerm) {
		this.feePaymentTerm = feePaymentTerm;
	}
	public String[] getFeeTerm() {
		return feeTerm;
	}
	public void setFeeTerm(String[] feeTerm) {
		this.feeTerm = feeTerm;
	}
	public String getFeesTerm() {
		return feesTerm;
	}
	public void setFeesTerm(String feesTerm) {
		this.feesTerm = feesTerm;
	}
	@Override
	public String toString() {
		return "FeeTermsKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", feePaymentTerm="
				+ feePaymentTerm + ", feeTerm=" + Arrays.toString(feeTerm)
				+ ", feesTerm=" + feesTerm + "]";
	}
	
	

}
