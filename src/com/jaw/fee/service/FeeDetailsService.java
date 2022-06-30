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
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FeeMasterExistException;
import com.jaw.common.exceptions.FeeMasterNotFoundException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.StudentNotFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.fee.controller.FeeDetailsMasterVO;
import com.jaw.fee.controller.FeeDueListVO;
import com.jaw.fee.controller.FeePaidListVO;
import com.jaw.fee.controller.FeePaymentList;
import com.jaw.fee.controller.FeePaymentSearchVO;
import com.jaw.fee.dao.FeeDueDetailsList;
import com.jaw.fee.dao.FeeDueList;
import com.jaw.fee.dao.FeeDueListKey;
import com.jaw.fee.dao.FeePaidList;
import com.jaw.fee.dao.FeePaidListKey;
import com.jaw.fee.dao.FeeReceipt;
import com.jaw.fee.dao.IFeeDetailsListDAO;
import com.jaw.fee.dao.IFeePaymentListDao;
import com.jaw.fee.dao.IStudentFeeListDAO;
import com.jaw.fee.dao.IStudentFeePaymentDao;
import com.jaw.fee.dao.IStudentFeePaymentListDAO;
import com.jaw.fee.dao.ReceiptKey;
import com.jaw.fee.dao.StudentFee;
import com.jaw.fee.dao.StudentFeePayment;
import com.jaw.fee.dao.StudentFeePaymentKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.ICustIdGeneratorService;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.dao.IStudentReportCardDAO;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.admission.dao.StudentMasterKey;

@Service
public class FeeDetailsService implements IFeeDetailsService {
	// Logging
	Logger logger = Logger.getLogger(FeeGenerationService.class);
	@Autowired
	DoAudit doAudit;

	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IFeeDetailsListDAO feeDetailsListDAO;
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
	public List<FeeDueList> selectFeeDueAndPaidListDetails(
			SessionCache sessionCache, FeeDetailsMasterVO feeDetailsMasterVO)
			throws NoDataFoundException {
		FeeDueListKey feeDueListKey = new FeeDueListKey();
		commonBusiness.changeObject(feeDueListKey,
				feeDetailsMasterVO.getFeeDetailsSearchVO());
		feeDueListKey
				.setFeeDemandStatus(CommonCodeConstant.FULL_PAYMENT_RECEIVED);
		System.out.println("fee due list keyyyyyyy  :"
				+ feeDueListKey.toString());

		if ((sessionCache.getUserSessionDetails().getProfileGroup()
				.equals(ApplicationConstant.PG_STUDENT))) {
			feeDueListKey.setAdmissionNumber(sessionCache.getStudentSession()
					.getStudentAdmisNo());
			feeDueListKey.setAcademicTerm(sessionCache.getUserSessionDetails()
					.getCurrAcTerm());
		} else if ((sessionCache.getUserSessionDetails().getProfileGroup()
				.equals(ApplicationConstant.PG_PARENT))) {
			StudentSession studentSession2 = sessionCache
					.getParentSession()
					.getStudentSession()
					.get(Integer.parseInt(sessionCache.getParentSession()
							.getSelectedStuIndex()));

			feeDueListKey.setAdmissionNumber(studentSession2
					.getStudentAdmisNo());
			feeDueListKey.setAcademicTerm(sessionCache.getUserSessionDetails()
					.getCurrAcTerm());
		}
		feeDueListKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		feeDueListKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		List<FeeDueList> feeDues = feeDetailsListDAO
				.selectFeeDueListDetails(feeDueListKey);
		List<FeeDueListVO> feeDueVos = new ArrayList<FeeDueListVO>();
		if (feeDues != null) {
			for (int i = 0; i < feeDues.size(); i++) {
				FeeDueList feeDueList = feeDues.get(i);
				FeeDueListVO feeDueListVo = new FeeDueListVO();
				commonBusiness.changeObject(feeDueListVo, feeDueList);
				feeDueListVo.setRowId(i);
				feeDueVos.add(feeDueListVo);
			}
			feeDetailsMasterVO.setFeeDueList(feeDueVos);
		}
		// For Fee paid list
		FeePaidListKey feePaidListKey = new FeePaidListKey();
		commonBusiness.changeObject(feePaidListKey,
				feeDetailsMasterVO.getFeeDetailsSearchVO());
		feePaidListKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		feePaidListKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());

