package com.jaw.batch.service;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.batch.controller.FileTransferVO;
import com.jaw.batch.dao.BatchStatus;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
@Service
public class FileTranDBToDiskService implements IFileTranDBToDiskService {
	
	@Autowired
	IIdGeneratorService simpleIdGenerator;
	@Autowired
	FileTransferHelper fileTransferHelper;
	@Autowired
	BatchHelper batchHelper;
	@Autowired
	CommonBusiness commonBusiness;
	
	
	@Override
	public String tranferingFilesToDisk(FileTransferVO fileTransferVO,UserSessionDetails userSessionDetails,
			ServletContext servletContext,ApplicationCache applicationCache) throws DatabaseException{
		
		BatchStatus batchStatus = new BatchStatus();
		fileTransferVO.setInstId(userSessionDetails.getInstId());
		commonBusiness.changeObject(batchStatus, fileTransferVO);
		
		final String batchSerialNo = simpleIdGenerator.getNextId(
				SequenceConstant.BATCH_SERIAL_SEQUENCE, 1).toString();	
		
		batchHelper.initializeBatch(batchStatus,batchSerialNo,userSessionDetails,fileTransferVO.getTypeOfOpt());
		
		if(fileTransferVO.getTypeOfOpt().equals(ApplicationConstant.DB_TO_DISK_CONSTANT)){
			fileTransferHelper.transferFileFromDBToDisk(fileTransferVO.getInstId(),fileTransferVO.getBranchId(),servletContext,
					applicationCache,userSessionDetails, batchSerialNo);
		}else if(fileTransferVO.getTypeOfOpt().equals(ApplicationConstant.DISK_TO_DB_CONSTANT)){		
			fileTransferHelper.transferFileFromDiskToDB(fileTransferVO.getInstId(),fileTransferVO.getBranchId(),servletContext,
					applicationCache,userSessionDetails, batchSerialNo);
		}
		
		
		
		return batchSerialNo;
		
		
	}
}
