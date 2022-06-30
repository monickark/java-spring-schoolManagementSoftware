package com.jaw.admin.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IBranchMasterListDAO {

	List<BranchMasterList> getBranchMasterList(final String instId)
			throws NoDataFoundException;

}
