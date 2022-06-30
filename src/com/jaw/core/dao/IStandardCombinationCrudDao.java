package com.jaw.core.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IStandardCombinationCrudDao {

	void insertStandard(StandardCombination standard)
			throws DuplicateEntryException;

	StandardCombination selectStandardCombination(
			StandardCombinationKey standardKey) throws NoDataFoundException;

	void deleteStandardCombination(StandardCombination standard,
			StandardCombinationKey stdKey) throws DeleteFailedException;

}
