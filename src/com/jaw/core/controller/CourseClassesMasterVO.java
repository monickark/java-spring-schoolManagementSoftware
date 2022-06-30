package com.jaw.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

//AcademicTermDetails Pojo class
public class CourseClassesMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(CourseClassesMasterVO.class);
	
	private List<CourseClassesVO> courseClassesVOs = new ArrayList<CourseClassesVO>();
	private CourseClassesVO courseClassesVO = new CourseClassesVO();
	private CourseClassesSearchVO courseClassesSearchVO = new CourseClassesSearchVO();
	
	private String pageSize = "10";
	
	public String getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
	public CourseClassesSearchVO getCourseClassesSearchVO() {
		return courseClassesSearchVO;
	}
	
	public void setCourseClassesSearchVO(CourseClassesSearchVO courseClassesSearchVO) {
		this.courseClassesSearchVO = courseClassesSearchVO;
	}
	
	public List<CourseClassesVO> getCourseClassesVOs() {
		return courseClassesVOs;
	}
	
	public void setCourseClassesVOs(List<CourseClassesVO> courseClassesVOs) {
		this.courseClassesVOs = courseClassesVOs;
	}
	
	public CourseClassesVO getCourseClassesVO() {
		return courseClassesVO;
	}
	
	public void setCourseClassesVO(CourseClassesVO courseClassesVO) {
		this.courseClassesVO = courseClassesVO;
	}
	
}
