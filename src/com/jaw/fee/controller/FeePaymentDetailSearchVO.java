package com.jaw.fee.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.core.controller.CourseTermLinkingVO;

public class FeePaymentDetailSearchVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(FeePaymentDetailSearchVO.class);
	
	// Properties	
	private String feePaymentTerm ;
	private String acTerm ;
	public String getFeePaymentTerm() {
		return feePaymentTerm;
	}
	public void setFeePaymentTerm(String feePaymentTerm) {
		this.feePaymentTerm = feePaymentTerm;
	}
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	@Override
	public String toString() {
		return "FeePaymentDetailSearchVO [feePaymentTerm=" + feePaymentTerm
				+ ", acTerm=" + acTerm + "]";
	}

}
