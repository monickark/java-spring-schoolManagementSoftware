package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

//CourseClasses Pojo class
public class CourseClassesList implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(CourseClassesList.class);
	private String instId;
	private String branchId;
	private String ccId = "";
	private String studentGrpId = "";
	private String acTerm = "";
	private String subId = "";
	private String staffId = "";
	private String staffName = "";
	private String optSubId = "";
	private String subType = "";
	private String subName="";
	private String crslId = "";
	private String saNo;
	private String clsType;
	private String labBatch;
	private String noOfClassesPerWeek;
	private String noOfLabClassesPerWeek;
	private String requiresLab="";
	private String duration = "";
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
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
	public String getStudentGrpId() {
		return studentGrpId;
	}
	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getOptSubId() {
		return optSubId;
	}
	public void setOptSubId(String optSubId) {
		this.optSubId = optSubId;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public String getCrslId() {
		return crslId;
	}
	public void setCrslId(String crslId) {
		this.crslId = crslId;
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
	public String getRequiresLab() {
		return requiresLab;
	}
	public void setRequiresLab(String requiresLab) {
		this.requiresLab = requiresLab;
	}
	@Override
	public String toString() {
		return "CourseClassesList [logger=" + logger + ", instId=" + instId
				+ ", branchId=" + branchId + ", ccId=" + ccId
				+ ", studentGrpId=" + studentGrpId + ", acTerm=" + acTerm
				+ ", subId=" + subId + ", staffId=" + staffId + ", staffName="
				+ staffName + ", optSubId=" + optSubId + ", subType=" + subType
				+ ", subName=" + subName + ", crslId=" + crslId + ", saNo="
				+ saNo + ", clsType=" + clsType + ", labBatch=" + labBatch
				+ ", noOfClassesPerWeek=" + noOfClassesPerWeek
				+ ", noOfLabClassesPerWeek=" + noOfLabClassesPerWeek
				+ ", requiresLab=" + requiresLab + ", duration=" + duration
				+ "]";
	}

	
	
	
	
}
