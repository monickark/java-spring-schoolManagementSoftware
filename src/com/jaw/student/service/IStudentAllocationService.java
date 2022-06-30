package com.jaw.student.service;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.controller.StudentAllocationVO;
import com.jaw.student.controller.StudentSearchVO;

public interface IStudentAllocationService {
	public List<StudentMaster> studentAllocList(
			StudentAllocationVO studentAllocationVO,UserSessionDetails userSessionDetails) throws NoDataFoundException;

	public void sectionAllocation(String[] studentRegNo, String section,UserSessionDetails userSessionDetails,String acy,String[] dbtsList,String[] sectionList
			,String course,String term)	throws DuplicateEntryException, DatabaseException;

	public List<StudentMaster> findStudent(StudentSearchVO studentSearchVO) throws NoDataFoundException;

	public StudentMaster getStudentMaster(String admisno,
			AdmissionDetailsVO admissionDetailsVO) throws NoDataFoundException;

	public List<StudentMaster> retrieveStudent(String studentAdmisNo) throws NoDataFoundException;
	
	public Map<String,String> termList(String instid,String branchId,String courseId);

}
