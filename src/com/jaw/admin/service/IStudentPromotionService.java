package com.jaw.admin.service;

import com.jaw.admin.controller.StudentPromotionVO;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.StudentPromoteException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IStudentPromotionService {
int checkAcademicTermStatus(StudentPromotionVO studentPromotionVO,UserSessionDetails userSessionDetails,String term);
String studentPromoted(StudentPromotionVO studentPromotionVO,SessionCache sessionCache,ApplicationCache applicationCache) throws NoDataFoundException, DuplicateEntryException, CommonCodeNotFoundException, DatabaseException, UpdateFailedException, StudentPromoteException;
String checkAcademicTermPromotiontatus(String threadId,String branchId,SessionCache sessionCache) throws  NoDataFoundException;
}
