package com.jaw.mark.service;

import java.util.Map;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.MainTestNotAddedFirstException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.controller.MarkSubjectLinkListVO;
import com.jaw.mark.controller.MarkSubjectLinkMasterVO;

public interface IMarkSubjectLinkService {

	Map<String, String> getTermDetailsBasedOnCourseId(String courseId,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	void selectMarkSubjectLinkingData(
			MarkSubjectLinkMasterVO MarkSubjectLinkMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	void insertMarkSubjectLinkRec(
			MarkSubjectLinkMasterVO markSubjectLinkMasterVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException, MainTestNotAddedFirstException;

	void updateMarkSubjectLinking(
			MarkSubjectLinkMasterVO markSubjectLinkMasterVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws UpdateFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, MainTestNotAddedFirstException;

	void deleteMarkSubjectLinkDAORec(MarkSubjectLinkListVO markSubjectLinkVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;

}
