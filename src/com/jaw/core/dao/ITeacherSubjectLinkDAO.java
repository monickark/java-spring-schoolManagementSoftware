package com.jaw.core.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ITeacherSubjectLinkDAO {
	void insertTeacherSubjectLinkRec(TeacherSubjectLink teacherSubjectLink)
			throws DuplicateEntryException;

	void updateTeacherSubjectLinkRec(TeacherSubjectLink teacherSubjectLink,final TeacherSubjectLinkKey teacherSubjectLinkKey)
			throws UpdateFailedException;

	void deleteTeacherSubjectLinkRec(TeacherSubjectLink teacherSubjectLink,final TeacherSubjectLinkKey teacherSubjectLinkKey) throws DeleteFailedException;

	TeacherSubjectLink selectTeacherSubjectLinkRec(
			final TeacherSubjectLinkKey teacherSubjectLinkKey)
			throws NoDataFoundException;

	TeacherSubjectLink checkTeacherSubjectLink(final TeacherSubjectLinkListKey teacherSubjectLinkListKey) ;
}
