package com.jaw.admin.controller;

import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;

public class BranchMasterVO implements Serializable{	
	private Integer dbTs;	
	private Integer rowid;		
	private String pageNo = "";
	private String instId="";
	private String branchId="";
	private String branchName="";
	private MultipartFile branchLogo;	
	private String address1="";
	private String address2="";
	private String address3="";		
	private String pincode="";	
	private String city="";
	private String state="";
	private String email="";
	private String fax="";
	private String contact1="";
	private String contact2="";	
	private String branchCategory = "";
	private String affId = "";
	private String affDetails = "";
		
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}
	public Integer getRowid() {
		return rowid;
	}
	public void setRowid(Integer rowid) {
		this.rowid = rowid;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
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
	public MultipartFile getBranchLogo() {
		return branchLogo;
	}
	public void setBranchLogo(MultipartFile branchLogo) {
		this.branchLogo = branchLogo;
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
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
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
	
}
