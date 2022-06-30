package com.jaw.student.admission.dao;

public class PreSportsPartDetailsKey {
	private String instId = "";
	private String branchId = "";
	private String sportsEntrySerialNo = "";
	public String getInstId() {
		return instId;
	}
	public String getSportsEntrySerialNo() {
		return sportsEntrySerialNo;
	}
	public void setSportsEntrySerialNo(String sportsEntrySerialNo) {
		this.sportsEntrySerialNo = sportsEntrySerialNo;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getBranchId() {
		return branchId;
	}
	@Override
	public String toString() {
		return "PreSportsPartDetailsKey [instId=" + instId + ", branchId="
				+ branchId + ", sportsEntrySerialNo=" + sportsEntrySerialNo
				+ ", studentAdmisNo=" + studentAdmisNo + "]";
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
	private String studentAdmisNo = "";
}
