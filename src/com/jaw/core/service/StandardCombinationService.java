package com.jaw.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InsertFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.controller.StandardCombinationListVO;
import com.jaw.core.controller.StandardCombinationVO;
import com.jaw.core.dao.IStandardCombinationCrudDao;
import com.jaw.core.dao.IStandardCombinationListDao;
import com.jaw.core.dao.StandardCombination;
import com.jaw.core.dao.StandardCombinationKey;
import com.jaw.core.dao.StandardCombinationList;
import com.jaw.framework.sessCache.UserSessionDetails;

/**
 * Standard combination is used for standard and combination management like
 * adding deleting selecting standards and their respective combination
 */

@Service
public class StandardCombinationService implements IStandardCombinationService {
	Logger logger = Logger.getLogger(StandardCombinationService.class);
	@Autowired
	IStandardCombinationCrudDao standardDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IStandardCombinationListDao standardListDao;

	// insertStandard is used to insert the standard and combination in to the
	// database

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertStandard(StandardCombinationVO standard,
			UserSessionDetails userVO) throws InsertFailedException,
			DuplicateEntryException {

		logger.debug("inside insert method in StandardCombinationService");

		/*
		 * change UI object to table object / getting branch id & inst id from
		 * session cache user object rcreid and rmodid from the session cache
		 * user's username
		 */

		StandardCombination std = new StandardCombination();

		std.setBranchId(userVO.getBranchId());
		std.setInstId(userVO.getInstId());
		std.setDbTs(1);
		std.setDelFlag("N");
		std.setStandardId(standard.getStandardId());
		std.setCombinationId(standard.getCombinationId());
		std.setrCreId(userVO.getUserId());
		std.setrModId(userVO.getUserId());

		// call StandardCombinationCrudDao insert method

		try {
			standardDao.insertStandard(std);
		} catch (DuplicateEntryException e) {
			StandardCombinationKey stdKey = new StandardCombinationKey();
			commonBusiness.changeObject(stdKey, standard);
			stdKey.setBranchId(userVO.getBranchId());
			stdKey.setInstId(userVO.getInstId());
			stdKey.setDelFlg("Y");
			StandardCombination standard1 = new StandardCombination();
			try {
				standard1 = standardDao.selectStandardCombination(stdKey);
			} catch (NoDataFoundException e1) {
				throw new DuplicateEntryException();
			}

			standard1.setDelFlag("N");
			standard1.setrModId(userVO.getUserId());
			try {
				standardDao.deleteStandardCombination(standard1, stdKey);
			} catch (DeleteFailedException e1) {
				throw new InsertFailedException();
			}
		}

	}

	// this method is used to select all standard and combination

	@Override
	public void selectAllStandard(StandardCombinationVO standardCombinationVO,
			String branchId, String instId) {

		logger.debug("inside select all standard method in StandardCombinationService");

		// pass branch id and inst id to get all standard from the list

		List<StandardCombinationList> cocds = null;
		try {
			cocds = standardListDao.getAllStandardList(branchId, instId);
		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// copy table pojo object to UI list object

		ArrayList<StandardCombinationListVO> standardCombinationList = new ArrayList<StandardCombinationListVO>();

		for (int i = 0; i < cocds.size(); i++) {

			StandardCombinationListVO std = new StandardCombinationListVO();
			commonBusiness.changeObject(std, cocds.get(i));
			std.setRowId(i);
			standardCombinationList.add(std);

		}

		// assign that ui object list object to ui mapped bean

		standardCombinationVO
				.setStandardCombinationList(standardCombinationList);

	}

	// This method is used to delete a particular record from the standard
	// combination table

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteStandard(StandardCombinationListVO standardVo,
			UserSessionDetails userVO) throws NoDataFoundException,
			DeleteFailedException {

		// Mapping UI object to table object / getting branch id & inst id from
		// session cache user object

		StandardCombinationKey stdKey = new StandardCombinationKey();

		commonBusiness.changeObject(stdKey, standardVo);
		stdKey.setBranchId(userVO.getBranchId());
		stdKey.setInstId(userVO.getInstId());
		stdKey.setDelFlg("N");
		StandardCombination standard = new StandardCombination();

		// call the StandardCombinationCrudDao

		standard = standardDao.selectStandardCombination(stdKey);

		// update del flag and rmodid in the selected standard combination
		// object

		standard.setDelFlag("Y");
		standard.setrModId(userVO.getUserId());

		// update the value of the selected object to database

		standardDao.deleteStandardCombination(standard, stdKey);
	}

}
