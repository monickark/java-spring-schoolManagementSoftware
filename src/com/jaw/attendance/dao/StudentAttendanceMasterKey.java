package com.jaw.attendance.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.core.dao.AcademicCalendarKey;

public class StudentAttendanceMasterKey implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(AcademicCalendarKey.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm ;
	private String status ;
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
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "StudentAttendanceMasterKey [dbTs=" + dbTs + ", instId="
				+ instId + ", branchId=" + branchId + ", acTerm=" + acTerm
				+ ", status=" + status + "]";
	}

}
