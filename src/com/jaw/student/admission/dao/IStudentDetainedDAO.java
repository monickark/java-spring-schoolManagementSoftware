package com.jaw.student.admission.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IStudentDetainedDAO {
	public StudentDetain getStuDetainRec(StudentDetainKey studentDetainKey) throws NoDataFoundException;
public void updateStudentDetainedRec(StudentDetain studentDetain,StudentDetainKey stuDetainKey ) throws UpdateFailedException;
public void deleteStudentDetainedRec(final StudentDetainKey stuDetainKey,final StudentDetain studentDetain) throws DeleteFailedException;
	
	
}
