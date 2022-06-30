package com.jaw.fee.service;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.fee.controller.FeeReportMasterVO;
import com.jaw.framework.sessCache.SessionCache;

public interface IFeeReportService {

	
	void selectFeePaymentListReport(SessionCache sessionCache,
			FeeReportMasterVO feeReportMasterVO) throws NoDataFoundException;

	void selectFeeStatusReport(SessionCache sessionCache,
			FeeReportMasterVO feeReportMasterVO) throws NoDataFoundException;

}
