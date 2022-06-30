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
import com.jaw.fee.controller.StudentFeeDiscountListVO;
import com.jaw.fee.controller.StudentFeeDiscountMasterVO;
import com.jaw.fee.dao.IStudentFeeDiscountListDAO;
import com.jaw.fee.dao.StudentFeeDiscountKey;
import com.jaw.fee.dao.StudentFeeDiscountList;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;

@Service
public class StudentFeeDiscountService implements IStudentFeeDiscountService {
	// Logging
	Logger logger = Logger.getLogger(StudentFeeDiscountService.class);
	@Autowired
	IStudentFeeDiscountListDAO studentFeeDiscountListDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DoAudit doAudit;
	@Autowired
	ICourseMasterDAO courseMasterDAO;

	// Method to get the List for FeeDiscount
	@Override
	public void getStuListForFeeDiscount(UserSessionDetails sessionDetails,
			StudentFeeDiscountMasterVO StudentFeeDiscountMasterVO)
			throws NoDataFoundException, InvalidCategoryForUpdateException {
		StudentFeeDiscountKey studentFeeDiscountKey = new StudentFeeDiscountKey();
		List<StudentFeeDiscountListVO> listOfMasterVOs = new ArrayList<StudentFeeDiscountListVO>();
		commonBusiness.changeObject(studentFeeDiscountKey, StudentFeeDiscountMasterVO.getStudentFeeDiscountSearch());
		studentFeeDiscountKey.setInstId(sessionDetails.getInstId());
		studentFeeDiscountKey.setBranchId(sessionDetails.getBranchId());
		List<StudentFeeDiscountList> stuMasList = studentFeeDiscountListDAO
				.selectStudentFeeDiscount(studentFeeDiscountKey);
		int rowId = 0;
		for (StudentFeeDiscountList studentFeeDiscountList : stuMasList) {
			StudentFeeDiscountListVO studentFeeDiscountListVO =new StudentFeeDiscountListVO();
			commonBusiness.changeObject(studentFeeDiscountListVO, studentFeeDiscountList);
			
			studentFeeDiscountListVO.setRowid(rowId);
			listOfMasterVOs.add(studentFeeDiscountListVO);
			rowId++;
			System.out.println("studentFeeDiscountListVO obj:"+studentFeeDiscountListVO.toString());
		}
		StudentFeeDiscountMasterVO.setStuList(listOfMasterVOs);
	}

	// Method to FeeDiscount the Student List
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertStuList(UserSessionDetails sessionDetails,
			StudentFeeDiscountMasterVO StudentFeeDiscountMasterVO)
			throws BatchUpdateFailedException, DuplicateEntryException,
			DatabaseException, DataIntegrityException, RuntimeExceptionForBatch {

		List<StudentFeeDiscountListVO> listOfVOs = StudentFeeDiscountMasterVO
				.getStuList();
		List<StudentFeeDiscountList> stuFeeDiscountList = new ArrayList<StudentFeeDiscountList>();
	
		// Converting UI List into DB list
		for (StudentFeeDiscountListVO listVO : listOfVOs) {
			StudentFeeDiscountList stuList = new StudentFeeDiscountList();
			commonBusiness.changeObject(stuList, listVO);
			stuList.setInstId(sessionDetails.getInstId());
			stuList.setBranchId(sessionDetails.getBranchId());
			stuList.setrModId(sessionDetails.getUserId());
			stuList.setAcTerm(sessionDetails.getCurrAcTerm());
			Double dueAmount=Double.parseDouble(stuList.getFeeDueAmt());
			Double conAmt=Double.parseDouble(stuList.getConcessionAmt());
			Double finalDueAmt=dueAmount-conAmt;
			stuList.setFeeDueAmt(finalDueAmt.toString());
			System.out.println("Student Object b4:"+stuList.toString());
			stuFeeDiscountList.add(stuList);
		}
		System.out.println("before insert FeeDiscount");
		studentFeeDiscountListDAO.updateStuList(stuFeeDiscountList);


		logger.debug("Function Audit for Student FeeDiscount has been done.");

	}

}
