package com.jaw.common.files.service;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.admin.service.InstituteMasterService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.common.files.dao.IFileMasterDao;
import com.jaw.common.files.dao.IFileMasterListDao;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

//Service class for FileMaster 
@Service
public class FileMasterService implements IFileMasterService {

	// Logging
	Logger logger = Logger.getLogger(InstituteMasterService.class);

	@Autowired
	IFileMasterDao fileMasterDao;
	@Autowired
	private IFileMasterListDao fileMasterListDao;
	@Autowired
	private FileSaveHelper fileSaveHelper;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;

	
	@Override
	public FileMaster getFile(String instId, String branchId, String linkId,
			String type,String srlNo,ApplicationCache applicationCache,ServletContext servletContext) throws FileNotFoundInDatabase, IOException {
		logger.debug("inside getFile for User profile");
		BufferedImage image = null;		
		FileMaster fileMaster = null;
		StringBuffer fileName = new StringBuffer();
		fileName.append(type.concat("_").concat(linkId));
		InputStream imagefile = null;
		 fileMaster = fileMasterDao.getSingleFile(instId, branchId,
				linkId, type,srlNo);		
		 String stuFileStorage = null;
			try {
				stuFileStorage=	propertyManagementUtil.getPropertyValue(applicationCache, instId,branchId, PropertyManagementConstant.STU_FILE_STORAGE);
			} catch (PropertyNotFoundException e) {
				logger.error("Prpm value for File storage option not found");
				e.printStackTrace();
			}			
			
			if((!fileMaster.getFileType().split("_")[0].equals(ApplicationConstant.PHOTO_CONSTANT))&&(!stuFileStorage.equals(ApplicationConstant.FILE_IN_DB))){						
				imagefile = new FileInputStream(FileSaveHelper.fileLocation(servletContext)+fileMaster.getFilepath());
				fileMaster.setInputStream(imagefile);
			}						
		return fileMaster;
	}

	@Override
	public List<FileMaster> getListOfFilesFileId(
			UserSessionDetails userSessionDetails, String linkId) {
		logger.debug("inside getListOfFilesFileId to fetch List of files");
		List<FileMaster> fileMaster = fileMasterListDao.fileTypeList(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), linkId);

		return fileMaster;
	}

}
