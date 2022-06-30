package com.jaw.core.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

//CourseClasses Pojo class
public class CourseClassesVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(CourseClassesVO.class);

	private String instId = "";
	private String branchId = "";
	private String ccId = "";
	private String acTerm = "";
	private String studentGrpId = "";
	private String crslId = "";
	private String saNo = "";
	private String clsType = "";
	private String labBatch = "";
	private String noOfClassesPerWeek = "0";
	private String noOfLabClassesPerWeek = "0";
	private String staffId = "";
	private String subId = "";
	private String subName = "";
	private Integer dbTs;
	private String staffName = "";
	private int rowid;
	private String requiresLab = "";
	private String isError = "";
	private String duration = "";

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getIsError() {
		return isError;
	}

	public void setIsError(String isError) {
		this.isError = isError;
	}

	public String getRequiresLab() {
		return requiresLab;
	}

	public void setRequiresLab(String requiresLab) {
		this.requiresLab = requiresLab;
	}

	public String getSaNo() {
		return saNo;
	}

	public void setSaNo(String saNo) {
		this.saNo = saNo;
	}

	public String getClsType() {
		return clsType;
	}

	public void setClsType(String clsType) {
		this.clsType = clsType;
	}

	public String getLabBatch() {
		return labBatch;
	}

	public void setLabBatch(String labBatch) {
		this.labBatch = labBatch;
	}

	public String getNoOfClassesPerWeek() {
		return noOfClassesPerWeek;
	}

	public void setNoOfClassesPerWeek(String noOfClassesPerWeek) {
		this.noOfClassesPerWeek = noOfClassesPerWeek;
	}

	public String getNoOfLabClassesPerWeek() {
		return noOfLabClassesPerWeek;
	}

	public void setNoOfLabClassesPerWeek(String noOfLabClassesPerWeek) {
		this.noOfLabClassesPerWeek = noOfLabClassesPerWeek;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
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

	public String getCcId() {
		return ccId;
	}

	public void setCcId(String ccId) {
		this.ccId = ccId;
	}

	public String getAcTerm() {
		return acTerm;
	}

	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}

	public String getStudentGrpId() {
		return studentGrpId;
	}

	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}

	public String getCrslId() {
		return crslId;
	}

	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}

	public String getStaffId() {
		System.out.println("staffid in vo:" + staffId);
		return ((staffId == null) ? "" : staffId);
	}

	public void setStaffId(String staffId) {

		this.staffId = staffId;

	}

	@Override
	public String toString() {
		return "CourseClassesVO [logger=" + logger + ", instId=" + instId
				+ ", branchId=" + branchId + ", ccId=" + ccId + ", acTerm="
				+ acTerm + ", studentGrpId=" + studentGrpId + ", crslId="
				+ crslId + ", saNo=" + saNo + ", clsType=" + clsType
				+ ", labBatch=" + labBatch + ", noOfClassesPerWeek="
				+ noOfClassesPerWeek + ", noOfLabClassesPerWeek="
				+ noOfLabClassesPerWeek + ", staffId=" + staffId + ", subId="
				+ subId + ", subName=" + subName + ", dbTs=" + dbTs
				+ ", staffName=" + staffName + ", rowid=" + rowid
				+ ", requiresLab=" + requiresLab + ", isError=" + isError
				+ ", duration=" + duration + "]";
	}

}
