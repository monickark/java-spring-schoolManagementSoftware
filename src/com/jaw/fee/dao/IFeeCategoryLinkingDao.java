package com.jaw.fee.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IFeeCategoryLinkingDao {

	void insertFeeCategoryLinking(FeeCategoryLinking feeCategoryLinking) throws DuplicateEntryException;

	void updateFeeCategoryLinking(FeeCategoryLinking feeCategoryLinking,
			FeeCategoryLinkingKey feeCategoryLinkingKey)
			throws UpdateFailedException;

	FeeCategoryLinking selectFeeCategoryLinking(
			FeeCategoryLinkingKey feeCategoryLinkingKey) throws NoDataFoundException;

	void deleteFeeCategoryLinking(FeeCategoryLinkingKey feeCategoryLinkingKey)
			throws DeleteFailedException;

}
