package com.jaw.batch.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.batch.controller.BatchDataUploadVO;
import com.jaw.batch.dao.BatchPgms;
import com.jaw.batch.dao.BatchStatus;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.ExcelRejectException;
import com.jaw.common.exceptions.batch.InValidCellValue;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.common.files.service.FileSaveHelper;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;


@Service
@Transactional(rollbackFor = Exception.class)
public class BatchUploadService implements IFileUploadService {
	Logger logger = Logger.getLogger(BatchUploadService.class);
	@Autowired
	private BatchHelper batchHelper;
	@Autowired
	private FileSaveHelper fileSaveHelper;
	@Autowired
	IBatchPgmsService batchPgmsService;
	@Autowired
	IIdGeneratorService simpleIdGenerator;
	
	@Override

	public String fileupload(String fileExtn, BatchDataUploadVO fileVO,
			ApplicationCache applicationCache, ServletContext servletContext,
			UserSessionDetails userSessionDetails, String dataType)
			throws IllegalAccessException, InvocationTargetException,
			IOException, InValidCellValue, DataIntegrityException,
			DuplicateEntryException, DatabaseException, NoRecordFoundException,
			ExcelRejectException, NoDataFoundException,FileNotSaveException{
		
		final BatchStatus batchStatus = new BatchStatus();
		
		fileVO.setBatchName(ApplicationConstant.BATCH_PGMS_EXCEL_EXPORT);
		if ((fileVO.getInstId()).equals("")) {
			fileVO.setInstId(userSessionDetails.getInstId());
			batchStatus.setInstId(userSessionDetails.getInstId());
		}else{
			batchStatus.setInstId(fileVO.getInstId());
		}		
		if ((fileVO.getBranchId()==null)||(fileVO.getBranchId()).equals("")) {			
			fileVO.setBranchId(userSessionDetails.getBranchId());
			batchStatus.setBranchId(userSessionDetails.getBranchId());
		}else{
			batchStatus.setBranchId(fileVO.getBranchId());
		}
		BatchPgms batch = null;
		batch = batchPgmsService.getBatchPgmsRec(fileVO);
		logger.info("batch :"+batch);		
		final String[] xmlNames = batch.getXmlFileName().split(",");
		final String[] sheetNames = batch.getSheetName().split(",");
		logger.info("xml names :"+batch.getXmlFileName());
		logger.info("ApplicationConstant.XML_FILE_LOCATION :"+ApplicationConstant.XML_FILE_LOCATION);
		for (int index = 0; index < xmlNames.length; index++) {
			String xmlName = xmlNames[index];
			String fileName = ApplicationConstant.XML_FILE_LOCATION
					.concat(xmlName);
			logger.info("file name :"+fileName);
			String realPath = servletContext.getRealPath(fileName);
			logger.info("real path :"+realPath);
			File fileExistCheck = new File(realPath);
			if (!fileExistCheck.exists()) {
				logger.info("file doesn't exists..!");
				throw new DatabaseException();
			}
		}

		final String batchSerialNo = simpleIdGenerator.getNextId(
				SequenceConstant.BATCH_SERIAL_SEQUENCE, 1).toString();
		
		batchStatus.setUpldDataType(fileVO.getDataType());		
		batchHelper.initializeBatch(batchStatus,							
				batchSerialNo,
				userSessionDetails,ApplicationConstant.BATCH_PGMS_EXCEL_IMPORT);		
		
		String srlNo = batchHelper.fileToObject(fileExtn, fileVO, applicationCache,
				servletContext, userSessionDetails, dataType,xmlNames,sheetNames,batchStatus,batchSerialNo);
	//	String destDir = fileSaveHelper.getDestinationDirectory(fileVO.getInstId(), fileVO.getBranchId(), ApplicationConstant.BATCH_OPERATION, "", "","");
	//	destDir =FileSaveHelper.fileLocation(servletContext)+ destDir+ApplicationConstant.BATCH_FILE_NAME_SEPERATOR_2+srlNo+ ApplicationConstant.BATCH_FILE_NAME_SEPERATOR_1+fileVO.getUploadFile().getOriginalFilename();	
		//destDir =FileSaveHelper.fileLocation(servletContext)+ destDir+ApplicationConstant.BATCH_FILE_NAME_SEPERATOR_2+srlNo+ ApplicationConstant.BATCH_FILE_NAME_SEPERATOR_1+fileVO.getUploadFile().getOriginalFilename();
		String destDir=fileVO.getUploadFile().getOriginalFilename();
		logger.debug("Excel File Storing Location using properties:"+destDir);
		fileSaveHelper.saveMultipartToDisk(destDir, fileVO.getUploadFile());		
		return srlNo;
	}

}