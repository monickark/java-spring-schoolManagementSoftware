package com.jaw.framework.appCache.dao;

import java.io.Serializable;

public class AcademicTerm implements Serializable {

	private String instId;
	private String branchId;
	private String acTerm = "";
	private String acTermStatus = "";
	private String acName="";
	
	
	public String getAcName() {
		return acName;
	}
	public void setAcName(String acName) {
		this.acName = acName;
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
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getAcTermStatus() {
		return acTermStatus;
	}
	public void setAcTermStatus(String acTermStatus) {
		this.acTermStatus = acTermStatus;
	}
	@Override
	public String toString() {
		return "AcademicTerm [instId=" + instId + ", branchId=" + branchId
				+ ", acTerm=" + acTerm + ", acTermStatus=" + acTermStatus
				+ ", acName=" + acName + "]";
	}

	

}
