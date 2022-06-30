package com.jaw.attendance.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

//Attendance Pojo class
public class AttendanceList implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(AttendanceList.class);
	private String instId = "";
	private String branchId = "";
	private String crslId = "";
	private String admissionNumber = "";
	private String isAbsent = "";
	private String remark = "";
	private String date = "";
	private String studentGroupId = "";
	private String subId = "";
	private String classType = "";
	private String acTerm = "";
	private String studentName = "";
	private String sattSeqNo = "";
	private String courseId = "";
	private String secId = "";
	private String termId = "";
	private String stamId = "";
	private String rollNumber = "";
	private String regNo="";
	private String subType="";
	
	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRollNumber() {
		return rollNumber;
	}
	
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	
	public String getStamId() {
		return stamId;
	}
	
	public void setStamId(String stamId) {
		this.stamId = stamId;
	}
	
	public String getTermId() {
		return termId;
	}
	
	public void setTermId(String termId) {
		this.termId = termId;
	}
	
	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public String getSecId() {
		return secId;
	}
	
	public void setSecId(String secId) {
		this.secId = secId;
	}
	
	public String getSattSeqNo() {
		return sattSeqNo;
	}
	
	public void setSattSeqNo(String sattSeqNo) {
		this.sattSeqNo = sattSeqNo;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getStudentGroupId() {
		return studentGroupId;
	}
	
	public void setStudentGroupId(String studentGroupId) {
		this.studentGroupId = studentGroupId;
	}
	
	public String getSubId() {
		return subId;
	}
	
	public void setSubId(String subId) {
		this.subId = subId;
	}
	
	public String getClassType() {
		return classType;
	}
	
	public void setClassType(String classType) {
		this.classType = classType;
	}
	
	public String getAdmissionNumber() {
		return admissionNumber;
	}
	
	public void setAdmissionNumber(String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}
	
	public String getCrslId() {
		return crslId;
	}
	
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public String getAcTerm() {
		return acTerm;
	}
	
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	
	public String getIsAbsent() {
		return isAbsent;
	}
	
	public void setIsAbsent(String isAbsent) {
		this.isAbsent = isAbsent;
	}
	
	@Override
	public String toString() {
		return "AttendanceList [getStamId()=" + getStamId() + ", getTermId()="
				+ getTermId() + ", getCourseId()=" + getCourseId()
				+ ", getSecId()=" + getSecId() + ", getSattSeqNo()="
				+ getSattSeqNo() + ", getStudentName()=" + getStudentName()
				+ ", getDate()=" + getDate() + ", getStudentGroupId()="
				+ getStudentGroupId() + ", getSubId()=" + getSubId()
				+ ", getClassType()=" + getClassType()
				+ ", getAdmissionNumber()=" + getAdmissionNumber()
				+ ", getCrslId()=" + getCrslId() + ", getRemark()="
				+ getRemark() + ", getInstId()=" + getInstId()
				+ ", getBranchId()=" + getBranchId() + ", getAcademicTerm()="
				+ getAcTerm() + ", getIsAbsent()=" + getIsAbsent() + "]";
	}
}
