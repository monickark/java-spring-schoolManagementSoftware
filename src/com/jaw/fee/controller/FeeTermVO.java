package com.jaw.fee.controller;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.jaw.fee.dao.FeeTerms;

public class FeeTermVO implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(FeeTermVO.class);
	// Properties
	private String[] feeTerm;
	private String feePaymentTerm ;
	private String[] feeTermValue;
	private String feePaymentTermValue ;
	public String[] getFeeTerm() {
		return feeTerm;
	}
	public void setFeeTerm(String[] feeTerm) {
		this.feeTerm = feeTerm;
	}
	public String getFeePaymentTerm() {
		return feePaymentTerm;
	}
	public void setFeePaymentTerm(String feePaymentTerm) {
		this.feePaymentTerm = feePaymentTerm;
	}
	public String[] getFeeTermValue() {
		return feeTermValue;
	}
	public void setFeeTermValue(String[] feeTermValue) {
		this.feeTermValue = feeTermValue;
	}
	public String getFeePaymentTermValue() {
		return feePaymentTermValue;
	}
	public void setFeePaymentTermValue(String feePaymentTermValue) {
		this.feePaymentTermValue = feePaymentTermValue;
	}
	@Override
	public String toString() {
		return "FeesTermVO [feeTerm=" + Arrays.toString(feeTerm)
				+ ", feePaymentTerm=" + feePaymentTerm + ", feeTermValue="
				+ Arrays.toString(feeTermValue) + ", feePaymentTermValue="
				+ feePaymentTermValue + "]";
	}
	

}
