package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

//AcademicCalendar Pojo class
public class AcademicCalendarList implements Serializable {
	
	// Logging
	Logger logger = Logger.getLogger(AcademicCalendarList.class);
	
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acItemId;
	private String acTerm;
	private String itemStartDate;
	private String itemEndDate;
	private String itemType;
	private String itemDesc;
	
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
	
	public String getAcItemId() {
		return acItemId;
	}
	
	public void setAcItemId(String acItemId) {
		this.acItemId = acItemId;
	}
	
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
	
	public String getItemDesc() {
		return itemDesc;
	}
	
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
	@Override
	public String toString() {
		return "AcademicCalendarList [getDbTs()=" + getDbTs() + ", getInstId()=" + getInstId()
				+ ", getBranchId()=" + getBranchId() + ", getAcItemId()=" + getAcItemId()
				+ ", getAcTerm()=" + getAcTerm() + ", getItemStartDate()=" + getItemStartDate()
				+ ", getItemEndDate()=" + getItemEndDate() + ", getItemType()=" + getItemType()
				+ ", getItemDesc()=" + getItemDesc() + "]";
	}
	
}
