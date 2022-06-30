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
import com.jaw.attendance.dao.StudentAttendanceMasterKey;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.AcadTermUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.ExistPresentTermException;
import com.jaw.common.exceptions.ExistPreviousTermException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.DateUtil;
import com.jaw.core.controller.AcademicTermDetailsVO;
import com.jaw.core.controller.AcademicTermMasterVO;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.AcademicTermList;
import com.jaw.core.dao.HolidayMaintenance;
import com.jaw.core.dao.HolidayMaintenanceKey;
import com.jaw.core.dao.IAcademicTermDetailsDAO;
import com.jaw.core.dao.IAcademicTermDetailsListDAO;
import com.jaw.core.dao.IHolidayMaintenanceDAO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

//Academic Term Details Service Class
@Service
public class AcademicTermDetailsService implements IAcademicTermDetailsService {
	// Logging
	Logger logger = Logger.getLogger(AcademicTermDetailsService.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	IAcademicTermDetailsDAO acadTrmDetailsDao;
	@Autowired
	IAcademicTermDetailsListDAO academicTermListDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	IHolidayMaintenanceDAO holidayMaintenanceDAO;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Autowired
	IStudentAttendanceMasterDAO studentAttendanceMasterDAO;
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertAcademicTermDetailsRec(AcademicTermDetailsVO acadTrmDetailVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws  ExistPresentTermException, DatabaseException, DuplicateEntryException, NoDataFoundException, UpdateFailedException, ExistPreviousTermException {
		
		logger.debug("inside insert Academic Term Details method");
		
		AcademicTermDetails academicTrmDet = new AcademicTermDetails();
		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		
		// map the UIObject to Pojo
		commonBusiness.changeObject(academicTrmDet, acadTrmDetailVO);
		academicTrmDet.setDbTs(1);
		academicTrmDet.setInstId(userSessionDetails.getInstId());
		academicTrmDet.setBranchId(userSessionDetails.getBranchId());
		academicTrmDet.setrCreId(userSessionDetails.getUserId());
		academicTrmDet.setrModId(userSessionDetails.getUserId());
		academicTrmDet.setDelFlag("N");
		academicTrmDet.setPromotionStatus(ApplicationConstant.STUDENT_PROMOTION_OPENED);
		academicTermDetailsKey.setInstId(userSessionDetails.getInstId());
		academicTermDetailsKey.setBranchId(userSessionDetails.getBranchId());
		academicTermDetailsKey.setAcTermSts(academicTrmDet.getAcTermSts());
		String action="";
		if (academicTrmDet.getAcTermSts().equals(ApplicationConstant.ACT_TRM_PRESENT)
				&& acadTrmDetailsDao.checkAcademicTermStatusRec(academicTermDetailsKey,action)) {
			throw new ExistPresentTermException();
		}else if (academicTrmDet.getAcTermSts().equals(ApplicationConstant.ACT_TRM_PREVIOUS)
				&& acadTrmDetailsDao.checkAcademicTermStatusRec(academicTermDetailsKey,action)) {
			throw new ExistPreviousTermException();
		}
		else {
			try {
				acadTrmDetailsDao.insertAcademicTermDetailsRec(academicTrmDet);
			} catch (DuplicateEntryException e) {
				updateDelFlg(academicTrmDet,userSessionDetails,applicationCache);
			}

			// functional audit
						doAudit.doFunctionalAudit(userSessionDetails,
								AuditConstant.ACADTERM_INSERT, " ");
						logger.debug("Completed Functional Auditing");
		}
		
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateAcademicTermDetailsRec(
			AcademicTermDetailsVO acadTrmDetailVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, AcadTermUpdateFailedException, ExistPresentTermException, ExistPreviousTermException {
		logger.debug("inside update Academic Term Details method");
		AcademicTermDetails academicTrmDet = new AcademicTermDetails();
		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		
		// map the UIObject to Pojo
		commonBusiness.changeObject(academicTrmDet, acadTrmDetailVO);
		System.out.println("dao object  "+acadTrmDetailVO.toString());
		academicTermDetailsKey.setInstId(userSessionDetails.getInstId());
		academicTermDetailsKey.setBranchId(userSessionDetails.getBranchId());
		academicTermDetailsKey.setAcTerm(academicTrmDet.getAcTerm());
		academicTermDetailsKey.setAcYear(academicTrmDet.getAcYear());
		academicTermDetailsKey.setAcTermSts(academicTrmDet.getAcTermSts());
		String action="Update";
		if (academicTrmDet.getAcTermSts().equals(ApplicationConstant.ACT_TRM_PRESENT)
				&& acadTrmDetailsDao.checkAcademicTermStatusRec(academicTermDetailsKey,action)) {
			throw new ExistPresentTermException();
		}else if (academicTrmDet.getAcTermSts().equals(ApplicationConstant.ACT_TRM_PREVIOUS)
				&& acadTrmDetailsDao.checkAcademicTermStatusRec(academicTermDetailsKey,action)) {
			throw new ExistPreviousTermException();
		}else{
		AcademicTermDetails academicTrm = null;
		try {
			academicTrm = acadTrmDetailsDao.selectAcademicTermDetailsRec(academicTermDetailsKey);
		}
		catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String oldStatus=academicTrm.getAcTermSts();
		System.out.println("update ac termmmm       "+academicTrm.toString());
		if(academicTrm.getAcTermSts().equals(ApplicationConstant.ACT_TRM_CLOSED)){
			throw new AcadTermUpdateFailedException();
		}else{
		String oldRecString = academicTrm.toStringForAuditAcademicTermRecord();
		HolidayMaintenanceKey holidayMaintenanceKey = new HolidayMaintenanceKey();
		holidayMaintenanceKey.setInstId(userSessionDetails.getInstId());
		holidayMaintenanceKey.setBranchId(userSessionDetails.getBranchId());
		holidayMaintenanceKey.setAcTerm(academicTrmDet.getAcTerm());
		
		if (dateUtil.getDateFormatByString(academicTrm.getTermStartDate()).compareTo(
				dateUtil.getCurrentDateInDateDataType()) > 0) {
			System.out.println("Term Start Date has not been started");
			academicTrm.setTermStartDate(academicTrmDet.getTermStartDate());
			academicTrm.setTermEndDate(academicTrmDet.getTermEndDate());
		}
		
		if (dateUtil.getCurrentDateInDateDataType().compareTo(
				dateUtil.getDateFormatByString(academicTrm.getTermEndDate())) < 0) {
			System.out.println("End Date Has not come yet");
			academicTrm.setTermEndDate(academicTrmDet.getTermEndDate());
		}
		if (holidayMaintenanceDAO.checkHolRecsAvble(holidayMaintenanceKey)) {
			System.out.println("You can update holiday here");
			academicTrm.setWeeklyHoliday(academicTrmDet.getWeeklyHoliday());
		}
		if((academicTrmDet.getAcTermSts().equals(ApplicationConstant.ACT_TRM_CLOSED))||
				(academicTrmDet.getAcTermSts().equals(ApplicationConstant.ACT_TRM_PREVIOUS))){
		if (dateUtil.getCurrentDateInDateDataType().compareTo(
				dateUtil.getDateFormatByString(academicTrm.getTermEndDate())) > 0) {
			System.out.println("You Can Update Status");
			academicTrm.setAcTermSts(academicTrmDet.getAcTermSts());
		}}else{
			academicTrm.setAcTermSts(academicTrmDet.getAcTermSts());
		}
		
		academicTermDetailsKey.setDbTs(academicTrm.getDbTs());
		
		
		
		academicTrm.setrModId(userSessionDetails.getUserId());
		academicTrm.setInstId(userSessionDetails.getInstId());
		academicTrm.setBranchId(userSessionDetails.getBranchId());
		
		acadTrmDetailsDao.updateAcademicTermDetailsRec(academicTrm, academicTermDetailsKey);
         System.out.println("Academic term status  Pre: "+academicTrm.getAcTermSts());
         System.out.println("Academic term status  Clo: "+academicTrmDet.getAcTermSts());
		//If Academic Term is Clos
		if((oldStatus.equals(ApplicationConstant.ACT_TRM_PRESENT))&&
				((academicTrmDet.getAcTermSts().equals(ApplicationConstant.ACT_TRM_PREVIOUS))||(
						academicTrmDet.getAcTermSts().equals(ApplicationConstant.ACT_TRM_CLOSED)))){
		System.out.println("inside if to update");
		StudentAttendanceMaster studentAttendanceMaster=new StudentAttendanceMaster();
		studentAttendanceMaster.setrModId(userSessionDetails.getUserId());
		studentAttendanceMaster.setStatus(ApplicationConstant.ATTENDANCE_STATUS_LOCKED);
		StudentAttendanceMasterKey studentAttendanceMasterKey=new StudentAttendanceMasterKey();
		commonBusiness.changeObject(studentAttendanceMasterKey, academicTrm);
		studentAttendanceMasterKey.setStatus(ApplicationConstant.ATTENDANCE_STATUS_LOCKED);
		studentAttendanceMasterDAO.updateAttendanceStatusLock(studentAttendanceMaster, studentAttendanceMasterKey);		
	/*	// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.ACADTERM_UPDATE, " ");
		logger.debug("Completed Functional Auditing");*/
		}
		// functional audit
					doAudit.doFunctionalAudit(userSessionDetails,
							AuditConstant.ACADTERM_UPDATE, " ");
					logger.debug("Completed Functional Auditing");
					
					// Database audit
						
					AcademicTermDetails academicTermDetailsForAudit = null;
							academicTermDetailsKey.setDbTs(0);
							try {
								academicTermDetailsForAudit=acadTrmDetailsDao.selectAcademicTermDetailsRec(academicTermDetailsKey);
							} catch (NoDataFoundException e) {
								logger.error("No data found  Exception occured in update academic calender:");
								e.printStackTrace();
							}						
							academicTermDetailsKey.setDbTs(academicTermDetailsForAudit.getDbTs());			
							String newRecString = academicTermDetailsForAudit.toStringForAuditAcademicTermRecord();
							doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
									TableNameConstant.ACADEMIC_TERM_DETAILS,
									academicTermDetailsKey.toStringForAuditAcademicCalendarKey(),
									oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
									"");

							logger.debug("Completed Database Auditing");
		}
		}
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteAcademicTermDetailsRec(
			AcademicTermDetailsVO acadTrmDetailVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		logger.debug("inside update Academic Term Details method");
		
		AcademicTermDetails academicTrmDet = new AcademicTermDetails();
		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		// map the UIObject to Pojo
		
		commonBusiness.changeObject(academicTrmDet, acadTrmDetailVO);
		academicTermDetailsKey.setInstId(userSessionDetails.getInstId());
		academicTermDetailsKey.setBranchId(userSessionDetails.getBranchId());
		academicTermDetailsKey.setAcTerm(academicTrmDet.getAcTerm());
		academicTermDetailsKey.setAcYear(academicTrmDet.getAcYear());
		AcademicTermDetails academicTrm = null;
		
		academicTrm = acadTrmDetailsDao.selectAcademicTermDetailsRec(academicTermDetailsKey);
		String oldRecString = academicTrm.toStringForAuditAcademicTermRecord();
		academicTrm.setrModId(userSessionDetails.getUserId());
		acadTrmDetailsDao.deleteAcademicTermDetailsRec(academicTrm, academicTermDetailsKey);
		// functional audit
					doAudit.doFunctionalAudit(userSessionDetails,
							AuditConstant.ACADTERM_DELETE, " ");
					logger.debug("Completed Functional Auditing");
						// Database audit
					
					
					
					
					doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
							TableNameConstant.ACADEMIC_TERM_DETAILS,
							academicTermDetailsKey.toStringForAuditAcademicCalendarKey(),
							oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, "",
							"");

					logger.debug("Completed Database Auditing");
		
	}
	
	@Override
	public void selectAcademicTermList(AcademicTermMasterVO academicTermMasterVO,
			UserSessionDetails
			userSessionDetails)
			throws NoDataFoundException {
		
		List<AcademicTermList> academicTermLists = academicTermListDAO.getAcademicTermList(
				userSessionDetails.getInstId(), userSessionDetails.getBranchId());
		List<AcademicTermDetailsVO> academicTermDetailsVOs = new ArrayList<AcademicTermDetailsVO>();
		
		for (int i = 0; i < academicTermLists.size(); i++) {
			AcademicTermList academicTermList = academicTermLists.get(i);
			AcademicTermDetailsVO acadTrmDetailVO = new AcademicTermDetailsVO();
			commonBusiness.changeObject(acadTrmDetailVO, academicTermList);
			acadTrmDetailVO.setRowId(i);
			academicTermDetailsVOs.add(acadTrmDetailVO);
		}
		academicTermMasterVO.setAcademicTermDetailsVOs(academicTermDetailsVOs);
	}
	@Override
	public void updateDelFlg(AcademicTermDetails academicTermDetails,UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws NoDataFoundException, UpdateFailedException, DuplicateEntryException, DatabaseException {
		
		logger.debug("inside update Academic Term Details method");
		
		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		
		academicTermDetailsKey.setInstId(academicTermDetails.getInstId());
		academicTermDetailsKey.setBranchId(academicTermDetails.getBranchId());
		academicTermDetailsKey.setAcTerm(academicTermDetails.getAcTerm());
		academicTermDetailsKey.setAcYear(academicTermDetails.getAcYear());
		AcademicTermDetails academicTrm = null;
		
			academicTrm = acadTrmDetailsDao.selectAcademicTermDetailsDelFlgRec(academicTermDetailsKey);
		
			
		if((academicTrm!=null)&&(academicTrm.getDelFlag().equals("Y"))){
			academicTermDetailsKey.setDbTs(academicTrm.getDbTs());
		acadTrmDetailsDao.updateAcademicTermToDelFlgNRecs(academicTrm, academicTermDetailsKey);
		// functional audit
					doAudit.doFunctionalAudit(userSessionDetails,
							AuditConstant.ACADTERM_INSERT, " ");
					logger.debug("Completed Functional Auditing");
					
					
		}else{
			throw new DuplicateEntryException();
		}
		
		
	}
}
