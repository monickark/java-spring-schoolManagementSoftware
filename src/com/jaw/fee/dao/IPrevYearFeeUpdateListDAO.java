package com.jaw.fee.dao;

import java.util.List;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IPrevYearFeeUpdateListDAO {

	void updateStuList(List<PrevYearFeeUpdateList> PrevYearFeeUpdateLists)
			throws BatchUpdateFailedException;

	List<PrevYearFeeUpdateList> selectPrevYearFeeUpdate(
			PrevYearFeeUpdateKey PrevYearFeeUpdateKey)
			throws NoDataFoundException;

}
