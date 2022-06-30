package com.jaw.batch.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.batch.controller.BatchDataUploadVO;
import com.jaw.batch.controller.ExportTemplateVO;
import com.jaw.batch.dao.BatchPgms;
import com.jaw.batch.dao.BatchPgmsKey;
import com.jaw.batch.dao.IBatchPgmsRecordDao;
import com.jaw.common.batch.util.ExcelUtils;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

@Service
public class BatchPgmsService implements IBatchPgmsService {
	//Logging
		Logger logger = Logger.getLogger(BatchPgmsService.class);
	@Autowired
	IBatchPgmsRecordDao batchPgmsRecDao;
	@Autowired
	CommonBusiness commonBusiness;	
	
	@Override

	//Method to get the BatchPgms record for ImportData 
	public BatchPgms getBatchPgmsRec(ExportTemplateVO importDataVO,UserSessionDetails usrSession)
			throws NoRecordFoundException {		
		logger.info("Get the BatchPgms Record for ImportDataVO");
		if((importDataVO.getInstId()).equals("")){
			
			importDataVO.setInstId(usrSession.getInstId());
		}
			
		if((importDataVO.getBranchId()).equals("")){
			
			importDataVO.setBranchId(usrSession.getBranchId());
		}	
		BatchPgmsKey batchPgmKey = new BatchPgmsKey();
		commonBusiness.changeObject(batchPgmKey, importDataVO);	
		BatchPgms batchList =  batchPgmsRecDao.retrieveBatchPgmsRec(batchPgmKey);		
		return batchList;
	}
	
//Method to get the BatchPgms Rec for EXportData
	@Override
	public BatchPgms getBatchPgmsRec(BatchDataUploadVO exportDataVo)
			throws NoRecordFoundException {
		logger.info("Get the BatchPgms Record for ExportDataVO");
		BatchPgmsKey batchPgmKey = new BatchPgmsKey();
		commonBusiness.changeObject(batchPgmKey, exportDataVo);	
		BatchPgms batchList =  batchPgmsRecDao.retrieveBatchPgmsRec(batchPgmKey);
		return batchList;
	}
	
		
	
		

}
