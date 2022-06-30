package com.jaw.student.controller;

import java.util.LinkedHashMap;
import java.util.Map;

public class StudentDetainListVO {
	
	private String stuAdmisNo = "";
	private String academicYear = "";
	private String reasonForUpdating = "";
	private String stuName = "";
	
	public int getRowid() {
		return rowid;
	}
	
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	public String getOldRec() {
		return OldRec;
	}

	public void setOldRec(String oldRec) {
		OldRec = oldRec;
	}
	private String detainRemarks = "";
	private String stuGrpId = "";
	private Map<String,String> stuGrpListMap ;
	private String OldRec = "N";
	private String newRecMoved = "N";
	private String updateOrDelNeeded = "Y";
	private int rowid;
	private String stuGrpName = "";
	
	
	public String getReasonForUpdating() {
		return reasonForUpdating;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public String getNewRecMoved() {
		return newRecMoved;
	}

	public void setNewRecMoved(String newRecMoved) {
		this.newRecMoved = newRecMoved;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public void setReasonForUpdating(String reasonForUpdating) {
		this.reasonForUpdating = reasonForUpdating;
	}
	public String getStuAdmisNo() {
		return stuAdmisNo;
	}
	public void setStuAdmisNo(String stuAdmisNo) {
		this.stuAdmisNo = stuAdmisNo;
	}
	public String getStuName() {
		return stuName;
	}
	public Map<String, String> getStuGrpListMap() {
		return stuGrpListMap;
	}
	public void setStuGrpListMap(Map<String, String> stuGrpListMap) {
		this.stuGrpListMap = stuGrpListMap;
	}	
	public String getUpdateOrDelNeeded() {
		return updateOrDelNeeded;
	}
	public void setUpdateOrDelNeeded(String updateOrDelNeeded) {
		this.updateOrDelNeeded = updateOrDelNeeded;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getDetainRemarks() {
		return detainRemarks;
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
	public String getStuGrpName() {
		return stuGrpName;
	}
	public void setStuGrpName(String stuGrpName) {
		this.stuGrpName = stuGrpName;
	}

	@Override
	public String toString() {
		return "StudentDetainListVO [stuAdmisNo=" + stuAdmisNo
				+ ", academicYear=" + academicYear + ", reasonForUpdating="
				+ reasonForUpdating + ", stuName=" + stuName
				+ ", detainRemarks=" + detainRemarks + ", stuGrpId=" + stuGrpId
				+ ", stuGrpListMap=" + stuGrpListMap + ", OldRec=" + OldRec
				+ ", updateOrDelNeeded=" + updateOrDelNeeded + ", rowid="
				+ rowid + ", stuGrpName=" + stuGrpName + "]";
	}

	
	


}
