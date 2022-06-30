package com.jaw.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class SpecialClassMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(SpecialClassMasterVO.class);

	// Properties
	
	private int rowid;
	private List<SpecialClassVO> splClsVOList = new ArrayList<SpecialClassVO>();
	private SpecialClassVO splClsVO = new SpecialClassVO();
    private SpecialClassSearchVO specialClassSearchVO=new SpecialClassSearchVO();
    private String pageSize = "10";

	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

	public List<SpecialClassVO> getSplClsVOList() {
		return splClsVOList;
	}

	public void setSplClsVOList(List<SpecialClassVO> splClsVOList) {
		this.splClsVOList = splClsVOList;
	}

	public SpecialClassVO getSplClsVO() {
		return splClsVO;
	}

	public void setSplClsVO(SpecialClassVO splClsVO) {
		this.splClsVO = splClsVO;
	}

	

	public SpecialClassSearchVO getSpecialClassSearchVO() {
		return specialClassSearchVO;
	}

	public void setSpecialClassSearchVO(SpecialClassSearchVO specialClassSearchVO) {
		this.specialClassSearchVO = specialClassSearchVO;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
}
