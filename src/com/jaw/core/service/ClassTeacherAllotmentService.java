package com.jaw.core.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.core.controller.AllottedClassTeachersVO;
import com.jaw.core.controller.ClassTeacherAllotmentVO;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.AllottedClassTeachers;
import com.jaw.core.dao.ClassTeacherAllotment;
import com.jaw.core.dao.ClassTeacherAllotmentKey;
import com.jaw.core.dao.IAcademicTermDetailsDAO;
import com.jaw.core.dao.IClassTeacherAllotmentDao;
import com.jaw.core.dao.IClassTeacherAllotmentListDao;
import com.jaw.core.dao.IStandardSectionListDao;
import com.jaw.core.dao.IStudentGroupMasterListDAO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.controller.StaffMasterVo;
import com.jaw.staff.dao.IStaffMasterListDao;
import com.jaw.staff.dao.StaffMaster;

@Service
public class ClassTeacherAllotmentService implements
		IClassTeacherAllotmentService {

	Logger logger = Logger.getLogger(ClassTeacherAllotmentService.class);

	@Autowired
	IClassTeacherAllotmentDao classTeacherCrudDao;
	@Autowired
	IClassTeacherAllotmentListDao classTeacherAllotmentListDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IStudentGroupMasterListDAO studentGroupMasterListDAO;
	@Autowired
	IStaffMasterListDao staffMasterListDao;
	@Autowired
	IStandardSectionListDao standardSectionDao;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	DoAudit doAudit;
	@Autowired
	IAcademicTermDetailsDAO academicTermDetailsDAO;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;

	@Override
	public void getAcTemStatus(
			ClassTeacherAllotmentVO classTeacherAllotmentVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {

		logger.debug("inside getting full course list method in ClassTeacherAllotmentService");
		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		academicTermDetailsKey.setInstId(userSessionDetails.getInstId());
		academicTermDetailsKey.setBranchId(userSessionDetails.getBranchId());
		academicTermDetailsKey.setAcTerm(classTeacherAllotmentVO.getAcTerm());
		AcademicTermDetails academicTermDetails = academicTermDetailsDAO
				.selectAcademicTermDetailsRec(academicTermDetailsKey);
		classTeacherAllotmentVO.setAcTermSts(academicTermDetails.getAcTermSts());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertClassTeacher(ClassTeacherAllotmentVO classTeacher,
			UserSessionDetails userSessionDetails) throws DatabaseException,
			DuplicateEntryException {

		logger.debug("inside insert method in ClassTeacherAllotmentService"
				+ classTeacher.toString());

		ClassTeacherAllotment setup = new ClassTeacherAllotment();

		setup.setDbTs(1);
		setup.setInstId(userSessionDetails.getInstId());
		setup.setBranchId(userSessionDetails.getBranchId());
		setup.setAcTerm(classTeacher.getAcTerm());
		setup.setStaffId(classTeacher.getStaffId());
		setup.setStGroup(classTeacher.getStGroup());
		setup.setCaSeqId(AlphaSequenceUtil.generateAlphaSequence(
				ApplicationConstant.STRING_CLS_TCHR_ALTMNT_SEQ,
				simpleIdGenerator
						.getNextId(SequenceConstant.CLS_TCHR_ALTMNT, 1)));
		setup.setStBatchId(classTeacher.getStBatchId());
		setup.setDelFlag("N");
		setup.setrCreId(userSessionDetails.getUserId());
		setup.setrModId(userSessionDetails.getUserId());
		if (classTeacher.getIsAuditRequired().equals("Y")) {
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.CLASS_TEACHER_ALLOTTED, "");
			classTeacher.setIsAuditRequired("N");
		}

		classTeacherCrudDao.insertStaff(setup);

	}

	@Override
	public List<StaffMasterVo> selectStaff(String academicYear,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {

		logger.debug("inside select method in ClassTeacherAllotmentService");

		List<StaffMaster> staffList = staffMasterListDao
				.getListForClassTeacherAllotment(
						userSessionDetails.getBranchId(),
						userSessionDetails.getInstId(), academicYear);

		List<StaffMasterVo> adminVOs = new ArrayList<StaffMasterVo>();

		for (StaffMaster staff : staffList) {

			StaffMasterVo std = new StaffMasterVo();
			commonBusiness.changeObject(std, staff);
			adminVOs.add(std);

		}

		return adminVOs;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteClassTeacher(ClassTeacherAllotmentVO classTeacher,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		logger.debug("inside delete method in ClassTeacherAllotmentService");
		ClassTeacherAllotmentKey classTeacherAllotmentKey = new ClassTeacherAllotmentKey();
		commonBusiness.changeObject(classTeacherAllotmentKey, classTeacher);
		classTeacherAllotmentKey.setInstId(userSessionDetails.getInstId());
		classTeacherAllotmentKey.setBranchId(userSessionDetails.getBranchId());
		ClassTeacherAllotment classTeacherAllotment = classTeacherCrudDao
				.selectStaff(classTeacherAllotmentKey);
		classTeacherAllotmentKey.setDbTs(classTeacherAllotment.getDbTs());
		classTeacherAllotmentKey.setCaSeqId(classTeacherAllotment.getCaSeqId());
		String oldrecord = classTeacherAllotment.stringForDbAudit();
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.CLASS_TEACHER_DELETED, "");
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.CLASS_TEACHER_ALLOTMENT,
				classTeacherAllotmentKey.toStringForDBKey(), oldrecord,
				AuditConstant.TYPE_OF_OPER_DELETE, "", "");
		classTeacherCrudDao
				.deleteClassTeacherAllotted(classTeacherAllotmentKey);
	}

	@Override
	public Map<String, String> getStudentGroupList(String academicYear,
			String courseId, UserSessionDetails userSessionDetails)
			throws NoDataFoundException {

		logger.debug("inside getting full course list method in ClassTeacherAllotmentService");

		Map<String, String> stGoupList = studentGroupMasterListDAO
				.getStGroupForClsTchrAltmnt(academicYear, courseId,
						userSessionDetails.getInstId(),
						userSessionDetails.getBranchId());

		return stGoupList;

	}

	@Override
	public Map<String, String> getClassTeachersList(
			ClassTeacherAllotmentVO classTeacherAllotmentVO,
			UserSessionDetails userSessionDetails) {

		logger.debug("inside getting full classteacher list method in ClassTeacherAllotmentService");

		Map<String, String> courseList = new HashMap<String, String>();
		List<AllottedClassTeachers> allottedClassTeachers2 = null;
		try {

			if (classTeacherAllotmentVO.getIsBatchInclude().equals("Y")) {
				allottedClassTeachers2 = classTeacherAllotmentListDao
						.getClassTeachersListForCollege(
								classTeacherAllotmentVO.getAcTerm(),
								classTeacherAllotmentVO.getCourseId(),
								userSessionDetails.getInstId(),
								userSessionDetails.getBranchId());
				for (AllottedClassTeachers allottedClassTeachers : allottedClassTeachers2) {
					String key = allottedClassTeachers.getStaffId();
					String key1 = allottedClassTeachers.getStGroupId();
					String key2 = allottedClassTeachers.getStBatchId();
					String value = allottedClassTeachers.getStaffName();
					String value1 = allottedClassTeachers.getStGroupName();
					String value2 = allottedClassTeachers.getStBatchName();
					courseList.put(key + "/" + key1 + "/" + key2, value + " - "
							+ value1 + " - " + value2);
				}
			} else if (classTeacherAllotmentVO.getIsBatchInclude().equals("N")) {

				allottedClassTeachers2 = classTeacherAllotmentListDao
						.getClassTeachersListForSchool(
								classTeacherAllotmentVO.getAcTerm(),
								classTeacherAllotmentVO.getCourseId(),
								userSessionDetails.getInstId(),
								userSessionDetails.getBranchId());
				for (AllottedClassTeachers allottedClassTeachers : allottedClassTeachers2) {
					String key = allottedClassTeachers.getStaffId();
					String key1 = allottedClassTeachers.getStGroupId();
					String value = allottedClassTeachers.getStaffName();
					String value1 = allottedClassTeachers.getStGroupName();
					courseList.put(key + "/" + key1, value + " - " + value1);
				}

			}

		} catch (NoDataFoundException e) {
			logger.debug("Class teacher not yet assigned");
		}

		return courseList;
	}

	@Override
	public Map<String, String> getStudentBatchList(final String stGroup,
			UserSessionDetails userSessionDetails, String acTerm)
			throws NoDataFoundException {
		return classTeacherAllotmentListDao.getStudentBatchList(stGroup,
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), acTerm);
	}

	@Override
	public List<AllottedClassTeachersVO> getOldClassTeacherList(
			ClassTeacherAllotmentVO classTeacherAllotmentVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {

		logger.debug("inside getting full classteacher list method in ClassTeacherAllotmentService");

		List<AllottedClassTeachers> allottedClassTeachers2 = null;
		List<AllottedClassTeachersVO> allottedClassTeachersVOs = new ArrayList<AllottedClassTeachersVO>();
		if (classTeacherAllotmentVO.getIsBatchInclude().equals("Y")) {
			allottedClassTeachers2 = classTeacherAllotmentListDao
					.getClassTeachersListForCollege(
							classTeacherAllotmentVO.getAcTerm(),
							classTeacherAllotmentVO.getCourseId(),
							userSessionDetails.getInstId(),
							userSessionDetails.getBranchId());

		} else if (classTeacherAllotmentVO.getIsBatchInclude().equals("N")) {

			allottedClassTeachers2 = classTeacherAllotmentListDao
					.getClassTeachersListForSchool(
							classTeacherAllotmentVO.getAcTerm(),
							classTeacherAllotmentVO.getCourseId(),
							userSessionDetails.getInstId(),
							userSessionDetails.getBranchId());

		}

		for (AllottedClassTeachers allottedClassTeachers : allottedClassTeachers2) {
			AllottedClassTeachersVO allottedClassTeachersVO = new AllottedClassTeachersVO();
			commonBusiness.changeObject(allottedClassTeachersVO,
					allottedClassTeachers);
			allottedClassTeachersVOs.add(allottedClassTeachersVO);
		}

		return allottedClassTeachersVOs;
	}

}
