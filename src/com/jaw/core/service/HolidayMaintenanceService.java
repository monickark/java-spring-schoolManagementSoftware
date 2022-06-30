package com.jaw.core.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.util.DateUtil;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.controller.HolidayGenerationVO;
import com.jaw.core.controller.StudentGroupMasterController;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarListKey;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.core.dao.HolidayMaintenance;
import com.jaw.core.dao.HolidayMaintenanceKey;
import com.jaw.core.dao.IAcademicTermDetailsDAO;
import com.jaw.core.dao.IAcademicTermDetailsListDAO;
import com.jaw.core.dao.IHolidayMaintenanceDAO;
import com.jaw.core.dao.IHolidayMaintenanceListDAO;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

@Service
public class HolidayMaintenanceService implements IHolidayMaintenanceService {
	// Logging
		Logger logger = Logger.getLogger(HolidayMaintenanceService.class);
	@Autowired
	IAcademicTermDetailsDAO academicTrmDet;
	@Autowired
	IHolidayMaintenanceListDAO holidayMaintenanceListDAO;
	@Autowired
	IHolidayMaintenanceDAO holidayMaintenanceDAO;
	@Autowired
	IAcademicTermDetailsListDAO academicTermDetailsListDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	DoAudit doAudit;
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertHolidayMaintenanceBatchRec(
			HolidayGenerationVO holidayGenerationVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryForHolGenException, DuplicateEntryException, DatabaseException {
		HolidayMaintenance holidayMaintenance = new HolidayMaintenance();

		// map the UIObject to Pojo
		commonBusiness.changeObject(holidayMaintenance, holidayGenerationVO);
		holidayMaintenance.setInstId(userSessionDetails.getInstId());
		HolidayMaintenanceKey holidayMaintenanceKey = new HolidayMaintenanceKey();

		commonBusiness.changeObject(holidayMaintenanceKey, holidayMaintenance);
		holidayMaintenanceKey.setInstId(userSessionDetails.getInstId());
		AcademicTermDetailsKey acdTrmDetKey = new AcademicTermDetailsKey();

		acdTrmDetKey.setInstId(userSessionDetails.getInstId());
		acdTrmDetKey.setBranchId(holidayMaintenance.getBranchId());
		acdTrmDetKey.setAcTerm(holidayMaintenance.getAcTerm());
		if (holidayMaintenanceDAO.checkHolRecsAvble(holidayMaintenanceKey)){
			// get date and weekly holiday
			AcademicTermDetails actd = academicTrmDet
					.getAcaTrmRecForHolidayGeneration(acdTrmDetKey);
			getHolidayObjects(userSessionDetails, holidayMaintenance, actd);
			// insert holidays
			holidayMaintenanceListDAO
					.insertHolidayMaintenanceListRecs(getHolidayObjects(
							userSessionDetails, holidayMaintenance, actd));
			// functional audit
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.HOL_GENERATE, "Holiday has been generated " +
							"successfully for "+holidayMaintenance.getAcTerm() +" Academic term and Branch Id  :"+holidayMaintenance.getBranchId());
			logger.debug("Completed Functional Auditing");

		}else{
			throw new DuplicateEntryForHolGenException();
		}

	}

	

