package com.jaw.staff.dao;

public class StaffInformationList {
	private String instId;
	private String branchId;
	private String staffId;
	private String staffName = "";
	private String deptId = "";
	private String designation = "";
	private String staffCategory1 = "";
	private String staffCategory2 = "";
	private String mobile = "";
	private String emergency = "";
	private String menuProfile;
	
	public String getMenuProfile() {
		return menuProfile;
	}
	
	public void setMenuProfile(String menuProfile) {
		this.menuProfile = menuProfile;
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
	
	public String getStaffId() {
		return staffId;
	}
	
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	public String getStaffName() {
		return staffName;
	}
	
	public void setStaffName(String staffName) {
		this.staffName = staffName;
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
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getEmergency() {
		return emergency;
	}
	
	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}
	
}
