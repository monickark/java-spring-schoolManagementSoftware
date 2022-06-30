package com.jaw.fee.controller;

import java.util.ArrayList;
import java.util.List;

import com.jaw.fee.dao.FeeDueList;
import com.jaw.fee.dao.FeePaidList;

public class FeeDetailsMasterVO {

	private FeeDetailsSearchVO feeDetailsSearchVO = new FeeDetailsSearchVO();
	private List<FeeDueListVO> feeDueList = new ArrayList<FeeDueListVO>();
	private List<FeePaidListVO> feePaidList = new ArrayList<FeePaidListVO>();
	private FeeDueDetailVO feeDueDetailVO = new FeeDueDetailVO();
	private FeePaidListVO feePaidListVO = new FeePaidListVO();
	private FeeReceiptVO feeReceiptVO = new FeeReceiptVO();
	private String pageSize = "30";

	public FeePaidListVO getFeePaidListVO() {
		return feePaidListVO;
	}

	public void setFeePaidListVO(FeePaidListVO feePaidListVO) {
		this.feePaidListVO = feePaidListVO;
	}

	public FeeReceiptVO getFeeReceiptVO() {
		return feeReceiptVO;
	}

	public void setFeeReceiptVO(FeeReceiptVO feeReceiptVO) {
		this.feeReceiptVO = feeReceiptVO;
	}

	public FeeDetailsSearchVO getFeeDetailsSearchVO() {
		return feeDetailsSearchVO;
	}

	public void setFeeDetailsSearchVO(FeeDetailsSearchVO feeDetailsSearchVO) {
		this.feeDetailsSearchVO = feeDetailsSearchVO;
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

	public List<FeeDueListVO> getFeeDueList() {
		return feeDueList;
	}

	public void setFeeDueList(List<FeeDueListVO> feeDueList) {
		this.feeDueList = feeDueList;
	}

	public List<FeePaidListVO> getFeePaidList() {
		return feePaidList;
	}

	public void setFeePaidList(List<FeePaidListVO> feePaidList) {
		this.feePaidList = feePaidList;
	}

}
