package com.jaw.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class TeacherSubjectLinkMasterVO {
	// Logging
	Logger logger = Logger.getLogger(TeacherSubjectLinkMasterVO.class);

	// Properties
	private String pageSize = "10";
	private int rowid;
	private List<TeacherSubjectLinkVO> teaSubLinkVOList = new ArrayList<TeacherSubjectLinkVO>();
	private TeacherSubjectLinkVO teacSubLinkVO = new TeacherSubjectLinkVO();
	TeacherSubjectLinkSearchVO teacherSubjectLinkSearchVO=new TeacherSubjectLinkSearchVO();
	

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

	public List<TeacherSubjectLinkVO> getTeaSubLinkVOList() {
		return teaSubLinkVOList;
	}

	public void setTeaSubLinkVOList(List<TeacherSubjectLinkVO> teaSubLinkVOList) {
		this.teaSubLinkVOList = teaSubLinkVOList;
	}

	public TeacherSubjectLinkVO getTeacSubLinkVO() {
		return teacSubLinkVO;
	}

	public void setTeacSubLinkVO(TeacherSubjectLinkVO teacSubLinkVO) {
		this.teacSubLinkVO = teacSubLinkVO;
	}

	

	public TeacherSubjectLinkSearchVO getTeacherSubjectLinkSearchVO() {
		return teacherSubjectLinkSearchVO;
	}

	public void setTeacherSubjectLinkSearchVO(
			TeacherSubjectLinkSearchVO teacherSubjectLinkSearchVO) {
		this.teacherSubjectLinkSearchVO = teacherSubjectLinkSearchVO;
	}

}
