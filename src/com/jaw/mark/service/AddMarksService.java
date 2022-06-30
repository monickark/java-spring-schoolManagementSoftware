package com.jaw.mark.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.attendance.dao.StudentAttendanceList;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.StudentNotFoundForMarkException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.IStudentGroupMasterDAO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.dao.StudentGroupMasterKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.controller.AddMarksMasterVO;
import com.jaw.mark.controller.MarkSubjectLinkListForAddMarksVO;
import com.jaw.mark.controller.StudentListForAddMarksVO;
import com.jaw.mark.controller.StudentMarksVO;
import com.jaw.mark.dao.AddMarksListKey;
import com.jaw.mark.dao.IAddMarksListDAO;
import com.jaw.mark.dao.IMarkMasterDAO;
import com.jaw.mark.dao.IMarkMasterListDAO;
import com.jaw.mark.dao.IMarkSubjectLinkDAO;
import com.jaw.mark.dao.IStudentMarkDAO;
import com.jaw.mark.dao.MarkMaster;
import com.jaw.mark.dao.MarkMasterKey;
import com.jaw.mark.dao.MarkSubjectLink;
import com.jaw.mark.dao.MarkSubjectLinkKey;
import com.jaw.mark.dao.MarkSubjectLinkListForAddMarks;
import com.jaw.mark.dao.StudentListForAddMarks;
import com.jaw.mark.dao.StudentMarks;
import com.jaw.mark.dao.StudentMarksKey;

