package com.jaw.framework.sessCache;

import java.io.Serializable;

public class StudentSession implements Serializable {

	private String studentAdmisNo;
	private String rollNo;
	private String standard;
	private String sec;
	private String combination;
	private String studentName;
	private String course;
	private String stuGrpId;
	private String stuGrpName;

	public String getStuGrpName() {
		return stuGrpName;
	}

	public void setStuGrpName(String stuGrpName) {
		this.stuGrpName = stuGrpName;
	}

	public String getStuGrpId() {
		return stuGrpId;
	}

	public void setStuGrpId(String stuGrpId) {
		this.stuGrpId = stuGrpId;
	}

	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}

	
	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "StudentSession [studentAdmisNo=" + studentAdmisNo + ", rollNo="
				+ rollNo + ", standard=" + standard + ", sec=" + sec
				+ ", combination=" + combination + ", studentName="
				+ studentName + ", course=" + course + ", stuGrpId=" + stuGrpId
				+ "]";
	}

	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getSec() {
		return sec;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}

	public String getCombination() {
		return combination;
	}

	public void setCombination(String combination) {
		this.combination = combination;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

}
