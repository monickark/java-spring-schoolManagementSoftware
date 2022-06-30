package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class AddMarksListKey implements Serializable {

	// Logging
	Logger logger = Logger.getLogger(AddMarksListKey.class);

	// Properties	
	private String instId;
	private String branchId;
	private String acTerm;
	private String mkslId;
	private String course;
	private String standard;
	private String sec;
	private String subType ;
	private String crslId = "";
	private String studentGrpId = "";
	private String labBatch= "" ;
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
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getMkslId() {
		return mkslId;
	}
	public void setMkslId(String mkslId) {
		this.mkslId = mkslId;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	
	public String getSec() {
		return sec;
	}
	public void setSec(String sec) {
		this.sec = sec;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getCrslId() {
		return crslId;
	}
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	public String getStudentGrpId() {
		return studentGrpId;
	}
	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	public String getLabBatch() {
		return labBatch;
	}
	public void setLabBatch(String labBatch) {
		this.labBatch = labBatch;
	}
	@Override
	public String toString() {
		return "AddMarksListKey [instId=" + instId + ", branchId=" + branchId
				+ ", acTerm=" + acTerm + ", mkslId=" + mkslId + ", course="
				+ course + ", standard=" + standard + ", sec=" + sec
				+ ", subType=" + subType + ", crslId=" + crslId
				+ ", studentGrpId=" + studentGrpId + ", labBatch=" + labBatch
				+ "]";
	}
	
	
	
	
}
