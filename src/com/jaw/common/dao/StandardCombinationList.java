package com.jaw.common.dao;
import java.io.Serializable;

public class StandardCombinationList implements Serializable{
	private String instId;
	private String branchId;
	private String standardId;
	private String combinationId;
	
	
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
