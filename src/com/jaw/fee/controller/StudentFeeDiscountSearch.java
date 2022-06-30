package com.jaw.fee.controller;

public class StudentFeeDiscountSearch {
	private String instId = "";
	private String branchId = "";
	private String acTerm;
	private String stGroup;
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
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getStGroup() {
		return stGroup;
	}
	public void setStGroup(String stGroup) {
		this.stGroup = stGroup;
	}
	@Override
	public String toString() {
		return "StudentFeeDiscountSearch [instId=" + instId + ", branchId="
				+ branchId + ", acTerm=" + acTerm + ", stGroup=" + stGroup
				+ "]";
	}
	
	
}
