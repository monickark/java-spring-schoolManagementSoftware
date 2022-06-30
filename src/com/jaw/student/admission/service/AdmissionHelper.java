package com.jaw.student.admission.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InsertFailedException;
import com.jaw.common.exceptions.UnableCreateParentPassword;
import com.jaw.common.exceptions.UnableCreateParentUserId;
import com.jaw.common.exceptions.UnableCreateStudentPassword;
import com.jaw.common.exceptions.UnableCreateStudentUserId;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.core.dao.IStudentGroupMasterDAO;
import com.jaw.core.dao.ITransportDetails;
import com.jaw.core.dao.TransportDetails;
import com.jaw.custom.student.UserIdAndPassWordGen;
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
import com.jaw.student.admission.dao.ICommunicationDetails;
import com.jaw.student.admission.dao.IParentDetails;
import com.jaw.student.admission.dao.IPreSportspartListDao;
import com.jaw.student.admission.dao.IPrevAcademicDetails;
import com.jaw.student.admission.dao.ISiblingDetails;
import com.jaw.student.admission.dao.IStudentInfo;
import com.jaw.student.admission.dao.IStudentMasterDao;
import com.jaw.student.admission.dao.ParentDetails;
import com.jaw.student.admission.dao.PreSportParticipationDetails;
import com.jaw.student.admission.dao.PrevAcademicDetails;
import com.jaw.student.admission.dao.SiblingDetails;
import com.jaw.student.admission.dao.StudentInfo;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.user.controller.UserCreationVO;
import com.jaw.user.util.UserCreation;

@Component
public class AdmissionHelper {
	
	Logger logger = Logger.getLogger(AdmissionHelper.class);
	
	@Autowired
	private CommonBusiness commonBusiness;
	@Autowired
	UserCreation userCreation;
	@Autowired
	IStudentGroupMasterDAO studentGroupMasterDAO;
	@Autowired
	UserIdAndPassWordGen userIdAndPassWordGen;
	
	private UserSessionDetails userSessionDetails;
	private AdmissionDetailsVO admissionDetailsVO;
	private ApplicationCache applicationCache;
	private String studentName;
	
	public UserSessionDetails getUserSessionDetails() {
		return userSessionDetails;
	}
	
	public void setUserSessionDetails(UserSessionDetails userSessionDetails) {
		this.userSessionDetails = userSessionDetails;
	}
	
	public AdmissionDetailsVO getAdmissionDetailsVO() {
		return admissionDetailsVO;
	}
	
	public void setAdmissionDetailsVO(AdmissionDetailsVO admissionDetailsVO) {
		this.admissionDetailsVO = admissionDetailsVO;
	}
	
	public ApplicationCache getApplicationCache() {
		return applicationCache;
	}
	
	public void setApplicationCache(ApplicationCache applicationCache) {
		this.applicationCache = applicationCache;
	}
	
	public void saveParentDetails(IParentDetails parentDetailsDao/*,String parentId*/)
			throws DuplicateEntryException, NumberFormatException,
			PropertyNotFoundException {
		ParentDetailsVO parentDetailsVO = admissionDetailsVO
				.getParentDetailsVO();
		
		ParentDetails parentDetails = new ParentDetails();		
		
		if(parentDetailsVO.getParentId().equals("")){	
			parentDetailsVO.setParentId(admissionDetailsVO.getStudentAdmisNo());
		}	
		commonBusiness.changeObject(parentDetails, parentDetailsVO);
		
		parentDetails.setStudentAdmisNo(admissionDetailsVO.getStudentAdmisNo());		
		parentDetails.setInstId(userSessionDetails.getInstId());
		parentDetails.setBranchId(userSessionDetails.getBranchId());
		parentDetails.setrCreId(userSessionDetails.getUserId());
		parentDetails.setrModId(userSessionDetails.getUserId());
		parentDetailsDao.insertParentDetails(parentDetails);
	}
	
