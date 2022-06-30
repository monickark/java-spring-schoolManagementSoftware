package com.jaw.fee.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InvalidCategoryForUpdateException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.core.dao.ICourseMasterDAO;
import com.jaw.fee.controller.PrevYearFeeUpdateListVO;
import com.jaw.fee.controller.PrevYearFeeUpdateMasterVO;
import com.jaw.fee.dao.IPrevYearFeeUpdateListDAO;
import com.jaw.fee.dao.PrevYearFeeUpdateKey;
import com.jaw.fee.dao.PrevYearFeeUpdateList;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;

@Service
public class PrevYearFeeUpdateService implements IPrevYearFeeUpdateService {
	// Logging
	Logger logger = Logger.getLogger(PrevYearFeeUpdateService.class);
	@Autowired
	IPrevYearFeeUpdateListDAO PrevYearFeeUpdateListDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DoAudit doAudit;
	@Autowired
	ICourseMasterDAO courseMasterDAO;

	// Method to get the List for FeeUpdate
	@Override
	public void getStuListForFeeUpdate(UserSessionDetails sessionDetails,
			PrevYearFeeUpdateMasterVO PrevYearFeeUpdateMasterVO)
			throws NoDataFoundException, InvalidCategoryForUpdateException {
		PrevYearFeeUpdateKey PrevYearFeeUpdateKey = new PrevYearFeeUpdateKey();
		List<PrevYearFeeUpdateListVO> listOfMasterVOs = new ArrayList<PrevYearFeeUpdateListVO>();
		commonBusiness.changeObject(PrevYearFeeUpdateKey, PrevYearFeeUpdateMasterVO.getPrevYearFeeUpdateSearch());
		PrevYearFeeUpdateKey.setInstId(sessionDetails.getInstId());
		PrevYearFeeUpdateKey.setBranchId(sessionDetails.getBranchId());
		List<PrevYearFeeUpdateList> stuMasList = PrevYearFeeUpdateListDAO
				.selectPrevYearFeeUpdate(PrevYearFeeUpdateKey);
		int rowId = 0;
		for (PrevYearFeeUpdateList PrevYearFeeUpdateList : stuMasList) {
			PrevYearFeeUpdateListVO PrevYearFeeUpdateListVO =new PrevYearFeeUpdateListVO();
			commonBusiness.changeObject(PrevYearFeeUpdateListVO, PrevYearFeeUpdateList);
			
			PrevYearFeeUpdateListVO.setRowid(rowId);
			listOfMasterVOs.add(PrevYearFeeUpdateListVO);
			rowId++;
			System.out.println("PrevYearFeeUpdateListVO obj:"+PrevYearFeeUpdateListVO.toString());
		}
		PrevYearFeeUpdateMasterVO.setStuList(listOfMasterVOs);
	}

	// Method to FeeUpdate the Student List
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertStuList(UserSessionDetails sessionDetails,
			PrevYearFeeUpdateMasterVO PrevYearFeeUpdateMasterVO)
			throws BatchUpdateFailedException, DuplicateEntryException,
			DatabaseException, DataIntegrityException, RuntimeExceptionForBatch {

		List<PrevYearFeeUpdateListVO> listOfVOs = PrevYearFeeUpdateMasterVO
				.getStuList();
		List<PrevYearFeeUpdateList> stuFeeUpdateList = new ArrayList<PrevYearFeeUpdateList>();
	
		// Converting UI List into DB list
		for (PrevYearFeeUpdateListVO listVO : listOfVOs) {
			PrevYearFeeUpdateList stuList = new PrevYearFeeUpdateList();
			commonBusiness.changeObject(stuList, listVO);
			stuList.setInstId(sessionDetails.getInstId());
			stuList.setBranchId(sessionDetails.getBranchId());
			stuList.setrModId(sessionDetails.getUserId());
			stuList.setAcTerm(sessionDetails.getCurrAcTerm());
			Double dueAmount=Double.parseDouble(stuList.getFeeDueAmt());
			Double lastYearAmt=Double.parseDouble(stuList.getLastYearAmt());
			Double finalDueAmt=dueAmount+lastYearAmt;
			stuList.setFeeDueAmt(finalDueAmt.toString());
			System.out.println("Student Object b4:"+stuList.toString());
			stuFeeUpdateList.add(stuList);
		}
		System.out.println("before insert FeeUpdate");
		PrevYearFeeUpdateListDAO.updateStuList(stuFeeUpdateList);


		logger.debug("Function Audit for Student FeeUpdate has been done.");

	}

}
