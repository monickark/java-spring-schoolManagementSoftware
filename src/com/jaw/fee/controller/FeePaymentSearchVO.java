package com.jaw.fee.controller;


/**
 * @author Gritz Horizons Ltd 1
 * 
 */
public class FeePaymentSearchVO {

	private String acTerm="";
	private String feeCategory="";
	private String studentAdmisNo="";
	private String stGroup="";
	private String feePaymentTerm="";
	private String path="";
	private String pageSize = "10";

	public String getFeeCategory() {
		return feeCategory;
	}

	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}

	public String getAcTerm() {
		return acTerm;
	}

	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}

	public String getFeePaymentTerm() {
		return feePaymentTerm;
	}

	public void setFeePaymentTerm(String feePaymentTerm) {
		this.feePaymentTerm = feePaymentTerm;
	}

	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}

	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}

	public String getStGroup() {
		return stGroup;
	}

	public void setStGroup(String stGroup) {
		this.stGroup = stGroup;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "FeePaymentSearchVO [acTerm=" + acTerm + ", feeCategory="
				+ feeCategory + ", studentAdmisNo=" + studentAdmisNo
				+ ", stGroup=" + stGroup + ", feePaymentTerm=" + feePaymentTerm
				+ ", path=" + path + ", pageSize=" + pageSize + "]";
	}

}
