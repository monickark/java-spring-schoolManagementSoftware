package com.jaw.student.admission.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.InsertFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UnableCreateParentPassword;
import com.jaw.common.exceptions.UnableCreateParentUserId;
import com.jaw.common.exceptions.UnableCreateStudentPassword;
import com.jaw.common.exceptions.UnableCreateStudentUserId;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.core.dao.CourseSubLinkKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionDetailsVO;

public interface IAdmissionService {

	public void newAdmission(ApplicationCache applicationCache,
			AdmissionDetailsVO admissionDetailsUiVo,
			UserSessionDetails userSessionDetails,ServletContext servletContext) throws IOException,
			DuplicateEntryException, DatabaseException, FileNotFoundInDatabase,
			NumberFormatException, PropertyNotFoundException, InsertFailedException,FileNotSaveException, UnableCreateParentUserId, UnableCreateParentPassword, UnableCreateStudentUserId, UnableCreateStudentPassword;
	
	public String duplicateParentId(String parentId,UserSessionDetails userSessionDetails);
	
	public String duplicateAdmisNo(String admisNo,UserSessionDetails userSessionDetails);
	
	public Map<String, Map<String,String>> getSubFromCourseAndTrm(String studentGrpId,
			String courseId,String trmId,UserSessionDetails userSessionDetails) throws NoDataFoundException ;	
	public String getStudentGrpId(UserSessionDetails userSessionDetails,String courseId,String termId,String secId);
	public Boolean courseVariantCheckWithCourse(String courseId,UserSessionDetails userSessionDetails);
	
	public String getStuGrpIdForSecList(String admisNo,UserSessionDetails userSessionDetails) ;

	String getNextAdmisNo(AdmissionDetailsVO admissionDetailsVO, UserSessionDetails userSessionDetails);


}
