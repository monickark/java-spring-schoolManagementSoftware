package com.jaw.fee.dao;

import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IFeePaymentListDao {

	Map<String, String> getStuAdmisNoList(String instId, String branchId,
			String acTerm, String stGroup) throws NoDataFoundException;

	Map<String, String> getAdmissionStudentList(String instId, String branchId,
			String acTerm, String feeCategory) throws NoDataFoundException;

	String getTerm(ReceiptKey receiptKey)
			throws NoDataFoundException;

	FeeReceipt selectReceiptRec(ReceiptKey receiptKey)
			throws NoDataFoundException;

}
