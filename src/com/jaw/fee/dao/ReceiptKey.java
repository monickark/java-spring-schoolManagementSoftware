package com.jaw.fee.dao;


/**
 * @author Gritz Horizons Ltd 1
 * 
 */
public class ReceiptKey {

	
	private String instId = "";
	private String branchId = "";	
	private String acTerm;
	private String feeCategory;
	private String studentAdmisNo;
	private String stGroup;
	private String feePaymentTerm;
	private String studFeeDDId = "";
	private String feePmtSrlNo = "";
	
	public String getStudFeeDDId() {
		return studFeeDDId;
	}
	public void setStudFeeDDId(String studFeeDDId) {
		this.studFeeDDId = studFeeDDId;
	}
	public String getFeePmtSrlNo() {
		return feePmtSrlNo;
	}
	public void setFeePmtSrlNo(String feePmtSrlNo) {
		this.feePmtSrlNo = feePmtSrlNo;
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
	public String getFeeCategory() {
		return feeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
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
	public String getFeePaymentTerm() {
		return feePaymentTerm;
	}
	public void setFeePaymentTerm(String feePaymentTerm) {
		this.feePaymentTerm = feePaymentTerm;
	}
	@Override
	public String toString() {
		return "ReceiptKey [instId=" + instId + ", branchId=" + branchId
				+ ", acTerm=" + acTerm + ", feeCategory=" + feeCategory
				+ ", studentAdmisNo=" + studentAdmisNo + ", stGroup=" + stGroup
				+ ", feePaymentTerm=" + feePaymentTerm + ", studFeeDDId="
				+ studFeeDDId + ", feePmtSrlNo=" + feePmtSrlNo + "]";
	}
	
	
	
}
