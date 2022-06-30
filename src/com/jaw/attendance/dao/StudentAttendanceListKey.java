package com.jaw.attendance.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.attendance.controller.AttendanceSearchVO;

public class StudentAttendanceListKey implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(StudentAttendanceListKey.class);

	
	private String attDate;
	private String crslId;
	private String classType;	
	private String studentGroupId;
	private String acTerm = "";
	private String occurNo= "" ;
	private String labBatch= "" ;
	private String instId;
	private String branchId ;
	private String subType ;
	private String studentBatch= "" ;
	public String getAttDate() {
		return attDate;
	}
	public void setAttDate(String attDate) {
		this.attDate = attDate;
	}
	public String getCrslId() {
		return crslId;
	}
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getStudentGroupId() {
		return studentGroupId;
	}
	public void setStudentGroupId(String studentGroupId) {
		this.studentGroupId = studentGroupId;
	}
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getOccurNo() {
		return occurNo;
	}
	public void setOccurNo(String occurNo) {
		this.occurNo = occurNo;
	}
	public String getLabBatch() {
		return labBatch;
	}
	public void setLabBatch(String labBatch) {
		this.labBatch = labBatch;
	}
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
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getStudentBatch() {
		return studentBatch;
	}
	public void setStudentBatch(String studentBatch) {
		this.studentBatch = studentBatch;
	}
	@Override
	public String toString() {
		return "StudentAttendanceListKey [attDate=" + attDate + ", crslId="
				+ crslId + ", classType=" + classType + ", studentGroupId="
				+ studentGroupId + ", acTerm=" + acTerm + ", occurNo="
				+ occurNo + ", labBatch=" + labBatch + ", instId=" + instId
				+ ", branchId=" + branchId + ", subType=" + subType
				+ ", studentBatch=" + studentBatch + "]";
	}
	
	
	
	
	
}
