package com.jaw.core.service;

import java.util.List;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.SpecialClsDeleteFailedException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.SpecialClassMasterVO;
import com.jaw.core.controller.SpecialClassVO;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ISpecialClassService {
	void insertSpecialClassRec(SpecialClassVO splClsVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException;

	void updateSpecialClassRec(SpecialClassVO splClsVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;

	public SpecialClassMasterVO selectSpecialClassList(
			SpecialClassMasterVO specialClassMasterVO,UserSessionDetails usersessiondetails)
			throws NoDataFoundException;

	public void deleteSpecialClassRec(SpecialClassVO specialClassVO,UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, SpecialClsDeleteFailedException;
	 List<String> selectSubjectListForSplCls(String stdGrpId,UserSessionDetails userSessionDetails)
				;
}
