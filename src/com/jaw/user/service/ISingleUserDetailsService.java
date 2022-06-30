package com.jaw.user.service;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mgmtUser.controller.MgmtUserVO;
import com.jaw.staff.controller.StaffVo;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.user.controller.BranchAdminVO;
import com.jaw.user.controller.SingleParentDetailsVO;

public interface ISingleUserDetailsService {

	String getSingleUser(ApplicationCache applicationCache, String userId,
			UserSessionDetails userSessionDetails, MgmtUserVO managementVO,
			BranchAdminVO branchAdminVO, StaffVo staffAdmissionVo,
			AdmissionDetailsVO admissionDetailsVO,
			SingleParentDetailsVO parentDetailsVO, String profileGroup)
			throws NoDataFoundException, InvalidUserIdException,
			PropertyNotFoundException;

}
