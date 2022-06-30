package com.jaw.mark.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

//CourseClasses Pojo class
public class StuDetailsListForRemarksVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(StuDetailsListForRemarksVO.class);
	
	private String acTerm = "";
	private String studentGrpId = "";
	private String examId = "";
	private String studentAdmisNo = "";
	private String attendance = "";
	private String rollNumber = "";
	private String studentName = "";
	private String standard = "";
	private String section = "";
	private String dob = "";
	
	public String getAcTerm() {
		return acTerm;
	}
	
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	
	public String getStudentGrpId() {
		return studentGrpId;
	}
	
	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	
	public String getExamId() {
		return examId;
	}
	
	public void setExamId(String examId) {
		this.examId = examId;
	}
	
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	
	public String getAttendance() {
		return attendance;
	}
	
	public void setAttendance(String attendance) {
		this.attendance = attendance;
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
	
	public String getStandard() {
		return standard;
	}
	
	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	public String getSection() {
		return section;
	}
	
	public void setSection(String section) {
		this.section = section;
	}
	
	public String getDob() {
		return dob;
	}
	
	public void setDob(String dob) {
		this.dob = dob;
	}
	
}
