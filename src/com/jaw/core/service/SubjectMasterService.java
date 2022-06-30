package com.jaw.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.CustomAndSubjectCodeExistException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.core.controller.SpecialClassMasterVO;
import com.jaw.core.controller.SpecialClassVO;
import com.jaw.core.controller.SubjectMasterVO;
import com.jaw.core.controller.SubjectMaster_MasterVO;
import com.jaw.core.dao.IHolidayMaintenanceDAO;
import com.jaw.core.dao.ISpecialClassDAO;
import com.jaw.core.dao.ISpecialClassListDAO;
import com.jaw.core.dao.ISubjectMasterDAO;
import com.jaw.core.dao.ISubjectMasterListDAO;
import com.jaw.core.dao.SpecialClass;
import com.jaw.core.dao.SubjectMaster;
import com.jaw.core.dao.SpecialClassKey;
import com.jaw.core.dao.SpecialClassListKey;
import com.jaw.core.dao.SubjectMaster;
import com.jaw.core.dao.SubjectMasterKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

@Service
public class SubjectMasterService implements ISubjectMasterService {
	// Logging
	Logger logger = Logger.getLogger(SpecialClassService.class);

	@Autowired
	ISubjectMasterDAO subjectMasterDao;
	
	@Autowired
	ISubjectMasterListDAO subjectMasterListDao;
	@Autowired
	IDropDownListService dropDownListService;
	
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Autowired
	DateUtil dateUtil;
	
