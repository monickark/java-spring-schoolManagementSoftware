package com.jaw.fee.service;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.CourseTermLinkingMasterVO;
import com.jaw.core.controller.CourseTermLinkingVO;
import com.jaw.fee.controller.FeePaymentDetailMasterVO;
import com.jaw.fee.controller.FeePaymentDetailVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IFeePaymentDetailService {
	public FeePaymentDetailMasterVO selectFeePaymentDetailsList(
			FeePaymentDetailMasterVO feePaymentDetailMasterVO,UserSessionDetails userSessionDetails)
			throws NoDataFoundException;
	public void insertFeePaymentDetailRec(
			FeePaymentDetailMasterVO feePaymentDetailMasterVO,
			UserSessionDetails userSessionDetails)
			throws DatabaseException, DuplicateEntryException, UpdateFailedException;
	public void deleteFeePaymentDetailRec(FeePaymentDetailVO feePaymentDetailVO,UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException;
}
