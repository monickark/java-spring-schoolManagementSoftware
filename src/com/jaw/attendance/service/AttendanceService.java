package com.jaw.attendance.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jaw.attendance.controller.AttendanceDetailsListVO;
import com.jaw.attendance.controller.AttendanceListVO;
import com.jaw.attendance.controller.AttendanceMasterVO;
import com.jaw.attendance.controller.ViewAttendance;
import com.jaw.attendance.dao.AttendanceList;
import com.jaw.attendance.dao.IAttendanceListDAO;
import com.jaw.attendance.dao.IStudentAbsenseDetailsDAO;
import com.jaw.attendance.dao.IStudentAbsenseDetailsListDAO;
import com.jaw.attendance.dao.IStudentAttendanceDAO;
import com.jaw.attendance.dao.IStudentAttendanceListDAO;
import com.jaw.attendance.dao.IStudentAttendanceMasterDAO;
import com.jaw.attendance.dao.IStudentAttendanceMasterListDAO;
import com.jaw.attendance.dao.IViewAttendanceDAO;
import com.jaw.attendance.dao.StudentAbsenseDetails;
import com.jaw.attendance.dao.StudentAbsenseDetailsKey;
import com.jaw.attendance.dao.StudentAttendance;
import com.jaw.attendance.dao.StudentAttendanceList;
import com.jaw.attendance.dao.StudentAttendanceListKey;
import com.jaw.attendance.dao.StudentAttendanceMaster;
import com.jaw.attendance.dao.StudentAttendanceMasterList;
import com.jaw.common.exceptions.batch.DateIsHolidayException;
import com.jaw.common.exceptions.batch.FutureDateException;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.AttendanceExistException;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.sms.SendAlertSMS;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.common.util.SMSPropertyManagementUtil;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.core.dao.HolidayMaintenanceKey;
import com.jaw.core.dao.IHolidayMaintenanceDAO;
import com.jaw.core.dao.ISpecialClassDAO;
import com.jaw.core.dao.SpecialClassKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.framework.sessCache.UserSessionDetails;
import org.springframework.transaction.annotation.Transactional;
import com.jaw.core.dao.SpecialClass;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.communication.dao.SMSAlert;
import com.jaw.communication.dao.SMSRequest;

//attendance Details Service Class
@Service
public class AttendanceService implements IAttendanceService {
	// Logging
	Logger logger = Logger.getLogger(AttendanceService.class);

	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	SMSPropertyManagementUtil smsPropertyManagementUtil;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IViewAttendanceDAO viewAttendanceDao;
	@Autowired
	IDropDownListService dropdownListService;
	@Autowired
	ISpecialClassDAO specialClassDao;
	@Autowired
	IAttendanceListDAO attendanceListDAO;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	CommonCodeUtil commonCodeUtil;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Autowired
	IStudentAttendanceDAO studentAttendanceDAO;
	@Autowired
	IHolidayMaintenanceDAO holidayMaintenanceDAO;
	@Autowired
	IStudentAttendanceListDAO studentAttendanceListDao;
	@Autowired
	IStudentAbsenseDetailsListDAO studentAbsenseDetailsDao;
	@Autowired
	IStudentAttendanceMasterDAO studentAttendanceMasterDao;
	@Autowired
	IStudentAttendanceMasterListDAO studentAttendanceMasterListDAO;
	@Autowired
	DoAudit doAudit;
	@Autowired
	IStudentAbsenseDetailsDAO studentAbsenseDetailsDAO;
    @Autowired
    SendAlertSMS sendAlertSMS;
	/*
	 * @Override public void selectAttendanceData(AttendanceMasterVO
	 * attendanceMasterVO, UserSessionDetails userSessionDetails) throws
	 * NoDataFoundException { AttendanceList attendanceList = new
	 * AttendanceList(); commonBusiness.changeObject(attendanceList,
	 * attendanceMasterVO.getAttendanceSearchVO());
	 * attendanceList.setInstId(userSessionDetails.getInstId());
	 * attendanceList.setBranchId(userSessionDetails.getBranchId());
	 * List<AttendanceList> attendanceLists =
	 * attendanceListDAO.getAttendanceList(attendanceList);
	 * List<AttendanceListVO> AttendanceListVOs = new
	 * ArrayList<AttendanceListVO>();
	 * 
	 * for (int i = 0; i < attendanceLists.size(); i++) { AttendanceList
	 * attendanceList2 = attendanceLists.get(i); AttendanceListVO
	 * attendanceListVO = new AttendanceListVO();
	 * commonBusiness.changeObject(attendanceListVO, attendanceList2);
	 * 
	 * attendanceListVO.setRowid(i); AttendanceListVOs.add(attendanceListVO); }
	 * 
	 * attendanceMasterVO.setAttendanceListVOs(AttendanceListVOs);
	 * 
	 * }
	 */

