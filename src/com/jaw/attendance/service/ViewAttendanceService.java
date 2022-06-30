package com.jaw.attendance.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jaw.attendance.controller.ViewAttendanceListVO;
import com.jaw.attendance.controller.ViewAttendanceMasterVO;
import com.jaw.attendance.dao.IViewAttendanceListDAO;
import com.jaw.attendance.dao.ViewAttendanceList;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.AttendanceUtil;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.ParentSession;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.framework.sessCache.UserSessionDetails;

//ViewAttendance Details Service Class
@Service
public class ViewAttendanceService implements IViewAttendanceService {
	// Logging
	Logger logger = Logger.getLogger(ViewAttendanceService.class);

	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	AttendanceUtil attendanceUtil;
	@Autowired
	IViewAttendanceListDAO ViewAttendanceListDAO;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	CommonCodeUtil commonCodeUtil;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;

	@Override
	public void selectViewAttendanceData(
			ViewAttendanceMasterVO attendanceMasterVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache,StudentSession studentSession,ParentSession parentSession) throws NoDataFoundException, PropertyNotFoundException {
		logger.debug("vo object :" + attendanceMasterVO.toString());
		ViewAttendanceList ViewAttendanceList = new ViewAttendanceList();
		commonBusiness.changeObject(ViewAttendanceList,
				attendanceMasterVO.getViewAttendanceSearchVO());
		ViewAttendanceList.setInstId(userSessionDetails.getInstId());
		ViewAttendanceList.setBranchId(userSessionDetails.getBranchId());
		ViewAttendanceList.setAcademicTerm(userSessionDetails.getCurrAcTerm());
		if ((attendanceMasterVO.getViewAttendanceSearchVO()
				.getStudentGroupId() == null)|| (attendanceMasterVO.getViewAttendanceSearchVO().getStudentGroupId()
								.equals(""))) {
			if ((userSessionDetails.getProfileGroup()
					.equals(ApplicationConstant.PG_STUDENT))) {
				System.out.println("Student session session :"+studentSession.getStuGrpId());
			ViewAttendanceList.setStudentGroupId(studentSession.getStuGrpId());
			} else if ((userSessionDetails.getProfileGroup()
					.equals(ApplicationConstant.PG_PARENT))) {
				StudentSession studentSession2=parentSession.getStudentSession().get(
						Integer.parseInt(parentSession.getSelectedStuIndex()));
				System.out.println("Parent's child session :"+studentSession2.getStuGrpId());
						ViewAttendanceList.setStudentGroupId(studentSession2.getStuGrpId());
			}
		}
		System.out.println("b4 admisno");
		if ((attendanceMasterVO.getViewAttendanceSearchVO()
				.getAdmissionNumber() == null)
				|| (attendanceMasterVO.getViewAttendanceSearchVO()
						.getAdmissionNumber().equals(""))) {
			
			if ((userSessionDetails.getProfileGroup()
					.equals(ApplicationConstant.PG_STUDENT))) {
				System.out.println("Student session session :"+studentSession);
				ViewAttendanceList.setAdmissionNumber(userSessionDetails
						.getLinkId());
			} else if ((userSessionDetails.getProfileGroup()
					.equals(ApplicationConstant.PG_PARENT))) {
				StudentSession studentSession2=parentSession.getStudentSession().get(
						Integer.parseInt(parentSession.getSelectedStuIndex()));
				System.out.println("Parent's child session :"+studentSession2.getStudentAdmisNo());
						ViewAttendanceList.setAdmissionNumber(studentSession2.getStudentAdmisNo());
			}
			
		}
		String isLanAttInclude= propertyManagementUtil.getPropertyValue(applicationCache, userSessionDetails.getInstId(), 
				userSessionDetails.getBranchId(), PropertyManagementConstant.INC_LAB_ATT);
		List<ViewAttendanceList> viewAttendanceLists = ViewAttendanceListDAO
				.getViewAttendanceList(ViewAttendanceList,isLanAttInclude);
		Map<String, ViewAttendanceListVO> map = new LinkedHashMap<String, ViewAttendanceListVO>();
		System.out.println("In service :" + viewAttendanceLists.size());
		for (int i = 0; i < viewAttendanceLists.size(); i++) {
			ViewAttendanceList ViewAttendanceList2 = viewAttendanceLists.get(i);
			ViewAttendanceListVO viewAttendanceListVO = new ViewAttendanceListVO();
			commonBusiness.changeObject(viewAttendanceListVO,
					ViewAttendanceList2);
			viewAttendanceListVO.setRowid(i);
			String key = viewAttendanceListVO.getDate();
			ViewAttendanceListVO value = viewAttendanceListVO;
			map.put(key, value);
		}
		logger.debug("Map size :" + map.size());
		attendanceMasterVO.setViewAttendanceListVOs(map);

	}

