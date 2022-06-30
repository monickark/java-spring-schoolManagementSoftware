package com.jaw.mgmtUser.dao;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.jaw.common.constants.AuditConstant;

//Management Pojo class
public class MgmtUser {

	// Logging
	Logger logger = Logger.getLogger(MgmtUser.class);

	// Properties
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String managementId = "";
	private String name = "";
	private MultipartFile managementPhoto;
	private String address1 = "";
	private String address2 = "";
	private String address3 = "";
	private String city = "";
	private String pincode = "";
	private String state = "";
	private String email = "";
	private String designation = "";
	private String contact1 = "";
	private String contact2 = "";
	private String gender = "";
	private String delFlg = "";
	private String rCreId = "";
	private String rCreTime = "";
	private String rModId = "";
	private String rModTime = "";
	private String userId = "";
	private String password = "";

	public Logger getLogger() {
		return logger;
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

	public void setLogger(Logger logger) {
		this.logger = logger;
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

	public String getManagementId() {
		return managementId;
	}

	public void setManagementId(String managementId) {
		this.managementId = managementId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultipartFile getManagementPhoto() {
		return managementPhoto;
	}

	public void setManagementPhoto(MultipartFile managementPhoto) {
		this.managementPhoto = managementPhoto;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
		result.append("DB_TS=").append(getDbTs())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("BRANCH_ID=").append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("MANAGEMENT_ID=").append(getManagementId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("NAME=").append(getName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("ADDRESS1=").append(getAddress1())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("ADDRESS2=").append(getAddress2())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("ADDRESS3=").append(getAddress3())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CITY=").append(getCity())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("STATE=").append(getState())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("PINCODE=").append(getPincode())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CONTACT1=").append(getContact1())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CONTACT2=").append(getContact2())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("EMAIL=").append(getEmail())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("DESIGNATION=").append(getDesignation())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("GENDER=").append(getGender())
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
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MANAGEMENT_ID=").append(getManagementId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		return stringBuffer.toString();
	}

	@Override
	public String toString() {
		return "Management [logger=" + logger + ", dbTs=" + dbTs + ", instId="
				+ instId + ", branchId=" + branchId + ", managementId="
				+ managementId + ", name=" + name + ", managementPhoto="
				+ managementPhoto + ", address1=" + address1 + ", address2="
				+ address2 + ", address3=" + address3 + ", city=" + city
				+ ", pincode=" + pincode + ", state=" + state + ", email="
				+ email + ", designation=" + designation + ", contact1="
				+ contact1 + ", contact2=" + contact2 + ", gender=" + gender
				+ ", delFlg=" + delFlg + ", rCreId=" + rCreId + ", rCreTime="
				+ rCreTime + ", rModId=" + rModId + ", rModTime=" + rModTime
				+ ", userId=" + userId + ", password=" + password + "]";
	}

}