	public List<HolidayMaintenance> getHolidayObjects(
			UserSessionDetails userSessionDetails,
			HolidayMaintenance holidayMaintance,
			AcademicTermDetails academicTrmD) {
		List<HolidayMaintenance> hml = new ArrayList<HolidayMaintenance>();
		SimpleDateFormat sdf = new SimpleDateFormat(ApplicationConstant.SIMPLE_DATE_FORMAT);
		Date startDate = dateUtil.getDateFormatByString(academicTrmD
				.getTermStartDate());
		Date endDate = dateUtil.getDateFormatByString(academicTrmD
				.getTermEndDate());

		Calendar startDateC = Calendar.getInstance();
		startDateC.clear();
		startDateC.setTime(startDate);
		Calendar defDateC = Calendar.getInstance();
		defDateC.clear();
		defDateC.setTime(startDate);
		int dayOfWeek = startDateC.get(Calendar.DAY_OF_WEEK);

		Calendar endDateC = Calendar.getInstance();
		endDateC.clear();
		endDateC.setTime(endDate);
		long diffInDays=dateUtil.getDiffBetweenTwoDates(startDateC, endDateC);
		logger.debug("Difference between two Dates  "+diffInDays);

		List<String> leaveDates = new ArrayList<String>();
		if ((startDateC.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
			leaveDates.add(sdf.format(defDateC.getTime()));
		}
		for (int i = 7 - dayOfWeek; i < diffInDays; i = i + 7) {
			defDateC = (Calendar) startDateC.clone();
			;
			if (academicTrmD.getWeeklyHoliday().equals("2")) {
				defDateC.add(Calendar.DATE, i);
				leaveDates.add(sdf.format(defDateC.getTime()));
				defDateC = (Calendar) startDateC.clone();
				;
				defDateC.add(Calendar.DATE, i + 1);
				leaveDates.add(sdf.format(defDateC.getTime()));

			} else if (academicTrmD.getWeeklyHoliday().equals("1")) {
				defDateC.add(Calendar.DATE, i + 1);
				leaveDates.add(sdf.format(defDateC.getTime()));
			}
		}
		if ((academicTrmD.getWeeklyHoliday().equals("2"))
				&& (endDateC.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
			leaveDates.add(sdf.format(endDateC.getTime()));
		}
		for (int i = 0; i < leaveDates.size(); i++) {			
			HolidayMaintenance hm = new HolidayMaintenance();
			hm.setDbTs(1);
			hm.setInstId(userSessionDetails.getInstId());
			hm.setBranchId(holidayMaintance.getBranchId());
			hm.setrCreId(userSessionDetails.getUserId());
			hm.setrModId(userSessionDetails.getUserId());
			hm.setDelFlag("N");
			hm.setIsWklyHoliday("Y");
			hm.setHolDate(leaveDates.get(i));
			hm.setAcTerm(holidayMaintance.getAcTerm());
			hml.add(hm);
		}
		return hml;
	}

	@Override
	public boolean checkHolRecsAvble(
			AcademicTermDetailsKey academicTermDetailsKey,
			UserSessionDetails userSessionDetails) {
		HolidayMaintenanceKey holidayMaintenanceKey = new HolidayMaintenanceKey();
		holidayMaintenanceKey.setInstId(userSessionDetails.getInstId());
		holidayMaintenanceKey.setBranchId(userSessionDetails.getBranchId());
		holidayMaintenanceKey.setAcTerm(academicTermDetailsKey.getAcTerm());
		return holidayMaintenanceDAO.checkHolRecsAvble(holidayMaintenanceKey);
		
	}

	@Override
	public List<String> selectAcTrmForHlyGen(UserSessionDetails userSessionDetails,String branchId)
			 {
		AcademicTermDetailsKey academicTermDetailsKey=new AcademicTermDetailsKey();
		academicTermDetailsKey.setInstId(userSessionDetails.getInstId());
		academicTermDetailsKey.setBranchId(branchId);
		
		List<String> details=new ArrayList<String>();
		List<AcademicTermDetails> academicTrmDetails;
		try {
			academicTrmDetails = academicTermDetailsListDAO.getAcaTrmBasedBranchForHlyGen(academicTermDetailsKey);
			for (AcademicTermDetails academicTermDetails : academicTrmDetails) {
				details.add(academicTermDetails.getAcTerm()+"="+academicTermDetails.getAcYear());
				
			}
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Academic Term for Branch :" + e.getMessage());
			details.add("error");// TODO Auto-generated catch block			
		}
		
		
		
		
		return details;
	}
	
	@Override
	public List<AcademicCalendarVO> getHolidayDetailsForDashBoard(
			AcademicCalendarListKey academicCalendarListKey,String monthYear,SessionCache sessionCache)
			throws NoDataFoundException {
		
		List<String>  dates=dateUtil.getMonthStartAndEndDate(monthYear);
		System.out.println("start date         "+dates.get(0));
		System.out.println("end date         "+dates.get(1));
		academicCalendarListKey.setItemStartDate(dates.get(0));
		
			academicCalendarListKey.setItemEndDate(dates.get(1));
String studentGrpId="";
		System.out.println("student grp iddddddddddd"+sessionCache.getStudentSession());
		if(sessionCache.getStudentSession()!=null){
			studentGrpId=sessionCache.getStudentSession().getStuGrpId();	
		}
		List<AcademicCalendar> acaCalList = holidayMaintenanceListDAO.getHolidayDetailsForDashBoard(academicCalendarListKey,sessionCache.getUserSessionDetails().getUserMenuProfile(),
				studentGrpId);
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
	}
