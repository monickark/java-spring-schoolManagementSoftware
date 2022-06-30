package com.jaw.framework.sessCache;

import java.io.Serializable;
import java.util.List;

import com.jaw.framework.appCache.dao.MenuProfileOptionLinking;

public class SessionCache implements Serializable {
	private UserSessionDetails userSessionDetails;
	private StudentSession studentSession;
	private ParentSession parentSession;
	private ManagementSession managementSession;
	private NonStaffSession nonStaffSession;
	private StaffSession staffSession;
	List<MenuProfileOptionLinking> optionLinkings;
	List<MenuProfileOptionLinking> userBreadCrumbs;
	List<String> allowedUrl;

	public StaffSession getStaffSession() {
		return staffSession;
	}

	public void setStaffSession(StaffSession staffSession) {
		this.staffSession = staffSession;
	}

	public NonStaffSession getNonStaffSession() {
		return nonStaffSession;
	}

	public void setNonStaffSession(NonStaffSession nonStaffSession) {
		this.nonStaffSession = nonStaffSession;
	}

	public ManagementSession getManagementSession() {
		return managementSession;
	}

	public void setManagementSession(ManagementSession managementSession) {
		this.managementSession = managementSession;
	}

	public List<String> getAllowedUrl() {
		return allowedUrl;
	}

	public void setAllowedUrl(List<String> allowedUrl) {
		this.allowedUrl = allowedUrl;
	}

	public List<MenuProfileOptionLinking> getUserBreadCrumbs() {
		return userBreadCrumbs;
	}

	public void setUserBreadCrumbs(List<MenuProfileOptionLinking> menus) {
		userBreadCrumbs = menus;
	}

	public List<MenuProfileOptionLinking> getOptionLinkings() {
		return optionLinkings;
	}

	public void setOptionLinkings(List<MenuProfileOptionLinking> optionLinkings) {
		this.optionLinkings = optionLinkings;
	}

	public UserSessionDetails getUserSessionDetails() {
		return userSessionDetails;
	}

	public void setUserSessionDetails(UserSessionDetails userSessionDetails) {
		this.userSessionDetails = userSessionDetails;
	}

	public StudentSession getStudentSession() {
		return studentSession;
	}

	public void setStudentSession(StudentSession studentSession) {
		this.studentSession = studentSession;
	}

	public ParentSession getParentSession() {
		return parentSession;
	}

	public void setParentSession(ParentSession parentSession) {
		this.parentSession = parentSession;
	}
}
