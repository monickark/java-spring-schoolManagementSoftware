package com.jaw.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.jaw.core.controller.CourseTermLinkingMasterVO;
import com.jaw.core.controller.CourseTermLinkingVO;
import com.jaw.core.dao.CourseTermLinking;
import com.jaw.core.dao.CourseTermLinkingKey;
import com.jaw.core.dao.ICourseTermLinkingDAO;
import com.jaw.core.dao.ICourseTermLinkingListDAO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.dao.StudentGroupMasterKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
@Service
public class CourseTermLinkListService implements ICourseTermLinkListService {
	// Logging
	Logger logger = Logger.getLogger(CourseTermLinkListService.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	ICourseTermLinkingDAO courseTermLinkingDAO;
	@Autowired
	ICourseTermLinkingListDAO courseTermLinkingListDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public CourseTermLinkingMasterVO selectCourseTermLinkList(
			CourseTermLinkingMasterVO courseTermLinkingMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		
		CourseTermLinkingKey courseTermLinkKey = new CourseTermLinkingKey();
		courseTermLinkKey.setCourseMasterId(courseTermLinkingMasterVO
				.getCourseMasterId());
		courseTermLinkKey.setInstId(userSessionDetails.getInstId());
		courseTermLinkKey.setBranchId(userSessionDetails.getBranchId());
		List<CourseTermLinking> courseTermLinkingList = courseTermLinkingListDAO.getCourseTermLinkingList(courseTermLinkKey);
		
		List<CourseTermLinkingVO> crsTrmLinkingVos = new ArrayList<CourseTermLinkingVO>();
		for (int i = 0; i < courseTermLinkingList.size(); i++) {
			CourseTermLinking courseTermLinking = courseTermLinkingList.get(i);
			CourseTermLinkingVO crstrmLinkingVO = new CourseTermLinkingVO();
			commonBusiness.changeObject(crstrmLinkingVO, courseTermLinking);
			crstrmLinkingVO.setRowId(i);
			crsTrmLinkingVos.add(crstrmLinkingVO);
		}
		courseTermLinkingMasterVO.setCourseTermLinkingVOs(crsTrmLinkingVos);
		
		return courseTermLinkingMasterVO;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertCourseTermLinkRec(
			CourseTermLinkingMasterVO courseTermLinkingMasterVO,
			UserSessionDetails userSessionDetails)
			throws DatabaseException, DuplicateEntryException, UpdateFailedException {

		logger.debug("inside insert Course term link method");

		CourseTermLinking courseTermLinking = new CourseTermLinking();
		// map the UIObject to Pojo
		commonBusiness.changeObject(courseTermLinking, courseTermLinkingMasterVO.getCourseTermLinkingVO());

		courseTermLinking.setDbTs(1);
		courseTermLinking.setInstId(userSessionDetails.getInstId());
		courseTermLinking.setBranchId(userSessionDetails.getBranchId());
		courseTermLinking.setrCreId(userSessionDetails.getUserId());
		courseTermLinking.setrModId(userSessionDetails.getUserId());
		courseTermLinking.setDelFlag("N");
		
		
			if(courseTermLinkingDAO.checkCourseTermLinkingOrderExist(courseTermLinking)==0){			
				
				try {
					courseTermLinkingDAO.insertCourseTermLinkingRec(courseTermLinking);
					// functional audit
					doAudit.doFunctionalAudit(userSessionDetails,
							AuditConstant.COURSE_TRM_LINK_INSERT, " ");
					logger.debug("Completed Functional Auditing");
				} catch (DuplicateEntryException e) {
					CourseTermLinkingKey courseTermLinkingKey = new CourseTermLinkingKey();
					commonBusiness.changeObject(courseTermLinkingKey, courseTermLinking);
					courseTermLinkingKey.setDbTs(0);
					try {
						CourseTermLinking crsTermLinkingNew=courseTermLinkingDAO.selectCourseTermLinkingDelFlgRec(courseTermLinkingKey);
						courseTermLinkingKey.setDbTs(crsTermLinkingNew.getDbTs());
						crsTermLinkingNew.setTermSerialOrder(courseTermLinking.getTermSerialOrder());
						courseTermLinkingDAO.updateCrsTmLinkRecDelFlgYesToNo(crsTermLinkingNew, courseTermLinkingKey);
						// functional audit
						doAudit.doFunctionalAudit(userSessionDetails,
								AuditConstant.COURSE_TRM_LINK_INSERT, "Course Term Link updated from del flag yes to no ");
						logger.debug("Completed Functional Auditing");
					} catch (NoDataFoundException e2) {
						throw new DuplicateEntryException();
					}
					
				}
				
			}else{
			
				throw new DuplicateEntryException();
			}
			
		
		

	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteCourseTermLinkingRec(
			CourseTermLinkingVO courseTermLinkingVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {
		
		
		CourseTermLinkingKey courseTermLinkingKey = new CourseTermLinkingKey();
		commonBusiness.changeObject(courseTermLinkingKey, courseTermLinkingVO);
		
		courseTermLinkingKey.setInstId(userSessionDetails.getInstId());
		courseTermLinkingKey.setBranchId(userSessionDetails.getBranchId());
		CourseTermLinking courseTermLinkingNew=courseTermLinkingDAO.selectCourseTermLinkingRec(courseTermLinkingKey);
		
		CourseTermLinking deleteCourseTermLinking=new CourseTermLinking();
		commonBusiness.changeObject(deleteCourseTermLinking, courseTermLinkingNew);
		courseTermLinkingKey.setDbTs(courseTermLinkingNew.getDbTs());
		deleteCourseTermLinking.setrModId(userSessionDetails.getUserId());
		deleteCourseTermLinking.setDbTs(courseTermLinkingNew.getDbTs());
		deleteCourseTermLinking.setDelFlag("Y");
		// get Data's from DB for dbts value
		courseTermLinkingDAO.deleteCourseTermLinkingRec(deleteCourseTermLinking, courseTermLinkingKey);
		// functional audit
				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.COURSE_TRM_LINK_DELETE, " ");
				logger.debug("Completed Functional Auditing");
				
				// Database audit
				
				String oldRecString = courseTermLinkingNew.toStringForAuditCourseTermLinkingRecord();
				
				String newRecString = deleteCourseTermLinking.toStringForAuditCourseTermLinkingRecord();
				doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
						TableNameConstant.COURSE_TERM_LINKING,
						courseTermLinkingKey.toStringForAuditCourseTermLinkingKey(),
						oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, "",
						"");

				logger.debug("Completed Database Auditing");

	}

	@Override
	public Map<String, String> getTrmIdForSectionAlloc(String instid,
			String branchId, String courseId) {
		
		return courseTermLinkingListDAO.getTermListFromCourse(instid, branchId, courseId);
		
	}
}
