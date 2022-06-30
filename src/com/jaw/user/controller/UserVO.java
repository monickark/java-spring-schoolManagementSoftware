package com.jaw.user.controller;
import java.io.Serializable;
import org.springframework.stereotype.Component;
@Component
public class UserVO implements Serializable{

	private String userId = "";
	private String password ="";
	private String instId =""; 
	private String branchId ="";
	private String changePassword = "";
	private String retypePassword = "";
	private String forcePage = "";
	private String sessionId ="";
	private String ipAddress ="";
	private String remarks ="";
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userName) {
		this.userId = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getChangePassword() {
		return changePassword;
	}
	public void setChangePassword(String changePassword) {
		this.changePassword = changePassword;
	}
	public String getRetypePassword() {
		return retypePassword;
	}
	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}
	public String getForcePage() {
		return forcePage;
	}
	public void setForcePage(String forcePage) {
		this.forcePage = forcePage;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
