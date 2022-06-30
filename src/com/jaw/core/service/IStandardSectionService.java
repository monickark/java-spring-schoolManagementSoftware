package com.jaw.core.service;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.core.controller.StandardCombinationVO;
import com.jaw.core.controller.StandardSectionVO;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IStandardSectionService {

	void insertStandard(StandardSectionVO standard,
			UserSessionDetails userSessionDetails) throws UpdateFailedException;

	List<StandardSectionVO> selectStandard(StandardSectionVO standard,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	void selectAllSection(StandardSectionVO standardSectionVO,
			UserSessionDetails userSessionDetails);

	public void getStandardSectionAllotmentList(
			StandardCombinationVO standardCombinationVO,
			StandardSectionVO standardSectionVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;
}
