package com.jaw.attendance.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class StudentAttendanceMasterList implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(StudentAttendanceMasterList.class);
	// Properties
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String stamId = "";
	private String acTerm = "";
	private String studentGroupId = "";
	private String crslId = "";
	private String occurNo = "";
	private String attDate = "";
	private String shiftId = "";
	private int noOfSessions;
	private String classType = "";
	private String labBatch = "";
	private String subId = "";
	private String subType = "";
	private String courseId = "";
	private String secId = "";
	private String termId = "";
	private String subName="";
	private String status="";	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getTermId() {
		return termId;
	}
	
	public void setTermId(String termId) {
		this.termId = termId;
	}
	
	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public String getSecId() {
		return secId;
	}
	
	public void setSecId(String secId) {
		this.secId = secId;
	}
	
	public String getSubId() {
		return subId;
	}
	
	public void setSubId(String subId) {
		this.subId = subId;
	}
	
	public String getSubType() {
		return subType;
	}
	
	public void setSubType(String subType) {
		this.subType = subType;
	}
	
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
	
	@Override
	public String toString() {
		return "StudentAttendanceMasterList [logger=" + logger + ", dbTs="
				+ dbTs + ", instId=" + instId + ", branchId=" + branchId
				+ ", stamId=" + stamId + ", acTerm=" + acTerm
				+ ", studentGroupId=" + studentGroupId + ", crslId=" + crslId
				+ ", occurNo=" + occurNo + ", attDate=" + attDate
				+ ", shiftId=" + shiftId + ", noOfSessions=" + noOfSessions
				+ ", classType=" + classType + ", labBatch=" + labBatch
				+ ", subId=" + subId + ", subType=" + subType + ", courseId="
				+ courseId + ", secId=" + secId + ", termId=" + termId + "]";
	}
	
}
