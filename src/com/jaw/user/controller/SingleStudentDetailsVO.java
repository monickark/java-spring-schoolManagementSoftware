package com.jaw.user.controller;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class SingleStudentDetailsVO implements Serializable {

	private String instId;
	private String branchId;
	private String academicYear;
	private String standard;
	private String sec;
	private String combination;
	private String studentName;
	private String userMenuProfile;
	private String studentUserId;
	private String studentAdmissionNumber;
	private String userId;
	private String userEnableFlag;

	public String getStudentAdmissionNumber() {
		return studentAdmissionNumber;
	}

	public void setStudentAdmissionNumber(String studentAdmissionNumber) {
		this.studentAdmissionNumber = studentAdmissionNumber;
	}

	public String getStudentUserId() {
		return studentUserId;
	}

	public void setStudentUserId(String studentUserId) {
		this.studentUserId = studentUserId;
	}

	public String getUserMenuProfile() {
		return userMenuProfile;
	}

	public void setUserMenuProfile(String userMenuProfile) {
		this.userMenuProfile = userMenuProfile;
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

	public String getSec() {
		return sec;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}

	public String getCombination() {
		return combination;
	}

	public void setCombination(String combination) {
		this.combination = combination;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserEnableFlag() {
		return userEnableFlag;
	}

	public void setUserEnableFlag(String userEnableFlag) {
		this.userEnableFlag = userEnableFlag;
	}

}
