package com.jaw.mark.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.MainTestNotAddedFirstException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.core.dao.ITermListBasedOnCourseIdDAO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.controller.IMarkSubjectLinkListDAO;
import com.jaw.mark.controller.MarkSubjectLinkListVO;
import com.jaw.mark.controller.MarkSubjectLinkMasterVO;
import com.jaw.mark.controller.MarkSubjectLinkSearchVO;
import com.jaw.mark.controller.MarkSubjectLinkVO;
import com.jaw.mark.dao.IMarkSubjectLinkDAO;
import com.jaw.mark.dao.MarkSubjectLink;
import com.jaw.mark.dao.MarkSubjectLinkKey;
import com.jaw.mark.dao.MarkSubjectLinkList;

//mark subject Details Service Class
@Service
public class MarkSubjectLinkService implements IMarkSubjectLinkService {
	// Logging
	Logger logger = Logger.getLogger(MarkSubjectLinkService.class);

	@Autowired
	IMarkSubjectLinkDAO markSubjectLinkDAO;
	@Autowired
	IMarkSubjectLinkListDAO MarkSubjectLinkListDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	DoAudit doAudit;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Autowired
	ITermListBasedOnCourseIdDAO termListBasedOnCourseIdDAO;

	@Override
	public void selectMarkSubjectLinkingData(
			MarkSubjectLinkMasterVO markSubjectLinkMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		MarkSubjectLinkKey markSubjectLinkKey = new MarkSubjectLinkKey();
		commonBusiness.changeObject(markSubjectLinkKey,
				markSubjectLinkMasterVO.getMarkSubjectLinkSearchVO());
		markSubjectLinkKey.setInstId(userSessionDetails.getInstId());
		markSubjectLinkKey.setBranchId(userSessionDetails.getBranchId());
		List<MarkSubjectLinkList> MarkSubjectLinks = MarkSubjectLinkListDAO
				.getMarkSubjectLinkList(markSubjectLinkKey);
		System.out.println("TestLink"+MarkSubjectLinks);
		List<MarkSubjectLinkListVO> MarkSubjectLinkVOs = new ArrayList<MarkSubjectLinkListVO>();

		for (int i = 0; i < MarkSubjectLinks.size(); i++) {
			MarkSubjectLinkList markSubjectLink = MarkSubjectLinks.get(i);
			MarkSubjectLinkListVO markSubjectLinkListVO = new MarkSubjectLinkListVO();
			commonBusiness.changeObject(markSubjectLinkListVO, markSubjectLink);
			markSubjectLinkListVO.setRowId(i);
			if (markSubjectLinkListVO.getExamType() == null) {
				markSubjectLinkListVO.setExamType("");
			}
			if (markSubjectLinkListVO.getSubTestId() == null) {
				markSubjectLinkListVO.setSubTestId("");
			}
			if (markSubjectLinkListVO.getExamDate() == null) {
				markSubjectLinkListVO.setExamDate("");
			}
			MarkSubjectLinkVOs.add(markSubjectLinkListVO);
		}
		markSubjectLinkMasterVO.setMarkSubjectLinkListVOs(MarkSubjectLinkVOs);

	}

	@Override
	public void insertMarkSubjectLinkRec(
			MarkSubjectLinkMasterVO markSubjectLinkMasterVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException, MainTestNotAddedFirstException {

		logger.debug("inside insert mark subject Details method");

		MarkSubjectLink MarkSubjectLink = new MarkSubjectLink();
		MarkSubjectLinkKey markSubjectLinkKey = new MarkSubjectLinkKey();

		
		// map the UIObject to Pojo
		commonBusiness.changeObject(MarkSubjectLink,
				markSubjectLinkMasterVO.getMarkSubjectLinkVO());
		commonBusiness.changeObject(MarkSubjectLink,
				markSubjectLinkMasterVO.getMarkSubjectLinkSearchVO());
		commonBusiness.changeObject(markSubjectLinkKey,
				markSubjectLinkMasterVO.getMarkSubjectLinkVO());
		commonBusiness.changeObject(markSubjectLinkKey,
				markSubjectLinkMasterVO.getMarkSubjectLinkSearchVO());
		commonBusiness.changeObject(markSubjectLinkMasterVO.getMarkSubjectLinkListVO(), MarkSubjectLink);
		markSubjectLinkKey.setInstId(userSessionDetails.getInstId());
		markSubjectLinkKey.setBranchId(userSessionDetails.getBranchId());
		int rec=markSubjectLinkDAO.checkFirstRecord(markSubjectLinkKey);
		if(rec==0) {
			if(!markSubjectLinkKey.getSubTestId().equals(CommonCodeConstant.MAIN_TEST_EXAM)){
				throw new MainTestNotAddedFirstException();
			}
		}
		int count=markSubjectLinkDAO.checkRecordExist(markSubjectLinkKey);
		System.out.println("Count value :"+count);
		if(count==0){
		MarkSubjectLink.setDbTs(1);
		MarkSubjectLink.setInstId(userSessionDetails.getInstId());
		MarkSubjectLink.setBranchId(userSessionDetails.getBranchId());
		MarkSubjectLink.setMarkSubjectLinkId(AlphaSequenceUtil
				.generateAlphaSequence(
						ApplicationConstant.STRING_MARK_SUBJECT_LINK_SEQ,
						simpleIdGenerator.getNextId(
								SequenceConstant.MARK_SUBJECT_LINKING_ID, 1)));
		MarkSubjectLink.setrCreId(userSessionDetails.getUserId());
		MarkSubjectLink.setrModId(userSessionDetails.getUserId());
		MarkSubjectLink.setDelFlag("N");
		MarkSubjectLink.setStatus(ApplicationConstant.MARK_SUB_LINK_OPEN);
		

		markSubjectLinkDAO.insertMarkSubjectRec(MarkSubjectLink);
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.MARK_SUBJECT_CREATE, "");
		}else {
						throw new DuplicateEntryException();
		}
	}

