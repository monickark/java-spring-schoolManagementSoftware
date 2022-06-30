package com.jaw.admin.service;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.admin.controller.InstituteMasterVO;
import com.jaw.admin.dao.IInstituteMasterDAO;
import com.jaw.admin.dao.InstituteMaster;
import com.jaw.admin.dao.InstituteMasterKey;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.files.dao.IFileMasterDao;
import com.jaw.common.files.service.FileMasterHelper;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;

//Institute Master Service Class
@Service
public class InstituteMasterService implements IInstituteMasterService {

	// Logging
	Logger logger = Logger.getLogger(InstituteMasterService.class);

	@Autowired
	IInstituteMasterDAO instMasterDao;
	@Autowired
	CommonBusiness commonBusiness;
	// Helper class to do Auditing
	@Autowired
	DoAudit doAudit;
	@Autowired
	FileMasterHelper fileMasterHelper;
	@Autowired
	IFileMasterDao fileMasterDao;
	@Autowired
	PropertyManagementUtil prpmUtil;

	@Override
	// method to Select Institute Master List
	public void selectInstituteMasterRec(ApplicationCache applicationCache,
			InstituteMasterVO instVO) throws DatabaseException,
			PropertyNotFoundException {
		// logger.debug("inside selectInstituteMasterRec method");
		InstituteMaster selectedInstMasterRecord = instMasterDao
				.selectInstituteMasterRec(null,null);
		commonBusiness.changeObject(instVO, selectedInstMasterRecord);	
		instVO.setSingleBranch(prpmUtil.getPropertyValue(applicationCache,
				instVO.getInstId(), instVO.getInstId(),
				PropertyManagementConstant.INST_SINGLE_BRANCH));			

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	// method to Update InstituteMaster
	public void updateInstituteMasterRec(InstituteMasterVO instVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,ServletContext servletContext) throws FileNotFoundInDatabase,
			DuplicateEntryException, NoRecordFoundException, DatabaseException,
			UpdateFailedException, TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {

		logger.debug("inside updateInstituteMaster method");
		InstituteMaster selectedInstMasterRecord = instMasterDao
				.selectInstituteMasterRec(instVO.getDbTs(), instVO.getInstId());
		InstituteMaster updatedInstMaster = new InstituteMaster();
		InstituteMasterKey updatedInstMasterKey = new InstituteMasterKey();
		// map the UIObject to Pojo
		commonBusiness.changeObject(updatedInstMaster, instVO);
		// Copy the Key from Pojo
		commonBusiness.changeObject(updatedInstMasterKey,
				selectedInstMasterRecord);
		updatedInstMaster.setDbTs(selectedInstMasterRecord.getDbTs());
		updatedInstMaster.setDelFlag(selectedInstMasterRecord.getDelFlag());
		updatedInstMaster.setrModId(userSessionDetails.getUserId());

		// for Institute Logo updation
		fileMasterHelper.fileUpload(applicationCache,
				instVO.getInstLogo(),
				ApplicationConstant.INSTITUTE_BRANCH_LOGO_KEY,
				updatedInstMaster.getDbTs(), userSessionDetails,
				userSessionDetails.getUserId(), instVO.getInstId(),ApplicationConstant.DEFAULT_FILE_SRL_NO,"",servletContext);

		// InstituteMaster Updation
		instMasterDao.updateInstituteMasterRec(updatedInstMaster,
				updatedInstMasterKey);

		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.INSM_UPDATE, " ");
		logger.debug("Completed Functional Auditing");

		// Database audit
		String oldRecString = selectedInstMasterRecord
				.toStringForAuditInstMasterRecord();
		InstituteMaster selectNewRecord = instMasterDao
				.selectInstituteMasterRec(null,null);
		String newRecString = selectNewRecord
				.toStringForAuditInstMasterRecord();
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.INSTITUTE_MASTER,
				selectedInstMasterRecord.toStringForAuditInstMasterKey(),
				oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
				"");

		logger.debug("Completed Database Auditing");

	}
	

}
