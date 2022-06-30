package com.jaw.communication.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;



public class SMSRequestListKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSRequestListKey.class);

	// Properties	
    private String instId;
	private String branchId;
	private String requestType ;
	private String acTerm;
	private String fromDate;
	private String toDate;
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

}
