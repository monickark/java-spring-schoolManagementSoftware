package com.jaw.staff.dao;
import java.io.Serializable;

public class StaffMasterKey implements Serializable{
		
	private String instId;
	private String branchId;
	private String staffId;
	
	
	public synchronized String getSubId() {
		return staffId;
	}
	public synchronized void setSubId(String staffId) {
		this.staffId = staffId;
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
