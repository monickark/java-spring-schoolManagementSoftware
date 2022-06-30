package com.jaw.user.dao;

import org.springframework.stereotype.Component;
import java.io.Serializable;
@Component
public class UserLink implements Serializable{

	private int db_ts;
	private String SerialNoForExcelUpdate="";
	private String userId;
	private String instId;	
	private String branchId="";
	private String profileGroup="";
	private String userMenuProfile="";
	private String linkId="";
	private String role="";
	private String deleteFlag="";
	private String rModId="";
	private String rModTime="";
	private String rCreId="";
	private String rCreTime="";

	public String getSerialNoForExcelUpdate() {
		return SerialNoForExcelUpdate;
	}

	public void setSerialNoForExcelUpdate(String serialNoForExcelUpdate) {
		SerialNoForExcelUpdate = serialNoForExcelUpdate;
	}

	

	public int getDb_ts() {
		return db_ts;
	}

	public void setDb_ts(int db_ts) {
		this.db_ts = db_ts;
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
	
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getProfileGroup() {
		return profileGroup;
	}

	public void setProfileGroup(String profileGroup) {
		this.profileGroup = profileGroup;
	}

	public String getUserMenuProfile() {
		return userMenuProfile;
	}

	public void setUserMenuProfile(String userMenuProfile) {
		this.userMenuProfile = userMenuProfile;
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	@Override
	public String toString() {
		return "UserLink [db_ts=" + db_ts + ", SerialNoForExcelUpdate="
				+ SerialNoForExcelUpdate + ", userId=" + userId + ", instId="
				+ instId + ", branchId=" + branchId + ", profileGroup="
				+ profileGroup + ", userMenuProfile=" + userMenuProfile
				+ ", linkId=" + linkId + ", role=" + role + ", deleteFlag="
				+ deleteFlag + ", rModId=" + rModId + ", rModTime=" + rModTime
				+ ", rCreId=" + rCreId + ", rCreTime=" + rCreTime + "]";
	}

}
