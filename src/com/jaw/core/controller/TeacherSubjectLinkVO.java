package com.jaw.core.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;


public class TeacherSubjectLinkVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(TeacherSubjectLinkVO.class);

	// Properties

	private String trslId;
	private String staffId;
	private String subId = "";
	private int rowId;

	public String getTrslId() {
		return trslId;
	}

	public void setTrslId(String trslId) {
		this.trslId = trslId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	

	@Override
	public String toString() {
		return "TeacherSubjectLinkVO [trslId=" + trslId + ", staffId="
				+ staffId + ", subId=" + subId + ", rowId=" + rowId + "]";
	}

}
