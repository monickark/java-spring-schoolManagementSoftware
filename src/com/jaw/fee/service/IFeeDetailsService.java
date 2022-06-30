package com.jaw.fee.service;

import java.util.List;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FeeMasterExistException;
import com.jaw.common.exceptions.FeeMasterNotFoundException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.StudentNotFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.fee.controller.FeeDetailsMasterVO;
import com.jaw.fee.controller.FeeDetailsSearchVO;
import com.jaw.fee.controller.FeeDueListVO;
import com.jaw.fee.controller.FeePaidListVO;
import com.jaw.fee.controller.FeePaymentList;
import com.jaw.fee.controller.FeePaymentMasterVO;
import com.jaw.fee.controller.FeePaymentSearchVO;
import com.jaw.fee.dao.FeeDueDetailsList;
import com.jaw.fee.dao.FeeDueList;
import com.jaw.fee.dao.FeeDueListKey;
import com.jaw.fee.dao.FeePaidList;
import com.jaw.fee.dao.FeePaidListKey;
import com.jaw.fee.dao.StudentFeePayment;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IFeeDetailsService {
	public List<FeeDueList> selectFeeDueAndPaidListDetails(
			SessionCache sessionCache, FeeDetailsMasterVO feeDetailsMasterVO)
			throws NoDataFoundException;

	public List<FeeDueDetailsList> getFeeDueDetailsList(
			FeeDueListVO feeDueList, UserSessionDetails userSessionDetails,
			FeeDetailsMasterVO feeDetailsMasterVO) throws NoDataFoundException;

	public List<FeePaymentList> selectFeePaidPaymentListDetails(
			FeePaidListVO feePaidList, SessionCache sessionCache,
			FeeDetailsMasterVO feeDetailsMasterVO,
			ApplicationCache applicationCache) throws NoDataFoundException,
			PropertyNotFoundException;

	void printReceipt(FeePaidListVO feePaidListVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws NoDataFoundException,
			DatabaseException, UpdateFailedException;

	void selectFeeReceiptRec(FeeDetailsMasterVO feeDetailsMasterVO,
			UserSessionDetails userSessionDetails, SessionCache sessionCache,
			ApplicationCache applicationCache) throws NoDataFoundException,
			FeeMasterNotFoundException, StudentNotFoundException,
			DuplicateEntryException, DatabaseException, UpdateFailedException,
			FeeMasterExistException, PropertyNotFoundException;

	List<FeeDueDetailsList> getFeeConsolidation(FeeDueListVO feeDueListVo,
			UserSessionDetails userSessionDetails,
			FeePaymentSearchVO feePaymentSearchVO) throws NoDataFoundException;
}
