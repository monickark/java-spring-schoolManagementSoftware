package com.jaw.batch.service;

import java.util.List;

import com.jaw.batch.controller.BatchStatusSearchVO;
import com.jaw.batch.controller.BatchStatusVO;
import com.jaw.batch.controller.ExportTemplateVO;
import com.jaw.batch.dao.BatchPgms;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;


//Interface for the BatchStatus service 
public interface IBatchStatusService {
	
	public List<BatchStatusVO> getBatchStatusList(BatchStatusSearchVO batchStatusVO,UserSessionDetails userSessionDetails) throws NoDataFoundException;
	public BatchStatusVO getBatchStatusRecord(BatchStatusSearchVO batchStatysVO,UserSessionDetails userSessionDetails,String batchSrlNo) throws NoRecordFoundException;
	public BatchStatusVO getBatchStatusRecord(BatchStatusSearchVO batchStatysVO) throws NoRecordFoundException;	
	public BatchPgms getBatchPgmRec(ExportTemplateVO exportDataVO,UserSessionDetails usrSession) throws NoRecordFoundException;
	

}
