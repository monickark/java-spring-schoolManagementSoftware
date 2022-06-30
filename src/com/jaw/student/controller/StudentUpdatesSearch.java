package com.jaw.student.controller;

public class StudentUpdatesSearch {
	private String instId = "";
	private String branchId = "";
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
	public String getAcademicYear() {
		return academicYear;
	}
	@Override
	public String toString() {
		return "StudentUpdatesSearch [instId=" + instId + ", branchId="
				+ branchId + ", academicYear=" + academicYear + ", stuGrpId="
				+ stuGrpId + ", stuUpdateCat=" + stuUpdateCat + "]";
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getStuGrpId() {
		return stuGrpId;
	}
	public String getStuUpdateCat() {
		return stuUpdateCat;
	}
	public void setStuUpdateCat(String stuUpdateCat) {
		this.stuUpdateCat = stuUpdateCat;
	}
	public void setStuGrpId(String stuGrpId) {
		this.stuGrpId = stuGrpId;
	}
	private String academicYear = "";
	private String stuGrpId = "";
	private String stuUpdateCat = "";
}
