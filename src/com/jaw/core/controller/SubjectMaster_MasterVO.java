package com.jaw.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class SubjectMaster_MasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(SubjectMaster_MasterVO.class);

	// Properties
	
	private int rowid;
	private List<SubjectMasterVO> subMtrVOList = new ArrayList<SubjectMasterVO>();
	private SubjectMasterVO subjectMasterVO = new SubjectMasterVO();
    private String pageSize = "10";
	public int getRowid() {
		return rowid;
	}
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	public List<SubjectMasterVO> getSubMtrVOList() {
		return subMtrVOList;
	}
	public void setSubMtrVOList(List<SubjectMasterVO> subMtrVOList) {
		this.subMtrVOList = subMtrVOList;
	}
	public SubjectMasterVO getSubjectMasterVO() {
		return subjectMasterVO;
	}
	public void setSubjectMasterVO(SubjectMasterVO subjectMasterVO) {
		this.subjectMasterVO = subjectMasterVO;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

}
