package com.jaw.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.attendance.dao.IStudentAttendanceMasterDAO;
import com.jaw.attendance.dao.StudentAttendanceMaster;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.ExistPresentTermException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.SpecialClsDeleteFailedException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.controller.SpecialClassMasterVO;
import com.jaw.core.controller.SpecialClassVO;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarKey;
import com.jaw.core.dao.AcademicCalendarListKey;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.CourseMaster;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.core.dao.HolidayMaintenanceKey;
import com.jaw.core.dao.IHolidayMaintenanceDAO;
import com.jaw.core.dao.ISpecialClassDAO;
import com.jaw.core.dao.ISpecialClassListDAO;
import com.jaw.core.dao.SpecialClass;
import com.jaw.core.dao.SpecialClassKey;
import com.jaw.core.dao.SpecialClassListKey;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

@Service
public class SpecialClassService implements ISpecialClassService {
	// Logging
	Logger logger = Logger.getLogger(SpecialClassService.class);

	@Autowired
	ISpecialClassDAO specialClassDao;
	@Autowired
	ISpecialClassListDAO specialClassListDao;
	@Autowired
	IDropDownListService dropdownListService;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	IHolidayMaintenanceDAO holidayMaintenanceDAO;
	@Autowired
	IStudentAttendanceMasterDAO studentAttendanceMasterDAO;
	@Autowired
	DoAudit doAudit;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertSpecialClassRec(SpecialClassVO splClsVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException {
		logger.debug("inside insert Special Class method");
		SpecialClass specialClass = new SpecialClass();
		// map the UIObject to Pojo
		commonBusiness.changeObject(specialClass, splClsVO);
		specialClass.setAcTerm(userSessionDetails.getCurrAcTerm());
		specialClass.setDbTs(1);
		specialClass.setInstId(userSessionDetails.getInstId());
		specialClass.setBranchId(userSessionDetails.getBranchId());
		specialClass.setrCreId(userSessionDetails.getUserId());
		specialClass.setrModId(userSessionDetails.getUserId());
		specialClass.setDelFlag("N");
		specialClass.setScItemId(AlphaSequenceUtil.generateAlphaSequence(
				ApplicationConstant.SPL_CLS_SEQ, simpleIdGenerator.getNextId(
						SequenceConstant.SPECIAL_CLASS_SEQUENCE, 1)));
		
		if(specialClassDao.checkSpecialClassExist(specialClass, userSessionDetails)==0){
		specialClassDao.insertSpecialClassRec(specialClass);
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.SPL_CLS_INSERT, " ");
		logger.debug("Completed Functional Auditing");
		}else{
			throw new DuplicateEntryException();
		}

	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateSpecialClassRec(SpecialClassVO splClsVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		logger.debug("inside update Special Class method");
		SpecialClass specialClass = new SpecialClass();
		SpecialClassKey specialClassKey = new SpecialClassKey();
		// map the UIObject to Pojo
		commonBusiness.changeObject(specialClass, splClsVO);
		specialClassKey.setInstId(userSessionDetails.getInstId());
		specialClassKey.setBranchId(userSessionDetails.getBranchId());
		specialClassKey.setAcTerm(userSessionDetails.getCurrAcTerm());
		specialClass.setAcTerm(userSessionDetails.getCurrAcTerm());
		specialClassKey.setScItemId(specialClass.getScItemId());
		SpecialClass specialClassNew = specialClassDao
				.selectSpecialClassRec(specialClassKey);
		SpecialClass updateSpecialClass=new SpecialClass();
		commonBusiness.changeObject(updateSpecialClass, specialClassNew);
		specialClassKey.setDbTs(updateSpecialClass.getDbTs());
		updateSpecialClass.setFromTime(specialClass.getFromTime());
		updateSpecialClass.setToTime(specialClass.getToTime());
		updateSpecialClass.setScRmks(specialClass.getScRmks());
		updateSpecialClass.setrModId(userSessionDetails.getUserId());
		specialClassDao.updateSpecialClassRec(updateSpecialClass,
				specialClassKey);
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.SPL_CLS_UPDATE, " ");
		logger.debug("Completed Functional Auditing");

		// Database audit
		String oldRecString = specialClassNew.toStringForAuditSpecialClassRecord();
		SpecialClass specialClassForAudit = null;
		specialClassKey.setDbTs(0);
		try {
			specialClassForAudit = specialClassDao.selectSpecialClassRec(specialClassKey);
		} catch (NoDataFoundException e) {
			logger.error("No data found  Exception occured in update student group master:");
			e.printStackTrace();
		}
		specialClassKey.setDbTs(specialClassForAudit.getDbTs());
		String newRecString = specialClassForAudit.toStringForAuditSpecialClassRecord();
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.SPECIAL_CLASS,
				specialClassKey.toStringForAuditSpecialClassKey(),
				oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
				"");

