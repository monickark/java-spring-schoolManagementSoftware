package com.jaw.staff.dao;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.jaw.common.constants.AuditConstant;

@Component
public class StaffMaster implements Serializable {
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String staffId;
	private String staffName = "";
	private String ttg = "";
	private String bloodGroup = "";
	private String dob = "";
	private String gender = "";
	private String maritalStatus = "";
	private String fhName = "";
	private String religion = "";
	private String casteGroup = "";
	private String casteName = "";
	private String subCasteName = "";
	private String permenantAddress1 = "";
	private String permenantAddress2 = "";
	private String permenantAddress3 = "";
	private String permenantCity = "";
	private String permenantState = "";
	private String permenantPincode = "";
	private String communicationAddress1 = "";
	private String communicationAddress2 = "";
	private String communicationAddress3 = "";
	private String communicationCity = "";
	private String communicationState = "";
	private String communicationPincode = "";
	private String landline = "";
	private String mobile = "";
	private String emergency = "";
	private String designation;
	private String email = "";
	private String panCardNo="";
	private String delFlg = "";
	private String rCreId = "";
	private String rCreTime = "";
	private String rModId = "";
	private String rModTime = "";

	public String getPanCardNo() {
		return panCardNo;
	}

	public void setPanCardNo(String panCardNo) {
		this.panCardNo = panCardNo;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getTtg() {
		return ttg;
	}

	public void setTtg(String ttg) {
		this.ttg = ttg;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getFhName() {
		return fhName;
	}

	public void setFhName(String fhName) {
		this.fhName = fhName;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getCasteGroup() {
		return casteGroup;
	}

	public void setCasteGroup(String casteGroup) {
		this.casteGroup = casteGroup;
	}

	public String getCasteName() {
		return casteName;
	}

	public void setCasteName(String casteName) {
		this.casteName = casteName;
	}

	public String getSubCasteName() {
		return subCasteName;
	}

	public void setSubCasteName(String subCasteName) {
		this.subCasteName = subCasteName;
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

	public String getPermenantCity() {
		return permenantCity;
	}

	public void setPermenantCity(String permenantCity) {
		this.permenantCity = permenantCity;
	}

	public String getPermenantState() {
		return permenantState;
	}

	public void setPermenantState(String permenantState) {
		this.permenantState = permenantState;
	}

	public String getPermenantPincode() {
		return permenantPincode;
	}

	public void setPermenantPincode(String permenantPincode) {
		this.permenantPincode = permenantPincode;
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

	public String getCommunicationCity() {
		return communicationCity;
	}

	public void setCommunicationCity(String communicationCity) {
		this.communicationCity = communicationCity;
	}

	public String getCommunicationState() {
		return communicationState;
	}

	public void setCommunicationState(String communicationState) {
		this.communicationState = communicationState;
	}

	public String getCommunicationPincode() {
		return communicationPincode;
	}

	public void setCommunicationPincode(String communicationPincode) {
		this.communicationPincode = communicationPincode;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getDbTs() {
		return dbTs;
	}

	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
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
		result.append("DB_TS=").append(getDbTs())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("BRANCH_ID=").append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("STAFF_ID=").append(getStaffId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("STAFF_NAME=").append(getStaffName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("TTG_ID=").append(getTtg())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("BLOOD_GROUP=").append(getBloodGroup())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("DOB=").append(getDob())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("GENDER=").append(getGender())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("MARITAL_STATUS=").append(getMaritalStatus())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FH_NAME=").append(getFhName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("RELIGION=").append(getReligion())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CASTE_GROUP=").append(getCasteGroup())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CASTE_NAME=").append(getCasteName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("SUB_CASTE_NAME=").append(getSubCasteName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("PERMENANT_ADDRESS1=").append(getPermenantAddress1())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("PERMENANT_ADDRESS2=").append(getPermenantAddress2())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("PERMENANT_ADDRESS3=").append(getPermenantAddress3())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("PERMENANT_CITY=").append(getPermenantCity())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("PERMENANT_STATE=").append(getPermenantState())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("PERMENANT_PINCODE=").append(getPermenantPincode())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CURRENT_ADDRESS1=").append(getCommunicationAddress1())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CURRENT_ADDRESS2=").append(getCommunicationAddress2())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CURRENT_ADDRESS3=").append(getCommunicationAddress3())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CURRENT_CITY=").append(getCommunicationCity())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CURRENT_STATE=").append(getCommunicationState())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CURRENT_PINCODE=").append(getCommunicationPincode())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("LANDLINE=").append(getLandline())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("MOBILE=").append(getMobile())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("EMERGENCY_CONTACT=").append(getEmergency())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("EMAIL=").append(getEmail())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("delFlg=").append(getDelFlg())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_ID=").append(getrCreId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_TIME=").append(getrCreTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_ID=").append(getrModId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_TIME=").append(getrModTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		return result.toString();
	}

	public String toStringForDBKey() {
		StringBuffer stringBuffer = new StringBuffer().append("DB_TS=")
				.append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_ID=")
				.append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("STAFF_ID=")
				.append(getStaffId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
		return stringBuffer.toString();
	}

}
