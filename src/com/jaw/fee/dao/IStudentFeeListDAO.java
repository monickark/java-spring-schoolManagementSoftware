package com.jaw.fee.dao;

import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IStudentFeeListDAO {
	public void insertStudentFeeListRecs(final List<StudentFee> studentFeeList)
			throws DuplicateEntryException;
	public List<StudentFee> selectStudentFeeListRecsForStuDmd(final StudentFeeKey studentFeeKey,String course,String term)
			throws NoDataFoundException;
	public List<StudentFee> selectStudentFeeListForFeePaidList(final StudentFee studentFee)
			throws NoDataFoundException;
}
