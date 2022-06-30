package com.jaw.fee.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FeeMasterExistException;
import com.jaw.common.exceptions.FeeMasterNotFoundException;
import com.jaw.common.exceptions.FeeMasterNotFoundForIntegratedCourseException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.StudentNotFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.fee.controller.FeeDetailsMasterVO;
import com.jaw.fee.controller.FeeGenerationMasterVO;
import com.jaw.fee.controller.FeePaidListVO;
import com.jaw.fee.controller.FeePaymentList;
import com.jaw.fee.controller.FeePaymentMasterVO;
import com.jaw.fee.controller.FeePaymentSearchVO;
import com.jaw.fee.controller.FeeReceiptVO;
import com.jaw.fee.dao.FeeReceipt;
import com.jaw.fee.dao.IFeePaymentListDao;
import com.jaw.fee.dao.IStudentFeeDemandDAO;
import com.jaw.fee.dao.IStudentFeePaymentDao;
import com.jaw.fee.dao.ReceiptKey;
import com.jaw.fee.dao.StudentFeeDemand;
import com.jaw.fee.dao.StudentFeeDemandKey;
import com.jaw.fee.dao.StudentFeePayment;
import com.jaw.fee.dao.StudentFeePaymentKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.ICustIdGeneratorService;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.controller.StudentReportCardVO;
import com.jaw.mark.dao.IStudentReportCardDAO;
import com.jaw.student.admission.controller.StudentMasterVO;
import com.jaw.student.admission.dao.IStudentMasterDao;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.admission.dao.StudentMasterKey;

//course classes Details Service Class
@Service
public class FeePaymentService implements IFeePaymentService {
	// Logging
	Logger logger = Logger.getLogger(FeePaymentService.class);

	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	DoAudit doAudit;
	@Autowired
	CommonCodeUtil commonCodeUtil;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	IFeePaymentListDao feePaymentListDao;
	@Autowired
	IStudentFeePaymentDao studentFeePaymentDao;
	@Autowired
	IStudentFeeDemandDAO studentFeeDemandDAO;
	@Autowired
	IFeeGenerationService feeGenerationService;
	@Autowired
	IFeeDetailsService feeDetailsService;
	@Autowired
	IStudentMasterDao studentMasterDao;
	@Autowired
	IStudentReportCardDAO studentReportCardDAO;

	@Autowired
	FeeGenerationAdmissionHelper admissionHelper;
	@Autowired
	@Qualifier(value = "simpleCustIdGenerator")
	private ICustIdGeneratorService simpleCustIdGenerator;

	@Override
	public Map<String, String> getStudentAdmisNo(ApplicationCache applicationCache,String acTerm, String stGroup,
			String feeCategory, UserSessionDetails userSessionDetails)
			throws NoDataFoundException, PropertyNotFoundException {
		System.out.println("Acterm :" + acTerm + " stGroup :" + stGroup
				+ " feecategory :" + feeCategory);
		String admisCat=propertyManagementUtil.getPropertyValue(applicationCache, userSessionDetails.getInstId(), userSessionDetails.getBranchId(), PropertyManagementConstant.ADMISSION_FEE_CATEGORY);
		logger.debug("inside update Academic Term Details method");
		System.out.println("Admis cat :"+admisCat);
	/*	if (feeCategory
				.equals(admisCat)) {
			System.out.println("Inside if");
			return feePaymentListDao.getAdmissionStudentList(
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId(), acTerm, feeCategory);
		} else {
			System.out.println("inside else");*/
			return feePaymentListDao.getStuAdmisNoList(
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId(), acTerm, stGroup);

		//}

	}

