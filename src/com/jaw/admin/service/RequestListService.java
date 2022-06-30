package com.jaw.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.admin.controller.RequestListVo;
import com.jaw.admin.dao.IBatchProcessPwdResetRequestDAO;
import com.jaw.admin.dao.IRequestDao;
import com.jaw.admin.dao.IRequestListDao;
import com.jaw.admin.dao.Request;
import com.jaw.admin.dao.RequestList;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.MenuProfileUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.user.dao.IUserDao;
import com.jaw.user.dao.IUserLinkDao;
import com.jaw.user.dao.IUserProfileGroupList;
import com.jaw.user.dao.User;

@Service
public class RequestListService implements IRequestListService {

	Logger logger = Logger.getLogger(RequestListService.class);

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

	// Select all the request

	@Override
	public List<RequestListVo> selectAllRequestList(
			ApplicationCache applicationCache, SessionCache sessionCache,
			RequestListVo requestVo, UserSessionDetails userSessionDetails,
			String url) throws NoDataFoundException {

		Request request = new Request();
		commonBusiness.changeObject(request, requestVo);
		System.out.println("user session details :" + userSessionDetails
				+ " url:" + url + " application cache :" + applicationCache);
		System.out.println("Menu option :"
				+ menuProfileUtil.getMenuOption(userSessionDetails, url,
						applicationCache));
		requestVo.setMenuOptionId(menuProfileUtil.getMenuOption(
				userSessionDetails, url, applicationCache));

		// Select the request list based on menu option

		List<Request> requests = requestListDao.getRequestsList(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), requestVo.getReqstType(),
				requestVo.getReqstStatus(), requestVo.getMenuOptionId());

		// Change the list to vo object

		List<RequestListVo> requestVos = new ArrayList<RequestListVo>();
		for (Request vo : requests) {
			RequestListVo request1 = new RequestListVo();
			commonBusiness.changeObject(request1, vo);
			requestVos.add(request1);
		}
		logger.debug("Request returning size from service :" + requests.size());
		return requestVos;

	}

	@Override
	public List<RequestListVo> selectPendingRequestRecords(
			RequestListVo requestVo, UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache, String url)
			throws NoDataFoundException {

		// Create DB object
		Request request = new Request();
		commonBusiness.changeObject(request, requestVo);
		request.setReqstStatus(CommonCodeConstant.REQUEST_STATUS_CLOSED);

		// Select all profile group group from user table to identify the tables
		// and column to fetched for different users

		List<String> profileGroups = UserMenuProfileList.selectProfileGroup(
				userSessionDetails.getInstId(), userSessionDetails
						.getBranchId(), menuProfileUtil.getMenuOption(
						userSessionDetails, url, applicationCache));

		String tableName = null;
		String columnName = null;
		String linkId = null;
		String profile = null;
		String menuProfile = null;
		int rowId = 0;

		List<RequestList> requests = new ArrayList<RequestList>();
		List<RequestListVo> requestVos = new ArrayList<RequestListVo>();
		// Assigning tableName columnName linkId profile for each users based on
		// profile group

		for (String profile1 : profileGroups) {

			switch (ProfileGroup.valueOf(profile1)) {

			case PAR:
				tableName = TableNameConstant.PARENT_DETAILS;
				columnName = TableNameConstant.PARENT_NAME;
				linkId = TableNameConstant.PARENT_LINK_ID;
				profile = profile1;
				menuProfile = "PARNT";
				break;

			case STF:
				tableName = TableNameConstant.STAFF_MASTER;
				columnName = TableNameConstant.STAFF_NAME;
				linkId = TableNameConstant.STAFF_LINK_ID;
				profile = profile1;
				menuProfile = "STAFF";
				break;

			case STU:
				tableName = TableNameConstant.STUDENT_MASTER;
				columnName = TableNameConstant.STUDENT_NAME;
				linkId = TableNameConstant.STUDENT_LINK_ID;
				profile = profile1;
				menuProfile = "STUDT";
				break;

			case NSF:
				tableName = TableNameConstant.NONSTAFF;
				columnName = TableNameConstant.NON_STAFF_NAME;
				linkId = TableNameConstant.NON_STAFF_LINK_ID;
				profile = profile1;
				break;

			case MGT:
				tableName = TableNameConstant.MANAGEMENT;
				columnName = TableNameConstant.MANAGEMENT_NAME;
				linkId = TableNameConstant.MANAGEMENT_LINK_ID;
				profile = profile1;
				break;

			}

			// Select all the request from the database

			requestVo.setMenuOptionId(menuProfileUtil.getMenuOption(
					userSessionDetails, url, applicationCache));

			try {
				requests = requestListDao.selectPendingRequestRecords(
						request.getReqstType(), request.getReqstStatus(),
						userSessionDetails.getBranchId(),
						userSessionDetails.getInstId(), profile, columnName,
						tableName, linkId, requestVo.getMenuOptionId(),
						menuProfile);
				System.out.println("requests size inside try:"
						+ requests.size());
			} catch (NoDataFoundException e) {
				System.out
						.println("No records found for selectPendingRequestRecords");
			}

			if (requests != null) {
				for (RequestList req : requests) {
					System.out.println("requests size :" + requests.size());
					RequestListVo request1 = new RequestListVo();
					commonBusiness.changeObject(request1, req);
					request1.setRowid(rowId);
					requestVos.add(request1);
					rowId++;
					System.out
							.println("requests size fin:" + requestVos.size());

				}
			} else {
				requests.removeAll(requests);
			}

		}

		logger.debug("Pending request List fetched size :" + requestVos.size());
		if (requestVos.size() == 0) {
			throw new NoDataFoundException();
		}
		return requestVos;

	}

	// Process the pending records

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void processRequest(RequestListVo requestVo,
			UserSessionDetails userSessionDetails,
			List<RequestListVo> passwordRequestList, String[] selectedRowIds)
			throws NoDataFoundException, DuplicateEntryException,
			BatchUpdateFailedException, DatabaseException {

		List<RequestList> batchup = new ArrayList<RequestList>();
		List<User> userBatch = new ArrayList<User>();
		String[] userIds = new String[selectedRowIds.length];
		// Create Database object to update

		for (int i = 0; i < selectedRowIds.length; i++) {
			String rowId = selectedRowIds[i];

			logger.debug("Slected row id to process:" + rowId);
			RequestList request = new RequestList();
			commonBusiness.changeObject(request,
					passwordRequestList.get(Integer.parseInt(rowId)));
			userIds[i] = request.getUserId();
			request.setReqstStatus(CommonCodeConstant.REQUEST_STATUS_CLOSED);
			request.setrModId(userSessionDetails.getUserId());
			batchup.add(request);
			logger.debug("Request value for id :" + rowId + " userId :"
					+ request.getUserId() + " Inst Id: " + request.getInstId()
					+ " Branch Id:" + request.getBranchId() + " DbTS :"
					+ request.getReqDbTs());

			User user = new User();
			user.setPassword(commonBusiness.createPassword(request.getUserId()));
			user.setPasswordResetFlag("Y");
			user.setUserEnableFlag("Y");
			user.setrModId(userSessionDetails.getUserId());
			user.setUserId(request.getUserId());
			user.setInstId(request.getInstId());
			user.setBranchId(request.getBranchId());
			user.setDb_ts(request.getReqDbTs());
			userBatch.add(user);
		}

		// Update the user and request table

		requestListDao.updateBatch(batchup);
		batchUserUpdate.updateBatch(userBatch);

		// On success do functional audit

		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.PENDING_REQUEST_UPDATE_SUCCESS, "", userIds);

	}

}
