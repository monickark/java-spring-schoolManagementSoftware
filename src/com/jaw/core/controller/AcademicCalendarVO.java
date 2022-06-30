package com.jaw.core.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;



public class AcademicCalendarVO implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(AcademicCalendarVO.class);

	// Properties	
	private String acItemId;
	private String acTerm ;
	private String itemStartDate;
	private String itemEndDate;
	private String itemType ;
	private String itemDesc ;
	private int rowId;
	
	
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
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	@Override
	public String toString() {
		return "AcademicCalendarVO [acItemId=" + acItemId + ", acTerm=" + acTerm
				+ ", itemStartDate=" + itemStartDate + ", itemEndDate="
				+ itemEndDate + ", itemType=" + itemType + ", itemDesc="
				+ itemDesc + ", rowId=" + rowId + "]";
	}
	
}
