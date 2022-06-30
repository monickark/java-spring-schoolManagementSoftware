package com.jaw.student.admission.controller;
import java.io.Serializable;
public class UserAcces implements Serializable{
	private String createStu = "";
	private String createParent = "";
	private String oldStudent = "";
	private String oldStudentAdmisNo = "";

	public String getCreateStu() {
		return createStu;
	}

	public void setCreateStu(String createStu) {
		this.createStu = createStu;
	}

	public String getCreateParent() {
		return createParent;
	}

	public void setCreateParent(String createParent) {
		this.createParent = createParent;
	}

	public String getOldStudent() {
		return oldStudent;
	}

	public void setOldStudent(String oldStudent) {
		this.oldStudent = oldStudent;
	}

	public String getOldStudentAdmisNo() {
		return oldStudentAdmisNo;
	}

	public void setOldStudentAdmisNo(String oldStudentAdmisNo) {
		this.oldStudentAdmisNo = oldStudentAdmisNo;
	}

}
