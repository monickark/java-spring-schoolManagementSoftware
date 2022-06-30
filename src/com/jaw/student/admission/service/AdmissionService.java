package com.jaw.student.admission.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.dropdown.dao.IAllSubListByCIDAndTRM;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.InsertFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UnableCreateParentPassword;
import com.jaw.common.exceptions.UnableCreateParentUserId;
import com.jaw.common.exceptions.UnableCreateStudentPassword;
import com.jaw.common.exceptions.UnableCreateStudentUserId;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.dao.IFileMasterDao;
import com.jaw.common.files.service.FileMasterHelper;
import com.jaw.common.files.service.FileSaveHelper;
import com.jaw.common.sms.SendAlertSMS;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.common.util.SMSPropertyManagementUtil;
import com.jaw.communication.dao.SMSAlert;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.core.dao.CourseSubLinkKey;
import com.jaw.core.dao.ICourseMasterDAO;
import com.jaw.core.dao.ICourseSubLinkListDAO;
import com.jaw.core.dao.IStudentGroupMasterDAO;
import com.jaw.core.dao.ITransportDetails;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.dao.IAuditDao;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.student.admission.controller.StudentMasterVO;
import com.jaw.student.admission.dao.ICommunicationDetails;
import com.jaw.student.admission.dao.IParentDetails;
import com.jaw.student.admission.dao.IPreSportsPartDao;
import com.jaw.student.admission.dao.IPreSportspartListDao;
import com.jaw.student.admission.dao.IPrevAcademicDetails;
import com.jaw.student.admission.dao.ISiblingDetails;
import com.jaw.student.admission.dao.IStudentInfo;
import com.jaw.student.admission.dao.IStudentMasterDao;
import com.jaw.student.admission.dao.ParentDetails;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.admission.dao.StudentMasterKey;
import com.jaw.user.dao.IUserDao;
import com.jaw.user.dao.IUserLinkDao;
import com.jaw.user.dao.UserLink;

@Service
public class AdmissionService implements IAdmissionService {

	Logger logger = Logger.getLogger(AdmissionService.class);

	@Autowired
	private IStudentMasterDao studentMasterDao;
	@Autowired
	private IStudentInfo studentInfoDao;
	@Autowired
	private IParentDetails parentDetailsDao;
	@Autowired
	private ISiblingDetails siblingDetailsDao;
	@Autowired
	private ICommunicationDetails communicationDetailsDao;
	@Autowired
	private IPrevAcademicDetails prevAcademicDetailsDao;
	@Autowired
	private ITransportDetails transportDetailsDao;
	@Autowired
	private IFileMasterDao fileMasterDao;
	@Autowired
	private AdmissionHelper admissionHelper;
	@Autowired
	private IAuditDao auditDAO;
	@Autowired
	private IUserDao userDAO;
	@Autowired
	private CommonBusiness commonBusiness;
	@Autowired
	private IIdGeneratorService simpleIdGenerator;
	@Autowired
	FileMasterHelper fileMaserHelper;
	@Autowired
	IUserLinkDao userLinkDao;
	@Autowired
	IPreSportspartListDao preSportsPartDao;
	@Autowired
	IAllSubListByCIDAndTRM allSubListDao;
	@Autowired
	ICourseMasterDAO courseDao;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	SMSPropertyManagementUtil smsPropertyManagementUtil;
	// Helper class to do Auditing
	@Autowired
	DoAudit doAudit;
	@Autowired
	IStudentGroupMasterDAO stuGrpMasDao;
	@Autowired
	SendAlertSMS sendAlertSMS;