	public void saveStudentInfo(IStudentInfo studentInfoDao)
			throws DuplicateEntryException, InsertFailedException {
		StudentInfoVO studentInfoVO = admissionDetailsVO.getStudentInfoVO();
		StudentInfo studentInfo = new StudentInfo();
		studentName = studentInfoVO.getFirstName() + ""
				+ studentInfoVO.getMiddleName() + ""
				+ studentInfoVO.getLastName();
		commonBusiness.changeObject(studentInfo, studentInfoVO);
		studentInfo.setStudentAdmisNo(admissionDetailsVO.getStudentAdmisNo());
		studentInfo.setAcademicYear(admissionDetailsVO.getAcademicYear());
		studentInfo.setInstId(userSessionDetails.getInstId());
		studentInfo.setBranchId(userSessionDetails.getBranchId());
		studentInfo.setrCreId(userSessionDetails.getUserId());
		studentInfo.setrModId(userSessionDetails.getUserId());
		studentInfoDao.insertStudentInfo(studentInfo);
	}
	
	public void saveStudentMaster(IStudentMasterDao studentMasterDao) throws DuplicateEntryException, DatabaseException,
			NumberFormatException, PropertyNotFoundException {
		StudentMasterVO studentMasterVO = admissionDetailsVO
				.getStudentMasterVO();
		StudentMaster studentMaster = new StudentMaster();
		commonBusiness.changeObject(studentMaster, studentMasterVO);
		studentMaster.setStudentAdmisNo(admissionDetailsVO.getStudentAdmisNo());
		studentMaster.setAcademicYear(admissionDetailsVO.getAcademicYear());
		studentMaster.setStudentName(studentName);
		studentMaster.setInstId(userSessionDetails.getInstId());
		studentMaster.setBranchId(userSessionDetails.getBranchId());
		studentMaster.setrCreId(userSessionDetails.getUserId());
		studentMaster.setrModId(userSessionDetails.getUserId());
		if(!admissionDetailsVO.getStudentMasterVO().getSec().equals("")){
			String stuGrp = studentGroupMasterDAO.getStuGrpIdForSTUM(userSessionDetails.getInstId(), userSessionDetails.getBranchId(), 
					admissionDetailsVO.getStudentMasterVO().getCourse(), admissionDetailsVO.getStudentMasterVO().getStandard(),  admissionDetailsVO.getStudentMasterVO().getSec());		
			admissionDetailsVO.getStudentMasterVO().setStuGrpId(stuGrp);
			studentMaster.setStuGrpId(admissionDetailsVO.getStudentMasterVO().getStuGrpId());
		}	
		//studentMaster.setGender(admissionDetailsVO.getStudentInfoVO().getGender());
		studentMasterDao.insertStudentMaster(studentMaster);
		
		
		
	}
		public void savePreSportsPartDetails(IPreSportspartListDao preSportsPartDao) throws DuplicateEntryException, DatabaseException,
		NumberFormatException, PropertyNotFoundException {
			List<PreSportParticipationDetailsVO> preSportParticipationDetailsVO = admissionDetailsVO.getPreSportPartDetails();
			List<PreSportParticipationDetails> preSportParticipationDetailsList =new ArrayList<PreSportParticipationDetails>(); 
			for(PreSportParticipationDetailsVO preSportsPartDetail:preSportParticipationDetailsVO){
				PreSportParticipationDetails preSportParticipationDetails = new PreSportParticipationDetails();
				if(!preSportsPartDetail.getSportsLevel().equals("")){
					commonBusiness.changeObject(preSportParticipationDetails, preSportsPartDetail);
					preSportParticipationDetails.setInstId(userSessionDetails.getInstId());
					preSportParticipationDetails.setBranchId(userSessionDetails.getBranchId());
					preSportParticipationDetails.setDelFlg("N");
					preSportParticipationDetails.setStudentAdmisNo(admissionDetailsVO.getStudentAdmisNo());
					preSportParticipationDetailsList.add(preSportParticipationDetails);
				}
				
			}
			if (preSportParticipationDetailsVO != null && preSportParticipationDetailsVO.size() > 0) {				
				preSportsPartDao.insertPreSportspartList(preSportParticipationDetailsList);
			}			


}
	