	@Transactional(rollbackFor = Exception.class)
	private void generateFeeRecord(FeePaymentMasterVO feePaymentMasterVO,
			UserSessionDetails userSessionDetails, SessionCache sessionCache,
			ApplicationCache applicationCache) throws NoDataFoundException,
			FeeMasterNotFoundException, StudentNotFoundException,
			DuplicateEntryException, DatabaseException, UpdateFailedException,
			FeeMasterExistException, PropertyNotFoundException, FeeMasterNotFoundForIntegratedCourseException {
		String admisCat=propertyManagementUtil.getPropertyValue(applicationCache, userSessionDetails.getInstId(), userSessionDetails.getBranchId(), PropertyManagementConstant.ADMISSION_FEE_CATEGORY);
		logger.debug("inside update Academic Term Details method");
		System.out.println("Admis cat :"+admisCat);
		if (feePaymentMasterVO.getFeePaymentSearchVO().getFeeCategory()
				.equals(admisCat)) {
			FeeGenerationMasterVO feeGenerationMasterVO = new FeeGenerationMasterVO();
			StudentMasterKey studentMasterKey = new StudentMasterKey();
			studentMasterKey.setInstId(userSessionDetails.getInstId());
			studentMasterKey.setBranchId(userSessionDetails.getBranchId());
			studentMasterKey.setStudentAdmisNo(feePaymentMasterVO
					.getFeePaymentSearchVO().getStudentAdmisNo());
			studentMasterKey.setAcademicYear(feePaymentMasterVO
					.getFeePaymentSearchVO().getAcTerm());

			StudentMaster studentMaster = studentMasterDao
					.retriveStudentDetails(studentMasterKey);
			feeGenerationMasterVO.setAcTerm(feePaymentMasterVO
					.getFeePaymentSearchVO().getAcTerm());
			feeGenerationMasterVO.setCourseMasterId(studentMaster.getCourse());
			feeGenerationMasterVO.setTermId(studentMaster.getStandard());
			feeGenerationMasterVO.setFeeCategory(feePaymentMasterVO
					.getFeePaymentSearchVO().getFeeCategory());
			System.out.println("Fee generation :"+feeGenerationMasterVO.toString());

			admissionHelper.feeGenerate(feeGenerationMasterVO, sessionCache,
					studentMaster);
		}
	}

	@Override
	public void selectFeePaymentRec(FeePaymentMasterVO feePaymentMasterVO,
			UserSessionDetails userSessionDetails, SessionCache sessionCache,
			ApplicationCache applicationCache) throws NoDataFoundException,
			FeeMasterNotFoundException, StudentNotFoundException,
			DuplicateEntryException, DatabaseException, UpdateFailedException,
			FeeMasterExistException, PropertyNotFoundException, FeeMasterNotFoundForIntegratedCourseException {

		generateFeeRecord(feePaymentMasterVO, userSessionDetails, sessionCache,
				applicationCache);
		System.out.println("after fee record generation");
		StudentFeePaymentKey StudentFeePaymentKey = new StudentFeePaymentKey();
		commonBusiness.changeObject(StudentFeePaymentKey,
				feePaymentMasterVO.getFeePaymentSearchVO());
		StudentFeePaymentKey.setInstId(userSessionDetails.getInstId());
		StudentFeePaymentKey.setBranchId(userSessionDetails.getBranchId());

		try {
			StudentFeePayment studentFeePayment = studentFeePaymentDao
					.selectStudentFeePayment(StudentFeePaymentKey);
			commonBusiness.changeObject(feePaymentMasterVO.getFeePaymentVO(),
					studentFeePayment);
		} catch (NoDataFoundException e) {
			StudentFeeDemandKey studentFeeDemandKey = new StudentFeeDemandKey();
			commonBusiness.changeObject(studentFeeDemandKey,
					feePaymentMasterVO.getFeePaymentSearchVO());
			studentFeeDemandKey.setInstId(userSessionDetails.getInstId());
			studentFeeDemandKey.setBranchId(userSessionDetails.getBranchId());
			System.out.println("Fee category :"
					+ feePaymentMasterVO.getFeePaymentSearchVO()
							.getFeeCategory());

			StudentFeeDemand studentFeeDemand = studentFeeDemandDAO

			.selectStudentFeeDemand(studentFeeDemandKey);
			commonBusiness.changeObject(feePaymentMasterVO.getFeePaymentVO(),
					studentFeeDemand);
			feePaymentMasterVO.getFeePaymentVO().setFeeDueAmtBeforePmt(
					studentFeeDemand.getFeeDueAmt());
			feePaymentMasterVO.getFeePaymentVO().setStudFeeDDId(
					studentFeeDemand.getsFeeDmdSeqId());
			feePaymentMasterVO.getFeePaymentVO().setMonthlyFeeDueAmtBeforePmt(
					studentFeeDemand.getMonthlyFeeDueAmt());
		}

		System.out.println("fee payment vo :"
				+ feePaymentMasterVO.getFeePaymentVO().toString());
	}

