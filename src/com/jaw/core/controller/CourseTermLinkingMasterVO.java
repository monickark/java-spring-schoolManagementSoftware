package com.jaw.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class CourseTermLinkingMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(CourseTermLinkingMasterVO.class);
	
	private List<CourseTermLinkingVO> courseTermLinkingVOs = new ArrayList<CourseTermLinkingVO>();
	private CourseTermLinkingVO courseTermLinkingVO = new CourseTermLinkingVO();
	private String courseMasterId;
	private String pageSize = "10";
	public List<CourseTermLinkingVO> getCourseTermLinkingVOs() {
		return courseTermLinkingVOs;
	}
	public void setCourseTermLinkingVOs(
			List<CourseTermLinkingVO> courseTermLinkingVOs) {
		this.courseTermLinkingVOs = courseTermLinkingVOs;
	}
	public CourseTermLinkingVO getCourseTermLinkingVO() {
		return courseTermLinkingVO;
	}
	public void setCourseTermLinkingVO(CourseTermLinkingVO courseTermLinkingVO) {
		this.courseTermLinkingVO = courseTermLinkingVO;
	}
	public String getCourseMasterId() {
		return courseMasterId;
	}
	public void setCourseMasterId(String courseMasterId) {
		this.courseMasterId = courseMasterId;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

}