	public void saveSiblingDetails(ISiblingDetails siblingDetailsDao)
			throws DuplicateEntryException {
		List<SiblingDetails> siblingDetails = convertSibDetailsList();	
		if (siblingDetails != null && siblingDetails.size() > 0) {
			siblingDetailsDao.insertSiblingDetails(siblingDetails);
		}
	}
	
	public void saveCommDetails(ICommunicationDetails communicationDetailsDao)
			throws DuplicateEntryException {
		CommunicationDetailsVO communicationDetailsVO = admissionDetailsVO
				.getCommunicationDetailsVO();	
		CommunicationDetails communicationDetails = new CommunicationDetails();
		commonBusiness.changeObject(communicationDetails,
				communicationDetailsVO);
		communicationDetails.setStudentAdmisNo(admissionDetailsVO
				.getStudentAdmisNo());
		communicationDetails.setInstId(userSessionDetails.getInstId());
		communicationDetails.setBranchId(userSessionDetails.getBranchId());
		communicationDetails.setrCreId(userSessionDetails.getUserId());
		communicationDetails.setrModId(userSessionDetails.getUserId());
		communicationDetailsDao.insertCommunication(communicationDetails);
	}
	
	public void savePrevAcadDetails(
			
			IPrevAcademicDetails prevAcademicDetailsDao) throws DuplicateEntryException {
		PrevAcademicDetailsVO prevAcademicDetailsVO = admissionDetailsVO
				.getPrevAcademicDetailsVO();
		PrevAcademicDetails prevAcademicDetails = new PrevAcademicDetails();
		commonBusiness.changeObject(prevAcademicDetails, prevAcademicDetailsVO);
		prevAcademicDetails.setStudentAdmisNo(admissionDetailsVO
				.getStudentAdmisNo());
		prevAcademicDetails.setInstId(userSessionDetails.getInstId());
		prevAcademicDetails.setBranchId(userSessionDetails.getBranchId());
		prevAcademicDetails.setrCreId(userSessionDetails.getUserId());
		prevAcademicDetails.setrModId(userSessionDetails.getUserId());
		prevAcademicDetailsDao.insertPrevAcademicDetaisl(prevAcademicDetails);
	}
	
	public void saveTransportDetails(ITransportDetails transportDetailsDao)
			throws DuplicateEntryException {
		TransportDetailsVO transportDetailsVO = admissionDetailsVO
				.getTransportDetailsVO();
		TransportDetails transportDetails = new TransportDetails();
		commonBusiness.changeObject(transportDetails, transportDetailsVO);
		transportDetails.setStudentAdmisNo(admissionDetailsVO
				.getStudentAdmisNo());
		transportDetails.setAcademicYear(admissionDetailsVO.getAcademicYear());
		transportDetails.setInstId(userSessionDetails.getInstId());
		transportDetails.setBranchId(userSessionDetails.getBranchId());
		transportDetails.setrCreId(userSessionDetails.getUserId());
		transportDetails.setrModId(userSessionDetails.getUserId());
		transportDetailsDao.insertTransportsDetails(transportDetails);
	}
	
	public void createStudentUser(AdmissionDetailsVO admissionDetailsVO) throws DuplicateEntryException,
			NumberFormatException, PropertyNotFoundException, UnableCreateStudentUserId, UnableCreateStudentPassword {			
	
			UserCreationVO userCreationVO = new UserCreationVO();
			userCreationVO.setApplicationCache(applicationCache);
			userCreationVO.setLinkId(admissionDetailsVO.getStudentAdmisNo());
			userCreationVO.setMenuProfile(ApplicationConstant.STUDENT);
			
			//String studentUserId = userCreation.generateUserId(studentName);
			String studentUserId = userIdAndPassWordGen.getStudentUserId(admissionDetailsVO.getStudentAdmisNo());		
			logger.info("Student User Id :"+studentUserId+","+"for user name :"+studentName);
			userCreationVO.setUserId(studentUserId);
			logger.info("Going to get the Studnet Password");
			//String studentPassword = userCreation.generateUserId(studentName);
			String studentPassword =userIdAndPassWordGen.getStudentPassword(admissionDetailsVO.getStudentInfoVO().getDob());
			logger.info("Student Password :"+studentPassword+","+"for user name :"+studentName);
			userCreationVO.setPassword(studentPassword);
			userCreationVO.setProfileGroup(ApplicationConstant.PG_STUDENT);
			userCreationVO.setRole("");			
			userCreationVO.setUserSessionDetails(userSessionDetails);
			admissionDetailsVO.getUserDetailsForSMS().setStudentUserId(studentUserId);
			admissionDetailsVO.getUserDetailsForSMS().setStudentPassword(studentPassword);
			userCreation.createUser(userCreationVO);
			
		
	}
	
