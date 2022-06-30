package com.jaw.prodAdm.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.appCache.dao.CommonCode;

public interface ICommonCodeDao {

	void insertCocdRecord(CommonCode code) throws DuplicateEntryException;

	void updateCocdRecord(CommonCode code, CommonCodeKey codeKey)
			throws UpdateFailedException;

	CommonCode selectCocdRecord(CommonCodeKey code) throws NoDataFoundException;

	void deleteCocdRecord(CommonCode code, CommonCodeKey codeKey)
			throws DeleteFailedException;

}
