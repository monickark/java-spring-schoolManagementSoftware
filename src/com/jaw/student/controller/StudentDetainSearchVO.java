package com.jaw.student.controller;

import java.util.Map;

public class StudentDetainSearchVO {
		
	public int getRowid() {
		return rowid;
	}
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	private int rowid;
	private String pageNo = "";
	
	private String instId = "";
	private String branchId = "";
	private String academicYear = "";
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	private String stuGrpId = "";
	private Map<String,String> stuGrpListMap ;
	private String course = "";
	
	public String getInstId() {
		return instId;
	}
	public Map<String, String> getStuGrpListMap() {
		return stuGrpListMap;
	}
	public void setStuGrpListMap(Map<String, String> stuGrpListMap) {
		this.stuGrpListMap = stuGrpListMap;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getBranchId() {
		return branchId;
	}
	public String getStuGrpId() {
		return stuGrpId;
	}
	public void setStuGrpId(String stuGrpId) {
		this.stuGrpId = stuGrpId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	@Override
	public String toString() {
		return "StudentDetainSearchVO [instId=" + instId + ", branchId="
				+ branchId + ", academicYear=" + academicYear + ", stuGrpId="
				+ stuGrpId + "]";
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}	
}
