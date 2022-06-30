package com.jaw.fee.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IFeeDetailsListDAO {
	List<FeeDueList> selectFeeDueListDetails(FeeDueListKey feeDueListKey)
			throws NoDataFoundException;

	List<FeePaidList> selectFeePaidListDetails(FeePaidListKey feePaidListKey)
			throws NoDataFoundException;

	List<FeeDueDetailsList> selectFeeDueDetailsList(FeeDueList feeDueList)
			throws NoDataFoundException;
}
