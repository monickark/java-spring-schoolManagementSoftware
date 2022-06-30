package com.jaw.student.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.core.dao.ITransportDetails;
import com.jaw.core.dao.TransportDetails;
import com.jaw.core.dao.TransportDetailsKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.student.admission.controller.CommunicationDetailsVO;
import com.jaw.student.admission.controller.ParentDetailsVO;
import com.jaw.student.admission.controller.PreSportParticipationDetailsVO;
import com.jaw.student.admission.controller.PrevAcademicDetailsVO;
import com.jaw.student.admission.controller.SiblingDetailsVO;
import com.jaw.student.admission.controller.StudentInfoVO;
import com.jaw.student.admission.controller.StudentMasterVO;
import com.jaw.student.admission.controller.TransportDetailsVO;
import com.jaw.student.admission.dao.CommunicationDetails;
import com.jaw.student.admission.dao.CommunicationDetailsKey;
import com.jaw.student.admission.dao.ICommunicationDetails;
import com.jaw.student.admission.dao.IParentDetails;
import com.jaw.student.admission.dao.IPreSportsPartDao;
import com.jaw.student.admission.dao.IPreSportspartListDao;
import com.jaw.student.admission.dao.IPrevAcademicDetails;
import com.jaw.student.admission.dao.ISiblingDetails;
import com.jaw.student.admission.dao.IStudentInfo;
import com.jaw.student.admission.dao.IStudentMasterDao;
import com.jaw.student.admission.dao.ParentDetails;
import com.jaw.student.admission.dao.ParentDetailsKey;
import com.jaw.student.admission.dao.PreSportParticipationDetails;
import com.jaw.student.admission.dao.PreSportsPartDetailsKey;
import com.jaw.student.admission.dao.PrevAcademicDetails;
import com.jaw.student.admission.dao.PrevAcademicDetailsKey;
import com.jaw.student.admission.dao.SiblingDetails;
import com.jaw.student.admission.dao.SiblingDetailsKey;
import com.jaw.student.admission.dao.StudentInfo;
import com.jaw.student.admission.dao.StudentInfoKey;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.admission.dao.StudentMasterKey;

@Component
public class ViewAdmisHelper {
	Logger logger = Logger.getLogger(ViewAdmisHelper.class);
	@Autowired
	private CommonBusiness commonBusiness;
	@Autowired
	private PropertyManagementUtil propertyManagementUtil;
	
