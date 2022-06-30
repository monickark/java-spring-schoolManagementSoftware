package com.jaw.communication.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.admin.controller.SMSConfigurationVO;

public class SMSHistorySearchVO implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSHistorySearchVO.class);

	// Properties	
	 private String requestType;
	private String acTerm;
	private String fromDate;
	private String toDate;
	
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
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
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	@Override
	public String toString() {
		return "SMSHistorySearchVO [requestType=" + requestType + ", acTerm="
				+ acTerm + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ "]";
	}
	

}
