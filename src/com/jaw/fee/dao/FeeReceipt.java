package com.jaw.fee.dao;

/**
 * @author Gritz Horizons Ltd 1
 * 
 */
public class FeeReceipt {

	private int totalPaid;
	private int fineAmt;
	private int feePaidAmt;
	private String feeReceiptDate = "";
	private String instrumentNo = "";
	private String feeReceiptNo = "";


	public int getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(int totalPaid) {
		this.totalPaid = totalPaid;
	}

	public int getFineAmt() {
		return fineAmt;
	}

	public void setFineAmt(int fineAmt) {
		this.fineAmt = fineAmt;
	}

	public int getFeePaidAmt() {
		return feePaidAmt;
	}

	public void setFeePaidAmt(int feePaidAmt) {
		this.feePaidAmt = feePaidAmt;
	}


	public String getFeeReceiptDate() {
		return feeReceiptDate;
	}

	public void setFeeReceiptDate(String feeReceiptDate) {
		this.feeReceiptDate = feeReceiptDate;
	}

	public String getInstrumentNo() {
		return instrumentNo;
	}

	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}

	public String getFeeReceiptNo() {
		return feeReceiptNo;
	}

	public void setFeeReceiptNo(String feeReceiptNo) {
		this.feeReceiptNo = feeReceiptNo;
	}

	@Override
	public String toString() {
		return "FeeReceipt [totalPaid=" + totalPaid + ", fineAmt=" + fineAmt
				+ ", feePaidAmt=" + feePaidAmt + ", feeReceiptDate=" + feeReceiptDate
				+ ", instrumentNo=" + instrumentNo + ", feeReceiptNo="
				+ feeReceiptNo + "]";
	}

}
