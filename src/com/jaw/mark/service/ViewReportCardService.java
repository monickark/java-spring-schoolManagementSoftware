package com.jaw.mark.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.ParentSession;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.controller.ReportCardMasterVo;
import com.jaw.mark.controller.StudentReportCardVO;
import com.jaw.mark.dao.IStuMrksRmksListDAO;
import com.jaw.mark.dao.IStudentReportCardDAO;
import com.jaw.mark.dao.StuDetailsListForRemarks;
import com.jaw.mark.dao.StuMrksRmksListKey;
import com.jaw.mark.dao.StudentReportCard;
import com.jaw.student.admission.controller.StudentMasterVO;
import com.jaw.student.admission.dao.IStudentMasterDao;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.admission.dao.StudentMasterKey;

@Service
public class ViewReportCardService implements IViewReportCardService {
	// Logging
	Logger logger = Logger.getLogger(ViewReportCardService.class);

	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IStudentReportCardDAO studentReportCardDAO;
	@Autowired
	IStudentMasterDao studentMasterDao;
	@Autowired
	IStuMrksRmksListDAO stuMrksRmksListDAO;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;

	@Override
	public void getStudentAdmisNo(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails, String flow)
			throws NoDataFoundException {
		StuMrksRmksListKey stuMrksRmksListKey = new StuMrksRmksListKey();
		commonBusiness.changeObject(stuMrksRmksListKey,
				ReportCardMasterVo.getMarkMasterVO());

		stuMrksRmksListKey.setInstId(userSessionDetails.getInstId());
		stuMrksRmksListKey.setBranchId(userSessionDetails.getBranchId());
		List<StuDetailsListForRemarks> stuMrksRmksListKeys = null;
		if (flow.equals("Add")) {
			stuMrksRmksListKeys = stuMrksRmksListDAO
					.getStuAdmisNoListForRemarksNotAdded(stuMrksRmksListKey);
		} else {
			stuMrksRmksListKeys = stuMrksRmksListDAO.getStuAdmisNoList(
					stuMrksRmksListKey, flow);
		}
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (StuDetailsListForRemarks stuDetailsListForRemarks : stuMrksRmksListKeys) {
			map.put(stuDetailsListForRemarks.getStudentAdmisNo(),
					stuDetailsListForRemarks.getStudentName());
		}
		ReportCardMasterVo.setStudentAdmisNoMap(map);
	}

