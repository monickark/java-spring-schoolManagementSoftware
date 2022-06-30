package com.jaw.mark.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IMarksValidationDAO {
	
	List<String> getStuMarksStatusList(MarkValidationKey markMasterKey) throws NoDataFoundException;
	
}
