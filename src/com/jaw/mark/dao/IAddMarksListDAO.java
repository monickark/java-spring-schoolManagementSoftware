package com.jaw.mark.dao;

import java.util.List;

import com.jaw.attendance.dao.StudentAttendance;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.StudentNotFoundForMarkException;

public interface IAddMarksListDAO {
	public List<MarkSubjectLinkListForAddMarks> getMarkSubjectLinkListForAddMarks(
			final MarkSubjectLinkKey markSubjectLinkKey,String staffId)
			throws NoDataFoundException ;
	public List<StudentListForAddMarks> getStudentListForAddMarks(
			final AddMarksListKey addMarksListKey,String add)
			throws NoDataFoundException, StudentNotFoundForMarkException ;
	public void insertMarkListRecs(
			final List<StudentMarks> studentMarks)
			throws  DuplicateEntryException;

}
