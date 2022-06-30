package com.jaw.admin.controller;

import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
//Institute Master VO class
public class InstituteMasterVO implements Serializable{

	// Properties
	private Integer dbTs;
	private String instId = "";
	private String instName = "";
	private MultipartFile instLogo;
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
	private String singleBranch = "";
	
	public String getSingleBranch() {
		return singleBranch;
	}

	public void setSingleBranch(String singleBranch) {
		this.singleBranch = singleBranch;
	}


	

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

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public MultipartFile getInstLogo() {
		return instLogo;
	}

	public void setInstLogo(MultipartFile instLogo) {
		this.instLogo = instLogo;
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

}
