package com.jaw.core.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.core.dao.SpecialClass;

public class SpecialClassVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(SpecialClassVO.class);

	// Properties
	private String acTerm;
	private String scItemId;
	private String scDate;
	private String studentGrpId;
	private String studentGrpName;
	private String crslId="" ;
	private String fromTime = "";
	private String toTime = "";
	private String scRmks;
	private String subName = "";
	private int rowId;

	public String getAcTerm() {
		return acTerm;
	}

	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}

	public String getScItemId() {
		return scItemId;
	}

	public void setScItemId(String scItemId) {
		this.scItemId = scItemId;
	}

	public String getScDate() {
		return scDate;
	}

	public void setScDate(String scDate) {
		this.scDate = scDate;
	}

	public String getStudentGrpId() {
		return studentGrpId;
	}

	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}

	public String getCrslId() {
		return crslId;
	}

	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getScRmks() {
		return scRmks;
	}

	public void setScRmks(String scRmks) {
		this.scRmks = scRmks;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getStudentGrpName() {
		return studentGrpName;
	}

	public void setStudentGrpName(String studentGrpName) {
		this.studentGrpName = studentGrpName;
	}

	@Override
	public String toString() {
		return "SpecialClassVO [acTerm=" + acTerm + ", scItemId=" + scItemId
				+ ", scDate=" + scDate + ", studentGrpId=" + studentGrpId
				+ ", studentGrpName=" + studentGrpName + ", crslId=" + crslId
				+ ", fromTime=" + fromTime + ", toTime=" + toTime + ", scRmks="
				+ scRmks + ", subName=" + subName + ", rowId=" + rowId + "]";
	}

	
	

	
}