	@Override
	public List<FeePaymentList> selectFeeReceiptRec(
			StudentFeePayment StudentFeePayment,
			FeePaymentMasterVO feePaymentMasterVO,
			UserSessionDetails userSessionDetails, SessionCache sessionCache,
			ApplicationCache applicationCache) throws NoDataFoundException,
			FeeMasterNotFoundException, StudentNotFoundException,
			DuplicateEntryException, DatabaseException, UpdateFailedException,
			FeeMasterExistException, PropertyNotFoundException {

		System.out.println("after fee record generation");
		FeePaidListVO feePaidListVO = new FeePaidListVO();

		feePaidListVO.setFeeCategory(feePaymentMasterVO.getFeePaymentSearchVO().getFeeCategory());
		feePaidListVO.setFeePaidAmt(StudentFeePayment.getFeePaidAmt());
		feePaidListVO.setFeePaymentDate(StudentFeePayment.getFeePmtDate());
		feePaidListVO.setsFeeDmdSeqId(StudentFeePayment.getStudFeeDDId());
		feePaidListVO.setFeePaymentSrlNo(StudentFeePayment.getFeePmtSrlNo());
		feePaidListVO.setFeePaymentSts(StudentFeePayment.getFeePmtStatus());
		feePaidListVO.setFeePaymentTerm(feePaymentMasterVO.getFeePaymentSearchVO().getFeePaymentTerm());
		feePaidListVO.setFeeReceiptCatgry(StudentFeePayment
				.getReceiptCategory());
		feePaidListVO.setFeeReceiptNum(StudentFeePayment.getFeeReceiptNo());
		feePaidListVO.setFeePaymentTerm(feePaymentMasterVO.getFeePaymentSearchVO().getFeePaymentTerm());
		FeeDetailsMasterVO feeDetailsMasterVO = new FeeDetailsMasterVO();
		feeDetailsMasterVO.getFeeDetailsSearchVO().setAdmissionNumber(
				feePaymentMasterVO.getFeePaymentSearchVO().getStudentAdmisNo());
		feeDetailsMasterVO.getFeeDetailsSearchVO().setAcademicTerm(
				feePaymentMasterVO.getFeePaymentSearchVO().getAcTerm());
		System.out.println("fee payment vo :" + feePaidListVO.toString());
		List<FeePaymentList> feePaymentLists = feeDetailsService
				.selectFeePaidPaymentListDetails(feePaidListVO, sessionCache,
						feeDetailsMasterVO, applicationCache);

		System.out.println("fee payments :" + feePaymentLists);

		ReceiptKey receiptKey = new ReceiptKey();
		receiptKey.setAcTerm(feePaymentMasterVO.getFeePaymentSearchVO().getAcTerm());
		receiptKey.setFeeCategory(feePaymentMasterVO.getFeePaymentSearchVO().getFeeCategory());
		receiptKey.setFeePaymentTerm(feePaymentMasterVO.getFeePaymentSearchVO().getFeePaymentTerm());
		receiptKey.setFeePmtSrlNo(StudentFeePayment.getFeePmtSrlNo());
		receiptKey.setStGroup(feePaymentMasterVO.getFeePaymentSearchVO().getStGroup());
		receiptKey.setStudentAdmisNo(feePaymentMasterVO.getFeePaymentSearchVO().getStudentAdmisNo());
		receiptKey.setStudFeeDDId(StudentFeePayment.getStudFeeDDId());
		receiptKey.setInstId(StudentFeePayment.getInstId());
		receiptKey.setBranchId(StudentFeePayment.getBranchId());
		
		System.out.println("term selection for receipt :"
				+ receiptKey.toString());
		String term = feePaymentListDao.getTerm(receiptKey);
		System.out.println("Receipt key :"+receiptKey.toString());
		System.out.println("result term :" + term);
		
		FeeReceipt feeReceipt= feePaymentListDao.selectReceiptRec(receiptKey);
		commonBusiness.changeObject(feePaymentMasterVO.getFeeReceiptVO(), feeReceipt);
		feePaymentMasterVO.getFeeReceiptVO().setFeePaymentTerm(term);
		System.out.println("Fee receipt :"+feeReceipt.toString());
		StudentMasterKey studentMasterKey = new StudentMasterKey();
		studentMasterKey.setStudentAdmisNo(feePaymentMasterVO.getFeePaymentSearchVO().getStudentAdmisNo());
		studentMasterKey.setInstId(userSessionDetails.getInstId());
		studentMasterKey.setBranchId(userSessionDetails.getBranchId());
		StudentMaster studentMaster = studentReportCardDAO
				.retriveStudentDetails(studentMasterKey);
		StudentMasterVO studentMasterVO = new StudentMasterVO();
		commonBusiness.changeObject(studentMasterVO, studentMaster);
		feePaymentMasterVO.setStudentMaster(studentMasterVO);
		System.out.println("Student master :" + studentMaster.toString());
		
		
		
		return feePaymentLists;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertFeePaymentRec(FeePaymentMasterVO feePaymentMasterVO,
			UserSessionDetails userSessionDetails, SessionCache sessionCache,
			ApplicationCache applicationCache) throws DatabaseException,
			DuplicateEntryException, NoDataFoundException,
			UpdateFailedException, FeeMasterNotFoundException,
			StudentNotFoundException, FeeMasterExistException,
			PropertyNotFoundException {

		logger.debug("inside insert Fee payment detail method");
		System.out.println("search vo : "
				+ feePaymentMasterVO.getFeePaymentSearchVO().toString());
		System.out.println("object vo : "
				+ feePaymentMasterVO.getFeePaymentVO().toString());
		StudentFeePayment studentFeePayment = new StudentFeePayment();
		// map the UIObject to Pojo

		StudentFeePaymentKey StudentFeePaymentKey = new StudentFeePaymentKey();
		commonBusiness.changeObject(StudentFeePaymentKey,
				feePaymentMasterVO.getFeePaymentSearchVO());
		StudentFeePaymentKey.setInstId(userSessionDetails.getInstId());
		StudentFeePaymentKey.setBranchId(userSessionDetails.getBranchId());
		StudentFeePaymentKey.setStudFeeDDId(feePaymentMasterVO
				.getFeePaymentVO().getStudFeeDDId());

		try {
			StudentFeePayment studentFeePayment1 = studentFeePaymentDao
					.selectStudentFeePayment(StudentFeePaymentKey);

			int srlNo = Integer.parseInt(studentFeePayment1.getFeePmtSrlNo()) + 1;
			System.out.println("Serial Number :" + srlNo);
			studentFeePayment.setFeePmtSrlNo(String.valueOf(srlNo));
		} catch (NoDataFoundException e) {
			studentFeePayment.setFeePmtSrlNo("1");
		}
		commonBusiness.changeObject(studentFeePayment,
				feePaymentMasterVO.getFeePaymentVO());
		System.out.println("after change object:"
				+ studentFeePayment.toString());
		studentFeePayment.setDbTs(1);
		studentFeePayment.setInstId(userSessionDetails.getInstId());
		studentFeePayment.setBranchId(userSessionDetails.getBranchId());
		studentFeePayment.setStudFeeDDId(feePaymentMasterVO.getFeePaymentVO()
				.getStudFeeDDId());
		studentFeePayment.setFeeDueAmtBeforePmt(feePaymentMasterVO
				.getFeePaymentVO().getFeeDueAmtBeforePmt());
		studentFeePayment.setFineAmt(0);
		System.out.println("testeeeeeee"+feePaymentMasterVO.getFeePaymentVO().getSelectFee());
		if(!feePaymentMasterVO.getFeePaymentVO().getSelectFee().equals("MonthlyFee")){
			System.out.println("Regular Fee Selected");
			studentFeePayment.setFeePaidAmt(feePaymentMasterVO.getFeePaymentVO()
					.getFeePaidAmt());
			studentFeePayment.setMonthlyFeePaidAmt(0);
		}
		else{
			System.out.println("Monthly Fee Selected");
			studentFeePayment.setMonthlyFeePaidAmt(feePaymentMasterVO.getFeePaymentVO()
					.getMonthlyFeePaidAmt());
			studentFeePayment.setFeePaidAmt(0);
			
		}
		System.out.println("Monthly Fee Paid Amt"+studentFeePayment.getMonthlyFeePaidAmt());
		System.out.println("Fee Paid Amt"+studentFeePayment.getFeePaidAmt());
		studentFeePayment.setFeePmtDate(feePaymentMasterVO.getFeePaymentVO()
				.getFeePmtDate());

		studentFeePayment.setPmtMode(feePaymentMasterVO.getFeePaymentVO()
				.getPmtMode());

		/*studentFeePayment.setFeePaidAmt(feePaymentMasterVO.getFeePaymentVO()
				.getFeePaidAmt());*/
		studentFeePayment.setrCreId(userSessionDetails.getUserId());
		studentFeePayment.setrModId(userSessionDetails.getUserId());
		studentFeePayment.setDelFlag("N");
		commonBusiness.changeObject(feePaymentMasterVO.getFeePaymentVO(),
				studentFeePayment);
		if ((feePaymentMasterVO.getFeePaymentVO().getIsReceiptRequired() != null)) {
			studentFeePayment.setFeeReceiptNo(AlphaSequenceUtil
					.generateAlphaSequence(
							ApplicationConstant.STRING_FEE_RECEIPT,
							simpleCustIdGenerator.getNextId(
									SequenceConstant.FEE_RECEIPT, 1,
									userSessionDetails)));
			studentFeePayment.setReceiptCategory(feePaymentMasterVO
					.getFeePaymentVO().getReceiptCategory());
			studentFeePayment.setFeeReceiptDate(feePaymentMasterVO
					.getFeePaymentVO().getFeePmtDate());
		}

		if (feePaymentMasterVO.getFeePaymentVO().getPmtMode().equals("FPMD1")) {
			studentFeePayment.setInstrumentDate(null);
			studentFeePayment
					.setFeePmtStatus(CommonCodeConstant.PAYMENT_RECIEVED);
		} else {
			studentFeePayment.setInstrumentDate(feePaymentMasterVO
					.getFeePaymentVO().getInstrumentDate());
			studentFeePayment.setInstrumentDetails(feePaymentMasterVO
					.getFeePaymentVO().getInstrumentDetails());
			studentFeePayment.setInstrumentNo(feePaymentMasterVO
					.getFeePaymentVO().getInstrumentNo());

			studentFeePayment
					.setFeePmtStatus(CommonCodeConstant.INSTRUMENT_RECEIVED_DD);
		}
		System.out.println("before insert in service :"
				+ studentFeePayment.toString());
		studentFeePaymentDao.insertStudentFeePayment(studentFeePayment);

		StudentFeeDemandKey studentFeeDemandKey = new StudentFeeDemandKey();
		commonBusiness.changeObject(studentFeeDemandKey,
				feePaymentMasterVO.getFeePaymentSearchVO());
		studentFeeDemandKey.setInstId(userSessionDetails.getInstId());
		System.out.println("StudentKey BranchId"+userSessionDetails.getBranchId());
		studentFeeDemandKey.setBranchId(userSessionDetails.getBranchId());
		StudentFeeDemand studentFeeDemand = studentFeeDemandDAO
				.selectStudentFeeDemand(studentFeeDemandKey);
		if (feePaymentMasterVO.getFeePaymentVO().getFeeDueAmtBeforePmt() > feePaymentMasterVO
				.getFeePaymentVO().getFeePaidAmt()) {
			studentFeeDemand
					.setFeeDemandStatus(CommonCodeConstant.PARTIAL_PAYMENT_RECEIVED);
		} else {
			studentFeeDemand
					.setFeeDemandStatus(CommonCodeConstant.FULL_PAYMENT_RECEIVED);
		}
		int feeAmount = studentFeeDemand.getFeeDueAmt()
				- feePaymentMasterVO.getFeePaymentVO().getFeePaidAmt();
		System.out.println("Fee Amount :" + feeAmount);
		int monthFeeAmount=studentFeeDemand.getMonthlyFeeDueAmt()-
				- feePaymentMasterVO.getFeePaymentVO().getMonthlyFeePaidAmt();
		System.out.println("Fee Amount :" + monthFeeAmount);
		studentFeeDemand.setFeeDueAmt(feeAmount);
		studentFeeDemand.setMonthlyFeeDueAmt(monthFeeAmount);
		studentFeeDemandKey.setsFeeDmdSeqId(studentFeeDemand.getsFeeDmdSeqId());
		studentFeeDemandKey.setDbTs(studentFeeDemand.getDbTs());
		studentFeeDemandDAO.updateStudentFeeDemandKey(studentFeeDemand,
				studentFeeDemandKey);

		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.FEES_PAYMENT_DETAIL_INSERT, "");

		System.out.println("Before Service :"
				+ feePaymentMasterVO.getFeePaymentVO().toString());
		logger.debug("Completed Functional Auditing");
		if (feePaymentMasterVO.getFeePaymentVO().getReceiptCategory()
				.equals(CommonCodeConstant.RECEIPT_CATGRY_BRANCH)) {
		List<FeePaymentList> feePaymentLists = selectFeeReceiptRec(
				studentFeePayment, feePaymentMasterVO,
				userSessionDetails, sessionCache, applicationCache);
		feePaymentMasterVO.setFeePaymentLists(feePaymentLists);
		} if (feePaymentMasterVO.getFeePaymentVO().getReceiptCategory()
				.equals(CommonCodeConstant.RECEIPT_CATGRY_TRUST)) {
			commonBusiness.changeObject(feePaymentMasterVO.getFeePaymentVO(), studentFeePayment);
		}

		
	}

}
