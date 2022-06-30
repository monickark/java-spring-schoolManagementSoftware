package com.jaw.mark.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.AllMarksNotEnteredException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.common.files.dao.IFileMasterDao;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.controller.ReportCardMasterVo;
import com.jaw.mark.controller.StuDetailsListForRemarksVO;
import com.jaw.mark.controller.StudentReportCardVO;
import com.jaw.mark.dao.IMarkMasterDAO;
import com.jaw.mark.dao.IMarkMasterListDAO;
import com.jaw.mark.dao.IMarkSubjectLinkDAO;
import com.jaw.mark.dao.IMarksValidationDAO;
import com.jaw.mark.dao.IStuMrksRmksListDAO;
import com.jaw.mark.dao.IStudentExamRemarksDAO;
import com.jaw.mark.dao.IStudentReportCardDAO;
import com.jaw.mark.dao.MarkMaster;
import com.jaw.mark.dao.MarkMasterKey;
import com.jaw.mark.dao.MarkSubjectLink;
import com.jaw.mark.dao.MarkSubjectLinkKey;
import com.jaw.mark.dao.MarkValidationKey;
import com.jaw.mark.dao.StuDetailsListForRemarks;
import com.jaw.mark.dao.StuMrksRmksListKey;
import com.jaw.mark.dao.StudentExamRemark;
import com.jaw.mark.dao.StudentExamRemarkKey;
import com.jaw.mark.dao.StudentReportCard;
import com.jaw.student.admission.controller.StudentMasterVO;

@Service
public class ReportCardRemarksService implements IReportCardRemarksService {
	// Logging
	Logger logger = Logger.getLogger(ReportCardRemarksService.class);

	@Autowired
	IMarkMasterDAO markMasterDao;
	@Autowired
	IMarkMasterListDAO markMasterListDao;
	@Autowired
	IMarksValidationDAO marksValidationDAO;
	@Autowired
	IStuMrksRmksListDAO stuMrksRmksListDAO;
	@Autowired
	IMarkSubjectLinkDAO markSubjectLinkDAO;
	@Autowired
	IStudentReportCardDAO studentReportCardDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IStudentExamRemarksDAO studentExamRemarksDAO;
	@Autowired
	DoAudit doAudit;
	@Autowired
	IViewReportCardService viewReportCardService;
	@Autowired
	IFileMasterDao fileMasterDao;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;

