package com.jaw.communication.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.jaw.common.util.DateUtil;
import com.jaw.communication.controller.AlertMasterVO;
import com.jaw.communication.controller.AlertSearchVo;
import com.jaw.communication.controller.AlertVO;
import com.jaw.communication.controller.NoticeBoardVO;
import com.jaw.communication.dao.Alert;
import com.jaw.communication.dao.AlertKey;
import com.jaw.communication.dao.AlertListKey;
import com.jaw.communication.dao.IAlertDAO;
import com.jaw.communication.dao.IAlertListDAO;
import com.jaw.communication.dao.INoticeBoardDAO;
import com.jaw.communication.dao.INoticeBoardListDAO;
import com.jaw.communication.dao.NoticeBoard;
import com.jaw.communication.dao.NoticeBoardKey;
import com.jaw.communication.dao.NoticeBoardListKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
@Service
public class AlertService implements IAlertService {
	// Logging
	Logger logger = Logger.getLogger(AlertService.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	IAlertDAO alertDao;
	@Autowired
	IAlertListDAO alertListDao;	
	@Autowired
	DateUtil dateUtil;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertAlertDetailsRec(AlertMasterVO alertMasterVO,
			UserSessionDetails userSessionDetails) throws DatabaseException,
			DuplicateEntryException, NoDataFoundException,
			UpdateFailedException {
		logger.debug("inside insert Alert Service method");
		Alert alert = new Alert();
		String generalGrpList="";
		System.out.println("targ lengthhhhhhhhhh"+alertMasterVO.getAlertVO().getGeneralGrpListArray());
		if(alertMasterVO.getAlertVO().getGeneralGrpListArray()!=null){
			System.out.println("targ lengthhhhhhhhhh"+alertMasterVO.getAlertVO().getGeneralGrpListArray().length);
			for(int i=0;i<alertMasterVO.getAlertVO().getGeneralGrpListArray().length;i++){
				System.out.println("targ valueeeee"+alertMasterVO.getAlertVO().getGeneralGrpListArray()[i]);
				if(i==0){
					generalGrpList=alertMasterVO.getAlertVO().getGeneralGrpListArray()[i];
				}else{
					generalGrpList=generalGrpList+","+alertMasterVO.getAlertVO().getGeneralGrpListArray()[i];
				}
			}
		}
		alertMasterVO.getAlertVO().setGeneralGrpList(generalGrpList);
		String specificGrpList="";
		System.out.println("targ lengthhhhhhhhhh"+alertMasterVO.getAlertVO().getSpecificGrpListArray());
		if(alertMasterVO.getAlertVO().getSpecificGrpListArray()!=null){
			System.out.println("targ lengthhhhhhhhhh"+alertMasterVO.getAlertVO().getSpecificGrpListArray().length);
			for(int i=0;i<alertMasterVO.getAlertVO().getSpecificGrpListArray().length;i++){
				System.out.println("targ valueeeee"+alertMasterVO.getAlertVO().getSpecificGrpListArray()[i]);
				if(i==0){
					specificGrpList=alertMasterVO.getAlertVO().getSpecificGrpListArray()[i];
				}else{
					specificGrpList=specificGrpList+","+alertMasterVO.getAlertVO().getSpecificGrpListArray()[i];
				}
			}
		}
		alertMasterVO.getAlertVO().setSpecificGrpList(specificGrpList);
		// map the UIObject to Pojo
		commonBusiness.changeObject(alert, alertMasterVO.getAlertVO());
		alert.setDbTs(1);
		alert.setInstId(userSessionDetails.getInstId());
		alert.setBranchId(userSessionDetails.getBranchId());
		alert.setrCreId(userSessionDetails.getUserId());
		alert.setrModId(userSessionDetails.getUserId());
		alert.setDelFlag("N");
		alert.setAlertStop("N");
		
		alert.setAlertSerialNo(AlphaSequenceUtil.generateAlphaSequence(
				ApplicationConstant.STRING_ALERT_SEQ, simpleIdGenerator
				.getNextId(SequenceConstant.ALERT_SEQUENCE,
						1)));
		alertDao.insertAlertRec(alert);
			
			// functional audit
						doAudit.doFunctionalAudit(userSessionDetails,
								AuditConstant.ALERT_INSERT, " ");
						logger.debug("Completed Functional Auditing");
						if(alertMasterVO.getAlertSearchVo()==null){
							String acTerm=userSessionDetails.getCurrAcTerm();
							AlertSearchVo alertSearchVO=new AlertSearchVo();
							alertSearchVO.setAcTerm(acTerm);
							alertMasterVO.setAlertSearchVo(alertSearchVO);
						}

	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateAlertDetailsRec(AlertVO alertVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws UpdateFailedException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, NoDataFoundException {
		logger.debug("inside update Alert method");
		Alert alert = new Alert();
		AlertKey alertKey = new AlertKey();
		String generalGrpList="";
		System.out.println("targ lengthhhhhhhhhh"+alertVo.getGeneralGrpListArray());
		if(alertVo.getGeneralGrpListArray()!=null){
			System.out.println("targ lengthhhhhhhhhh"+alertVo.getGeneralGrpListArray().length);
			for(int i=0;i<alertVo.getGeneralGrpListArray().length;i++){
				System.out.println("targ valueeeee"+alertVo.getGeneralGrpListArray()[i]);
				if(i==0){
					generalGrpList=alertVo.getGeneralGrpListArray()[i];
				}else{
					generalGrpList=generalGrpList+","+alertVo.getGeneralGrpListArray()[i];
				}
			}
		}
		alertVo.setGeneralGrpList(generalGrpList);
		String specificGrpList="";
		System.out.println("targ lengthhhhhhhhhh"+alertVo.getSpecificGrpListArray());
		if(alertVo.getSpecificGrpListArray()!=null){
			System.out.println("targ lengthhhhhhhhhh"+alertVo.getSpecificGrpListArray().length);
			for(int i=0;i<alertVo.getSpecificGrpListArray().length;i++){
				System.out.println("targ valueeeee"+alertVo.getSpecificGrpListArray()[i]);
				if(i==0){
					specificGrpList=alertVo.getSpecificGrpListArray()[i];
				}else{
					specificGrpList=specificGrpList+","+alertVo.getSpecificGrpListArray()[i];
				}
			}
		}
		alertVo.setSpecificGrpList(specificGrpList);
		System.out.println("service alert vo  :"+alertVo.toString());
		// map the UIObject to Pojo
		commonBusiness.changeObject(alert, alertVo);
		System.out.println("service alert   :"+alert.toString());
		alert.setInstId(userSessionDetails.getInstId());
		alert.setBranchId(userSessionDetails.getBranchId());
		alertKey.setInstId(userSessionDetails.getInstId());
		alertKey.setBranchId(userSessionDetails.getBranchId());
		alertKey.setAlertSerialNo(alert.getAlertSerialNo());
		Alert alertNew=alertDao.selectAlertRec(alertKey);		
		Alert updateAlert=new Alert();
		commonBusiness.changeObject(updateAlert, alertNew);
		alertKey.setDbTs(updateAlert.getDbTs());
	
		System.out.println("update  alert  first :"+updateAlert.toString());
		
		updateAlert.setrModId(userSessionDetails.getUserId());
		
      
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        
        System.out.println("From Date   :"+dateUtil.getDateFormatByString(updateAlert.getFromDate()));
        System.out.println("To Date   :"+dateUtil.getDateFormatByString(updateAlert.getToDate()));
      
        
    	try {
    		 System.out.println("Current Date   :"+dateUtil.getCurrentDate());
    		  System.out.println("Current Date   :"+ft.parse(dateUtil.getCurrentDate()));
			if ((dateUtil.getDateFormatByString(updateAlert.getFromDate())
					.compareTo(ft.parse(dateUtil.getCurrentDate())) > 0)
					|| (dateUtil.getDateFormatByString(
							updateAlert.getFromDate()).compareTo(
									ft.parse(dateUtil.getCurrentDate())) == 0)) {	
				System.out.println("from dateeeeeeeee");
				updateAlert.setFromDate(alert.getFromDate());
			}			
		if ((dateUtil.getDateFormatByString(updateAlert.getToDate())
				.compareTo(ft.parse(dateUtil.getCurrentDate()))> 0)
				|| (dateUtil.getDateFormatByString(updateAlert.getToDate())
						.compareTo(ft.parse(dateUtil.getCurrentDate())) == 0)) {
			System.out.println("to date eeeeeeeeeeee"+updateAlert.getToDate());
			 updateAlert.setReqstCategory(alert.getReqstCategory());
			 updateAlert.setGeneralGrpList(alert.getGeneralGrpList());
			 updateAlert.setSpecificGrpList(alert.getSpecificGrpList());
		        updateAlert.setAlertMessage(alert.getAlertMessage());
		        updateAlert.setImportant(alert.getImportant());
			updateAlert.setToDate(alert.getToDate());
		}
		
		
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    	System.out.println("update  alert  Second :"+updateAlert.toString());
		alertDao.updateAlertRec(updateAlert, alertKey);
		
	
		
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.NOTICE_BOARD_UPDATE, " ");
		logger.debug("Completed Functional Auditing");

		// Database audit
		String oldRecString = alertNew.toStringForAuditAlertRecord();
		Alert alertForAudit = null;
		alertKey.setDbTs(0);
		try {
			alertForAudit = alertDao.selectAlertRec(alertKey);		
		} catch (NoDataFoundException e) {
			logger.error("No data found  Exception occured in update student group master:");
			e.printStackTrace();
		}
		alertKey.setDbTs(alertForAudit.getDbTs());
		String newRecString = alertForAudit.toStringForAuditAlertRecord();
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.GEN_ALERT,
				alertKey.toStringForAuditAlertKey(),
				oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
				"");

		logger.debug("Completed Database Auditing");

	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteAlertDetailsRec(AlertVO alertVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		AlertKey alertKey = new AlertKey();
		commonBusiness.changeObject(alertKey, alertVo);

		
		alertKey.setInstId(userSessionDetails.getInstId());
		alertKey.setBranchId(userSessionDetails.getBranchId());

		Alert alertNew=alertDao.selectAlertRec(alertKey);
		Alert deleteAlert=new Alert();
		commonBusiness.changeObject(deleteAlert, alertNew);
		alertKey.setDbTs(alertNew.getDbTs());
		deleteAlert.setrModId(userSessionDetails.getUserId());
		deleteAlert.setDbTs(alertNew.getDbTs()+1);
		deleteAlert.setDelFlag("Y");
		
		alertDao.deleteAlertRec(deleteAlert, alertKey);		
			// functional audit
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.ALERT_DELETE, " ");
			logger.debug("Completed Functional Auditing");
			
			
			// Database audit
			
			String oldRecString = alertNew.toStringForAuditAlertRecord();
			
			doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
					TableNameConstant.GEN_ALERT,
					alertKey.toStringForAuditAlertKey(),
					oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, "",
					"");

			logger.debug("Completed Database Auditing");
		

	}
	@Override
	public void selectAlertList(AlertMasterVO alertMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		//String profileGroup=userSessionDetails.getProfileGroup();
		if((alertMasterVO.getAlertSearchVo().getGeneralGrpListRadio()!=null)){
			String[] myArray = new String[]{alertMasterVO.getAlertSearchVo().getGeneralGrpListRadio()};
			alertMasterVO.getAlertSearchVo().setGeneralGrpList(myArray);
		}
		
		
		AlertListKey alertListKey = new AlertListKey();
		
		if(alertMasterVO.getAlertSearchVo()!=null){
		commonBusiness.changeObject(alertListKey,alertMasterVO.getAlertSearchVo());
		}
		System.out.println("search vosssssssssss"+alertListKey.toString());
		alertListKey.setInstId(userSessionDetails.getInstId());
		alertListKey.setBranchId(userSessionDetails.getBranchId());
		List<Alert> alertList = alertListDao.getAlertList(alertListKey);
		List<AlertVO> alertVOs = new ArrayList<AlertVO>();

		for (int i = 0; i < alertList.size(); i++) {
			Alert alert = alertList.get(i);
			
			
			
			AlertVO alertVO = new AlertVO();
			commonBusiness.changeObject(alertVO, alert);			
			if(alert.getGeneralGrpList()!=null){
				alertVO.setGeneralGrpListArray(alert.getGeneralGrpList().split(","));
			}
			if(alert.getSpecificGrpList()!=null){
				alertVO.setSpecificGrpListArray(alert.getSpecificGrpList().split(","));
			}		
			String[] genA=alert.getGeneralGrpList().split(",");
			
			for(int j=0;j<genA.length;j++){
				System.out.println("genral list  : "+genA[j]);
			}
			System.out.println("alert vo in list : "+alertVO.toString());
			alertVO.setRowId(i);
			alertVOs.add(alertVO);
		}
		alertMasterVO.setAlertVOList(alertVOs);
		
	}
	@Override
	public List<AlertVO> selectAlertListForDashBoard(AlertListKey alertListKey,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		String profileGroup=userSessionDetails.getProfileGroup();
		
		List<String> targetArrayList = new ArrayList<String>();
		if(userSessionDetails.getProfileGroup().equals(ApplicationConstant.PG_STUDENT)){
			targetArrayList.add("STU");
		}else if(userSessionDetails.getProfileGroup().equals(ApplicationConstant.PG_PARENT)){
			targetArrayList.add("PAR");
		}else if(userSessionDetails.getProfileGroup().equals(ApplicationConstant.PG_STAFF)){
			targetArrayList.add("STA");
		}else /*if(userSessionDetails.getProfileGroup().equals(ApplicationConstant.PG_MGMT))*/{
			targetArrayList.add("MGT");
		}
	
		
		String[] myArray = new String[targetArrayList.size()];
		targetArrayList.toArray(myArray);
		alertListKey.setGeneralGrpList(myArray);
		List<Alert> alertList = alertListDao.getAlertListForDashBoard(alertListKey, profileGroup);
		List<AlertVO> alertVOs = new ArrayList<AlertVO>();


		for (int i = 0; i < alertList.size(); i++) {
			Alert alert = alertList.get(i);
			
		
			AlertVO alertVO = new AlertVO();
			commonBusiness.changeObject(alertVO, alert);
			//alertVO.setTargetGroupArray(targetArray);
			alertVO.setRowId(i);
			alertVOs.add(alertVO);
		}
		
		return alertVOs;
		
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void stopAlertDetailsRec(AlertVO alertVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws UpdateFailedException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, NoDataFoundException {

		AlertKey alertKey = new AlertKey();
		commonBusiness.changeObject(alertKey, alertVo);

		
		alertKey.setInstId(userSessionDetails.getInstId());
		alertKey.setBranchId(userSessionDetails.getBranchId());

		Alert alertNew=alertDao.selectAlertRec(alertKey);
		Alert stopAlert=new Alert();
		commonBusiness.changeObject(stopAlert, alertNew);
		alertKey.setDbTs(alertNew.getDbTs());
		stopAlert.setrModId(userSessionDetails.getUserId());
		stopAlert.setDbTs(alertNew.getDbTs()+1);
		stopAlert.setAlertStop("Y");
		
		alertDao.stopAlertRec(stopAlert, alertKey);		
			// functional audit
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.ALERT_UPDATE, " ");
			logger.debug("Completed Functional Auditing");
			
			
			// Database audit
			
			String oldRecString = alertNew.toStringForAuditAlertRecord();
			Alert alertForAudit = null;
			alertKey.setDbTs(0);
			try {
				alertForAudit = alertDao.selectAlertRec(alertKey);		
			} catch (NoDataFoundException e) {
				logger.error("No data found  Exception occured in update student group master:");
				e.printStackTrace();
			}
			alertKey.setDbTs(alertForAudit.getDbTs());
			String newRecString = alertForAudit.toStringForAuditAlertRecord();
			doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
					TableNameConstant.GEN_ALERT,
					alertKey.toStringForAuditAlertKey(),
					oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
					"");

			logger.debug("Completed Database Auditing");
		

	}

}
