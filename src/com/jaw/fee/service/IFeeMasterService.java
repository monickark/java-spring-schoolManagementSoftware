package com.jaw.fee.service;

import java.util.List;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.fee.controller.FeeCategoryLinkingListVO;
import com.jaw.fee.controller.FeeMasterSearchVO;
import com.jaw.fee.controller.FeeMasterVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IFeeMasterService {

	 List<FeeMasterVO> selectfeeMasterList(FeeMasterSearchVO feeMasterSearchVO,	UserSessionDetails userSessionDetails) throws NoDataFoundException ;

	List<FeeCategoryLinkingListVO> selectFeeCategoryList(
			FeeMasterSearchVO feeMasterSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;
	List<FeeCategoryLinkingListVO> selectUnAllottedList(
			FeeMasterSearchVO feeMasterSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	void updateFeeMaster(FeeMasterVO FeeMasterVO,
			FeeMasterSearchVO feeMasterSearchVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws UpdateFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException;

	void deleteFeeMasterDAORec(FeeMasterVO FeeMasterVO,
			FeeMasterSearchVO feeMasterSearchVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException;

	int selectfeeStatusLocked(
			FeeMasterSearchVO feeMasterSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	void insertFeeMasters(UserSessionDetails userSessionDetails,
			String[] feeAmount, String[] courseVariant,
			ApplicationCache applicationCache,
			List<FeeCategoryLinkingListVO> feeCategoryLinkingListVOs,
			FeeMasterSearchVO feeMasterSearchVO)
			throws DuplicateEntryException, BatchUpdateFailedException,
			DatabaseException;

	void insertFeeMaster(UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,
			FeeMasterSearchVO feeMasterSearchVO, FeeMasterVO feeMasterVO)
			throws DuplicateEntryException, BatchUpdateFailedException,
			DatabaseException;

	
		

}
