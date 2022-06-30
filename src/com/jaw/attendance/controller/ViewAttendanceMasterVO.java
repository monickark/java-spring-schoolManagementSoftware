package com.jaw.attendance.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

//AcademicTermDetails Pojo class
public class ViewAttendanceMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(ViewAttendanceMasterVO.class);
	
	private Map<String, ViewAttendanceListVO> viewAttendanceListVOs = new LinkedHashMap<String, ViewAttendanceListVO>();
	private List<ViewAttendanceListVO> subjectAttendanceList = new ArrayList<ViewAttendanceListVO>();
	private Map<String, String> monthList = new LinkedHashMap<String, String>();
	
	public List<ViewAttendanceListVO> getSubjectAttendanceList() {
		return subjectAttendanceList;
	}
	
	public void setSubjectAttendanceList(List<ViewAttendanceListVO> subjectAttendanceList) {
		this.subjectAttendanceList = subjectAttendanceList;
	}
	
	private ViewAttendanceSearchVO viewAttendanceSearchVO = new ViewAttendanceSearchVO();
	
	public Map<String, String> getMonthList() {
		return monthList;
	}
	
	public void setMonthList(Map<String, String> monthList) {
		this.monthList = monthList;
	}
	
	public Map<String, ViewAttendanceListVO> getViewAttendanceListVOs() {
		return viewAttendanceListVOs;
	}
	
	public void setViewAttendanceListVOs(Map<String, ViewAttendanceListVO> viewAttendanceListVOs) {
		this.viewAttendanceListVOs = viewAttendanceListVOs;
	}
	
	public ViewAttendanceSearchVO getViewAttendanceSearchVO() {
		return viewAttendanceSearchVO;
	}
	
	public void setViewAttendanceSearchVO(ViewAttendanceSearchVO viewAttendanceSearchVO) {
		this.viewAttendanceSearchVO = viewAttendanceSearchVO;
	}
	
}
