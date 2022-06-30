package com.jaw.prodAdm.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.appCache.dao.CommonCode;

public interface ICommonCodeListDao {

	List<CommonCode> getCocdListByType(CommonCodeKey codeKey)
			throws NoDataFoundException;

	public List<CommonCode> retriveType() throws NoDataFoundException;
}
