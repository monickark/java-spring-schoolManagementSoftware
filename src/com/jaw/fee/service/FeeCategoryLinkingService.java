package com.jaw.fee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.fee.controller.FeeCategoryLinkingVO;
import com.jaw.fee.dao.FeeCategoryLinking;
import com.jaw.fee.dao.FeeCategoryLinkingKey;
import com.jaw.fee.dao.IFeeCategoryLinkingDao;
import com.jaw.fee.dao.IFeeCategoryLinkingListDao;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

//course classes Details Service Class
@Service
public class FeeCategoryLinkingService implements IFeeCategoryLinkingService {
	// Logging
	Logger logger = Logger.getLogger(FeeCategoryLinkingService.class);

	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	DoAudit doAudit;
	@Autowired
	CommonCodeUtil commonCodeUtil;
	@Autowired
	IFeeCategoryLinkingDao feeCategoryLinkingDao;
	@Autowired
	IFeeCategoryLinkingListDao feeCategoryLinkingListDao;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;

	@Override
	public void selectFeeCategoryLinkingData(
			FeeCategoryLinkingVO FeeCategoryLinkingVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertFeeCategoryLinking(UserSessionDetails userSessionDetails,
			String[] selectedFeeType, ApplicationCache applicationCache,
			String feeCategory) throws NoDataFoundException,
			DuplicateEntryException, BatchUpdateFailedException,
			DatabaseException {

		List<FeeCategoryLinking> batchup = new ArrayList<FeeCategoryLinking>();

		for (int i = 0; i < selectedFeeType.length; i++) {
			System.out.println("Selected fee type :"+selectedFeeType[i]);
			String[] split = selectedFeeType[i].split("/");
			String feeType = split[0];
			System.out.println("split length :" + split.length);
			String elective = "";
			if (split.length == 1) {
				elective = "";
			} else {
				elective = selectedFeeType[i].substring(split[0].length()+1, selectedFeeType[i].length());
				System.out.println("Elective :"+elective);
			}
			FeeCategoryLinking feeCategoryLinking = new FeeCategoryLinking();
			feeCategoryLinking.setInstId(userSessionDetails.getInstId());
			feeCategoryLinking.setBranchId(userSessionDetails.getBranchId());
			feeCategoryLinking.setDelFlag("N");
			feeCategoryLinking.setFeeType(feeType);
			feeCategoryLinking.setFeeCategory(feeCategory);
			feeCategoryLinking.setIsElective(elective);
			feeCategoryLinking.setrCreId(userSessionDetails.getUserId());
			feeCategoryLinking.setrModId(userSessionDetails.getUserId());
			feeCategoryLinking.setDbTs(1);
			System.out.println("batch object added :"
					+ feeCategoryLinking.toString());
			batchup.add(feeCategoryLinking);
		}

		feeCategoryLinkingListDao.insertFeeCategoryBatch(batchup);
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.FEE_CATEGORY_LINKING_ADDED, "");

	}

	@Override
	public Map<String, String> getUnallotedFeetype(
			UserSessionDetails userSessionDetails, String feeCategory) {
		try {
			return feeCategoryLinkingListDao.getUnAllottedFeeType(
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId(), feeCategory);
		} catch (NoDataFoundException e) {
			return null;
		}
	}

	@Override
	public Map<String, String> getAllotedFeetype(
			UserSessionDetails userSessionDetails, String feeCategory) {
		try {
			return feeCategoryLinkingListDao.getAllottedFeeCategory(
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId(), feeCategory);
		} catch (NoDataFoundException e) {
			return null;
		}
	}
	
	@Override
	public Map<String, String> getElectiveSubjects(
			UserSessionDetails userSessionDetails) {
		try {
			return feeCategoryLinkingListDao.getElectiveSubjects(
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId());
		} catch (NoDataFoundException e) {
			return null;
		}
	}

	@Override
	public void deleteFeeCategory(String feeCategory, String feeType,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {
		logger.debug("inside delete course classes Details method");

		FeeCategoryLinking feeCategoryLinking = new FeeCategoryLinking();
		FeeCategoryLinkingKey feeCategoryLinkingKey = new FeeCategoryLinkingKey();
		// map the UIObject to Pojo
		feeCategoryLinkingKey.setInstId(userSessionDetails.getInstId());
		feeCategoryLinkingKey.setBranchId(userSessionDetails.getBranchId());
		feeCategoryLinkingKey.setFeeCategory(feeCategory);
		feeCategoryLinkingKey.setFeeType(feeType);
		feeCategoryLinking = feeCategoryLinkingDao
				.selectFeeCategoryLinking(feeCategoryLinkingKey);

		String tableKey = feeCategoryLinkingKey.getStringForAudit();
		String oldRecord = feeCategoryLinking.stringForDbAudit();
		feeCategoryLinkingKey.setDbTs(feeCategoryLinking.getDbTs());
		feeCategoryLinkingDao.deleteFeeCategoryLinking(feeCategoryLinkingKey);
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.FEE_CATEGORY_LINKING_DELETED, "");
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.FEE_CATEGORY_LINKING, tableKey, oldRecord,
				AuditConstant.TYPE_OF_OPER_DELETE, "", "");
	}

}
