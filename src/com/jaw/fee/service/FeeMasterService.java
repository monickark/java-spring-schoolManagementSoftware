package com.jaw.fee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.fee.controller.FeeCategoryLinkingListVO;
import com.jaw.fee.controller.FeeMasterSearchVO;
import com.jaw.fee.controller.FeeMasterVO;
import com.jaw.fee.dao.FeeCategoryLinkingList;
import com.jaw.fee.dao.FeeMaster;
import com.jaw.fee.dao.FeeMasterKey;
import com.jaw.fee.dao.FeeMasterList;
import com.jaw.fee.dao.FeeMasterStatus;
import com.jaw.fee.dao.FeeMasterStatusKey;
import com.jaw.fee.dao.IFeeCategoryLinkingListDao;
import com.jaw.fee.dao.IFeeMasterDao;
import com.jaw.fee.dao.IFeeMasterListDao;
import com.jaw.fee.dao.IFeeStatusDao;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;

@Service
public class FeeMasterService implements IFeeMasterService {
	@Autowired
	IFeeMasterListDao feeMasterListDao;
	@Autowired
	IFeeCategoryLinkingListDao feeCategoryLinkingListDao;
	@Autowired
	IFeeMasterDao feeMasterDao;
	@Autowired
	IFeeStatusDao feeStatusDao;
	@Autowired
	DoAudit doAudit;
	@Autowired
	CommonBusiness commonBusiness;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertFeeMasters(UserSessionDetails userSessionDetails,
			String[] feeAmount, String[] courseVariant,
			ApplicationCache applicationCache,
			List<FeeCategoryLinkingListVO> feeCategoryLinkingListVOs,
			FeeMasterSearchVO feeMasterSearchVO)
			throws DuplicateEntryException, BatchUpdateFailedException,
			DatabaseException {

		List<FeeMaster> batchup = new ArrayList<FeeMaster>();

		for (int i = 0; i < feeCategoryLinkingListVOs.size(); i++) {
			System.out.println("Fee Amount:" + feeAmount[i]);
			if (!feeAmount[i].equals("")) {
				FeeMaster feeMaster = new FeeMaster();
				feeMaster.setInstId(userSessionDetails.getInstId());
				feeMaster.setBranchId(userSessionDetails.getBranchId());
				feeMaster.setDelFlag("N");
				feeMaster.setAcTerm(feeMasterSearchVO.getAcTerm());
				feeMaster.setFeeType(feeCategoryLinkingListVOs.get(i)
						.getFeeType());
				feeMaster.setFeeCategory(feeCategoryLinkingListVOs.get(i)
						.getFeeCategory());
				if(courseVariant!=null){
				feeMaster.setCourseVariant(courseVariant[i]);
				}
				feeMaster.setFeeTerm(feeMasterSearchVO.getFeeTerm());
				feeMaster.setCourse(feeMasterSearchVO.getCourse());
				feeMaster.setTerm(feeMasterSearchVO.getTerm());
				feeMaster.setFeeAmt(Integer.parseInt(feeAmount[i]));
				feeMaster.setrCreId(userSessionDetails.getUserId());
				feeMaster.setrModId(userSessionDetails.getUserId());
				feeMaster.setDbTs(1);
				System.out.println("batch object added :"
						+ feeMaster.toString());
				batchup.add(feeMaster);
			}
		}

		feeMasterListDao.insertFeeMasterBatch(batchup);
		FeeMasterStatusKey feeStatusKey = new FeeMasterStatusKey();
		commonBusiness.changeObject(feeStatusKey, feeMasterSearchVO);
		feeStatusKey.setInstId(userSessionDetails.getInstId());
		feeStatusKey.setBranchId(userSessionDetails.getBranchId());
		try {
			feeStatusDao.selectFeeStatus(feeStatusKey);
		} catch (NoDataFoundException e) {
			FeeMasterStatus feeStatus = new FeeMasterStatus();
			commonBusiness.changeObject(feeStatus, feeMasterSearchVO);
			feeStatus.setInstId(userSessionDetails.getInstId());
			feeStatus.setBranchId(userSessionDetails.getBranchId());
			feeStatus.setFeeStatus(ApplicationConstant.FEE_STATUS_ENTERED);
			feeStatus.setDbTs(1);
			feeStatus.setDelFlag("N");
			feeStatus.setrCreId(userSessionDetails.getUserId());
			feeStatus.setrModId(userSessionDetails.getUserId());
			feeStatusDao.insertFeeStatus(feeStatus);
		}

		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.FEE_MASTER_ADDED, "");

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertFeeMaster(UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,
			FeeMasterSearchVO feeMasterSearchVO, FeeMasterVO feeMasterVO)
			throws DuplicateEntryException, BatchUpdateFailedException,
			DatabaseException {

		FeeMaster feeMaster = new FeeMaster();
		commonBusiness.changeObject(feeMaster, feeMasterVO);
		commonBusiness.changeObject(feeMaster, feeMasterSearchVO);
		feeMaster.setInstId(userSessionDetails.getInstId());
		feeMaster.setBranchId(userSessionDetails.getBranchId());
		feeMaster.setDelFlag("N");
		feeMaster.setrCreId(userSessionDetails.getUserId());
		feeMaster.setrModId(userSessionDetails.getUserId());
		feeMaster.setDbTs(1);
		System.out.println("batch object added :" + feeMaster.toString());

		feeMasterDao.insertFeeMaster(feeMaster);
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.FEE_MASTER_ADDED, "");

	}

	@Override
	public List<FeeMasterVO> selectfeeMasterList(
			FeeMasterSearchVO feeMasterSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		FeeMasterKey feeMasterKey = new FeeMasterKey();
		commonBusiness.changeObject(feeMasterKey, feeMasterSearchVO);
		feeMasterKey.setInstId(userSessionDetails.getInstId());
		feeMasterKey.setBranchId(userSessionDetails.getBranchId());
		List<FeeMasterList> FeeMasterLists = feeMasterListDao
				.getFeeMasterList(feeMasterKey);
		List<FeeMasterVO> feeMasters = new ArrayList<FeeMasterVO>();

		for (int i = 0; i < FeeMasterLists.size(); i++) {
			FeeMasterList feeMasterList = FeeMasterLists.get(i);
			FeeMasterVO FeeMasterVO = new FeeMasterVO();
			commonBusiness.changeObject(FeeMasterVO, feeMasterList);
			FeeMasterVO.setRowid(i);

			feeMasters.add(FeeMasterVO);

		}

		int count = selectfeeStatusLocked(feeMasterSearchVO, userSessionDetails);
		System.out.println("COunt is :" + count);
		if (count == 0) {
			feeMasterSearchVO.setIsFeeLocked("N");
		} else {
			feeMasterSearchVO.setIsFeeLocked("Y");
		}
		return feeMasters;
	}

	@Override
	public int selectfeeStatusLocked(FeeMasterSearchVO feeMasterSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		return feeStatusDao
				.checkFeeStatusLocked(userSessionDetails.getInstId(),
						userSessionDetails.getBranchId(),
						feeMasterSearchVO.getAcTerm(),
						feeMasterSearchVO.getFeeCategory(),

						feeMasterSearchVO.getCourse(),
						feeMasterSearchVO.getTerm(), "L");

	}

	@Override
	public List<FeeCategoryLinkingListVO> selectFeeCategoryList(
			FeeMasterSearchVO feeMasterSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		FeeMasterKey feeMasterKey = new FeeMasterKey();
		commonBusiness.changeObject(feeMasterKey, feeMasterSearchVO);
		feeMasterKey.setInstId(userSessionDetails.getInstId());
		feeMasterKey.setBranchId(userSessionDetails.getBranchId());
		List<FeeCategoryLinkingList> FeeMasterLists = feeCategoryLinkingListDao
				.getFeeCategoryList(feeMasterKey);
		List<FeeCategoryLinkingListVO> feeCategoryLinkingListVOs = new ArrayList<FeeCategoryLinkingListVO>();

		for (int i = 0; i < FeeMasterLists.size(); i++) {
			FeeCategoryLinkingList feeMasterList = FeeMasterLists.get(i);
			FeeCategoryLinkingListVO feeCategoryLinkingListVO = new FeeCategoryLinkingListVO();
			commonBusiness
					.changeObject(feeCategoryLinkingListVO, feeMasterList);
			feeCategoryLinkingListVO.setRowid(i);

			feeCategoryLinkingListVOs.add(feeCategoryLinkingListVO);

		}

		return feeCategoryLinkingListVOs;
	}

	@Override
	public void updateFeeMaster(FeeMasterVO FeeMasterVO,
			FeeMasterSearchVO feeMasterSearchVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws UpdateFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		FeeMasterKey feeMasterKey = new FeeMasterKey();
		commonBusiness.changeObject(feeMasterKey, feeMasterSearchVO);
		feeMasterKey.setInstId(userSessionDetails.getInstId());
		feeMasterKey.setBranchId(userSessionDetails.getBranchId());
		feeMasterKey.setFeeType(FeeMasterVO.getFeeType());
		feeMasterKey.setCourseVariant(FeeMasterVO.getCourseVariant());
		FeeMaster FeeMaster = feeMasterDao.selectFeeMaster(feeMasterKey);

		String tableKey = feeMasterKey.stringForDbAudit();
		String oldRecord = FeeMaster.stringForDbAudit();

		FeeMaster.setFeeAmt(FeeMasterVO.getFeeAmt());

		feeMasterDao.updateFeeMaster(FeeMaster, feeMasterKey);
		FeeMaster FeeMaster1 = feeMasterDao.selectFeeMaster(feeMasterKey);
		String newRecord = FeeMaster1.stringForDbAudit();
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.FEE_MASTER_UPDATED, "");
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.FEE_MASTER, tableKey, oldRecord,
				AuditConstant.TYPE_OF_OPER_UPDATE, newRecord, "");

	}

	@Override
	public void deleteFeeMasterDAORec(FeeMasterVO FeeMasterVO,
			FeeMasterSearchVO feeMasterSearchVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		FeeMasterKey FeeMasterKey = new FeeMasterKey();
		// map the UIObject to Pojo

		commonBusiness.changeObject(FeeMasterKey, FeeMasterVO);
		FeeMasterKey.setInstId(userSessionDetails.getInstId());
		FeeMasterKey.setBranchId(userSessionDetails.getBranchId());
		FeeMasterKey.setCourseVariant(FeeMasterVO.getCourseVariant());
		FeeMaster FeeMaster = feeMasterDao.selectFeeMaster(FeeMasterKey);
		String tableKey = FeeMasterKey.stringForDbAudit();
		String oldRecord = FeeMaster.stringForDbAudit();
		FeeMasterKey.setDbTs(FeeMaster.getDbTs());
		FeeMaster.setDelFlag("Y");
		try {
			feeMasterDao.deletFeeMaster(FeeMaster);
		} catch (UpdateFailedException e) {
			throw new DeleteFailedException();
		}

		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.FEE_MASTER_DELETED, "");
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.FEE_MASTER, tableKey, oldRecord,
				AuditConstant.TYPE_OF_OPER_DELETE, "", "");

	}

	@Override
	public List<FeeCategoryLinkingListVO> selectUnAllottedList(
			FeeMasterSearchVO feeMasterSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		FeeMasterKey feeMasterKey = new FeeMasterKey();
		commonBusiness.changeObject(feeMasterKey, feeMasterSearchVO);
		feeMasterKey.setInstId(userSessionDetails.getInstId());
		feeMasterKey.setBranchId(userSessionDetails.getBranchId());
		List<FeeCategoryLinkingList> FeeMasterLists = feeMasterListDao
				.getUnAllottedFeetypeList(feeMasterKey);
		List<FeeCategoryLinkingListVO> feeCategoryLinkingListVOs = new ArrayList<FeeCategoryLinkingListVO>();

		for (int i = 0; i < FeeMasterLists.size(); i++) {
			FeeCategoryLinkingList feeMasterList = FeeMasterLists.get(i);
			FeeCategoryLinkingListVO feeCategoryLinkingListVO = new FeeCategoryLinkingListVO();
			commonBusiness
					.changeObject(feeCategoryLinkingListVO, feeMasterList);
			feeCategoryLinkingListVO.setRowid(i);

			feeCategoryLinkingListVOs.add(feeCategoryLinkingListVO);

		}

		return feeCategoryLinkingListVOs;
	}

}
