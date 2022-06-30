package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;


public class StudentMasterForFeeGen implements Serializable{

	Logger logger = Logger.getLogger(StudentMasterForFeeGen.class);

	private String studentAdmisNo;
	private String isNewAdmission="";
	private String studentName = "";	
	private String elective1 = "";
	private String elective2 = "";
	private String courseVariant = "";
	private Integer rollno;
	
	public String getIsNewAdmission() {
		return isNewAdmission;
	}
	public void setIsNewAdmission(String isNewAdmission) {
		this.isNewAdmission = isNewAdmission;
	}
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getElective1() {
		return elective1;
	}
	public void setElective1(String elective1) {
		this.elective1 = elective1;
	}
	public String getElective2() {
		return elective2;
	}
	public void setElective2(String elective2) {
		this.elective2 = elective2;
	}
	public Integer getRollno() {
		return rollno;
	}
	public void setRollno(Integer rollno) {
		this.rollno = rollno;
	}
	
	public String getCourseVariant() {
		return courseVariant;
	}
	public void setCourseVariant(String courseVariant) {
		this.courseVariant = courseVariant;
	}
	@Override
	public String toString() {
		return "StudentMasterForFeeGen [studentAdmisNo=" + studentAdmisNo
				+ ", studentName=" + studentName + ", elective1=" + elective1
				+ ", elective2=" + elective2 + ", courseVariant="
				+ courseVariant + ", rollno=" + rollno + "]";
	}

}
