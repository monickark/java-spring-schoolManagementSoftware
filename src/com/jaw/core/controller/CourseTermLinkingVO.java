package com.jaw.core.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.core.dao.CourseTermLinking;

public class CourseTermLinkingVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(CourseTermLinkingVO.class);
	
	// Properties	
	private Integer dbTs;
	private String courseMasterId ;
	private String termId ;
	private int termSerialOrder;
	private int rowId;
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}
	public String getCourseMasterId() {
		return courseMasterId;
	}
	public void setCourseMasterId(String courseMasterId) {
		this.courseMasterId = courseMasterId;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public int getTermSerialOrder() {
		return termSerialOrder;
	}
	public void setTermSerialOrder(int termSerialOrder) {
		this.termSerialOrder = termSerialOrder;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	@Override
	public String toString() {
		return "CourseTermLinkingVO [dbTs=" + dbTs + ", courseMasterId="
				+ courseMasterId + ", termId=" + termId + ", termSerialOrder="
				+ termSerialOrder + ", rowId=" + rowId + "]";
	}

}
