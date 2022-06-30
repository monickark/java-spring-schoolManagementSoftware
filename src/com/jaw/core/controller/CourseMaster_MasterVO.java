package com.jaw.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class CourseMaster_MasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(CourseMaster_MasterVO.class);

	// Properties
	private String pageSize = "10";
	private int rowid;
	private List<CourseMasterVO> crseMtrVOList = new ArrayList<CourseMasterVO>();
	private CourseMasterVO crsMtrO = new CourseMasterVO();

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

	public List<CourseMasterVO> getCrseMtrVOList() {
		return crseMtrVOList;
	}

	public void setCrseMtrVOList(List<CourseMasterVO> crseMtrVOList) {
		this.crseMtrVOList = crseMtrVOList;
	}

	public CourseMasterVO getCrsMtrO() {
		return crsMtrO;
	}

	public void setCrsMtrO(CourseMasterVO crsMtrO) {
		this.crsMtrO = crsMtrO;
	}

}