	@Override
	public String getNextAdmisNo(AdmissionDetailsVO admissionDetailsVO, UserSessionDetails userSessionDetails) {
admissionDetailsVO.setInstId(userSessionDetails.getInstId());
admissionDetailsVO.setBranchId(userSessionDetails.getBranchId());
		return studentMasterDao.getNextAdmisNo(admissionDetailsVO);

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void newAdmission(ApplicationCache applicationCache,
			AdmissionDetailsVO admissionDetailsVO,
			UserSessionDetails userSessionDetails, ServletContext servletContext)
			throws IOException, DuplicateEntryException, NumberFormatException,
			PropertyNotFoundException, DatabaseException, FileNotSaveException,
			InsertFailedException, UnableCreateParentUserId,
			UnableCreateParentPassword, UnableCreateStudentUserId,
			UnableCreateStudentPassword {
		// String parentId = null;

		/*
		 * Order of Admission 1.Need to insert into "parentsdetails" table
		 * 2.Need to insert into "studentinformation" table 3.Need to insert
		 * into "studentmaster" table. 4.Need to insert into
		 * "Communicationdetails" table 5.Need to insert into "siblingdetails"
		 * table 6.Need to insert into "previousschooldetails" table 7.Need to
		 * insert into "transportdetails" table 8.Need to insert into
		 * "filemaster" table 9.Need to insert into "user" table 10. Need to
		 * insert into "useraudit" table
		 */
		admissionHelper.setAdmissionDetailsVO(admissionDetailsVO);
		admissionHelper.setUserSessionDetails(userSessionDetails);
		admissionHelper.setApplicationCache(applicationCache);

		// admissionHelper.saveParentDetails(parentDetailsDao,parentId);
		admissionHelper.saveParentDetails(parentDetailsDao);
		admissionHelper.saveStudentInfo(studentInfoDao);
		admissionHelper.saveStudentMaster(studentMasterDao);
		admissionHelper.saveCommDetails(communicationDetailsDao);
		admissionHelper.savePrevAcadDetails(prevAcademicDetailsDao);
		admissionHelper.savePreSportsPartDetails(preSportsPartDao);

		if (admissionDetailsVO.getSiblingDetailsVO() != null) {
			admissionHelper.saveSiblingDetails(siblingDetailsDao);
		}

		fileMaserHelper.fileUpload(applicationCache, admissionDetailsVO,
				userSessionDetails, ApplicationConstant.PG_STUDENT,
				servletContext);

		if ((admissionDetailsVO.getUserAcces().getCreateStu() != null)
				&& (admissionDetailsVO.getUserAcces().getCreateStu().trim()
						.equals(ApplicationConstant.CREATE_STUDENT_USR))) {
			admissionHelper.createStudentUser(admissionDetailsVO);
		}

		if ((admissionDetailsVO.getParentDetailsVO().getParentType() != null)
				&& (admissionDetailsVO.getParentDetailsVO().getParentType()
						.trim().equals(ApplicationConstant.PARENT_USR_NEW))) {
			admissionHelper.createParentUser(admissionDetailsVO);
		}

		String newAdmisAudit = "INST_ID :" + userSessionDetails.getInstId()
				+ ",BRANCH_ID :" + userSessionDetails.getBranchId()
				+ ",ACADEMIC_YEAR :" + admissionDetailsVO.getAcademicYear()
				+ ",STUDENT_ADMIS_NO :"
				+ admissionDetailsVO.getStudentAdmisNo() + ",TRM :"
				+ admissionDetailsVO.getStudentMasterVO().getStandard();
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.NEW_ADMISSION_SUCCESS, newAdmisAudit);

		logger.debug("new Admission details are inserted to DB ");
		logger.debug("new line   new Admission details are inserted to DB ");
		System.out.println("user detailsss  :  for check");
		System.out.println("user detailsss  :  "
				+ admissionDetailsVO.getUserDetailsForSMS().toString());

		// Send SMS
		if (((admissionDetailsVO.getUserAcces().getCreateStu() != null) && (admissionDetailsVO
				.getUserAcces().getCreateStu().trim()
				.equals(ApplicationConstant.CREATE_STUDENT_USR)))
				|| ((admissionDetailsVO.getParentDetailsVO().getParentType() != null) && (admissionDetailsVO
						.getParentDetailsVO().getParentType().trim()
						.equals(ApplicationConstant.PARENT_USR_NEW)))) {
			// Call Send SMS method
			// sendUserDetailsSMS(applicationCache,admissionDetailsVO,userSessionDetails);
		}
	}

