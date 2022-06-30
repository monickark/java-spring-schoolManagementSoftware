package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

//CourseSubLink Pojo class
public class CourseSubLinkList implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(CourseSubLinkList.class);
	
	
	private String crslId;
	private String courseMasterId;
	private String termId = "";
	private String subId;
	private String subName = "";
	private String subType="";
	private String requiresTeacher = "";
	private Integer reportCardOrder = 0;
	
	
	public String getRequiresTeacher() {
		return requiresTeacher;
	}
	public void setRequiresTeacher(String requiresTeacher) {
		this.requiresTeacher = requiresTeacher;
	}
	public Integer getReportCardOrder() {
		return reportCardOrder;
	}
	public void setReportCardOrder(Integer reportCardOrder) {
		this.reportCardOrder = reportCardOrder;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getCrslId() {
		return crslId;
	}
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	public String getCourseMasterId() {
		return courseMasterId;
	}
	public void setCourseMasterId(String courseMasterId) {
		this.courseMasterId = courseMasterId;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	
	

}
