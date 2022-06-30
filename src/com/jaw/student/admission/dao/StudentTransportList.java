package com.jaw.student.admission.dao;

public class StudentTransportList {
	
	
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String studentAdmisNo = "";
	private String studentName="";
	private String academicYear="";
	private String modeOfTrans="";
	private String vehicleNumber="";
	private String dropPoint="";
	private String pickupPoint="";
	private String pickupAssisSalut="";
	private String pickupAssisName="";
	private String delFlg = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
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
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getModeOfTrans() {
		return modeOfTrans;
	}
	public void setModeOfTrans(String modeOfTrans) {
		this.modeOfTrans = modeOfTrans;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getDropPoint() {
		return dropPoint;
	}
	public void setDropPoint(String dropPoint) {
		this.dropPoint = dropPoint;
	}
	public String getPickupPoint() {
		return pickupPoint;
	}
	public void setPickupPoint(String pickupPoint) {
		this.pickupPoint = pickupPoint;
	}
	public String getPickupAssisSalut() {
		return pickupAssisSalut;
	}
	public void setPickupAssisSalut(String pickupAssisSalut) {
		this.pickupAssisSalut = pickupAssisSalut;
	}
	public String getPickupAssisName() {
		return pickupAssisName;
	}
	public void setPickupAssisName(String pickupAssisName) {
		this.pickupAssisName = pickupAssisName;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	public String getrModId() {
		return rModId;
	}
	public void setrModId(String rModId) {
		this.rModId = rModId;
	}
	public String getrModTime() {
		return rModTime;
	}
	public void setrModTime(String rModTime) {
		this.rModTime = rModTime;
	}
	public String getrCreId() {
		return rCreId;
	}
	public void setrCreId(String rCreId) {
		this.rCreId = rCreId;
	}
	public String getrCreTime() {
		return rCreTime;
	}
	public void setrCreTime(String rCreTime) {
		this.rCreTime = rCreTime;
	}
	public int getRowid() {
		return rowid;
	}
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	@Override
	public String toString() {
		return "StudentTransportList [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", studentAdmisNo="
				+ studentAdmisNo + ", studentName=" + studentName
				+ ", academicYear=" + academicYear + ", modeOfTrans="
				+ modeOfTrans + ", vehicleNumber=" + vehicleNumber
				+ ", dropPoint=" + dropPoint + ", pickupPoint=" + pickupPoint
				+ ", pickupAssisSalut=" + pickupAssisSalut
				+ ", pickupAssisName=" + pickupAssisName + ", delFlg=" + delFlg
				+ ", rModId=" + rModId + ", rModTime=" + rModTime + ", rCreId="
				+ rCreId + ", rCreTime=" + rCreTime + ", rowid=" + rowid + "]";
	}
	
	
}
