package com.jaw.core.controller;

import org.apache.log4j.Logger;

public class CourseSubLinkSearchVO {
	// Logging
	Logger logger = Logger.getLogger(CourseSubLinkSearchVO.class);
	
	// Properties
	private String courseMasterId;
	private String termId = "";
	
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
	
}
