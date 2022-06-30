package com.jaw.analytics.service;

import com.jaw.analytics.controller.ViewAnalyticMark;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mark.dao.StuMrksRmksListKey;

public interface IAnalyticMarkService {
	ViewAnalyticMark selectStudentSubjectMarksForBarChart(
			StuMrksRmksListKey stuMrksRmksListKey,ApplicationCache applicationCache,SessionCache sessionCache) throws NoDataFoundException, CommonCodeNotFoundException;
}
