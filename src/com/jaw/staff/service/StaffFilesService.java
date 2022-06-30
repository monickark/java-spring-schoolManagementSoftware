package com.jaw.staff.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.common.files.service.FileMasterHelper;
import com.jaw.common.files.service.IFileMasterService;
import com.jaw.common.util.MenuProfileUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.controller.FileTypeVO;

//Service class for FileMaster 
@Service
public class StaffFilesService implements IStaffFilesService {

	// Logging
	Logger logger = Logger.getLogger(StaffFilesService.class);

	@Autowired
	FileMasterHelper fileMasHelper;
	@Autowired
	IFileMasterService fileMasterService;
	@Autowired
	FileMasterHelper fileMasterHelper;
	@Autowired
	MenuProfileUtil menuProfileUtil;
	@Override
	public void getFileType(FileTypeVO fileTypeVO,
			UserSessionDetails userSessionDetails, String staffId,ApplicationCache applicationCache,String url)
			throws NoDataFoundException {
		
		fileMasterHelper.fileTypeList(applicationCache,
				fileTypeVO, userSessionDetails);
		getMenuOption(fileTypeVO, userSessionDetails, applicationCache, url);
	}
	
	@Override
	public void getMenuOption(FileTypeVO fileTypeVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,String url){
	
		
		fileTypeVO.setMenuOption(menuProfileUtil.getMenuOption(userSessionDetails, url,
				applicationCache));
		
		
	}
	@Override
	public void uploadStaff(FileTypeVO fileTypeVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,ServletContext servletContext) throws DatabaseException, DuplicateEntryException, IllegalStateException, FileNotSaveException  {
	
	
		fileMasterHelper.fileUpload(applicationCache, fileTypeVO, userSessionDetails,ApplicationConstant.PG_PARENT,servletContext);
		
		
	}


}
