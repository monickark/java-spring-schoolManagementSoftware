package com.jaw.student.service;

import java.io.IOException;

import javax.servlet.ServletContext;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.student.admission.controller.FileTypeVO;
import com.jaw.student.admission.dao.StudentMaster;

public interface IViewProfileService {
	
	public void viewProfilEdit(ApplicationCache applicationCache,
			AdmissionDetailsVO admissionDetailsVO,
			UserSessionDetails userSessionDetails, String action,ServletContext servletContext)
			throws IOException, DuplicateEntryException,
			FileNotFoundInDatabase, DatabaseException,
			TableNotSpecifiedForAuditException, PropertyNotFoundException, UpdateFailedException, NoDataFoundException, DeleteFailedException, IllegalStateException, FileNotSaveException;
	
	public FileTypeVO getFileType(UserSessionDetails userSessionDetails,
			String studentAdmisNo) throws NoDataFoundException;
	
	StudentMaster getStudentMaster(String studentAdmisNo, AdmissionDetailsVO admissionDetailsVO,
			UserSessionDetails userSessionDetails, ApplicationCache applicationCache)
			throws PropertyNotFoundException, NoDataFoundException;
	
	void viewStudentDetails(AdmissionDetailsVO viewAdmis, UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws PropertyNotFoundException, NoDataFoundException;
	
}
