package com.jaw.student.controller;

public class StuTranVO {
	
	private String feeDue = "";
	private String transferDate = "";
	private String reasonForLeaving = "";
	public String getFeeDue() {
		return feeDue;
	}
	public void setFeeDue(String feeDue) {
		this.feeDue = feeDue;
	}
	public String getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}
	public String getReasonForLeaving() {
		return reasonForLeaving;
	}
	@Override
	public String toString() {
		return "StuTranVO [feeDue=" + feeDue + ", transferDate=" + transferDate
				+ ", reasonForLeaving=" + reasonForLeaving + "]";
	}
	public void setReasonForLeaving(String reasonForLeaving) {
		this.reasonForLeaving = reasonForLeaving;
	}

}
