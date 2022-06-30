package com.jaw.fee.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IStudentFeePaymentDao {

	StudentFeePayment selectStudentFeePayment(
			StudentFeePaymentKey StudentFeePaymentKey)
			throws NoDataFoundException;

	void updateStudentFeePayment(StudentFeePayment StudentFeePayment,
			StudentFeePaymentKey StudentFeePaymentKey)
			throws UpdateFailedException;

	void insertStudentFeePayment(StudentFeePayment StudentFeePayment)
			throws DuplicateEntryException;

	StudentFeePayment selectStudentFeePaymentWithPk(
			StudentFeePaymentKey StudentFeePaymentKey)
			throws NoDataFoundException;

}
