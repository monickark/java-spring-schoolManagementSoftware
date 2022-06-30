package com.jaw.admin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.admin.controller.BranchMasterListVO;
import com.jaw.admin.controller.BranchMasterVO;
import com.jaw.admin.dao.BranchMaster;
import com.jaw.admin.dao.BranchMasterKey;
import com.jaw.admin.dao.BranchMasterList;
import com.jaw.admin.dao.IBranchMasterDAO;
import com.jaw.admin.dao.IBranchMasterListDAO;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.files.service.FileMasterHelper;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.SessionCache;

@Service
public class BranchMasterService implements IBranchMasterService {

	Logger logger = Logger.getLogger(BranchMasterService.class);
	@Autowired
	IBranchMasterDAO branchMasterDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IBranchMasterListDAO branchMasterList;	
	@Autowired
	FileMasterHelper fileMasterHelper;
	@Autowired
	DoAudit doAudit;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	IIdGeneratorService simpleIdGenerator;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertIntoBranchMaster(ApplicationCache applicationCahce,
			BranchMasterVO branchVO, SessionCache session,ServletContext servletContext)
			throws DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {
		logger.debug("inside insertIntoInstMaster method");
		Integer sequence = simpleIdGenerator.getNextId(
				SequenceConstant.BRANCH_ID_SEQUENCE, 1);
		String branchIdSequence = AlphaSequenceUtil.generateAlphaSequence(
				ApplicationConstant.STRING_BRANCH_SEQ, sequence);
		BranchMaster branchMaster = new BranchMaster();
		commonBusiness.changeObject(branchMaster, branchVO);
		branchMaster.setDbTs(1);
		branchMaster.setDelFlag("N");
		branchMaster.setBranchId(branchIdSequence);
		branchMaster.setInstId(session.getUserSessionDetails().getInstId());
		branchMaster.setrCreId(session.getUserSessionDetails().getUserId());
		branchMaster.setrModId(session.getUserSessionDetails().getUserId());
		try {

			fileMasterHelper.fileUpload(applicationCahce, branchVO
					.getBranchLogo(),
					ApplicationConstant.INSTITUTE_BRANCH_LOGO_KEY, 1, session
							.getUserSessionDetails(), session
							.getUserSessionDetails().getUserId(),
					branchIdSequence,ApplicationConstant.DEFAULT_FILE_SRL_NO,"",servletContext);
		} catch (FileNotFoundInDatabase e) {
			e.printStackTrace();
		}
		branchMasterDao.insertBranchMasterDetails(branchMaster);
		// functional audit
		doAudit.doFunctionalAudit(session.getUserSessionDetails(),
				AuditConstant.BRCM_INSERT_SUCCESS, "");
		logger.info("Completed Functional Auditing");

		// Database audit
		String newRecString = branchMaster.toStringForAuditBranchMasterRecord();
		doAudit.doDatabaseAudit(applicationCahce,
				session.getUserSessionDetails(),
				TableNameConstant.BRANCH_MASTER,
				branchMaster.toStringForAuditBranchMasterKey(), "",
				AuditConstant.TYPE_OF_OPER_INSERT, newRecString, "");

	}

	// method to Branch Master List
	@Override
	public List<BranchMasterListVO> selectBranchMasterList(String instId)
			throws NoDataFoundException {

		logger.debug("inside selectBranchMasterList method");

		List<BranchMasterList> selectedBranchMasterList = branchMasterList
				.getBranchMasterList(instId);
		List<BranchMasterListVO> branchMasterVoList = new ArrayList<BranchMasterListVO>();

		for (BranchMasterList branchMasterRecord : selectedBranchMasterList) {
			BranchMasterListVO branchMasterVoRecord = new BranchMasterListVO();
			commonBusiness.changeObject(branchMasterVoRecord,
					branchMasterRecord);
			branchMasterVoList.add(branchMasterVoRecord);
		}
		int i = 0;
		for (BranchMasterListVO branchRec : branchMasterVoList) {
			branchRec.setRowid(i);
			i++;
		}
		return branchMasterVoList;
	}

	// method to select Branch Master Record for Delete,update and to show
	// details
	@Override
	public BranchMaster selectBranchMasterRecord(
			BranchMasterListVO branchMasterListVO,
			BranchMasterVO branchMasterVO, SessionCache sessionCache)
			throws NoDataFoundException {
		Integer rowId = 0;
		BranchMasterKey branchMasterKey = new BranchMasterKey();
		commonBusiness.changeObject(branchMasterKey, branchMasterListVO);
		branchMasterKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		BranchMaster branchMaster = branchMasterDao
				.selectBranchMaster(branchMasterKey);
		commonBusiness.changeObject(branchMasterVO, branchMaster);
		branchMasterVO.setRowid(rowId);
		return branchMaster;
	}

