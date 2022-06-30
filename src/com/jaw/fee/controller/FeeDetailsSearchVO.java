package com.jaw.fee.controller;

public class FeeDetailsSearchVO {
	private String admissionNumber="";
	private String studentGroupId="";
	private String academicTerm = "";
	public String getAdmissionNumber() {
		return admissionNumber;
	}
	public void setAdmissionNumber(String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}
	public String getStudentGroupId() {
		return studentGroupId;
	}
	public void setStudentGroupId(String studentGroupId) {
		this.studentGroupId = studentGroupId;
	}
	public String getAcademicTerm() {
		return academicTerm;
	}
	public void setAcademicTerm(String academicTerm) {
		this.academicTerm = academicTerm;
	}
	@Override
	public String toString() {
		return "FeeDetailsSearchVO [admissionNumber=" + admissionNumber
				+ ", studentGroupId=" + studentGroupId + ", academicTerm="
				+ academicTerm + "]";
	}
}
