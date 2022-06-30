package com.jaw.core.service;

import java.util.ArrayList;
import java.util.List;

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
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.core.controller.StudentGroupMasterVO;
import com.jaw.core.controller.StudentGroupVO;
import com.jaw.core.dao.CourseMaster;
import com.jaw.core.dao.IStudentGroupMasterDAO;
import com.jaw.core.dao.IStudentGroupMasterListDAO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.dao.StudentGroupMasterKey;
import com.jaw.core.dao.StudentGroupMasterListKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

@Service
public class StudentGroupMasterService implements IStudentGroupMasterService {
	// Logging
	Logger logger = Logger.getLogger(StudentGroupMasterService.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	IStudentGroupMasterDAO studentGroupMasterDao;
	@Autowired
	IStudentGroupMasterListDAO studentGroupMasterListDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertStudentGroupMasterRec(StudentGroupVO studGrpMrVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException {

		logger.debug("inside insert Student Group Master method");

		StudentGroupMaster studentGroupMaster = new StudentGroupMaster();
		// map the UIObject to Pojo
		commonBusiness.changeObject(studentGroupMaster, studGrpMrVO);

		studentGroupMaster.setDbTs(1);
		studentGroupMaster.setInstId(userSessionDetails.getInstId());
		studentGroupMaster.setBranchId(userSessionDetails.getBranchId());
		studentGroupMaster.setrCreId(userSessionDetails.getUserId());
		studentGroupMaster.setrModId(userSessionDetails.getUserId());
		studentGroupMaster.setDelFlag("N");
		
		
			if(studentGroupMasterDao.validateStudentGrpMasterRec(studentGroupMaster)==0){
			studentGroupMaster.setStudentGrpId(AlphaSequenceUtil
				.generateAlphaSequence(
						ApplicationConstant.STRING_STUD_GRP_MASTER_SEQ,
						simpleIdGenerator.getNextId(
								SequenceConstant.STUDENT_GROUP_MASTER_SEQUENCE,
								1)));
				logger.info("student group valuesss"+studentGroupMaster.toString());
				System.out.println("student group valuesss"+studentGroupMaster);
				studentGroupMasterDao.insertStudentGrpMasterRec(studentGroupMaster);
				// functional audit
				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.SGM_INSERT, " ");
				logger.debug("Completed Functional Auditing");
			}else{
			logger.info("student group valuesss"+studentGroupMaster.toString());
				throw new DuplicateEntryException();
			}
			
		
		

	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateStudentGroupMasterRec(StudentGroupVO stuGrpVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		
		logger.debug("inside update Student Group Master method");		
		
		StudentGroupMasterKey studentGroupMasterKey = new StudentGroupMasterKey();
		// map the UIObject to Pojo
		
		studentGroupMasterKey.setStudentGrpId(stuGrpVO
				.getStudentGrpId());
		studentGroupMasterKey.setInstId(userSessionDetails.getInstId());
		studentGroupMasterKey.setBranchId(userSessionDetails.getBranchId());
		// get Data's from DB for dbts value
		StudentGroupMaster studentGrpMtrNew = studentGroupMasterDao
				.selectStudentGrpMasterRec(studentGroupMasterKey);
		
		StudentGroupMaster updateStudentGroupMaster=new StudentGroupMaster();
		commonBusiness.changeObject(updateStudentGroupMaster, studentGrpMtrNew);
		studentGroupMasterKey.setDbTs(studentGrpMtrNew.getDbTs());
		updateStudentGroupMaster.setInstId(userSessionDetails.getInstId());
		updateStudentGroupMaster.setBranchId(userSessionDetails.getBranchId());
		updateStudentGroupMaster.setrModId(userSessionDetails.getUserId());
		updateStudentGroupMaster.setMedium(stuGrpVO.getMedium());
		updateStudentGroupMaster.setStudentGrp(stuGrpVO.getStudentGrp());
		
		studentGroupMasterDao.updateStudentGrpMasterRec(updateStudentGroupMaster,
				studentGroupMasterKey);

		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.SGM_UPDATE, " ");
		logger.debug("Completed Functional Auditing");
		
		// Database audit
				String oldRecString = studentGrpMtrNew.toStringForAuditStudentGroupMasterRecord();
				StudentGroupMaster studentGroupMasterForAudit = null;
				studentGroupMasterKey.setDbTs(0);
				try {
					studentGroupMasterForAudit=studentGroupMasterDao.selectStudentGrpMasterRec(studentGroupMasterKey);
				} catch (NoDataFoundException e) {
					logger.error("No data found  Exception occured in update student group master:");
					e.printStackTrace();
				}						
				studentGroupMasterKey.setDbTs(studentGroupMasterForAudit.getDbTs());			
				String newRecString = studentGroupMasterForAudit
						.toStringForAuditStudentGroupMasterRecord();
				doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
						TableNameConstant.STUDENT_GROUP_MASTER,
						studentGroupMasterKey.toStringForAuditStudentGroupMasterKey(),
						oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
						"");

				logger.debug("Completed Database Auditing");
	}

