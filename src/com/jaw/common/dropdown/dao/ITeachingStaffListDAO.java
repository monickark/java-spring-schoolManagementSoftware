package com.jaw.common.dropdown.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.dao.StaffDetails;

public interface ITeachingStaffListDAO {
	List<StaffDetails> selectTeachingStaffList(final UserSessionDetails userSessionDetails) throws NoDataFoundException;
}
