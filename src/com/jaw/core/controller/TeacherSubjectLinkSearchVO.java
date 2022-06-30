package com.jaw.core.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class TeacherSubjectLinkSearchVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(TeacherSubjectLinkSearchVO.class);

	// Properties

	private String staffId;
	private String subId = "";
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
	@Override
	public String toString() {
		return "TeacherSubjectLinkSearchVO [staffId=" + staffId + ", subId="
				+ subId + "]";
	}

}
