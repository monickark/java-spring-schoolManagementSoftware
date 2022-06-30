package com.jaw.batch.service;

import javax.servlet.ServletContext;

import com.jaw.batch.controller.FileTransferVO;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IFileTranDBToDiskService {
	public String tranferingFilesToDisk(FileTransferVO fileTransferVO,UserSessionDetails userSessionDetails,
			ServletContext servletContext,ApplicationCache applicationCache) throws DatabaseException;
	

}
