package com.jaw.common.dropdown.dao;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.dao.StaffDetails;

public interface ISubjectListBasedOnStudentGrpDAO {
	List<CourseSubLink> getSubListForStudentGrp(String stdGrpId,
			String staffId, UserSessionDetails userSessionDetails)
			throws NoDataFoundException;
}
