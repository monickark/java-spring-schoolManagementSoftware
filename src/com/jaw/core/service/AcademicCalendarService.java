package com.jaw.core.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.jaw.common.exceptions.AcadCalFutureDateDeleteFailedException;
import com.jaw.common.exceptions.AcadCalendarDeleteFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForAcaTermHolGenException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.core.controller.AcademicCalendarMasterVO;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.controller.StudentGroupMasterVO;
import com.jaw.core.controller.StudentGroupVO;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarKey;
import com.jaw.core.dao.AcademicCalendarListKey;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.HolidayMaintenance;
import com.jaw.core.dao.HolidayMaintenanceKey;
import com.jaw.core.dao.IAcademicCalendarDAO;
import com.jaw.core.dao.IAcademicCalendarListDAO;
import com.jaw.core.dao.IAcademicTermDetailsDAO;
import com.jaw.core.dao.IHolidayMaintenanceDAO;
import com.jaw.core.dao.IHolidayMaintenanceListDAO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.dao.StudentGroupMasterKey;
import com.jaw.core.dao.StudentGroupMasterListKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

//Academic Calendar Service Class
@Service
public class AcademicCalendarService implements IAcademicCalendarService {
	// Logging
	Logger logger = Logger.getLogger(AcademicCalendarService.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	IAcademicCalendarDAO acadCalendarDao;
	@Autowired
	IAcademicCalendarListDAO acadCalendarListDao;
	@Autowired
	IAcademicTermDetailsDAO acadTrmDetailDao;
	@Autowired
	HolidayInsertionHelper holidayInsertHelper;
	@Autowired
	HolidayUpdateHelper holidayUpdateHelper;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	
	@Autowired
	IHolidayMaintenanceDAO holidayMaintenanceDAO;
	@Transactional(rollbackFor = Exception.class)
	@Override	
	public void insertAcademicCalRec(AcademicCalendarVO acadcaVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException, DuplicateEntryForAcaTermHolGenException, NoDataFoundException, UpdateFailedException, DuplicateEntryForHolGenException
			 {
		logger.debug("inside insert Academic Calendar method");
		AcademicCalendar academicCalendar = new AcademicCalendar();
		// map the UIObject to Pojo
		commonBusiness.changeObject(academicCalendar, acadcaVO);
		academicCalendar.setDbTs(1);
		academicCalendar.setInstId(userSessionDetails.getInstId());
		academicCalendar.setBranchId(userSessionDetails.getBranchId());
		academicCalendar.setrCreId(userSessionDetails.getUserId());
		academicCalendar.setrModId(userSessionDetails.getUserId());
		academicCalendar.setDelFlag("N");
		academicCalendar.setAcItemId(AlphaSequenceUtil.generateAlphaSequence(
				ApplicationConstant.STRING_ACAD_CALENDAR_SEQ, simpleIdGenerator
						.getNextId(SequenceConstant.ACADEMIC_CALENDAR_SEQUENCE,
								1)));
		if(acadCalendarDao.checkAcademicCalRecExist(academicCalendar)!=0){
			throw new DuplicateEntryException();
		}
		else{
		if (academicCalendar.getItemType().equals(ApplicationConstant.ACT_TRM_ITEM_HOL)) {
			if(checkHolidayEntered(academicCalendar)!=0){
				throw new DuplicateEntryForAcaTermHolGenException();
			}else{
			try {
				String studentGrpId = "";
				
					holidayInsertHelper.insertHolidayRec(
							academicCalendar.getAcTerm(),
							academicCalendar.getItemStartDate(),
							academicCalendar.getItemEndDate(), studentGrpId,
							userSessionDetails);
			
				// functional audit
				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.HOL_GENERATE, "Holiday has been generated " +
							"successfully for "+academicCalendar.getAcTerm() +" Academic term and Branch Id  :"+academicCalendar.getBranchId() );
				logger.debug("Completed Functional Auditing");
			} catch (ParseException e) {
				logger.error("Parse Exception Occured");
				e.printStackTrace();
			}}
		}
		if (checkAcTermValidation(academicCalendar.getAcTerm(),
				userSessionDetails)) {
			acadCalendarDao.insertAcademicCalRec(academicCalendar);
			
			// functional audit
						doAudit.doFunctionalAudit(userSessionDetails,
								AuditConstant.ACADCAL_INSERT, " ");
						logger.debug("Completed Functional Auditing");
		} else {
			logger.error("Duplicate Entry Exception Occured");
			throw new DuplicateEntryException();
		}}

	}

	// Get AcTerm Date for Validation
	@Override
	public AcademicCalendarVO getAcademicTermDateRec(
			AcademicCalendarVO academicCalKey,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {

		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		academicTermDetailsKey.setAcTerm(academicCalKey.getAcTerm());
		academicTermDetailsKey.setInstId(userSessionDetails.getInstId());
		academicTermDetailsKey.setBranchId(userSessionDetails.getBranchId());

		AcademicTermDetails atd = acadTrmDetailDao
				.selectAcademicTermDetailsRec(academicTermDetailsKey);
System.out.println("academic term detailsssssssssssss"+atd.toString());
		academicCalKey.setItemStartDate(atd.getTermStartDate());
		academicCalKey.setItemEndDate(atd.getTermEndDate());
		return academicCalKey;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateAcademicCalRec(AcademicCalendarVO acadcaVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		logger.debug("inside update Academic Calendar method");
		AcademicCalendar academicCalendar = new AcademicCalendar();
		AcademicCalendarKey academicCalendarKey = new AcademicCalendarKey();
		// map the UIObject to Pojo
		commonBusiness.changeObject(academicCalendar, acadcaVO);
		if (checkAcTermValidation(academicCalendar.getAcTerm(),
				userSessionDetails)) {
			academicCalendarKey.setAcItemId(academicCalendar.getAcItemId());
			academicCalendarKey.setInstId(userSessionDetails.getInstId());
			academicCalendarKey.setBranchId(userSessionDetails.getBranchId());

			AcademicCalendar academicCalendarnew = acadCalendarDao
					.selectAcademicCalRec(academicCalendarKey);
			AcademicCalendar updateAcademicCalendar=new AcademicCalendar();
			commonBusiness.changeObject(updateAcademicCalendar, academicCalendarnew);
			academicCalendarKey.setDbTs(academicCalendarnew.getDbTs());
			updateAcademicCalendar.setrModId(userSessionDetails.getUserId());
			updateAcademicCalendar.setItemDesc(academicCalendar.getItemDesc());
			updateAcademicCalendar.setInstId(userSessionDetails.getInstId());
			updateAcademicCalendar.setBranchId(userSessionDetails.getBranchId());
			acadCalendarDao.updateAcademicCalRec(updateAcademicCalendar,
					academicCalendarKey);
			
			// functional audit
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.ACADCAL_UPDATE, " ");
			logger.debug("Completed Functional Auditing");
			
			// Database audit
					String oldRecString = academicCalendarnew.toStringForAuditAcademicCalendarRecord();
					AcademicCalendar academicCalendarForAudit = null;
					academicCalendarKey.setDbTs(0);
					try {
						academicCalendarForAudit=acadCalendarDao.selectAcademicCalRec(academicCalendarKey);
					} catch (NoDataFoundException e) {
						logger.error("No data found  Exception occured in update academic calender:");
						e.printStackTrace();
					}						
					academicCalendarKey.setDbTs(academicCalendarForAudit.getDbTs());			
					String newRecString = academicCalendarForAudit
							.toStringForAuditAcademicCalendarRecord();
					doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
							TableNameConstant.ACADEMIC_CALENDER,
							academicCalendarKey.toStringForAuditAcademicCalendarKey(),
							oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
							"");

					logger.debug("Completed Database Auditing");
		} else {
			logger.error("Update Failed Exception Occured");
			throw new UpdateFailedException();
		}

	}

	@Override
	public AcademicCalendarMasterVO selectStudentGroupList(
			AcademicCalendarMasterVO acadCalMtrVO,UserSessionDetails userSessionDetails) throws NoDataFoundException {

		AcademicCalendarListKey acaCalListKey = new AcademicCalendarListKey();
		if(acadCalMtrVO.getAcadClSeaVo()!=null){
		commonBusiness.changeObject(acaCalListKey, acadCalMtrVO.getAcadClSeaVo());
		}
		acaCalListKey.setInstId(userSessionDetails.getInstId());
		acaCalListKey.setBranchId(userSessionDetails.getBranchId());
		List<AcademicCalendar> acaCalList = acadCalendarListDao
				.getAcademicCalendarList(acaCalListKey);
		List<AcademicCalendarVO> acaCalVOs = new ArrayList<AcademicCalendarVO>();

		for (int i = 0; i < acaCalList.size(); i++) {
			AcademicCalendar acadCalendar = acaCalList.get(i);
			AcademicCalendarVO acadCalVO = new AcademicCalendarVO();
			commonBusiness.changeObject(acadCalVO, acadCalendar);
			acadCalVO.setRowId(i);
			acaCalVOs.add(acadCalVO);
		}
		acadCalMtrVO.setAcadCalVOList(acaCalVOs);
		return acadCalMtrVO;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteAcademicCalendarRec(AcademicCalendarVO acadCalVo,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, AcadCalendarDeleteFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, AcadCalFutureDateDeleteFailedException, ParseException, DuplicateEntryForHolGenException, UpdateFailedException {

		AcademicCalendarKey acadCalKey = new AcademicCalendarKey();
		commonBusiness.changeObject(acadCalKey, acadCalVo);

		// Check AcTerm is Future
		AcademicTermDetailsKey academicTermDetails = new AcademicTermDetailsKey();
		academicTermDetails.setAcTerm(acadCalVo.getAcTerm());
		academicTermDetails.setInstId(userSessionDetails.getInstId());
		academicTermDetails.setBranchId(userSessionDetails.getBranchId());
		acadCalKey.setInstId(userSessionDetails.getInstId());
		acadCalKey.setBranchId(userSessionDetails.getBranchId());

		AcademicCalendar acadCalNew = acadCalendarDao
				.selectAcademicCalRec(acadCalKey);
		AcademicCalendar deleteAcademicCalendar=new AcademicCalendar();
		commonBusiness.changeObject(deleteAcademicCalendar, acadCalNew);
		acadCalKey.setDbTs(acadCalNew.getDbTs());
		deleteAcademicCalendar.setrModId(userSessionDetails.getUserId());
		deleteAcademicCalendar.setDbTs(acadCalNew.getDbTs()+1);
		deleteAcademicCalendar.setDelFlag("Y");
		boolean delete = false;
		if ((dateUtil.getDateFormatByString(deleteAcademicCalendar.getItemStartDate())
				.compareTo(dateUtil.getCurrentDateInDateDataType()) > 0)
				|| (dateUtil.getDateFormatByString(
						deleteAcademicCalendar.getItemStartDate()).compareTo(
						dateUtil.getCurrentDateInDateDataType()) == 0)) {
			delete = true;
		} else if ((dateUtil.getDateFormatByString(deleteAcademicCalendar.getItemEndDate())
				.compareTo(dateUtil.getCurrentDateInDateDataType())< 0)
				|| (dateUtil.getDateFormatByString(deleteAcademicCalendar.getItemEndDate())
						.compareTo(dateUtil.getCurrentDateInDateDataType()) == 0)) {
			delete = true;
		} else {
			delete = false;
			logger.error("AcadCalendarDeleteFailedException Occured");
			throw new AcadCalFutureDateDeleteFailedException();
		}

		if (delete) {		
			if (deleteAcademicCalendar.getItemType().equals(ApplicationConstant.ACT_TRM_ITEM_HOL)) {
			
				String studentGrpId="";
				holidayUpdateHelper.updateHolidayRec(deleteAcademicCalendar.getAcTerm(), deleteAcademicCalendar.getItemStartDate(), 
						deleteAcademicCalendar.getItemEndDate(), studentGrpId, userSessionDetails);
			}
			
			acadCalendarDao.deleteAcademicCalRec(deleteAcademicCalendar, acadCalKey);
			// functional audit
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.ACADCAL_DELETE, " ");
			logger.debug("Completed Functional Auditing");
			// functional audit
						doAudit.doFunctionalAudit(userSessionDetails,
								AuditConstant.HOL_UPDATE, "Holiday has been updated " +
							"successfully for "+deleteAcademicCalendar.getAcTerm() +" Academic term and Branch Id  :"+deleteAcademicCalendar.getBranchId());
						logger.debug("Completed Functional Auditing");
			
			// Database audit
			
			String oldRecString = acadCalNew
					.toStringForAuditAcademicCalendarRecord();
			
			String newRecString = deleteAcademicCalendar
					.toStringForAuditAcademicCalendarRecord();
			doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
					TableNameConstant.ACADEMIC_CALENDER,
					acadCalKey.toStringForAuditAcademicCalendarKey(),
					oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, newRecString,
					"");

			logger.debug("Completed Database Auditing");
		}

	}

	@Override
	public boolean checkAcTermValidation(String acTerm,
			UserSessionDetails userSessionDetails) {
		AcademicTermDetailsKey academicTermDetails = new AcademicTermDetailsKey();
		academicTermDetails.setAcTerm(acTerm);
		academicTermDetails.setInstId(userSessionDetails.getInstId());
		academicTermDetails.setBranchId(userSessionDetails.getBranchId());
		boolean result = false;
		AcademicTermDetails academicTermDetails2;
		try {
			academicTermDetails2 = acadTrmDetailDao
					.checkAcTermValidation(academicTermDetails);
			if (academicTermDetails2.getAcTermSts().equals(ApplicationConstant.ACT_TRM_CLOSED)) {
				result = false;
			} else {
				result = true;
			}
		} catch (NoDataFoundException e) {
			logger.error("No Data found Exception Occured");
			result = false;
		}

		return result;
	}
	
	@Override
	public AcademicCalendarVO getAcTermDateForValidation(String acTerm,
			UserSessionDetails userSessionDetails) {
		AcademicCalendar academicCalendar=null;
		try {
			academicCalendar=acadCalendarDao.selectAcademicCalDateForValidation(acTerm, userSessionDetails);
		} catch (NoDataFoundException e) {
			logger.error("No Data found exception occured ");
		}
		AcademicCalendarVO acadeCalVo=new AcademicCalendarVO();
		commonBusiness.changeObject(acadeCalVo, academicCalendar);
		return acadeCalVo;
	}

	@Override
	public int checkHolidayEntered(AcademicCalendar academicCalendar)  {	
	HolidayMaintenanceKey holidayMaintenanceKey = new HolidayMaintenanceKey();
	holidayMaintenanceKey.setAcTerm(academicCalendar.getAcTerm());
	holidayMaintenanceKey.setInstId(academicCalendar.getInstId());
	holidayMaintenanceKey.setBranchId(academicCalendar.getBranchId());
	List<String> holDates=dateUtil.getDateBetweenTwoDates(academicCalendar.getItemStartDate(), academicCalendar.getItemEndDate());
	
	int result=holidayMaintenanceDAO.checkHolidayDateExistForAcademicCalendar(holidayMaintenanceKey, holDates);
	System.out.println("result in academic calendar  holiday check"+result);
	return result;
	
	}

	@Override
	public int checkHolidayExistForStudentGrp(AcademicCalendar academicCalendar) {	
		HolidayMaintenanceKey holidayMaintenanceKey = new HolidayMaintenanceKey();
		holidayMaintenanceKey.setAcTerm(academicCalendar.getAcTerm());
		holidayMaintenanceKey.setInstId(academicCalendar.getInstId());
		holidayMaintenanceKey.setBranchId(academicCalendar.getBranchId());
		List<String> holDates=dateUtil.getDateBetweenTwoDates(academicCalendar.getItemStartDate(), academicCalendar.getItemEndDate());
		
		int results=holidayMaintenanceDAO.checkHolidayDateExistForAcademicCalendarAddlHol(holidayMaintenanceKey, holDates);
		System.out.println("result in academic calendar  holiday check"+results);
		return results;
		
		}

	@Override
	public void removeAddlHolForStudentGrp(AcademicCalendar academicCalendar) {
		HolidayMaintenanceKey holidayMaintenanceKey = new HolidayMaintenanceKey();
		holidayMaintenanceKey.setAcTerm(academicCalendar.getAcTerm());
		holidayMaintenanceKey.setInstId(academicCalendar.getInstId());
		holidayMaintenanceKey.setBranchId(academicCalendar.getBranchId());
		List<String> holDates=dateUtil.getDateBetweenTwoDates(academicCalendar.getItemStartDate(), academicCalendar.getItemEndDate());
		
		holidayMaintenanceDAO.removeHolidayAcademicCalendarAddlHol(holidayMaintenanceKey, holDates);
	
		
	}

	@Override
	public List<AcademicCalendarVO> getAcademicCalendarDetailsForDashBoard(
			AcademicCalendarListKey academicCalendarListKey,String monthYear)
			throws NoDataFoundException {
		
		List<String>  dates=dateUtil.getMonthStartAndEndDate(monthYear);
		System.out.println("start date         "+dates.get(0));
		System.out.println("end date         "+dates.get(1));
		academicCalendarListKey.setItemStartDate(dates.get(0));
		
			academicCalendarListKey.setItemEndDate(dates.get(1));
	   /* SimpleDateFormat format = new SimpleDateFormat(/////////add your format here);
	    		System.out.println("Calculated month end date : " + format.format(calculatedDate));*/
		
		
		List<AcademicCalendar> acaCalList = acadCalendarListDao.getAcademicCalendarDetailsForDashBoard(academicCalendarListKey);
		List<AcademicCalendarVO> acaCalVOs = new ArrayList<AcademicCalendarVO>();

		for (int i = 0; i < acaCalList.size(); i++) {
			AcademicCalendar acadCalendar = acaCalList.get(i);
			AcademicCalendarVO acadCalVO = new AcademicCalendarVO();
			commonBusiness.changeObject(acadCalVO, acadCalendar);
			acadCalVO.setRowId(i);
			acaCalVOs.add(acadCalVO);
		}		
		return acaCalVOs;
	}

	@Override
	public List<String> getListOfMonthsForAcademicTerm(UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
		AcademicCalendarVO acadCalVOff = new AcademicCalendarVO();
		acadCalVOff.setAcTerm(userSessionDetails.getCurrAcTerm());		
		AcademicCalendarVO acv = getAcademicTermDateRec(acadCalVOff,
				userSessionDetails);
		System.out.println("academic term detailssssssssss"+acv.toString());
				List<String> months=dateUtil.getMonthBetweenTwoDates(acv.getItemStartDate(), acv.getItemEndDate());
				for(int i=0;i<months.size();i++){
					System.out.println("list monthsssssssssssssss"+months.get(i));
				}
		return months;
	}
	
}
