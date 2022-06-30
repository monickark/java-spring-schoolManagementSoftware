package com.jaw.fee.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IFeeMasterStatusListDAO {
	public List<FeeMasterStatus> checkFeeStatus(FeeMasterStatusKey feeStatusKey)
			throws NoDataFoundException;
}
