package com.jaw.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class StudentGroupMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(StudentGroupMasterVO.class);

	// Properties
	private String pageSize = "10";
	private int rowid;
	private List<StudentGroupVO> studentMasterVOList = new ArrayList<StudentGroupVO>();
	private StudentGroupVO studentMtrVo = new StudentGroupVO();
	private String courseMasterId ;

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

	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

	public List<StudentGroupVO> getStudentMasterVOList() {
		return studentMasterVOList;
	}

	public void setStudentMasterVOList(List<StudentGroupVO> studentMasterVOList) {
		this.studentMasterVOList = studentMasterVOList;
	}

	public StudentGroupVO getStudentMtrVo() {
		return studentMtrVo;
	}

	public void setStudentMtrVo(StudentGroupVO studentMtrVo) {
		this.studentMtrVo = studentMtrVo;
	}
}
