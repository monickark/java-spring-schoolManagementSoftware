package com.jaw.core.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class AcademicCalendarSearchVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(AcademicCalendarSearchVO.class);
	// Properties
	
	private String acTerm;
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
	@Override
	public String toString() {
		return "AcademicCalendarSearchVO [ acTerm="
				+ acTerm + ", itemStartDate=" + itemStartDate
				+ ", itemEndDate=" + itemEndDate + ", itemType=" + itemType
				+ "]";
	}
}
