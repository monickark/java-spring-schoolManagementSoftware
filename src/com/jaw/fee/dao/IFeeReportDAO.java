package com.jaw.fee.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IFeeReportDAO {

	List<FeeReportList> selectFeeReportListDetails(FeeReportKey FeeReportListKey)
			throws NoDataFoundException;

	List<FeeReportList> selectFeePaidStudent(FeeReportKey FeeReportListKey)
			throws NoDataFoundException;

	List<FeeReportList> selectFeeDueStudent(FeeReportKey FeeReportListKey)
			throws NoDataFoundException;

}