	// method to select Branch Master Record for Delete,update and to show
	// details
	@Override
	public BranchMasterListVO selectBranchMasterRecordAfterUpdate(
			BranchMasterVO branchMasterVO, SessionCache sessionCache)
			throws NoDataFoundException {
		Integer rowId = 0;
		BranchMasterKey branchMasterKey = new BranchMasterKey();
		commonBusiness.changeObject(branchMasterKey, branchMasterVO);
		branchMasterKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		BranchMaster branchMaster = branchMasterDao
				.selectBranchMaster(branchMasterKey);
		commonBusiness.changeObject(branchMasterVO, branchMaster);
		branchMasterVO.setRowid(rowId);
		// For refreshing the list
		BranchMasterListVO branchMasterListVO = new BranchMasterListVO();
		commonBusiness.changeObject(branchMasterListVO, branchMasterVO);
		return branchMasterListVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateBranchMaster(BranchMasterListVO oldRec,
			ApplicationCache applicationCache, BranchMasterVO branchMasterVo,
			SessionCache sessionCache,ServletContext servletContext) throws NoDataFoundException,
			UpdateFailedException, DuplicateEntryException,
			FileNotFoundInDatabase, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {
		BranchMasterKey branchMasterKey = new BranchMasterKey();
		BranchMaster branchMaster = new BranchMaster();
		commonBusiness.changeObject(branchMasterKey, oldRec);
		commonBusiness.changeObject(branchMaster, branchMasterVo);
		branchMasterKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		BranchMaster selectedBranchMasterRec = branchMasterDao
				.selectBranchMaster(branchMasterKey);
		branchMaster.setDelFlag(selectedBranchMasterRec.getDelFlag());
		branchMaster
				.setrModId(sessionCache.getUserSessionDetails().getUserId());
		fileMasterHelper.fileUpload(applicationCache, branchMasterVo
				.getBranchLogo(),
				ApplicationConstant.INSTITUTE_BRANCH_LOGO_KEY, 1, sessionCache
						.getUserSessionDetails(), sessionCache
						.getUserSessionDetails().getUserId(), branchMasterVo
						.getBranchId(),ApplicationConstant.DEFAULT_FILE_SRL_NO,"",servletContext);
		branchMasterDao.updateBranchMaster(branchMaster, branchMasterKey);

		// functional audit
		doAudit.doFunctionalAudit(sessionCache.getUserSessionDetails(),
				AuditConstant.BRCM_UPDATE_SUCCESS, "");
		logger.info("Completed Functional Auditing");

		// Database audit
		String oldRecString = selectedBranchMasterRec
				.toStringForAuditBranchMasterRecord();
		String newRecString = branchMaster.toStringForAuditBranchMasterRecord();
		doAudit.doDatabaseAudit(applicationCache,
				sessionCache.getUserSessionDetails(),
				TableNameConstant.BRANCH_MASTER,
				branchMaster.toStringForAuditBranchMasterKey(), oldRecString,
				AuditConstant.TYPE_OF_OPER_UPDATE, newRecString, "");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBranchMaster(ApplicationCache applicationCache,
			BranchMasterListVO branchMasterVoRecord, SessionCache sessionCache)
			throws NoDataFoundException, DeleteFailedException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {
		logger.debug("inside deleteBranchMaster method");
		BranchMasterKey branchMasterKey = new BranchMasterKey();
		commonBusiness.changeObject(branchMasterKey, branchMasterVoRecord);
		branchMasterKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		BranchMaster selectedBranchMasterRecord = branchMasterDao
				.selectBranchMaster(branchMasterKey);
		selectedBranchMasterRecord.setrModId(sessionCache
				.getUserSessionDetails().getUserName());
		selectedBranchMasterRecord.setDelFlag("Y");
		try {
			branchMasterKey.setInstId(sessionCache.getUserSessionDetails()
					.getInstId());
			selectedBranchMasterRecord.setDelFlag("Y");
			branchMasterDao.updateBranchMaster(selectedBranchMasterRecord,
					branchMasterKey);

			// functional audit
			doAudit.doFunctionalAudit(sessionCache.getUserSessionDetails(),
					AuditConstant.BRCM_DELETE_SUCCESS, "");
			logger.info("Completed Functional Auditing");

			// Database audit
			String newRecString = selectedBranchMasterRecord
					.toStringForAuditBranchMasterRecord();
			doAudit.doDatabaseAudit(applicationCache, sessionCache
					.getUserSessionDetails(), TableNameConstant.BRANCH_MASTER,
					selectedBranchMasterRecord
							.toStringForAuditBranchMasterKey(), "",
					AuditConstant.TYPE_OF_OPER_DELETE, newRecString, "");

		} catch (UpdateFailedException e) {
			throw new DeleteFailedException();
		}

	}

}
