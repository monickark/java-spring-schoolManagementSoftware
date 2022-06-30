package com.jaw.core.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.util.DateUtil;
import com.jaw.core.dao.HolidayMaintenance;
import com.jaw.core.dao.HolidayMaintenanceKey;
import com.jaw.core.dao.IHolidayMaintenanceDAO;
import com.jaw.core.dao.IHolidayMaintenanceListDAO;
import com.jaw.framework.sessCache.UserSessionDetails;
@Component
public class HolidayUpdateHelper {
	@Autowired
	DateUtil dateUtil;
	@Autowired
	IHolidayMaintenanceListDAO holidayMaintenanceListDao;
	@Autowired
	IHolidayMaintenanceDAO holidayMaintenanceDao;
	
	
	public void updateHolidayRec(String acTerm, String itemStartDate,
			String itemEndDate, String studentGrpId,
			UserSessionDetails userSessionDetails) throws ParseException, DuplicateEntryForHolGenException, NoDataFoundException,  UpdateFailedException{

		List<HolidayMaintenance> holidayMaits = new ArrayList<HolidayMaintenance>();
		List<HolidayMaintenanceKey> holidayMaitsKeys = new ArrayList<HolidayMaintenanceKey>();
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat(ApplicationConstant.SIMPLE_DATE_FORMAT);
		calendar.setTime(dateUtil.getDateFormatByString(itemStartDate));
		HolidayMaintenanceKey holidayMaintenanceKey=new HolidayMaintenanceKey();
		
		while (calendar.getTime().before(
				sdf.parse(itemEndDate))
				|| calendar.getTime().equals(
						sdf.parse(itemEndDate))) {
			Date resultado = calendar.getTime();
			holidayMaintenanceKey.setInstId(userSessionDetails.getInstId());
			holidayMaintenanceKey.setBranchId(userSessionDetails.getBranchId());
			holidayMaintenanceKey.setHolDate(sdf.format(resultado));
			holidayMaintenanceKey.setAcTerm(acTerm);
			holidayMaintenanceKey.setStudentGrpId(studentGrpId);
			HolidayMaintenance holidayMaintenance=holidayMaintenanceDao.selectHolidayMaintenanceRec(holidayMaintenanceKey);
			
			HolidayMaintenance hm = new HolidayMaintenance();
			hm.setDbTs(holidayMaintenance.getDbTs()+1);			
			hm.setrModId(userSessionDetails.getUserId());
			hm.setDelFlag("N");
			hm.setAcTerm(acTerm);
			hm.setStudentGrpId(studentGrpId);
			HolidayMaintenanceKey hmk = new HolidayMaintenanceKey();
			hmk.setInstId(userSessionDetails.getInstId());
			hmk.setBranchId(userSessionDetails.getBranchId());
			hmk.setHolDate(sdf.format(resultado));
			hmk.setAcTerm(acTerm);
			hmk.setDbTs(holidayMaintenance.getDbTs());
			hmk.setStudentGrpId(holidayMaintenance.getStudentGrpId());
			holidayMaits.add(hm);
			holidayMaitsKeys.add(hmk);
			calendar.add(Calendar.DATE, 1);
		}

		holidayMaintenanceListDao.updateHolidayMaintenanceListRecs(holidayMaits, holidayMaitsKeys);
	}
}
