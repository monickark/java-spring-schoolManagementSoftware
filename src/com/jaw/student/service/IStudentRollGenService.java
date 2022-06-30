package com.jaw.student.service;

import java.util.List;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.SectionNotAllocatedException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.controller.StudentRollSearchVO;

public interface IStudentRollGenService {

	List<StudentMaster> getStudentList(ApplicationCache applicationCache,
			StudentRollSearchVO studentRollSearchVO,
			UserSessionDetails userSessionDetails) throws DatabaseException,
			DuplicateEntryException, NoDataFoundException, BatchUpdateFailedException, SectionNotAllocatedException ;	
	public Integer checkForRollNoGeneration(UserSessionDetails userSessionDetails,String acTrm,String sGrpId);
}
