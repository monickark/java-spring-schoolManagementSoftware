package com.jaw.fee.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jaw.student.admission.controller.StudentMasterVO;

//AcademicTermDetails Pojo class
public class FeePaymentMasterVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Logging
	Logger logger = Logger.getLogger(FeePaymentMasterVO.class);

	private FeePaymentVO feePaymentVO = new FeePaymentVO();
	private FeePaymentSearchVO feePaymentSearchVO = new FeePaymentSearchVO();
	private List<FeePaymentList> feePaymentLists = new ArrayList<FeePaymentList>();
	private FeeReceiptVO feeReceiptVO = new FeeReceiptVO();
	private StudentMasterVO studentMaster = new StudentMasterVO();
	
	public StudentMasterVO getStudentMaster() {
		return studentMaster;
	}

	public void setStudentMaster(StudentMasterVO studentMaster) {
		this.studentMaster = studentMaster;
	}

	public FeeReceiptVO getFeeReceiptVO() {
		return feeReceiptVO;
	}

	public void setFeeReceiptVO(FeeReceiptVO feeReceiptVO) {
		this.feeReceiptVO = feeReceiptVO;
	}

	public List<FeePaymentList> getFeePaymentLists() {
		return feePaymentLists;
	}

	public void setFeePaymentLists(List<FeePaymentList> feePaymentLists) {
		this.feePaymentLists = feePaymentLists;
	}

	public FeePaymentVO getFeePaymentVO() {
		return feePaymentVO;
	}

	public void setFeePaymentVO(FeePaymentVO feePaymentVO) {
		this.feePaymentVO = feePaymentVO;
	}

	public FeePaymentSearchVO getFeePaymentSearchVO() {
		return feePaymentSearchVO;
	}

	public void setFeePaymentSearchVO(FeePaymentSearchVO feePaymentSearchVO) {
		this.feePaymentSearchVO = feePaymentSearchVO;
	}

}
