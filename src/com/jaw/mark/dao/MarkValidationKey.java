package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

//CourseClasses Pojo class
public class MarkValidationKey implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(MarkValidationKey.class);
	
	private String acTerm = "";
	private String studentGrpId = "";
	private String examId = "";
	private String studentAdmisNo = "";
	private String status = "";
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
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "MarkValidationKey [logger=" + logger + ", acTerm=" + acTerm + ", studentGrpId="
				+ studentGrpId + ", examId=" + examId + ", studentAdmisNo=" + studentAdmisNo
				+ ", status=" + status + ", instId=" + instId + ", branchId=" + branchId + "]";
	}
	
}
