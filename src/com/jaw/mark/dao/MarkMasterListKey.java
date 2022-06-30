package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class MarkMasterListKey implements Serializable {

	// Logging
	Logger logger = Logger.getLogger(MarkMasterListKey.class);
	// Properties	
	private String instId;
	private String branchId;
	private String acTerm;
	private String studentGrpId = "";
	private String examId;
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
	@Override
	public String toString() {
		return "MarkMasterListKey [instId=" + instId + ", branchId=" + branchId
				+ ", acTerm=" + acTerm + ", studentGrpId=" + studentGrpId
				+ ", examId=" + examId + "]";
	}
	

}
