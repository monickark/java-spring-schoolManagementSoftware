package com.jaw.batch.service;

import java.io.IOException;

import javax.servlet.ServletContext;

import com.jaw.batch.controller.BatchFileUploadVO;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IBatchFileUploadService {
	public String batchFileInsert(final BatchFileUploadVO batchFileUpload,final UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,String profileGrp,ServletContext servletContext) throws DatabaseException, IllegalStateException, IOException, FileNotSaveException;	
}
