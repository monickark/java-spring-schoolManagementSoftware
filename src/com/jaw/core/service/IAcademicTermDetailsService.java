package com.jaw.core.service;

import java.util.List;

import com.jaw.common.exceptions.AcadTermUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.ExistPresentTermException;
import com.jaw.common.exceptions.ExistPreviousTermException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.AcademicTermDetailsVO;
import com.jaw.core.controller.AcademicTermMasterVO;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.core.dao.HolidayMaintenance;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

//Interface for Academic Term Details Service Class

public interface IAcademicTermDetailsService {
	
	void insertAcademicTermDetailsRec(AcademicTermDetailsVO acadTrmDetailVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws
			ExistPresentTermException, DatabaseException, DuplicateEntryException, NoDataFoundException, UpdateFailedException, ExistPreviousTermException;
	
	void updateAcademicTermDetailsRec(AcademicTermDetailsVO acadTrmDetailVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, AcadTermUpdateFailedException, ExistPresentTermException, ExistPreviousTermException;
	
	void deleteAcademicTermDetailsRec(AcademicTermDetailsVO acadTrmDetailVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
	
	void selectAcademicTermList(AcademicTermMasterVO academicTermMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;
	void updateDelFlg(AcademicTermDetails academicTermDetails,UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws NoDataFoundException, UpdateFailedException, DuplicateEntryException, DatabaseException;
}
