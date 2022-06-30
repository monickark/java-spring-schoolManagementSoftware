package com.jaw.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class AcademicTermMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(AcademicTermMasterVO.class);
	
	private String pageSize = "10";
	private int rowid;
	private List<AcademicTermDetailsVO> academicTermDetailsVOs = new ArrayList<AcademicTermDetailsVO>();
	private AcademicTermDetailsVO academicTermDetailsVO = new AcademicTermDetailsVO();
	
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
	
	public List<AcademicTermDetailsVO> getAcademicTermDetailsVOs() {
		return academicTermDetailsVOs;
	}
	
	public void setAcademicTermDetailsVOs(List<AcademicTermDetailsVO> academicTermDetailsVOs) {
		this.academicTermDetailsVOs = academicTermDetailsVOs;
	}
	
	public AcademicTermDetailsVO getAcademicTermDetailsVO() {
		return academicTermDetailsVO;
	}
	
	public void setAcademicTermDetailsVO(AcademicTermDetailsVO academicTermDetailsVO) {
		this.academicTermDetailsVO = academicTermDetailsVO;
	}
	
}