	@Override
	public void selectSubjectAttendance(ApplicationCache applicationCache,
			ViewAttendanceMasterVO attendanceMasterVO,
			UserSessionDetails userSessionDetails,StudentSession studentSession,ParentSession parentSession) throws NoDataFoundException {
		logger.debug("vo object :" + attendanceMasterVO.getViewAttendanceSearchVO().toString());
		ViewAttendanceList viewAttendanceList = new ViewAttendanceList();
		commonBusiness.changeObject(viewAttendanceList,
				attendanceMasterVO.getViewAttendanceSearchVO());
		viewAttendanceList.setInstId(userSessionDetails.getInstId());
		viewAttendanceList.setBranchId(userSessionDetails.getBranchId());
		viewAttendanceList.setAcademicTerm(userSessionDetails.getCurrAcTerm());
		if ((attendanceMasterVO.getViewAttendanceSearchVO()
				.getStudentGroupId() == null)|| (attendanceMasterVO.getViewAttendanceSearchVO().getStudentGroupId()
								.equals(""))) {
			if ((userSessionDetails.getProfileGroup()
					.equals(ApplicationConstant.PG_STUDENT))) {
				System.out.println("Student session session :"+studentSession.getStuGrpId());
				viewAttendanceList.setStudentGroupId(studentSession.getStuGrpId());
			} else if ((userSessionDetails.getProfileGroup()
					.equals(ApplicationConstant.PG_PARENT))) {
				StudentSession studentSession2=parentSession.getStudentSession().get(
						Integer.parseInt(parentSession.getSelectedStuIndex()));
				System.out.println("Parent's child session :"+studentSession2.getStuGrpId());
				viewAttendanceList.setStudentGroupId(studentSession2.getStuGrpId());
			}
		}
		System.out.println("b4 admisno");
		if ((attendanceMasterVO.getViewAttendanceSearchVO()
				.getAdmissionNumber() == null)
				|| (attendanceMasterVO.getViewAttendanceSearchVO()
						.getAdmissionNumber().equals(""))) {
			
			if ((userSessionDetails.getProfileGroup()
					.equals(ApplicationConstant.PG_STUDENT))) {
				System.out.println("Student session session :"+studentSession);
				viewAttendanceList.setAdmissionNumber(userSessionDetails
						.getLinkId());
			} else if ((userSessionDetails.getProfileGroup()
					.equals(ApplicationConstant.PG_PARENT))) {
				StudentSession studentSession2=parentSession.getStudentSession().get(
						Integer.parseInt(parentSession.getSelectedStuIndex()));
				System.out.println("Parent's child session :"+studentSession2.getStudentAdmisNo());
				viewAttendanceList.setAdmissionNumber(studentSession2.getStudentAdmisNo());
			}
			
		}
		List<ViewAttendanceList> viewAttendanceLists = ViewAttendanceListDAO
				.getSubjectWiseAttendance(viewAttendanceList);
		List<ViewAttendanceListVO> list = new ArrayList<ViewAttendanceListVO>();
		System.out.println("In service :" + viewAttendanceLists.size());
		for (int i = 0; i < viewAttendanceLists.size(); i++) {
			ViewAttendanceList ViewAttendanceList2 = viewAttendanceLists.get(i);
			ViewAttendanceListVO viewAttendanceListVO = new ViewAttendanceListVO();
			commonBusiness.changeObject(viewAttendanceListVO,
					ViewAttendanceList2);

			String attDesc = commonCodeUtil.getDescriptionByTypeAndCode(
					applicationCache, "ATTLV",
					viewAttendanceListVO.getIsAbsent(),
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId());
			viewAttendanceListVO.setRowid(i);
			viewAttendanceListVO.setIsAbsentDesc(attDesc);
			list.add(viewAttendanceListVO);
		}
		logger.debug("Map size :" + list.size());
		attendanceMasterVO.setSubjectAttendanceList(list);

	}

	@Override
	public void getMonth(ViewAttendanceMasterVO attendanceMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		Map<String, String> dates = attendanceUtil.datesBetween(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(),
				userSessionDetails.getCurrAcTerm());
		attendanceMasterVO.setMonthList(dates);

	}

}
