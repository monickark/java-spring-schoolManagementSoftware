package com.jaw.admin.dao;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

@Component
public class RequestList implements Serializable {

	@NotEmpty
	private int reqDbTs;
	private int userDbts;
	private String userId;
	private String instId;
	private String branchId;
	private String userMenuProfile;
	private String name;
	private String requestSerialNumber;
	private String reqstType;
	private String reqstStatus;
	private String rCreTime;
	private String rModTime;
	private String menuOptionId;
	private String branchName;
	private String rModId;
	private String closedDate;
	private String linkId;

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}

	public String getrModId() {
		return rModId;
	}

	public void setrModId(String rModId) {
		this.rModId = rModId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public int getReqDbTs() {
		return reqDbTs;
	}

	public void setReqDbTs(int reqDbTs) {
		this.reqDbTs = reqDbTs;
	}

	public int getUserDbts() {
		return userDbts;
	}

	public void setUserDbts(int userDbts) {
		this.userDbts = userDbts;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getUserMenuProfile() {
		return userMenuProfile;
	}

	public void setUserMenuProfile(String userMenuProfile) {
		this.userMenuProfile = userMenuProfile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRequestSerialNumber() {
		return requestSerialNumber;
	}

	public void setRequestSerialNumber(String requestSerialNumber) {
		this.requestSerialNumber = requestSerialNumber;
	}

	public String getReqstType() {
		return reqstType;
	}

	public void setReqstType(String reqstType) {
		this.reqstType = reqstType;
	}

	public String getReqstStatus() {
		return reqstStatus;
	}

	public void setReqstStatus(String reqstStatus) {
		this.reqstStatus = reqstStatus;
	}

	public String getrCreTime() {
		return rCreTime;
	}

	public void setrCreTime(String rCreTime) {
		this.rCreTime = rCreTime;
	}

	public String getrModTime() {
		return rModTime;
	}

	public void setrModTime(String rModTime) {
		this.rModTime = rModTime;
	}

	public String getMenuOptionId() {
		return menuOptionId;
	}

	public void setMenuOptionId(String menuOptionId) {
		this.menuOptionId = menuOptionId;
	}

}
