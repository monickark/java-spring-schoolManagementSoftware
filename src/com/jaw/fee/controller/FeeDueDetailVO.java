package com.jaw.fee.controller;

public class FeeDueDetailVO {
	private Integer totalFeeAmt;
	private Integer concessionAmt;
	private Integer feeDueAmt;
	private String concessionCategry;
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

	public String getConcessionCategry() {
		return concessionCategry;
	}

	public void setConcessionCategry(String concessionCategry) {
		this.concessionCategry = concessionCategry;
	}
	
	public Integer getMonthlyFeeDueAmt() {
		return monthlyFeeDueAmt;
	}

	public void setMonthlyFeeDueAmt(Integer monthlyFeeDueAmt) {
		this.monthlyFeeDueAmt = monthlyFeeDueAmt;
	}

	@Override
	public String toString() {
		return "FeeDueDetailVO [totalFeeAmt=" + totalFeeAmt
				+ ", concessionAmt=" + concessionAmt + ", feeDueAmt="
				+ feeDueAmt + ", concessionCategry=" + concessionCategry
				+ ", feePaidAmt=" + feePaidAmt + ", lastYearPayment="
				+ lastYearPayment + ", monthlyFeeDueAmt=" + monthlyFeeDueAmt
				+ "]";
	}
	
	
}
