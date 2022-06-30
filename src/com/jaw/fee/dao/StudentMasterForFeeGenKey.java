package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class StudentMasterForFeeGenKey implements Serializable{

	Logger logger = Logger.getLogger(StudentMasterForFeeGenKey.class);
	
	
	private String instId ;
	private String branchId;
	private String academicYear;
	private String standard = "";
	private String course = "";
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
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	@Override
	public String toString() {
		return "StudentMasterForFeeGenKey [instId=" + instId + ", branchId="
				+ branchId + ", academicYear=" + academicYear + ", standard="
				+ standard + ", course=" + course + "]";
	}

}
