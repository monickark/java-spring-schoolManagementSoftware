package com.jaw.login.dao;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.jaw.common.constants.AuditConstant;

//Management Pojo class
public class DashboardConsolidatedKey {

	// Logging
	Logger logger = Logger.getLogger(DashboardConsolidatedKey.class);

	// Properties
	private String instId;
	private String branchId ;
	private String fromDate;
	private String toDate;
	private String fromDateTime;
	private String toDateTime;
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
	public String getFromDateTime() {
		return fromDate+" 00:00:00";
	}
	
	public String getToDateTime() {
		return toDate+" 23:59:59";
	}
	
	
	

}
