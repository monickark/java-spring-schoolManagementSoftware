package com.jaw.communication.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class SMSListKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSListKey.class);
	
	// Properties	
	private String instId;
	private String branchId;
	private String acTerm;
	private String studentGrpId;
	private String departmentId;
	private String specificGrp;
	private String genCategory;
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
	public String getStudentGrpId() {
		return studentGrpId;
	}
	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getSpecificGrp() {
		return specificGrp;
	}
	public void setSpecificGrp(String specificGrp) {
		this.specificGrp = specificGrp;
	}
	public String getGenCategory() {
		return genCategory;
	}
	public void setGenCategory(String genCategory) {
		this.genCategory = genCategory;
	}
	@Override
	public String toString() {
		return "SMSListKey [instId=" + instId + ", branchId=" + branchId
				+ ", acTerm=" + acTerm + ", studentGrpId=" + studentGrpId
				+ ", departmentId=" + departmentId + ", specificGrp="
				+ specificGrp + ", genCategory=" + genCategory + "]";
	}
	

}