		System.out.println("fee Paid list keyyyyyyy  :"
				+ feePaidListKey.toString());

		if ((sessionCache.getUserSessionDetails().getProfileGroup()
				.equals(ApplicationConstant.PG_STUDENT))) {
			feePaidListKey.setAdmissionNumber(sessionCache.getStudentSession()
					.getStudentAdmisNo());
			feePaidListKey.setAcademicTerm(sessionCache.getUserSessionDetails()
					.getCurrAcTerm());
		} else if ((sessionCache.getUserSessionDetails().getProfileGroup()
				.equals(ApplicationConstant.PG_PARENT))) {
			StudentSession studentSession2 = sessionCache
					.getParentSession()
					.getStudentSession()
					.get(Integer.parseInt(sessionCache.getParentSession()
							.getSelectedStuIndex()));

			feePaidListKey.setAdmissionNumber(studentSession2
					.getStudentAdmisNo());
			feePaidListKey.setAcademicTerm(sessionCache.getUserSessionDetails()
					.getCurrAcTerm());
		}

		List<FeePaidList> feePaids = feeDetailsListDAO
				.selectFeePaidListDetails(feePaidListKey);
		List<FeePaidListVO> feePaidVos = new ArrayList<FeePaidListVO>();
		if (feePaids != null) {
			for (int i = 0; i < feePaids.size(); i++) {
				FeePaidList feePaidList = feePaids.get(i);
				FeePaidListVO feePaidListVO = new FeePaidListVO();
				commonBusiness.changeObject(feePaidListVO, feePaidList);
				feePaidListVO.setRowId(i);
				feePaidVos.add(feePaidListVO);
			}
			feeDetailsMasterVO.setFeePaidList(feePaidVos);
		}
		if ((feeDues == null) && (feeDues == null)) {
			throw new NoDataFoundException();
		}
		return feeDues;
	}

	@Override
	public void printReceipt(FeePaidListVO feePaidListVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws NoDataFoundException,
			DatabaseException, UpdateFailedException {

		StudentFeePaymentKey StudentFeePaymentKey = new StudentFeePaymentKey();
		commonBusiness.changeObject(StudentFeePaymentKey, feePaidListVO);
		StudentFeePaymentKey.setInstId(userSessionDetails.getInstId());
		StudentFeePaymentKey.setBranchId(userSessionDetails.getBranchId());
		StudentFeePaymentKey.setStudFeeDDId(feePaidListVO.getsFeeDmdSeqId());
		StudentFeePaymentKey.setFeePmtSrlNo(feePaidListVO.getFeePaymentSrlNo());
		StudentFeePaymentKey.setReceiptCategory(feePaidListVO
				.getFeeReceiptCatgry());
		StudentFeePayment studentFeePayment = studentFeePaymentDao
				.selectStudentFeePayment(StudentFeePaymentKey);
		StudentFeePaymentKey.setDbTs(studentFeePayment.getDbTs());
		studentFeePayment.setFeeReceiptNo(AlphaSequenceUtil
				.generateAlphaSequence(ApplicationConstant.STRING_FEE_RECEIPT,
						simpleCustIdGenerator.getNextId(
								SequenceConstant.FEE_RECEIPT, 1,
								userSessionDetails)));

		studentFeePayment.setFeeReceiptDate(dateUtil.getCurrentDate());
		System.out.println("Print receipt key :"
				+ StudentFeePaymentKey.toString());
		System.out.println("Print receipt  :" + studentFeePayment.toString());
		studentFeePaymentDao.updateStudentFeePayment(studentFeePayment,
				StudentFeePaymentKey);

	}

	@Override
	public void selectFeeReceiptRec(FeeDetailsMasterVO feeDetailsMasterVO,
			UserSessionDetails userSessionDetails, SessionCache sessionCache,
			ApplicationCache applicationCache) throws NoDataFoundException,
			FeeMasterNotFoundException, StudentNotFoundException,
			DuplicateEntryException, DatabaseException, UpdateFailedException,
			FeeMasterExistException, PropertyNotFoundException {

		ReceiptKey receiptKey = new ReceiptKey();
		receiptKey.setAcTerm(feeDetailsMasterVO.getFeeDetailsSearchVO()
				.getAcademicTerm());
		receiptKey.setFeeCategory(feeDetailsMasterVO.getFeePaidListVO()
				.getFeeCategory());
		receiptKey.setFeePaymentTerm(feeDetailsMasterVO.getFeePaidListVO()
				.getFeePaymentTerm());
		receiptKey.setFeePmtSrlNo(feeDetailsMasterVO.getFeePaidListVO()
				.getFeePaymentSrlNo());
		receiptKey.setStGroup(feeDetailsMasterVO.getFeeDetailsSearchVO()
				.getStudentGroupId());
		receiptKey.setStudentAdmisNo(feeDetailsMasterVO.getFeeDetailsSearchVO()
				.getAdmissionNumber());
		receiptKey.setStudFeeDDId(feeDetailsMasterVO.getFeePaidListVO()
				.getsFeeDmdSeqId());
		receiptKey.setInstId(userSessionDetails.getInstId());
		receiptKey.setBranchId(userSessionDetails.getBranchId());

		String term = feePaymentListDao.getTerm(receiptKey);

		StudentMasterKey studentMasterKey = new StudentMasterKey();
		studentMasterKey.setInstId(userSessionDetails.getInstId());
		studentMasterKey.setBranchId(userSessionDetails.getBranchId());
		studentMasterKey.setAcademicYear(feeDetailsMasterVO
				.getFeeDetailsSearchVO().getAcademicTerm());
		studentMasterKey.setStudentAdmisNo(feeDetailsMasterVO
				.getFeeDetailsSearchVO().getAdmissionNumber());
		StudentMaster studentMaster = studentReportCardDAO
				.retriveStudentDetails(studentMasterKey);
		FeeReceipt feeReceipt = feePaymentListDao.selectReceiptRec(receiptKey);
		commonBusiness.changeObject(feeDetailsMasterVO.getFeeReceiptVO(),
				feeReceipt);
		feeDetailsMasterVO.getFeeReceiptVO().setFeePaymentTerm(term);
		feeDetailsMasterVO.getFeeReceiptVO().setStudentName(
				studentMaster.getStudentName());
		feeDetailsMasterVO.getFeeReceiptVO().setStudentAdmisNo(
				studentMaster.getStudentAdmisNo());
		feeDetailsMasterVO.getFeeReceiptVO().setStudentGroup(
				studentMaster.getStuGrpId());
	}

	@Override
	public List<FeeDueDetailsList> getFeeDueDetailsList(
			FeeDueListVO feeDueListVo, UserSessionDetails userSessionDetails,
			FeeDetailsMasterVO feeDetailsMasterVO) throws NoDataFoundException {
		FeeDueList feeDueList = new FeeDueList();
		commonBusiness.changeObject(feeDueList, feeDueListVo);
		feeDueList.setInstId(userSessionDetails.getInstId());
		feeDueList.setBranchId(userSessionDetails.getBranchId());
		feeDueList.setAdmissionNumber(feeDetailsMasterVO
				.getFeeDetailsSearchVO().getAdmissionNumber());
		List<FeeDueDetailsList> feeDueDetList = feeDetailsListDAO
				.selectFeeDueDetailsList(feeDueList);
		return feeDueDetList;
	}
	
	@Override
	public List<FeeDueDetailsList> getFeeConsolidation(
			FeeDueListVO feeDueListVo, UserSessionDetails userSessionDetails,
			FeePaymentSearchVO feePaymentSearchVO) throws NoDataFoundException {
		FeeDueList feeDueList = new FeeDueList();
		commonBusiness.changeObject(feeDueList, feePaymentSearchVO);
		feeDueList.setInstId(userSessionDetails.getInstId());
		feeDueList.setBranchId(userSessionDetails.getBranchId());
		feeDueList.setAdmissionNumber(feePaymentSearchVO.getStudentAdmisNo());
		List<FeeDueDetailsList> feeDueDetList = feeDetailsListDAO
				.selectFeeDueDetailsList(feeDueList);
		return feeDueDetList;
	}

	@Override
	public List<FeePaymentList> selectFeePaidPaymentListDetails(
			FeePaidListVO feePaidListVo, SessionCache sessionCache,
			FeeDetailsMasterVO feeDetailsMasterVO,
			ApplicationCache applicationCache) throws NoDataFoundException,
			PropertyNotFoundException {
		FeePaidList feePaidList = new FeePaidList();
		commonBusiness.changeObject(feePaidList, feePaidListVo);
		// Get the Student payment list
		StudentFeePaymentKey stuFeePaymentKey = new StudentFeePaymentKey();
		stuFeePaymentKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		stuFeePaymentKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		stuFeePaymentKey
				.setReceiptCategory(CommonCodeConstant.RECEIPT_CATGRY_BRANCH);
		stuFeePaymentKey.setStudFeeDDId(feePaidList.getsFeeDmdSeqId());
		stuFeePaymentKey.setFeePmtSrlNo(feePaidList.getFeePaymentSrlNo());
		List<StudentFeePayment> paymentList = studentFeePaymentListDAO
				.selectStudentFeePaymentListForFeePaid(stuFeePaymentKey);

		// Get the student Fee list
		String excludeFeeType = propertyManagementUtil.getPropertyValue(
				applicationCache,
				sessionCache.getUserSessionDetails().getInstId(),
				sessionCache.getUserSessionDetails().getBranchId(),
				PropertyManagementConstant.EXCLUDE_FEETYPE).toString();
		StudentFee studentFee = new StudentFee();
		studentFee.setInstId(sessionCache.getUserSessionDetails().getInstId());
		studentFee.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		studentFee.setFeePaymentTerm(feePaidList.getFeePaymentTerm());
		studentFee.setFeeCategory(feePaidList.getFeeCategory());
		studentFee.setStudentAdmissNo(feeDetailsMasterVO
				.getFeeDetailsSearchVO().getAdmissionNumber());
		studentFee.setAcTerm(feeDetailsMasterVO.getFeeDetailsSearchVO()
				.getAcademicTerm());
		if ((excludeFeeType != "") && (excludeFeeType != null)
				&& (!excludeFeeType.equals(""))) {
			studentFee.setFeeType(excludeFeeType);
		} else {
			studentFee.setFeeType("");
		}
		List<StudentFee> studentFeeList = studentFeeListDAO
				.selectStudentFeeListForFeePaidList(studentFee);

		List<FeePaymentList> allFeePaymentList = getFeePaymentDetails(
				studentFeeList, paymentList, feePaidList);

		// Get the current serial Number values
		List<FeePaymentList> finalFeePaymentList = new ArrayList<FeePaymentList>();

		for (int aa = 0; aa < allFeePaymentList.size(); aa++) {

			if ((allFeePaymentList.get(aa).getFeePaidSerialNum() == null)
					|| (allFeePaymentList.get(aa).getFeePaidSerialNum()
							.equals(feePaidList.getFeePaymentSrlNo()))) {
				finalFeePaymentList.add(allFeePaymentList.get(aa));
				allFeePaymentList.get(aa);
			}

		}
		return finalFeePaymentList;

	}

	// Method for FeePayment list logic
	List<FeePaymentList> getFeePaymentDetails(List<StudentFee> studentFeeList,
			List<StudentFeePayment> paymentList, FeePaidList feePaidList) {
		List<FeePaymentList> allFeePaymentList = new ArrayList<FeePaymentList>();
		// For fee payment serial num 1
		if ((studentFeeList != null)
				&& (feePaidList.getFeePaymentSrlNo().equals("1"))) {
			for (int pml = 0; pml < paymentList.size(); pml++) {
				Integer paymentAmount = paymentList.get(pml).getFeePaidAmt();
				for (int sfl = 0; sfl < studentFeeList.size(); sfl++) {

					FeePaymentList feePaymentList = new FeePaymentList();
					if (studentFeeList.get(sfl).getFeeAmt() < paymentAmount) {
						feePaymentList.setElectiveSpe(studentFeeList.get(sfl)
								.getElecticeSpec());
						feePaymentList.setFeeType(studentFeeList.get(sfl)
								.getFeeType());
						feePaymentList.setReceiptFeeAmt(studentFeeList.get(sfl)
								.getFeeAmt());
						paymentAmount = paymentAmount
								- studentFeeList.get(sfl).getFeeAmt();

						allFeePaymentList.add(feePaymentList);
					} else if (studentFeeList.get(sfl).getFeeAmt() >= paymentAmount) {
						feePaymentList.setElectiveSpe(studentFeeList.get(sfl)
								.getElecticeSpec());
						feePaymentList.setFeeType(studentFeeList.get(sfl)
								.getFeeType());
						feePaymentList.setReceiptFeeAmt(paymentAmount);
						allFeePaymentList.add(feePaymentList);

						// paymentAmount=paymentAmount-studentFeeList.get(sfl).getFeeAmt();
						break;
					}

				}
			}
		} else if ((studentFeeList != null)
				&& (Integer.parseInt(feePaidList.getFeePaymentSrlNo()) > 1)) {
			for (int pml = 0; pml < paymentList.size(); pml++) {
				List<FeePaymentList> localFeePaymentList = new ArrayList<FeePaymentList>();
				Integer paymentAmount = paymentList.get(pml).getFeePaidAmt();

				for (int sfl = 0; sfl < studentFeeList.size(); sfl++) {
					FeePaymentList feePaymentList = new FeePaymentList();

					if (allFeePaymentList.size() == 0) {// if all fee payment
														// list is 0

						if (studentFeeList.get(sfl).getFeeAmt() < paymentAmount) {
							feePaymentList.setElectiveSpe(studentFeeList.get(
									sfl).getElecticeSpec());
							feePaymentList.setFeeType(studentFeeList.get(sfl)
									.getFeeType());
							feePaymentList.setReceiptFeeAmt(studentFeeList.get(
									sfl).getFeeAmt());
							feePaymentList.setFeePaidSerialNum(paymentList.get(
									pml).getFeePmtSrlNo());
							paymentAmount = paymentAmount
									- studentFeeList.get(sfl).getFeeAmt();
							localFeePaymentList.add(feePaymentList);
						} else if (studentFeeList.get(sfl).getFeeAmt() >= paymentAmount) {
							feePaymentList.setElectiveSpe(studentFeeList.get(
									sfl).getElecticeSpec());
							feePaymentList.setFeeType(studentFeeList.get(sfl)
									.getFeeType());
							feePaymentList.setReceiptFeeAmt(paymentAmount);
							feePaymentList.setFeePaidSerialNum(paymentList.get(
									pml).getFeePmtSrlNo());
							localFeePaymentList.add(feePaymentList);
							// paymentAmount=paymentAmount-studentFeeList.get(sfl).getFeeAmt();
							break;
						}
					} else {// if all fee payment list is not 0

						for (int aa = 0; aa < allFeePaymentList.size(); aa++) {

						}

						boolean existFeeType = false;
						boolean insert = true;
						Integer amountToBePaid = 0;
						Integer amountPaid = 0;
						for (int apl = 0; apl < allFeePaymentList.size(); apl++) {
							if ((allFeePaymentList.get(apl).getFeeType()
									.equals(studentFeeList.get(sfl)
											.getFeeType()))) {
								insert = false;

								if ((allFeePaymentList.get(apl)
										.getReceiptFeeAmt() < studentFeeList
										.get(sfl).getFeeAmt())) {

									amountPaid = amountPaid
											+ allFeePaymentList.get(apl)
													.getReceiptFeeAmt();
									existFeeType = true;
									insert = true;
								}

							}
						}
						amountToBePaid = studentFeeList.get(sfl).getFeeAmt()
								- amountPaid;

						if (existFeeType && insert) {

							if (amountToBePaid < paymentAmount) {
								feePaymentList.setElectiveSpe(studentFeeList
										.get(sfl).getElecticeSpec());
								feePaymentList.setFeeType(studentFeeList.get(
										sfl).getFeeType());
								feePaymentList.setReceiptFeeAmt(amountToBePaid);
								paymentAmount = paymentAmount - amountToBePaid;
								feePaymentList.setFeePaidSerialNum(paymentList
										.get(pml).getFeePmtSrlNo());

								localFeePaymentList.add(feePaymentList);
							} else if (amountToBePaid >= paymentAmount) {
								feePaymentList.setElectiveSpe(studentFeeList
										.get(sfl).getElecticeSpec());
								feePaymentList.setFeeType(studentFeeList.get(
										sfl).getFeeType());
								feePaymentList.setReceiptFeeAmt(paymentAmount);
								feePaymentList.setFeePaidSerialNum(paymentList
										.get(pml).getFeePmtSrlNo());
								localFeePaymentList.add(feePaymentList);
								// paymentAmount=0;

								// paymentAmount=paymentAmount-studentFeeList.get(sfl).getFeeAmt();
								break;
							}
						} else if (insert) {

							if (studentFeeList.get(sfl).getFeeAmt() < paymentAmount) {
								feePaymentList.setElectiveSpe(studentFeeList
										.get(sfl).getElecticeSpec());
								feePaymentList.setFeeType(studentFeeList.get(
										sfl).getFeeType());
								feePaymentList.setReceiptFeeAmt(studentFeeList
										.get(sfl).getFeeAmt());
								paymentAmount = paymentAmount
										- studentFeeList.get(sfl).getFeeAmt();
								feePaymentList.setFeePaidSerialNum(paymentList
										.get(pml).getFeePmtSrlNo());
								localFeePaymentList.add(feePaymentList);
							} else if (studentFeeList.get(sfl).getFeeAmt() >= paymentAmount) {
								feePaymentList.setElectiveSpe(studentFeeList
										.get(sfl).getElecticeSpec());
								feePaymentList.setFeeType(studentFeeList.get(
										sfl).getFeeType());
								feePaymentList.setReceiptFeeAmt(paymentAmount);
								feePaymentList.setFeePaidSerialNum(paymentList
										.get(pml).getFeePmtSrlNo());
								localFeePaymentList.add(feePaymentList);
								// paymentAmount=paymentAmount-studentFeeList.get(sfl).getFeeAmt();
								break;
							}
						}

					}
				}

				for (int index = 0; index < localFeePaymentList.size(); index++) {
					if (localFeePaymentList.get(index).getReceiptFeeAmt() != 0) {
						allFeePaymentList.add(localFeePaymentList.get(index));
					}

				}
			}
		}
		return allFeePaymentList;
	}

}