@Service
public class AddMarksService implements IAddMarksService{
	// Logging
	Logger logger = Logger.getLogger(AddMarksService.class);

	
	@Autowired
	IAddMarksListDAO addMarksListDAO;
	@Autowired 
	IStudentGroupMasterDAO stuGrpMtrDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IStudentMarkDAO studentMarkDAO;
	@Autowired
	IMarkSubjectLinkDAO markSubjectLinkDAO;
	@Autowired
	IMarkMasterDAO markMasterkDAO;
	@Autowired
	DoAudit doAudit;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Override
	public void getMarkSubjectListForAdd(AddMarksMasterVO addMarksMasterVO,UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
		logger.debug("inside insert MarkMaster method");
		MarkSubjectLinkKey subjectLinkKey = new MarkSubjectLinkKey();
		// map the UIObject to Pojo
		System.out.println("crsl id in service"+subjectLinkKey.getCrslId()+"endd");
		commonBusiness.changeObject(subjectLinkKey, addMarksMasterVO.getAddMarkSearch());		
		if((subjectLinkKey.getCrslId()!=null)&&(subjectLinkKey.getCrslId()!="")&&(subjectLinkKey.getCrslId().equals(","))){
			subjectLinkKey.setCrslId("");
		}
		
		if ((subjectLinkKey.getCrslId()!="" )
				&& (!subjectLinkKey.getCrslId().isEmpty() )
				&& (subjectLinkKey.getCrslId() != null)&& (!subjectLinkKey.getCrslId().equals(","))) {
			String[] crst =subjectLinkKey
					.getCrslId().split("-");
			subjectLinkKey.setCrslId(crst[0]);
			subjectLinkKey.setSubType(crst[1]);
			
		}
		subjectLinkKey.setDbTs(1);
		subjectLinkKey.setInstId(userSessionDetails.getInstId());
		subjectLinkKey.setBranchId(userSessionDetails.getBranchId());
		String staffId="";
		if (userSessionDetails.getUserMenuProfile()
				.equals(ApplicationConstant.TEACHING_STAFF)) {
			staffId=userSessionDetails.getLinkId();
		}
		List<MarkSubjectLinkListForAddMarks> markSubList=addMarksListDAO.getMarkSubjectLinkListForAddMarks(subjectLinkKey,staffId);
		List<MarkSubjectLinkListForAddMarksVO> resultMarkSubList=new ArrayList<MarkSubjectLinkListForAddMarksVO>();
		for (int i = 0; i < markSubList.size(); i++) {
			MarkSubjectLinkListForAddMarksVO maSuLiVo=new MarkSubjectLinkListForAddMarksVO();
			commonBusiness.changeObject(maSuLiVo, markSubList.get(i));
			maSuLiVo.setRowid(i);		
			resultMarkSubList.add(maSuLiVo);
		}
		addMarksMasterVO.setMarkSubListVo(resultMarkSubList);
	}
	@Override
	public void getStudentListForAddMarks(AddMarksMasterVO addMarksMasterVO,
			UserSessionDetails userSessionDetails,MarkSubjectLinkListForAddMarksVO markSubLinkVO,String add) throws StudentNotFoundForMarkException, NoDataFoundException {
		MarkSubjectLinkListForAddMarks markSubjectLinkAddMarks=new MarkSubjectLinkListForAddMarks();
		commonBusiness.changeObject(markSubjectLinkAddMarks, markSubLinkVO);
		
		AddMarksListKey addMarksListKey=new AddMarksListKey();
		addMarksListKey.setInstId(userSessionDetails.getInstId());
		addMarksListKey.setBranchId(userSessionDetails.getBranchId());
		addMarksListKey.setAcTerm(addMarksMasterVO.getAddMarkSearch().getAcTerm());
		addMarksListKey.setMkslId(markSubjectLinkAddMarks.getMkslId());
		addMarksListKey.setSubType(markSubjectLinkAddMarks.getSubjectType());
		addMarksListKey.setCrslId(markSubjectLinkAddMarks.getCrslId());
		addMarksListKey.setStudentGrpId(addMarksMasterVO.getAddMarkSearch().getStudentGrpId());
		addMarksListKey.setLabBatch(markSubjectLinkAddMarks.getLabBatch());
		System.out.println("serviceeeeeeeee"+addMarksMasterVO.getAddMarkSearch().getCrslId()+"endd");
		/*if ((!addMarksMasterVO.getAddMarkSearch().getCrslId().isEmpty() )
				&& (addMarksMasterVO.getAddMarkSearch().getCrslId() != null)&& (!addMarksMasterVO.getAddMarkSearch().getCrslId().equals(","))) {
			String[] crst =addMarksMasterVO.getAddMarkSearch().getCrslId().split("-");
			addMarksListKey.setCrslId(crst[0]);
			addMarksListKey.setSubType(crst[1]);
			
		}*/	
		
		List<StudentListForAddMarks> stuListForService=addMarksListDAO.getStudentListForAddMarks(addMarksListKey,add);
		List<StudentListForAddMarksVO> stuListAddMarkVOs=new ArrayList<StudentListForAddMarksVO>();
		for (int i = 0; i < stuListForService.size(); i++) {
			StudentListForAddMarksVO stdMrkList=new StudentListForAddMarksVO();
			commonBusiness.changeObject(stdMrkList, stuListForService.get(i));
			if(add.equals("ADD")){
			stdMrkList.setGradeObtd("");
			stdMrkList.setMarksObtd("");
			stdMrkList.setSubRemarks("");		
			}
			stdMrkList.setRowId(i);
			stuListAddMarkVOs.add(stdMrkList);
		}
		addMarksMasterVO.setStuListForMark(stuListAddMarkVOs);
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveMarkdetails(AddMarksMasterVO addMarksMasterVO,
			UserSessionDetails userSessionDetails, String mkslId,
			String markOrGrade, List<StudentListForAddMarksVO> markList,String subject,String examId,ApplicationCache applicationCache) throws DuplicateEntryException, NoDataFoundException, UpdateFailedException, DatabaseException, TableNotSpecifiedForAuditException {
		
		List<StudentMarks> stuMarksList=new ArrayList<StudentMarks>();
		for(int i=0;i<markList.size();i++){			
			StudentListForAddMarksVO stuListForAddVo=markList.get(i);
			
			if(stuListForAddVo.getAttendance()==null){
				stuListForAddVo.setAttendance("P");
			}	
			if(((stuListForAddVo.getMarksObtd()!=null)&&(stuListForAddVo.getMarksObtd()!=""))||
					(stuListForAddVo.getGradeObtd()!=null)&&(stuListForAddVo.getGradeObtd()!="")||(stuListForAddVo.getAttendance().equals("A"))){
			
				StudentMarks stdMrks=new StudentMarks();
				commonBusiness.changeObject(stdMrks, stuListForAddVo);
				stdMrks.setMkslSeqId(mkslId);
				stdMrks.setInstId(userSessionDetails.getInstId());
				stdMrks.setBranchId(userSessionDetails.getBranchId());
				stdMrks.setrCreId(userSessionDetails.getUserId());
				stdMrks.setrModId(userSessionDetails.getUserId());
				if(stdMrks.getAttendance().equals("A")){
					stdMrks.setGradeObtd("");
					stdMrks.setMarksObtd(0);
				}
				if((stuListForAddVo.getMarksObtd()!=null)&&(stuListForAddVo.getMarksObtd()!="")&&(stdMrks.getMarksObtd()!=0)){
					stdMrks.setMarksObtd(Integer.parseInt(stuListForAddVo.getMarksObtd()));
				}	
				stuMarksList.add(stdMrks);
			}
		}
		
		
		System.out.println("list size in mark serviceeeeeeee"+stuMarksList.size());
		if(stuMarksList.size()!=0){
			addMarksListDAO.insertMarkListRecs(stuMarksList);
		MarkSubjectLinkKey subjectLinkKey=new MarkSubjectLinkKey();
		subjectLinkKey.setInstId(userSessionDetails.getInstId());
		subjectLinkKey.setBranchId(userSessionDetails.getBranchId());
		subjectLinkKey.setMarkSubjectLinkId(mkslId);
		MarkSubjectLink markSubLink=markSubjectLinkDAO.selectMarkSubjectRec(subjectLinkKey);
		String oldRecord = markSubLink.stringForDbAudit();
		subjectLinkKey.setDbTs(markSubLink.getDbTs());
		markSubLink.setStatus(ApplicationConstant.MARKS_STATUS_ENTERED);
		markSubjectLinkDAO.updateMarkSubjectRec(markSubLink, subjectLinkKey);
		MarkSubjectLink markSubjectLink2 = markSubjectLinkDAO
				.selectMarkSubjectRec(subjectLinkKey);
		String newRecord = markSubjectLink2.stringForDbAudit();
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.MARK_SUBJECT_UPDATE, "");
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.MARK_SUBJECT, subjectLinkKey.toStringForDBKey(), oldRecord,
				AuditConstant.TYPE_OF_OPER_UPDATE, newRecord, "");
		
		
		MarkMaster markMaster=new MarkMaster();
		commonBusiness.changeObject(markMaster, markSubLink);
		markMaster.setDbTs(0);
		MarkMaster markMasterNew=markMasterkDAO.selectMarkMasterRecForStatus(markMaster);
		System.out.println("mark master values : "+markMasterNew.toString());
		if(markMasterNew.getStatus().equals(ApplicationConstant.MARKS_STATUS_OPEN)){
		//d
		
		MarkMasterKey markMasterKey=new MarkMasterKey();
		markMasterKey.setmMSeqId(markMasterNew.getmMSeqId());
		markMasterKey.setInstId(userSessionDetails.getInstId());
		markMasterKey.setBranchId(userSessionDetails.getBranchId());
		MarkMaster updateMarkMaster=new MarkMaster();
		commonBusiness.changeObject(updateMarkMaster, markMasterNew);
		markMasterKey.setDbTs(markMasterNew.getDbTs());
		updateMarkMaster.setrModId(userSessionDetails.getUserId());
		updateMarkMaster.setStatus(ApplicationConstant.MARKS_STATUS_ENTERED);
		markMasterkDAO.updateMarkMasterRec(updateMarkMaster, markMasterKey);
	
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.MRK_MTR_UPDATE, " ");
		logger.debug("Completed Functional Auditing");

		// Database audit
		String oldRecString = markMasterNew.toStringForAuditMarkMasterRecord();
		MarkMaster markMasterAudit = null;
		markMasterKey.setDbTs(0);
		try {
			markMasterAudit = markMasterkDAO.selectMarkMasterRec(markMasterKey);
		} catch (NoDataFoundException e) {
			logger.error("No data found  Exception occured in update Teacher subject link:");
			e.printStackTrace();
		}
		markMasterKey.setDbTs(markMasterAudit.getDbTs());
		String newRecString = markMasterAudit.toStringForAuditMarkMasterRecord();
		
			doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
					TableNameConstant.MARK_MASTER,
					markMasterKey.toStringForAuditMarkMasterKey(),
					oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
					"");
		

