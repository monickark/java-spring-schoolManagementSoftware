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
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import java.text.ParseException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.controller.AddlHolidaysMasterVO;
import com.jaw.core.controller.AddlHolidaysVO;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AddlHolidays;
import com.jaw.core.dao.AddlHolidaysKey;
import com.jaw.core.dao.HolidayMaintenanceKey;
import com.jaw.core.dao.IAddlHolidaysDAO;
import com.jaw.core.dao.IAddlHolidaysListDAO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.core.dao.IHolidayMaintenanceDAO;
import com.jaw.common.exceptions.batch.DuplicateEntryForAcaTermHolGenException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;

@Service
public class AdditionalHolidaysService implements IAdditionalHolidaysService{
	// Logging
			Logger logger = Logger.getLogger(AdditionalHolidaysService.class);

			@Autowired
			IAddlHolidaysDAO addlHolidaysDao;
			@Autowired
	        IHolidayMaintenanceDAO holidayMaintenanceDAO;
			@Autowired
			CommonBusiness commonBusiness;
			@Autowired
	        HolidayInsertionHelper holidayInsertHelper;
			@Autowired
			HolidayUpdateHelper holidayUpdateHelper;
			@Autowired
			@Qualifier(value = "simpleIdGenerator")
			private IIdGeneratorService simpleIdGenerator;
			@Autowired
			IAddlHolidaysListDAO addlHolidaysListDao;
			@Autowired
			DoAudit doAudit;
			@Autowired
			DateUtil dateUtil;
	@Transactional(rollbackFor = Exception.class)	
	@Override
	public void insertAdditionalHolidaysRec(AddlHolidaysVO addlHVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException,DuplicateEntryForHolGenException,DuplicateEntryForAcaTermHolGenException, NoDataFoundException, UpdateFailedException {
		logger.debug("inside insert Additional Holidays method");
		AddlHolidays addlHolidays = new AddlHolidays();
		// map the UIObject to Pojo
		commonBusiness.changeObject(addlHolidays, addlHVO);
		addlHolidays.setDbTs(1);
		addlHolidays.setInstId(userSessionDetails.getInstId());
		addlHolidays.setBranchId(userSessionDetails.getBranchId());
		addlHolidays.setrCreId(userSessionDetails.getUserId());
		addlHolidays.setrModId(userSessionDetails.getUserId());
		addlHolidays.setDelFlag("N");	
		addlHolidays.setAhId(AlphaSequenceUtil.generateAlphaSequence(
				ApplicationConstant.ADDL_HOLIDAYS_SEQ,
				simpleIdGenerator.getNextId(
						SequenceConstant.ADDL_HOLIDAYS_SEQUENCE, 1)));	
		
		try {
				String studentGrpId=addlHolidays.getStudentGrpId();
				HolidayMaintenanceKey hmk = new HolidayMaintenanceKey();
				hmk.setInstId(userSessionDetails.getInstId());
				hmk.setBranchId(userSessionDetails.getBranchId());
				hmk.setAcTerm(addlHolidays.getAcTerm());
				List<String> holDates=dateUtil.getDateBetweenTwoDates(addlHolidays.getHolFromDate(), addlHolidays.getHolToDate());
				if(holidayMaintenanceDAO.checkHolidayDateExist(hmk, holDates,addlHolidays.getStudentGrpId())==0){
				holidayInsertHelper.insertHolidayRec(addlHolidays.getAcTerm(),addlHolidays.getHolFromDate(),addlHolidays.getHolToDate(),studentGrpId,userSessionDetails);
				// functional audit
				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.HOL_GENERATE, "Addl Holiday has been generated " +
							"successfully for "+addlHolidays.getAcTerm() +" AcadTerm and BranchId  :"+addlHolidays.getBranchId()+"StudentGrp"+addlHolidays.getStudentGrpId() );
				logger.debug("Completed Functional Auditing");
				}else{
					throw new DuplicateEntryForAcaTermHolGenException();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		addlHolidaysDao.insertAddHolidaysRec(addlHolidays);		
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.ADDL_HOL_INSERT, " ");
		logger.debug("Completed Functional Auditing");
		
	}

	@Override
	public AddlHolidaysMasterVO getListOfAddlHolidays(
			AddlHolidaysMasterVO addHolidaysSearch,UserSessionDetails userSessionDetails) throws NoDataFoundException {
		AddlHolidaysKey addlHolidaysKey = new AddlHolidaysKey();	
		commonBusiness.changeObject(addlHolidaysKey, addHolidaysSearch.getAddlHolidaysSearchVo());
		addlHolidaysKey.setInstId(userSessionDetails.getInstId());
		addlHolidaysKey.setBranchId(userSessionDetails.getBranchId());
		ArrayList<AddlHolidaysVO> listOfAddlHolidays = new ArrayList<AddlHolidaysVO>();
		List<AddlHolidays> addlHolidaysList = addlHolidaysListDao.getAddlHolidaysList(addlHolidaysKey);
		for (int i = 0; i < addlHolidaysList.size(); i++) {
			AddlHolidays addlHolidays = addlHolidaysList.get(i);
			AddlHolidaysVO addlHolidaysVO = new AddlHolidaysVO();	
			commonBusiness.changeObject(addlHolidaysVO, addlHolidays);
			System.out.println("get remarks in for loop :"+addlHolidaysVO.getAhRemarks());
			addlHolidaysVO.setRowId(i);
			listOfAddlHolidays.add(addlHolidaysVO);
		}	
		System.out.println("list sizeeeeeee             : "+listOfAddlHolidays.size());
		addHolidaysSearch.setAddlHolidaysVOList(listOfAddlHolidays);
		return addHolidaysSearch;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void removeAddHolidaysRec(AddlHolidaysVO  addHolidays,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, ParseException, DuplicateEntryForHolGenException, UpdateFailedException {
		
		AddlHolidaysKey addlHolidayKey = new AddlHolidaysKey();
		commonBusiness.changeObject(addlHolidayKey, addHolidays);	
		addlHolidayKey.setInstId(userSessionDetails.getInstId());
		addlHolidayKey.setBranchId(userSessionDetails.getBranchId());		
		// get Data's from DB for dbts value		
		AddlHolidays selectedAddlHoliday = addlHolidaysDao.selectAddHolidaysRec(addlHolidayKey);
		String oldRecString =selectedAddlHoliday.toStringForAuditAddlHolidaysRecord();
		addlHolidayKey.setDbTs(selectedAddlHoliday.getDbTs());
		selectedAddlHoliday.setrModId(userSessionDetails.getUserId());
		
					// Database audit
					
					doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
							TableNameConstant.ADDL_HOLIDAYS,
							addlHolidayKey.toStringForAuditAddlHolidaysKeyRecord(),
							oldRecString, AuditConstant.TYPE_OF_OPER_DELETE,"", 
							"");

					logger.debug("Completed Database Auditing");
					holidayUpdateHelper.updateHolidayRec(selectedAddlHoliday.getAcTerm(), selectedAddlHoliday.getHolFromDate(), 
							selectedAddlHoliday.getHolToDate(), selectedAddlHoliday.getStudentGrpId(), userSessionDetails);
				
				
				
				// functional audit
							doAudit.doFunctionalAudit(userSessionDetails,
									AuditConstant.HOL_UPDATE, "Holiday has been updated " +
								"successfully for "+selectedAddlHoliday.getAcTerm() +" AcadTerm and Branch Id  :"+selectedAddlHoliday.getBranchId()+" StudentGrp Id :"+selectedAddlHoliday.getStudentGrpId());
							logger.debug("Completed Functional Auditing");	
							addlHolidaysDao.deleteAddHolidaysRec(selectedAddlHoliday, addlHolidayKey);
							// functional audit
										doAudit.doFunctionalAudit(userSessionDetails,
												AuditConstant.ADDL_HOL_DELETE, " ");
										logger.debug("Completed Functional Auditing");
					
		

	}
	@Transactional(rollbackFor = Exception.class)
	public void updateAddlHolidaysRec(AddlHolidaysVO addlHolidayVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		logger.debug("inside update Academic Calendar method");
		AddlHolidays addlHoliday = new AddlHolidays();
		
		AddlHolidaysKey addlHolidayKey = new AddlHolidaysKey();
		// map the UIObject to Pojo
		commonBusiness.changeObject(addlHoliday, addlHolidayVO);
		commonBusiness.changeObject(addlHolidayKey, addlHolidayVO);
				
		addlHolidayKey.setInstId(userSessionDetails.getInstId());
		addlHolidayKey.setBranchId(userSessionDetails.getBranchId());	
		AddlHolidays selectedAddlHolidaysRec  = addlHolidaysDao.selectAddHolidaysRec(addlHolidayKey);
		String oldRecString = selectedAddlHolidaysRec.toStringForAuditAddlHolidaysRecord();
		addlHolidayKey.setDbTs(selectedAddlHolidaysRec.getDbTs());
		selectedAddlHolidaysRec.setrModId(userSessionDetails.getUserId());
		selectedAddlHolidaysRec.setAhRemarks(addlHoliday.getAhRemarks());	

		addlHolidaysDao.updateAddHolidaysRec(selectedAddlHolidaysRec, addlHolidayKey);

		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.ADDL_HOL_UPDATE, " ");
		logger.debug("Completed Functional Auditing");
		
		// Database audit
				
		AddlHolidays addlHolidaysForAudit = null;
		addlHolidayKey.setDbTs(0);
				try {
					addlHolidaysForAudit=addlHolidaysDao.selectAddHolidaysRec(addlHolidayKey);
				} catch (NoDataFoundException e) {
					logger.error("No data found  Exception occured in update academic calender:");
					e.printStackTrace();
				}						
				addlHolidayKey.setDbTs(addlHolidaysForAudit.getDbTs());			
				String newRecString = addlHolidaysForAudit.toStringForAuditAddlHolidaysRecord();
				doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
						TableNameConstant.ADDL_HOLIDAYS,
						addlHolidayKey.toStringForAuditAddlHolidaysKeyRecord(),
						oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
						"");

				logger.debug("Completed Database Auditing");
		
			}
	

}
