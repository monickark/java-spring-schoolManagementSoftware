package com.jaw.batch.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.batch.controller.DownLoadFileVO;
import com.jaw.batch.dao.DownloadFileKey;
import com.jaw.batch.dao.IDownloadBatchFileDao;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.PropertyManagementConstant;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.common.files.service.FileSaveHelper;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

@Service
public class DownloadFileService implements IDownloadFileService {
	@Autowired
	IDownloadBatchFileDao downLoadFileDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	
	Logger logger = Logger.getLogger(DownloadFileService.class);

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<DownLoadFileVO> downloadFile(DownLoadFileVO downloadFile,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache,ServletContext servletContext,String profileGrp) throws NoDataFoundException, FileNotFoundException, IOException {
		InputStream imagefile = null;
		List<DownLoadFileVO> listOfFileDwnld = new ArrayList<DownLoadFileVO>();
		downloadFile.setInstId(userSessionDetails.getInstId());
		
		DownloadFileKey downloadFileKey = new DownloadFileKey();
		commonBusiness.changeObject(downloadFileKey, downloadFile);
		
		List<FileMaster> fileMaster = downLoadFileDao
				.downloadFile(downloadFileKey);
		
		
		 String stuFileStorage = null;
			try {
				String prpmConstant = null;
				if(profileGrp.equals(ApplicationConstant.PG_STUDENT)){
					prpmConstant = PropertyManagementConstant.STU_FILE_STORAGE;
				}else if(profileGrp.equals(ApplicationConstant.PG_STAFF)){
					prpmConstant = PropertyManagementConstant.STF_FILE_STORAGE;
				}
				stuFileStorage=	propertyManagementUtil.getPropertyValue(applicationCache, downloadFileKey.getInstId(),downloadFileKey.getBranchId(), prpmConstant);
			} catch (PropertyNotFoundException e) {
				logger.error("Prpm value for File storage option not found");
				e.printStackTrace();
			}			
			
			
			for(FileMaster fileObject : fileMaster){
				if((!fileObject.getFileType().split("_")[0].equals(ApplicationConstant.PHOTO_CONSTANT))&&(!stuFileStorage.equals(ApplicationConstant.FILE_IN_DB))){				
					String filePath = FileSaveHelper.fileLocation(servletContext)+fileObject.getFilepath();				
					imagefile = new FileInputStream(filePath);
					fileObject.setInputStream(imagefile);
				}	
			}
		
		for (FileMaster fileMas : fileMaster) {
			DownLoadFileVO fileVO = new DownLoadFileVO();
			commonBusiness.changeObject(fileVO, fileMas);
			listOfFileDwnld.add(fileVO);
		}

		return listOfFileDwnld;

	}

}
