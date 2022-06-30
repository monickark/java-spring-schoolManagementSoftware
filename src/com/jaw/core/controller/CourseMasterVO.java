package com.jaw.core.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class CourseMasterVO implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(CourseMasterVO.class);
	  
	
	// Properties
	private String courseName = "";
	private String courseId = "";
	private String combBranchId = "";		
	private String courseGrp = "";
	private String cvAppcble = "";
	private String cvCatType = "";
	private String cvListType= "";
	private String affId = "";
	private String affDetails = "";		
	private String termApplcble = "";
	private String medium = "";
	private String courseMasterId ;
	private String department = "";
	private int rowId;
	
	public String getCourseMasterId() {
		return courseMasterId;
	}
	public void setCourseMasterId(String courseMasterId) {
		this.courseMasterId = courseMasterId;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCombBranchId() {
		return combBranchId;
	}
	public void setCombBranchId(String combBranchId) {
		this.combBranchId = combBranchId;
	}
	public String getCourseGrp() {
		return courseGrp;
	}
	public void setCourseGrp(String courseGrp) {
		this.courseGrp = courseGrp;
	}
	public String getCvAppcble() {
		return cvAppcble;
	}
	public void setCvAppcble(String cvAppcble) {
		this.cvAppcble = cvAppcble;
	}
	public String getCvCatType() {
		return cvCatType;
	}
	public void setCvCatType(String cvCatType) {
		this.cvCatType = cvCatType;
	}
	public String getCvListType() {
		return cvListType;
	}
	public void setCvListType(String cvListType) {
		this.cvListType = cvListType;
	}
	public String getAffId() {
		return affId;
	}
	public void setAffId(String affId) {
		this.affId = affId;
	}
	public String getAffDetails() {
		return affDetails;
	}
	public void setAffDetails(String affDetails) {
		this.affDetails = affDetails;
	}
	public String getTermApplcble() {
		return termApplcble;
	}
	public void setTermApplcble(String termApplcble) {
		this.termApplcble = termApplcble;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Override
	public String toString() {
		return "CourseMasterVO [courseName=" + courseName + ", courseId="
				+ courseId + ", combBranchId=" + combBranchId + ", courseGrp="
				+ courseGrp + ", cvAppcble=" + cvAppcble + ", cvCatType="
				+ cvCatType + ", cvListType=" + cvListType + ", affId=" + affId
				+ ", affDetails=" + affDetails + ", termApplcble="
				+ termApplcble + ", medium=" + medium + ", courseMasterId="
				+ courseMasterId + ", department=" + department + ", rowId="
				+ rowId + "]";
	}
	
	
}
