package com.jaw.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.admin.dao.InstituteMaster;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.core.controller.AcademicCalendarMasterVO;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.controller.CourseMasterVO;
import com.jaw.core.controller.CourseMaster_MasterVO;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarKey;
import com.jaw.core.dao.AcademicCalendarListKey;
import com.jaw.core.dao.CourseMaster;
import com.jaw.core.dao.CourseMasterKey;
import com.jaw.core.dao.ICourseMasterDAO;
import com.jaw.core.dao.ICourseMasterListDAO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.dao.StudentGroupMasterKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

//Course Master Service Class
@Service
public class CourseMasterService implements ICourseMasterService{
	// Logging
	Logger logger = Logger.getLogger(CourseMasterService.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	ICourseMasterDAO courseMasterDao;
	@Autowired
	ICourseMasterListDAO courseMasterListDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertCourseMasterRec(CourseMasterVO corseMrVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException {
		logger.debug("inside insert Course Master method");
		CourseMaster courseMaster = new CourseMaster();
		// map the UIObject to Pojo
		commonBusiness.changeObject(courseMaster, corseMrVO);
		if(courseMaster.getTermApplcble()==null){
			courseMaster.setTermApplcble(ApplicationConstant.NO);
		}
		if(courseMaster.getCvAppcble()==null){
			courseMaster.setCvAppcble(ApplicationConstant.NO);
		}
		courseMaster.setDbTs(1);
		courseMaster.setInstId(userSessionDetails.getInstId());
		courseMaster.setBranchId(userSessionDetails.getBranchId());
		courseMaster.setrCreId(userSessionDetails.getUserId());
		courseMaster.setrModId(userSessionDetails.getUserId());
		courseMaster.setDelFlag("N");	
		courseMaster.setCourseMasterId(AlphaSequenceUtil.generateAlphaSequence(
				ApplicationConstant.STRING_COURSE_MASTER_SEQ,
				simpleIdGenerator.getNextId(
						SequenceConstant.COURSE_MASTER_SEQUENCE, 1)));
		if(courseMasterDao.checkCourseMasterExist(courseMaster)==0){
		courseMasterDao.insertCourseMasterRec(courseMaster);
		// functional audit
				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.CRM_INSERT, " ");
				logger.debug("Completed Functional Auditing");
		}else{
			throw new DuplicateEntryException();
		}
		
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateCourseMasterRec(CourseMasterVO corseMrVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		logger.debug("inside update Course Master method");
		CourseMaster courseMaster = new CourseMaster();
		CourseMasterKey courseMasterrKey = new CourseMasterKey();
		// map the UIObject to Pojo
		commonBusiness.changeObject(courseMaster, corseMrVO);
		courseMasterrKey.setCourseMasterId(courseMaster.getCourseMasterId());
		courseMasterrKey.setInstId(userSessionDetails.getInstId());
		courseMasterrKey.setBranchId(userSessionDetails.getBranchId());
		CourseMaster courseMasterNew=null;
		try {
			courseMasterNew=courseMasterDao.selectCourseMasterRec(courseMasterrKey);
		} catch (NoDataFoundException e) {
			logger.error("No data found  Exception occured in update course master:");
			e.printStackTrace();
		}
		CourseMaster updateCourseMaster=new CourseMaster();
		commonBusiness.changeObject(updateCourseMaster, courseMasterNew);
		courseMasterrKey.setDbTs(courseMasterNew.getDbTs());
		updateCourseMaster.setInstId(userSessionDetails.getInstId());
		updateCourseMaster.setBranchId(userSessionDetails.getBranchId());
		updateCourseMaster.setrModId(userSessionDetails.getUserId());
		updateCourseMaster.setDelFlag("N");
		
		if(courseMaster.getTermApplcble()==null){
			updateCourseMaster.setTermApplcble(ApplicationConstant.NO);
		}else{
			updateCourseMaster.setTermApplcble(courseMaster.getTermApplcble());
		}
		if(courseMaster.getCvAppcble()==null){
			updateCourseMaster.setCvAppcble(ApplicationConstant.NO);
		}else{
			updateCourseMaster.setCvAppcble(courseMaster.getCvAppcble());
		}
		updateCourseMaster.setCourseGrp(courseMaster.getCourseGrp());
		//updateCourseMaster.setCvAppcble(courseMaster.getCvAppcble());
		
		updateCourseMaster.setAffId(courseMaster.getAffId());
		updateCourseMaster.setAffDetails(courseMaster.getAffDetails());
		updateCourseMaster.setMedium(courseMaster.getMedium());
		updateCourseMaster.setCourseName(courseMaster.getCourseName());
		updateCourseMaster.setDepartment(courseMaster.getDepartment());
		courseMasterDao.updateCourseMasterRec(updateCourseMaster, courseMasterrKey);
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.CRM_UPDATE, " ");
		logger.debug("Completed Functional Auditing");
		
		// Database audit
				String oldRecString = courseMasterNew
						.toStringForAuditCourseMasterRecord();
				CourseMaster courseMasterForAudit = null;
				courseMasterrKey.setDbTs(0);
				try {
					courseMasterForAudit=courseMasterDao.selectCourseMasterRec(courseMasterrKey);
				} catch (NoDataFoundException e) {
					logger.error("No data found  Exception occured in update course master:");
					e.printStackTrace();
				}						
				courseMasterrKey.setDbTs(courseMasterForAudit.getDbTs());			
				String newRecString = courseMasterForAudit
						.toStringForAuditCourseMasterRecord();
				doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
						TableNameConstant.COURSE_MASTER,
						courseMasterrKey.toStringForAuditCourseMasterKey(),
						oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
						"");

				logger.debug("Completed Database Auditing");
		
	}
	@Override
	public CourseMaster_MasterVO selectCourseMasterList(
			CourseMaster_MasterVO courseMaster_MasterVO,UserSessionDetails userSessionDetails) throws NoDataFoundException {
		
		CourseMasterKey courseMasterKey=new CourseMasterKey();
		courseMasterKey.setInstId(userSessionDetails.getInstId());
		courseMasterKey.setBranchId(userSessionDetails.getBranchId());
		List<CourseMaster> crseMtrList = courseMasterListDao.getCourseMasterList(courseMasterKey);
		List<CourseMasterVO> crsMtrVOs = new ArrayList<CourseMasterVO>();

		for (int i = 0; i < crseMtrList.size(); i++) {
			CourseMaster courseMaster = crseMtrList.get(i);
			CourseMasterVO courseMasterVO = new CourseMasterVO();
			commonBusiness.changeObject(courseMasterVO, courseMaster);
			courseMasterVO.setRowId(i);
			crsMtrVOs.add(courseMasterVO);
			System.out.println("Added course master object in list:"+courseMasterVO);
		}
		courseMaster_MasterVO.setCrseMtrVOList(crsMtrVOs);
		return courseMaster_MasterVO;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteCourseMasterRec(CourseMasterVO courseMasterVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		CourseMasterKey courseMasterKey = new CourseMasterKey();
		commonBusiness.changeObject(courseMasterKey, courseMasterVO);
	
		courseMasterKey.setInstId(userSessionDetails.getInstId());
		courseMasterKey.setBranchId(userSessionDetails.getBranchId());		
		// get Data's from DB for dbts value
		CourseMaster courseMaster = courseMasterDao.selectCourseMasterRec(courseMasterKey);
		CourseMaster deleteCourseMaster=new CourseMaster();
		commonBusiness.changeObject(deleteCourseMaster, courseMaster);
		courseMasterKey.setDbTs(courseMaster.getDbTs());
		deleteCourseMaster.setrModId(userSessionDetails.getUserId());
		deleteCourseMaster.setDbTs(courseMaster.getDbTs()+1);
		deleteCourseMaster.setDelFlag("Y");
		courseMasterDao.deleteCourseMasterRec(deleteCourseMaster, courseMasterKey);
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.CRM_DELETE, " ");
		logger.debug("Completed Functional Auditing");
		
		// Database audit
		
		String oldRecString = courseMaster
				.toStringForAuditCourseMasterRecord();
		
		String newRecString = deleteCourseMaster
				.toStringForAuditCourseMasterRecord();
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.COURSE_MASTER,
				courseMasterKey.toStringForAuditCourseMasterKey(),
				oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, newRecString,
				"");

		logger.debug("Completed Database Auditing");
		
	}

}