	@Override
	public StudentGroupMasterVO selectStudentGroupList(
			StudentGroupMasterVO studentGroupMasterVO,UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
		
		StudentGroupMasterListKey stuGrpMtrListKey = new StudentGroupMasterListKey();
		stuGrpMtrListKey.setCourseMasterId(studentGroupMasterVO
				.getCourseMasterId());
		stuGrpMtrListKey.setInstId(userSessionDetails.getInstId());
		stuGrpMtrListKey.setBranchId(userSessionDetails.getBranchId());
		List<StudentGroupMaster> studentGrpMtr = studentGroupMasterListDao
				.selectGroupMasterList(stuGrpMtrListKey);
		
		List<StudentGroupVO> studentGrpVOs = new ArrayList<StudentGroupVO>();
		for (int i = 0; i < studentGrpMtr.size(); i++) {
			StudentGroupMaster stdGrpMtrList = studentGrpMtr.get(i);
			StudentGroupVO stdGrpVO = new StudentGroupVO();
			commonBusiness.changeObject(stdGrpVO, stdGrpMtrList);
			stdGrpVO.setRowId(i);
			studentGrpVOs.add(stdGrpVO);
		}
		studentGroupMasterVO.setStudentMasterVOList(studentGrpVOs);
		
		return studentGroupMasterVO;
	}

	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteStudentGrpMasterRec(
			StudentGroupVO studentGroupVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		
		
		StudentGroupMasterKey stuGrpMtrKey = new StudentGroupMasterKey();
		commonBusiness.changeObject(stuGrpMtrKey, studentGroupVO);
		
		stuGrpMtrKey.setInstId(userSessionDetails.getInstId());
		stuGrpMtrKey.setBranchId(userSessionDetails.getBranchId());
		
		StudentGroupMaster studentGrpMtrNew =  studentGroupMasterDao
				.selectStudentGrpMasterRec(stuGrpMtrKey);
		StudentGroupMaster deleteStudentGrpMtr=new StudentGroupMaster();
		commonBusiness.changeObject(deleteStudentGrpMtr, studentGrpMtrNew);
		stuGrpMtrKey.setDbTs(studentGrpMtrNew.getDbTs());
		deleteStudentGrpMtr.setrModId(userSessionDetails.getUserId());
		deleteStudentGrpMtr.setDbTs(studentGrpMtrNew.getDbTs()+1);
		deleteStudentGrpMtr.setDelFlag("Y");
		// get Data's from DB for dbts value
		studentGroupMasterDao.deleteStudentGrpMasterRec(deleteStudentGrpMtr,
				stuGrpMtrKey);
		// functional audit
				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.SGM_DELETE, " ");
				logger.debug("Completed Functional Auditing");
				
				// Database audit
				
				String oldRecString = studentGrpMtrNew
						.toStringForAuditStudentGroupMasterRecord();
				
				String newRecString = deleteStudentGrpMtr
						.toStringForAuditStudentGroupMasterRecord();
				doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
						TableNameConstant.STUDENT_GROUP_MASTER,
						stuGrpMtrKey.toStringForAuditStudentGroupMasterKey(),
						oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, "",
						"");

				logger.debug("Completed Database Auditing");

	}

	
}
