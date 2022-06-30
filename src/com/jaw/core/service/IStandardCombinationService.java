package com.jaw.core.service;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InsertFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.controller.StandardCombinationListVO;
import com.jaw.core.controller.StandardCombinationVO;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IStandardCombinationService {

	void deleteStandard(StandardCombinationListVO standardCombinationList,
			UserSessionDetails userSessionDetails) throws NoDataFoundException,
			DeleteFailedException;

	void insertStandard(StandardCombinationVO standard,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, InsertFailedException;

	void selectAllStandard(StandardCombinationVO StandardCombinationVO,
			String branchId, String instId) throws NoDataFoundException;

}