	public void viewStudentDetails(AdmissionDetailsVO viewAdmis,
			IParentDetails parentDetailsDAO,
			IStudentMasterDao studentMasterDAO, IStudentInfo studentInfoDao,
			ISiblingDetails siblingDetailsDao,
			ICommunicationDetails communicationDetailsDao,
			IPrevAcademicDetails prevAcademicDetailsDao,
			ITransportDetails transportDetailsDao,IPreSportspartListDao preSportsPartDao,
			UserSessionDetails userSessionDetails, ApplicationCache applicationCache)
			throws PropertyNotFoundException, NoDataFoundException {
		StudentMasterVO studentMasterVO = new StudentMasterVO();
		StudentInfoVO studentInfoVO = new StudentInfoVO();
		ParentDetailsVO parentDetailsVO = new ParentDetailsVO();
		
		CommunicationDetailsVO communicationDetailsVO = new CommunicationDetailsVO();
		PrevAcademicDetailsVO prevAcademicDetailsVO = new PrevAcademicDetailsVO();
		TransportDetailsVO transportDetailsVO = new TransportDetailsVO();
		
		String studentAdmisNo = viewAdmis.getStudentAdmisNo();		
		
	//	 * Retrieving Admission Details from DB
		 //For Parent Details
		ParentDetailsKey parentDetailsKey = new ParentDetailsKey();
		parentDetailsKey.setBranchId(userSessionDetails.getBranchId());
		parentDetailsKey.setInstId(userSessionDetails.getInstId());
		parentDetailsKey.setStudentAdmisNo(studentAdmisNo);
		parentDetailsKey.setParentId(userSessionDetails.getLinkId());
		ParentDetails parentDetails = parentDetailsDAO
				.retriveParentDetails(parentDetailsKey);
		commonBusiness.changeObject(parentDetailsVO, parentDetails);
		viewAdmis.setParentDetailsVO(parentDetailsVO);
		
		//For Student Master
		StudentMasterKey studentMasterKey = new StudentMasterKey();
		studentMasterKey.setBranchId(userSessionDetails.getBranchId());
		studentMasterKey.setInstId(userSessionDetails.getInstId());
		studentMasterKey.setStudentAdmisNo(studentAdmisNo);
		studentMasterKey.setAcademicYear(viewAdmis.getAcademicYear());
		StudentMaster studentMaster = studentMasterDAO
				.retriveStudentDetails(studentMasterKey);
		commonBusiness.changeObject(studentMasterVO, studentMaster);
		commonBusiness.changeObject(viewAdmis, studentMaster);
		viewAdmis.setStudentMasterVO(studentMasterVO);
		
		//For Studnet Info
		StudentInfoKey studentInfoKey = new StudentInfoKey();
		studentInfoKey.setBranchId(userSessionDetails.getBranchId());
		studentInfoKey.setInstId(userSessionDetails.getInstId());
		studentInfoKey.setStudentAdmisNo(studentAdmisNo);
		StudentInfo studentInfo = studentInfoDao
				.retriveStudentInfo(studentInfoKey);
		commonBusiness.changeObject(studentInfoVO, studentInfo);
		viewAdmis.setStudentInfoVO(studentInfoVO);
		
		//For Sibling Details			
		 if (viewAdmis.getSiblingDetailsVO() != null) { 
			 SiblingDetailsKey siblingDetailsKey = new SiblingDetailsKey();
		  siblingDetailsKey.setBranchId(userSessionDetails.getBranchId());
		  siblingDetailsKey.setInstId(userSessionDetails.getInstId());
		  siblingDetailsKey.setStudentAdmisNo(studentAdmisNo);
		  List<SiblingDetails> siblingDetails =null;
		  try{
		  siblingDetails = siblingDetailsDao.retriveSiblingDetails(siblingDetailsKey);
		  }catch(NoDataFoundException e){
			  
		  }		  
		  if(siblingDetails!=null){
			  List<SiblingDetailsVO> siblingDetailsVOs = new ArrayList<SiblingDetailsVO>();
			  for (SiblingDetails details : siblingDetails) {
				  SiblingDetailsVO detailsVO = new SiblingDetailsVO();
				  commonBusiness.changeObject(detailsVO, details);
			  siblingDetailsVOs.add(detailsVO); 
			  }
			  viewAdmis.setSiblingDetailsVO(siblingDetailsVOs); 
		  }
		 
		  }
		 
		//For Communication Details
		CommunicationDetailsKey communicationDetailsKey = new CommunicationDetailsKey();
		communicationDetailsKey.setBranchId(userSessionDetails.getBranchId());
		communicationDetailsKey.setInstId(userSessionDetails.getInstId());
		communicationDetailsKey.setStudentAdmisNo(studentAdmisNo);
		CommunicationDetails communicationDetails = communicationDetailsDao
				.retriveCommunicationDetails(communicationDetailsKey);
		commonBusiness.changeObject(communicationDetailsVO,
				communicationDetails);
		viewAdmis.setCommunicationDetailsVO(communicationDetailsVO);
		
		
		//For Previous School Details
		PrevAcademicDetailsKey prevAcademicDetailsKey = new PrevAcademicDetailsKey();
		prevAcademicDetailsKey.setBranchId(userSessionDetails.getBranchId());
		prevAcademicDetailsKey.setInstId(userSessionDetails.getInstId());
		prevAcademicDetailsKey.setStudentAdmisNo(studentAdmisNo);
		PrevAcademicDetails prevAcademicDetails = prevAcademicDetailsDao
				.retrivePrevAcademicDetails(prevAcademicDetailsKey);
		commonBusiness.changeObject(prevAcademicDetailsVO, prevAcademicDetails);
		viewAdmis.setPrevAcademicDetailsVO(prevAcademicDetailsVO);
		
		//For PReSportsParticipation Details
		PreSportsPartDetailsKey preSportsPartdetailsKey = new PreSportsPartDetailsKey();
		preSportsPartdetailsKey.setBranchId(userSessionDetails.getBranchId());
		preSportsPartdetailsKey.setInstId(userSessionDetails.getInstId());
		preSportsPartdetailsKey.setStudentAdmisNo(studentAdmisNo);
		List<PreSportParticipationDetails> sportParticipationDetailsList = preSportsPartDao.retrivePreSportParticipationDetailsList(preSportsPartdetailsKey);
		List<PreSportParticipationDetailsVO> sportParticipationDetailsVOList = new ArrayList<PreSportParticipationDetailsVO>();
		for(PreSportParticipationDetails participationDetails:sportParticipationDetailsList){
			PreSportParticipationDetailsVO detailsVO = new PreSportParticipationDetailsVO();
			commonBusiness.changeObject(detailsVO, participationDetails);
			sportParticipationDetailsVOList.add(detailsVO);
		}
		viewAdmis.setPreSportPartDetails(sportParticipationDetailsVOList);
		
		//For Transport Details
		if (viewAdmis.getTransportDetailsVO() != null) {
			TransportDetailsKey transportDetailsKey = new TransportDetailsKey();
			transportDetailsKey.setBranchId(userSessionDetails.getBranchId());
			transportDetailsKey.setInstId(userSessionDetails.getInstId());
			transportDetailsKey.setStudentAdmisNo(studentAdmisNo);
			TransportDetails transportDetails = transportDetailsDao
					.retriveTransportDetails(transportDetailsKey);
			commonBusiness.changeObject(transportDetailsVO, transportDetails);
			viewAdmis.setTransportDetailsVO(transportDetailsVO);
		}
		
		logger.debug("VO to DB Pojo mapping is completed.");
		
	}
	
