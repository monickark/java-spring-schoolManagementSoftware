package com.jaw.prodAdm.service;

import java.util.List;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InsertFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.prodAdm.controller.CommonCodeListVO;
import com.jaw.prodAdm.controller.CommonCodeVO;

public interface ICommonCodeService {

	public void insertCocd(CommonCodeVO admin,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, InsertFailedException;

	List<CommonCodeListVO> selectCocdList(CommonCodeVO admin,
			UserSessionDetails userVO) throws NoDataFoundException;

	void updateCocdRecord(CommonCodeListVO cocdlist, UserSessionDetails userVO)
			throws NoDataFoundException, UpdateFailedException;

	void deleteCocdRecord(CommonCodeListVO cocdlist, UserSessionDetails userVO)
			throws NoDataFoundException, DeleteFailedException;

	public String[] retriveType() throws NoDataFoundException;

}
