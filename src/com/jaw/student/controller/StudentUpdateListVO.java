package com.jaw.student.controller;

public class StudentUpdateListVO {
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private int rowid;
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}
	public String getInstId() {
		return instId;
	}
	public int getRowid() {
		return rowid;
	}
	public void setRowid(int rowid) {
		this.rowid = rowid;
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
	@Override
	public String toString() {
		return "StudentUpdateListVO [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", rowid=" + rowid
				+ ", studentAdmisNo=" + studentAdmisNo + ", studentName="
				+ studentName + ", colValue=" + colValue + "]";
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
}
