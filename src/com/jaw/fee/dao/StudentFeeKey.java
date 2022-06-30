package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class StudentFeeKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(StudentFeeKey.class);
	// Properties
	
			  
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm;
	private String studentAdmissNo;
	private String feeCategory;
	private String feeTerm;
	private String feeType;
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
	public String getStudentAdmissNo() {
		return studentAdmissNo;
	}
	public void setStudentAdmissNo(String studentAdmissNo) {
		this.studentAdmissNo = studentAdmissNo;
	}
	public String getFeeCategory() {
		return feeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}
	public String getFeeTerm() {
		return feeTerm;
	}
	public void setFeeTerm(String feeTerm) {
		this.feeTerm = feeTerm;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	@Override
	public String toString() {
		return "StudentFeeKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", studentAdmissNo=" + studentAdmissNo + ", feeCategory="
				+ feeCategory + ", feeTerm=" + feeTerm + ", feeType=" + feeType
				+ "]";
	}

}
