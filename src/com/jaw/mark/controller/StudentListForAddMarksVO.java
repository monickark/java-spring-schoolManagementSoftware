package com.jaw.mark.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.attendance.dao.StudentAttendanceList;

public class StudentListForAddMarksVO implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(StudentListForAddMarksVO.class);
	// Properties
	private String studentAdmisNo ;		
	private String rollNumber;
	private String regNo;
	private String studentName;
	private String marksObtd=" ";
	private String gradeObtd=" ";
	private String subRemarks=" ";
	private String attendance="";
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
	public String getMarksObtd() {
		return marksObtd;
	}
	public void setMarksObtd(String marksObtd) {
		this.marksObtd = marksObtd;
	}
	public String getGradeObtd() {
		return gradeObtd;
	}
	public void setGradeObtd(String gradeObtd) {
		this.gradeObtd = gradeObtd;
	}
	public String getSubRemarks() {
		return subRemarks;
	}
	public void setSubRemarks(String subRemarks) {
		this.subRemarks = subRemarks;
	}
	public String getAttendance() {
		return attendance;
	}
	public void setAttendance(String attendance) {
		this.attendance = attendance;
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
		return "StudentListForAddMarksVO [studentAdmisNo=" + studentAdmisNo
				+ ", rollNumber=" + rollNumber + ", regNo=" + regNo
				+ ", studentName=" + studentName + ", marksObtd=" + marksObtd
				+ ", gradeObtd=" + gradeObtd + ", subRemarks=" + subRemarks
				+ ", attendance=" + attendance + ", rowId=" + rowId + "]";
	}


}
