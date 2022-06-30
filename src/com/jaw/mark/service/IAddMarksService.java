package com.jaw.mark.service;

import java.util.List;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.StudentNotFoundForMarkException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.controller.AddMarksMasterVO;
import com.jaw.mark.controller.MarkSubjectLinkListForAddMarksVO;
import com.jaw.mark.controller.StudentListForAddMarksVO;
import com.jaw.mark.controller.StudentMarksVO;
import com.jaw.mark.dao.MarkSubjectLinkKey;

public interface IAddMarksService {
public void getMarkSubjectListForAdd(AddMarksMasterVO addMarksMasterVO,UserSessionDetails userSessionDetails)
		throws NoDataFoundException ;
public void getStudentListForAddMarks(AddMarksMasterVO addMarksMasterVO,UserSessionDetails userSessionDetails,MarkSubjectLinkListForAddMarksVO markSubLinkVo,String add) throws NoDataFoundException, StudentNotFoundForMarkException;
public void saveMarkdetails(AddMarksMasterVO addMarksMasterVO,UserSessionDetails userSessionDetails,String mkslId,String markOrGrade,List<StudentListForAddMarksVO> markList,String subject,String examId,ApplicationCache applicationCache) throws DuplicateEntryException, NoDataFoundException, UpdateFailedException, DatabaseException, TableNotSpecifiedForAuditException ;
public void updateStudentMarks(StudentMarksVO studentMarksVO,UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException ;
		
}
