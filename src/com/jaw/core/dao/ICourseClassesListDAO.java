package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ICourseClassesListDAO {
	
	List<CourseClassesList> getCourseSubjectLinkList(CourseClassesList courseClassesList)
			throws NoDataFoundException;
	
	void insertCourseClassesList(List<CourseClasses> courseClassesList)
			throws DuplicateEntryException;


	void updateStaffDataOnTransfer(List<CourseClasses> courseClassesLists)
			throws UpdateFailedException;

	List<CourseClasses> getStaffListForTransferProcess(
			CourseClassesList courseClassesList) throws NoDataFoundException;
	
}