		@Override
	public void selectAttendanceData(AttendanceMasterVO attendanceMasterVO,
			UserSessionDetails userSessionDetails)
			throws NoRecordFoundException, NoDataFoundException {
		StudentAttendanceMasterList studentAttendanceMasterList = new StudentAttendanceMasterList();
		commonBusiness.changeObject(studentAttendanceMasterList,
				attendanceMasterVO.getAttendanceSearchVO());
		studentAttendanceMasterList.setInstId(userSessionDetails.getInstId());
		studentAttendanceMasterList.setBranchId(userSessionDetails
				.getBranchId());
		List<StudentAttendanceMasterList> attendanceLists = studentAttendanceMasterListDAO
				.getStudentAttendanceMasterList(studentAttendanceMasterList,userSessionDetails);
		System.out.println("institution category :"
				+ userSessionDetails.getInbrCategory());
		if (userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_PRE_UNIVERSITY_COLLEGE)) {
			List<AttendanceDetailsListVO> attendanceDetailsListVOs = new ArrayList<AttendanceDetailsListVO>();

			for (int i = 0; i < attendanceLists.size(); i++) {
				StudentAttendanceMasterList studentAttendanceMasterList2 = attendanceLists
						.get(i);
				AttendanceDetailsListVO attendanceDetailsListVO = new AttendanceDetailsListVO();
				commonBusiness.changeObject(attendanceDetailsListVO,
						studentAttendanceMasterList2);

				attendanceDetailsListVO.setRowid(i);

				attendanceDetailsListVOs.add(attendanceDetailsListVO);
			}

			attendanceMasterVO
					.setAttendanceDetailsListVOs(attendanceDetailsListVOs);
		} else {
			System.out.println("inside else");
			AttendanceDetailsListVO attendanceDetailsListVO = new AttendanceDetailsListVO();
			StudentAttendanceMasterList studentAttendanceMasterList2 = attendanceLists
					.get(0);
			commonBusiness.changeObject(attendanceDetailsListVO,
					studentAttendanceMasterList2);
			selectAttendanceDetailsData(attendanceMasterVO,
					attendanceDetailsListVO, userSessionDetails);
			System.out.println("else completed");
			System.out.println("size :"+attendanceMasterVO.getAttendanceListVOs().size());
		}
	}

	@Override
	public void selectAttendanceDetailsData(
			AttendanceMasterVO attendanceMasterVO,
			AttendanceDetailsListVO attendanceDetailsListVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		AttendanceList attendanceList = new AttendanceList();
		commonBusiness.changeObject(attendanceList, attendanceDetailsListVO);
		attendanceList.setInstId(userSessionDetails.getInstId());
		attendanceList.setBranchId(userSessionDetails.getBranchId());
		List<AttendanceList> attendanceLists = attendanceListDAO
				.getAttendanceList(attendanceList);
		String stamId = attendanceDetailsListVO.getStamId();
		List<AttendanceListVO> AttendanceListVOs = new ArrayList<AttendanceListVO>();

		for (int i = 0; i < attendanceLists.size(); i++) {
			AttendanceList attendanceList2 = attendanceLists.get(i);
			AttendanceListVO attendanceListVO = new AttendanceListVO();
			commonBusiness.changeObject(attendanceListVO, attendanceList2);
			attendanceListVO.setStamId(stamId);
			attendanceListVO.setRowid(i);
			if (attendanceList.getRemark() == null) {
				attendanceListVO.setRemark("");
			}
			AttendanceListVOs.add(attendanceListVO);

		}

		attendanceMasterVO.setAttendanceListVOs(AttendanceListVOs);

	}

	@Override
	public void updateAttendanceRec(AttendanceMasterVO attendanceMasterVO,
			AttendanceListVO attendanceListVO,
			UserSessionDetails userSessionDetails)
			throws UpdateFailedException, NoDataFoundException,
			DuplicateEntryException, DatabaseException, DeleteFailedException {
		logger.debug("inside update attendance Details method");
		logger.debug("attendance vo :" + attendanceListVO.toString());
		if (attendanceListVO.getIsAbsent().equals("P")) {

			StudentAbsenseDetailsKey studentAbsenseDetailsKey = new StudentAbsenseDetailsKey();
			studentAbsenseDetailsKey.setInstId(userSessionDetails.getInstId());
			studentAbsenseDetailsKey.setBranchId(userSessionDetails
					.getBranchId());
			studentAbsenseDetailsKey.setStamId(attendanceListVO.getStamId());
			studentAbsenseDetailsKey.setStudentAdmisNo(attendanceListVO
					.getAdmissionNumber());
			StudentAbsenseDetails studentAbsenseDetails = studentAbsenseDetailsDAO
					.selectStudentAbsenseDetailsRec(studentAbsenseDetailsKey);
			studentAbsenseDetailsKey.setDbTs(studentAbsenseDetails.getDbTs());
			logger.debug("attendance key :"
					+ studentAbsenseDetailsKey.toString());
			studentAbsenseDetailsDAO
					.manualDeleteStudentAbsenseDetailsRec(studentAbsenseDetailsKey);
		} else {
			StudentAbsenseDetails studentAbsenseDetails = new StudentAbsenseDetails();
			studentAbsenseDetails.setInstId(userSessionDetails.getInstId());
			studentAbsenseDetails.setBranchId(userSessionDetails.getBranchId());
			studentAbsenseDetails.setrCreId(userSessionDetails.getUserId());
			studentAbsenseDetails.setrModId(userSessionDetails.getUserId());
			studentAbsenseDetails.setAbsenteeRmks(attendanceListVO.getRemark());
			studentAbsenseDetails.setIsPresent(attendanceListVO.getIsAbsent());
			studentAbsenseDetails.setStamId(attendanceListVO.getStamId());
			studentAbsenseDetails.setStudentAdmisNo(attendanceListVO
					.getAdmissionNumber());

			studentAbsenseDetailsDAO
					.insertStudentAbsenseDetailsRec(studentAbsenseDetails);
		}
		String remark = " AcTerm: "
				+ attendanceMasterVO.getAttendanceSearchVO().getAcTerm()
				+ "/StGroup: "
				+ attendanceMasterVO.getAttendanceSearchVO()
						.getStudentGroupId() + "/Sub: "
				+ attendanceMasterVO.getAttendanceSearchVO().getCrslId()
				+ " / date : "
				+ attendanceMasterVO.getAttendanceSearchVO().getAttDate()
				+ attendanceListVO.getAdmissionNumber()
				+ "/ Remarks: " + attendanceListVO.getRemark()
				+ "Reason :/"
				+ attendanceListVO.getRemarkForChange();
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.ATTENDANCE_UPDATE_SUCCESS, remark);

	}

	public int checkDateIsHoliday(String acTerm, String holDate,
			String studentGrpId, UserSessionDetails userSessionDetails,String crslId) {
		SpecialClass specialClass = new SpecialClass();
		specialClass.setAcTerm(acTerm);
		specialClass.setInstId(userSessionDetails.getInstId());
		specialClass.setBranchId(userSessionDetails.getBranchId());
		specialClass.setStudentGrpId(studentGrpId);
		specialClass.setScDate(holDate);
		specialClass.setCrslId(crslId);
		int result = 0;
		if(specialClassDao.checkDateHasSpecialClass(specialClass,userSessionDetails)==0){
			HolidayMaintenanceKey holidayMaintenanceKey = new HolidayMaintenanceKey();
			holidayMaintenanceKey.setAcTerm(acTerm);
			holidayMaintenanceKey.setHolDate(holDate);
			holidayMaintenanceKey.setInstId(userSessionDetails.getInstId());
			holidayMaintenanceKey.setBranchId(userSessionDetails.getBranchId());
			
			result= holidayMaintenanceDAO.checkDateIsHoliday(holidayMaintenanceKey,
					studentGrpId);
		}
		
		System.out.println("resultttttttttttttttt"+result);
		return result;
	}


	/*@Override
	public int checkAttendanceExist(StudentAttendance studentAttendance,
			String studentGrpId) {
		return studentAttendanceDAO.checkAttendanceExist(studentAttendance,
				studentGrpId);
	}
*/

	@Override
	public List<String> selectSubjectListForAtt(String stdGrpId,
			UserSessionDetails userSessionDetails) {
		List<String> details = new ArrayList<String>();
		List<CourseSubLink> courseSubLink;
		try {
			courseSubLink = dropdownListService.selectSubListForStudentGrp(
					stdGrpId, userSessionDetails);
			for (CourseSubLink courseSubLinkP : courseSubLink) {
				details.add(courseSubLinkP.getCrslId() + "_"
						+ courseSubLinkP.getSubType() + "_"
						+ courseSubLinkP.getSubId() + "_"
						+ courseSubLinkP.getRequiresLab());

			}
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Subject Dropdown :" + e.getMessage());
			details.add("error");// TODO Auto-generated catch block
		}
		return details;
	}

	public List<StudentAttendanceList> getStudentListForAttendance(
			AttendanceMasterVO attendanceMasterVo,
			UserSessionDetails userSessionDetails) throws NoDataFoundException, AttendanceExistException, FutureDateException, DateIsHolidayException {
		List<StudentAttendanceList> stAtt=null ;
		StudentAttendanceListKey studentAttendanceListKey = new StudentAttendanceListKey();
		commonBusiness.changeObject(studentAttendanceListKey,
				attendanceMasterVo.getAttendanceSearchVO());
		
		studentAttendanceListKey.setOccurNo("OCR1");
		//Check Attendance exist 
		StudentAttendanceMaster studentAttMaster = new StudentAttendanceMaster();		
		commonBusiness.changeObject(studentAttMaster,
				attendanceMasterVo.getAttendanceSearchVO());
		if ((attendanceMasterVo.getAttendanceSearchVO().getCrslId() != "")
				&& (attendanceMasterVo.getAttendanceSearchVO().getCrslId() != null)) {
			String[] crst = attendanceMasterVo.getAttendanceSearchVO()
					.getCrslId().split("-");
			studentAttMaster.setCrslId(crst[0]);
		}
		studentAttMaster.setOccurNo("OCR1");
		studentAttMaster.setInstId(userSessionDetails.getInstId());
		studentAttMaster.setBranchId(userSessionDetails.getBranchId());
		if ((studentAttendanceListKey.getCrslId() != "")
				&& (studentAttendanceListKey.getCrslId() != null)) {
			String[] crst = studentAttendanceListKey.getCrslId().split("-");
			studentAttendanceListKey.setCrslId(crst[0]);
			studentAttendanceListKey.setSubType(crst[1]);
			
		}
		if(dateUtil.getDateFormatByString(studentAttMaster.getAttDate()).compareTo(dateUtil.getCurrentDateInDateDataType())>0){
				throw new FutureDateException();
    	}else if(checkDateIsHoliday(studentAttMaster.getAcTerm(), studentAttMaster.getAttDate(),
				studentAttMaster.getStudentGroupId(), userSessionDetails,studentAttendanceListKey.getCrslId())!=0){
			throw new DateIsHolidayException();
		}else  if (studentAttendanceMasterDao.checkCollegeAttendanceExist(studentAttMaster,userSessionDetails) == 1) {
			throw new AttendanceExistException();
		} else {
		
		
				
		studentAttendanceListKey.setInstId(userSessionDetails.getInstId());
		studentAttendanceListKey.setBranchId(userSessionDetails.getBranchId());
		
		 stAtt = studentAttendanceListDao.getStudentsForAttendance(studentAttendanceListKey);
		for (int i = 0; i < stAtt.size(); i++) {
			StudentAttendanceList stdAttList = stAtt.get(i);
			stdAttList.setRowId(i);
			// acaCalVOs.add(acadCalVO);
		}
		

		}
		
		return stAtt;
	}
	@Transactional(rollbackFor = Exception.class)
	public void markAttendance(List<StudentAttendanceList> stuAttList,
			
			UserSessionDetails userSessionDetails,
			AttendanceMasterVO attendanceMasterVO,ApplicationCache applicationCache) throws DatabaseException,
			DuplicateEntryException, PropertyNotFoundException, NoDataFoundException, UpdateFailedException{
		
		String subjectId=null;
		attendanceMasterVO.getAttendanceSearchVO().setOccurNo("OCR1");
		attendanceMasterVO.setNoOfSessions("1");
		if ((attendanceMasterVO.getNoOfSessions()!=null)||(attendanceMasterVO.getNoOfSessions().contains(","))) {
			String parts[] = attendanceMasterVO.getNoOfSessions().split(",");
			attendanceMasterVO.setNoOfSessions(parts[0]);
		}
		if ((attendanceMasterVO.getAttendanceSearchVO().getCrslId() != "")
				&& (attendanceMasterVO.getAttendanceSearchVO().getCrslId() != null)) {
			String[] crst = attendanceMasterVO.getAttendanceSearchVO()
					.getCrslId().split("-");
			attendanceMasterVO.getAttendanceSearchVO().setCrslId(crst[0]);
			subjectId=crst[3];
		}
		String stamId=AlphaSequenceUtil
				.generateAlphaSequence(ApplicationConstant.ATT_MTR_SEQ,
						simpleIdGenerator.getNextId(
								SequenceConstant.ATTENDANCE_MASTER_SEQUENCE, 1));
		StudentAttendanceMaster studentAttMaster=new StudentAttendanceMaster();
		commonBusiness.changeObject(studentAttMaster, attendanceMasterVO.getAttendanceSearchVO());
		studentAttMaster.setInstId(userSessionDetails.getInstId());
		studentAttMaster.setBranchId(userSessionDetails.getBranchId());
		studentAttMaster.setrCreId(userSessionDetails.getUserId());
		studentAttMaster.setrModId(userSessionDetails.getUserId());
		studentAttMaster.setNoOfSessions(Integer.parseInt(attendanceMasterVO.getNoOfSessions()));
		studentAttMaster.setStamId(stamId);
		/*List<StudentAttendance> toMarkStudentList= getStudentObjectForAttendance(
				stuAttList, userSessionDetails, attendanceMasterVO);*/
		List<StudentAbsenseDetails> studentAbsenseDetails = getStudentAbsentObjectForAttendance(
		stuAttList, userSessionDetails, attendanceMasterVO,stamId);
		studentAttendanceMasterDao.insertStudentAttendanceMasterRec(studentAttMaster);
		if(studentAbsenseDetails.size()!=0){
		studentAbsenseDetailsDao.insertAttendanceAbsentRecs(studentAbsenseDetails);
		
		}
		// functional audit
					if((!userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_SSLC))&&(!userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_ICSE))){
		                    doAudit.doFunctionalAudit(userSessionDetails,
							AuditConstant.MARK_ATTENDANCE, " Academic term : "+studentAttMaster.getAcTerm()+ 
											",Studenr Group Id : "+studentAttMaster.getStudentGroupId() +", Subject : "+
											subjectId +" and date : "+studentAttMaster.getAttDate());
					}else{
							doAudit.doFunctionalAudit(userSessionDetails,
							AuditConstant.MARK_ATTENDANCE, " Academic term : "+studentAttMaster.getAcTerm()+ 
									",Studenr Group Id : "+studentAttMaster.getStudentGroupId()  +" and date : "+studentAttMaster.getAttDate());
					}
			
					System.out.println("remarks in service"+"Attendance has been marked " +
									"successfully for "+studentAttMaster.getAcTerm() +" Academic term, " +
											"Studenr Group Id  :"+studentAttMaster.getStudentGroupId() +", Subject "+
											subjectId +" and date "+studentAttMaster.getAttDate());
					logger.debug("Completed Functional Auditing");
			//studentAttendanceListDao.insertAttendanceRecs(toMarkStudentList);
					
					//Send Absent detail SMS
					if(studentAbsenseDetails.size()!=0){
					//sendAbsentDetailsSMS(applicationCache,studentAttMaster,studentAbsenseDetails,userSessionDetails);
					}

	}
	//Method to send sms to absent students
		void sendAbsentDetailsSMS(ApplicationCache applicationCache,
				StudentAttendanceMaster studentAttMaster,List<StudentAbsenseDetails> studentAbsenseDetails,
			UserSessionDetails userSessionDetails) throws PropertyNotFoundException, DuplicateEntryException, NoDataFoundException, UpdateFailedException{
			//Send SMS
			logger.debug("Send Absent SMS method");
			System.out.println("send message property ");
			String sendSMS=smsPropertyManagementUtil.getPropertyValue(
					  applicationCache, userSessionDetails.getInstId(),
					  userSessionDetails.getBranchId(),
					  PropertyManagementConstant.ABSENT_SEND_SMS_RQRD).toString();

			logger.debug("Send Absent SMS Required : "+sendSMS);
			
			if((!sendSMS.equals(""))&&(sendSMS.equals(ApplicationConstant.ABSENT_SMS_REQUIRED))){
				String message=smsPropertyManagementUtil.getPropertyValue(
						  applicationCache, userSessionDetails.getInstId(),
						  userSessionDetails.getBranchId(),
						  PropertyManagementConstant.ABSENT_SMS_MSG).toString();
				System.out.println("send message"+message);
				SMSAlert smsAlert=new SMSAlert();
				smsAlert.setInstId(studentAttMaster.getInstId());
				smsAlert.setBranchId(studentAttMaster.getBranchId());
				smsAlert.setAcTerm(studentAttMaster.getAcTerm());
				smsAlert.setrModId(userSessionDetails.getUserId());
				smsAlert.setrCreId(userSessionDetails.getUserId());
				smsAlert.setSmsMessage(message + " "+studentAttMaster.getAttDate());
				//smsAlert.setAlertDate(alertDate);
				smsAlert.setAlertType(ApplicationConstant.ATTENDANCE_ALERT);				
				smsAlert.setAttendanceDate(studentAttMaster.getAttDate());
				List<String> admissionNum=new ArrayList<String>();
				for(int a=0;a<studentAbsenseDetails.size();a++){
					admissionNum.add(studentAbsenseDetails.get(a).getStudentAdmisNo());
				}
				
				sendAlertSMS.sendAlertSMS(applicationCache, smsAlert, admissionNum,userSessionDetails);
			}
		}
	private List<StudentAbsenseDetails> getStudentAbsentObjectForAttendance(
			List<StudentAttendanceList> studentList,
			UserSessionDetails userSessionDetails,
			AttendanceMasterVO attendanceMasterVO, String stamId)
			throws DatabaseException {
		List<StudentAbsenseDetails> stuAbsList = new ArrayList<StudentAbsenseDetails>();

		for (int i = 0; i < studentList.size(); i++) {
			if (studentList.get(i).getAttendanceAbsent()
					.equals(ApplicationConstant.ATT_ABSENT)) {
				StudentAbsenseDetails absenseDetails = new StudentAbsenseDetails();

				absenseDetails.setInstId(userSessionDetails.getInstId());
				absenseDetails.setBranchId(userSessionDetails.getBranchId());
				absenseDetails.setrCreId(userSessionDetails.getUserId());
				absenseDetails.setrModId(userSessionDetails.getUserId());
				absenseDetails.setStamId(stamId);
				absenseDetails.setAbsenteeRmks(studentList.get(i).getRemarks());
				absenseDetails.setStudentAdmisNo(studentList.get(i)
						.getStudentAdmisNo());
				absenseDetails.setIsPresent(studentList.get(i)
						.getAttendanceAbsent());

				stuAbsList.add(absenseDetails);
			}
		}
		return stuAbsList;
	}

	/*
	 * private List<StudentAttendance> getStudentObjectForAttendance(
	 * List<StudentAttendanceList> studentList, UserSessionDetails
	 * userSessionDetails, AttendanceMasterVO attendanceMasterVO) throws
	 * DatabaseException { List<StudentAttendance> stuAttList = new
	 * ArrayList<StudentAttendance>();
	 * 
	 * for (int i = 0; i < studentList.size(); i++) { StudentAttendance
	 * studentAttendance = new StudentAttendance();
	 * 
	 * studentAttendance.setInstId(userSessionDetails.getInstId());
	 * studentAttendance.setBranchId(userSessionDetails.getBranchId());
	 * studentAttendance.setrCreId(userSessionDetails.getUserId());
	 * studentAttendance.setrModId(userSessionDetails.getUserId());
	 * studentAttendance.setAbsenteeRmks(studentList.get(i).getRemarks());
	 * 
	 * 
	 * studentAttendance.setAttDate(attendanceMasterVO
	 * .getAttendanceSearchVO().getAttDate());
	 * studentAttendance.setClsType(attendanceMasterVO
	 * .getAttendanceSearchVO().getClassType());
	 * studentAttendance.setCrslId(attendanceMasterVO
	 * .getAttendanceSearchVO().getCrslId());
	 * studentAttendance.setIsPresent(studentList.get(i)
	 * .getAttendanceAbsent());
	 * 
	 * studentAttendance.setNoOfSessions(Integer
	 * .parseInt(attendanceMasterVO.getNoOfSessions()));
	 * studentAttendance.setSattSeqNo(AlphaSequenceUtil
	 * .generateAlphaSequence(ApplicationConstant.ATT_SEQ,
	 * simpleIdGenerator.getNextId( SequenceConstant.ATTENDANCE_SEQUENCE, 1)));
	 * studentAttendance.setStudentAdmisNo(studentList.get(i)
	 * .getStudentAdmisNo()); stuAttList.add(studentAttendance); } return
	 * stuAttList; }
	 */

	@Override
	public void getSubjectWiseAttendance(ApplicationCache applicationCache,
			UserSessionDetails userSessionDetails,
			StudentSession studentSession, AttendanceMasterVO attendanceMasterVO)
			throws NoDataFoundException, PropertyNotFoundException {
		StudentAttendance studentAttendance = new StudentAttendance();
		studentAttendance.setInstId(userSessionDetails.getInstId());
		studentAttendance.setBranchId(userSessionDetails.getBranchId());
		studentAttendance.setStudentAdmisNo(studentSession.getStudentAdmisNo());
		studentAttendance.setAcTerm("AT4");
		studentAttendance.setStudentAdmisNo("10101010");
		List<StudentAttendance> stuAttList = null;

		/*
		 * String labInclude=propertyManagementUtil.getPropertyValue(
		 * applicationCache, userSessionDetails.getInstId(),
		 * userSessionDetails.getBranchId(),
		 * PropertyManagementConstant.LAB_ATTENDANCE_INCLUDE).toString(); String
		 * showLabInclude=propertyManagementUtil.getPropertyValue(
		 * applicationCache, userSessionDetails.getInstId(),
		 * userSessionDetails.getBranchId(),
		 * PropertyManagementConstant.SHOW_LAB_ATTENDANCE).toString();
		 */
		// String showLabInclude=null;
		// String labInclude="N";
		String labInclude = "Y";
		String showLabInclude = "N";

		if (labInclude == "N") {
			studentAttendance.setClsType("T");
			stuAttList = viewAttendanceDao
					.attendanceWithoutLab(studentAttendance);
		} else if (showLabInclude == "Y") {
			System.out.println("secondddddddddddd");
			stuAttList = viewAttendanceDao
					.attendanceWithLabSeperatly(studentAttendance);
		} else {
			studentAttendance.setClsType("");
			stuAttList = viewAttendanceDao
					.attendanceWithoutLab(studentAttendance);

		}

		ViewAttendanceHelper viewAttendanceHelper = new ViewAttendanceHelper();
		List<ViewAttendance> viewAttList = new ArrayList<ViewAttendance>();
		viewAttList = viewAttendanceHelper.getAttendanceDeatils(stuAttList);

		attendanceMasterVO.setViewAttenList(viewAttList);
	}

	@Override
	public void getConsolidatedAttendance(
			UserSessionDetails userSessionDetails,
			StudentSession studentSession, AttendanceMasterVO attendanceMasterVO)
			throws NoDataFoundException {
		StudentAttendance studentAttendance = new StudentAttendance();
		studentAttendance.setInstId(userSessionDetails.getInstId());
		studentAttendance.setBranchId(userSessionDetails.getBranchId());
		studentAttendance.setStudentAdmisNo(studentSession.getStudentAdmisNo());
		studentAttendance.setAcTerm("AT4");
		studentAttendance.setStudentAdmisNo("10101010");
		List<ViewAttendance> viewAttList = null;

		viewAttList = viewAttendanceDao
				.consolidateAttendance(studentAttendance);

		for (ViewAttendance viewAttendance : viewAttList) {
			System.out.println("sssssssssssssssssssssssssssssssssssssssss"
					+ viewAttendance);
		}

		attendanceMasterVO.setViewAttenList(viewAttList);
	}

}
