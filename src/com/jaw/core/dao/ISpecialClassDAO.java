package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ISpecialClassDAO {
	void insertSpecialClassRec(SpecialClass specialClass)
			throws DuplicateEntryException;

	void updateSpecialClassRec(SpecialClass specialClass,final SpecialClassKey specialClassKey)
			throws UpdateFailedException;

	void deleteSpecialClassRec(SpecialClass specialClass,final SpecialClassKey specialClassKey) throws DeleteFailedException;

	SpecialClass selectSpecialClassRec(
			final SpecialClassKey specialClassKey)
			throws NoDataFoundException;
	public int checkDateHasSpecialClass(SpecialClass specialClass,UserSessionDetails userSessionDetails) ;
	public int checkSpecialClassExist(SpecialClass specialClass,UserSessionDetails userSessionDetails) ;
}
