package com.jaw.core.controller;

import org.apache.log4j.Logger;


public class StudentGroupVO {
	// Logging
	Logger logger = Logger.getLogger(StudentGroupVO.class);

	// Properties
	private String studentGrp;
	private String termId = "";
	private String secId = "";
	private String medium = "";
	private String courseMasterId;
	private String studentGrpId ;
	private int rowId;

	

	public String getCourseMasterId() {
		return courseMasterId;
	}

	public void setCourseMasterId(String courseMasterId) {
		this.courseMasterId = courseMasterId;
	}

	public String getStudentGrp() {
		return studentGrp;
	}

	public void setStudentGrp(String studentGrp) {
		this.studentGrp = studentGrp;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getStudentGrpId() {
		return studentGrpId;
	}

	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	@Override
	public String toString() {
		return "StudentGroupVO [studentGrp=" + studentGrp + ", termId="
				+ termId + ", secId=" + secId + ", medium=" + medium
				+ ", courseMasterId=" + courseMasterId + ", studentGrpId="
				+ studentGrpId + ", rowId=" + rowId + "]";
	}



}
