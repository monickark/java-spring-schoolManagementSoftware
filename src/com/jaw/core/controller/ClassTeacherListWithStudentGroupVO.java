package com.jaw.core.controller;

import java.io.Serializable;

public class ClassTeacherListWithStudentGroupVO implements Serializable {
	private String staffId;
	private String sgId;
	private String standard;
	private String combination;
	private String section;

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getSgId() {
		return sgId;
	}

	public void setSgId(String sgId) {
		this.sgId = sgId;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getCombination() {
		return combination;
	}

	public void setCombination(String combination) {
		this.combination = combination;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

}
