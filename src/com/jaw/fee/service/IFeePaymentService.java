package com.jaw.fee.service;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FeeMasterExistException;
import com.jaw.common.exceptions.FeeMasterNotFoundException;
import com.jaw.common.exceptions.FeeMasterNotFoundForIntegratedCourseException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.StudentNotFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.fee.controller.FeePaymentList;
import com.jaw.fee.controller.FeePaymentMasterVO;
import com.jaw.fee.dao.StudentFeePayment;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IFeePaymentService {

	void selectFeePaymentRec(FeePaymentMasterVO feePaymentMasterVO,
			UserSessionDetails userSessionDetails, SessionCache sessionCache,
			ApplicationCache applicationCache) throws NoDataFoundException,
			FeeMasterNotFoundException, StudentNotFoundException,
			DuplicateEntryException, DatabaseException, UpdateFailedException,
			FeeMasterExistException, PropertyNotFoundException, FeeMasterNotFoundForIntegratedCourseException;

	void insertFeePaymentRec(FeePaymentMasterVO feePaymentMasterVO,
			UserSessionDetails userSessionDetails, SessionCache sessionCache,
			ApplicationCache applicationCache) throws DatabaseException,
			DuplicateEntryException, NoDataFoundException,
			UpdateFailedException, FeeMasterNotFoundException, StudentNotFoundException, FeeMasterExistException, PropertyNotFoundException;

	List<FeePaymentList> selectFeeReceiptRec(
			StudentFeePayment StudentFeePayment,
			FeePaymentMasterVO feePaymentMasterVO,
			UserSessionDetails userSessionDetails, SessionCache sessionCache,
			ApplicationCache applicationCache) throws NoDataFoundException,
			FeeMasterNotFoundException, StudentNotFoundException,
			DuplicateEntryException, DatabaseException, UpdateFailedException,
			FeeMasterExistException, PropertyNotFoundException;

	Map<String, String> getStudentAdmisNo(ApplicationCache applicationCache,
			String acTerm, String stGroup, String feeCategory,
			UserSessionDetails userSessionDetails) throws NoDataFoundException, PropertyNotFoundException;

}
