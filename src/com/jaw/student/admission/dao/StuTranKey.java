package com.jaw.student.admission.dao;

public class StuTranKey {
	private String instId = "";
	private String branchId = "";
	private String acTrm = "";
	private String stuGrpName = "";
	private String stuAdmisNo = "";
	public String getInstId() {
		return instId;
	}
	@Override
	public String toString() {
		return "StuTranKey [instId=" + instId + ", branchId=" + branchId
				+ ", acTrm=" + acTrm + ", stuGrpName=" + stuGrpName
				+ ", stuAdmisNo=" + stuAdmisNo + "]";
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
	public String getAcTrm() {
		return acTrm;
	}
	public void setAcTrm(String acTrm) {
		this.acTrm = acTrm;
	}
	public String getStuGrpName() {
		return stuGrpName;
	}
	public String getStuAdmisNo() {
		return stuAdmisNo;
	}
	public void setStuAdmisNo(String stuAdmisNo) {
		this.stuAdmisNo = stuAdmisNo;
	}
	public void setStuGrpName(String stuGrpName) {
		this.stuGrpName = stuGrpName;
	}
	
	


}
