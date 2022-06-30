package com.jaw.mark.dao;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IStudentMarkDAO {
	public void updateStudentMarksRec(final StudentMarks studentMarks,
			final StudentMarksKey studentMarksKey) throws UpdateFailedException;
	public StudentMarks selectStudentMarksRec(final StudentMarksKey studentMarksKey)
			throws NoDataFoundException ;
}
