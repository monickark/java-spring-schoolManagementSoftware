package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ISpecialClassListDAO {
	public List<SpecialClass> getSpecialClassList(SpecialClassListKey specialClassListKey,UserSessionDetails userSessionDetails)
			throws NoDataFoundException;
	/*public List<CourseSubLink> getSubListForSplCLs(String stuGrpId,UserSessionDetails userSessionDetails)
			throws NoDataFoundException;*/
}