	public void studentMaster(AdmissionDetailsVO admissionDetailsVO,
			StudentMaster studentMaster) {
		StudentMasterVO studentMasterVO = admissionDetailsVO
				.getStudentMasterVO();
		try {
			BeanUtils.copyProperties(studentMaster, studentMasterVO);
			BeanUtils.copyProperties(studentMaster, admissionDetailsVO);
		}
		catch (IllegalAccessException illegalAccessException) {
			logger.error(illegalAccessException);
		}
		catch (InvocationTargetException invocationTargetException) {
			logger.error(invocationTargetException);
		}
	}
	
	public void studentInfo(AdmissionDetailsVO admissionDetailsVO,
			StudentInfo studentInfo) {
		StudentInfoVO studentInfoVO = admissionDetailsVO.getStudentInfoVO();
		try {
			BeanUtils.copyProperties(studentInfo, studentInfoVO);
			BeanUtils.copyProperties(studentInfo, admissionDetailsVO);
		}
		catch (IllegalAccessException illegalAccessException) {
			logger.error(illegalAccessException);
		}
		catch (InvocationTargetException invocationTargetException) {
			logger.error(invocationTargetException);
		}
	}
	
	public void parentDetails(AdmissionDetailsVO admissionDetailsVO,
			ParentDetails parentDetails) {
		ParentDetailsVO parentDetailsVO = admissionDetailsVO
				.getParentDetailsVO();
		try {
			BeanUtils.copyProperties(parentDetails, parentDetailsVO);
			BeanUtils.copyProperties(parentDetails, admissionDetailsVO);
		}
		catch (IllegalAccessException illegalAccessException) {
			logger.error(illegalAccessException);
		}
		catch (InvocationTargetException invocationTargetException) {
			logger.error(invocationTargetException);
		}
	}
	
