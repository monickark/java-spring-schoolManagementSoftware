package com.jaw.mark.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IMarkMasterListDAO {
	public List<MarkMaster> getMarkMasterList(MarkMasterListKey markMasterListKey)throws NoDataFoundException ;
}
