package com.jaw.admin.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RequestListVo implements Serializable {
	private int reqDbTs;
	private int userDbts;
	private String instId;
	private String branchId;
	private String requestSerialNumber;
	private String reqstType;
	private String reqstStatus;
	private String userId;
	private String userMenuProfile;
	private String name;
	private String rCreTime;
	private String rModTime;
	private String[] userList;
	private List<RequestListVo> requestVos;
	private String pageNo = "10";
	private int rowid;
	private String menuOptionId;
	private String menuId;
	private String branchName;
	private String closedDate;

	public String getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
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

	public String getMenuOptionId() {
		return menuOptionId;
	}

	public void setMenuOptionId(String menuOptionId) {
		this.menuOptionId = menuOptionId;
	}

	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

	public String getReqstStatus() {
		return reqstStatus;
	}

	public void setReqstStatus(String reqstStatus) {
		this.reqstStatus = reqstStatus;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getRequestSerialNumber() {
		return requestSerialNumber;
	}

	public void setRequestSerialNumber(String regstSrtNum) {
		requestSerialNumber = regstSrtNum;
	}

	public String getReqstType() {
		return reqstType;
	}

	public void setReqstType(String reqstType) {
		this.reqstType = reqstType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String[] getUserList() {
		return userList;
	}

	public void setUserList(String[] serial) {
		userList = serial;
	}

	public List<RequestListVo> getRequestVos() {
		return requestVos;
	}

	public void setRequestVos(List<RequestListVo> requestVos) {
		this.requestVos = requestVos;
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

}
