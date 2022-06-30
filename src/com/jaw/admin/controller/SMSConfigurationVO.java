package com.jaw.admin.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.core.controller.AcademicCalendarVO;

public class SMSConfigurationVO implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSConfigurationVO.class);

	// Properties	
	 private Integer dbTs;
	 private String instId;
	private String branchId;
	private String propertyType ;
	private String propertyName;
	private String propertyValue;
	private String propertyDesc ;
	private int rowId;
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getPropertyDesc() {
		return propertyDesc;
	}
	public void setPropertyDesc(String propertyDesc) {
		this.propertyDesc = propertyDesc;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
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
	@Override
	public String toString() {
		return "SMSConfigurationVO [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", propertyType=" + propertyType
				+ ", propertyName=" + propertyName + ", propertyValue="
				+ propertyValue + ", propertyDesc=" + propertyDesc + ", rowId="
				+ rowId + "]";
	}
	
	

}
