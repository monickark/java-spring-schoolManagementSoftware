package com.jaw.core.service;



import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.controller.CourseMasterVO;
import com.jaw.core.controller.CourseMaster_MasterVO;
import com.jaw.core.controller.StudentGroupVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

//Interface for Course Master Service Class

public interface ICourseMasterService {
	void insertCourseMasterRec(CourseMasterVO corseMrVO,
			UserSessionDetails userSessionDetails) throws DuplicateEntryException, DatabaseException;
	
	void updateCourseMasterRec(CourseMasterVO corseMrVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
	public CourseMaster_MasterVO selectCourseMasterList(
			CourseMaster_MasterVO courseMaster_MasterVO,UserSessionDetails userSessionDetails) throws NoDataFoundException;
	public void deleteCourseMasterRec(CourseMasterVO courseMasterVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
}
