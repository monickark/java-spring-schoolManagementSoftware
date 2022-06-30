package com.jaw.common.dropdown.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.admin.dao.BranchMaster;
import com.jaw.common.dropdown.dao.IAcademicTermListDAO;
import com.jaw.common.dropdown.dao.IBranchListDAO;
import com.jaw.common.dropdown.dao.ICourseNameListDAO;
import com.jaw.common.dropdown.dao.IExamNameListDAO;
import com.jaw.common.dropdown.dao.IFeeCategoryListDao;
import com.jaw.common.dropdown.dao.IMenuProfileListDAO;
import com.jaw.common.dropdown.dao.IStudentGroupListTagDAO;
import com.jaw.common.dropdown.dao.IStudentListDAO;
import com.jaw.common.dropdown.dao.ISubjectListBasedOnStudentGrpDAO;
import com.jaw.common.dropdown.dao.ITeachingStaffListDAO;
import com.jaw.common.dropdown.dao.ITermAndSecListDAO;
import com.jaw.common.dropdown.dao.ITransportListDAO;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.ClassTeacherAllotment;
import com.jaw.core.dao.ClassTeacherAllotmentKey;
import com.jaw.core.dao.CourseMaster;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.core.dao.IClassTeacherAllotmentDao;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.dao.MarkMaster;
import com.jaw.staff.dao.StaffDetails;
import com.jaw.common.dropdown.dao.ISubjectListDAO;
import com.jaw.common.dropdown.dao.IStaffDepartmentListDAO;
import com.jaw.common.dropdown.dao.IAllSubjectListDAO;

//Service Class for Simple Sequence Generation

@Service
public class DropDownListService implements IDropDownListService {

	@Autowired
	IBranchListDAO branchListTag;
	@Autowired
	ICourseNameListDAO courseNameListTag;
	@Autowired
	IStudentGroupListTagDAO studentGroupListTag;
	@Autowired
	IAcademicTermListDAO academicTermListDao;
	@Autowired
	IMenuProfileListDAO menuProfileListDAO;
	@Autowired
	ITeachingStaffListDAO teachingStaffListDAO;
	@Autowired
	IExamNameListDAO examNameListDAO;
	@Autowired
	ISubjectListBasedOnStudentGrpDAO subjectForSplDAO;
	@Autowired
	ISubjectListDAO subjectListDAO;
	@Autowired
	IAllSubjectListDAO allSubjectListDAO;
	@Autowired
	ITermAndSecListDAO termAndSecListDAO;
	@Autowired
	IClassTeacherAllotmentDao classTeacherAllotmentDao;
	@Autowired
	IStaffDepartmentListDAO staffDepartmentListDAO;
	@Autowired
	IStudentListDAO studentListDAO;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired 
	IFeeCategoryListDao feeCategoryListDao;
	@Autowired
	ITransportListDAO transportListDAO;
	// Logging
	Logger logger = Logger.getLogger(DropDownListService.class);

	@Override
	public Map<String, String> getBranchListTag(
			final UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
		List<BranchMaster> branchMasters = branchListTag
				.selectBranchList(userSessionDetails);
		Map<String, String> branchMap = new LinkedHashMap<String, String>();
		for (BranchMaster branchMaster : branchMasters) {
			branchMap.put(branchMaster.getBranchId(),
					branchMaster.getBranchName());
		}
		return branchMap;

	}

	@Override
	public Map<String, String> getCourseNameListTag(
			final UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
		List<CourseMaster> courseMasters = courseNameListTag
				.selectCourseNameList(userSessionDetails);
		Map<String, String> courseMap = new LinkedHashMap<String, String>();
		for (CourseMaster courseMaster : courseMasters) {
			courseMap.put(courseMaster.getCourseMasterId(),
					courseMaster.getCourseName());
		}
		return courseMap;
	}

