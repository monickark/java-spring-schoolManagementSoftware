package com.jaw.common.files.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;

import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

//Interface for FileMaster
@Service
public interface IFileMasterService {

	public FileMaster getFile(String instId, String branchId, String linkId,
			String type,String srlNo,ApplicationCache applicationCache,ServletContext servletContext) throws FileNotFoundInDatabase, FileNotFoundException, IOException;

	public List<FileMaster> getListOfFilesFileId(
			UserSessionDetails userSessionDetails, String linkId);

}
