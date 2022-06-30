package com.jaw.fee.service;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InvalidCategoryForUpdateException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.fee.controller.StudentFeeDiscountMasterVO;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IStudentFeeDiscountService {

	void getStuListForFeeDiscount(UserSessionDetails sessionDetails,
			StudentFeeDiscountMasterVO StudentFeeDiscountMasterVO)
			throws NoDataFoundException, InvalidCategoryForUpdateException;

	void insertStuList(UserSessionDetails sessionDetails,
			StudentFeeDiscountMasterVO StudentFeeDiscountMasterVO)
			throws BatchUpdateFailedException, DuplicateEntryException,
			DatabaseException, DataIntegrityException, RuntimeExceptionForBatch;

}
