package com.jaw.fee.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ITermFeeDAO {
	void deleteTermFeesRec(FeeTerms feeTerms,final FeeTermsKey feeTermsKey) throws DeleteFailedException;
	public FeeTerms selectFeesTermRec(final FeeTermsKey feeTermsKey)throws NoDataFoundException;
	public FeeTerms selectFeesTermRecDelFlgY(final FeeTermsKey feeTermsKey);
}
