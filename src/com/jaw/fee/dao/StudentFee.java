package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class StudentFee implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(StudentFee.class);
	// Properties
	
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm;
	private String studentAdmissNo;
	private String isNewAdmission;
	private String feeCategory;
	private String feeTerm;
	private String feeType;
	private int feeAmt;
	private String feePaymentTerm ;
	private String electiceSpec="" ;
	private String courseVariant = "";
	private String delFlag = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
	
	public String getIsNewAdmission() {
		return isNewAdmission;
	}
	public void setIsNewAdmission(String isNewAdmission) {
		this.isNewAdmission = isNewAdmission;
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
	public int getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(int feeAmt) {
		this.feeAmt = feeAmt;
	}
	public String getFeePaymentTerm() {
		return feePaymentTerm;
	}
	public void setFeePaymentTerm(String feePaymentTerm) {
		this.feePaymentTerm = feePaymentTerm;
	}
	public String getElecticeSpec() {
		return electiceSpec;
	}
	public void setElecticeSpec(String electiceSpec) {
		this.electiceSpec = electiceSpec;
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
	
	public String getCourseVariant() {
		return courseVariant;
	}
	public void setCourseVariant(String courseVariant) {
		this.courseVariant = courseVariant;
	}
	@Override
	public String toString() {
		return "StudentFee [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", studentAdmissNo=" + studentAdmissNo + ", isNewAdmission="
				+ isNewAdmission + ", feeCategory=" + feeCategory
				+ ", feeTerm=" + feeTerm + ", feeType=" + feeType + ", feeAmt="
				+ feeAmt + ", feePaymentTerm=" + feePaymentTerm
				+ ", electiceSpec=" + electiceSpec + ", courseVariant="
				+ courseVariant + ", delFlag=" + delFlag + ", rModId=" + rModId
				+ ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + ", getIsNewAdmission()="
				+ getIsNewAdmission() + ", getDbTs()=" + getDbTs()
				+ ", getInstId()=" + getInstId() + ", getBranchId()="
				+ getBranchId() + ", getAcTerm()=" + getAcTerm()
				+ ", getStudentAdmissNo()=" + getStudentAdmissNo()
				+ ", getFeeCategory()=" + getFeeCategory() + ", getFeeTerm()="
				+ getFeeTerm() + ", getFeeType()=" + getFeeType()
				+ ", getFeeAmt()=" + getFeeAmt() + ", getFeePaymentTerm()="
				+ getFeePaymentTerm() + ", getElecticeSpec()="
				+ getElecticeSpec() + ", getDelFlag()=" + getDelFlag()
				+ ", getrModId()=" + getrModId() + ", getrModTime()="
				+ getrModTime() + ", getrCreId()=" + getrCreId()
				+ ", getrCreTime()=" + getrCreTime() + ", getCourseVariant()="
				+ getCourseVariant() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}


}