	@Autowired
	DoAudit doAudit;
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertSubjectMasterRec(SubjectMasterVO subjectMasterVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
					throws DuplicateEntryException, DatabaseException, NoDataFoundException, UpdateFailedException, TableNotSpecifiedForAuditException,  CustomAndSubjectCodeExistException {
		logger.debug("inside insert Subject Master method");
		SubjectMaster subjectMaster = new SubjectMaster();
		// map the UIObject to Pojo
		commonBusiness.changeObject(subjectMaster, subjectMasterVO);		
		subjectMaster.setDbTs(1);
		subjectMaster.setInstId(userSessionDetails.getInstId());
		subjectMaster.setBranchId(userSessionDetails.getBranchId());
		subjectMaster.setrCreId(userSessionDetails.getUserId());
		subjectMaster.setrModId(userSessionDetails.getUserId());
		subjectMaster.setDelFlag("N");
		
		if(subjectMaster.getIs_Com()==null){
			subjectMaster.setIs_Com(ApplicationConstant.NO);
		}
		if(subjectMaster.getIs_Elective()==null){
			subjectMaster.setIs_Elective(ApplicationConstant.NO);
		}
		if(subjectMaster.getIs_Lang()==null){
			subjectMaster.setIs_Lang(ApplicationConstant.NO);
		}
		if(subjectMaster.getIs_rel()==null){
			subjectMaster.setIs_rel(ApplicationConstant.NO);
		}
		if(subjectMaster.getIsPreAcaSubject()==null){
			subjectMaster.setIsPreAcaSubject(ApplicationConstant.NO);
		}
	
		 if((subjectMasterDao.checkSubjectNameExist(subjectMaster)!=0)||((subjectMaster.getCust_Code()!="")&&(!subjectMaster.getCust_Code().isEmpty())&&(subjectMasterDao.checkCustomCodeExist(subjectMaster)!=0))){
			throw new CustomAndSubjectCodeExistException();
		}
		else{		
			String subId=AlphaSequenceUtil
					.generateAlphaSequence(ApplicationConstant.SUB_MTR_SEQ,
							simpleIdGenerator.getNextId(
									SequenceConstant.SUBJECT_MASTER_SEQUENCE, 1));
			subjectMaster.setSub_Id(subId);
			subjectMasterDao.insertSubjectMasterRec(subjectMaster);
		}
		// functional audit
		
					doAudit.doFunctionalAudit(userSessionDetails,
							AuditConstant.SUB_MASTER_INSERT, " ");		
				logger.debug("Completed Functional Auditing");
		/*Map<String, String> subjectMap = null;
		
			subjectMap = dropDownListService.selectAllSubjectListNew(userSessionDetails);
		
		String subjectName = (String) subjectMap.get(subjectMaster.getSub_Id());
		subjectMaster.setSub_Name(subjectName);		
		try {
			subjectMasterDao.insertSubjectMasterRec(subjectMaster);
		} catch (DuplicateEntryException e) {
			SubjectMasterKey subjectMasterKey = new SubjectMasterKey();
			subjectMasterKey.setInstId(userSessionDetails.getInstId());
			subjectMasterKey.setBranchId(userSessionDetails.getBranchId());
			subjectMasterKey.setSub_Id(subjectMaster.getSub_Id());
			try {
				SubjectMaster subjectMasterNew = subjectMasterDao.selectSubjectMasterHasDelFlgYRec(subjectMasterKey);	
				String oldString=subjectMasterNew.toStringForAuditSubjectMasterRecord();
				subjectMasterKey.setDbTs(subjectMasterNew.getDbTs());
				subjectMasterNew.setrModId(userSessionDetails.getUserId());
				commonBusiness.changeObject(subjectMasterNew, subjectMaster);
				String newString=subjectMasterNew.toStringForAuditSubjectMasterRecord();
				subjectMasterDao.updateSubjectMasterToDelFlgNRec(subjectMasterNew, subjectMasterKey);
				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.SUB_MASTER_DEL_INSERT, " ");		
			logger.debug("Completed Functional Auditing");
			doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
					TableNameConstant.SUBJECT_MASTER,
					subjectMasterKey.toStringForAuditSubjectMasterKey(),
					oldString, AuditConstant.TYPE_OF_OPER_INSERT, newString,
					"");
			logger.debug("Completed Database Auditing");
			} catch (NoDataFoundException e1) {
				throw new DuplicateEntryException();
			}
		}
		// functional audit
		
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.SUB_MASTER_INSERT, " ");		
		logger.debug("Completed Functional Auditing");*/
		

	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateSubjectMasterRec(SubjectMasterVO subjectMasterVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws UpdateFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, CustomAndSubjectCodeExistException {
		logger.debug("inside update Subject Master method");
		SubjectMaster subjectMaster = new SubjectMaster();
		SubjectMasterKey subjectMasterKey = new SubjectMasterKey();
		// map the UIObject to Pojo
		commonBusiness.changeObject(subjectMaster, subjectMasterVO);
		subjectMaster.setInstId(userSessionDetails.getInstId());
		subjectMaster.setBranchId(userSessionDetails.getBranchId());
		subjectMasterKey.setInstId(userSessionDetails.getInstId());
		subjectMasterKey.setBranchId(userSessionDetails.getBranchId());
		subjectMasterKey.setSub_Id(subjectMaster.getSub_Id());
		SubjectMaster subjectMasterNew = subjectMasterDao.selectSubjectMasterRec(subjectMasterKey);
		SubjectMaster updateSubjectMaster=new SubjectMaster();
		commonBusiness.changeObject(updateSubjectMaster, subjectMasterNew);
		subjectMasterKey.setDbTs(updateSubjectMaster.getDbTs());
		updateSubjectMaster.setShort_Code(subjectMaster.getShort_Code());
		updateSubjectMaster.setrModId(userSessionDetails.getUserId());
		updateSubjectMaster.setDepartment(subjectMaster.getDepartment());
		updateSubjectMaster.setCourseName(subjectMaster.getCourseName());
		//subjectMaster.setSub_Name(subjectMasterNew.getSub_Name());
		if(subjectMaster.getIs_Com()==null){
			updateSubjectMaster.setIs_Com(ApplicationConstant.NO);
		}else{
			updateSubjectMaster.setIs_Com(subjectMaster.getIs_Com());
		}
		if(subjectMaster.getIs_Elective()==null){
			updateSubjectMaster.setIs_Elective(ApplicationConstant.NO);
		}else{
			updateSubjectMaster.setIs_Elective(subjectMaster.getIs_Elective());
		}
		if(subjectMaster.getIs_Lang()==null){
			updateSubjectMaster.setIs_Lang(ApplicationConstant.NO);
		}else{
			updateSubjectMaster.setIs_Lang(subjectMaster.getIs_Lang());
		}
		if(subjectMaster.getIs_rel()==null){
			updateSubjectMaster.setIs_rel(ApplicationConstant.NO);
		}else{
			updateSubjectMaster.setIs_rel(subjectMaster.getIs_rel());
		}
		if(subjectMaster.getIsPreAcaSubject()==null){
			updateSubjectMaster.setIsPreAcaSubject(ApplicationConstant.NO);
		}else{
			updateSubjectMaster.setIsPreAcaSubject(subjectMaster.getIsPreAcaSubject());
		}
		
		
		
			subjectMasterDao.updateSubjectMasterRec(updateSubjectMaster, subjectMasterKey);		
	
		
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.SUB_MASTER_UPDATE, " ");
		logger.debug("Completed Functional Auditing");

		// Database audit
		String oldRecString = subjectMasterNew.toStringForAuditSubjectMasterRecord();
		SubjectMaster subjectMasterForAudit = null;
		subjectMasterKey.setDbTs(0);
		try {
			subjectMasterForAudit = subjectMasterDao.selectSubjectMasterRec(subjectMasterKey);
		} catch (NoDataFoundException e) {
			logger.error("No data found  Exception occured in update student group master:");
			e.printStackTrace();
		}
		subjectMasterKey.setDbTs(subjectMasterForAudit.getDbTs());
		String newRecString = subjectMasterForAudit.toStringForAuditSubjectMasterRecord();
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.SUBJECT_MASTER,
				subjectMasterKey.toStringForAuditSubjectMasterKey(),
				oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
				"");

		logger.debug("Completed Database Auditing");

	}

	@Override
	public SubjectMaster_MasterVO selectSubjectMasterList(
			SubjectMaster_MasterVO subjectMaster_MasterVO,
			UserSessionDetails usersessiondetails) throws NoDataFoundException {
		SubjectMasterKey subjectMasterKey = new SubjectMasterKey();
		// map the UIObject to Pojo
		
		
		subjectMasterKey.setInstId(usersessiondetails.getInstId());
		subjectMasterKey.setBranchId(usersessiondetails.getBranchId());
		List<SubjectMaster> subMtrList = subjectMasterListDao.getSubjectMasterList(subjectMasterKey);
		List<SubjectMasterVO> subMtrVOs = new ArrayList<SubjectMasterVO>();

		for (int i = 0; i < subMtrList.size(); i++) {
			SubjectMaster subjectMaster = subMtrList.get(i);
			SubjectMasterVO subMtrVO = new SubjectMasterVO();
			commonBusiness.changeObject(subMtrVO, subjectMaster);
			subMtrVO.setRowId(i);
			subMtrVOs.add(subMtrVO);
		}
		subjectMaster_MasterVO.setSubMtrVOList(subMtrVOs);
		return subjectMaster_MasterVO;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteSubjectMasterRec(SubjectMasterVO subjectMasterVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {
		SubjectMasterKey subMasterKey = new SubjectMasterKey();
		commonBusiness.changeObject(subMasterKey, subjectMasterVO);
		subMasterKey.setInstId(userSessionDetails.getInstId());
		subMasterKey.setBranchId(userSessionDetails.getBranchId());
		SubjectMaster subjectMasterNew = subjectMasterDao.selectSubjectMasterRec(subMasterKey);
		SubjectMaster deleteSubjectMasters=new SubjectMaster();
		commonBusiness.changeObject(deleteSubjectMasters, subjectMasterNew);
		subMasterKey.setDbTs(deleteSubjectMasters.getDbTs());
		deleteSubjectMasters.setrModId(userSessionDetails.getUserId());
		subjectMasterDao.deleteSubjectMasterRec(deleteSubjectMasters, subMasterKey);
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.SUB_MASTER_DELETE, " ");
		logger.debug("Completed Functional Auditing");
		
		// Database audit
		
		String oldRecString = subjectMasterNew.toStringForAuditSubjectMasterRecord();
		
		
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.SUBJECT_MASTER,
				subMasterKey.toStringForAuditSubjectMasterKey(),
				oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, "",
				"");

		logger.debug("Completed Database Auditing");
	}

	
}
