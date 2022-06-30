package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class SpecialClassListKey implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(SpecialClassListKey.class);
	 
	// Properties
	 private Integer dbTs;
		private String instId;
		private String branchId;
	 private String acTerm;
		private String scDate;
		private String studentGrpId;
		private String crslId ;
	
	
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
	public String getScDate() {
		return scDate;
	}
	public void setScDate(String scDate) {
		this.scDate = scDate;
	}
	public String getCrslId() {
		return crslId;
	}
	public void setCrslId(String crslId) {
		this.crslId = crslId;
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
	@Override
	public String toString() {
		return "SpecialClassListKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acTerm=" + acTerm + ", scDate="
				+ scDate + ", studentGrpId=" + studentGrpId + ", crslId="
				+ crslId + "]";
	}
}
