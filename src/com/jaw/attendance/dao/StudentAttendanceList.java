package com.jaw.attendance.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class StudentAttendanceList implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(StudentAttendanceList.class);
	// Properties
	private String studentAdmisNo ;		
	private String rollNumber;
	private String regNo;
	private String studentName;
	private String attendanceAbsent="";
	private String remarks=" ";
	private int rowId;
	
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getAttendanceAbsent() {
		return attendanceAbsent;
	}
	public void setAttendanceAbsent(String attendanceAbsent) {
		this.attendanceAbsent = attendanceAbsent;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	@Override
	public String toString() {
		return "StudentAttendanceList [studentAdmisNo=" + studentAdmisNo
				+ ", rollNumber=" + rollNumber + ", regNo=" + regNo
				+ ", studentName=" + studentName + ", attendanceAbsent="
				+ attendanceAbsent + ", remarks=" + remarks + ", rowId="
				+ rowId + "]";
	}
	
	
}
