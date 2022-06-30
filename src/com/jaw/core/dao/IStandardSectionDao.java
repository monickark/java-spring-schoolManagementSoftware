package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;

public interface IStandardSectionDao {
	public void insertStandard(final List<StandardSection> standard)
			throws DuplicateEntryException;

}
