package com.jaw.student.controller;

import java.io.Serializable;

public class BatchSectUpdate implements Serializable {
	private String dbts = "";
	private String regno ="";
	private String stuAdmisNo = "";
	private String sec = "";	
	private String stuGrpId = "";
	public String getRegno() {
		return regno;
	}
	public void setRegno(String regno) {
		this.regno = regno;
	}
	public String getStuAdmisNo() {
		return stuAdmisNo;
	}
	@Override
	public String toString() {
		return "BatchSectUpdate [dbts=" + dbts + ", regno=" + regno
				+ ", stuAdmisNo=" + stuAdmisNo + ", sec=" + sec + ", stuGrpId="
				+ stuGrpId + ", rModId=" + rModId + "]";
	}
	public String getDbts() {
		return dbts;
	}
	public void setDbts(String dbts) {
		this.dbts = dbts;
	}
	public void setStuAdmisNo(String stuAdmisNo) {
		this.stuAdmisNo = stuAdmisNo;
	}
	public String getSec() {
		return sec;
	}
	public void setSec(String sec) {
		this.sec = sec;
	}
	public String getStuGrpId() {
		return stuGrpId;
	}
	public void setStuGrpId(String stuGrpId) {
		this.stuGrpId = stuGrpId;
	}
	public String getrModId() {
		return rModId;
	}
	public void setrModId(String rModId) {
		this.rModId = rModId;
	}
	private String rModId = "";

	

}
