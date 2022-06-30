package com.jaw.fee.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jaw.core.controller.CourseTermLinkingMasterVO;

public class FeePaymentDetailMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(FeePaymentDetailMasterVO.class);
	
	private List<FeePaymentDetailVO> feePaymentDetailsVOs = new ArrayList<FeePaymentDetailVO>();
	private FeePaymentDetailVO feePaymentDetailVO = new FeePaymentDetailVO();
	private FeePaymentDetailSearchVO feePaymentDetailSearchVO = new FeePaymentDetailSearchVO();
	private String pageSize = "10";
	public List<FeePaymentDetailVO> getFeePaymentDetailsVOs() {
		return feePaymentDetailsVOs;
	}
	public void setFeePaymentDetailsVOs(
			List<FeePaymentDetailVO> feePaymentDetailsVOs) {
		this.feePaymentDetailsVOs = feePaymentDetailsVOs;
	}
	public FeePaymentDetailVO getFeePaymentDetailVO() {
		return feePaymentDetailVO;
	}
	public void setFeePaymentDetailVO(FeePaymentDetailVO feePaymentDetailVO) {
		this.feePaymentDetailVO = feePaymentDetailVO;
	}
	public FeePaymentDetailSearchVO getFeePaymentDetailSearchVO() {
		return feePaymentDetailSearchVO;
	}
	public void setFeePaymentDetailSearchVO(
			FeePaymentDetailSearchVO feePaymentDetailSearchVO) {
		this.feePaymentDetailSearchVO = feePaymentDetailSearchVO;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
}
