package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IClassTeacherAndStandardSectionListDao {

	List<ClassTeacherListWithStudentGroup> getClassTeachersListWithStandardAndSection(
			String instId, String branchid, String academicYear)
			throws NoDataFoundException;

	List<ClassTeacherListWithStudentGroup> getStandardSectionListForClassTeacherAllotment(
			String academicYear, String instId, String branchId)
			throws NoDataFoundException;

}
