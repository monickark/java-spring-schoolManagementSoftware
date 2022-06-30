package com.jaw.student.admission.dao;

public class StudentUpdateList {
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";	
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}
	public String getInstId() {
		return instId;
	}
	
	@Override
	public String toString() {
		return "StudentUpdateList [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", studentAdmisNo="
				+ studentAdmisNo + ", studentName=" + studentName
				+ ", colValue=" + colValue + ", rModId=" + rModId + "]";
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
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	public String getStudentName() {
		return studentName;
	}	
	public String getrModId() {
		return rModId;
	}
	public void setrModId(String rModId) {
		this.rModId = rModId;
	}	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getColValue() {
		return colValue;
	}
	public void setColValue(String colValue) {
		this.colValue = colValue;
	}
	private String studentAdmisNo = "";
	private String studentName = "";
	private String colValue = "";
	private String rModId = "";
	
}
