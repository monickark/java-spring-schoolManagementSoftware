package com.jaw.batch.service;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jaw.batch.controller.BatchFileUploadVO;
import com.jaw.batch.dao.BatchStatus;
import com.jaw.batch.dao.IBatchStatusDao;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
@Service
public class BatchFileUploadService implements IBatchFileUploadService {
	Logger logger = Logger.getLogger(BatchFileUploadService.class);
@Autowired
CommonBusiness commonBusiness;
@Autowired
IIdGeneratorService simpleIdGenerator;
@Autowired
IBatchStatusDao batchStatusDao;

@Autowired
BatchHelper batchHelper;
@Autowired
BatchFileUploadHelper batchFileUplfHelper;
	@Override
	public String batchFileInsert(final BatchFileUploadVO batchFileUpload,final UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,String profileGrp,ServletContext servletContext) throws DatabaseException, IllegalStateException, IOException, FileNotSaveException {
		
		
		BatchStatus batchStatus = new BatchStatus();

		//Check Branch Name for Institute Specific
		if(batchFileUpload.getBranchId().equals("Institute Specified")){
			batchFileUpload.setBranchId(userSessionDetails.getInstId());
		}
			
		commonBusiness.changeObject(batchStatus, batchFileUpload);
		
		final String batchSerialNo = simpleIdGenerator.getNextId(
				SequenceConstant.BATCH_SERIAL_SEQUENCE, 1).toString();	
		
		batchHelper.initializeBatch(batchStatus,batchSerialNo,userSessionDetails,ApplicationConstant.BATCH_PGMS_FILE_IMPORT);
		
		//Batch of files upload using thread
		batchFileUplfHelper.uploadBatchOfFiles(batchFileUpload, batchSerialNo, userSessionDetails, applicationCache, batchStatus,profileGrp,servletContext);
			
		return batchSerialNo;
		
	}
	

}
