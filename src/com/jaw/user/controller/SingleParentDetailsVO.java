package com.jaw.user.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jaw.student.admission.controller.StudentMasterVO;

@Component
public class SingleParentDetailsVO implements Serializable {

	private String userId;
	private String profileGroup;
	private String userEnableFlag;
	private String fatherName;
	private List<StudentMasterVO> studentDetails;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public List<StudentMasterVO> getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(List<StudentMasterVO> studentDetails) {
		this.studentDetails = studentDetails;
	}

}
