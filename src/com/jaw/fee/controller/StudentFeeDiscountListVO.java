package com.jaw.fee.controller;

public class StudentFeeDiscountListVO {

	private String studentAdmisNo = "";
	private String studentName = "";
	private String feeAmt = "";
	private String concessionAmt = "";
	private String feePaidAmt = "";
	private String feeDueAmt = "";
	private String FeeDmdremarks = "";
	private String acTerm = "";
	private int rowid;
	private Integer lastYearPayment;

	public Integer getLastYearPayment() {
		return lastYearPayment;
	}

	public void setLastYearPayment(Integer lastYearPayment) {
		this.lastYearPayment = lastYearPayment;
	}

	public String getAcTerm() {
		return acTerm;
	}

	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}

	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}

	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getConcessionAmt() {
		return concessionAmt;
	}

	public void setConcessionAmt(String concessionAmt) {
		this.concessionAmt = concessionAmt;
	}

	public String getFeePaidAmt() {
		return feePaidAmt;
	}

	public void setFeePaidAmt(String feePaidAmt) {
		this.feePaidAmt = feePaidAmt;
	}

	public String getFeeDueAmt() {
		return feeDueAmt;
	}

	public void setFeeDueAmt(String feeDueAmt) {
		this.feeDueAmt = feeDueAmt;
	}

	public String getFeeDmdremarks() {
		return FeeDmdremarks;
	}

	public void setFeeDmdremarks(String feeDmdremarks) {
		FeeDmdremarks = feeDmdremarks;
	}

	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

	@Override
	public String toString() {
		return "StudentFeeDiscountListVO [studentAdmisNo=" + studentAdmisNo
				+ ", studentName=" + studentName + ", feeAmt=" + feeAmt
				+ ", concessionAmt=" + concessionAmt + ", feePaidAmt="
				+ feePaidAmt + ", feeDueAmt=" + feeDueAmt + ", FeeDmdremarks="
				+ FeeDmdremarks + ", rowid=" + rowid + "]";
	}

}
