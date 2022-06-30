package com.jaw.attendance.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class ViewAttendanceKey implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(ViewAttendanceKey.class);
	// Properties
	private String instId;
	private String branchId;
	private String acTerm ;
	private String studentAdmisNo ;		
	private String studentGrpId = "" ;
	private String classType = "" ;
	private String crslId = "" ;
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
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	public String getStudentGrpId() {
		return studentGrpId;
	}
	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	
	public String getCrslId() {
		return crslId;
	}
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	@Override
	public String toString() {
		return "ViewAttendanceKey [instId=" + instId + ", branchId=" + branchId
				+ ", acTerm=" + acTerm + ", studentAdmisNo=" + studentAdmisNo
				+ ", studentGrpId=" + studentGrpId + ", classType=" + classType
				+ ", crslId=" + crslId + "]";
	}
	

}