	@Override
	public List<StudentGroupMaster> getStudentGroupListTag(
			SessionCache sessionCache, String acTerm)
			throws NoDataFoundException {
		List<StudentGroupMaster> studGrpMasters = null;
		if ((acTerm == null) || (acTerm == "")) {
			acTerm = sessionCache.getUserSessionDetails().getCurrAcTerm();
		}
		logger.debug("User Menu Profile  :"
				+ sessionCache.getUserSessionDetails().getUserMenuProfile());

		if (sessionCache.getUserSessionDetails().getUserMenuProfile()
				.equals(ApplicationConstant.TEACHING_STAFF)) {

			ClassTeacherAllotmentKey classTeacherAllotmentKey = new ClassTeacherAllotmentKey();
			classTeacherAllotmentKey.setInstId(sessionCache
					.getUserSessionDetails().getInstId());
			classTeacherAllotmentKey.setBranchId(sessionCache
					.getUserSessionDetails().getBranchId());
			classTeacherAllotmentKey.setStaffId(sessionCache
					.getUserSessionDetails().getLinkId());
			classTeacherAllotmentKey.setAcTerm(sessionCache
					.getUserSessionDetails().getCurrAcTerm());
			try {
				ClassTeacherAllotment classTeacherAllotment = classTeacherAllotmentDao
						.selectStaff(classTeacherAllotmentKey);
				studGrpMasters = studentGroupListTag
						.selectStudentGroupListForClassTeacher(
								sessionCache.getUserSessionDetails(),
								classTeacherAllotment.getStGroup(),
								classTeacherAllotment.getAcTerm());
			} catch (NoDataFoundException e) {
				studGrpMasters = studentGroupListTag
						.selectStudentGroupListForStaff(
								sessionCache.getUserSessionDetails(), acTerm);
			}

		} else {
			studGrpMasters = studentGroupListTag
					.selectStudentGroupList(sessionCache
							.getUserSessionDetails());
		}			
		return studGrpMasters;
	}

	@Override
	public Map<String, String> getAcademicTermListTag(
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		academicTermDetailsKey.setInstId(userSessionDetails.getInstId());
		academicTermDetailsKey.setBranchId(userSessionDetails.getBranchId());
		List<AcademicTermDetails> academicTermDetList = null;
		academicTermDetList = academicTermListDao
				.selectAcademicTermList(academicTermDetailsKey);

		Map<String, String> acTrmMap = new LinkedHashMap<String, String>();

		for (AcademicTermDetails academicTermDetails : academicTermDetList) {
			acTrmMap.put(academicTermDetails.getAcTerm(),
					academicTermDetails.getAcYear());
		}
		return acTrmMap;
	}

	@Override
	public Map<String, String> selectTeachingStaffList(
			final UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
		List<StaffDetails> staffDetails = teachingStaffListDAO
				.selectTeachingStaffList(userSessionDetails);
		Map<String, String> teachingStaffMap = new LinkedHashMap<String, String>();
		for (StaffDetails StaffDetail : staffDetails) {
			teachingStaffMap.put(StaffDetail.getStaffId(),
					StaffDetail.getStaffName());
		}
		return teachingStaffMap;
	}

	@Override
	public Map<String, String> getTermListForCurAcYear(
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		Map<String, String> map = academicTermListDao
				.selectTermForCurAcademicYear(userSessionDetails.getInstId(),
						userSessionDetails.getBranchId());
		return map;
	}

	@Override
	public Map<String, String> selectSubjectList(String stdGrpId,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		if (userSessionDetails.getUserMenuProfile().equals(
				ApplicationConstant.TEACHING_STAFF)) {
			return subjectListDAO.getSubList(stdGrpId,
					userSessionDetails.getLinkId(), userSessionDetails);
		} else {
			return subjectListDAO
					.getSubList(stdGrpId, null, userSessionDetails);
		}

	}

