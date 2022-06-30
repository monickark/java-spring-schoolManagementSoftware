package com.jaw.admin.dao;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import com.jaw.common.constants.AuditConstant;

//Institute Master Pojo class
public class InstituteMaster {

	// Logging
	 Logger logger = Logger.getLogger(InstituteMaster.class);

	// Properties
	private Integer dbTs;	
	private String instId ;
	private String instName = "";
	private String add1 = "";
	private String add2 = "";
	private String add3 = "";
	private String city = "";
	private String pincode = "";
	private String state = "";
	private String email = "";
	private String fax = "";
	private String contact1 = "";
	private String contact2 = "";
	private String instCategory = "";
	private String affId = "";
	private String affDetails = "";
	private String delFlag = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";

	public String getInstCategory() {
		return instCategory;
	}

	public void setInstCategory(String instCategory) {
		this.instCategory = instCategory;
	}

	public String getAffId() {
		return affId;
	}

	public void setAffId(String affId) {
		this.affId = affId;
	}

	public String getAffDetails() {
		return affDetails;
	}

	public void setAffDetails(String affDetails) {
		this.affDetails = affDetails;
	}

	
	// Getters & Setters

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}	
	public String getAdd1() {
		return add1;
	}

	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	public String getAdd2() {
		return add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	public String getAdd3() {
		return add3;
	}

	public void setAdd3(String add3) {
		this.add3 = add3;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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

	public Integer getDbTs() {
		return dbTs;
	}

	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}

	// method for create InstituteMaster Record for Audit
	public String toStringForAuditInstMasterRecord() {

		StringBuffer stringBuffer = new StringBuffer()
		.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_NAME=").append(getInstName()).append(AuditConstant.AUDIT_REC_SEPERATOR)	
		.append("INST_ADD1=").append(getAdd1()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ADD2=").append(getAdd2()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ADD3=").append(getAdd3()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_CITY=").append(getCity()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_PINCODE=").append(getPincode()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_STATE=").append(getState()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_EMAIL=").append(getEmail()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_FAX=").append(getFax()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_CONTACT1=").append(getContact1()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_CONTACT2=").append(getContact2()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_CATEGORY=").append(getInstCategory()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("AFF_ID=").append(getAffId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("AFF_DETAILS=").append(getAffDetails()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

		logger.debug("String formed for audit is :" + stringBuffer.toString());

		return stringBuffer.toString();
	}

	// method for create InstituteMasterKey String for Audit
	public String toStringForAuditInstMasterKey() {

		StringBuffer stringBuffer = new StringBuffer().append("INST_ID=")
				.append(getInstId());
		return stringBuffer.toString();
	}

}
