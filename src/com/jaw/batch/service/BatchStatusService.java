package com.jaw.batch.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jaw.batch.controller.BatchStatusSearchVO;
import com.jaw.batch.controller.BatchStatusVO;
import com.jaw.batch.controller.ExportTemplateVO;
import com.jaw.batch.dao.BatchPgms;
import com.jaw.batch.dao.BatchStatus;
import com.jaw.batch.dao.IBatchStatusDao;
import com.jaw.batch.dao.IBatchStatusListDao;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;

//Service class for BatchStatus
@Service
public class BatchStatusService implements IBatchStatusService {
	//Logging
	Logger logger = Logger.getLogger(BatchStatusService.class);
	@Autowired
	IBatchStatusListDao batchStatusListDao;

	@Autowired
	IBatchStatusDao batchStatusDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IBatchPgmsService batchPgmService;
	
	
	
	//Method to get the list of BatchStatus Reocrds
	@Override
	public List<BatchStatusVO> getBatchStatusList(BatchStatusSearchVO batchStatusVO,UserSessionDetails userSessionDetails) throws NoDataFoundException {		
		logger.info("For Advanced Search,In service,to change UI VO into DB object and calling DAO layer");
		List<BatchStatusVO> listOfBatchStatus = new ArrayList<BatchStatusVO>();
		batchStatusVO.setInstId(userSessionDetails.getInstId());	
		List<BatchStatus> batchStatusList = batchStatusListDao.retrieveBatchStatus(batchStatusVO);		
		int i = 0;
		for(BatchStatus batchStatusObj : batchStatusList){			
			BatchStatusVO batchVO = new BatchStatusVO();	
			commonBusiness.changeObject(batchVO, batchStatusObj);
			batchVO.setRowid(i);
			i++;
			listOfBatchStatus.add(batchVO);
		}
		return listOfBatchStatus;
	}

	
	//Method to get the single BatchStatus Record
	@Override
	public BatchStatusVO getBatchStatusRecord(BatchStatusSearchVO batchStatusVO,UserSessionDetails userSessionDetails,String batchSrlNo) throws NoRecordFoundException {
		logger.info("For Search by batch id,In service,to change UI VO into DB object and calling DAO layer");
		batchStatusVO.setBatchSrlNo(batchSrlNo);
		batchStatusVO.setInstId(userSessionDetails.getInstId());
		batchStatusVO.setBranchId(userSessionDetails
				.getBranchId());
		return getBatchStatusRecDaoCall(batchStatusVO);
	}


	@Override
	public BatchStatusVO getBatchStatusRecord(BatchStatusSearchVO batchStatysVO)
			throws NoRecordFoundException {		
		return getBatchStatusRecDaoCall(batchStatysVO);
	}
	
	
	public BatchStatusVO getBatchStatusRecDaoCall(BatchStatusSearchVO batchStatysVO) throws NoRecordFoundException{
		String batchId = batchStatysVO.getBatchSrlNo();		
		BatchStatusVO batchStatusVORec = new BatchStatusVO();
		BatchStatus batchStatusRec = batchStatusDao.retrieveBatchStatusRec(batchId);
		commonBusiness.changeObject(batchStatusVORec, batchStatusRec);
		return batchStatusVORec;
		
	}
	

	@Override
	public BatchPgms getBatchPgmRec(ExportTemplateVO exportDataVO,
			UserSessionDetails usrSession) throws NoRecordFoundException {
		return batchPgmService.getBatchPgmsRec(exportDataVO,usrSession);
	}
	

}
