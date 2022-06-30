package com.jaw.user.dao;
import java.io.Serializable;
public class UserKey implements Serializable{
	private String instId; 
	private String branchId;
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
