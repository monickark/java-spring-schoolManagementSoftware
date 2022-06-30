package com.jaw.batch.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletContext;
import com.jaw.batch.controller.BatchDataUploadVO;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.ExcelRejectException;
import com.jaw.common.exceptions.batch.InValidCellValue;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IFileUploadService {
	public String fileupload(String fileExtn, BatchDataUploadVO fileVO,
			ApplicationCache applicationCache, ServletContext servletContext,
			UserSessionDetails userSessionDetails, String dataType)
			throws IllegalAccessException, InvocationTargetException,
			IOException, InValidCellValue, DataIntegrityException,
			DuplicateEntryException, DatabaseException, NoRecordFoundException,
			ExcelRejectException, NoDataFoundException, FileNotSaveException;
}
