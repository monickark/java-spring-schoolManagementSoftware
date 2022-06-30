package com.jaw.common.dropdown.dao;

import java.util.List;

import com.jaw.admin.dao.BranchMaster;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.CourseMaster;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IBranchListDAO {

	List<BranchMaster> selectBranchList(final UserSessionDetails userSessionDetails) throws NoDataFoundException;

}
