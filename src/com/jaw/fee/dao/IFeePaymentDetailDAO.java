package com.jaw.fee.dao;

import java.util.List;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.core.dao.CourseTermLinking;
import com.jaw.core.dao.CourseTermLinkingKey;

public interface IFeePaymentDetailDAO {
	void insertFeePaymentDetailRec(FeePaymentDetail feePaymentDetail)
			throws DuplicateEntryException;
	
	void deleteFeePaymentDetailRec(FeePaymentDetail feePaymentDetail, final FeePaymentDetailKey feePaymentDetailKey)
			throws DeleteFailedException;
	FeePaymentDetail selectFeePaymentDetailRec(
			final FeePaymentDetailKey feePaymentDetailKey)
			throws NoDataFoundException;
	FeePaymentDetail selectFeePaymentDetailDelFlgYRec(
			final FeePaymentDetailKey feePaymentDetailKey)
			throws NoDataFoundException;
	public void updateFeePaymentDetailRecDelFlgYesToNo(FeePaymentDetail feePaymentDetail, final FeePaymentDetailKey feePaymentDetailKey)
			throws UpdateFailedException ;
}
