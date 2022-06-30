package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

//CourseClasses Pojo class
public class StuMrksRmksListKey implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(StuMrksRmksListKey.class);
	
	private String acTerm = "";
	private String studentGrpId = "";
	private String examId = "";
	private String studentAdmisNo = "";
	private String instId = "";
	private String branchId = "";
	
	public String getInstId() {
		return instId;
	}
	
	public void setInstId(String instId) {
		this.instId = instId;
	}
	
	public String getBranchId() {
		return branchId;
	}
	
	public void setBranchId(String branchId) {
		this.branchId = branchId;
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
	
	public String getStudentAdmisNo() {
		return studentAdmisNo;
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

	@Override
	public String toString() {
		return "StuMrksRmksListKey [logger=" + logger + ", acTerm=" + acTerm
				+ ", studentGrpId=" + studentGrpId + ", examId=" + examId
				+ ", studentAdmisNo=" + studentAdmisNo + ", instId=" + instId
				+ ", branchId=" + branchId + "]";
	}
	
	
	
}
