package com.jaw.analytics.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.attendance.controller.ViewAttendanceSearchVO;

public class MarkAnalyticSearchVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(MarkAnalyticSearchVO.class);
	private String instId;
	private String branchId;
	private String crslId="";
	private String admissionNumber="";
	private String studentGroupId="";
	private String academicTerm = "";
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
	public String getCrslId() {
		return crslId;
	}
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	public String getAdmissionNumber() {
		return admissionNumber;
	}
	public void setAdmissionNumber(String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}
	public String getStudentGroupId() {
		return studentGroupId;
	}
	public void setStudentGroupId(String studentGroupId) {
		this.studentGroupId = studentGroupId;
	}
	public String getAcademicTerm() {
		return academicTerm;
	}
	public void setAcademicTerm(String academicTerm) {
		this.academicTerm = academicTerm;
	}
	@Override
	public String toString() {
		return "MarkAnalyticSearchVO [instId=" + instId + ", branchId="
				+ branchId + ", crslId=" + crslId + ", admissionNumber="
				+ admissionNumber + ", studentGroupId=" + studentGroupId
				+ ", academicTerm=" + academicTerm + "]";
	}

}
