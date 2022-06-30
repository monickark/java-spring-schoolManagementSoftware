package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IStandardSectionListDao {

	List<StandardSection> getSection(StandardSection standard)
			throws NoDataFoundException;

	List<StandardSection> getStandardSectionList(String instId, String branchId)
			throws NoDataFoundException;

	void deleteStandardSection(String instId, String branchId)
			throws UpdateFailedException;

	void deleteStandardSectionBasedOnMedium(String instId, String branchId,
			String medium) throws UpdateFailedException;

}
