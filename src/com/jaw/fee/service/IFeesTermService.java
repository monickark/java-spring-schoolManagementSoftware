package com.jaw.fee.service;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.HolidayGenerationVO;
import com.jaw.core.controller.StudentGroupVO;
import com.jaw.fee.controller.FeeTermVO;
import com.jaw.fee.dao.FeeTermsKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IFeesTermService {
	public Map<String,String> getFeeTermList(UserSessionDetails userSessionDetails) throws NoDataFoundException;
	public Map<String,String> getFeePaymentTermList(UserSessionDetails userSessionDetails) throws NoDataFoundException;
	public void inserttermFeesBatchRec(
			String[] feeTerm,String feePaymentTerm,UserSessionDetails userSessionDetails) throws   DuplicateEntryException, DatabaseException, NoDataFoundException, UpdateFailedException;
	public List<FeeTermVO> getFeeTermsList(ApplicationCache applicationCache,SessionCache sessionCache) throws NoDataFoundException;
	public void deleteFeeTermRec(
			String[] feeTerm,String feePaymentTerm,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws  DeleteFailedException, DuplicateEntryException, DatabaseException;
	//FeesTermVO
}