	public void siblingDetails(AdmissionDetailsVO admissionDetailsVO,
			List<SiblingDetails> siblingDetails) {
		List<SiblingDetailsVO> siblingDetailsVOs = admissionDetailsVO
				.getSiblingDetailsVO();
		try {
			for(SiblingDetailsVO sibVO :admissionDetailsVO.getSiblingDetailsVO()){
				SiblingDetails siblingDetail = new SiblingDetails();
				BeanUtils.copyProperties(siblingDetail, sibVO);
				BeanUtils.copyProperties(siblingDetail, admissionDetailsVO);
				siblingDetails.add(siblingDetail);
			}		
			//BeanUtils.copyProperties(siblingDetails, siblingDetailsVOs);
		
		}
		catch (IllegalAccessException illegalAccessException) {
			logger.error(illegalAccessException);
		}
		catch (InvocationTargetException invocationTargetException) {
			logger.error(invocationTargetException);
		}
	}
	
	public void preSportsPartDetails(AdmissionDetailsVO admissionDetailsVO,
			List<PreSportParticipationDetails> preSportPartDetailsList,UserSessionDetails userDetails) {
		List<PreSportParticipationDetailsVO> preSportParticipationDetailsVOs = admissionDetailsVO.getPreSportPartDetails();					;
		try {
			for(PreSportParticipationDetailsVO detailsVO:preSportParticipationDetailsVOs){
				PreSportParticipationDetails preSportParticipationDetails = new PreSportParticipationDetails();
				BeanUtils.copyProperties(preSportParticipationDetails, detailsVO);
				BeanUtils.copyProperties(preSportParticipationDetails, admissionDetailsVO);
				preSportParticipationDetails.setInstId(userDetails.getInstId());
				preSportParticipationDetails.setBranchId(userDetails.getBranchId());
				preSportParticipationDetails.setrModId(userDetails.getUserId());
				preSportParticipationDetails.setDelFlg("N");
				preSportPartDetailsList.add(preSportParticipationDetails);
			}						
		}
		catch (IllegalAccessException illegalAccessException) {
			logger.error(illegalAccessException);
		}
		catch (InvocationTargetException invocationTargetException) {
			logger.error(invocationTargetException);
		}
	}
	
	public void communicationDetails(AdmissionDetailsVO admissionDetailsVO,
			CommunicationDetails communicationDetails) {
		CommunicationDetailsVO communicationDetailsVO = admissionDetailsVO
				.getCommunicationDetailsVO();
		try {
			BeanUtils.copyProperties(communicationDetails,
					communicationDetailsVO);
			BeanUtils.copyProperties(communicationDetails, admissionDetailsVO);
		}
		catch (IllegalAccessException illegalAccessException) {
			logger.error(illegalAccessException);
		}
		catch (InvocationTargetException invocationTargetException) {
			logger.error(invocationTargetException);
		}
		
	}
	
	public void prevAcademicDetails(AdmissionDetailsVO admissionDetailsVO,
			PrevAcademicDetails prevAcademicDetails) {
		PrevAcademicDetailsVO prevAcademicDetailsVO = admissionDetailsVO
				.getPrevAcademicDetailsVO();
		try {
			BeanUtils
					.copyProperties(prevAcademicDetails, prevAcademicDetailsVO);
			BeanUtils.copyProperties(prevAcademicDetails, admissionDetailsVO);			
		}
		catch (IllegalAccessException illegalAccessException) {
			logger.error(illegalAccessException);
		}
		catch (InvocationTargetException invocationTargetException) {
			logger.error(invocationTargetException);
		}
		
	}
	
	public void transportDetails(AdmissionDetailsVO admissionDetailsVO,
			TransportDetails transportDetails) {
		TransportDetailsVO transportDetailsVO = admissionDetailsVO
				.getTransportDetailsVO();
		try {
			BeanUtils.copyProperties(transportDetails, transportDetailsVO);
			BeanUtils.copyProperties(transportDetails, admissionDetailsVO);
		}
		catch (IllegalAccessException illegalAccessException) {
			logger.error(illegalAccessException);
		}
		catch (InvocationTargetException invocationTargetException) {
			logger.error(invocationTargetException);
		}
		
	}
	
}
