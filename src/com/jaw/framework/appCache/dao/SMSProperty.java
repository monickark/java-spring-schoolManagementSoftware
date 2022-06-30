package com.jaw.framework.appCache.dao;

import java.io.Serializable;

public class SMSProperty implements Serializable {

	private Integer dbTs;
	private String instId;
	private String branchId;
	private String propertyName;
	private String propertyValue = "";
	private String propertyDesc = "";
	private String delFlag = "";
	private String rModId = "";
	private String rModTime;
	private String rCreId = "";
	private String rCreTime;
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
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getrModId() {
		return rModId;
	}
	public void setrModId(String rModId) {
		this.rModId = rModId;
	}
	public String getrModTime() {
		return rModTime;
	}
	public void setrModTime(String rModTime) {
		this.rModTime = rModTime;
	}
	public String getrCreId() {
		return rCreId;
	}
	public void setrCreId(String rCreId) {
		this.rCreId = rCreId;
	}
	public String getrCreTime() {
		return rCreTime;
	}
	public void setrCreTime(String rCreTime) {
		this.rCreTime = rCreTime;
	}
	@Override
	public String toString() {
		return "SMSProperty [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", propertyName=" + propertyName
				+ ", propertyValue=" + propertyValue + ", propertyDesc="
				+ propertyDesc + ", delFlag=" + delFlag + ", rModId=" + rModId
				+ ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + "]";
	}

	
}