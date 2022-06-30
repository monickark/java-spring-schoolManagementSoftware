package com.jaw.fee.dao;

public class FeeDueDetailsList {

	private String feeTerm;
	private String feeType;
	private String electiveSpec;
	private Integer feeTermAmt;
	private Integer totalFeeAmt;
	private Integer concessionAmt;
	private Integer feeDueAmt;
	private Integer feePaidAmt;
	private Integer lastYearPayment;
	private Integer monthlyFeeDueAmt;

	public Integer getLastYearPayment() {
		return lastYearPayment;
	}

	public void setLastYearPayment(Integer lastYearPayment) {
		this.lastYearPayment = lastYearPayment;
	}

	public Integer getFeePaidAmt() {
		return feePaidAmt;
	}

	public void setFeePaidAmt(Integer feePaidAmt) {
		this.feePaidAmt = feePaidAmt;
	}

	public String getFeeTerm() {
		return feeTerm;
	}

	public void setFeeTerm(String feeTerm) {
		this.feeTerm = feeTerm;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getElectiveSpec() {
		return electiveSpec;
	}

	public void setElectiveSpec(String electiveSpec) {
		this.electiveSpec = electiveSpec;
	}

	public Integer getFeeTermAmt() {
		return feeTermAmt;
	}

	public void setFeeTermAmt(Integer feeTermAmt) {
		this.feeTermAmt = feeTermAmt;
	}

	public Integer getTotalFeeAmt() {
		return totalFeeAmt;
	}

	public void setTotalFeeAmt(Integer totalFeeAmt) {
		this.totalFeeAmt = totalFeeAmt;
	}

	public Integer getConcessionAmt() {
		return concessionAmt;
	}

	public void setConcessionAmt(Integer concessionAmt) {
		this.concessionAmt = concessionAmt;
	}

	public Integer getFeeDueAmt() {
		return feeDueAmt;
	}

	public void setFeeDueAmt(Integer feeDueAmt) {
		this.feeDueAmt = feeDueAmt;
	}
	
	public Integer getMonthlyFeeDueAmt() {
		return monthlyFeeDueAmt;
	}

	public void setMonthlyFeeDueAmt(Integer monthlyFeeDueAmt) {
		this.monthlyFeeDueAmt = monthlyFeeDueAmt;
	}

	@Override
	public String toString() {
		return "FeeDueDetailsList [feeTerm=" + feeTerm + ", feeType=" + feeType
				+ ", electiveSpec=" + electiveSpec + ", feeTermAmt="
				+ feeTermAmt + ", totalFeeAmt=" + totalFeeAmt
				+ ", concessionAmt=" + concessionAmt + ", feeDueAmt="
				+ feeDueAmt + ", feePaidAmt=" + feePaidAmt
				+ ", lastYearPayment=" + lastYearPayment
				+ ", monthlyFeeDueAmt=" + monthlyFeeDueAmt + "]";
	}

}
