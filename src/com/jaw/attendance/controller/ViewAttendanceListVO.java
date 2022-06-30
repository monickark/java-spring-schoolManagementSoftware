package com.jaw.attendance.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

//Attendance Pojo class
public class ViewAttendanceListVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(ViewAttendanceListVO.class);
	
	private String attendance;
	private int rowid;
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
	private String subDesc;
	private String isAbsentDesc;
	
	public String getSubDesc() {
		return subDesc;
	}
	
	public void setSubDesc(String subDesc) {
		this.subDesc = subDesc;
	}
	
	public String getIsAbsentDesc() {
		return isAbsentDesc;
	}
	
	public void setIsAbsentDesc(String isAbsentDesc) {
		this.isAbsentDesc = isAbsentDesc;
	}
	
	public String getSubType() {
		return subType;
	}
	
	public void setSubType(String subType) {
		this.subType = subType;
	}
	
	public String getNoOfSession() {
		return noOfSession;
	}
	
	public void setNoOfSession(String noOfSession) {
		this.noOfSession = noOfSession;
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
	
	public String getCrslId() {
		return crslId;
	}
	
	public void setCrslId(String crslId) {
		this.crslId = crslId;
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
	
	public String getAcademicTerm() {
		return academicTerm;
	}
	
	public void setAcademicTerm(String academicTerm) {
		this.academicTerm = academicTerm;
	}
	
	public String getSattSeqNo() {
		return sattSeqNo;
	}
	
	public void setSattSeqNo(String sattSeqNo) {
		this.sattSeqNo = sattSeqNo;
	}
	
	public String getIsAbsent() {
		return isAbsent;
	}
	
	public void setIsAbsent(String isAbsent) {
		this.isAbsent = isAbsent;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public String getAdmissionNumber() {
		return admissionNumber;
	}
	
	public void setAdmissionNumber(String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}
	
	public String getAttendance() {
		return attendance;
	}
	
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int getRowid() {
		return rowid;
	}
	
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

	@Override
	public String toString() {
		return "ViewAttendanceListVO [logger=" + logger + ", attendance="
				+ attendance + ", rowid=" + rowid + ", instId=" + instId
				+ ", branchId=" + branchId + ", crslId=" + crslId
				+ ", admissionNumber=" + admissionNumber + ", isAbsent="
				+ isAbsent + ", remark=" + remark + ", date=" + date
				+ ", studentGroupId=" + studentGroupId + ", subId=" + subId
				+ ", classType=" + classType + ", academicTerm=" + academicTerm
				+ ", studentName=" + studentName + ", sattSeqNo=" + sattSeqNo
				+ ", subType=" + subType + ", noOfSession=" + noOfSession
				+ ", subDesc=" + subDesc + ", isAbsentDesc=" + isAbsentDesc
				+ "]";
	}
	
}
