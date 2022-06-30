package com.jaw.student.controller;

import java.io.Serializable;
import java.util.List;

import com.jaw.student.admission.dao.StudentMaster;

public class StudentAllocationVO implements Serializable {
	private String academicYear;
	private String standard;
	private String section;
	private String combination;
	private List<StudentMaster> studentMaster;
	private String studentlist;
	private String course;

	

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getStudentlist() {
		return studentlist;
	}

	public void setStudentlist(String studentlist) {
		this.studentlist = studentlist;
	}

	public List<StudentMaster> getStudentMaster() {
		return studentMaster;
	}

	public void setStudentMaster(List<StudentMaster> studentMaster) {
		this.studentMaster = studentMaster;
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

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getCombination() {
		return combination;
	}

	public void setCombination(String combination) {
		this.combination = combination;
	}
	
	@Override
	public String toString() {
		return "StudentAllocationVO [academicYear=" + academicYear
				+ ", standard=" + standard + ", section=" + section
				+ ", combination=" + combination + ", studentMaster="
				+ studentMaster + ", studentlist=" + studentlist + ", course="
				+ course + "]";
	}

}
