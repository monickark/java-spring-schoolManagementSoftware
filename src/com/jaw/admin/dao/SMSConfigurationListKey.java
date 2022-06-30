package com.jaw.admin.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class SMSConfigurationListKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSConfigurationListKey.class);

	// Properties	
	private String instId;
	private String branchId;
	private String propertyType ;
	private Integer dbTs;	
	private String propertyName;
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
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	@Override
	public String toString() {
		return "SMSConfigurationListKey [instId=" + instId + ", branchId="
				+ branchId + ", propertyType=" + propertyType + ", dbTs="
				+ dbTs + ", propertyName=" + propertyName + "]";
	}
	
}
