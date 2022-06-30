package com.jaw.student.service;

import java.util.List;

import javax.servlet.ServletContext;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.controller.StudentMasterListVO;
import com.jaw.student.controller.StudentSearchVO;

public interface IStudentMasterService {
	public List<StudentMasterListVO> findStudent(StudentSearchVO studentSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;
	
	/*StudentMaster getStudentMaster(String studentAdmisNo, AdmissionDetailsVO admissionDetailsVO,
			UserSessionDetails userSessionDetails, ApplicationCache applicationCache)
			throws PropertyNotFoundException, NoDataFoundException;*/
	
	List<StudentMasterListVO> retrieveStudent(String studentAdmisNo,
			UserSessionDetails userSessionDetails, ApplicationCache applicationCache)
			throws PropertyNotFoundException, NoDataFoundException;
	
	
	public void editFiles(ApplicationCache applicationCache,AdmissionDetailsVO admisVo,UserSessionDetails sessionDetails,ServletContext servletContext) throws DatabaseException, DuplicateEntryException, IllegalStateException, FileNotSaveException;
	
}