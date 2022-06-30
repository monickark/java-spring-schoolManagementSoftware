package com.jaw.fee.dao;

import java.util.List;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;

public interface IStudentFeeDiscountListDAO {

	List<StudentFeeDiscountList> selectStudentFeeDiscount(
			StudentFeeDiscountKey studentFeeDiscountKey)
			throws NoDataFoundException;


	void updateStuList(List<StudentFeeDiscountList> studentFeeDiscountLists)
			throws BatchUpdateFailedException;

}
