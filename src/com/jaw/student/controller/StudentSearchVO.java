package com.jaw.student.controller;
import java.io.Serializable;
public class StudentSearchVO implements Serializable{
	private String studentAdmisNo = "";
	private String stuGrpId= "";
	private String academicYear = "";
	private String standard = "";
	private String section = "";
	private String combination = "";
	private String casteCategory = "";
	private String allocation = "";
	private String valueId = "";
	private String searchId = "";
	private String instId = "";
	@Override
	public String toString() {
		return "StudentSearchVO [studentAdmisNo=" + studentAdmisNo
				+ ", stuGrpId=" + stuGrpId + ", academicYear=" + academicYear
				+ ", standard=" + standard + ", section=" + section
				+ ", combination=" + combination + ", casteCategory="
				+ casteCategory + ", allocation=" + allocation + ", valueId="
				+ valueId + ", searchId=" + searchId + ", instId=" + instId
				+ ", branchId=" + branchId + ", button=" + button + ", pageNo="
				+ pageNo + ", course=" + course + "]";
	}


	private String branchId = "";
	private String button = "";
	private String pageNo = "10";
	

	private String course = "";

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getStuGrpId() {
		return stuGrpId;
	}

	public void setStuGrpId(String stuGrpId) {
		this.stuGrpId = stuGrpId;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public String getValueId() {
		return valueId;
	}

	public void setValueId(String valueId) {
		this.valueId = valueId;
	}

	public String getCasteCategory() {
		return casteCategory;
	}

	public void setCasteCategory(String casteCategory) {
		this.casteCategory = casteCategory;
	}

	public String getAllocation() {
		return allocation;
	}

	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}

	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}

	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getCombination() {
		return combination;
	}
	

	public void setCombination(String combination) {
		this.combination = combination;
	}

}
