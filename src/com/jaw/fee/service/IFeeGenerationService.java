package com.jaw.fee.service;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FeeMasterExistException;
import com.jaw.common.exceptions.FeeMasterNotFoundException;
import com.jaw.common.exceptions.FeeMasterNotFoundForIntegratedCourseException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.StudentNotFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.fee.controller.FeeGenerationMasterVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

public interface IFeeGenerationService {

	
	String checkFeeStatus(String threadId,SessionCache sessionCache) throws  NoDataFoundException;

	String feeGenerate(ApplicationCache applicationCache,
			FeeGenerationMasterVO feeGenerationMasterVO,
			SessionCache sessionCache) throws FeeMasterNotFoundException,
			StudentNotFoundException, DuplicateEntryException,
			DatabaseException, UpdateFailedException, FeeMasterExistException,
			FeeMasterNotFoundForIntegratedCourseException, NoDataFoundException;
	
}
