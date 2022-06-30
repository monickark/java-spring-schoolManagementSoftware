package com.jaw.core.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.core.controller.CourseClassesMasterVO;
import com.jaw.core.controller.CourseClassesSearchVO;
import com.jaw.core.controller.CourseClassesVO;
import com.jaw.core.dao.CourseClasses;
import com.jaw.core.dao.CourseClassesKey;
import com.jaw.core.dao.CourseClassesList;
import com.jaw.core.dao.ICourseClassesDAO;
import com.jaw.core.dao.ICourseClassesListDAO;
import com.jaw.core.dao.ITeacherSubjectLinkListDAO;
import com.jaw.core.dao.ITermListBasedOnCourseIdDAO;
import com.jaw.core.dao.TeacherSubjectLink;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

//course classes Details Service Class
@Service
public class CourseClassesService implements ICourseClassesService {
	// Logging
	Logger logger = Logger.getLogger(CourseClassesService.class);

	@Autowired
	ICourseClassesListDAO courseClassesListDAO;
	@Autowired
	ITeacherSubjectLinkListDAO teacherSubjectLinkListDAO;
	@Autowired
	ICourseClassesDAO courseClassesDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	DoAudit doAudit;
	@Autowired
	CommonCodeUtil commonCodeUtil;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Autowired
	ITermListBasedOnCourseIdDAO termListBasedOnCourseIdDAO;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertCourseClassList(UserSessionDetails userSessionDetails,
			CourseClassesSearchVO classesSearchVO,
			List<CourseClassesVO> courseClassesVOs, String[] selectedRowIds,
			String[] staffIds, String[] clsType, String[] labSessNo,
			String[] classNo, String[] labBatch, String[] duration,
			ApplicationCache applicationCache, String[] hiddenRowId)
			throws NoDataFoundException, DuplicateEntryException,
			BatchUpdateFailedException, DatabaseException {

		List<CourseClasses> batchup = new ArrayList<CourseClasses>();
		System.out.println("ClasssearchVo :" + courseClassesVOs + " length :"
				+ courseClassesVOs.size());

		if (staffIds != null) {
			System.out.println(Arrays.toString(staffIds));
			System.out.println(Arrays.toString(hiddenRowId));
			System.out.println(Arrays.toString(selectedRowIds));
			List<String> list = new ArrayList<String>(Arrays.asList(staffIds));
			list.removeAll(Arrays.asList(""));
			staffIds = list.toArray(staffIds);
		}

		for (int i = 0; i < selectedRowIds.length; i++) {
			int rowId = Integer.parseInt(selectedRowIds[i]);
			int position = Arrays.binarySearch(hiddenRowId, selectedRowIds[i]);
			System.out.println("Position :" + position);

			String staffId = staffIds[i];
			logger.debug("Slected row id to process:" + rowId);
			CourseClasses courseClasses = new CourseClasses();
			commonBusiness.changeObject(courseClasses,
					courseClassesVOs.get(rowId));
			courseClasses.setCcId(AlphaSequenceUtil.generateAlphaSequence(
					ApplicationConstant.STRING_COURSE_CLASSES_LINK_SEQ,
					simpleIdGenerator.getNextId(
							SequenceConstant.COURSE_CLASSES_LINKING_ID, 1)));
			courseClasses.setStudentGrpId(classesSearchVO.getStudentGrpId());
			System.out.println("In service acterm :"
					+ userSessionDetails.getInstId());
			courseClasses.setInstId(userSessionDetails.getInstId());
			courseClasses.setBranchId(userSessionDetails.getBranchId());
			courseClasses.setAcTerm(classesSearchVO.getAcTerm());
			courseClasses.setDuration(duration[i]);
			courseClasses.setDelFlag("N");
			courseClasses.setrCreId(userSessionDetails.getUserId());
			courseClasses.setrModId(userSessionDetails.getUserId());
			courseClasses.setStaffId(staffId);
			courseClasses.setDbTs(1);

			System.out.println("Lab Session :" + labSessNo.length);
			System.out.println("Lab batch :" + labBatch.length);
			System.out.println("classNo :" + classNo.length);
			System.out.println("clsType :" + clsType.length);
			System.out.println("hidden row id :" + hiddenRowId.length);
			System.out.println("i :" + position);
			if (courseClasses.getClsType() == ApplicationConstant.THEORY) {
				courseClasses.setLabBatch("NA");
			} else {

				courseClasses.setLabBatch(labBatch[position]);
			}
			courseClasses.setNoOfClassesPerWeek(classNo[position]);
			courseClasses.setNoOfLabClassesPerWeek(labSessNo[position]);
			courseClasses.setClsType(clsType[position]);

			courseClasses.setSaNo("" + position + "");
			System.out.println("course classes batch records to add :"
					+ courseClasses.toString());
			batchup.add(courseClasses);
		}

		courseClassesListDAO.insertCourseClassesList(batchup);
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.COURSE_CLASSES_BATCH_CREATE, "");

	}

	@Override
	public void insertCourseClassList(UserSessionDetails userSessionDetails,
			CourseClassesSearchVO classesSearchVO,
			CourseClassesVO courseClassesVO) throws NoDataFoundException,
			DuplicateEntryException, BatchUpdateFailedException,
			DatabaseException, TableNotSpecifiedForAuditException {
		System.out.println("course class search vo :"
				+ classesSearchVO.toString());
		System.out.println("course class  vo :" + courseClassesVO.toString());

		int count = courseClassesDAO.checkRecordExist(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), classesSearchVO.getAcTerm(),
				classesSearchVO.getStudentGrpId(),
				courseClassesVO.getLabBatch(), courseClassesVO.getCrslId(),
				courseClassesVO.getStaffId());
		System.out.println("Count value :" + count);
		if (count == 0) {
			CourseClasses courseClasses = new CourseClasses();
			commonBusiness.changeObject(courseClasses, courseClassesVO);
			courseClasses.setCcId(AlphaSequenceUtil.generateAlphaSequence(
					ApplicationConstant.STRING_COURSE_CLASSES_LINK_SEQ,
					simpleIdGenerator.getNextId(
							SequenceConstant.COURSE_CLASSES_LINKING_ID, 1)));
			courseClasses.setStudentGrpId(classesSearchVO.getStudentGrpId());
			courseClasses.setInstId(userSessionDetails.getInstId());
			courseClasses.setBranchId(userSessionDetails.getBranchId());
			courseClasses.setDuration(courseClassesVO.getDuration());
			courseClasses.setAcTerm(classesSearchVO.getAcTerm());
			courseClasses.setDelFlag("N");
			courseClasses.setrCreId(userSessionDetails.getUserId());
			courseClasses.setrModId(userSessionDetails.getUserId());
			courseClasses.setDbTs(1);
			courseClasses.setSaNo("1");
			System.out.println("insert course classes :"
					+ courseClasses.toString());
			courseClassesDAO.insertCourseClassesRec(courseClasses);
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.COURSE_CLASSES_CREATE, "");
		} else {
			throw new DuplicateEntryException();
		}
	}

	@Override
	public void selectCourseClassesData(
			CourseClassesMasterVO courseClassesMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		CourseClassesList courseClassesList = new CourseClassesList();
		commonBusiness.changeObject(courseClassesList,
				courseClassesMasterVO.getCourseClassesSearchVO());
		courseClassesList.setInstId(userSessionDetails.getInstId());
		courseClassesList.setBranchId(userSessionDetails.getBranchId());
		List<CourseClassesList> courseClassess = courseClassesListDAO
				.getCourseSubjectLinkList(courseClassesList);
		List<CourseClassesVO> courseClassesVOs = new ArrayList<CourseClassesVO>();

		for (int i = 0; i < courseClassess.size(); i++) {
			CourseClassesList courseClasses = courseClassess.get(i);
			CourseClassesVO courseClassesVO = new CourseClassesVO();
			System.out.println("In b4 service :" + courseClasses.getStaffId());
			System.out.println("Dao course class list :"
					+ courseClasses.toString());
			commonBusiness.changeObject(courseClassesVO, courseClasses);
			courseClassesVO.setRowid(i);
			if (courseClassesVO.getStaffName() == null) {
				System.out.println("Inside null getStaffId");
				courseClassesVO.setStaffName("");
			}
			if (courseClassesVO.getNoOfClassesPerWeek() == null) {
				System.out.println("Inside null getLabBatch");
				courseClassesVO.setNoOfClassesPerWeek("0");
			}
			if (courseClassesVO.getNoOfLabClassesPerWeek() == null) {
				courseClassesVO.setNoOfLabClassesPerWeek("0");
				System.out.println("Inside null getClsType");
			}
			System.out.println("In after service :"
					+ courseClassesVO.getStaffId());
			System.out.println("B4 added to list :"
					+ courseClassesVO.toString());
			courseClassesVOs.add(courseClassesVO);
		}

		courseClassesMasterVO.setCourseClassesVOs(courseClassesVOs);

	}

	@Override
	public void updateCourseClasses(CourseClassesVO courseClassesVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws UpdateFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		logger.debug("inside update course classes Details method");
		logger.debug("Course classes vo :" + courseClassesVO.toString());

		CourseClasses courseClasses = new CourseClasses();
		CourseClassesKey courseClassesKey = new CourseClassesKey();
		courseClassesKey.setInstId(userSessionDetails.getInstId());
		courseClassesKey.setBranchId(userSessionDetails.getBranchId());
		courseClassesKey.setCcId(courseClassesVO.getCcId());
		logger.debug("Course classes key :" + courseClassesKey.toString());
		courseClasses = courseClassesDAO
				.selectCourseClassesRec(courseClassesKey);
		courseClassesKey.setDbTs(courseClasses.getDbTs());
		String tableKey = courseClassesKey.toStringForDBKey();
		String oldRecord = courseClasses.stringForDbAudit();
		courseClasses.setLabBatch(courseClassesVO.getLabBatch());
		courseClasses.setClsType(courseClassesVO.getClsType());
		courseClasses.setNoOfClassesPerWeek(courseClassesVO
				.getNoOfClassesPerWeek());
		courseClasses.setNoOfLabClassesPerWeek(courseClassesVO
				.getNoOfLabClassesPerWeek());
		courseClasses.setDuration(courseClassesVO.getDuration());
		courseClasses.setStaffId(courseClassesVO.getStaffId());
		courseClasses.setrModId(userSessionDetails.getUserId());

		System.out.println("course classes b4 update :"
				+ courseClasses.toString());
		courseClassesDAO
				.updateCourseClassesRec(courseClasses, courseClassesKey);
		courseClasses = courseClassesDAO
				.selectCourseClassesRec(courseClassesKey);
		String newRecord = courseClasses.stringForDbAudit();
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.COURSE_CLASSES_UPDATE, "");
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.COURSE_CLASSES, tableKey, oldRecord,
				AuditConstant.TYPE_OF_OPER_UPDATE, newRecord, "");
	}

	@Override
	public void deleteCourseClassesDAORec(CourseClassesVO courseClassesVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {
		logger.debug("inside delete course classes Details method");

		CourseClasses courseClasses = new CourseClasses();
		CourseClassesKey courseClassesKey = new CourseClassesKey();
		// map the UIObject to Pojo

		commonBusiness.changeObject(courseClassesKey, courseClassesVO);
		courseClassesKey.setInstId(userSessionDetails.getInstId());
		courseClassesKey.setBranchId(userSessionDetails.getBranchId());
		courseClassesKey.setCcId(courseClassesVO.getCcId());
		courseClasses = courseClassesDAO
				.selectCourseClassesRec(courseClassesKey);

		String tableKey = courseClassesKey.toStringForDBKey();
		String oldRecord = courseClasses.stringForDbAudit();
		courseClasses.setrModId(userSessionDetails.getUserId());
		courseClassesDAO
				.deleteCourseClassesRec(courseClasses, courseClassesKey);
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.COURSE_CLASSES_DELETE, "");
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.COURSE_CLASSES, tableKey, oldRecord,
				AuditConstant.TYPE_OF_OPER_DELETE, "", "");
	}

	@Override
	public Map<String, String> selectStaffList(String subId,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		logger.debug("inside update course classes Details method");

		TeacherSubjectLink teacherSubjectLink = new TeacherSubjectLink();
		System.out.println("sub id in service :" + subId);
		teacherSubjectLink.setInstId(userSessionDetails.getInstId());
		teacherSubjectLink.setBranchId(userSessionDetails.getBranchId());
		teacherSubjectLink.setSubId(subId);
		return teacherSubjectLinkListDAO.selectStaffList(teacherSubjectLink);

	}

}
