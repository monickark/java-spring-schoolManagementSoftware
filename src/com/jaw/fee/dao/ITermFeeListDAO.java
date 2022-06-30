package com.jaw.fee.dao;

import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.core.dao.HolidayMaintenance;

public interface ITermFeeListDAO {
public List<FeeTerms> selectFeeTermList(FeeTermsKey feeTermsKey) throws NoDataFoundException;
public List<FeeTerms> selectFeePaymentTermList(FeeTermsKey feeTermsKey) throws NoDataFoundException;
public void insertFeeTermListRecs(
		final List<FeeTerms> feeTermList)
		throws DuplicateEntryException;
public List<FeeTerms> selectTermFeesListRecs(FeeTermsKey feeTermsKey) throws NoDataFoundException;
public void updateTermFeesRecDelFlgYesToNo(final List<FeeTerms> feeTerms,final List<FeeTermsKey> feeTermsKey)
		throws UpdateFailedException ;
}
