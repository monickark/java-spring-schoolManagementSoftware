package com.jaw.prodAdm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InsertFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.prodAdm.controller.CommonCodeListVO;
import com.jaw.prodAdm.controller.CommonCodeVO;
import com.jaw.prodAdm.dao.CommonCodeKey;
import com.jaw.prodAdm.dao.ICommonCodeDao;
import com.jaw.prodAdm.dao.ICommonCodeListDao;

/**
 * CoomonCode Service class for doing crud operation in commoncode table and
 * also used for selecting all commoncode
 */

@Service
public class CommonCodeService implements ICommonCodeService {
	@Autowired
	ICommonCodeDao cocdDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	ICommonCodeListDao cocdList;

	// insertCoCd() is for insert data in cocd table

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertCocd(CommonCodeVO commonCode,
			UserSessionDetails UserSessionDetails)
			throws DuplicateEntryException, InsertFailedException {

		CommonCode code = new CommonCode();

		// get branch id and institutional id from the session cache user object

		code.setBranchId(UserSessionDetails.getBranchId());
		code.setInstId(UserSessionDetails.getInstId());
		code.setDbTs(1);
		code.setDelFlag("N");

		// Mapping UI object(UserSessionDetails) to Table Object (CommonCode)

		code.setCommonCode(commonCode.getCommonCode());
		code.setCodeType(commonCode.getCodeType());
		code.setCodeDescription(commonCode.getCodeDescription());

		// get rcreid and rmodid from the session cache user object

		code.setrCreId(UserSessionDetails.getUserId());
		code.setrModId(UserSessionDetails.getUserId());

		// Call Insert method from service method to insert the new commoncode
		// data to the database

		try {
			cocdDao.insertCocdRecord(code);
		} catch (DuplicateEntryException e) {

			CommonCodeKey codeKey = new CommonCodeKey();
			commonBusiness.changeObject(codeKey, code);
			try {

				// dao select method caling
				codeKey.setDelFlg("Y");
				code = cocdDao.selectCocdRecord(codeKey);
				code.setDelFlag("N");
				code.setrModId(UserSessionDetails.getUserId());
				cocdDao.updateCocdRecord(code, codeKey);

			} catch (NoDataFoundException e1) {

				throw new DuplicateEntryException();
			} catch (UpdateFailedException e1) {
				throw new InsertFailedException();
			}
		}

		// after data saved in dabase reset the values

		code.setCommonCode("");
		code.setCodeDescription("");
	}

	// updateCocd() is for update data in cocd table

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateCocdRecord(CommonCodeListVO commonCodeVO,
			UserSessionDetails UserSessionDetails) throws NoDataFoundException,
			UpdateFailedException {

		CommonCodeKey cocdkey = new CommonCodeKey();

		// Mapping UI object(UserSessionDetails) to Table Object (CommonCodeKey)

		commonBusiness.changeObject(cocdkey, commonCodeVO);

		// get branch id and institutional id from the session cache user object

		cocdkey.setBranchId(UserSessionDetails.getBranchId());
		cocdkey.setInstId(UserSessionDetails.getInstId());

		// Select that particular record from the database and assign that to
		// the global variable 'cocdGen'

		// selectCocdSingleRecord( commonCodeVO,UserSessionDetails);

		/*
		 * Get cocdGen and set code description and rmodid codedescription got
		 * from the UI object rmodid is set by getting userid from the session
		 * cache user object
		 */
		CommonCode cocdGen = new CommonCode();
		cocdkey.setDelFlg("N");
		cocdGen = cocdDao.selectCocdRecord(cocdkey);
		cocdGen.setCodeDescription(commonCodeVO.getCodeDescription());
		cocdGen.setrModId(UserSessionDetails.getUserId());

		/*
		 * calling update method from the dao and update method contains common
		 * code obect and commoncode key
		 */
		cocdDao.updateCocdRecord(cocdGen, cocdkey);
	}

	// deleteCocd() is for delete data in cocd table

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteCocdRecord(CommonCodeListVO commonCodeVO,
			UserSessionDetails UserSessionDetails) throws NoDataFoundException,
			DeleteFailedException {

		CommonCodeKey cocdkey = new CommonCodeKey();

		// Mapping UI object(UserSessionDetails) to Table Object (CommonCodeKey)

		commonBusiness.changeObject(cocdkey, commonCodeVO);

		// get branch id and institutional id from the session cache user object

		cocdkey.setBranchId(UserSessionDetails.getBranchId());
		cocdkey.setInstId(UserSessionDetails.getInstId());

		// calling select method from the database having return type commoncode
		// and assign that to a new object

		CommonCode commonCode = cocdDao.selectCocdRecord(cocdkey);

		/*
		 * Delete is only changing delflag from 'N' to 'Y' and change rmodid to
		 * userid which got from sessioncache user object assigning del flag and
		 * rmodid
		 */

		commonCode.setrModId(UserSessionDetails.getUserId());
		commonCode.setDelFlag("Y");

		// Calling update method from the dao and update the changes made before

		cocdDao.deleteCocdRecord(commonCode, cocdkey);

	}

	// ListMethods
	// selectCocd() is for select data from cocd table
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<CommonCodeListVO> selectCocdList(CommonCodeVO commonCode,
			UserSessionDetails UserSessionDetails) throws NoDataFoundException {

		CommonCodeKey code = new CommonCodeKey();

		// calling select method from the database having return type commoncode
		// and assign that to a new object

		code.setBranchId(UserSessionDetails.getBranchId());
		code.setInstId(UserSessionDetails.getInstId());

		// Select list is only based on the code type getting that code type
		// from UI object

		code.setCodeType(commonCode.getCodeType());

		/*
		 * Calling getCocdListByCode method from the DB that returning list of
		 * Commoncodelist Object Commoncode list object is a Table object meant
		 * only for handling list
		 */

		List<CommonCode> cocds = cocdList.getCocdListByType(code);

		/*
		 * Converting TableListObject(CommonCodeList) to UI list
		 * Object(CommonCodeListVO) CommonCodeListVO list object is a Table
		 * object meant only for handling list
		 */

		// craeting a new arraylist fro UI Object

		List<CommonCodeListVO> cocdList = new ArrayList<CommonCodeListVO>();

		// Iterating all values getting from database base on code type

		for (int i = 0; i < cocds.size(); i++) {

			// Create a new Commoncodelistvo UI object

			CommonCodeListVO commonCodelist = new CommonCodeListVO();

			// Mapping table object to ui object

			commonBusiness.changeObject(commonCodelist, cocds.get(i));

			// Add the UI obeject into the UIList
			commonCodelist.setRowId(i);
			cocdList.add(commonCodelist);
		}

		// set UIList(CommonCodeListVO) Object to UI form object CommonCodeVO
		// object

		return cocdList;

	}

	@Override
	public String[] retriveType() throws NoDataFoundException {
		List<CommonCode> cocds = cocdList.retriveType();

		String[] cocdList = new String[cocds.size()];

		// Iterating all values getting from database base on code type

		for (int i = 0; i < cocds.size(); i++) {

			cocdList[i] = cocds.get(i).getCodeType();
		}

		// set UIList(CommonCodeListVO) Object to UI form object CommonCodeVO
		// object
		return cocdList;

	}

}
