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
import com.jaw.login.controller.DashboardConsolidatedVO;
import com.jaw.login.dao.DashboardConsolidated;
import com.jaw.login.dao.DashboardConsolidatedKey;
import com.jaw.login.dao.DashboardDao;
import com.jaw.login.dao.IDashboardDao;
import com.jaw.user.controller.UserVO;
import com.jaw.user.dao.IUserDao;
import com.jaw.user.dao.IUserLinkDao;
import com.jaw.user.dao.IUserProfileGroupList;
import com.jaw.user.dao.User;
import com.jaw.user.dao.UserLink;

@Service
public class DashboardService implements IDashboardService {

	Logger logger = Logger.getLogger(DashboardService.class);
	@Autowired
	IDashboardDao dashboardDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;

	
	@Override
	
	public DashboardConsolidatedVO selectDashboardRecord(String fromDate, String toDate, UserSessionDetails userSessionDetails) throws NoDataFoundException {

		DashboardConsolidatedKey dashboardConsolidatedKey=new DashboardConsolidatedKey();
		dashboardConsolidatedKey.setInstId(userSessionDetails.getInstId());
		dashboardConsolidatedKey.setBranchId(userSessionDetails.getBranchId());
		dashboardConsolidatedKey.setFromDate(fromDate);
		dashboardConsolidatedKey.setToDate(toDate);
		DashboardConsolidatedVO   consolidatedVO=new DashboardConsolidatedVO();
		DashboardConsolidated consolidated=dashboardDao.retriveDashboardDetails(dashboardConsolidatedKey);
		commonBusiness.changeObject(consolidatedVO, consolidated);
		return consolidatedVO;

		
	}

}
