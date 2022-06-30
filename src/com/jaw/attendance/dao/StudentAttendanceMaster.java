package com.jaw.attendance.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class StudentAttendanceMaster implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(StudentAttendanceMaster.class);
	// Properties			 
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String stamId;
	private String acTerm ;
	private String studentGroupId;
	private String crslId= "";
	private String occurNo= "" ;
	private String attDate ;	
	private String shiftId = "" ;
	private int noOfSessions;	
	private String classType= "";
	private String labBatch= "" ;
	private String status;
	private String delFlag = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
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
	public String getStamId() {
		return stamId;
	}
	public void setStamId(String stamId) {
		this.stamId = stamId;
	}
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	
	public String getCrslId() {
		return crslId;
	}
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	public String getOccurNo() {
		return occurNo;
	}
	public void setOccurNo(String occurNo) {
		this.occurNo = occurNo;
	}
	public String getAttDate() {
		return attDate;
	}
	public void setAttDate(String attDate) {
		this.attDate = attDate;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public int getNoOfSessions() {
		return noOfSessions;
	}
	public void setNoOfSessions(int noOfSessions) {
		this.noOfSessions = noOfSessions;
	}
	
	public String getLabBatch() {
		return labBatch;
	}
	public void setLabBatch(String labBatch) {
		this.labBatch = labBatch;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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
	public String getStudentGroupId() {
		return studentGroupId;
	}
	public void setStudentGroupId(String studentGroupId) {
		this.studentGroupId = studentGroupId;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "StudentAttendanceMaster [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", stamId=" + stamId + ", acTerm="
				+ acTerm + ", studentGroupId=" + studentGroupId + ", crslId="
				+ crslId + ", occurNo=" + occurNo + ", attDate=" + attDate
				+ ", shiftId=" + shiftId + ", noOfSessions=" + noOfSessions
				+ ", classType=" + classType + ", labBatch=" + labBatch
				+ ", status=" + status + ", delFlag=" + delFlag + ", rModId="
				+ rModId + ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + "]";
	}
	

}
