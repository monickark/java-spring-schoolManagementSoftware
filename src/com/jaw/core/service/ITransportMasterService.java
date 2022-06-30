package com.jaw.core.service;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.TransportMasterVO;
import com.jaw.core.controller.TransportMaster_MasterVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ITransportMasterService {

	void insertTransportMasterRec(TransportMasterVO transportMrVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException;

	void updateTransportMasterRec(TransportMasterVO transportMrVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws UpdateFailedException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException;

	TransportMaster_MasterVO selectTransportMasterList(
			TransportMaster_MasterVO TransportMaster_MasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	void deleteTransportMasterRec(TransportMasterVO TransportMasterVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException;

}
