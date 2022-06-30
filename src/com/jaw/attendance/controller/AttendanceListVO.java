package com.jaw.attendance.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

//Attendance Pojo class
public class AttendanceListVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(AttendanceListVO.class);
	private String admissionNumber = "";
	private String name = "";
	private String attendance = "";
	private String remark = "";
	private int rowid;
	private String studentName = "";
	private String isAbsent = "";
	private String stamId = "";
	private String rollNumber = "";
	private String remarkForChange = "";
	private String regNo = "";
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

	public String getRemarkForChange() {
		return remarkForChange;
	}

	public void setRemarkForChange(String remarkForChange) {
		this.remarkForChange = remarkForChange;
	}

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getAdmissionNumber() {
		return admissionNumber;
	}

	public void setAdmissionNumber(String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getIsAbsent() {
		return isAbsent;
	}

	public void setIsAbsent(String isAbsent) {
		this.isAbsent = isAbsent;
	}

	public String getStamId() {
		return stamId;
	}

	public void setStamId(String stamId) {
		this.stamId = stamId;
	}

	@Override
	public String toString() {
		return "AttendanceListVO [logger=" + logger + ", admissionNumber="
				+ admissionNumber + ", name=" + name + ", attendance="
				+ attendance + ", remark=" + remark + ", rowid=" + rowid
				+ ", studentName=" + studentName + ", isAbsent=" + isAbsent
				+ ", stamId=" + stamId + ", rollNumber=" + rollNumber + "]";
	}

}
