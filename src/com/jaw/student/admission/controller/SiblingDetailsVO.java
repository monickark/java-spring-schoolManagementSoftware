package com.jaw.student.admission.controller;
import java.io.Serializable;
public class SiblingDetailsVO implements Serializable{
	private String serialNoForExcelUpdate = "";
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String studentAdmisNo = "";			
	private String siblingNo = "";
	private String siblingName = "";
	private String siblingClass = "";
	private String siblingYear = "";
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

	private String siblingAdmisNo;	

	
	public String getSerialNoForExcelUpdate() {
		return serialNoForExcelUpdate;
	}

	public void setSerialNoForExcelUpdate(String serialNoForExcelUpdate) {
		this.serialNoForExcelUpdate = serialNoForExcelUpdate;
	}

	public Integer getDbTs() {
		return dbTs;
	}

	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}
	

	public String getSiblingNo() {
		return siblingNo;
	}

	public void setSiblingNo(String siblingNo) {
		this.siblingNo = siblingNo;
	}

	public String getSiblingName() {
		return siblingName;
	}

	public void setSiblingName(String siblingName) {
		this.siblingName = siblingName;
	}

	public String getSiblingClass() {
		return siblingClass;
	}

	public void setSiblingClass(String siblingClass) {
		this.siblingClass = siblingClass;
	}

	public String getSiblingYear() {
		return siblingYear;
	}

	public void setSiblingYear(String siblingYear) {
		this.siblingYear = siblingYear;
	}

	@Override
	public String toString() {
		return "SiblingDetailsVO [serialNoForExcelUpdate="
				+ serialNoForExcelUpdate + ", dbTs=" + dbTs + ", instId="
				+ instId + ", branchId=" + branchId + ", studentAdmisNo="
				+ studentAdmisNo + ", siblingNo=" + siblingNo
				+ ", siblingName=" + siblingName + ", siblingClass="
				+ siblingClass + ", siblingYear=" + siblingYear
				+ ", siblingAdmisNo=" + siblingAdmisNo + "]";
	}

	public String getSiblingAdmisNo() {
		return siblingAdmisNo;
	}

	public void setSiblingAdmisNo(String siblingAdmisNo) {
		this.siblingAdmisNo = siblingAdmisNo;
	}

}