	@Override
	public Map<String, String> selectMenuProfileList(String profileGroup,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		return menuProfileListDAO.selectMenuProfileList(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), profileGroup);

	}
	
	@Override
	public Map<String, String> selectAdminUserList() throws NoDataFoundException {
		return menuProfileListDAO.selectAdminUserList();

	}

	@Override
	public Map<String, String> selectExamNameList(
			UserSessionDetails userSessionDetails, String studentGrpId)
			throws NoDataFoundException {
		List<MarkMaster> markMasters = examNameListDAO.selectExamNameList(
				userSessionDetails, studentGrpId);
		Map<String, String> examNameMap = new LinkedHashMap<String, String>();
		for (MarkMaster markMaster : markMasters) {
			examNameMap.put(markMaster.getExamId(), markMaster.getExamTest());
		}
		return examNameMap;
	}

	@Override
	public List<CourseSubLink> selectSubListForStudentGrp(String stdGrpId,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		if (userSessionDetails.getUserMenuProfile().equals(
				ApplicationConstant.TEACHING_STAFF)) {

			return subjectForSplDAO.getSubListForStudentGrp(stdGrpId,
					userSessionDetails.getLinkId(), userSessionDetails);
		} else {
			return subjectForSplDAO.getSubListForStudentGrp(stdGrpId, null,
					userSessionDetails);
		}

	}

	@Override
	public Map<String, String> selectAllSubjectList(
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		List<CourseSubLink> courseSubLinks = allSubjectListDAO
				.getAllSubjectList(userSessionDetails);
		Map<String, String> examNameMap = new LinkedHashMap<String, String>();
		for (CourseSubLink courseSubLink : courseSubLinks) {
			examNameMap.put(courseSubLink.getSubId(),
					courseSubLink.getSubType());
		}
		return examNameMap;
	}

	@Override
	public Map<String, String> getTermList(UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
		return termAndSecListDAO.selectTermList(userSessionDetails);
	}

	@Override
	public Map<String, String> getSectionList(
			UserSessionDetails userSessionDetails, String courseId,
			String trmId, final String stuGrpId) throws NoDataFoundException {
		Map<String, String> mapForSecList = new HashMap<String, String>();

		if ((stuGrpId != null)) {
			mapForSecList = termAndSecListDAO.getsecAndTrmListFromStuGrpId(
					userSessionDetails, stuGrpId);
		} else if ((stuGrpId == null)) {
			mapForSecList = termAndSecListDAO.selectSectionList(
					userSessionDetails, courseId, trmId);
		}
		return mapForSecList;
	}

	@Override
	public Map<String, String> getAcademicTermListForPresentAndFuture(
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		academicTermDetailsKey.setInstId(userSessionDetails.getInstId());
		academicTermDetailsKey.setBranchId(userSessionDetails.getBranchId());
		List<AcademicTermDetails> academicTermDetList = null;
		academicTermDetList = academicTermListDao
				.selectPresentAndFutureAcademicTerm(academicTermDetailsKey);

		Map<String, String> acTrmMap = new LinkedHashMap<String, String>();

		for (AcademicTermDetails academicTermDetails : academicTermDetList) {
			acTrmMap.put(academicTermDetails.getAcTerm(),
					academicTermDetails.getAcYear());
		}
		return acTrmMap;
	}

	@Override
	public Map<String, String> getPresentAcademicTermTag(
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		academicTermDetailsKey.setInstId(userSessionDetails.getInstId());
		academicTermDetailsKey.setBranchId(userSessionDetails.getBranchId());
		List<AcademicTermDetails> academicTermDetList = null;
		academicTermDetList = academicTermListDao
				.selectPresentAcademicTerm(academicTermDetailsKey);

		Map<String, String> acTrmMap = new LinkedHashMap<String, String>();

		for (AcademicTermDetails academicTermDetails : academicTermDetList) {
			acTrmMap.put(academicTermDetails.getAcTerm(),
					academicTermDetails.getAcYear());
		}
		return acTrmMap;
	}

	@Override
	public Map<String, String> getDepartementList(
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		return staffDepartmentListDAO
				.getStaffDepartmentList(userSessionDetails);
	}

	@Override
	public Map<String, String> getStGroupModelAttr(
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		List<StudentGroupMaster> studGrpMasters = null;

		logger.debug("User Menu Profile  :"
				+ userSessionDetails.getUserMenuProfile());
		if (userSessionDetails.getUserMenuProfile().equals(
				ApplicationConstant.TEACHING_STAFF)) {

			ClassTeacherAllotmentKey classTeacherAllotmentKey = new ClassTeacherAllotmentKey();
			classTeacherAllotmentKey.setInstId(userSessionDetails.getInstId());
			classTeacherAllotmentKey.setBranchId(userSessionDetails
					.getBranchId());
			classTeacherAllotmentKey.setStaffId(userSessionDetails.getLinkId());
			classTeacherAllotmentKey.setAcTerm(userSessionDetails
					.getCurrAcTerm());
			try {
				ClassTeacherAllotment classTeacherAllotment = classTeacherAllotmentDao
						.selectStaff(classTeacherAllotmentKey);
				studGrpMasters = studentGroupListTag
						.selectStudentGroupListForClassTeacher(
								userSessionDetails,
								classTeacherAllotment.getStGroup(),
								classTeacherAllotment.getAcTerm());
			} catch (NoDataFoundException e) {

			}

		} else {
			studGrpMasters = studentGroupListTag
					.selectStudentGroupList(userSessionDetails);

		}
		Map<String, String> stGroupMap = new LinkedHashMap<String, String>();
		if (studGrpMasters != null) {
			for (StudentGroupMaster studentGroupMaster : studGrpMasters) {
				stGroupMap.put(studentGroupMaster.getStudentGrpId(),
						studentGroupMaster.getStudentGrp());
			}
		}

		return stGroupMap;
	}

	@Override
	public Map<String, String> getStudentAdmisNo(
			UserSessionDetails userSessionDetails, String stGroup)
			throws NoDataFoundException {

		return studentListDAO.selectStudAdmisList(userSessionDetails, stGroup);
	}

	@Override
	public List<StudentGroupMaster> getStudentGroupListTagForDashBoard(
			SessionCache sessionCache, String acTerm)
			throws NoDataFoundException {
		List<StudentGroupMaster> studGrpMasters = null;
		if ((acTerm == null) || (acTerm == "")) {
			acTerm = sessionCache.getUserSessionDetails().getCurrAcTerm();
		}
		logger.debug("User Menu Profile  :"
				+ sessionCache.getUserSessionDetails().getUserMenuProfile());		
		if (sessionCache.getUserSessionDetails().getUserMenuProfile()
				.equals(ApplicationConstant.TEACHING_STAFF)) {
			studGrpMasters = studentGroupListTag
					.selectStudentGroupListForStaffDashBoard(
							sessionCache.getUserSessionDetails(), acTerm);

		} else {
			studGrpMasters = studentGroupListTag
					.selectStudentGroupList(sessionCache
							.getUserSessionDetails());
		}
			
		return studGrpMasters;
	}

	@Override
	public List<CourseSubLink> selectSubListForStudentGrpAnalytics(
			ApplicationCache applicationCache, String stdGrpId,
			UserSessionDetails userSessionDetails) throws NoDataFoundException,
			PropertyNotFoundException {
		List<CourseSubLink> courseSubLink = null;
		Map<String, String> subListForStaff = new LinkedHashMap<String, String>();
		String mentorAppl = propertyManagementUtil.getPropertyValue(
				applicationCache, userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(),
				PropertyManagementConstant.MENTOR_APPLICABLE).toString();
		if (userSessionDetails.getUserMenuProfile().equals(
				ApplicationConstant.TEACHING_STAFF)) {

			ClassTeacherAllotmentKey classTeacherAllotmentKey = new ClassTeacherAllotmentKey();
			classTeacherAllotmentKey.setInstId(userSessionDetails.getInstId());
			classTeacherAllotmentKey.setBranchId(userSessionDetails
					.getBranchId());
			classTeacherAllotmentKey.setStaffId(userSessionDetails.getLinkId());
			classTeacherAllotmentKey.setAcTerm(userSessionDetails
					.getCurrAcTerm());
			ClassTeacherAllotment classTeacherAllotment = classTeacherAllotmentDao
					.selectStudentBatchForStaff(classTeacherAllotmentKey,
							stdGrpId);			
			if (classTeacherAllotment != null) {
				courseSubLink = subjectForSplDAO.getSubListForStudentGrp(
						stdGrpId, null, userSessionDetails);
				for (int i = 0; i < courseSubLink.size(); i++) {
					courseSubLink.get(i).setMarkGrade("All");
					if (mentorAppl
							.equals(ApplicationConstant.MENTOR_IS_APPLICABLE)) {
						courseSubLink.get(i).setBranchId(
								classTeacherAllotment.getStBatchId());
					}
				}
			} else {

				courseSubLink = subjectForSplDAO.getSubListForStudentGrp(
						stdGrpId, userSessionDetails.getLinkId(),
						userSessionDetails);
			}

		}
		return courseSubLink;
	}

	@Override
	public Map<String, String> selectFeeCategoryList(
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		Map<String, String> map = feeCategoryListDao
				.selectFeeCategoryList(userSessionDetails);
		return map;
	}

	@Override
	public List<StudentGroupMaster> getStudentGroupListTag(
			SessionCache sessionCache) throws NoDataFoundException {
		List<StudentGroupMaster> studGrpMasters = null;	

		
			studGrpMasters = studentGroupListTag
					.selectStudentGroupList(sessionCache
							.getUserSessionDetails());
					
		return studGrpMasters;
	}

	
	@Override
	public Map<String, String> getPickupPoints(
			SessionCache sessionCache) throws NoDataFoundException {
		Map<String, String> pickupPoints = null;	

		
		pickupPoints = transportListDAO
					.selectPickupPoint(sessionCache
							.getUserSessionDetails());
					
		return pickupPoints;
	}

}
