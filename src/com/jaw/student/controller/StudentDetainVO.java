package com.jaw.student.controller;

public class StudentDetainVO {

	private String stuAdmisNo = "";
	public String getStuAdmisNo() {
		return stuAdmisNo;
	}
	public void setStuAdmisNo(String stuAdmisNo) {
		this.stuAdmisNo = stuAdmisNo;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getDetainRemarks() {
		return detainRemarks;
	}
	@Override
	public String toString() {
		return "StudentDetain [stuAdmisNo=" + stuAdmisNo + ", stuName="
				+ stuName + ", detainRemarks=" + detainRemarks + ", stuGrpId="
				+ stuGrpId + "]";
	}
	public void setDetainRemarks(String detainRemarks) {
		this.detainRemarks = detainRemarks;
	}
	public String getStuGrpId() {
		return stuGrpId;
	}
	public void setStuGrpId(String stuGrpId) {
		this.stuGrpId = stuGrpId;
	}
	private String stuName = "";
	private String detainRemarks = "";
	private String stuGrpId = "";
	
	
}
