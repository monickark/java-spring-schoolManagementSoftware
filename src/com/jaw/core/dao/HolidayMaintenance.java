package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;
//HolidayMaintenance Pojo class
public class HolidayMaintenance implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(HolidayMaintenance.class);	
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm ;
	private String holDate ;
	private String studentGrpId ="";
	private String isWklyHoliday = "";
	private String delFlag = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
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
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getHolDate() {
		return holDate;
	}
	public void setHolDate(String holDate) {
		this.holDate = holDate;
	}
	public String getIsWklyHoliday() {
		return isWklyHoliday;
	}
	public void setIsWklyHoliday(String isWklyHoliday) {
		this.isWklyHoliday = isWklyHoliday;
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
	public String getStudentGrpId() {
		return studentGrpId;
	}
	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	@Override
	public String toString() {
		return "HolidayMaintenance [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", holDate=" + holDate + ", studentGrpId=" + studentGrpId
				+ ", isWklyHoliday=" + isWklyHoliday + ", delFlag=" + delFlag
				+ ", rModId=" + rModId + ", rModTime=" + rModTime + ", rCreId="
				+ rCreId + ", rCreTime=" + rCreTime + "]";
	}
	
}
