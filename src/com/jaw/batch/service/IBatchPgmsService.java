package com.jaw.batch.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jaw.batch.controller.BatchDataUploadVO;
import com.jaw.batch.controller.ExportTemplateVO;
import com.jaw.batch.dao.BatchPgms;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;


public interface IBatchPgmsService {
	
	public BatchPgms getBatchPgmsRec(ExportTemplateVO importDataVo,UserSessionDetails usrSession)  throws NoRecordFoundException;
	public BatchPgms getBatchPgmsRec(BatchDataUploadVO exportDataVo) throws NoRecordFoundException;	
//	public void generateExcel(String createtemp,ExportTemplateVO exportDataVO,SessionCache sessionCache,HttpSession session,HttpServletResponse response) throws NoRecordFoundException, IOException;

}
