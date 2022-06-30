package com.jaw.batch.controller;

public class FileTransferVO {
	
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
	public String getTypeOfOpt() {
		return typeOfOpt;
	}
	public void setTypeOfOpt(String typeOfOpt) {
		this.typeOfOpt = typeOfOpt;
	}
	@Override
	public String toString() {
		return "FileTransferVO [instId=" + instId + ", branchId=" + branchId
				+ ", typeOfOpt=" + typeOfOpt + "]";
	}
	private String instId;
	 private String branchId;
	 private String typeOfOpt;

}
