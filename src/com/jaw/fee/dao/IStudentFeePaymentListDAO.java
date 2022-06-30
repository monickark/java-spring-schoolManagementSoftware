package com.jaw.fee.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IStudentFeePaymentListDAO {
	List<StudentFeePayment> selectStudentFeePaymentListForFeePaid(
			StudentFeePaymentKey StudentFeePaymentKey)
			throws NoDataFoundException;
}
