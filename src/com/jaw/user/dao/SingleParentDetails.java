package com.jaw.user.dao;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class SingleParentDetails implements Serializable {
	private String userId;
	private String instId;
	private String branchId;
	private String parentId;
	private String academicYear = "";
	private String standard = "";
	private String sec = "";
	private String combination = "";
	private String studentName = "";
	private String fatherName = "";
	private String studentUserId = "";
	private String studentAdmisNo = "";
	
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	
	public void setStudentAdmisNo(String studentAdmissionNumber) {
		studentAdmisNo = studentAdmissionNumber;
	}
	
	public String getStudentUserId() {
		return studentUserId;
	}
	
	public void setStudentUserId(String studentUserId) {
		this.studentUserId = studentUserId;
	}
	
	public String getUserId() {
		return userId;
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
	
	public void setUserId(String userId) {
		this.userId = userId;
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
	
	public String getFatherName() {
		return fatherName;
	}
	
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	
}
