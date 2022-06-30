package com.jaw.core.dao;

import java.io.Serializable;

public class StandardSectionKey implements Serializable {

	private String instId;
	private String branchId;
	private String standardId;
	private String combinationId;
	private String secId;

	public synchronized String getSecId() {
		return secId;
	}

	public synchronized void setSecId(String secId) {
		this.secId = secId;
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

	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	public String getCombinationId() {
		return combinationId;
	}

	public void setCombinationId(String combinationId) {
		this.combinationId = combinationId;
	}

}
