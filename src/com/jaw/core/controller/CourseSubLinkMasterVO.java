package com.jaw.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

//AcademicTermDetails Pojo class
public class CourseSubLinkMasterVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Logging
	Logger logger = Logger.getLogger(CourseSubLinkMasterVO.class);
	
	private List<CourseSubLinkListVO> courseSubLinkVOs = new ArrayList<CourseSubLinkListVO>();
	private CourseSubLinkVO courseSubLinkVO = new CourseSubLinkVO();
	private CourseSubLinkListVO courseSubLinkListVO = new CourseSubLinkListVO();
	private CourseSubLinkSearchVO courseSubLinkSearchVO = new CourseSubLinkSearchVO();
	private String pageSize = "10";
	
	public CourseSubLinkListVO getCourseSubLinkListVO() {
		return courseSubLinkListVO;
	}

	public void setCourseSubLinkListVO(CourseSubLinkListVO courseSubLinkListVO) {
		this.courseSubLinkListVO = courseSubLinkListVO;
	}

	public String getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
	public CourseSubLinkSearchVO getCourseSubLinkSearchVO() {
		return courseSubLinkSearchVO;
	}
	
	public void setCourseSubLinkSearchVO(CourseSubLinkSearchVO courseSubLinkSearchVO) {
		this.courseSubLinkSearchVO = courseSubLinkSearchVO;
	}
	
	
	
	public List<CourseSubLinkListVO> getCourseSubLinkVOs() {
		return courseSubLinkVOs;
	}

	public void setCourseSubLinkVOs(List<CourseSubLinkListVO> courseSubLinkVOs) {
		this.courseSubLinkVOs = courseSubLinkVOs;
	}

	public CourseSubLinkVO getCourseSubLinkVO() {
		return courseSubLinkVO;
	}
	
	public void setCourseSubLinkVO(CourseSubLinkVO courseSubLinkVO) {
		this.courseSubLinkVO = courseSubLinkVO;
	}
	
}
