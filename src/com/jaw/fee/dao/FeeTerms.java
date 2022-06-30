package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.core.dao.AcademicCalendar;

public class FeeTerms implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(FeeTerms.class);
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String feeTerm;
	private String feePaymentTerm ;
	private String feeTermValue;
	private String feePaymentTermValue ;
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
	public String getFeeTerm() {
		return feeTerm;
	}
	public void setFeeTerm(String feeTerm) {
		this.feeTerm = feeTerm;
	}
	public String getFeePaymentTerm() {
		return feePaymentTerm;
	}
	public void setFeePaymentTerm(String feePaymentTerm) {
		this.feePaymentTerm = feePaymentTerm;
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
	public String getFeeTermValue() {
		return feeTermValue;
	}
	public void setFeeTermValue(String feeTermValue) {
		this.feeTermValue = feeTermValue;
	}
	public String getFeePaymentTermValue() {
		return feePaymentTermValue;
	}
	public void setFeePaymentTermValue(String feePaymentTermValue) {
		this.feePaymentTermValue = feePaymentTermValue;
	}
	@Override
	public String toString() {
		return "FeeTerms [dbTs=" + dbTs + ", instId=" + instId + ", branchId="
				+ branchId + ", feeTerm=" + feeTerm + ", feePaymentTerm="
				+ feePaymentTerm + ", feeTermValue=" + feeTermValue
				+ ", feePaymentTermValue=" + feePaymentTermValue + ", delFlag="
				+ delFlag + ", rModId=" + rModId + ", rModTime=" + rModTime
				+ ", rCreId=" + rCreId + ", rCreTime=" + rCreTime + "]";
	}
	

}