	@Override
	public void selectMarkListForRmksAdd(ReportCardMasterVo reportCardMasterVo,
			UserSessionDetails userSessionDetails, String action)
			throws NoDataFoundException {
		System.out.println("In service vo:" + reportCardMasterVo.toString());
		System.out.println("Action value :" + action);

		StuMrksRmksListKey stuMrksRmksListKey = new StuMrksRmksListKey();
		List<StuDetailsListForRemarksVO> stuDetailsListForRemarkslist = new ArrayList<StuDetailsListForRemarksVO>();
		commonBusiness.changeObject(stuMrksRmksListKey,
				reportCardMasterVo.getMarkMasterVO());

		stuMrksRmksListKey.setInstId(userSessionDetails.getInstId());
		stuMrksRmksListKey.setBranchId(userSessionDetails.getBranchId());
		List<StuDetailsListForRemarks> stuDetailsListForRemarks = null;
		int newIndex = 0;
		if ((reportCardMasterVo.getStuMrksRmksVO().getForWholeClass() != null)
				&& (reportCardMasterVo.getStuMrksRmksVO().getForWholeClass()
						.equals("W"))) {
			System.out.println("Inside whole class");
			if (action.equals("Prev")) {
				System.out.println("Inside prev");
				int index = getIndexByProperty(reportCardMasterVo
						.getStuMrksRmksVO().getStudentAdmisNo(),
						reportCardMasterVo.getStuDetailsListForRemarksVOs());
				System.out.println(" index:" + index);
				newIndex = index - 1;
				System.out.println("new index:" + newIndex);
				stuMrksRmksListKey.setStudentAdmisNo(reportCardMasterVo
						.getStuDetailsListForRemarksVOs().get(newIndex)
						.getStudentAdmisNo());
			} else if (action.equals("Next")) {
				System.out.println("Inside next");
				int index = getIndexByProperty(reportCardMasterVo
						.getStuMrksRmksVO().getStudentAdmisNo(),
						reportCardMasterVo.getStuDetailsListForRemarksVOs());
				System.out.println(" index:" + index);
				newIndex = index + 1;
				System.out.println("new index:" + newIndex);
				stuMrksRmksListKey.setStudentAdmisNo(reportCardMasterVo
						.getStuDetailsListForRemarksVOs().get(index + 1)
						.getStudentAdmisNo());
			} else {
				System.out.println("nothing");

				stuDetailsListForRemarks = stuMrksRmksListDAO
						.getStuAdmisNoList(stuMrksRmksListKey, "common");
				System.out.println("In service key2:"
						+ stuMrksRmksListKey.toString());
				stuMrksRmksListKey.setStudentAdmisNo(stuDetailsListForRemarks
						.get(0).getStudentAdmisNo());
				System.out.println("In service key2:"
						+ stuDetailsListForRemarks.get(0).getStudentAdmisNo());
				System.out.println("In service key2:"
						+ stuMrksRmksListKey.toString());
				for (StuDetailsListForRemarks stuDetailsListForRemarks2 : stuDetailsListForRemarks) {
					StuDetailsListForRemarksVO stuDetailsListForRemarksVO = new StuDetailsListForRemarksVO();
					commonBusiness.changeObject(stuDetailsListForRemarksVO,
							stuDetailsListForRemarks2);
					stuDetailsListForRemarkslist
							.add(stuDetailsListForRemarksVO);
				}
				reportCardMasterVo
						.setStuDetailsListForRemarksVOs(stuDetailsListForRemarkslist);
				System.out.println("In service key1:"
						+ stuMrksRmksListKey.toString());
			}
			if (newIndex == 0) {
				reportCardMasterVo.getStuMrksRmksVO().setButton("Next");
			} else if (newIndex == reportCardMasterVo
					.getStuDetailsListForRemarksVOs().size()) {
				reportCardMasterVo.getStuMrksRmksVO().setButton("Prev");
			} else {
				reportCardMasterVo.getStuMrksRmksVO().setButton("Both");
			}
		} else {
			System.out.println("Inside Single Student");
			stuMrksRmksListKey.setStudentAdmisNo(reportCardMasterVo
					.getStuMrksRmksVO().getStudentAdmisNo());
		}

		System.out.println("In service key:" + stuMrksRmksListKey.toString());

		getMarkSheet(reportCardMasterVo, stuMrksRmksListKey, userSessionDetails);

		StudentExamRemarkKey studentExamRemarkKey = new StudentExamRemarkKey();
		studentExamRemarkKey.setInstId(userSessionDetails.getInstId());
		studentExamRemarkKey.setBranchId(userSessionDetails.getBranchId());
		studentExamRemarkKey.setAcTerm(reportCardMasterVo
				.getMarkMasterSearchVo().getAcTerm());
		studentExamRemarkKey.setStudentAdmisNo(stuMrksRmksListKey
				.getStudentAdmisNo());
		studentExamRemarkKey.setExamId(stuMrksRmksListKey.getExamId());
		StudentExamRemark studentExamRemark = null;
		try {
			studentExamRemark = studentExamRemarksDAO
					.selectStudentExamRemark(studentExamRemarkKey);
			reportCardMasterVo.getStuMrksRmksVO().setRemarks(
					studentExamRemark.getRemarks());
		} catch (NoDataFoundException e) {
			logger.error("No remarks found for admission  number :"
					+ stuMrksRmksListKey.getStudentAdmisNo());
			reportCardMasterVo.getStuMrksRmksVO().setRemarks("");
		}

		reportCardMasterVo.getStuMrksRmksVO().setStudentAdmisNo(
				stuMrksRmksListKey.getStudentAdmisNo());

	}

