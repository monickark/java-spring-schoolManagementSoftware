package com.jaw.fee.controller;

import java.util.ArrayList;
import java.util.List;

public class FeeReportMasterVO {

	private FeeReportSearchVO feeReportSearchVO = new FeeReportSearchVO();
	private List<FeeReportListVO> feeReportList = new ArrayList<FeeReportListVO>();
	private FeeDueDetailVO feeDueDetailVO = new FeeDueDetailVO();
	private String pageSize = "30";


	public FeeReportSearchVO getFeeReportSearchVO() {
		return feeReportSearchVO;
	}

	public void setFeeReportSearchVO(FeeReportSearchVO feeReportSearchVO) {
		this.feeReportSearchVO = feeReportSearchVO;
	}

	public FeeDueDetailVO getFeeDueDetailVO() {
		return feeDueDetailVO;
	}

	public void setFeeDueDetailVO(FeeDueDetailVO feeDueDetailVO) {
		this.feeDueDetailVO = feeDueDetailVO;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public List<FeeReportListVO> getFeeReportList() {
		return feeReportList;
	}

	public void setFeeReportList(List<FeeReportListVO> feeReportList) {
		this.feeReportList = feeReportList;
	}

	

}
