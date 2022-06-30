package com.jaw.communication.service;

import java.text.ParseException;
import java.util.List;

import com.jaw.common.exceptions.AcadTermUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.ExistPresentTermException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.communication.controller.NoticeBoardMasterVO;
import com.jaw.communication.controller.NoticeBoardVO;
import com.jaw.communication.dao.NoticeBoard;
import com.jaw.communication.dao.NoticeBoardListKey;
import com.jaw.core.controller.AcademicTermDetailsVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface INoticeBoardService {
	void insertNoticeBoardDetailsRec(NoticeBoardVO noticeBoardVO,
			UserSessionDetails userSessionDetails) throws
			 DatabaseException, DuplicateEntryException, NoDataFoundException, UpdateFailedException;
	
	void updateNoticeBoardDetailsRec(NoticeBoardVO noticeBoardVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, NoDataFoundException;
	
	void deleteNoticeBoardDetailsRec(NoticeBoardVO noticeBoardVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
	
	void selectNoticeBoardList(NoticeBoardMasterVO noticeBoardMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;
	List<NoticeBoardVO> selectNoticeBoardListForDashBoard(NoticeBoardListKey noticeBoardListKey,UserSessionDetails userSessionDetails) throws NoDataFoundException;
}
