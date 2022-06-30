package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class AcademicCalendarListKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(AcademicCalendarListKey.class);

	// Properties
	private String instId;
	private String branchId;
	private String acTerm ;
	private String itemStartDate;
	private String itemEndDate;
	private String itemType ;

	public String getAcTerm() {
		return acTerm;
	}

	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}

	public String getItemStartDate() {
		return itemStartDate;
	}

	public void setItemStartDate(String itemStartDate) {
		this.itemStartDate = itemStartDate;
	}

	public String getItemEndDate() {
		return itemEndDate;
	}

	public void setItemEndDate(String itemEndDate) {
		this.itemEndDate = itemEndDate;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
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

	@Override
	public String toString() {
		return "AcademicCalendarListKey [instId=" + instId + ", branchId="
				+ branchId + ", acTerm=" + acTerm + ", itemStartDate="
				+ itemStartDate + ", itemEndDate=" + itemEndDate
				+ ", itemType=" + itemType + "]";
	}

	
}
