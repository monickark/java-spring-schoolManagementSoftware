package com.jaw.student.admission.controller;

public class PreSportParticipationDetailsVO {
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String studentAdmisNo = "";
	private String sportsEntrySerialNo = "";
	private String sportsLevel = "";
	private String partDetails = "";
	public Integer getDbTs() {
		return dbTs;
	}
	@Override
	public String toString() {
		return "PreSportParticipationDetails [dbTs=" + dbTs + ", instId="
				+ instId + ", branchId=" + branchId + ", studentAdmisNo="
				+ studentAdmisNo + ", sportsEntrySerialNo="
				+ sportsEntrySerialNo + ", sportsLevel=" + sportsLevel
				+ ", partDetails=" + partDetails + "]";
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
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	public String getSportsEntrySerialNo() {
		return sportsEntrySerialNo;
	}
	public void setSportsEntrySerialNo(String sportsEntrySerialNo) {
		this.sportsEntrySerialNo = sportsEntrySerialNo;
	}
	public String getSportsLevel() {
		return sportsLevel;
	}
	public void setSportsLevel(String sportsLevel) {
		this.sportsLevel = sportsLevel;
	}
	public String getPartDetails() {
		return partDetails;
	}
	public void setPartDetails(String partDetails) {
		this.partDetails = partDetails;
	}

}
