package com.jaw.communication.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class SMSMemberList implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSMemberList.class);
	
	// Properties
	private String studentName;
	private String parentName;
	private String staffName ;
	private String mobileNum;
	private String admissionNum;
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getAdmissionNum() {
		return admissionNum;
	}
	public void setAdmissionNum(String admissionNum) {
		this.admissionNum = admissionNum;
	}
	@Override
	public String toString() {
		return "SMSMemberList [studentName=" + studentName + ", parentName="
				+ parentName + ", staffName=" + staffName + ", mobileNum="
				+ mobileNum + ", admissionNum=" + admissionNum + "]";
	}


}
