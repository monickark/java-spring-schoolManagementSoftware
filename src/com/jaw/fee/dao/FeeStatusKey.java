package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class FeeStatusKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(FeeStatusKey.class);
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm ;
	private String courseMasterId;
	private String feeCategory;
	private String termId;
	private String feeStatus ;
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
	public String getCourseMasterId() {
		return courseMasterId;
	}
	public void setCourseMasterId(String courseMasterId) {
		this.courseMasterId = courseMasterId;
	}
	public String getFeeCategory() {
		return feeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getFeeStatus() {
		return feeStatus;
	}
	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}
	@Override
	public String toString() {
		return "FeeStatusKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", courseMasterId=" + courseMasterId + ", feeCategory="
				+ feeCategory + ", termId=" + termId + ", feeStatus="
				+ feeStatus + "]";
	}
	

}
