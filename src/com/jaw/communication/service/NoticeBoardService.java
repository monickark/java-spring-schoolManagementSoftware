package com.jaw.communication.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.jaw.common.exceptions.AcadCalFutureDateDeleteFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForAcaTermHolGenException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.communication.controller.NoticeBoardMasterVO;
import com.jaw.communication.controller.NoticeBoardVO;
import com.jaw.communication.dao.INoticeBoardDAO;
import com.jaw.communication.dao.INoticeBoardListDAO;
import com.jaw.communication.dao.NoticeBoard;
import com.jaw.communication.dao.NoticeBoardKey;
import com.jaw.communication.dao.NoticeBoardListKey;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarKey;
import com.jaw.core.dao.AcademicCalendarListKey;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.IAcademicCalendarDAO;
import com.jaw.core.dao.IAcademicCalendarListDAO;
import com.jaw.core.dao.IAcademicTermDetailsDAO;
import com.jaw.core.dao.SubjectMaster;
import com.jaw.core.dao.SubjectMasterKey;
import com.jaw.core.service.AcademicCalendarService;
import com.jaw.core.service.HolidayInsertionHelper;
import com.jaw.core.service.HolidayUpdateHelper;
import com.jaw.core.service.IAcademicCalendarService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
@Service
public class NoticeBoardService  implements INoticeBoardService {
	// Logging
	Logger logger = Logger.getLogger(NoticeBoardService.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	INoticeBoardDAO noticeBoardDao;
	@Autowired
	INoticeBoardListDAO noticeBoardListDao;	
	@Autowired
	DateUtil dateUtil;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertNoticeBoardDetailsRec(NoticeBoardVO noticeBoardVO,
			UserSessionDetails userSessionDetails) throws DatabaseException,
			DuplicateEntryException, NoDataFoundException,
			UpdateFailedException {
		logger.debug("inside insert Notice Board Service method");
		NoticeBoard noticeBoard = new NoticeBoard();
		// map the UIObject to Pojo
		commonBusiness.changeObject(noticeBoard, noticeBoardVO);
		noticeBoard.setDbTs(1);
		noticeBoard.setInstId(userSessionDetails.getInstId());
		noticeBoard.setBranchId(userSessionDetails.getBranchId());
		noticeBoard.setrCreId(userSessionDetails.getUserId());
		noticeBoard.setrModId(userSessionDetails.getUserId());
		noticeBoard.setDelFlag("N");
		if(noticeBoard.getInformParent()==null){
			noticeBoard.setInformParent("");
		}
		if(noticeBoard.getIsImportant()==null){
			noticeBoard.setIsImportant("");
		}
		noticeBoard.setNoticeSerialNo(AlphaSequenceUtil.generateAlphaSequence(
				ApplicationConstant.STRING_NOTICE_BOARD_SEQ, simpleIdGenerator
				.getNextId(SequenceConstant.NOTICE_BOARD_SEQUENCE,
						1)));
		noticeBoardDao.insertNoticeBoardRec(noticeBoard);
			
			// functional audit
						doAudit.doFunctionalAudit(userSessionDetails,
								AuditConstant.NOTICE_BOARD_INSERT, " ");
						logger.debug("Completed Functional Auditing");
		

	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateNoticeBoardDetailsRec(NoticeBoardVO noticeBoardVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws UpdateFailedException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, NoDataFoundException {
		logger.debug("inside update Notice Board method");
		NoticeBoard noticeBoard = new NoticeBoard();
		NoticeBoardKey noticeBoardKey = new NoticeBoardKey();
		// map the UIObject to Pojo
		commonBusiness.changeObject(noticeBoard, noticeBoardVO);
		noticeBoard.setInstId(userSessionDetails.getInstId());
		noticeBoard.setBranchId(userSessionDetails.getBranchId());
		noticeBoardKey.setInstId(userSessionDetails.getInstId());
		noticeBoardKey.setBranchId(userSessionDetails.getBranchId());
		noticeBoardKey.setNoticeSerialNo(noticeBoard.getNoticeSerialNo());
		NoticeBoard noticeBoardNew = noticeBoardDao.selectNoticeBoardRec(noticeBoardKey);
		NoticeBoard updateNoticeBoard=new NoticeBoard();
		commonBusiness.changeObject(updateNoticeBoard, noticeBoardNew);
		noticeBoardKey.setDbTs(updateNoticeBoard.getDbTs());
	
		
		
		updateNoticeBoard.setrModId(userSessionDetails.getUserId());
		//subjectMaster.setSub_Name(subjectMasterNew.getSub_Name());
		if(noticeBoard.getInformParent()==null){
			updateNoticeBoard.setInformParent("");
		}else{
			updateNoticeBoard.setInformParent(noticeBoard.getInformParent());
		}
		if(noticeBoard.getIsImportant()==null){
			updateNoticeBoard.setIsImportant("");
		}else{
			updateNoticeBoard.setIsImportant(noticeBoard.getIsImportant());
		}
		if(noticeBoard.getNoticeName()!=null){
			updateNoticeBoard.setNoticeName(noticeBoard.getNoticeName());
		}
		if(noticeBoard.getNoticeDesc()!=null){
			updateNoticeBoard.setNoticeDesc(noticeBoard.getNoticeDesc());
		}
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("checking  :"+dateUtil.getDateFormatByString(updateNoticeBoard.getFromDate()));
		System.out.println("checking  :"+dateUtil.getCurrentDateInDateDataType());
		
		
			
			try {
				if ((dateUtil.getDateFormatByString(updateNoticeBoard.getFromDate())
						.compareTo(ft.parse(dateUtil.getCurrentDate())) > 0)
						|| (dateUtil.getDateFormatByString(
								updateNoticeBoard.getFromDate()).compareTo(
										ft.parse(dateUtil.getCurrentDate())) == 0)) {
					System.out.println("from date eeeeeeeeeeeeif");
					System.out.println("from date eeeeeeeeeeee"+updateNoticeBoard.getFromDate());
					updateNoticeBoard.setFromDate(noticeBoard.getFromDate());
				}			
			if ((dateUtil.getDateFormatByString(updateNoticeBoard.getToDate())
					.compareTo(ft.parse(dateUtil.getCurrentDate()))> 0)
					|| (dateUtil.getDateFormatByString(updateNoticeBoard.getToDate())
							.compareTo(ft.parse(dateUtil.getCurrentDate())) == 0)) {
				System.out.println("to date eeeeeeeeeeee"+updateNoticeBoard.getToDate());
				updateNoticeBoard.setToDate(noticeBoard.getToDate());
			}
			
			if ((dateUtil.getDateFormatByString(updateNoticeBoard.getToDate())
					.compareTo(ft.parse(dateUtil.getCurrentDate()))< 0)
					) {
				updateNoticeBoard.setInformParent(noticeBoardNew.getInformParent());
				updateNoticeBoard.setIsImportant(noticeBoardNew.getIsImportant());
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("notice eeeeeeeeeeee"+updateNoticeBoard.toString());
		noticeBoardDao.updateNoticeBoardRec(updateNoticeBoard, noticeBoardKey);		
	
		
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.NOTICE_BOARD_UPDATE, " ");
		logger.debug("Completed Functional Auditing");

		// Database audit
		String oldRecString = noticeBoardNew.toStringForAuditNoticeBoardRecord();
		NoticeBoard noticeBoardForAudit = null;
		noticeBoardKey.setDbTs(0);
		try {
			noticeBoardForAudit = noticeBoardDao.selectNoticeBoardRec(noticeBoardKey);
		} catch (NoDataFoundException e) {
			logger.error("No data found  Exception occured in update student group master:");
			e.printStackTrace();
		}
		noticeBoardKey.setDbTs(noticeBoardForAudit.getDbTs());
		String newRecString = noticeBoardForAudit.toStringForAuditNoticeBoardRecord();
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.NOTICE_BOARD,
				noticeBoardKey.toStringForAuditNoticeBoardKey(),
				oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
				"");

		logger.debug("Completed Database Auditing");

	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteNoticeBoardDetailsRec(NoticeBoardVO noticeBoardVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		NoticeBoardKey noticeBoardKey = new NoticeBoardKey();
		commonBusiness.changeObject(noticeBoardKey, noticeBoardVO);

		
		noticeBoardKey.setInstId(userSessionDetails.getInstId());
		noticeBoardKey.setBranchId(userSessionDetails.getBranchId());

		NoticeBoard noticeBoardNew = noticeBoardDao.selectNoticeBoardRec(noticeBoardKey);
		NoticeBoard deleteNoticeBoard=new NoticeBoard();
		commonBusiness.changeObject(deleteNoticeBoard, noticeBoardNew);
		noticeBoardKey.setDbTs(noticeBoardNew.getDbTs());
		deleteNoticeBoard.setrModId(userSessionDetails.getUserId());
		deleteNoticeBoard.setDbTs(noticeBoardNew.getDbTs()+1);
		deleteNoticeBoard.setDelFlag("Y");
		
		noticeBoardDao.deleteNoticeBoardRec(deleteNoticeBoard, noticeBoardKey);			
			// functional audit
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.NOTICE_BOARD_DELETE, " ");
			logger.debug("Completed Functional Auditing");
			
			
			// Database audit
			
			String oldRecString = noticeBoardNew.toStringForAuditNoticeBoardRecord();
			
			doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
					TableNameConstant.NOTICE_BOARD,
					noticeBoardKey.toStringForAuditNoticeBoardKey(),
					oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, "",
					"");

			logger.debug("Completed Database Auditing");
		

	}
	@Override
	public void selectNoticeBoardList(NoticeBoardMasterVO noticeBoardMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {

		NoticeBoardListKey noticeboardListKey = new NoticeBoardListKey();
		if(noticeBoardMasterVO.getNoticeBoardSearchVO()!=null){
		commonBusiness.changeObject(noticeboardListKey, noticeBoardMasterVO.getNoticeBoardSearchVO());
		}
		noticeboardListKey.setInstId(userSessionDetails.getInstId());
		noticeboardListKey.setBranchId(userSessionDetails.getBranchId());
		List<NoticeBoard> noticeBoardList = noticeBoardListDao.getNoticeBoardList(noticeboardListKey);
		List<NoticeBoardVO> NoticeBoardVOs = new ArrayList<NoticeBoardVO>();

		for (int i = 0; i < noticeBoardList.size(); i++) {
			NoticeBoard noticeBoard = noticeBoardList.get(i);
			System.out.println("description in service dao"+noticeBoard.getNoticeDesc());
			NoticeBoardVO noticeBoardVO = new NoticeBoardVO();
			commonBusiness.changeObject(noticeBoardVO, noticeBoard);
			System.out.println("description in service vo"+noticeBoardVO.getNoticeDesc());
			noticeBoardVO.setRowId(i);
			NoticeBoardVOs.add(noticeBoardVO);
		}
		noticeBoardMasterVO.setNoticeBoardVOList(NoticeBoardVOs);
		
	}
	@Override
	public List<NoticeBoardVO> selectNoticeBoardListForDashBoard(
			NoticeBoardListKey noticeBoardListKey,UserSessionDetails userSessionDetails) throws NoDataFoundException {
		System.out.println("Inside Notice Service");
		String profileGroup=userSessionDetails.getProfileGroup();
		System.out.println("profile groupppppppppppppppp"+profileGroup);
		List<NoticeBoard> noticeBoardList = noticeBoardListDao.getNoticeBoardListForDashBoard(noticeBoardListKey,profileGroup);
		List<NoticeBoardVO> noticeBoardVOs = new ArrayList<NoticeBoardVO>();

		for (int i = 0; i < noticeBoardList.size(); i++) {
			NoticeBoard noticeBoard = noticeBoardList.get(i);
			NoticeBoardVO noticeBoardVO = new NoticeBoardVO();
			commonBusiness.changeObject(noticeBoardVO, noticeBoard);
			noticeBoardVO.setRowId(i);
			noticeBoardVOs.add(noticeBoardVO);
		}
		return noticeBoardVOs;
		
	}

}
