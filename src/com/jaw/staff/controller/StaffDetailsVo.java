
package com.jaw.staff.controller;
import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class StaffDetailsVo implements Serializable{
	
	private String staffName = "";
	private String qualification = "";
	private String noOfYrsExp ;
	private String skills = "";
	private String prevWorkPlace = "";
	private String doj ;
	private String deptId = "";
	private String designation = "";
	private String staffCategory1="";
	private String staffCategory2 = "";
	private String staffRoom = "";
	private String salaryActNo = "";
	private String salary;
	private String transfered="";	
	private String prevTransferedFrom = "";
	private String transferedDate ;
	private String reasonForLeaving = "";
	private String transferDate;
	private String transferedTo = "";
	
	
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getNoOfYrsExp() {
		return noOfYrsExp;
	}
	public void setNoOfYrsExp(String noOfYrsExp) {
		this.noOfYrsExp = noOfYrsExp;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public String getPrevWorkPlace() {
		return prevWorkPlace;
	}
	public void setPrevWorkPlace(String prevWorkPlace) {
		this.prevWorkPlace = prevWorkPlace;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getStaffCategory1() {
		return staffCategory1;
	}
	public void setStaffCategory1(String staffCategory1) {
		this.staffCategory1 = staffCategory1;
	}
	public String getStaffCategory2() {
		return staffCategory2;
	}
	public void setStaffCategory2(String staffCategory2) {
		this.staffCategory2 = staffCategory2;
	}
	public String getStaffRoom() {
		return staffRoom;
	}
	public void setStaffRoom(String staffRoom) {
		this.staffRoom = staffRoom;
	}
	public String getSalaryActNo() {
		return salaryActNo;
	}
	public void setSalaryActNo(String salaryActNo) {
		this.salaryActNo = salaryActNo;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getTransfered() {
		return transfered;
	}
	public void setTransfered(String transfered) {
		this.transfered = transfered;
	}
	public String getPrevTransferedFrom() {
		return prevTransferedFrom;
	}
	public void setPrevTransferedFrom(String prevTransferedFrom) {
		this.prevTransferedFrom = prevTransferedFrom;
	}
	public String getTransferedDate() {
		return transferedDate;
	}
	public void setTransferedDate(String transferedDate) {
		this.transferedDate = transferedDate;
	}
	
	public String getReasonForLeaving() {
		return reasonForLeaving;
	}
	public void setReasonForLeaving(String reasonForLeaving) {
		this.reasonForLeaving = reasonForLeaving;
	}
	public String getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}
	public String getTransferedTo() {
		return transferedTo;
	}
	public void setTransferedTo(String transferedTo) {
		this.transferedTo = transferedTo;
	}
	@Override
	public String toString() {
		return "StaffDetailsVo [staffName=" + staffName + ", qualification="
				+ qualification + ", noOfYrsExp=" + noOfYrsExp + ", skills="
				+ skills + ", prevWorkPlace=" + prevWorkPlace + ", doj=" + doj
				+ ", deptId=" + deptId + ", designation=" + designation
				+ ", staffCategory1=" + staffCategory1 + ", staffCategory2="
				+ staffCategory2 + ", staffRoom=" + staffRoom
				+ ", salaryActNo=" + salaryActNo + ", salary=" + salary
				+ ", transfered=" + transfered + ", prevTransferedFrom="
				+ prevTransferedFrom + ", transferedDate=" + transferedDate
				+ ", reasonForLeaving=" + reasonForLeaving + ", transferDate="
				+ transferDate + ", transferedTo=" + transferedTo + ", files="
				 + "]";
	}
	

}
