package com.jaw.common.files.dao;

import java.io.Serializable;

//Class for FileMaster key 
public class FileMasterKeyForAudit implements Serializable {

	// Properties
	private String instId;
	private String branchId;
	private String contentType = "";

	// Getters & Setters
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
