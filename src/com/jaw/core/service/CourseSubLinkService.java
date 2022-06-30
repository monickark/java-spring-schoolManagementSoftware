package com.jaw.core.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.jaw.common.exceptions.SubjectOrderAlreadyExistExecption;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.core.controller.CourseSubLinkListVO;
import com.jaw.core.controller.CourseSubLinkMasterVO;
import com.jaw.core.controller.CourseSubLinkVO;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.core.dao.CourseSubLinkList;
import com.jaw.core.dao.CourseSubLinkKey;
import com.jaw.core.dao.ICourseSubLinkDAO;
import com.jaw.core.dao.ICourseSubLinkListDAO;
import com.jaw.core.dao.IHolidayMaintenanceDAO;
import com.jaw.core.dao.ISubjectMasterListDAO;
import com.jaw.core.dao.ITermListBasedOnCourseIdDAO;
import com.jaw.core.dao.SubjectMasterKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

//Academic Term Details Service Class
@Service
public class CourseSubLinkService implements ICourseSubLinkService {
	// Logging
	Logger logger = Logger.getLogger(CourseSubLinkService.class);

	@Autowired
	ICourseSubLinkDAO courseSubLinkDAO;
	@Autowired
	ICourseSubLinkListDAO courseSubLinkListDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	IHolidayMaintenanceDAO holidayMaintenanceDAO;
	@Autowired
	CommonCodeUtil commonCodeUtil;
	@Autowired
	ISubjectMasterListDAO subjectMasterListDAO;
	@Autowired
	DoAudit doAudit;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Autowired
	ITermListBasedOnCourseIdDAO termListBasedOnCourseIdDAO;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertCouseSubjectLinkRec(CourseSubLinkVO courseSubLinkVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException, SubjectOrderAlreadyExistExecption {

		logger.debug("inside insert Academic Term Details method");

		CourseSubLink courseSubLink = new CourseSubLink();
		CourseSubLinkKey CourseSubLinkDAOKey = new CourseSubLinkKey();

		// map the UIObject to Pojo
		commonBusiness.changeObject(courseSubLink, courseSubLinkVO);
		int count1 = courseSubLinkDAO.checkRecordOrderExist(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(),
				courseSubLinkVO.getCourseMasterId(),
				courseSubLinkVO.getTermId(),
				courseSubLinkVO.getReportCardOrder());
		int count = courseSubLinkDAO.checkRecordExist(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(),
				courseSubLinkVO.getCourseMasterId(),
				courseSubLinkVO.getTermId(), courseSubLinkVO.getSubId(),
				courseSubLink.getSubType());
		System.out.println("Count value :" + count + "    count1 value:"
				+ count1);
		if ((count == 0) && (count1 == 0)) {
			courseSubLink.setDbTs(1);
			courseSubLink.setInstId(userSessionDetails.getInstId());
			courseSubLink.setBranchId(userSessionDetails.getBranchId());
			courseSubLink.setCrslId(AlphaSequenceUtil.generateAlphaSequence(
					ApplicationConstant.STRING_COURSE_SUBJECT_LINK_SEQ,
					simpleIdGenerator.getNextId(
							SequenceConstant.COURSE_SUBJECT_LINKING_ID, 1)));
			courseSubLink.setrCreId(userSessionDetails.getUserId());
			courseSubLink.setrModId(userSessionDetails.getUserId());
			courseSubLink.setDelFlag("N");
			CourseSubLinkDAOKey.setInstId(userSessionDetails.getInstId());
			CourseSubLinkDAOKey.setBranchId(userSessionDetails.getBranchId());

			courseSubLinkDAO.insertCourseSubLinkRec(courseSubLink);
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.COURSE_SUBJECT_CREATE, "");
		} else if(count==0){
			throw new DuplicateEntryException();
		} else{
			throw new SubjectOrderAlreadyExistExecption();
			
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void selectCourseSubjectLinkingData(
			CourseSubLinkMasterVO courseSubLinkMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		CourseSubLinkKey courseSubLinkKey = new CourseSubLinkKey();
		commonBusiness.changeObject(courseSubLinkKey,
				courseSubLinkMasterVO.getCourseSubLinkSearchVO());
		courseSubLinkKey.setInstId(userSessionDetails.getInstId());
		courseSubLinkKey.setBranchId(userSessionDetails.getBranchId());
		List<CourseSubLinkList> courseSubLinks = courseSubLinkListDAO
				.getCourseSubjectLinkList(courseSubLinkKey);
		List<CourseSubLinkListVO> courseSubLinkVOs = new ArrayList<CourseSubLinkListVO>();

		for (int i = 0; i < courseSubLinks.size(); i++) {
			CourseSubLinkList courseSubLink = courseSubLinks.get(i);
			CourseSubLinkListVO courseSubLinkVO = new CourseSubLinkListVO();
			commonBusiness.changeObject(courseSubLinkVO, courseSubLink);
			courseSubLinkVO.setRowId(i);
			courseSubLinkVOs.add(courseSubLinkVO);
		}
		courseSubLinkMasterVO.setCourseSubLinkVOs(courseSubLinkVOs);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateCourseSubjectLinking(CourseSubLinkVO courseSubLinkVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws UpdateFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {
		logger.debug("inside update Academic Term Details method");

		CourseSubLinkKey courseSubLinkKey = new CourseSubLinkKey();
		commonBusiness.changeObject(courseSubLinkKey, courseSubLinkVO);

		System.out.println("CRSL b4 update 2:" + courseSubLinkVO.toString());
		courseSubLinkKey.setInstId(userSessionDetails.getInstId());
		courseSubLinkKey.setBranchId(userSessionDetails.getBranchId());
		CourseSubLink courseSubLink = courseSubLinkDAO
				.selectCourseSubLinkRec(courseSubLinkKey);
		courseSubLinkKey.setDbTs(courseSubLink.getDbTs());
		String tableKey = courseSubLinkKey.toStringForDBKey();
		String oldRecord = courseSubLink.stringForDbAudit();
		System.out.println("CRSL b4 update1 :" + courseSubLink.toString());
		System.out.println("CRSL b4 update1 :" + courseSubLinkVO.toString());
		commonBusiness.changeObject(courseSubLink, courseSubLinkVO);
		courseSubLink.setInstId(userSessionDetails.getInstId());
		courseSubLink.setBranchId(userSessionDetails.getBranchId());
		courseSubLink.setRequiresTeacher(courseSubLinkVO.getRequiresTeacher());
		courseSubLink.setReportCardOrder(courseSubLinkVO.getReportCardOrder());
		System.out.println("CRSL b4 update :" + courseSubLink.toString());
		System.out.println("CRSL b4 update :" + courseSubLinkVO.toString());
		courseSubLinkDAO
				.updateCourseSubLinkRec(courseSubLink, courseSubLinkKey);
		courseSubLinkKey.setDbTs(courseSubLink.getDbTs() + 1);
		CourseSubLink courseSubLink2 = courseSubLinkDAO
				.selectCourseSubLinkRec(courseSubLinkKey);
		String newRecord = courseSubLink2.stringForDbAudit();
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.COURSE_SUBJECT_UPDATE, "");
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.COURSE_SUBJECT, tableKey, oldRecord,
				AuditConstant.TYPE_OF_OPER_UPDATE, newRecord, "");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteCourseSubLinkDAORec(CourseSubLinkListVO courseSubLinkVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws NoDataFoundException,
			DeleteFailedException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {
		logger.debug("inside update Academic Term Details method");

		CourseSubLinkKey courseSubLinkKey = new CourseSubLinkKey();
		// map the UIObject to Pojo

		commonBusiness.changeObject(courseSubLinkKey, courseSubLinkVO);
		courseSubLinkKey.setInstId(userSessionDetails.getInstId());
		courseSubLinkKey.setBranchId(userSessionDetails.getBranchId());
		CourseSubLink courseSubLink = courseSubLinkDAO
				.selectCourseSubLinkRec(courseSubLinkKey);
		String tableKey = courseSubLinkKey.toStringForDBKey();
		String oldRecord = courseSubLink.stringForDbAudit();
		courseSubLinkKey.setDbTs(courseSubLink.getDbTs());
		courseSubLinkDAO.deleteCourseSubLinkRec(userSessionDetails.getUserId(),
				courseSubLinkKey);
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.COURSE_SUBJECT_DELETE, "");
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.COURSE_SUBJECT, tableKey, oldRecord,
				AuditConstant.TYPE_OF_OPER_DELETE, "", "");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void copyListToVO(CourseSubLinkMasterVO courseSubLinkMasterVO) {
		logger.debug("inside update Academic Term Details method");

		commonBusiness.changeObject(courseSubLinkMasterVO.getCourseSubLinkVO(),
				courseSubLinkMasterVO.getCourseSubLinkListVO());
		System.out.println("courseSubLinkMasterVO.getCourseSubLinkListVO() :"
				+ courseSubLinkMasterVO.getCourseSubLinkListVO().toString());
		System.out.println("courseSubLinkMasterVO.getCourseSubLinkVO() :"
				+ courseSubLinkMasterVO.getCourseSubLinkVO().toString());
	}

	@Override
	public Map<String, String> getTermDetailsBasedOnCourseId(String courseId,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		return termListBasedOnCourseIdDAO.getTermListBasedOnCourseId(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), courseId);

	}

	@Override
	public Map<String, String> getSubjectList(
			ApplicationCache applicationCache,
			UserSessionDetails userSessionDetails, String subjectType, String courseId)
			throws NoDataFoundException {
		SubjectMasterKey subjectMasterKey = new SubjectMasterKey();
		subjectMasterKey.setInstId(userSessionDetails.getInstId());
		subjectMasterKey.setBranchId(userSessionDetails.getBranchId());
		subjectMasterKey.setSubType(subjectType);
		Map<String, String> map = subjectMasterListDAO
				.getSubjectBasedOnSubTypeAndCourseId(subjectMasterKey,courseId);

		return map;
	}

}