		logger.debug("Completed Database Auditing");
		
		}
		// functional audit
				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.MARK_ADD, "Acad Term: "+addMarksMasterVO.getAddMarkSearch().getAcTerm()
						+",Student Grp: "+addMarksMasterVO.getAddMarkSearch().getStudentGrpId()+",Exam: "+examId
						+",subject:"+subject +",MarkSubLink Id : "+markSubLink.getMarkSubjectLinkId());
				System.out.println("sssssssss"+"Acad Term: "+addMarksMasterVO.getAddMarkSearch().getAcTerm()
						+",Student Grp: "+addMarksMasterVO.getAddMarkSearch().getStudentGrpId()+",Exam: "+examId
						+",subject:"+subject +",MarkSubLink Id : "+markSubLink.getMarkSubjectLinkId());
				logger.debug("Completed Functional Auditing");
		}
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateStudentMarks(StudentMarksVO studentMarksVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		StudentMarks studentMarks = new StudentMarks();
		StudentMarksKey studentMarksKey=new StudentMarksKey();
		// map the UIObject to Pojo
		commonBusiness.changeObject(studentMarks, studentMarksVO);	
		studentMarksKey.setBranchId(userSessionDetails.getBranchId());
		studentMarksKey.setInstId(userSessionDetails.getInstId());
		studentMarksKey.setStudentAdmisNo(studentMarks.getStudentAdmisNo());
		studentMarksKey.setMkslSeqId(studentMarks.getMkslSeqId());
		StudentMarks newStudentMarks=studentMarkDAO.selectStudentMarksRec(studentMarksKey);
		StudentMarks updateStudentMarks=new StudentMarks();
		commonBusiness.changeObject(updateStudentMarks, newStudentMarks);	
		updateStudentMarks.setMarksObtd(studentMarks.getMarksObtd());
		if(studentMarks.getGradeObtd()==null){
			updateStudentMarks.setGradeObtd("");
		}else{
			updateStudentMarks.setGradeObtd(studentMarks.getGradeObtd());
		}
		
		updateStudentMarks.setSubRemarks(studentMarks.getSubRemarks());
		updateStudentMarks.setAttendance(studentMarks.getAttendance());
		updateStudentMarks.setrModId(userSessionDetails.getUserId());
		studentMarksKey.setDbTs(updateStudentMarks.getDbTs());
		studentMarkDAO.updateStudentMarksRec(updateStudentMarks, studentMarksKey);
		
		// functional audit
					doAudit.doFunctionalAudit(userSessionDetails,
							AuditConstant.MARK_UPDATE, studentMarksVO.getUpdateReason());
					logger.debug("Completed Functional Auditing");
					
					// Database audit
							String oldRecString = newStudentMarks.toStringForAuditStudentMarksRecord();
							StudentMarks studentMarksForAudit = null;
							studentMarksKey.setDbTs(0);
							try {
								studentMarksForAudit=studentMarkDAO.selectStudentMarksRec(studentMarksKey);
							} catch (NoDataFoundException e) {
								logger.error("No data found  Exception occured in update Marks:");
								e.printStackTrace();
							}						
							studentMarksKey.setDbTs(studentMarksForAudit.getDbTs());			
							String newRecString = studentMarksForAudit.toStringForAuditStudentMarksRecord();
							doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
									TableNameConstant.STUDENT_MARKS,
									studentMarksKey.toStringForAuditStudentMarksKey(),
									oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
									studentMarksVO.getUpdateReason());

							logger.debug("Completed Database Auditing");
		
	}
	
	

}
