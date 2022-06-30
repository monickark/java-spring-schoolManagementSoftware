package com.jaw.fee.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.dao.CourseTermLinking;
import com.jaw.core.dao.CourseTermLinkingKey;
import com.jaw.fee.controller.FeePaymentDetailMasterVO;
import com.jaw.fee.controller.FeePaymentDetailVO;
import com.jaw.fee.dao.FeePaymentDetail;
import com.jaw.fee.dao.FeePaymentDetailKey;
import com.jaw.fee.dao.IFeePaymentDetailDAO;
import com.jaw.fee.dao.IFeePaymentDetailListDAO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
@Service
public class FeePaymentDetailService implements IFeePaymentDetailService {
	// Logging
	Logger logger = Logger.getLogger(FeePaymentDetailService.class);
	@Autowired
	DoAudit doAudit;
	/*@Autowired
	ICourseTermLinkingDAO courseTermLinkingDAO;*/
	@Autowired
	IFeePaymentDetailListDAO feePaymentDetailsListDAO;
	@Autowired
	IFeePaymentDetailDAO feePaymentDetailDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Override
	public FeePaymentDetailMasterVO selectFeePaymentDetailsList(
			FeePaymentDetailMasterVO feePaymentDetailMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		FeePaymentDetailKey feePaymentDetailsKey = new FeePaymentDetailKey();
		commonBusiness.changeObject(feePaymentDetailsKey, feePaymentDetailMasterVO.getFeePaymentDetailSearchVO());
		feePaymentDetailsKey.setInstId(userSessionDetails.getInstId());
		feePaymentDetailsKey.setBranchId(userSessionDetails.getBranchId());
		List<FeePaymentDetail> feePaymentDetailsList = feePaymentDetailsListDAO.getFeePaymentDetailsList(feePaymentDetailsKey);

		List<FeePaymentDetailVO> feePaymentDetVos = new ArrayList<FeePaymentDetailVO>();
		for (int i = 0; i < feePaymentDetailsList.size(); i++) {
			FeePaymentDetail feePaymentDetail = feePaymentDetailsList.get(i);
			FeePaymentDetailVO feePaymentDetVo = new FeePaymentDetailVO();
			commonBusiness.changeObject(feePaymentDetVo, feePaymentDetail);
			feePaymentDetVo.setRowId(i);
			feePaymentDetVos.add(feePaymentDetVo);
		}
		feePaymentDetailMasterVO.setFeePaymentDetailsVOs(feePaymentDetVos);

		return feePaymentDetailMasterVO;

	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertFeePaymentDetailRec(
			FeePaymentDetailMasterVO feePaymentDetailMasterVO,
			UserSessionDetails userSessionDetails) throws DatabaseException, DuplicateEntryException, UpdateFailedException
			 {

		logger.debug("inside insert Fee payment detail method");
System.out.println("search vo : "+feePaymentDetailMasterVO.getFeePaymentDetailSearchVO().toString());
		FeePaymentDetail feePaymentDetail = new FeePaymentDetail();
		// map the UIObject to Pojo
		commonBusiness.changeObject(feePaymentDetail, feePaymentDetailMasterVO.getFeePaymentDetailVO());

		feePaymentDetail.setDbTs(1);
		feePaymentDetail.setInstId(userSessionDetails.getInstId());
		feePaymentDetail.setBranchId(userSessionDetails.getBranchId());
		feePaymentDetail.setrCreId(userSessionDetails.getUserId());
		feePaymentDetail.setrModId(userSessionDetails.getUserId());
		feePaymentDetail.setDelFlag("N");
		feePaymentDetail.setAcTerm(feePaymentDetailMasterVO.getFeePaymentDetailSearchVO().getAcTerm());
		//feePaymentDetail.setAcTerm(userSessionDetails.getCurrAcTerm());
		try {
			feePaymentDetailDAO.insertFeePaymentDetailRec(feePaymentDetail);
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.FEES_PAYMENT_DETAIL_INSERT, "");
			logger.debug("Completed Functional Auditing");
		} catch (DuplicateEntryException e) {
			FeePaymentDetailKey feePaymentDetailKey=new FeePaymentDetailKey();
			commonBusiness.changeObject(feePaymentDetailKey, feePaymentDetail);
			try {
				feePaymentDetailKey.setDbTs(0);
				FeePaymentDetail feePayDetNew=feePaymentDetailDAO.selectFeePaymentDetailDelFlgYRec(feePaymentDetailKey);
				feePaymentDetailKey.setDbTs(feePayDetNew.getDbTs());
				feePaymentDetailDAO.updateFeePaymentDetailRecDelFlgYesToNo(feePayDetNew, feePaymentDetailKey);
				// functional audit
				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.FEES_PAYMENT_DETAIL_INSERT, "Fee Payment detail updated from del flag yes to no ");
				logger.debug("Completed Functional Auditing");
			} catch (NoDataFoundException e2) {
				throw new DuplicateEntryException();
			}
			
		}
		
		

	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteFeePaymentDetailRec(
			FeePaymentDetailVO feePaymentDetailVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {
		
		
		FeePaymentDetailKey feePaymentDetailKey = new FeePaymentDetailKey();
		commonBusiness.changeObject(feePaymentDetailKey, feePaymentDetailVO);
		
		feePaymentDetailKey.setInstId(userSessionDetails.getInstId());
		feePaymentDetailKey.setBranchId(userSessionDetails.getBranchId());
		FeePaymentDetail feePaymentDetailNew=feePaymentDetailDAO.selectFeePaymentDetailRec(feePaymentDetailKey);
		
		FeePaymentDetail deleteFeePaymentDetail=new FeePaymentDetail();
		commonBusiness.changeObject(deleteFeePaymentDetail, feePaymentDetailNew);
		feePaymentDetailKey.setDbTs(feePaymentDetailNew.getDbTs());
		deleteFeePaymentDetail.setrModId(userSessionDetails.getUserId());
		deleteFeePaymentDetail.setDbTs(feePaymentDetailNew.getDbTs());
		deleteFeePaymentDetail.setDelFlag("Y");
		// get Data's from DB for dbts value
		feePaymentDetailDAO.deleteFeePaymentDetailRec(deleteFeePaymentDetail, feePaymentDetailKey);
		// functional audit
				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.FEES_PAYMENT_DETAIL_DELETE, " ");
				logger.debug("Completed Functional Auditing");
				
				// Database audit
				
				String oldRecString = feePaymentDetailNew.toStringForAuditFeePaymentDetailRecord();
				
				String newRecString = deleteFeePaymentDetail.toStringForAuditFeePaymentDetailRecord();
				doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
						TableNameConstant.FEE_PAYMENT_DETAIL,
						feePaymentDetailKey.toStringForAuditFeePaymentDetailKey(),
						oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, "",
						"");

				logger.debug("Completed Database Auditing");

	}

}


