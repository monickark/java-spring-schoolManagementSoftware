package com.jaw.staff.controller;

import java.io.Serializable;

public class StaffListVo implements Serializable {
	private String staffId = "";
	private String staffName = "";
	private String deptId = "";
	private String designation = "";
	private String staffCategory1 = "";
	private String staffCategory2 = "";
	private String mobile = "";
	private String emergency = "";
	private int rowId;
	private String pageNo = "10";
	private String menuProfile;
	
	public String getMenuProfile() {
		return menuProfile;
	}
	
	public void setMenuProfile(String menuProfile) {
		this.menuProfile = menuProfile;
	}
	
	public String getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getRowId() {
		return rowId;
	}
	
	public void setRowId(int rowId) {
		this.rowId = rowId;
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
