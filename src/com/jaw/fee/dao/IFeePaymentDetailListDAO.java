package com.jaw.fee.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IFeePaymentDetailListDAO {
	public List<FeePaymentDetail> getFeePaymentDetailsList(
			FeePaymentDetailKey feePaymentDetailsKey) throws NoDataFoundException;
}
