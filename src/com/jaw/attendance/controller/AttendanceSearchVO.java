package com.jaw.attendance.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

//Attendance Pojo class
public class AttendanceSearchVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(AttendanceSearchVO.class);
	private String attDate;
	private String crslId;
	private String classType;
	private String studentGroupId;
	private String subId;
	private String acTerm = "";
	private String occurNo= "" ;
	private String labBatch= "" ;
    
	private String reasonForUpdate = "";
	
	public String getReasonForUpdate() {
		return reasonForUpdate;
	}
	
	public void setReasonForUpdate(String reasonForUpdate) {
		this.reasonForUpdate = reasonForUpdate;
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

	


	public String getStudentGroupId() {
		return studentGroupId;
	}

	public void setStudentGroupId(String studentGroupId) {
		this.studentGroupId = studentGroupId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
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

	public String getAttDate() {
		return attDate;
	}

	public void setAttDate(String attDate) {
		this.attDate = attDate;
	}

	public String getAcTerm() {
		return acTerm;
	}

	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}

	@Override
	public String toString() {
		return "AttendanceSearchVO [attDate=" + attDate + ", crslId=" + crslId
				+ ", classType=" + classType 
				+ ", studentGroupId=" + studentGroupId + ", subId=" + subId
				+ ", acTerm=" + acTerm + ", occurNo=" + occurNo + ", labBatch="
				+ labBatch + "]";
	}

	

	
	
	
	
}
