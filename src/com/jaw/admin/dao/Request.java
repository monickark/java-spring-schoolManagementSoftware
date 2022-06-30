package com.jaw.admin.dao;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

@Component
public class Request implements Serializable {
	
	@NotEmpty
	private int dbTs;
	private String userId;
	private String instId;
	private String branchId;
	private String userMenuProfile = "";
	private String name = "";
	private String requestSerialNumber;
	private String reqstType = "";
	private String reqstStatus = "";
	private String delFlg = "";
	private String rCreId = "";
	private String rCreTime;
	private String rModId = "";
	private String rModTime;
	private String menuOptionId = "";
	private String closedDate;
	
	public String getClosedDate() {
		return closedDate;
	}
	
	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}
	
	public String getMenuOptionId() {
		return menuOptionId;
	}
	
	public void setMenuOptionId(String menuOptionId) {
		this.menuOptionId = menuOptionId;
	}
	
	public String getRequestSerialNumber() {
		return requestSerialNumber;
	}
	
	public void setRequestSerialNumber(String requestSerialNumber) {
		this.requestSerialNumber = requestSerialNumber;
	}
	
	public int getDbTs() {
		return dbTs;
	}
	
	public void setDbTs(int dbTs) {
		this.dbTs = dbTs;
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
	
}
