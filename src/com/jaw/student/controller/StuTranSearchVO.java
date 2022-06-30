package com.jaw.student.controller;

public class StuTranSearchVO {

	private String instId = "";
	private String branchId = "";
	private String acTrm = "";
	private String stuGrpName = "";
	private String stuName = "";
	private String stuAdmisNo = "";
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	@Override
	public String toString() {
		return "StuTranSearchVO [instId=" + instId + ", branchId=" + branchId
				+ ", acTrm=" + acTrm + ", stuGrpName=" + stuGrpName
				+ ", stuName=" + stuName + ", stuAdmisNo=" + stuAdmisNo + "]";
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}	
	public String getAcTrm() {
		return acTrm;
	}
	public void setAcTrm(String acTrm) {
		this.acTrm = acTrm;
	}
	public String getStuGrpName() {
		return stuGrpName;
	}
	public void setStuGrpName(String stuGrpName) {
		this.stuGrpName = stuGrpName;
	}
	public String getStuName() {
		return stuName;
	}
	public String getStuAdmisNo() {
		return stuAdmisNo;
	}
	public void setStuAdmisNo(String stuAdmisNo) {
		this.stuAdmisNo = stuAdmisNo;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	
}

