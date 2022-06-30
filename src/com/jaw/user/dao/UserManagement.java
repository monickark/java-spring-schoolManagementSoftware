package com.jaw.user.dao;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class UserManagement implements Serializable {

	private String userId;
	private String instId;
	private String branchId;
	private String profileGroup = "";
	private String userEnableFlag = "";
	private String deleteFlag = "";
	private String rModId = "";
	private String rModTime;
	private String rCreId = "";
	private String rCreTime;
	private Date loginTime;
	private String remarks = "";
	private String userMenuProfile = "";
	private String menuOptionId = "";

	public String getMenuOptionId() {
		return menuOptionId;
	}

	public void setMenuOptionId(String menuOptionId) {
		this.menuOptionId = menuOptionId;
	}

	public String getUserMenuProfile() {
		return userMenuProfile;
	}

	public void setUserMenuProfile(String userMenuProfile) {
		this.userMenuProfile = userMenuProfile;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
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

	public String getProfileGroup() {
		return profileGroup;
	}

	public void setProfileGroup(String userMenuProfile) {
		profileGroup = userMenuProfile;
	}

	public String getUserEnableFlag() {
		return userEnableFlag;
	}

	public void setUserEnableFlag(String userEnableFlag) {
		this.userEnableFlag = userEnableFlag;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getrModId() {
		return rModId;
	}

	public void setrModId(String rModId) {
		this.rModId = rModId;
	}

	public String getrModTime() {
		return rModTime;
	}

	public void setrModTime(String rModTime) {
		this.rModTime = rModTime;
	}

	public String getrCreId() {
		return rCreId;
	}

	public void setrCreId(String rCreId) {
		this.rCreId = rCreId;
	}

	public String getrCreTime() {
		return rCreTime;
	}

	public void setrCreTime(String rCreTime) {
		this.rCreTime = rCreTime;
	}

}
