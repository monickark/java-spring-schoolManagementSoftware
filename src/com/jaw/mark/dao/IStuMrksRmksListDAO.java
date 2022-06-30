package com.jaw.mark.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IStuMrksRmksListDAO {
	
	List<StuMrksRmksList> getStuMarksRmksList(StuMrksRmksListKey stuMrksRmksListKey)
			throws NoDataFoundException;
	
	List<StuDetailsListForRemarks> getStuAdmisNoList(StuMrksRmksListKey stuMrksRmksListKey, String flow)
			throws NoDataFoundException;

	List<StuDetailsListForRemarks> getStuAdmisNoListForRemarksNotAdded(
			StuMrksRmksListKey stuMrksRmksListKey) throws NoDataFoundException;

}
