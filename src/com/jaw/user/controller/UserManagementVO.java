package com.jaw.user.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserManagementVO implements Serializable {

	private String userId;
	private String instId;
	private String profileGroup;
	private String userEnableFlag;
	private String rModTime;
	private String rCreTime;
	private Date loginTime;
	private String remarks;
	private List<UserManagementVO> userManagementVOs;
	private ArrayList<String> remarkList;
	private String pageNo = "10";
	private int rowId;
	private String userMenuProfile;
	private String menuId;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getUserMenuProfile() {
		return userMenuProfile;
	}

	public void setUserMenuProfile(String userMenuProfile) {
		this.userMenuProfile = userMenuProfile;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getUserEnableFlag() {
		return userEnableFlag;
	}

	public void setUserEnableFlag(String userEnableFlag) {
		this.userEnableFlag = userEnableFlag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<UserManagementVO> getUserManagementVOs() {
		return userManagementVOs;
	}

	public void setUserManagementVOs(List<UserManagementVO> userManagementVOs2) {
		userManagementVOs = userManagementVOs2;
	}

	public ArrayList<String> getRemarkList() {
		return remarkList;
	}

	public void setRemarkList(ArrayList<String> remarkList) {
		this.remarkList = remarkList;
	}

	public String getProfileGroup() {
		return profileGroup;
	}

	public void setProfileGroup(String profileGroup) {
		this.profileGroup = profileGroup;
	}

	public String getrModTime() {
		return rModTime;
	}

	public void setrModTime(String rModTime) {
		this.rModTime = rModTime;
	}

	public String getrCreTime() {
		return rCreTime;
	}

	public void setrCreTime(String rCreTime) {
		this.rCreTime = rCreTime;
	}

}
