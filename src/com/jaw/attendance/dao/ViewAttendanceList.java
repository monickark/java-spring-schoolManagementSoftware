package com.jaw.attendance.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

//Attendance Pojo class
public class ViewAttendanceList implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(ViewAttendanceList.class);
	private String instId;
	private String branchId;
	private String crslId;
	private String admissionNumber;
	private String isAbsent;
	private String remark;
	private String date;
	private String studentGroupId;
	private String subId;
	private String classType;
	private String academicTerm = "";
	private String studentName = "";
	private String sattSeqNo;
	private String subType;
	private String noOfSession;
	
	public String getNoOfSession() {
		return noOfSession;
	}
	
	public void setNoOfSession(String noOfSession) {
		this.noOfSession = noOfSession;
	}
	
	public String getSattSeqNo() {
		return sattSeqNo;
	}
	
	public void setSattSeqNo(String sattSeqNo) {
		this.sattSeqNo = sattSeqNo;
	}
	
	public String getSubType() {
		return subType;
	}
	
	public void setSubType(String subType) {
		this.subType = subType;
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
	
	public String getAcademicTerm() {
		return academicTerm;
	}
	
	public void setAcademicTerm(String academicTerm) {
		this.academicTerm = academicTerm;
	}
	
	public String getIsAbsent() {
		return isAbsent;
	}
	
	public void setIsAbsent(String isAbsent) {
		this.isAbsent = isAbsent;
	}
	
	@Override
	public String toString() {
		return "AttendanceList [getDate()=" + getDate() + ", getStudentGroupId()="
				+ getStudentGroupId() + ", getSubId()=" + getSubId() + ", getClassType()="
				+ getClassType() + ", getAdmissionNumber()=" + getAdmissionNumber()
				+ ", getCrslId()=" + getCrslId() + ", getRemark()=" + getRemark()
				+ ", getInstId()=" + getInstId() + ", getBranchId()=" + getBranchId()
				+ ", getAcademicTerm()=" + getAcademicTerm() + ", getIsPresent()=" + getIsAbsent()
				+ "]";
	}
	
}
