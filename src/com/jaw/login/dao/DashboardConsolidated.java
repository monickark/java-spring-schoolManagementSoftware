package com.jaw.login.dao;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.jaw.common.constants.AuditConstant;

//Management Pojo class
public class DashboardConsolidated {

	// Logging
	Logger logger = Logger.getLogger(DashboardConsolidated.class);

	// Properties
	private float feeCollection;
	private Integer newAdmission ;
	private Integer newRequest;
	private Integer newActivity;
	
	public float getFeeCollection() {
		return feeCollection;
	}

	public void setFeeCollection(float feeCollection) {
		this.feeCollection = feeCollection;
	}

	public Integer getNewAdmission() {
		return newAdmission;
	}

	public void setNewAdmission(Integer newAdmission) {
		this.newAdmission = newAdmission;
	}

	public Integer getNewRequest() {
		return newRequest;
	}

	public void setNewRequest(Integer newRequest) {
		this.newRequest = newRequest;
	}

	public Integer getNewActivity() {
		return newActivity;
	}

	public void setNewActivity(Integer newActivity) {
		this.newActivity = newActivity;
	}

	@Override
	public String toString() {
		return "DashboardConsolidated [logger=" + logger + ", feeCollection="
				+ feeCollection + ", newAdmission=" + newAdmission
				+ ", newRequest=" + newRequest + ", newActivity=" + newActivity
				+ "]";
	}

	

}
