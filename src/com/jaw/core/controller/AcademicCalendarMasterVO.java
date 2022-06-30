package com.jaw.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class AcademicCalendarMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(AcademicCalendarMasterVO.class);

	// Properties	
	private int rowid;
	private String pageSize = "10";
	private List<AcademicCalendarVO> acadCalVOList = new ArrayList<AcademicCalendarVO>();
	private AcademicCalendarVO acadClVO = new AcademicCalendarVO();
	private AcademicCalendarSearchVO acadClSeaVo=new AcademicCalendarSearchVO();

	
	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

	public List<AcademicCalendarVO> getAcadCalVOList() {
		return acadCalVOList;
	}

	public void setAcadCalVOList(List<AcademicCalendarVO> acadCalVOList) {
		this.acadCalVOList = acadCalVOList;
	}

	public AcademicCalendarVO getAcadClVO() {
		return acadClVO;
	}

	public void setAcadClVO(AcademicCalendarVO acadClVO) {
		this.acadClVO = acadClVO;
	}

	public AcademicCalendarSearchVO getAcadClSeaVo() {
		return acadClSeaVo;
	}

	public void setAcadClSeaVo(AcademicCalendarSearchVO acadClSeaVo) {
		this.acadClSeaVo = acadClSeaVo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	
}
