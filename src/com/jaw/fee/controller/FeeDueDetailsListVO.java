package com.jaw.fee.controller;

public class FeeDueDetailsListVO {
	private String feeTerm;
	private String feeType;
	private String electiveSpec;
	private Integer feeTermAmt;
	private Integer totalFeeAmt;
	private Integer concessionAmt;
	private Integer feeDueAmt;
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
	@Override
	public String toString() {
		return "FeeDueDetailsListVO [feeTerm=" + feeTerm + ", feeType="
				+ feeType + ", electiveSpec=" + electiveSpec + ", feeTermAmt="
				+ feeTermAmt + ", totalFeeAmt=" + totalFeeAmt
				+ ", concessionAmt=" + concessionAmt + ", feeDueAmt="
				+ feeDueAmt + "]";
	}
}
