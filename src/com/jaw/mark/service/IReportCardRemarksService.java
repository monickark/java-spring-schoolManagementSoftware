package com.jaw.mark.service;

import com.jaw.common.exceptions.AllMarksNotEnteredException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.controller.ReportCardMasterVo;

public interface IReportCardRemarksService {

	void selectStuMarksStatus(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails) throws NoDataFoundException,
			AllMarksNotEnteredException;

	void selectMarkListForRmksAdd(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails, String var)
			throws NoDataFoundException;

	void insertStudentExamRemarks(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DuplicateEntryException,
			NoDataFoundException, UpdateFailedException, DatabaseException,
			TableNotSpecifiedForAuditException;

	void updateStudentExamRemarks(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DuplicateEntryException,
			NoDataFoundException, UpdateFailedException, DatabaseException,
			TableNotSpecifiedForAuditException;

	void marksLockandClose(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails, String action,
			ApplicationCache applicationCache) throws NoDataFoundException,
			UpdateFailedException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException;

	void reportCardPublish(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws NoDataFoundException,
			UpdateFailedException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException;

	void reportCardGeneration(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws UpdateFailedException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, FileNotFoundInDatabase,
			NoDataFoundException;

}
