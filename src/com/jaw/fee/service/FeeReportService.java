package com.jaw.fee.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.fee.controller.FeePaidListVO;
import com.jaw.fee.controller.FeeReportListVO;
import com.jaw.fee.controller.FeeReportMasterVO;
import com.jaw.fee.dao.FeePaidList;
import com.jaw.fee.dao.FeePaidListKey;
import com.jaw.fee.dao.FeeReportKey;
import com.jaw.fee.dao.FeeReportList;
import com.jaw.fee.dao.IFeePaymentListDao;
import com.jaw.fee.dao.IFeeReportDAO;
import com.jaw.fee.dao.IStudentFeeListDAO;
import com.jaw.fee.dao.IStudentFeePaymentDao;
import com.jaw.fee.dao.IStudentFeePaymentListDAO;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.ICustIdGeneratorService;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.mark.dao.IStudentReportCardDAO;

@Service
public class FeeReportService implements IFeeReportService {
	// Logging
	Logger logger = Logger.getLogger(FeeGenerationService.class);
	@Autowired
	DoAudit doAudit;

	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IFeeReportDAO feeReportDAO;
	@Autowired
	IStudentFeePaymentListDAO studentFeePaymentListDAO;
	@Autowired
	IStudentFeeListDAO studentFeeListDAO;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	IFeePaymentListDao feePaymentListDao;
	@Autowired
	IStudentFeePaymentDao studentFeePaymentDao;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	IStudentReportCardDAO studentReportCardDAO;
	@Qualifier(value = "simpleCustIdGenerator")
	@Autowired
	private ICustIdGeneratorService simpleCustIdGenerator;

	@Override
	public void selectFeePaymentListReport(
			SessionCache sessionCache, FeeReportMasterVO feeReportMasterVO)
			throws NoDataFoundException {
		FeeReportKey feeReportKey = new FeeReportKey();
		commonBusiness.changeObject(feeReportKey,
				feeReportMasterVO.getFeeReportSearchVO());
	
			feeReportKey.setAcademicTerm(sessionCache.getUserSessionDetails()
					.getCurrAcTerm());
		
		feeReportKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		feeReportKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		List<FeeReportList> feeDues = feeReportDAO
				.selectFeeReportListDetails(feeReportKey);
		List<FeeReportListVO> feeReportList = new ArrayList<FeeReportListVO>();
		if (feeDues != null) {
			for (int i = 0; i < feeDues.size(); i++) {
				FeeReportList feeReport = feeDues.get(i);
				FeeReportListVO feeReportVo = new FeeReportListVO();
				Double.parseDouble(feeReport.getFeePaidAmt());
				commonBusiness.changeObject(feeReportVo, feeReport);
				feeReportVo.setRowId(i);
				feeReportList.add(feeReportVo);
			}
			feeReportMasterVO.setFeeReportList(feeReportList);
		}
		
	}
	
	
	@Override
	public void selectFeeStatusReport(
			SessionCache sessionCache, FeeReportMasterVO feeReportMasterVO)
			throws NoDataFoundException {
		FeeReportKey feeReportKey = new FeeReportKey();
		commonBusiness.changeObject(feeReportKey,
				feeReportMasterVO.getFeeReportSearchVO());
	
			feeReportKey.setAcademicTerm(sessionCache.getUserSessionDetails()
					.getCurrAcTerm());
		
		feeReportKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		feeReportKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		List<FeeReportList> list = null;
		if(feeReportKey.getReportType().equals(CommonCodeConstant.FEE_DUE_STUDENT_DETAILS)){
			list = feeReportDAO
					.selectFeeDueStudent(feeReportKey);
			} else if(feeReportKey.getReportType().equals(CommonCodeConstant.FEE_COMPLETED_STUDNET_DETAILS)){
			list = feeReportDAO
					.selectFeePaidStudent(feeReportKey);
		}
		
		
		List<FeeReportListVO> feeReportList = new ArrayList<FeeReportListVO>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				FeeReportList feeReport = list.get(i);
				FeeReportListVO feeReportVo = new FeeReportListVO();
				commonBusiness.changeObject(feeReportVo, feeReport);
				feeReportVo.setRowId(i);
				feeReportList.add(feeReportVo);
			}
			feeReportMasterVO.setFeeReportList(feeReportList);
		}
		
	}

	
}
