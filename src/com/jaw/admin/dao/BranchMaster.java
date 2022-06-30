package com.jaw.admin.dao;

import org.apache.log4j.Logger;
import java.io.Serializable;
import com.jaw.common.constants.AuditConstant;

public class BranchMaster implements Serializable{
	Logger logger=Logger.getLogger(BranchMaster.class);
	private Integer dbTs;	
	private String instId;
	private String branchId;
	private String branchName="";	
	private String address1="";
	private String address2="";
	private String address3="";
	private String city="";
	private String state="";
	private String pincode="";
	private String email="";
	private String fax="";
	private String contact1="";
	private String contact2="";
	private String branchCategory = "";
	private String affId = "";
	private String affDetails = "";	
	private String delFlag="";
	private String rModId="";
	private String rModTime="";
	private String rCreId="";
	private String rCreTime="";
	
	public String getBranchCategory() {
		return branchCategory;
	}
	public void setBranchCategory(String branchCategory) {
		this.branchCategory = branchCategory;
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
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
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
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getAddress1() {
		return address1;
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
	
	// method for create InstituteMaster Record for Audit
		public String toStringForAuditBranchMasterRecord() {			
			
			StringBuffer stringBuffer = new StringBuffer().append("DB_TS=")
					.append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("INST_ID=").append(getInstId())
					.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_ID=")
					.append(getBranchId())
					.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_NAME=")
					.append(getBranchName())					
					.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_ADD1=")
					.append(getAddress1()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("BRANCH_ADD2=").append(getAddress2())
					.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_ADD3=")
					.append(getAddress3()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("BRANCH_CITY=").append(getCity())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("BRANCH_PINCODE=").append(getPincode())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("BRANCH_STATE=").append(getState())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("BRANCH_EMAIL=").append(getEmail())
					.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_FAX=")
					.append(getFax()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("BRANCH_CONTACT1=").append(getContact1())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("BRANCH_CONTACT2=").append(getContact2())					
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("BRANCH_CATEGORY=").append(getBranchCategory())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("AFF_ID=").append(getAffId())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("AFF_DETAILS=").append(getAffDetails())
					.append(AuditConstant.AUDIT_REC_SEPERATOR).append("DEL_FLG=")
					.append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_MOD_ID=").append(getrModId())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_MOD_TIME=").append(getrModTime())
					.append(AuditConstant.AUDIT_REC_SEPERATOR).append("R_CRE_ID=")
					.append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_CRE_TIME=").append(getrCreTime())
					.append(AuditConstant.AUDIT_REC_SEPERATOR);

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}
		
		
		// method for create InstituteMasterKey String for Audit
		public String toStringForAuditBranchMasterKey() {

			StringBuffer stringBuffer = new StringBuffer().append("INST_ID=")					
					.append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_ID=")
					.append(getBranchId())
					.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_NAME=")
					.append(getBranchName());
			return stringBuffer.toString();
		}
	
	
	
}
