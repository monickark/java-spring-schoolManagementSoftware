package com.jaw.student.service;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InvalidCategoryForUpdateException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.controller.StudentTransportMasterVO;

public interface IStudentTransportService {

	void getStuListForTransport(UserSessionDetails sessionDetails,
			StudentTransportMasterVO StudentTransportMasterVO)
			throws NoDataFoundException, InvalidCategoryForUpdateException;

	
	void insertStuList(UserSessionDetails sessionDetails,
			StudentTransportMasterVO StudentTransportMasterVO)
			throws BatchUpdateFailedException, DuplicateEntryException,
			DatabaseException, DataIntegrityException, RuntimeExceptionForBatch;

}
