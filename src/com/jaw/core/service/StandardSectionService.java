package com.jaw.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.core.controller.StandardCombinationVO;
import com.jaw.core.controller.StandardSectionVO;
import com.jaw.core.dao.IStandardSectionDao;
import com.jaw.core.dao.IStandardSectionListDao;
import com.jaw.core.dao.StandardSection;
import com.jaw.framework.sessCache.UserSessionDetails;

@Service
public class StandardSectionService implements IStandardSectionService {

	Logger logger = Logger.getLogger(StandardSectionService.class);

	@Autowired
	IStandardSectionDao standardDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IStandardSectionListDao standardSectionListDao;
	@Autowired
	IStandardCombinationService standardCombinationService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertStandard(StandardSectionVO standard,
			UserSessionDetails userSessionDetails) throws UpdateFailedException {

		logger.debug("inside insert method in StandardSectionService");
		if (standard.getMedium() == null) {
			standard.setMedium("N/A");
		}
		standardSectionListDao.deleteStandardSectionBasedOnMedium(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), standard.getMedium());

		List<StandardSection> standardSections = new ArrayList<StandardSection>();
		for (int i = 0; i < standard.getStdseclist().size(); i++) {
			for (int j = 0; j < standard.getStdseclist().get(i).size(); j++) {

				StandardSection std = new StandardSection();
				std.setDbTs(1);
				std.setDelFlag("N");
				std.setInstId(userSessionDetails.getInstId());
				std.setBranchId(userSessionDetails.getBranchId());
				std.setStandard(standard.getStdlist().get(i));
				std.setCombination(standard.getComblist().get(i));
				std.setSection(standard.getStdseclist().get(i).get(j));
				if (standard.getMedium() == null) {
					standard.setMedium("N/A");
				}
				std.setMedium(standard.getMedium());
				std.setrCreId(userSessionDetails.getUserId());
				std.setrModId(userSessionDetails.getUserId());

				standardSections.add(std);
			}
		}
		try {
			standardDao.insertStandard(standardSections);
		} catch (DuplicateEntryException e) {
		}

	}

	@Override
	public List<StandardSectionVO> selectStandard(StandardSectionVO standard,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {

		logger.debug("inside select method in StandardSectionService");

		StandardSection list = new StandardSection();
		list.setStandard(standard.getStandard());

		List<StandardSection> cocds = standardSectionListDao
				.getStandardSectionList(userSessionDetails.getInstId(),
						userSessionDetails.getBranchId());
		List<StandardSectionVO> adminVOs = new ArrayList<StandardSectionVO>();

		for (StandardSection cocd : cocds) {
			StandardSectionVO std = new StandardSectionVO();
			commonBusiness.changeObject(std, cocd);
			adminVOs.add(std);
		}

		return adminVOs;
	}

	@Override
	public void selectAllSection(StandardSectionVO standardSectionVO,
			UserSessionDetails userSessionDetails) {

		List<StandardSection> cocds = null;
		try {
			cocds = standardSectionListDao.getStandardSectionList(
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId());
		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<StandardSectionVO> adminVOs = new ArrayList<StandardSectionVO>();

		for (StandardSection cocd : cocds) {
			StandardSectionVO std = new StandardSectionVO();
			commonBusiness.changeObject(std, cocd);
			adminVOs.add(std);
		}

		standardSectionVO.setStandardSectionVOList(adminVOs);
	}

	@Override
	public void getStandardSectionAllotmentList(
			StandardCombinationVO standardCombinationVO,
			StandardSectionVO standardSectionVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {

		selectAllSection(standardSectionVO, userSessionDetails);
		standardCombinationService.selectAllStandard(standardCombinationVO,
				userSessionDetails.getBranchId(),
				userSessionDetails.getInstId());
	}

}