	@Override
	public void viewRepordCard(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails,
			StudentSession studentSession, ParentSession parentSession)
			throws NoDataFoundException {
		StuMrksRmksListKey stuMrksRmksListKey = new StuMrksRmksListKey();

		StudentMasterKey studentMasterKey = new StudentMasterKey();
		commonBusiness.changeObject(studentMasterKey,
				ReportCardMasterVo.getMarkMasterSearchVo());
		studentMasterKey.setInstId(userSessionDetails.getInstId());
		studentMasterKey.setBranchId(userSessionDetails.getBranchId());
		StudentMaster studentMaster = studentReportCardDAO
				.retriveStudentDetails(studentMasterKey);
		System.out.println("Student master :" + studentMaster.toString());
		commonBusiness.changeObject(stuMrksRmksListKey,
				ReportCardMasterVo.getMarkMasterSearchVo());

		stuMrksRmksListKey.setInstId(userSessionDetails.getInstId());
		stuMrksRmksListKey.setBranchId(userSessionDetails.getBranchId());
		if ((userSessionDetails.getProfileGroup()
				.equals(ApplicationConstant.PG_STUDENT))) {
			stuMrksRmksListKey.setExamId(studentReportCardDAO.getLatestExam(
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId(),
					studentSession.getStuGrpId()));
			stuMrksRmksListKey.setAcTerm(userSessionDetails.getCurrAcTerm());
			stuMrksRmksListKey.setStudentGrpId(studentSession.getStuGrpId());
		} else if ((userSessionDetails.getProfileGroup()
				.equals(ApplicationConstant.PG_PARENT))) {
			studentSession = parentSession.getStudentSession().get(
					Integer.parseInt(parentSession.getSelectedStuIndex()));
			stuMrksRmksListKey.setExamId(studentReportCardDAO.getLatestExam(
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId(),
					studentSession.getStuGrpId()));
			stuMrksRmksListKey.setAcTerm(userSessionDetails.getCurrAcTerm());
			stuMrksRmksListKey.setStudentGrpId(studentSession.getStuGrpId());
			stuMrksRmksListKey.setExamId(studentReportCardDAO.getLatestExam(
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId(),
					studentSession.getStuGrpId()));
			stuMrksRmksListKey.setAcTerm(userSessionDetails.getCurrAcTerm());
			stuMrksRmksListKey.setStudentGrpId(studentSession.getStuGrpId());
		} else {
			System.out.println("Inside else");
			stuMrksRmksListKey.setExamId(ReportCardMasterVo.getMarkMasterVO()
					.getExamId());
			stuMrksRmksListKey.setAcTerm(ReportCardMasterVo.getMarkMasterVO()
					.getAcTerm());
			stuMrksRmksListKey.setStudentGrpId(ReportCardMasterVo
					.getMarkMasterVO().getStudentGrpId());
		}
		System.out.println("Exam id passed :"
				+ stuMrksRmksListKey.getStudentGrpId());
		List<StudentReportCard> reportCards = studentReportCardDAO
				.getStuMarkPerClass(stuMrksRmksListKey);
		List<StudentReportCard> reportCardsAbsentee = studentReportCardDAO
				.getAbsentListInExamForRC(stuMrksRmksListKey);
		List<StudentReportCard> studentReportCards = studentReportCardDAO
				.getStuMarksForAllClass(stuMrksRmksListKey);
		
		// Get current exam marks
		
		StudentReportCard studentReportCard=studentReportCardDAO.getStuClassRank(stuMrksRmksListKey);
		System.out.println("Student report card dao obj :"+studentReportCard.toString());
		
		// get previous exams mark consolidation
		
		List<StudentReportCard> studentReportCards2 = studentReportCardDAO
				.getStuPrevExamConsolidation(stuMrksRmksListKey);
		
		
		List<StudentReportCardVO> studentReportCardVOs = new ArrayList<StudentReportCardVO>();
		List<StudentReportCardVO> consolidatedReportCard = new ArrayList<StudentReportCardVO>();
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
		
			System.out.println("Student report card :"
					+ studentReportCardVO.toString());
			for (int j = 0; j < reportCardsAbsentee.size(); j++) {
				if(reportCards.get(i).getCrslId().equals(reportCardsAbsentee.get(j).getCrslId())) {
					studentReportCardVO.setAttendance(reportCardsAbsentee.get(j).getAttendance());
				}
			}
			
			studentReportCardVOs.add(studentReportCardVO);
		}

		for (int i = 0; i < studentReportCards2.size(); i++) {
			StudentReportCardVO studentReportCardVO = new StudentReportCardVO();
			commonBusiness.changeObject(studentReportCardVO,
					studentReportCards2.get(i));
			consolidatedReportCard.add(studentReportCardVO);
		}
		StudentMasterVO studentMasterVO = new StudentMasterVO();
		StudentReportCardVO studentReportCardVO = new StudentReportCardVO();
		commonBusiness.changeObject(studentMasterVO, studentMaster);
		commonBusiness.changeObject(studentReportCardVO, studentReportCard);
		ReportCardMasterVo.setStudentReportCardVOs(studentReportCardVOs);
		ReportCardMasterVo.setConsolidatedreportCard(consolidatedReportCard);
		ReportCardMasterVo.setStudentMasterVO(studentMasterVO);
		ReportCardMasterVo.setStudentReportCardVO(studentReportCardVO);
	}
}
