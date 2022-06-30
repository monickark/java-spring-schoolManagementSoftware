package com.jaw.communication.service;

import java.util.List;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.communication.controller.AlertMasterVO;
import com.jaw.communication.controller.AlertVO;
import com.jaw.communication.dao.AlertListKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IAlertService {
	void insertAlertDetailsRec(AlertMasterVO alertMasterVO,
			UserSessionDetails userSessionDetails) throws
			 DatabaseException, DuplicateEntryException, NoDataFoundException, UpdateFailedException;
	
	void updateAlertDetailsRec(AlertVO alertVo,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, NoDataFoundException;
	
	void deleteAlertDetailsRec(AlertVO alertVo,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
	void stopAlertDetailsRec(AlertVO alertVo,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, NoDataFoundException;
	void selectAlertList(AlertMasterVO alertMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;
	List<AlertVO> selectAlertListForDashBoard(AlertListKey alertListKey,UserSessionDetails userSessionDetails) throws NoDataFoundException;
}
