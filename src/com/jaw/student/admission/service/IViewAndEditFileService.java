package com.jaw.student.admission.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContext;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionDetailsVO;

public interface IViewAndEditFileService {
	public void saveFile(AdmissionDetailsVO admisVO,UserSessionDetails userDetails,ApplicationCache applicationCache,ServletContext servletContext)throws IOException, IllegalAccessException, InvocationTargetException, DatabaseException, DuplicateEntryException, FileNotFoundInDatabase, DeleteFailedException, FileNotSaveException;
}
