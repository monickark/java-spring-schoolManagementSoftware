package com.jaw.core.controller;

import org.apache.log4j.Logger;

import com.jaw.core.dao.HolidayMaintenance;

public class HolidayGenerationVO {
	// Logging
		 Logger logger = Logger.getLogger(HolidayGenerationVO.class);	
		// Properties
	private String branchId;
	private String acTerm ;
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
	
}
