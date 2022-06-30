package com.jaw.nonStaff.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.login.controller.LoginController;
import com.jaw.nonStaff.dao.INonStaffDetailsDAO;
import com.jaw.nonStaff.dao.NonStaff;
import com.jaw.user.controller.BranchAdminVO;

@Service
public class NonStaffService implements INonStaffService {
	Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private CommonBusiness commonBussiness;
	@Autowired
	private INonStaffDetailsDAO nonStaffDetailsDAO;
	// Helper class to do Auditing
	@Autowired
	DoAudit doAudit;

	@Override
	public void getSingleNonStaff(BranchAdminVO branchAdminVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException,
			InvalidUserIdException {
		NonStaff nonStaff = nonStaffDetailsDAO.selectNonStaffRec(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(),
				userSessionDetails.getLinkId());
		commonBussiness.changeObject(branchAdminVO, nonStaff);
	}

}
