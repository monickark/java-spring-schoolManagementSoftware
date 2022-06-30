package com.jaw.login.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.admin.dao.IBatchProcessPwdResetRequestDAO;
import com.jaw.admin.dao.IRequestDao;
import com.jaw.admin.dao.IRequestListDao;
import com.jaw.admin.dao.Request;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.RequestNotAcceptedException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.login.UserDisabledException;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.MenuProfileUtil;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.user.controller.UserVO;
import com.jaw.user.dao.IUserDao;
import com.jaw.user.dao.IUserLinkDao;
import com.jaw.user.dao.IUserProfileGroupList;
import com.jaw.user.dao.User;
import com.jaw.user.dao.UserLink;

@Service
public class RequestService implements IRequestService {

	Logger logger = Logger.getLogger(RequestService.class);

	@Autowired
	private IRequestListDao requestListDao;
	@Autowired
	private IRequestDao requestDao;
	@Autowired
	private CommonBusiness commonBusiness;
	@Autowired
	IBatchProcessPwdResetRequestDAO batchUserUpdate;
	@Autowired
	IUserLinkDao userLinkDao;
	@Autowired
	DoAudit doAudit;
	@Autowired
	IUserDao userDao;
	@Autowired
	IUserProfileGroupList UserMenuProfileList;
	@Autowired
	MenuProfileUtil menuProfileUtil;
	@Autowired
	DateUtil dateUtil;

	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;

	// Enum for menu profiles

	public enum ProfileGroup {
		STU, PAR, STF, NSF, MGT
	};

	// Process password request

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertRequest(UserVO userVo) throws DuplicateEntryException,
			RequestNotAcceptedException, DatabaseException,
			InvalidUserIdException, UpdateFailedException,
			UserDisabledException {

		User user = null;
		UserLink userLink = null;
		try {
			user = userDao.validateUserId(userVo.getUserId());
			if (user.getUserEnableFlag().equals("N")) {
				throw new UserDisabledException();
			}
			userLink = userLinkDao.getUserDetails(userVo.getUserId());
		} catch (InvalidUserIdException e) {
			throw e;
		}

		UserSessionDetails userSessionDetails = new UserSessionDetails();
		userSessionDetails.setInstId(user.getInstId());
		userSessionDetails.setBranchId(user.getBranchId());
		userSessionDetails.setUserId(userVo.getUserId());

		if (userLink.getUserMenuProfile().equals(
				ApplicationConstant.SUPER_ADMIN)) {
			logger.error("Super admin requested password");
			throw new RequestNotAcceptedException();
		} else {

			try {
				requestDao.selectRequestRecordBasedonTypeAndStatus(user.getInstId(),
						user.getBranchId(), user.getUserId(),
						ApplicationConstant.REQUEST_TYPE_NEW_PASSWORD,
						ApplicationConstant.REQUEST_STATUS_REQUESTED);
				throw new DuplicateEntryException();
			} catch (NoDataFoundException e) {

				user.setrModId(user.getUserId());
				user.setPasswordResetFlag("R");
				userDao.updateUser(user);

				Request request = new Request();
				request.setUserId(userVo.getUserId());
				request.setInstId(user.getInstId());
				request.setBranchId(user.getBranchId());
				request.setReqstStatus(CommonCodeConstant.REQUEST_STATUS_REQUESTED);
				request.setReqstType(CommonCodeConstant.REQUEST_TYPE_PASSWORD_REQUEST);
				request.setRequestSerialNumber(simpleIdGenerator.getNextId(
						SequenceConstant.PASSWORD_REQUEST_SEQUENCE, 1)
						.toString());
				request.setrCreId(user.getUserId());
				request.setDelFlg("N");
				request.setrModId(user.getUserId());
				request.setDbTs(1);

				// request inserted successfully
				requestDao.insertRequest(request);

				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.REQUESTED_FOR_PASSWORDRESET,
						"Requested id :" + "");
			}
		}
	}

}