		logger.debug("Completed Database Auditing");

	}

	@Override
	public SpecialClassMasterVO selectSpecialClassList(
			SpecialClassMasterVO specialClassMasterVO,UserSessionDetails usersessiondetails)
			throws NoDataFoundException {
		SpecialClassListKey splClsListKey = new SpecialClassListKey();
		// map the UIObject to Pojo
		System.out.println("search voooooooo"+specialClassMasterVO.getSpecialClassSearchVO().toString());
		commonBusiness.changeObject(splClsListKey,
				specialClassMasterVO.getSpecialClassSearchVO());
		splClsListKey.setInstId(usersessiondetails.getInstId());
		splClsListKey.setBranchId(usersessiondetails.getBranchId());
		List<SpecialClass> splClsList = specialClassListDao
				.getSpecialClassList(splClsListKey,usersessiondetails);
		List<SpecialClassVO> splClsVOs = new ArrayList<SpecialClassVO>();

		for (int i = 0; i < splClsList.size(); i++) {
			SpecialClass specialCls = splClsList.get(i);
			SpecialClassVO splCLsVO = new SpecialClassVO();
			commonBusiness.changeObject(splCLsVO, specialCls);
			splCLsVO.setRowId(i);
			splClsVOs.add(splCLsVO);
		}
		specialClassMasterVO.setSplClsVOList(splClsVOs);
		return specialClassMasterVO;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteSpecialClassRec(SpecialClassVO specialClassVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, SpecialClsDeleteFailedException {
		System.out.println("delete function in special class serviceeeeeeeeeeeeeeeeee");
		SpecialClassKey splClsKey = new SpecialClassKey();
		commonBusiness.changeObject(splClsKey, specialClassVO);
		splClsKey.setInstId(userSessionDetails.getInstId());
		splClsKey.setBranchId(userSessionDetails.getBranchId());
		splClsKey.setAcTerm(userSessionDetails.getCurrAcTerm());
		SpecialClass specialClassNew = specialClassDao
				.selectSpecialClassRec(splClsKey);
		StudentAttendanceMaster studentAttendance=new StudentAttendanceMaster();
		commonBusiness.changeObject(studentAttendance, specialClassNew);
		studentAttendance.setStudentGroupId(specialClassNew.getStudentGrpId());
		studentAttendance.setAttDate(specialClassNew.getScDate());
		if(studentAttendanceMasterDAO.checkAttendanceMarkedForSplCls(studentAttendance, userSessionDetails)==0){
		SpecialClass deleteSpecialClass=new SpecialClass();
		commonBusiness.changeObject(deleteSpecialClass, specialClassNew);
		splClsKey.setDbTs(deleteSpecialClass.getDbTs());
		deleteSpecialClass.setrModId(userSessionDetails.getUserId());
		specialClassDao.deleteSpecialClassRec(deleteSpecialClass, splClsKey);
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.SPL_CLS_DELETE, " ");
		logger.debug("Completed Functional Auditing");
		
		// Database audit
		
		String oldRecString = specialClassNew.toStringForAuditSpecialClassRecord();
		
		
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.SPECIAL_CLASS,
				splClsKey.toStringForAuditSpecialClassKey(),
				oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, "",
				"");

		logger.debug("Completed Database Auditing");
		}else{
			throw new SpecialClsDeleteFailedException();
		}
	}

	@Override
	public List<String> selectSubjectListForSplCls(String stdGrpId,
			UserSessionDetails userSessionDetails) {
		List<String> details = new ArrayList<String>();
		List<CourseSubLink> courseSubLink;
		try {
			courseSubLink = dropdownListService.selectSubListForStudentGrp(
					stdGrpId, userSessionDetails);
			for (CourseSubLink courseSubLinkP : courseSubLink) {
				details.add(courseSubLinkP.getCrslId() + "-"
						+ courseSubLinkP.getSubId());

			}
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Subject Dropdown :" + e.getMessage());
			details.add("error");// TODO Auto-generated catch block
		}

		return details;
	}

}
