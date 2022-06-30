package com.jaw.student.admission.controller;

import java.io.Serializable;

public class UserDetailsForSMS implements Serializable{
	private String studentUserId = "";
	private String studentPassword = "";
	private String parentUserId = "";
	private String parentPassword = "";
	public String getStudentUserId() {
		return studentUserId;
	}
	public void setStudentUserId(String studentUserId) {
		this.studentUserId = studentUserId;
	}
	public String getStudentPassword() {
		return studentPassword;
	}
	public void setStudentPassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}
	public String getParentUserId() {
		return parentUserId;
	}
	public void setParentUserId(String parentUserId) {
		this.parentUserId = parentUserId;
	}
	public String getParentPassword() {
		return parentPassword;
	}
	public void setParentPassword(String parentPassword) {
		this.parentPassword = parentPassword;
	}
	@Override
	public String toString() {
		return "UserDetailsForSMS [studentUserId=" + studentUserId
				+ ", studentPassword=" + studentPassword + ", parentUserId="
				+ parentUserId + ", parentPassword=" + parentPassword + "]";
	}

}
