package com.jaw.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.admin.dao.InstituteMaster;
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
import com.jaw.core.controller.AcademicCalendarMasterVO;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.controller.TransportMasterVO;
import com.jaw.core.controller.TransportMaster_MasterVO;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarKey;
import com.jaw.core.dao.AcademicCalendarListKey;
import com.jaw.core.dao.TransportMaster;
import com.jaw.core.dao.TransportMasterKey;
import com.jaw.core.dao.ITransportMasterDAO;
import com.jaw.core.dao.ITransportMasterListDAO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.dao.StudentGroupMasterKey;
import com.jaw.core.dao.TransportMasterList;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

//Transport Master Service Class
@Service
public class TransportMasterService implements ITransportMasterService{
	// Logging
	Logger logger = Logger.getLogger(TransportMasterService.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	ITransportMasterDAO transportMasterDao;
	@Autowired
	ITransportMasterListDAO transportMasterListDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertTransportMasterRec(TransportMasterVO transportMrVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException {
		logger.debug("inside insert Transport Master method");
		TransportMaster transportMaster = new TransportMaster();
		// map the UIObject to Pojo
		commonBusiness.changeObject(transportMaster, transportMrVO);
		
		transportMaster.setDbTs(1);
		transportMaster.setInstId(userSessionDetails.getInstId());
		transportMaster.setBranchId(userSessionDetails.getBranchId());
		transportMaster.setrCreId(userSessionDetails.getUserId());
		transportMaster.setAcademicYear(userSessionDetails.getCurrAcTerm());
		transportMaster.setrModId(userSessionDetails.getUserId());
		transportMaster.setDelFlag("N");	
		transportMaster.setPickupPointId(AlphaSequenceUtil.generateAlphaSequence(
				ApplicationConstant.STRING_TRANSPORT_MASTER_SEQ,
				simpleIdGenerator.getNextId(
						SequenceConstant.TRANSPORT_MASTER_SEQUENCE, 1)));
		
		transportMasterDao.insertTransportMasterRec(transportMaster);
		
		
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateTransportMasterRec(TransportMasterVO transportMrVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		logger.debug("inside update Transport Master method");
		TransportMaster TransportMaster = new TransportMaster();
		TransportMasterKey transportMasterKey = new TransportMasterKey();
		// map the UIObject to Pojo
		commonBusiness.changeObject(TransportMaster, transportMrVO);
		transportMasterKey.setPickupPointId(TransportMaster.getPickupPointId());
		transportMasterKey.setInstId(userSessionDetails.getInstId());
		transportMasterKey.setBranchId(userSessionDetails.getBranchId());
		TransportMaster transportMasterNew=null;
		try {
			transportMasterNew=transportMasterDao.selectTransportMasterRec(transportMasterKey);
		} catch (NoDataFoundException e) {
			logger.error("No data found  Exception occured in update Transport master:");
			e.printStackTrace();
		}
		TransportMaster updateTransportMaster=new TransportMaster();
		commonBusiness.changeObject(updateTransportMaster, transportMasterNew);
		transportMasterKey.setDbTs(transportMasterNew.getDbTs());
		updateTransportMaster.setInstId(userSessionDetails.getInstId());
		updateTransportMaster.setBranchId(userSessionDetails.getBranchId());
		updateTransportMaster.setrModId(userSessionDetails.getUserId());
		updateTransportMaster.setAmount(transportMasterNew.getAmount());
		updateTransportMaster.setDelFlag("N");
		
		transportMasterDao.updateTransportMasterRec(updateTransportMaster, transportMasterKey);
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.TRANSPORT_MASTER_UPDATE, " ");
		logger.debug("Completed Functional Auditing");
		
		// Database audit
				String oldRecString = transportMasterNew
						.toStringForAuditTransportMasterRecord();
				TransportMaster transportMasterForAudit = null;
				transportMasterKey.setDbTs(0);
				try {
					transportMasterForAudit=transportMasterDao.selectTransportMasterRec(transportMasterKey);
				} catch (NoDataFoundException e) {
					logger.error("No data found  Exception occured in update Transport master:");
					e.printStackTrace();
				}						
				transportMasterKey.setDbTs(transportMasterForAudit.getDbTs());			
				String newRecString = transportMasterForAudit
						.toStringForAuditTransportMasterRecord();
				doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
						TableNameConstant.TRANSPORT_MASTER,
						transportMasterKey.toStringForAuditTransportMasterKey(),
						oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
						"");

				logger.debug("Completed Database Auditing");
		
	}
	@Override
	public TransportMaster_MasterVO selectTransportMasterList(
			TransportMaster_MasterVO TransportMaster_MasterVO,UserSessionDetails userSessionDetails) throws NoDataFoundException {
		
		TransportMasterKey TransportMasterKey=new TransportMasterKey();
		TransportMasterKey.setInstId(userSessionDetails.getInstId());
		TransportMasterKey.setBranchId(userSessionDetails.getBranchId());
		List<TransportMasterList> trnsMtrList = transportMasterListDao.getTransportMasterList(TransportMasterKey);
		List<TransportMasterVO> crsMtrVOs = new ArrayList<TransportMasterVO>();

		for (int i = 0; i < trnsMtrList.size(); i++) {
			TransportMasterList TransportMaster = trnsMtrList.get(i);
			TransportMasterVO TransportMasterVO = new TransportMasterVO();
			commonBusiness.changeObject(TransportMasterVO, TransportMaster);
			TransportMasterVO.setRowId(i);
			crsMtrVOs.add(TransportMasterVO);
			System.out.println("Added Transport master object in list:"+TransportMasterVO);
		}
		TransportMaster_MasterVO.setTrnsMtrVOList(crsMtrVOs);
		return TransportMaster_MasterVO;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteTransportMasterRec(TransportMasterVO TransportMasterVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		TransportMasterKey TransportMasterKey = new TransportMasterKey();
		commonBusiness.changeObject(TransportMasterKey, TransportMasterVO);
	
		TransportMasterKey.setInstId(userSessionDetails.getInstId());
		TransportMasterKey.setBranchId(userSessionDetails.getBranchId());		
		// get Data's from DB for dbts value
		TransportMaster TransportMaster = transportMasterDao.selectTransportMasterRec(TransportMasterKey);
		TransportMaster deleteTransportMaster=new TransportMaster();
		commonBusiness.changeObject(deleteTransportMaster, TransportMaster);
		TransportMasterKey.setDbTs(TransportMaster.getDbTs());
		deleteTransportMaster.setrModId(userSessionDetails.getUserId());
		deleteTransportMaster.setDbTs(TransportMaster.getDbTs()+1);
		deleteTransportMaster.setDelFlag("Y");
		transportMasterDao.deleteTransportMasterRec(deleteTransportMaster, TransportMasterKey);
		// functional audit
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.TRANSPORT_MASTER_DELETE, " ");
		logger.debug("Completed Functional Auditing");
		
		// Database audit
		
		String oldRecString = TransportMaster
				.toStringForAuditTransportMasterRecord();
		
		String newRecString = deleteTransportMaster
				.toStringForAuditTransportMasterRecord();
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.TRANSPORT_MASTER,
				TransportMasterKey.toStringForAuditTransportMasterKey(),
				oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, newRecString,
				"");

		logger.debug("Completed Database Auditing");
		
	}

}
