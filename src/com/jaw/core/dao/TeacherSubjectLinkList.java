package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

//TeacherSubjectLink Pojo class
public class TeacherSubjectLinkList implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(TeacherSubjectLinkList.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String trslId;
	private String staffId;
	private String delFlag = "";
	private String rModTime = "";
	private String rModId = "";
	
	public String getrModId() {
		return rModId;
	}

	public void setrModId(String rModId) {
		this.rModId = rModId;
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

	public String getTrslId() {
		return trslId;
	}

	public void setTrslId(String trslId) {
		this.trslId = trslId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getrModTime() {
		return rModTime;
	}

	public void setrModTime(String rModTime) {
		this.rModTime = rModTime;
	}

	@Override
	public String toString() {
		return "TeacherSubjectLinkList [logger=" + logger + ", dbTs=" + dbTs
				+ ", instId=" + instId + ", branchId=" + branchId + ", trslId="
				+ trslId + ", staffId=" + staffId + ", delFlag=" + delFlag
				+ "]";
	}

}
