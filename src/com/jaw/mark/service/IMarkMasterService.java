package com.jaw.mark.service;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.ExamOrderExistException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.controller.MarkMasterVO;
import com.jaw.mark.controller.MarkMtrMasterVO;
import com.jaw.mark.controller.ReportCardMasterVo;

public interface IMarkMasterService {

	void insertMarkMasterRec(MarkMasterVO markMasterVO,
			UserSessionDetails userSessionDetails) throws DuplicateEntryException, DatabaseException, ExamOrderExistException ;
	
	public void updateMarkMasterRec(MarkMasterVO markMasterVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException,NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
	public MarkMtrMasterVO selectmarkMasterList(
			MarkMtrMasterVO markMtrMasterVO,ReportCardMasterVo reportCardMasterVo, UserSessionDetails userSessionDetails)
			throws NoDataFoundException;
	public void deleteMarkMasterRec(
			MarkMasterVO markMasterVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException ;

	MarkMtrMasterVO selectmarkMasterList(MarkMtrMasterVO markMtrMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;
}
	
