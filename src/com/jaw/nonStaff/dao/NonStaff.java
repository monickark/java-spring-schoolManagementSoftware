package com.jaw.nonStaff.dao;

import org.springframework.web.multipart.MultipartFile;

import com.jaw.common.constants.AuditConstant;

//Institute Master VO class
public class NonStaff {

	// Properties

	private Integer dbTs;
	private String instId ;
	private String branchId ;
	private String staffId ;
	private String staffName = "";
	private MultipartFile staffPhoto;
	private String communicationAddress1 = "";
	private String communicationAddress2 = "";
	private String communicationAddress3 = "";
	private String communicationCity = "";
	private String communicationPincode = "";
	private String communicationState = "";
	private String permenantAddress1 = "";
	private String permenantAddress2 = "";
	private String permenantAddress3 = "";
	private String permenantCity = "";
	private String permenantPincode = "";
	private String permenantState = "";
	private String email = "";
	private String designation = "";
	private String contact1 = "";
	private String contact2 = "";
	private String emergencyContact="";
	private String gender="";
	private String userId="";
	private String password="";
	private String isStaff="";
	private String profileGroup="";
	private String userEnableFlag="";
	private String isBranchAdmin="";
	private String isEdit="";
	private String ttg="";
	private String profile="";
	private String delFlg="";
	private String rCreId="";
	private String rCreTime="";
	private String rModId="";
	private String rModTime="";
	
	
	public String getTtg() {
		return ttg;
	}
	public void setTtg(String ttg) {
		this.ttg = ttg;
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
	public String getCommunicationCity() {
		return communicationCity;
	}
	public void setCommunicationCity(String communicationCity) {
		this.communicationCity = communicationCity;
	}
	public String getCommunicationPincode() {
		return communicationPincode;
	}
	public void setCommunicationPincode(String communicationPincode) {
		this.communicationPincode = communicationPincode;
	}
	public String getCommunicationState() {
		return communicationState;
	}
	public void setCommunicationState(String communicationState) {
		this.communicationState = communicationState;
	}
	public String getPermenantCity() {
		return permenantCity;
	}
	public void setPermenantCity(String permenantCity) {
		this.permenantCity = permenantCity;
	}
	public String getPermenantPincode() {
		return permenantPincode;
	}
	public void setPermenantPincode(String permenantPincode) {
		this.permenantPincode = permenantPincode;
	}
	public String getPermenantState() {
		return permenantState;
	}
	public void setPermenantState(String permenantState) {
		this.permenantState = permenantState;
	}
	public String getIsStaff() {
		return isStaff;
	}
	public void setIsStaff(String isStaff) {
		this.isStaff = isStaff;
	}
	public String getProfileGroup() {
		return profileGroup;
	}
	public void setProfileGroup(String profileGroup) {
		this.profileGroup = profileGroup;
	}
	public String getUserEnableFlag() {
		return userEnableFlag;
	}
	public void setUserEnableFlag(String userEnableFlag) {
		this.userEnableFlag = userEnableFlag;
	}
	public String getIsBranchAdmin() {
		return isBranchAdmin;
	}
	public void setIsBranchAdmin(String isBranchAdmin) {
		this.isBranchAdmin = isBranchAdmin;
	}
	public String getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}
	public String getPermenantAddress1() {
		return permenantAddress1;
	}
	public void setPermenantAddress1(String permenantAddress1) {
		this.permenantAddress1 = permenantAddress1;
	}
	public String getPermenantAddress2() {
		return permenantAddress2;
	}
	public void setPermenantAddress2(String permenantAddress2) {
		this.permenantAddress2 = permenantAddress2;
	}
	public String getPermenantAddress3() {
		return permenantAddress3;
	}
	public void setPermenantAddress3(String permenantAddress3) {
		this.permenantAddress3 = permenantAddress3;
	}
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}

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
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
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
	

	public MultipartFile getStaffPhoto() {
		return staffPhoto;
	}
	public void setStaffPhoto(MultipartFile staffPhoto) {
		this.staffPhoto = staffPhoto;
	}
	public String getCommunicationAddress1() {
		return communicationAddress1;
	}
	public void setCommunicationAddress1(String communicationAddress1) {
		this.communicationAddress1 = communicationAddress1;
	}
	public String getCommunicationAddress2() {
		return communicationAddress2;
	}
	public void setCommunicationAddress2(String communicationAddress2) {
		this.communicationAddress2 = communicationAddress2;
	}
	public String getCommunicationAddress3() {
		return communicationAddress3;
	}
	public void setCommunicationAddress3(String communicationAddress3) {
		this.communicationAddress3 = communicationAddress3;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getContact1() {
		return contact1;
	}
	public void setContact1(String contact1) {
		this.contact1 = contact1;
	}
	public String getContact2() {
		return contact2;
	}
	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
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
	public String stringForDbAudit() {

		StringBuffer result = new StringBuffer();
		result.append("DB_TS=").append( getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("INST_ID=").append( getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("BRANCH_ID=").append( getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("NON_STAFF_ID=").append( getStaffId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("STAFF_NAME=").append( getStaffName()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("TTG_ID=").append( getTtg()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CURRENT_ADDRESS1=").append( getCommunicationAddress1()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CURRENT_ADDRESS2=").append( getCommunicationAddress2()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CURRENT_ADDRESS3=").append( getCommunicationAddress3()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CURRENT_CITY=").append( getCommunicationCity()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CURRENT_STATE=").append( getCommunicationState()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CURRENT_PINCODE=").append( getCommunicationPincode()).append(AuditConstant.AUDIT_REC_SEPERATOR);

		result.append("PERMENANT_ADDRESS1=").append( getPermenantAddress1()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("PERMENANT_ADDRESS2=").append( getPermenantAddress2()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("PERMENANT_ADDRESS3=").append( getPermenantAddress3()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("PERMENANT_CITY=").append( getPermenantCity()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("PERMENANT_STATE=").append( getPermenantState()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("PERMENANT_PINCODE=").append( getPermenantPincode()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		

		result.append("CONTACT1=").append( getContact1()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CONTACT2=").append( getContact2()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("EMERGENCY_CONTACT=").append( getEmergencyContact()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		
		result.append("EMAIL=").append( getEmail()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("DESIGNATION=").append( getDesignation()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("GENDER=").append( getGender()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("delFlg=").append( getDelFlg()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_ID=").append( getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_TIME=").append( getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_ID=").append( getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_TIME=").append( getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		return result.toString();
	}
	
	
	public String toStringForDBKey() {
		StringBuffer stringBuffer = new StringBuffer()
		.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("NON_STAFF_ID=").append(getStaffId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		return stringBuffer.toString();
	}
	
	
}
