package com.jaw.staff.service;

import javax.servlet.ServletContext;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.controller.FileTypeVO;

public interface IStaffFilesService {


	void getFileType(com.jaw.staff.controller.FileTypeVO fileTypeVO,
			UserSessionDetails userSessionDetails, String staffId, ApplicationCache applicationCache, String string)
			throws NoDataFoundException;

	void uploadStaff(FileTypeVO fileTypeVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,ServletContext servletContext) throws DuplicateEntryException,
			 DatabaseException, IllegalStateException, FileNotSaveException	;

	void getMenuOption(FileTypeVO fileTypeVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache, String url);

}
