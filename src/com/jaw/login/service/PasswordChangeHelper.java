package com.jaw.login.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.dao.IAuditDao;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.user.controller.UserVO;
import com.jaw.user.dao.User;

@Component
public class PasswordChangeHelper {
	@Autowired
	IAuditDao auditDaoImpl;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	// Helper class to do Auditing
	@Autowired
	DoAudit doAudit;
	Logger logger = Logger.getLogger(PasswordChangeHelper.class);

	public void updatePasswordChangeUser(UserVO userVO, User user,
			ApplicationCache applicationCache) throws DuplicateEntryException,
			DatabaseException, NumberFormatException, PropertyNotFoundException {
		UserSessionDetails userSessionDetails = new UserSessionDetails();
		userSessionDetails.setInstId(user.getInstId());
		userSessionDetails.setBranchId(user.getBranchId());
		userSessionDetails.setUserId(user.getUserId());
		userSessionDetails.setIpAddress(userVO.getIpAddress());
		int days = Integer.parseInt(propertyManagementUtil.getPropertyValue(
				applicationCache, user.getInstId(), user.getBranchId(),
				PropertyManagementConstant.PWD_EXPIRY_PRD).toString());
		user.setPasswordExpiryDate(dateUtil.addDaysToCurrentDate(days));
		user.setNoOfAttempts(0);
		user.setPasswordResetDate(user.getCurrentDate());
		user.setPasswordResetFlag("N");

		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.PASSWORD_CHANGE, " ");

	}

}
