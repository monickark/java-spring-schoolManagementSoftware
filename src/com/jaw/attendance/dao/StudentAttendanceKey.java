package com.jaw.attendance.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class StudentAttendanceKey implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(StudentAttendanceKey.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String sattSeqNo;	
	private String attDate ;
	private String studentAdmisNo ;	
	private String crslId;
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
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
	public String getSattSeqNo() {
		return sattSeqNo;
	}
	public void setSattSeqNo(String sattSeqNo) {
		this.sattSeqNo = sattSeqNo;
	}
	public String getAttDate() {
		return attDate;
	}
	public void setAttDate(String attDate) {
		this.attDate = attDate;
	}
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	public String getCrslId() {
		return crslId;
	}
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	@Override
	public String toString() {
		return "StudentAttendanceKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", sattSeqNo=" + sattSeqNo
				+ ", attDate=" + attDate + ", studentAdmisNo=" + studentAdmisNo
				+ ", crslId=" + crslId + "]";
	}
	
}
