package com.jaw.nonStaff.service;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.user.controller.BranchAdminVO;

public interface INonStaffService {

	void getSingleNonStaff(BranchAdminVO branchAdminVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException,
			InvalidUserIdException;

}
