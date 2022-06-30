package com.jaw.mark.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import com.jaw.common.exceptions.AcadCalendarDeleteFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.ExamOrderExistException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarKey;
import com.jaw.core.dao.AcademicCalendarListKey;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.IAcademicCalendarDAO;
import com.jaw.core.dao.IAcademicCalendarListDAO;
import com.jaw.core.dao.TeacherSubjectLink;
import com.jaw.core.service.AcademicCalendarService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.controller.MarkMasterVO;
import com.jaw.mark.controller.MarkMtrMasterVO;
import com.jaw.mark.dao.IMarkMasterDAO;
import com.jaw.mark.dao.IMarkMasterListDAO;
import com.jaw.mark.dao.MarkMaster;
import com.jaw.mark.dao.MarkMasterKey;
import com.jaw.mark.dao.MarkMasterListKey;
import com.jaw.mark.controller.ReportCardMasterVo;
@Service
public class MarkMasterService implements IMarkMasterService{
	// Logging
		Logger logger = Logger.getLogger(MarkMasterService.class);
		@Autowired
		DoAudit doAudit;
		@Autowired
		IMarkMasterDAO markMasterDao;
		@Autowired
		IMarkMasterListDAO markMasterListDao;
		@Autowired
		CommonBusiness commonBusiness;
		@Autowired
		@Qualifier(value = "simpleIdGenerator")
		private IIdGeneratorService simpleIdGenerator;
		@Transactional(rollbackFor = Exception.class)
		@Override
	public void insertMarkMasterRec(MarkMasterVO markMasterVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException, ExamOrderExistException {
		logger.debug("inside insert MarkMaster method");
		MarkMaster markMaster = new MarkMaster();
		// map the UIObject to Pojo
		commonBusiness.changeObject(markMaster, markMasterVO);
		markMaster.setDbTs(1);
		markMaster.setInstId(userSessionDetails.getInstId());
		markMaster.setBranchId(userSessionDetails.getBranchId());
		markMaster.setrCreId(userSessionDetails.getUserId());
		markMaster.setrModId(userSessionDetails.getUserId());
		markMaster.setDelFlag("N");
		markMaster.setmMSeqId(AlphaSequenceUtil.generateAlphaSequence(
				ApplicationConstant.MARK_MASTER_SEQ, simpleIdGenerator
						.getNextId(SequenceConstant.MARK_MASTER_SEQUENCE,
								1)));
		markMaster.setStatus(ApplicationConstant.MARKS_STATUS_OPEN);
		if(markMaster.getAttTermStartDate()==""){
			markMaster.setAttTermStartDate(null);
		}
		if(markMaster.getAttTermEndDate()==""){
			markMaster.setAttTermEndDate(null);
		}
		if(markMaster.getExpRptDate()==""){
			markMaster.setExpRptDate(null);
		}
	   if(markMasterDao.checkMarkMasterRecExist(markMaster)!=0){
		   throw new DuplicateEntryException();
	   }else   if(markMasterDao.checkMarkMasterExamOrderExist(markMaster)!=0){
		   throw new ExamOrderExistException();
	   }else{
		  
		markMasterDao.insertMarkMasterRec(markMaster);
		// functional audit
				doAudit.doFunctionalAudit(userSessionDetails, AuditConstant.MRK_MTR_INSERT,
						" ");
				logger.debug("Completed Functional Auditing");
				   
			   }
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateMarkMasterRec(MarkMasterVO markMasterVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		logger.debug("inside update Academic Calendar method");
		MarkMaster markMaster = new MarkMaster();
		MarkMasterKey markMasterKey = new MarkMasterKey();
		// map the UIObject to Pojo
		commonBusiness.changeObject(markMaster, markMasterVO);
		
			markMasterKey.setmMSeqId(markMaster.getmMSeqId());
			markMasterKey.setInstId(userSessionDetails.getInstId());
			markMasterKey.setBranchId(userSessionDetails.getBranchId());

			MarkMaster markMasternew = markMasterDao
					.selectMarkMasterRec(markMasterKey);
			MarkMaster updateMarkMaster=new MarkMaster();
			commonBusiness.changeObject(updateMarkMaster, markMasternew);
			markMasterKey.setDbTs(markMasternew.getDbTs());
			updateMarkMaster.setrModId(userSessionDetails.getUserId());
			if(markMaster.getAttTermStartDate()==""){
				updateMarkMaster.setAttTermStartDate(null);
			}else{
				updateMarkMaster.setAttTermStartDate(markMaster.getAttTermStartDate());
			}
			if(markMaster.getAttTermEndDate()==""){
				updateMarkMaster.setAttTermEndDate(null);
			}else{
				updateMarkMaster.setAttTermEndDate(markMaster.getAttTermEndDate());
			}
			if(markMaster.getExpRptDate()==""){
				updateMarkMaster.setExpRptDate(null);
			}else{
				updateMarkMaster.setExpRptDate(markMaster.getExpRptDate());
			}
			
			updateMarkMaster.setActRptDate(markMaster.getActRptDate());

			markMasterDao.updateMarkMasterRec(updateMarkMaster, markMasterKey);
		
			// functional audit
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.MRK_MTR_UPDATE, " ");
			logger.debug("Completed Functional Auditing");

			// Database audit
			String oldRecString = markMasternew.toStringForAuditMarkMasterRecord();
			MarkMaster markMasterAudit = null;
			markMasterKey.setDbTs(0);
			try {
				markMasterAudit = markMasterDao.selectMarkMasterRec(markMasterKey);
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
	@Override
	public MarkMtrMasterVO selectmarkMasterList(MarkMtrMasterVO markMtrMasterVO,UserSessionDetails userSessionDetails)
			throws NoDataFoundException {

		MarkMasterListKey markMasterListKey=new MarkMasterListKey();
		if(markMtrMasterVO.getMarkMasterSearchVo()!=null){
		commonBusiness.changeObject(markMasterListKey, markMtrMasterVO.getMarkMasterSearchVo());
		}
		markMasterListKey.setInstId(userSessionDetails.getInstId());
		markMasterListKey.setBranchId(userSessionDetails.getBranchId());
		List<MarkMaster> markMasters = markMasterListDao.getMarkMasterList(markMasterListKey);
		List<MarkMasterVO> masterVOs = new ArrayList<MarkMasterVO>();

		for (int i = 0; i < markMasters.size(); i++) {
			MarkMaster markMaster = markMasters.get(i);
			MarkMasterVO markMasterVO = new MarkMasterVO();
			commonBusiness.changeObject(markMasterVO, markMaster);
			markMasterVO.setRowid(i);
			masterVOs.add(markMasterVO);
		}
		markMtrMasterVO.setMarkMasterVOs(masterVOs);
		return markMtrMasterVO;
	}
		@Override
	public MarkMtrMasterVO selectmarkMasterList(
			MarkMtrMasterVO markMtrMasterVO,
			ReportCardMasterVo reportCardMasterVo,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {

		MarkMasterListKey markMasterListKey = new MarkMasterListKey();
		if (markMtrMasterVO.getMarkMasterSearchVo() != null) {
			commonBusiness.changeObject(markMasterListKey,
					reportCardMasterVo.getMarkMasterSearchVo());
		}
		markMasterListKey.setInstId(userSessionDetails.getInstId());
		markMasterListKey.setBranchId(userSessionDetails.getBranchId());
		List<MarkMaster> markMasters = markMasterListDao
				.getMarkMasterList(markMasterListKey);
		List<MarkMasterVO> masterVOs = new ArrayList<MarkMasterVO>();

		for (int i = 0; i < markMasters.size(); i++) {
			MarkMaster markMaster = markMasters.get(i);
			MarkMasterVO markMasterVO = new MarkMasterVO();
			commonBusiness.changeObject(markMasterVO, markMaster);
			markMasterVO.setRowid(i);
			masterVOs.add(markMasterVO);
		}
		markMtrMasterVO.setMarkMasterVOs(masterVOs);
		return markMtrMasterVO;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteMarkMasterRec(MarkMasterVO markMasterVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {		
		MarkMasterKey markMasterKey = new MarkMasterKey();		
		commonBusiness.changeObject(markMasterKey,markMasterVO);

		markMasterKey.setInstId(userSessionDetails.getInstId());
		markMasterKey.setBranchId(userSessionDetails.getBranchId());
		MarkMaster masrkMasterNew=markMasterDao.selectMarkMasterRec(markMasterKey);	
		
		MarkMaster deleteMarkMaster=new MarkMaster();
		commonBusiness.changeObject(deleteMarkMaster, masrkMasterNew);
		markMasterKey.setDbTs(masrkMasterNew.getDbTs());
		deleteMarkMaster.setrModId(userSessionDetails.getUserId());		
		deleteMarkMaster.setDbTs(masrkMasterNew.getDbTs()+1);
		deleteMarkMaster.setDelFlag("Y");
		markMasterDao.deleteMarkMasterRec(deleteMarkMaster, markMasterKey);
		// functional audit
					doAudit.doFunctionalAudit(userSessionDetails,
							AuditConstant.MRK_MTR_DELETE, " ");
					logger.debug("Completed Functional Auditing");
					
					// Database audit
					
					String oldRecString = masrkMasterNew.toStringForAuditMarkMasterRecord();
					
					String newRecString = deleteMarkMaster.toStringForAuditMarkMasterRecord();
					doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
							TableNameConstant.MARK_MASTER,
							markMasterKey.toStringForAuditMarkMasterKey(),
							oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, newRecString,
							"");

					logger.debug("Completed Database Auditing");

	}

	

}
