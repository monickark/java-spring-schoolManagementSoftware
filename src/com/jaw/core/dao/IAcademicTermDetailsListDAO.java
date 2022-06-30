package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IAcademicTermDetailsListDAO {
	
	public List<AcademicTermDetails> getAcaTrmBasedBranchForHlyGen(
			final AcademicTermDetailsKey academicTermDetailsKey) throws NoDataFoundException;
	
	List<AcademicTermList> getAcademicTermList(String instId, String branchId)
			throws NoDataFoundException;
	
}
