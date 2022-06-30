package com.jaw.user.controller;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

@Component
public class UserCreationVO implements Serializable {
	
	private String userId;
	private String password;
	private String instId;
	private String branchId;
	private String menuProfile;
	private String profileGroup;
	private String linkId;
	private String role;
	private ApplicationCache applicationCache;
	private UserSessionDetails userSessionDetails;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
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
	
	public String getMenuProfile() {
		return menuProfile;
	}
	
	public void setMenuProfile(String menuProfile) {
		this.menuProfile = menuProfile;
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
	
	public ApplicationCache getApplicationCache() {
		return applicationCache;
	}
	
	public void setApplicationCache(ApplicationCache applicationCache) {
		this.applicationCache = applicationCache;
	}
	
	public UserSessionDetails getUserSessionDetails() {
		return userSessionDetails;
	}
	
	public void setUserSessionDetails(UserSessionDetails userSessionDetails) {
		this.userSessionDetails = userSessionDetails;
	}
	
}
