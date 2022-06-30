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

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForAcaTermHolGenException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.util.DateUtil;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.HolidayMaintenance;
import com.jaw.core.dao.HolidayMaintenanceKey;
import com.jaw.core.dao.IHolidayMaintenanceDAO;
import com.jaw.core.dao.IHolidayMaintenanceListDAO;
import com.jaw.framework.sessCache.UserSessionDetails;
@Component
public class HolidayInsertionHelper {
	@Autowired
	DateUtil dateUtil;
	@Autowired
	IHolidayMaintenanceListDAO holidayMaintenanceListDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IHolidayMaintenanceDAO holidayMaintenanceDao;
	
	public void insertHolidayRec(String acTerm, String itemStartDate,
			String itemEndDate, String studentGrpId,
			UserSessionDetails userSessionDetails) throws ParseException, DuplicateEntryForAcaTermHolGenException, NoDataFoundException,DuplicateEntryForHolGenException, UpdateFailedException{

		List<HolidayMaintenance> holidayMaits = new ArrayList<HolidayMaintenance>();
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat(ApplicationConstant.SIMPLE_DATE_FORMAT);
		calendar.setTime(dateUtil.getDateFormatByString(itemStartDate));
		while (calendar.getTime().before(
				sdf.parse(itemEndDate))
				|| calendar.getTime().equals(
						sdf.parse(itemEndDate))) {
			Date resultado = calendar.getTime();
			HolidayMaintenance hm = new HolidayMaintenance();
			hm.setDbTs(1);
			hm.setInstId(userSessionDetails.getInstId());
			hm.setBranchId(userSessionDetails.getBranchId());
			hm.setrCreId(userSessionDetails.getUserId());
			hm.setrModId(userSessionDetails.getUserId());
			hm.setDelFlag("N");
			hm.setAcTerm(acTerm);
			hm.setHolDate(sdf.format(resultado));
			hm.setIsWklyHoliday("N");
			hm.setStudentGrpId(studentGrpId);
			holidayMaits.add(hm);
			calendar.add(Calendar.DATE, 1);
		}

		try {
			holidayMaintenanceListDao
					.insertHolidayMaintenanceListRecs(holidayMaits);
		} catch (DuplicateEntryForHolGenException e) {
			//throw new DuplicateEntryForAcaTermHolGenException();
			System.out.println("catchhhhhh");
			updateDelFlg(holidayMaits);
		}
	
	}
	
	void updateDelFlg(List<HolidayMaintenance> holidayMaits) throws NoDataFoundException, UpdateFailedException{
		System.out.println("update del flgggggggg");
		List<HolidayMaintenance> holidayMaitenances = new ArrayList<HolidayMaintenance>();
		List<HolidayMaintenanceKey> holidayMaitenancekeys = new ArrayList<HolidayMaintenanceKey>();
		for(HolidayMaintenance holidayMaintenance :holidayMaits){
			holidayMaintenance.setDbTs(0);
			HolidayMaintenanceKey holidayMaintenanceKey=new HolidayMaintenanceKey();
			commonBusiness.changeObject(holidayMaintenanceKey, holidayMaintenance);
			HolidayMaintenance holidayMaintenancenew=holidayMaintenanceDao.selectHolidayMaintenanceRecForDelFlgY(holidayMaintenanceKey);	
			if(holidayMaintenancenew!=null){
			HolidayMaintenanceKey hmk = new HolidayMaintenanceKey();
			hmk.setInstId(holidayMaintenance.getInstId());
			hmk.setBranchId(holidayMaintenance.getBranchId());
			hmk.setHolDate(holidayMaintenance.getHolDate());
			hmk.setAcTerm(holidayMaintenance.getAcTerm());
			hmk.setDbTs(holidayMaintenancenew.getDbTs());
			hmk.setStudentGrpId(holidayMaintenancenew.getStudentGrpId());
			holidayMaitenances.add(holidayMaintenancenew);
			holidayMaintenancenew.setDbTs(holidayMaintenancenew.getDbTs()+1);
			holidayMaitenancekeys.add(hmk);			
			}
		}
		holidayMaintenanceListDao.updateHolidayMaintenanceListToDelFlgNRecs(holidayMaitenances, holidayMaitenancekeys);
	}
}