	@Override
	public void updateMarkSubjectLinking(
			MarkSubjectLinkMasterVO markSubjectLinkMasterVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws UpdateFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, MainTestNotAddedFirstException {
		logger.debug("inside update mark subject Details method");

		MarkSubjectLinkKey markSubjectLinkKey = new MarkSubjectLinkKey();

		markSubjectLinkKey.setInstId(userSessionDetails.getInstId());
		markSubjectLinkKey.setBranchId(userSessionDetails.getBranchId());
		markSubjectLinkKey.setMarkSubjectLinkId(markSubjectLinkMasterVO
				.getMarkSubjectLinkListVO().getMarkSubjectLinkId());
		MarkSubjectLink markSubjectLink = markSubjectLinkDAO
				.selectMarkSubjectRec(markSubjectLinkKey);

		String tableKey = markSubjectLinkKey.toStringForDBKey();
		String oldRecord = markSubjectLink.stringForDbAudit();
		MarkSubjectLinkVO markSubjectLinkVO = markSubjectLinkMasterVO
				.getMarkSubjectLinkVO();
		MarkSubjectLinkSearchVO markSubjectLinkSearchVO = markSubjectLinkMasterVO
				.getMarkSubjectLinkSearchVO();
		markSubjectLink.setMinMark(markSubjectLinkVO.getMinMark());
		markSubjectLink.setMaxMark(markSubjectLinkVO.getMaxMark());
		markSubjectLink.setExamDate(markSubjectLinkVO.getExamDate());
		markSubjectLink.setRemarks(markSubjectLinkVO.getRemarks());
		markSubjectLink.setStartTime(markSubjectLinkVO.getStartTime());
		markSubjectLink.setDuration(markSubjectLinkVO.getDuration());
        markSubjectLink.setSubTestId(markSubjectLinkVO.getSubTestId());
        markSubjectLink.setLabBatch(markSubjectLinkVO.getLabBatch());
        markSubjectLink.setCrslId(markSubjectLinkVO.getCrslId());
        markSubjectLink.setExamType(markSubjectLinkVO.getExamType());
		markSubjectLink.setSubPortionsDetails(markSubjectLinkVO.getSubPortionsDetails());		
		markSubjectLink.setAcTerm(markSubjectLinkSearchVO.getAcTerm());
		markSubjectLink.setStudentGrpId(markSubjectLinkSearchVO
				.getStudentGrpId());
		markSubjectLink.setExamId(markSubjectLinkSearchVO.getExamId());
		int rec=markSubjectLinkDAO.checkFirstRecord(markSubjectLinkKey);
		if(rec==1) {
			if(!markSubjectLinkKey.getSubTestId().equals(CommonCodeConstant.MAIN_TEST_EXAM)){
				throw new MainTestNotAddedFirstException();
			}
		}
		
		markSubjectLinkDAO.updateMarkSubjectRec(markSubjectLink,
				markSubjectLinkKey);
		MarkSubjectLink markSubjectLink2 = markSubjectLinkDAO
				.selectMarkSubjectRec(markSubjectLinkKey);
		String newRecord = markSubjectLink2.stringForDbAudit();
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.MARK_SUBJECT_UPDATE, "");
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.MARK_SUBJECT, tableKey, oldRecord,
				AuditConstant.TYPE_OF_OPER_UPDATE, newRecord, "");

	}

	@Override
	public void deleteMarkSubjectLinkDAORec(
			MarkSubjectLinkListVO markSubjectLinkVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {
		logger.debug("inside update mark subject Details method");

		MarkSubjectLinkKey markSubjectLinkKey = new MarkSubjectLinkKey();
		// map the UIObject to Pojo

		commonBusiness.changeObject(markSubjectLinkKey, markSubjectLinkVO);
		markSubjectLinkKey.setInstId(userSessionDetails.getInstId());
		markSubjectLinkKey.setBranchId(userSessionDetails.getBranchId());
		markSubjectLinkKey.setMarkSubjectLinkId(markSubjectLinkVO
				.getMarkSubjectLinkId());
		MarkSubjectLink markSubjectLink = markSubjectLinkDAO
				.selectMarkSubjectRec(markSubjectLinkKey);
		String tableKey = markSubjectLinkKey.toStringForDBKey();
		String oldRecord = markSubjectLink.stringForDbAudit();
		markSubjectLinkKey.setDbTs(markSubjectLink.getDbTs());
		markSubjectLinkDAO.deleteMarkSubjectRec(markSubjectLink,
				markSubjectLinkKey);

		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.MARK_SUBJECT_DELETE, "");
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.MARK_SUBJECT, tableKey, oldRecord,
				AuditConstant.TYPE_OF_OPER_DELETE, "", "");

	}

	@Override
	public Map<String, String> getTermDetailsBasedOnCourseId(String courseId,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		return termListBasedOnCourseIdDAO.getTermListBasedOnCourseId(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), courseId);

	}

}
