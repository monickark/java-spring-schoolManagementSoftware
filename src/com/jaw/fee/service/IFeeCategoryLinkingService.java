package com.jaw.fee.service;

import java.util.Map;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.fee.controller.FeeCategoryLinkingVO;
import com.jaw.fee.controller.FeePaymentSearchVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IFeeCategoryLinkingService {

	void selectFeeCategoryLinkingData(
			FeeCategoryLinkingVO FeeCategoryLinkingVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	void insertFeeCategoryLinking(UserSessionDetails userSessionDetails,
			String[] selectedFeeType, ApplicationCache applicationCache,
			String feeCategory) throws NoDataFoundException,
			DuplicateEntryException, BatchUpdateFailedException,
			DatabaseException;

	Map<String, String> getUnallotedFeetype(
			UserSessionDetails userSessionDetails, String feeCategory)
			;

	Map<String, String> getAllotedFeetype(
			UserSessionDetails userSessionDetails, String feeCategory)
			;

	void deleteFeeCategory(String feeCategory, String feeType,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException;

	Map<String, String> getElectiveSubjects(
			UserSessionDetails userSessionDetails);

	
}
