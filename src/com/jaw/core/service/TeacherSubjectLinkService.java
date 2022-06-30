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
import com.jaw.core.controller.StudentGroupVO;
import com.jaw.core.controller.TeacherSubjectLinkMasterVO;
import com.jaw.core.controller.TeacherSubjectLinkVO;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.IStudentGroupMasterDAO;
import com.jaw.core.dao.IStudentGroupMasterListDAO;
import com.jaw.core.dao.ITeacherSubjectLinkDAO;
import com.jaw.core.dao.ITeacherSubjectLinkListDAO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.dao.StudentGroupMasterKey;
import com.jaw.core.dao.StudentGroupMasterListKey;
import com.jaw.core.dao.TeacherSubjectLink;
import com.jaw.core.dao.TeacherSubjectLinkKey;
import com.jaw.core.dao.TeacherSubjectLinkListKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

@Service
public class TeacherSubjectLinkService implements ITeacherSubjectLinkService {
	// Logging
	Logger logger = Logger.getLogger(TeacherSubjectLinkService.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	ITeacherSubjectLinkDAO trSubLinkDao;
	@Autowired
	ITeacherSubjectLinkListDAO trSubLinkListDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertTeacherSubLinkRec(TeacherSubjectLinkVO trSubLinkVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DuplicateEntryException, DatabaseException, DeleteFailedException, NoDataFoundException, TableNotSpecifiedForAuditException {
		logger.debug("inside insert Teacher Subject Link method");
		TeacherSubjectLink teacherSubjectLink = new TeacherSubjectLink();
		TeacherSubjectLinkListKey teacherSubLinkListKey = new TeacherSubjectLinkListKey();
		// map the UIObject to Pojo
		commonBusiness.changeObject(teacherSubjectLink, trSubLinkVO);
		teacherSubjectLink.setDbTs(1);
		teacherSubjectLink.setInstId(userSessionDetails.getInstId());
		teacherSubjectLink.setBranchId(userSessionDetails.getBranchId());
		
		teacherSubjectLink.setrCreId(userSessionDetails.getUserId());
		teacherSubjectLink.setrModId(userSessionDetails.getUserId());
		teacherSubjectLink.setDelFlag("N");		
		commonBusiness.changeObject(teacherSubLinkListKey, teacherSubjectLink);
		TeacherSubjectLink checkedTeacherSubLink=trSubLinkDao.checkTeacherSubjectLink(teacherSubLinkListKey);
		if(checkedTeacherSubLink!=null){
		//	trSubLinkVO.setTrslId(checkedTeacherSubLink.getTrslId());
			//deleteTeacherSubLinkRec(trSubLinkVO,userSessionDetails,applicationCache);
			throw new DuplicateEntryException();
		}else{
	
		teacherSubjectLink.setTrslId(AlphaSequenceUtil.generateAlphaSequence(
				ApplicationConstant.TR_SUB_LINK_SEQ, simpleIdGenerator
						.getNextId(SequenceConstant.TEACHER_SUB_LINK_SEQUENCE,
								1)));

		trSubLinkDao.insertTeacherSubjectLinkRec(teacherSubjectLink);
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails, AuditConstant.TSL_INSERT,
				" ");
		logger.debug("Completed Functional Auditing");
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateTeacherSubLinkRec(TeacherSubjectLinkVO trSubLinkVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		logger.debug("inside update Teacher Subject Link method");
		TeacherSubjectLink TeacherSubjectLink = new TeacherSubjectLink();
		TeacherSubjectLinkKey teacherSubjectLinkKey = new TeacherSubjectLinkKey();
		// map the UIObject to Pojo
		commonBusiness.changeObject(TeacherSubjectLink, trSubLinkVO);

		teacherSubjectLinkKey.setTrslId(TeacherSubjectLink.getTrslId());
		teacherSubjectLinkKey.setInstId(userSessionDetails.getInstId());
		teacherSubjectLinkKey.setBranchId(userSessionDetails.getBranchId());
		// get Data's from DB for dbts value
		TeacherSubjectLink teacherSubjectLinkNew = trSubLinkDao
				.selectTeacherSubjectLinkRec(teacherSubjectLinkKey);
		TeacherSubjectLink updateTeacherSubjectLink=new TeacherSubjectLink();
		commonBusiness.changeObject(updateTeacherSubjectLink, teacherSubjectLinkNew);
		teacherSubjectLinkKey.setDbTs(teacherSubjectLinkNew.getDbTs());
		updateTeacherSubjectLink.setrModId(userSessionDetails.getUserId());
		updateTeacherSubjectLink.setStaffId(TeacherSubjectLink.getStaffId());
		updateTeacherSubjectLink.setSubId(TeacherSubjectLink.getSubId());

		trSubLinkDao.updateTeacherSubjectLinkRec(updateTeacherSubjectLink,
				teacherSubjectLinkKey);

		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.TSL_UPDATE, " ");
		logger.debug("Completed Functional Auditing");

		// Database audit
		String oldRecString = teacherSubjectLinkNew
				.toStringForAuditTeacherSubLinkRecord();
		TeacherSubjectLink teacherSubjectLinkForAudit = null;
		teacherSubjectLinkKey.setDbTs(0);
		try {
			teacherSubjectLinkForAudit = trSubLinkDao.selectTeacherSubjectLinkRec(teacherSubjectLinkKey);
		} catch (NoDataFoundException e) {
			logger.error("No data found  Exception occured in update Teacher subject link:");
			e.printStackTrace();
		}
		teacherSubjectLinkKey.setDbTs(teacherSubjectLinkForAudit.getDbTs());
		String newRecString = teacherSubjectLinkForAudit.toStringForAuditTeacherSubLinkRecord();
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.TEACHER_SUB_LINK,
				teacherSubjectLinkKey.toStringForAuditTeacherSubjectLinkKey(),
				oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
				"");

		logger.debug("Completed Database Auditing");

	}

	@Override
	public TeacherSubjectLinkMasterVO selectTeacherSubjectLinkList(
			TeacherSubjectLinkMasterVO trSubLinkMtrVO,UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
		TeacherSubjectLink trSubLink = new TeacherSubjectLink();
		
		TeacherSubjectLinkListKey teacherSubLinkListKey=new TeacherSubjectLinkListKey();
		System.out.println("printttttttt"+trSubLinkMtrVO.getTeacherSubjectLinkSearchVO());
		if(trSubLinkMtrVO.getTeacherSubjectLinkSearchVO()!=null){
		commonBusiness.changeObject(teacherSubLinkListKey, trSubLinkMtrVO.getTeacherSubjectLinkSearchVO());
		}System.out.println("printttttttt endddddddddd");
		teacherSubLinkListKey.setInstId(userSessionDetails.getInstId());
		teacherSubLinkListKey.setBranchId(userSessionDetails.getBranchId());
		List<TeacherSubjectLink> teacherSubjectLink = trSubLinkListDao
				.selectTeacherSubLinkList(teacherSubLinkListKey);
		List<TeacherSubjectLinkVO> trSubLinkVOs = new ArrayList<TeacherSubjectLinkVO>();

		for (int i = 0; i < teacherSubjectLink.size(); i++) {
			TeacherSubjectLink trSubLinkList = teacherSubjectLink.get(i);
			TeacherSubjectLinkVO trSubLinkVO = new TeacherSubjectLinkVO();
			commonBusiness.changeObject(trSubLinkVO, trSubLinkList);
			trSubLinkVO.setRowId(i);
			trSubLinkVOs.add(trSubLinkVO);
		}

		trSubLinkMtrVO.setTeaSubLinkVOList(trSubLinkVOs);
		return trSubLinkMtrVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteTeacherSubLinkRec(
			TeacherSubjectLinkVO trSubLinkVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		
		TeacherSubjectLinkKey trSubLinkKey = new TeacherSubjectLinkKey();
		commonBusiness.changeObject(trSubLinkKey, trSubLinkVO);

		trSubLinkKey.setInstId(userSessionDetails.getInstId());
		trSubLinkKey.setBranchId(userSessionDetails.getBranchId());
		// get Data's from DB for dbts value
		TeacherSubjectLink teacherSubjectLinkNew = trSubLinkDao
				.selectTeacherSubjectLinkRec(trSubLinkKey);
		TeacherSubjectLink deleteTeacherSubjectLink=new TeacherSubjectLink();
		commonBusiness.changeObject(deleteTeacherSubjectLink, teacherSubjectLinkNew);
		trSubLinkKey.setDbTs(teacherSubjectLinkNew.getDbTs());
		deleteTeacherSubjectLink.setrModId(userSessionDetails.getUserId());
		deleteTeacherSubjectLink.setDbTs(teacherSubjectLinkNew.getDbTs()+1);
		deleteTeacherSubjectLink.setDelFlag("Y");
		trSubLinkDao.deleteTeacherSubjectLinkRec(deleteTeacherSubjectLink,
				trSubLinkKey);
		// functional audit
					doAudit.doFunctionalAudit(userSessionDetails,
							AuditConstant.TSL_DELETE, " ");
					logger.debug("Completed Functional Auditing");
					
					// Database audit
					
					String oldRecString = teacherSubjectLinkNew
							.toStringForAuditTeacherSubLinkRecord();
					
					String newRecString = deleteTeacherSubjectLink
							.toStringForAuditTeacherSubLinkRecord();
					doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
							TableNameConstant.TEACHER_SUB_LINK,
							trSubLinkKey.toStringForAuditTeacherSubjectLinkKey(),
							oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, newRecString,
							"");

					logger.debug("Completed Database Auditing");
	}

}
