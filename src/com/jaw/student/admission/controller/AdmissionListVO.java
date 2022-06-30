package com.jaw.student.admission.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.student.admission.dao.AdmissionList;

public class AdmissionListVO implements Serializable{
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AdmissionList.class);
	// Properties

	private String admissionNumber = "";
	private String admissionDate = "";
	private String studentName = "";
	private String course= "";
	private String section= "";
	private int rowid;
	
	public int getRowid() {
		return rowid;
	}
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	public String getAdmissionNumber() {
		return admissionNumber;
	}
	public void setAdmissionNumber(String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}
	public String getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	@Override
	public String toString() {
		return "AdmissionListVO [admissionNumber=" + admissionNumber
				+ ", admissionDate=" + admissionDate + ", studentName="
				+ studentName + ", course=" + course + ", section=" + section
				+ ", rowid=" + rowid + ", getRowid()=" + getRowid()
				+ ", getAdmissionNumber()=" + getAdmissionNumber()
				+ ", getAdmissionDate()=" + getAdmissionDate()
				+ ", getStudentName()=" + getStudentName() + ", getCourse()="
				+ getCourse() + ", getSection()=" + getSection()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