	public void createParentUser(AdmissionDetailsVO admissionDetailsVO)
			throws DuplicateEntryException, NumberFormatException,
			PropertyNotFoundException, UnableCreateParentUserId, UnableCreateParentPassword {		
				
				UserCreationVO userCreationVO = new UserCreationVO();
				userCreationVO.setApplicationCache(applicationCache);
				userCreationVO.setLinkId(admissionDetailsVO.getParentDetailsVO().getParentId());
				userCreationVO.setMenuProfile(ApplicationConstant.PARENT);
				String parentUserId =userIdAndPassWordGen.getParentUserId(admissionDetailsVO.getStudentAdmisNo());
			//	String parentUserId = userCreation.generateUserId(admissionDetailsVO.getParentDetailsVO().getParentId());
				logger.info("Parent User Id :"+parentUserId+","+"for user name :"+admissionDetailsVO.getParentDetailsVO().getParentId());
				userCreationVO.setUserId(parentUserId);
				logger.info("Going to get the Parent Password");
				String parentPassword = userIdAndPassWordGen.getParentPassword(admissionDetailsVO.getStudentInfoVO().getDob());
				//String parentPassword = userCreation.generateUserId(admissionDetailsVO.getParentDetailsVO().getParentId());
				logger.info("Parent Password :"+parentPassword+","+"for user name :"+admissionDetailsVO.getParentDetailsVO().getParentId());
				userCreationVO.setPassword(parentPassword);
				userCreationVO.setProfileGroup(ApplicationConstant.PG_PARENT);
				userCreationVO.setRole("");				
				userCreationVO.setUserSessionDetails(userSessionDetails);
				admissionDetailsVO.getUserDetailsForSMS().setParentUserId(parentUserId);
				admissionDetailsVO.getUserDetailsForSMS().setParentPassword(parentPassword);
				
				userCreation.createUser(userCreationVO);		
		
	}
	
	List<SiblingDetails> convertSibDetailsList() {
		
		List<SiblingDetailsVO> siblingDetailsVOs = admissionDetailsVO
				.getSiblingDetailsVO();
		List<SiblingDetails> siblingDetails = new ArrayList<SiblingDetails>();
		int i = 1;
		for (SiblingDetailsVO sibDetailsVO : siblingDetailsVOs) {
			SiblingDetails sibDetails = new SiblingDetails();
			if (sibDetailsVO.getSiblingName() == null
					|| sibDetailsVO.getSiblingName().trim().equals("")) {
				continue;
			}
			commonBusiness.changeObject(sibDetails, sibDetailsVO);
			sibDetails
					.setStudentAdmisNo(admissionDetailsVO.getStudentAdmisNo());
			sibDetails.setInstId(userSessionDetails.getInstId());
			sibDetails.setBranchId(userSessionDetails.getBranchId());
			sibDetails.setrCreId(userSessionDetails.getUserId());
			sibDetails.setrModId(userSessionDetails.getUserId());			
			sibDetails.setSiblingNo(String.valueOf(i));
			siblingDetails.add(sibDetails);
			i = i + 1;
		}		
		return siblingDetails;
	}
	
	/*public String getCurrentJavaSqlDate(int days) {
		DateFormat stringDate = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date today = new java.util.Date();
		return stringDate.format(new java.sql.Date(today.getTime() + days));
		
	}
	*/
}
