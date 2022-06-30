package com.jaw.attendance.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jaw.attendance.dao.StudentAttendance;
import com.jaw.attendance.dao.StudentAttendanceList;

//AcademicTermDetails Pojo class
public class AttendanceMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(AttendanceMasterVO.class);
	
	private AttendanceSearchVO attendanceSearchVO = new AttendanceSearchVO();
	private List<StudentAttendanceList> studentList = new ArrayList<StudentAttendanceList>();
	private String noOfSessions;
	private List<AttendanceListVO> attendanceListVOs = new ArrayList<AttendanceListVO>();
	private List<StudentAttendance> studentAttenList = new ArrayList<StudentAttendance>();
	private List<AttendanceDetailsListVO> attendanceDetailsListVOs = new ArrayList<AttendanceDetailsListVO>();
	private List<ViewAttendance> viewAttenList = new ArrayList<ViewAttendance>();
	private String pageSize = "10";
	private String pageSize1 = "10";
	private String pageSize2 = "10";
	
	public String getPageSize1() {
		return pageSize1;
	}
	
	public void setPageSize1(String pageSize1) {
		this.pageSize1 = pageSize1;
	}
	
	public String getPageSize2() {
		return pageSize2;
	}
	
	public void setPageSize2(String pageSize2) {
		this.pageSize2 = pageSize2;
	}
	
	public List<ViewAttendance> getViewAttenList() {
		return viewAttenList;
	}
	
	public void setViewAttenList(List<ViewAttendance> viewAttenList) {
		this.viewAttenList = viewAttenList;
	}
	
	public List<AttendanceDetailsListVO> getAttendanceDetailsListVOs() {
		return attendanceDetailsListVOs;
	}
	
	public void setAttendanceDetailsListVOs(
			List<AttendanceDetailsListVO> attendanceDetailsListVOs) {
		this.attendanceDetailsListVOs = attendanceDetailsListVOs;
	}
	
	public AttendanceSearchVO getAttendanceSearchVO() {
		return attendanceSearchVO;
	}
	
	public void setAttendanceSearchVO(AttendanceSearchVO attendanceSearchVO) {
		this.attendanceSearchVO = attendanceSearchVO;
	}
	
	public List<StudentAttendanceList> getStudentList() {
		return studentList;
	}
	
	public void setStudentList(List<StudentAttendanceList> studentList) {
		this.studentList = studentList;
	}
	
	public String getNoOfSessions() {
		return noOfSessions;
	}
	
	public void setNoOfSessions(String noOfSessions) {
		this.noOfSessions = noOfSessions;
	}
	
	public List<AttendanceListVO> getAttendanceListVOs() {
		return attendanceListVOs;
	}
	
	public void setAttendanceListVOs(List<AttendanceListVO> attendanceListVOs) {
		this.attendanceListVOs = attendanceListVOs;
	}
	
	public List<StudentAttendance> getStudentAttenList() {
		return studentAttenList;
	}
	
	public void setStudentAttenList(List<StudentAttendance> studentAttenList) {
		this.studentAttenList = studentAttenList;
	}
	
	public String getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
}