	// Method to send login id sms to parent
	void sendUserDetailsSMS(ApplicationCache applicationCache,
			AdmissionDetailsVO admissionDetailsVO,
			UserSessionDetails userSessionDetails)
			throws PropertyNotFoundException, DuplicateEntryException {

		// Send SMS
		String sendSMS = smsPropertyManagementUtil.getPropertyValue(
				applicationCache, userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(),
				PropertyManagementConstant.ADMISSION_SEND_SMS_RQRD).toString();
		System.out.println("send sms" + sendSMS);

		if ((!sendSMS.equals(""))
				&& (sendSMS.equals(ApplicationConstant.ADMISSION_SMS_REQUIRED))) {
			String message = smsPropertyManagementUtil.getPropertyValue(
					applicationCache, userSessionDetails.getInstId(),
					userSessionDetails.getBranchId(),
					PropertyManagementConstant.ADMISSION_SMS_MSG).toString();
			System.out.println("send message" + message);
			SMSAlert smsAlert = new SMSAlert();
			smsAlert.setInstId(userSessionDetails.getInstId());
			smsAlert.setBranchId(userSessionDetails.getBranchId());
			smsAlert.setAcTerm(admissionDetailsVO.getAcademicYear());
			smsAlert.setrModId(userSessionDetails.getUserId());
			smsAlert.setrCreId(userSessionDetails.getUserId());
			// smsAlert.setSmsMessage(message +
			// " "+studentAttMaster.getAttDate());
			String[] messageFormat = message
					.split(ApplicationConstant.COMMA_SEPERATOR);
			String messageToSend = "";
			if (!admissionDetailsVO.getUserDetailsForSMS().getParentUserId()
					.equals("")) {
				messageToSend = messageFormat[0].replace("PARENT",
						admissionDetailsVO.getUserDetailsForSMS()
								.getParentUserId()
								+ "/"
								+ admissionDetailsVO.getUserDetailsForSMS()
										.getParentPassword());
			}
			if (!admissionDetailsVO.getUserDetailsForSMS().getStudentUserId()
					.equals("")) {
				messageToSend = messageToSend
						+ messageFormat[1].replace("STUDENT",
								admissionDetailsVO.getUserDetailsForSMS()
										.getStudentUserId()
										+ "/"
										+ admissionDetailsVO
												.getUserDetailsForSMS()
												.getStudentPassword());
			}
			smsAlert.setSmsMessage(messageToSend);
			smsAlert.setAlertType(ApplicationConstant.ADMISSION_ALERT);
			List<String> admissionNum = new ArrayList<String>();

			admissionNum.add(admissionDetailsVO.getStudentAdmisNo());

			try {
				sendAlertSMS.sendAlertSMS(applicationCache, smsAlert,
						admissionNum, userSessionDetails);
			} catch (NoDataFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UpdateFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public String duplicateParentId(String parentId,
			UserSessionDetails userSessionDetails) {
		String parentUserId = "";
		try {
			UserLink userLink = userLinkDao.getUserDetails(parentId);
			parentUserId = userLink.getLinkId();
		} catch (InvalidUserIdException e) {
			return parentUserId;
		}
		return parentUserId;
	}

	@Override
	public String duplicateAdmisNo(String admisNo,
			UserSessionDetails userSessionDetails) {
		String duplicates = "";
		StudentMaster studentMaster = null;
		StudentMasterKey studentMasterKey = new StudentMasterKey();
		studentMasterKey.setInstId(userSessionDetails.getInstId());
		studentMasterKey.setBranchId(userSessionDetails.getBranchId());
		studentMasterKey.setStudentAdmisNo(admisNo);
		try {
			studentMaster = studentMasterDao
					.retriveStudentDetails(studentMasterKey);
		} catch (NoDataFoundException e) {
			return duplicates;
		}
		if (studentMaster != null) {
			duplicates = "Y";
		}
		return duplicates;
	}

	public String getStudentGrpId(UserSessionDetails userSessionDetails,
			String courseId, String termId, String secId) {
		return stuGrpMasDao.getStuGrpIdForSTUM(userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), courseId, termId, secId);
	}

	@Override
	public Map<String, Map<String, String>> getSubFromCourseAndTrm(
			String studentGrpId, String courseId, String trmId,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		Map<String, Map<String, String>> subIdAndListOfSubTypeMap = new HashMap<String, Map<String, String>>();
		Map<String, CourseSubLink> subList = null;
		if (studentGrpId != null) {
			subList = allSubListDao.getSubListFromCrslAndSbjm(
					userSessionDetails, studentGrpId);
		} else {
			subList = allSubListDao.getSubListFromCrsl(userSessionDetails,
					courseId, trmId);
		}

		for (Map.Entry<String, CourseSubLink> entry : subList.entrySet()) {
			if (subIdAndListOfSubTypeMap.containsKey(entry.getValue()
					.getSubType())) {
				Map<String, String> listOfSubTypeExisting = subIdAndListOfSubTypeMap
						.get(entry.getValue().getSubType());
				listOfSubTypeExisting.put(entry.getValue().getSubId(),
						entry.getKey());
				subIdAndListOfSubTypeMap.put(entry.getValue().getSubType(),
						listOfSubTypeExisting);
			} else {
				Map<String, String> listOfSubTypeNew = new LinkedHashMap<String, String>();
				listOfSubTypeNew.put(entry.getValue().getSubId(),
						entry.getKey());
				subIdAndListOfSubTypeMap.put(entry.getValue().getSubType(),
						listOfSubTypeNew);
			}

		}
		return subIdAndListOfSubTypeMap;
	}

	@Override
	public Boolean courseVariantCheckWithCourse(String courseId,
			UserSessionDetails userSessionDetails) {
		return courseDao.checkForCVFromCourseId(courseId,
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId());

	}

	@Override
	public String getStuGrpIdForSecList(String admisNo,
			UserSessionDetails userSessionDetails) {
		return studentMasterDao.getStuGrpIdForSecList(admisNo,
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId());
		// return null;
	}

}
