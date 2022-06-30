package com.jaw.user.dao;

import java.util.Date;
import java.io.Serializable;
import org.springframework.stereotype.Component;
@Component
public class User implements Serializable{
	
	 private String userId;
	 private String password="";
	 private String ipAddress="";
	 private int NoOfAttempts=0;
	 private Date loginTime;
	 private int db_ts;
	 private String instId;
	 private String branchId;
	 private String lastLogoutTime="";
	 private Date PasswordResetDate;
	 private String sessionId="";
	 private String totalNoOfLogin="";
	 private Date currentDate;
	 private int passwordExpiryDay;
	 private String PasswordExpiryDate="";
	 private String userEnableFlag="";
	 private String deleteFlag="";
	 private String rModId="";
	 private String rModTime;
	 private String rCreId="";
	 private String rCreTime;
	 private String maxInvalidTime;
	 private String passwordResetFlag=""; 
	 private String remarks="";
	
	public String getPasswordResetFlag() {
		return passwordResetFlag;
	}
	public void setPasswordResetFlag(String passwordResetFlag) {
		this.passwordResetFlag = passwordResetFlag;
	}
	public String getMaxInvalidTime() {
		return maxInvalidTime;
	}
	public void setMaxInvalidTime(String maxInvalidTime) {
		this.maxInvalidTime = maxInvalidTime;
	}
	public String getUserEnableFlag() {
		return userEnableFlag;
	}
	public void setUserEnableFlag(String passwordEnableFlag) {
		this.userEnableFlag = passwordEnableFlag;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
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
	
	 public String getPasswordExpiryDate() {
		return PasswordExpiryDate;
	}
	public void setPasswordExpiryDate(String dt) {
		PasswordExpiryDate = dt;
	}
	public int getPasswordExpiryDay() {
		return passwordExpiryDay;
	}
	public void setPasswordExpiryDay(int passwrodExpiryDay) {
		this.passwordExpiryDay = passwrodExpiryDay;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	public int getDb_ts() {
		return db_ts;
	}
	public void setDb_ts(int db_ts) {
		this.db_ts = db_ts;
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
	public String getLastLogoutTime() {
		return lastLogoutTime;
	}
	public void setLastLogoutTime(String lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}
	public Date getPasswordResetDate() {
		return PasswordResetDate;
	}
	public void setPasswordResetDate(Date passwordResetDate) {
		PasswordResetDate = passwordResetDate;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getTotalNoOfLogin() {
		return totalNoOfLogin;
	}
	public void setTotalNoOfLogin(String totalNoOfLogin) {
		this.totalNoOfLogin = totalNoOfLogin;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getNoOfAttempts() {
		return NoOfAttempts;
	}
	public void setNoOfAttempts(int noOfAttempts) {
		NoOfAttempts = noOfAttempts;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	

}
