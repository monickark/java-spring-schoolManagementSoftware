package com.jaw.fee.dao;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IStudentFeeDemandDAO {

	
	StudentFeeDemand selectStudentFeeDemandWithId(
			StudentFeeDemandKey StudentFeeDemand) throws NoDataFoundException;

	StudentFeeDemand selectStudentFeeDemand(StudentFeeDemandKey StudentFeeDemand)
			throws NoDataFoundException;
	void updateStudentFeeDemandKey(StudentFeeDemand StudentFeeDemand,
			StudentFeeDemandKey StudentFeeDemandKey)
			throws UpdateFailedException;


}
