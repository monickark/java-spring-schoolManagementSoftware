package com.jaw.core.dao;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IClassTeacherAllotmentListDao {
	
	List<AllottedClassTeachers> getClassTeachersListForSchool(String acTerm,
			String courseId, String instId, String branchid)
			throws NoDataFoundException;


	List<AllottedClassTeachers> getClassTeachersListForCollege(String acTerm,
			String courseId, String instId, String branchid)
			throws NoDataFoundException;


	Map<String, String> getStudentBatchList(String stGroup, String instId,
			String branchid, String acTerm) throws NoDataFoundException;

}
