package com.jaw.common.dropdown.dao;

import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;

import com.jaw.framework.sessCache.UserSessionDetails;

public interface ITermAndSecListDAO {
	Map<String,String> selectTermList(final UserSessionDetails userSessionDetails) throws NoDataFoundException;
	Map<String,String> selectSectionList(
			final UserSessionDetails userSessionDetails,final String courseId,final String trmId) throws NoDataFoundException;
	Map<String,String> getsecAndTrmListFromStuGrpId(final UserSessionDetails userSessionDetails,final String stuGrpId) throws NoDataFoundException;
	 Map<String, Map<String,String>>  getsecListForBatch(
			final String courseId,final String trmId) throws NoDataFoundException;
}
