package com.jaw.common.dropdown.dao;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.CourseMaster;

public interface IAcademicTermListDAO {
	List<AcademicTermDetails> selectAcademicTermList(AcademicTermDetailsKey academicTermDetailsKey) throws NoDataFoundException;
	List<AcademicTermDetails> selectPresentAndFutureAcademicTerm(AcademicTermDetailsKey academicTermDetailsKey) throws NoDataFoundException;
	public Map<String, String> selectTermForCurAcademicYear(final String instId,final String branchId)	throws NoDataFoundException ;
	List<AcademicTermDetails> selectPresentAcademicTerm(AcademicTermDetailsKey academicTermDetailsKey) throws NoDataFoundException;
}
