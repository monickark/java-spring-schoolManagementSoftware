package com.jaw.framework.sessCache;

import java.io.Serializable;
import java.util.Arrays;

public class UserSessionDetails implements Serializable {
	private String ipAddress;
	private String userId;
	private String sessionId;
	private String userMenuProfile;
	private String userName;	
	private byte[] userPhoto;
	private String instId;
	private String branchId;
	private String profileGroup;
	private String linkId;
	

	private String role;
	private String currAcTerm;
	private String inbrCategory;	
	
	
		
	
	public String getCurrAcTerm() {
		return currAcTerm;
	}

	public void setCurrAcTerm(String currAcTerm) {
		this.currAcTerm = currAcTerm;
	}
	

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}
	

	public String getInbrCategory() {
		return inbrCategory;
	}

	public void setInbrCategory(String inbrCategory) {
		this.inbrCategory = inbrCategory;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserMenuProfile() {
		return userMenuProfile;
	}

	public void setUserMenuProfile(String userMenuProfile) {
		this.userMenuProfile = userMenuProfile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public byte[] getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(byte[] userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getProfileGroup() {
		return profileGroup;
	}

	public void setProfileGroup(String profileGroup) {
		this.profileGroup = profileGroup;
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

	
	@Override
	public String toString() {
		return "UserSessionDetails [ipAddress=" + ipAddress + ", userId="
				+ userId + ", sessionId=" + sessionId + ", userMenuProfile="
				+ userMenuProfile + ", userName=" + userName + ", userPhoto="
				+ Arrays.toString(userPhoto) + ", instId=" + instId
				+ ", branchId=" + branchId + ", profileGroup=" + profileGroup
				+ ", linkId=" + linkId + ", role=" + role + ", currAcTerm="
				+ currAcTerm + ", inbrCategory=" + inbrCategory + "]";
	}
	
}
