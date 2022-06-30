package com.jaw.mark.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

//CourseClasses Pojo class
public class StuMrksRmksVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(StuMrksRmksVO.class);
	
	private String acTerm = "";
	private String studentGrpId = "";
	private String examId = "";
	private String studentAdmisNo = "";
	private String attendance = "";
	private String forWholeClass = "";
	private String remarks = "";
	private String button = "";
	private String action = "";
	private int rowid;
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getButton() {
		return button;
	}
	
	public void setButton(String button) {
		this.button = button;
	}
	
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	
	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	
	public void setExamId(String examId) {
		this.examId = examId;
	}
	
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	
	public void setForWholeClass(String forWholeClass) {
		this.forWholeClass = forWholeClass;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public int getRowid() {
		return rowid;
	}
	
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	
	public String getAcTerm() {
		return acTerm;
	}
	
	public String getStudentGrpId() {
		return studentGrpId;
	}
	
	public String getExamId() {
		return examId;
	}
	
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	
	public String getAttendance() {
		return attendance;
	}
	
	public String getForWholeClass() {
		return forWholeClass;
	}
	
	@Override
	public String toString() {
		return "StuMrksRmksVO [logger=" + logger + ", acTerm=" + acTerm + ", studentGrpId="
				+ studentGrpId + ", examId=" + examId + ", studentAdmisNo=" + studentAdmisNo
				+ ", attendance=" + attendance + ", forWholeClass=" + forWholeClass + ", rowid="
				+ rowid + "]";
	}
	
}