	@Override
	public void selectStuMarksStatus(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails) throws NoDataFoundException,
			AllMarksNotEnteredException {
		System.out.println("In service vo:"
				+ ReportCardMasterVo.getMarkMasterVO().toString());
		MarkValidationKey markValidationKey = new MarkValidationKey();
		commonBusiness.changeObject(markValidationKey,
				ReportCardMasterVo.getMarkMasterVO());
		System.out.println("In service key:" + markValidationKey.toString());
		markValidationKey.setInstId(userSessionDetails.getInstId());
		markValidationKey.setBranchId(userSessionDetails.getBranchId());
		List<String> markMasters = marksValidationDAO
				.getStuMarksStatusList(markValidationKey);

		for (int i = 0; i < markMasters.size(); i++) {
			if (markMasters.get(i)
					.equals(ApplicationConstant.MARKS_STATUS_OPEN)) {
				System.out.println("markMasters.get(i) :" + markMasters.get(i));
				throw new AllMarksNotEnteredException();
			}
		}

	}

	@Override
	public void insertStudentExamRemarks(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DuplicateEntryException,
			NoDataFoundException, UpdateFailedException, DatabaseException,
			TableNotSpecifiedForAuditException {

		StudentExamRemark studentExamRemark = new StudentExamRemark();
		studentExamRemark.setDbTs(1);
		studentExamRemark.setInstId(userSessionDetails.getInstId());
		studentExamRemark.setBranchId(userSessionDetails.getBranchId());
		studentExamRemark.setAcTerm(ReportCardMasterVo.getMarkMasterVO()
				.getAcTerm());
		studentExamRemark.setStudentAdmisNo(ReportCardMasterVo
				.getStuMrksRmksVO().getStudentAdmisNo());
		studentExamRemark.setExamTypeId(ReportCardMasterVo.getMarkMasterVO()
				.getExamId());
		studentExamRemark.setRemarks(ReportCardMasterVo.getStuMrksRmksVO()
				.getRemarks());
		studentExamRemark.setrCreId(userSessionDetails.getUserId());
		studentExamRemark.setrModId(userSessionDetails.getUserId());
		studentExamRemark.setDelFlg("N");
		studentExamRemarksDAO.insertStudentExamRemark(studentExamRemark);
		MarkMasterKey markMasterKey = new MarkMasterKey();
		markMasterKey.setInstId(userSessionDetails.getInstId());
		markMasterKey.setBranchId(userSessionDetails.getBranchId());
		markMasterKey.setAcTerm(ReportCardMasterVo.getMarkMasterVO()
				.getAcTerm());
		markMasterKey.setStudentGrpId(ReportCardMasterVo.getMarkMasterVO()
				.getStudentGrpId());
		markMasterKey.setExamId(ReportCardMasterVo.getMarkMasterVO()
				.getExamId());

		MarkMaster markMaster = markMasterDao
				.selectMarkMasterRecNotById(markMasterKey);
		if (markMaster.getStatus().equals(
				ApplicationConstant.MARKS_STATUS_ENTERED)) {
			String oldRecString = markMaster.toStringForAuditMarkMasterRecord();
			markMaster.setStatus(ApplicationConstant.REMARKS_ENTERED);
			markMasterKey.setDbTs(markMaster.getDbTs());
			markMasterKey.setmMSeqId(markMaster.getmMSeqId());

			markMasterDao.updateMarkMasterRec(markMaster, markMasterKey);
			MarkMaster markMaster1 = markMasterDao
					.selectMarkMasterRecNotById(markMasterKey);
			String newRecString = markMaster1
					.toStringForAuditMarkMasterRecord();
			doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
					TableNameConstant.MARK_MASTER,
					markMasterKey.toStringForAuditMarkMasterKey(),
					oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE,
					newRecString,
					"Changed status as 'R', remarks started to enter");
		}

		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.REMARKS_ENTERED_SUCCESSFULLY, "");

	}

	@Override
	public void updateStudentExamRemarks(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DuplicateEntryException,
			NoDataFoundException, UpdateFailedException, DatabaseException,
			TableNotSpecifiedForAuditException {

		StudentExamRemarkKey studentExamRemarkKey = new StudentExamRemarkKey();
		commonBusiness.changeObject(studentExamRemarkKey,
				ReportCardMasterVo.getStuMrksRmksVO());

		studentExamRemarkKey.setInstId(userSessionDetails.getInstId());
		studentExamRemarkKey.setBranchId(userSessionDetails.getBranchId());
		studentExamRemarkKey.setAcTerm(ReportCardMasterVo.getMarkMasterVO()
				.getAcTerm());
		studentExamRemarkKey.setExamId(ReportCardMasterVo.getMarkMasterVO()
				.getExamId());
		StudentExamRemark studentExamRemark = studentExamRemarksDAO
				.selectStudentExamRemark(studentExamRemarkKey);

		String oldRecString = studentExamRemark
				.toStringForAuditMarkMasterRecord();
		studentExamRemark.setRemarks(ReportCardMasterVo.getStuMrksRmksVO()
				.getRemarks());
		studentExamRemark.setrModId(userSessionDetails.getUserId());

		studentExamRemarksDAO.updateStudentExamRemark(studentExamRemark,
				studentExamRemarkKey);
		StudentExamRemark studentExamRemark1 = studentExamRemarksDAO
				.selectStudentExamRemark(studentExamRemarkKey);
		String newRecString = studentExamRemark1
				.toStringForAuditMarkMasterRecord();
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.STUDENT_EXAM_REMARK,
				studentExamRemarkKey.toStringForAuditMarkMasterKey(),
				oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
				"");
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.REMARKS_UPDATED_SUCCESSFULLY, "");

	}

	@Override
	public void reportCardGeneration(
			ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws UpdateFailedException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, FileNotFoundInDatabase,
			NoDataFoundException {

		System.out.println("Inside generate report card");
		StuMrksRmksListKey stuMrksRmksListKey = new StuMrksRmksListKey();
		List<StuDetailsListForRemarks> stuDetailsListForRemarks = null;
		commonBusiness.changeObject(stuMrksRmksListKey,
				ReportCardMasterVo.getMarkMasterVO());

		stuMrksRmksListKey.setInstId(userSessionDetails.getInstId());
		stuMrksRmksListKey.setBranchId(userSessionDetails.getBranchId());
		stuDetailsListForRemarks = stuMrksRmksListDAO.getStuAdmisNoList(
				stuMrksRmksListKey, "common");

		List<List<StudentReportCardVO>> studentReportCardVOsList = new ArrayList<List<StudentReportCardVO>>();
		List<StudentMasterVO> studentMasterVOs = new ArrayList<StudentMasterVO>();
		List<StudentReportCardVO> studentReportCardVOs = new ArrayList<StudentReportCardVO>();
		System.out.println("Student admission number size :"
				+ stuDetailsListForRemarks.size());
		for (StuDetailsListForRemarks stuDetailsListForRemarks2 : stuDetailsListForRemarks) {
			ReportCardMasterVo.getMarkMasterSearchVo().setStudentAdmisNo(
					stuDetailsListForRemarks2.getStudentAdmisNo());
			try {
				viewReportCardService.viewRepordCard(ReportCardMasterVo,
						userSessionDetails, null, null);
				studentReportCardVOsList.add(ReportCardMasterVo
						.getStudentReportCardVOs());
				studentMasterVOs.add(ReportCardMasterVo.getStudentMasterVO());
				studentReportCardVOs.add(ReportCardMasterVo
						.getStudentReportCardVO());
			} catch (NoDataFoundException e) {
				System.out.println("No data found");
			}

		}

		System.out.println(" retur list size :"
				+ studentReportCardVOsList.size());

		ReportCardMasterVo
				.setStudentReportCardVOsList(studentReportCardVOsList);
		ReportCardMasterVo.setStudentMasterVOs(studentMasterVOs);
		
		
		
		  marksLockandClose(ReportCardMasterVo, userSessionDetails,
		 ApplicationConstant.MARKS_LOCKED, applicationCache);
		  
		  MarkMasterKey markMasterKey = new MarkMasterKey();
		  markMasterKey.setInstId(userSessionDetails.getInstId());
		  markMasterKey.setBranchId(userSessionDetails.getBranchId());
		  markMasterKey.setAcTerm(ReportCardMasterVo.getMarkMasterVO()
		  .getAcTerm());
		  markMasterKey.setStudentGrpId(ReportCardMasterVo.getMarkMasterVO()
		  .getStudentGrpId());
		  markMasterKey.setExamId(ReportCardMasterVo.getMarkMasterVO()
		  .getExamId());
		  
		  MarkMaster markMaster = markMasterDao
		  .selectMarkMasterRecNotById(markMasterKey); String oldRecString =
		  markMasterKey.toStringForAuditMarkMasterKey();
		  markMaster.setStatus(ApplicationConstant.REPORT_CARD_GENERATED);
		  markMasterKey.setDbTs(markMaster.getDbTs());
		  markMasterKey.setmMSeqId(markMaster.getmMSeqId());
		  markMasterDao.updateMarkMasterRec(markMaster, markMasterKey);
		  
		  MarkMaster markMaster1 = markMasterDao
		  .selectMarkMasterRecNotById(markMasterKey);
		  
		  String newRecString = markMaster1.toStringForAuditMarkMasterRecord();
		  doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
		  TableNameConstant.MARK_MASTER,
		  markMasterKey.toStringForAuditMarkMasterKey(), oldRecString,
		  AuditConstant.TYPE_OF_OPER_UPDATE, newRecString, "");
		 
		 
	}

	@Override
	public void reportCardPublish(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws NoDataFoundException,
			UpdateFailedException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		MarkMasterKey markMasterKey = new MarkMasterKey();
		markMasterKey.setInstId(userSessionDetails.getInstId());
		markMasterKey.setBranchId(userSessionDetails.getBranchId());
		markMasterKey.setAcTerm(ReportCardMasterVo.getMarkMasterVO()
				.getAcTerm());
		markMasterKey.setStudentGrpId(ReportCardMasterVo.getMarkMasterVO()
				.getStudentGrpId());
		markMasterKey.setExamId(ReportCardMasterVo.getMarkMasterVO()
				.getExamId());

		MarkMaster markMaster = markMasterDao
				.selectMarkMasterRecNotById(markMasterKey);
		String oldRecString = markMasterKey.toStringForAuditMarkMasterKey();
		markMaster.setStatus(ApplicationConstant.REPORT_CARD_PUBLISHED);
		markMasterKey.setDbTs(markMaster.getDbTs());
		markMasterKey.setmMSeqId(markMaster.getmMSeqId());
		markMasterDao.updateMarkMasterRec(markMaster, markMasterKey);

		MarkMaster markMaster1 = markMasterDao
				.selectMarkMasterRecNotById(markMasterKey);

		String newRecString = markMaster1.toStringForAuditMarkMasterRecord();
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.MARK_MASTER,
				markMasterKey.toStringForAuditMarkMasterKey(), oldRecString,
				AuditConstant.TYPE_OF_OPER_UPDATE, newRecString, "");
	}

	@Override
	public void marksLockandClose(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails, String action,
			ApplicationCache applicationCache) throws NoDataFoundException,
			UpdateFailedException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		MarkMasterKey markMasterKey = new MarkMasterKey();
		markMasterKey.setInstId(userSessionDetails.getInstId());
		markMasterKey.setBranchId(userSessionDetails.getBranchId());
		markMasterKey.setAcTerm(ReportCardMasterVo.getMarkMasterVO()
				.getAcTerm());
		markMasterKey.setStudentGrpId(ReportCardMasterVo.getMarkMasterVO()
				.getStudentGrpId());
		markMasterKey.setExamId(ReportCardMasterVo.getMarkMasterVO()
				.getExamId());

		MarkMaster markMaster = markMasterDao
				.selectMarkMasterRecNotById(markMasterKey);
		String oldRecString = markMasterKey.toStringForAuditMarkMasterKey();
		if (action.equals(ApplicationConstant.MARKS_LOCKED)) {
			markMaster.setStatus(ApplicationConstant.MARKS_LOCKED);

		} else if (action.equals(ApplicationConstant.MARKS_CLOSED)) {
			markMaster.setStatus(ApplicationConstant.MARKS_CLOSED);
		}

		markMasterKey.setDbTs(markMaster.getDbTs());
		markMasterKey.setmMSeqId(markMaster.getmMSeqId());

		markMasterDao.updateMarkMasterRec(markMaster, markMasterKey);
		MarkMaster markMaster1 = markMasterDao
				.selectMarkMasterRecNotById(markMasterKey);
		String newRecString = markMaster1.toStringForAuditMarkMasterRecord();
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.MARK_MASTER,
				markMasterKey.toStringForAuditMarkMasterKey(), oldRecString,
				AuditConstant.TYPE_OF_OPER_UPDATE, newRecString, "");
		MarkSubjectLinkKey markSubjectLinkKey = new MarkSubjectLinkKey();
		markSubjectLinkKey.setInstId(userSessionDetails.getInstId());
		markSubjectLinkKey.setBranchId(userSessionDetails.getBranchId());
		markSubjectLinkKey.setAcTerm(ReportCardMasterVo.getMarkMasterVO()
				.getAcTerm());
		markSubjectLinkKey.setStudentGrpId(ReportCardMasterVo.getMarkMasterVO()
				.getStudentGrpId());
		markSubjectLinkKey.setExamId(ReportCardMasterVo.getMarkMasterVO()
				.getExamId());

		MarkSubjectLink markSubjectLink = markSubjectLinkDAO
				.selectMarkSubjectRecNotById(markSubjectLinkKey);
		String oldRecString1 = markSubjectLink.stringForDbAudit();
		if (action.equals(ApplicationConstant.MARKS_LOCKED)) {
			markSubjectLink.setStatus(ApplicationConstant.MARKS_LOCKED);
		} else if (action.equals(ApplicationConstant.MARKS_CLOSED)) {
			markSubjectLink.setStatus(ApplicationConstant.MARKS_CLOSED);
		}

		markSubjectLinkKey.setDbTs(markMaster.getDbTs());
		markSubjectLinkKey.setMarkSubjectLinkId(markSubjectLink
				.getMarkSubjectLinkId());
		String newRecString1 = markSubjectLink.stringForDbAudit();
		markSubjectLinkDAO.updateMarkSubjectRec(markSubjectLink,
				markSubjectLinkKey);
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.MARK_SUBJECT,
				markSubjectLinkKey.toStringForDBKey(), oldRecString1,
				AuditConstant.TYPE_OF_OPER_UPDATE, newRecString1, "");

		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.REMARKS_UPDATED_SUCCESSFULLY, "");

	}

	private void getMarkSheet(ReportCardMasterVo ReportCardMasterVo,
			StuMrksRmksListKey stuMrksRmksListKey,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {

		System.out.println("stuMrksRmksListKey :"
				+ stuMrksRmksListKey.toString());

		List<StudentReportCard> reportCards = studentReportCardDAO
				.getStuMarkPerClass(stuMrksRmksListKey);
		List<StudentReportCard> studentReportCards = studentReportCardDAO
				.getStuMarksForAllClass(stuMrksRmksListKey);

		List<StudentReportCardVO> studentReportCardVOs = new ArrayList<StudentReportCardVO>();

		for (int i = 0; i < reportCards.size(); i++) {
			commonBusiness.changeObject(reportCards.get(i),
					ReportCardMasterVo.getMarkMasterSearchVo());

			reportCards.get(i).setAverage(
					studentReportCards.get(i).getAverage());
			reportCards.get(i).setMaxMarkObt(
					studentReportCards.get(i).getMaxMarkObt());

			System.out.println(" list data :" + reportCards.get(i).toString());
			StudentReportCardVO studentReportCardVO = new StudentReportCardVO();
			commonBusiness
					.changeObject(studentReportCardVO, reportCards.get(i));
			studentReportCardVOs.add(studentReportCardVO);
		}

		ReportCardMasterVo.setStudentReportCardVOs(studentReportCardVOs);

	}

	private int getIndexByProperty(String yourString,
			List<StuDetailsListForRemarksVO> stuDetailsListForRemarks) {
		for (int i = 0; i < stuDetailsListForRemarks.size(); i++) {
			StuDetailsListForRemarksVO stuDetailsListForRemarks2 = stuDetailsListForRemarks
					.get(i);
			if (stuDetailsListForRemarks2.getStudentAdmisNo()
					.equals(yourString)) {
				System.out.println("returned index :" + i);
				return i;
			}
		}
		return -1;
	}
}