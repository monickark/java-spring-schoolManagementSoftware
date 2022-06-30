package com.jaw.common.dropdown.dao;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IAllSubListByCIDAndTRM {
	Map<String,CourseSubLink> getSubListFromCrsl(final UserSessionDetails userSessionDetails,final String courseId,final String trmId) throws NoDataFoundException;
	Map<String,CourseSubLink> getSubListFromCrslAndSbjm(final UserSessionDetails userSessionDetails,final String stuGrpId) throws NoDataFoundException;
	Map<String,String> getSubList(final UserSessionDetails userSessionDetails,final String stuGrpId,final String subType) throws NoDataFoundException;
	
	List<CourseSubLink> getAllSubjectListForBatch(final String INSTID,final String BRANCHID) throws NoDataFoundException;
}
