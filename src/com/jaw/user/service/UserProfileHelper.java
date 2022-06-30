package com.jaw.user.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaw.common.business.CommonBusiness;
import com.jaw.framework.sessCache.ManagementSession;
import com.jaw.framework.sessCache.NonStaffSession;
import com.jaw.framework.sessCache.ParentSession;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.StaffSession;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.dao.ParentDetails;

@Component
public class UserProfileHelper {
	@Autowired
	CommonBusiness commonBusiness;

	// Parent Profile
	public void getParentProfile(UserSessionDetails userSessionDetails,
			SessionCache sessionCache, List<StudentSession> studentsession,
			ParentDetails parentDetails, InputStream parentPhoto) {

		ParentSession parentSession = new ParentSession();

		try {
			byte[] bytes = IOUtils.toByteArray(parentPhoto);
			userSessionDetails.setUserPhoto(bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}
		parentSession.setParentName(parentDetails.getFatherName());
		parentSession.setStudentSession(studentsession);
		userSessionDetails.setUserName(parentDetails.getFatherName());
		sessionCache.setParentSession(parentSession);
	}

	// Student Profile
	public void getStudentProfile(UserSessionDetails userSessionDetails,
			SessionCache sessionCache, StudentSession studentSession,
			InputStream studentPhoto) {
		try {
			byte[] bytes = IOUtils.toByteArray(studentPhoto);
			userSessionDetails.setUserPhoto(bytes);
			userSessionDetails.setUserName(studentSession.getStudentName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		sessionCache.setStudentSession(studentSession);
	}

	// Admin Profile
	public void getAdminProfile(UserSessionDetails userSessionDetails,
			InputStream inputStream) {

		try {
			byte[] bytes = IOUtils.toByteArray(inputStream);
			userSessionDetails.setUserPhoto(bytes);
			userSessionDetails.setUserName("admin");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Admin Profile
	public void getManagementProfile(UserSessionDetails userSessionDetails,
			SessionCache sessionCache, ManagementSession managementSession,
			InputStream inputStream) {

		try {
			byte[] bytes = IOUtils.toByteArray(inputStream);
			userSessionDetails.setUserPhoto(bytes);
			userSessionDetails.setUserName(managementSession
					.getManagementName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		sessionCache.setManagementSession(managementSession);
	}

	// Admin Profile
	public void getNonStaffProfile(UserSessionDetails userSessionDetails,
			SessionCache sessionCache, NonStaffSession nonStaffSession,
			InputStream inputStream) {

		try {
			byte[] bytes = IOUtils.toByteArray(inputStream);
			userSessionDetails.setUserPhoto(bytes);
			userSessionDetails.setUserName(nonStaffSession.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		sessionCache.setNonStaffSession(nonStaffSession);
	}

	// Staff profile
	public void getStaffProfile(UserSessionDetails userSessionDetails,
			SessionCache sessionCache, StaffSession staffSession,
			InputStream inputStream) {

		try {
			byte[] bytes = IOUtils.toByteArray(inputStream);
			userSessionDetails.setUserPhoto(bytes);
			userSessionDetails.setUserName(staffSession.getStaffName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		sessionCache.setStaffSession(staffSession);
	}

}
