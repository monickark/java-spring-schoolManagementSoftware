package com.jaw.fee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.BatchConstants;
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
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.fee.controller.FeeGenerationMasterVO;
import com.jaw.fee.dao.FeeMasterKey;
import com.jaw.fee.dao.FeeMasterListForFeeGen;
import com.jaw.fee.dao.FeeMasterStatus;
import com.jaw.fee.dao.FeeMasterStatusKey;
import com.jaw.fee.dao.FeeStatusKey;
import com.jaw.fee.dao.IFeeGenerationDAO;
import com.jaw.fee.dao.IFeeGenerationListDAO;
import com.jaw.fee.dao.IFeeMasterListDao;
import com.jaw.fee.dao.IFeeMasterStatusListDAO;
import com.jaw.fee.dao.IFeeStatusDao;
import com.jaw.fee.dao.IStudentFeeDemandListDAO;
import com.jaw.fee.dao.IStudentFeeListDAO;
import com.jaw.fee.dao.StudentFee;
import com.jaw.fee.dao.StudentFeeDemand;
import com.jaw.fee.dao.StudentFeeKey;
import com.jaw.fee.dao.StudentMasterForFeeGen;
import com.jaw.fee.dao.StudentMasterForFeeGenKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.dao.StudentMasterKey;

@Service
public class FeeGenerationService implements IFeeGenerationService {
	// Logging
	Logger logger = Logger.getLogger(FeeGenerationService.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	IFeeGenerationDAO feeGenerationDAO;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IFeeMasterListDao feeMasterListDao;
	@Autowired
	IFeeGenerationListDAO feeGenerationListDAO;
	@Autowired
	IStudentFeeListDAO studentFeeListDAO;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Autowired
	IStudentFeeDemandListDAO studentFeeDemandListDAO;
	@Autowired
	IFeeStatusDao feeStatusDao;
	@Autowired
	IFeeMasterStatusListDAO feeMasterStatusListDAO;
	ArrayList<String> errorList;
	String threadId = "";

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String feeGenerate(final ApplicationCache applicationCache,
			final FeeGenerationMasterVO feeGenerationMasterVO,
			final SessionCache sessionCache) throws FeeMasterNotFoundException,
			StudentNotFoundException, DuplicateEntryException,
			DatabaseException, UpdateFailedException, FeeMasterExistException,
			FeeMasterNotFoundForIntegratedCourseException, NoDataFoundException {

		/**
		 * Process of fee generation 1. Check fee status for current class is
		 * E(Which is get updated during fee master entry) 2. Check fee master
		 * exist for all terms - throw fee master exist exception (Actually not
		 * an exception) if not - throw fee master not exist exception 3. Check
		 * fee master available for course variant 4. Check whether students are
		 * available for that particular class else throw
		 * StudentNotFoundExceptions 5. Fee Generation 5.1 Select fee master non
		 * - elective subjetcs 5.2 Select Fee master elective subject 5.3 Select
		 * fee master course variant list 5.4 Select student list to insert fee
		 * 5.5 Get Student fee list object 5.5.1
		 * 
		 */

		FeeStatusKey feeStatusKey = new FeeStatusKey();
		commonBusiness.changeObject(feeStatusKey, feeGenerationMasterVO);
		feeStatusKey
				.setInstId(sessionCache.getUserSessionDetails().getInstId());
		feeStatusKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		feeStatusKey.setFeeStatus(ApplicationConstant.FEE_STATUS_ENTERED);
		// Integer feeMasAva=0;

		// Total term length
		Integer length = 1;
		List<String> termList = new ArrayList<String>();

		if (feeGenerationMasterVO.getTermId().contains(",")) {
			length = feeGenerationMasterVO.getTermId().split(",").length;
		}

		for (int i = 0; i < length; i++) {
			if (feeGenerationMasterVO.getTermId().contains(",")) {
				termList.add(feeGenerationMasterVO.getTermId().split(",")[i]);
			} else {
				termList.add(feeGenerationMasterVO.getTermId());
			}

		}

		FeeMasterStatus feeMasterStatus = new FeeMasterStatus();
		feeMasterStatus.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		feeMasterStatus.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		feeMasterStatus.setAcTerm(feeGenerationMasterVO.getAcTerm());
		feeMasterStatus.setCourse(feeGenerationMasterVO.getCourseMasterId());
		feeMasterStatus.setTerm(feeGenerationMasterVO.getTermId());
		feeMasterStatus.setFeeCategory(feeGenerationMasterVO.getFeeCategory());
		List<FeeMasterStatus> listFee = null;

		listFee = feeGenerationListDAO.getFeeStatusListForValidation(
				feeMasterStatus, termList);

		System.out.println("Fee status for term list size : " + listFee.size());
		for (int i = 0; i < listFee.size(); i++) {
			System.out.println("fee master existtttttttt  : "
					+ listFee.get(i).toString());
		}
		if ((listFee != null) && (listFee.size() != length)) {
			throw new FeeMasterNotFoundException();
		}
		for (int ls = 0; ls < listFee.size(); ls++) {
			if (!listFee.get(ls).getFeeStatus()
					.equals(ApplicationConstant.FEE_STATUS_ENTERED)) {
				throw new FeeMasterExistException();
			}
		}
		// Verify Students available for each term,if its not there,throw an
		// exception
		Integer stuAvai = 0;
		StudentMasterKey studentMasterKey = new StudentMasterKey();
		studentMasterKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		studentMasterKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		studentMasterKey.setAcademicYear(feeGenerationMasterVO.getAcTerm());

		for (int i = 0; i < length; i++) {
			String term = "";
			if (feeGenerationMasterVO.getTermId().contains(",")) {
				term = feeGenerationMasterVO.getTermId().split(",")[i];
			} else {
				term = feeGenerationMasterVO.getTermId();
			}
			stuAvai = stuAvai
					+ feeGenerationDAO.checkStudentsAvailable(studentMasterKey,
							feeGenerationMasterVO.getCourseMasterId(), term);
			logger.debug("No of students for generate fee record:" + stuAvai);
		}

		if (stuAvai != length) {
			throw new StudentNotFoundException();
		}

		// Check course variant is there
		FeeMasterKey feeMasterKey = new FeeMasterKey();
		feeMasterKey.setAcTerm(feeGenerationMasterVO.getAcTerm());
		feeMasterKey
				.setInstId(sessionCache.getUserSessionDetails().getInstId());
		feeMasterKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		feeMasterKey.setCourse(feeGenerationMasterVO.getCourseMasterId());
		feeMasterKey.setTerm(feeGenerationMasterVO.getTermId());
		FeeMasterListForFeeGen feeMasterListGen = feeGenerationDAO
				.checkCourseVariantExist(feeMasterKey);
		if (feeMasterListGen != null) {
			// throw exception
			System.out.println("Fee master not found for integrated groups");
			throw new FeeMasterNotFoundForIntegratedCourseException();

		}
		UUID idOne = UUID.randomUUID();
		threadId = idOne.toString();

		// Create thread to create student fee and student demand record
		Thread t = new Thread(new Runnable() {
			public void run() {
				Integer length = 1;
				if (feeGenerationMasterVO.getTermId().contains(",")) {
					length = feeGenerationMasterVO.getTermId().split(",").length;
				}

				// System.out.println("UUID One: " + idOne.toString());
				// Update fee Status

				System.out.println("term length size  : " + length);
				if (feeGenerationMasterVO.getTermId() != null) {
					List<String> termList1 = new ArrayList<String>();
					for (int i = 0; i < length; i++) {
						if (feeGenerationMasterVO.getTermId().contains(",")) {
							termList1.add(feeGenerationMasterVO.getTermId()
									.split(",")[i]);
						} else {
							termList1.add(feeGenerationMasterVO.getTermId());
						}
					}
					for (int ts = 0; ts < termList1.size(); ts++) {
						// ArrayList<String> threadError=new
						// ArrayList<String>();
						errorList = new ArrayList<String>();
						System.out.println("length values : " + ts);
						feeGenerationMasterVO.setTermId(termList1.get(ts));
						System.out.println("fee generation vo  : "
								+ feeGenerationMasterVO.toString());
						System.out.println("fee generation vo thread id : "
								+ threadId);
						updateFeeStatus(sessionCache, feeGenerationMasterVO,
								threadId);

						try {
							feeGeneration(applicationCache, sessionCache,
									feeGenerationMasterVO);
						} catch (PropertyNotFoundException e) {
							logger.error("property not found");
						}
					}
				}

			}
		});
		t.start();
		return threadId;

	}

	// Generate fee record method
	@Transactional(rollbackFor = Exception.class)
	private void feeGeneration(ApplicationCache applicationCache,
			SessionCache sessionCache,
			FeeGenerationMasterVO feeGenerationMasterVO)
			throws PropertyNotFoundException {
		FeeMasterKey feeMasterKey = new FeeMasterKey();
		commonBusiness.changeObject(feeMasterKey, feeGenerationMasterVO);
		feeMasterKey
				.setInstId(sessionCache.getUserSessionDetails().getInstId());
		feeMasterKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		feeMasterKey.setCourse(feeGenerationMasterVO.getCourseMasterId());

		feeMasterKey.setTerm(feeGenerationMasterVO.getTermId());

		List<FeeMasterListForFeeGen> feeMastrNonElectiveList = getNonElectiveFeeMasterList(feeMasterKey);

		List<FeeMasterListForFeeGen> feeMastrListElective = getElectiveFeeMasterList(feeMasterKey);

		List<FeeMasterListForFeeGen> feeMastrCourseVariantList = getCourseVariantFeeMasterList(feeMasterKey);

		List<StudentFee> transportlist = getTransportFee(feeMasterKey);

		List<StudentMasterForFeeGen> studentList = getStudentList(feeMasterKey);

		List<StudentFee> studentFees = getStudentFeeObjectList(
				applicationCache, studentList, feeMastrListElective,
				feeMastrNonElectiveList, feeMastrCourseVariantList,
				sessionCache, feeGenerationMasterVO);
		System.out.println("list of stu fee to be insert : "
				+ studentFees.size());
		if(transportlist!=null){
		for(StudentFee studentFee:transportlist) {
			studentFee.setDbTs(1);
			studentFee.setDelFlag("N");
			studentFee.setrCreId(sessionCache
					.getUserSessionDetails().getUserId());
			studentFee.setrModId(sessionCache
					.getUserSessionDetails().getUserId());
			studentFee.setElecticeSpec("");
			studentFee.setAcTerm(feeMasterKey.getAcTerm());
			studentFee.setInstId(sessionCache.getUserSessionDetails().getInstId());
			studentFee.setBranchId(sessionCache.getUserSessionDetails().getBranchId());
			studentFee.setFeeCategory(feeMasterKey.getFeeCategory());
			studentFee.setFeeTerm("");
			studentFee.setFeePaymentTerm("");
			studentFee.setFeeType(propertyManagementUtil.getPropertyValue(applicationCache, sessionCache.getUserSessionDetails().getInstId(),
					sessionCache.getUserSessionDetails().getBranchId(), PropertyManagementConstant.TRANSPORT_FEE_TYPE));
			studentFees.add(studentFee);
		}
		}
		System.out.println("list of stu fee to be insert later: "
				+ studentFees.size());
		// Insert the student fee record
		insertStudentFeesDetails(studentFees);

		System.out.println("After insertion of stff");

		// Get student fee list for student fee demand insert
		List<StudentFee> stuFeeListForStuDmdInsert = getStudentFeeListForStudentFeeDemand(
				sessionCache, feeGenerationMasterVO);

		// Insert Student fee demand list
		insertStudentFeeDemand(stuFeeListForStuDmdInsert, sessionCache,
				feeGenerationMasterVO);

		if (errorList != null) {
			System.out.println("error list size : " + errorList.size());
			for (int e = 0; e < errorList.size(); e++) {
				System.out.println("error list values :" + errorList.get(e));
			}

			if (errorList.size() == 0) {
				String thread = "";
				// Update fee Status as Generated
				updateFeeStatus(sessionCache, feeGenerationMasterVO, thread);
			}
		}
		// }

		System.out.println("finish methoddddd");
	}

	// Insert Student fee demand details
	@Transactional(rollbackFor = Exception.class)
	void insertStudentFeeDemand(List<StudentFee> stuFeeListForStuDmdInsert,
			SessionCache sessionCache,
			FeeGenerationMasterVO feeGenerationMasterVO) {


		// Insert the Student fee demand
		List<StudentFeeDemand> stuFeeDemandList = new ArrayList<StudentFeeDemand>();
		if(stuFeeListForStuDmdInsert!=null){
		for (int sd = 0; sd < stuFeeListForStuDmdInsert.size(); sd++) {
			StudentFeeDemand stuFeeDemand = new StudentFeeDemand();
			commonBusiness.changeObject(stuFeeDemand,
					stuFeeListForStuDmdInsert.get(sd));
			stuFeeDemand.setDbTs(1);
			stuFeeDemand.setInstId(sessionCache.getUserSessionDetails()
					.getInstId());
			stuFeeDemand.setBranchId(sessionCache.getUserSessionDetails()
					.getBranchId());
			stuFeeDemand.setrCreId(sessionCache.getUserSessionDetails()
					.getUserId());
			stuFeeDemand.setrModId(sessionCache.getUserSessionDetails()
					.getUserId());
			try {
				stuFeeDemand
						.setsFeeDmdSeqId(AlphaSequenceUtil
								.generateAlphaSequence(
										ApplicationConstant.STRING_STUD_FEE_DEMAND_SEQ,
										simpleIdGenerator
												.getNextId(
														SequenceConstant.STUDENT_FEE_DEMAND_SEQUENCE,
														1)));

			} catch (DatabaseException e) {
				errorList.add("Database exception");
			}
			stuFeeDemand.setDelFlag("N");
			stuFeeDemand.setConcessionAmt(0);
			stuFeeDemand.setFeeDueAmt(stuFeeListForStuDmdInsert.get(sd)
					.getFeeAmt());
			stuFeeDemand.setFeeDemandStatus(CommonCodeConstant.DEMAND_RAISED);
			// stuFeeDemand.setFeeDemandRemarks("ENTERED");
			stuFeeDemandList.add(stuFeeDemand);
		}
		}
		
		try {
			studentFeeDemandListDAO
					.insertStudentFeeDemandListRecs(stuFeeDemandList);
		} catch (DuplicateEntryException e) {
			errorList.add("Duplicate Exception");
		}
	}

	// Get Student fee list for Student fee demand insertion
	List<StudentFee> getStudentFeeListForStudentFeeDemand(
			SessionCache sessionCache,
			FeeGenerationMasterVO feeGenerationMasterVO) {
		StudentFeeKey studentFeeKey = new StudentFeeKey();
		commonBusiness.changeObject(studentFeeKey, feeGenerationMasterVO);
		studentFeeKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		studentFeeKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		List<StudentFee> stuFeeListForStuDmdInsert = null;
		try {
			stuFeeListForStuDmdInsert = studentFeeListDAO
					.selectStudentFeeListRecsForStuDmd(studentFeeKey,
							feeGenerationMasterVO.getCourseMasterId(),
							feeGenerationMasterVO.getTermId());
		} catch (NoDataFoundException e) {
			errorList.add("No data Exception");
		}
		return stuFeeListForStuDmdInsert;
	}

	// Get Non elective fee master details
	List<FeeMasterListForFeeGen> getNonElectiveFeeMasterList(
			FeeMasterKey feeMasterKey) {
		List<FeeMasterListForFeeGen> feeMastrList = null;
		try {
			feeMastrList = feeGenerationListDAO
					.getFeeMasterNonElectiveListForFeeGeneration(feeMasterKey);
		} catch (NoDataFoundException e) {
			logger.error("No non elective subjects found");

		}
		return feeMastrList;
	}

	// Get elective fee master details
	List<FeeMasterListForFeeGen> getElectiveFeeMasterList(
			FeeMasterKey feeMasterKey) {
		List<FeeMasterListForFeeGen> feeMastrList = null;
		try {
			feeMastrList = feeGenerationListDAO
					.getFeeMasterElectiveListForFeeGeneration(feeMasterKey);
		} catch (NoDataFoundException e) {
			logger.error("No elective subjects found");
		}
		return feeMastrList;
	}

	// Get Course Variant fee master details
	List<FeeMasterListForFeeGen> getCourseVariantFeeMasterList(
			FeeMasterKey feeMasterKey) {
		List<FeeMasterListForFeeGen> feeMastrList = null;
		try {
			feeMastrList = feeGenerationListDAO
					.getFeeMasterCourseVariantListForFeeGeneration(feeMasterKey);
		} catch (NoDataFoundException e) {
			logger.error("No Course variant subjects found");
		}
		return feeMastrList;
	}

	// Get Student List for Fee generation
	List<StudentMasterForFeeGen> getStudentList(FeeMasterKey feeMasterKey) {
		StudentMasterForFeeGenKey studentMasterForFeeGenKey = new StudentMasterForFeeGenKey();
		commonBusiness.changeObject(studentMasterForFeeGenKey, feeMasterKey);
		studentMasterForFeeGenKey.setStandard(feeMasterKey.getTerm());
		studentMasterForFeeGenKey.setAcademicYear(feeMasterKey.getAcTerm());
		// get the student details
		List<StudentMasterForFeeGen> studentList = null;
		try {
			studentList = feeGenerationListDAO
					.getStudentListForFeeGeneration(studentMasterForFeeGenKey);
		} catch (NoDataFoundException e) {
			logger.error("No Students found");
		}
		return studentList;
	}

	// Get Transport fee Fee generation
	List<StudentFee> getTransportFee(FeeMasterKey feeMasterKey) {

		List<StudentFee> studentList = null;
		try {
			studentList = feeGenerationListDAO
					.getTransportFeeForFeeGeneration(feeMasterKey);
		} catch (NoDataFoundException e) {
			logger.error("No Students found");
		}
		return studentList;
	}

	// Batch insert Student fees
	@Transactional(rollbackFor = Exception.class)
	void insertStudentFeesDetails(List<StudentFee> studentFees) {
		for (int count = 0; count < (studentFees.size()); count = count
				+ Integer
						.valueOf(BatchConstants.BATCH_SIZE_FOR_STUDENT_FEE_INSERT)) {
			Integer startIndex = count;
			Integer endInd = startIndex
					+ (Integer
							.valueOf(BatchConstants.BATCH_SIZE_FOR_STUDENT_FEE_INSERT));
			Integer endIndex = endInd > studentFees.size() ? studentFees.size()
					: endInd;
			List<StudentFee> splitStudentVal = studentFees.subList(startIndex,
					endIndex);
			try {
				studentFeeListDAO.insertStudentFeeListRecs(splitStudentVal);
			} catch (DuplicateEntryException e) {
				System.out.println("Duplicate exception");
				e.printStackTrace();
				errorList.add("Duplicate Exception");
			}

		}
	}

	// To form the Student Fee object
	List<StudentFee> getStudentFeeObjectList(ApplicationCache applicationCache,
			List<StudentMasterForFeeGen> studentList,
			List<FeeMasterListForFeeGen> feeMastrListElective,
			List<FeeMasterListForFeeGen> feeMastrNonElectiveList,
			List<FeeMasterListForFeeGen> feeMastrCourseVariantList,
			SessionCache sessionCache,
			FeeGenerationMasterVO feeGenerationMasterVO)
			throws PropertyNotFoundException {

		String admissionFeeType = propertyManagementUtil.getPropertyValue(
				applicationCache, sessionCache.getUserSessionDetails()
						.getInstId(), sessionCache.getUserSessionDetails()
						.getBranchId(),
				PropertyManagementConstant.ADMSN_FEE_TYPE);
		String nonAdmissionFeeType = propertyManagementUtil.getPropertyValue(
				applicationCache, sessionCache.getUserSessionDetails()
						.getInstId(), sessionCache.getUserSessionDetails()
						.getBranchId(),
				PropertyManagementConstant.NON_ADMSN_FEE_TYPE);

		List<StudentFee> studentFees = new ArrayList<StudentFee>();
if(studentList!=null){
		for (int s = 0; s < studentList.size(); s++) {
			
			
			if (feeMastrNonElectiveList != null) {
				String isNewAdmission = studentList.get(s).getIsNewAdmission();

				for (int sf = 0; sf < feeMastrNonElectiveList.size(); sf++) {
					StudentFee studentFeeFinal = new StudentFee();
					System.out.println("Is new Admission : " + isNewAdmission);
					System.out
							.println("Admission fee type:" + admissionFeeType);
					System.out.println("Non admission fee type:"
							+ nonAdmissionFeeType);
					System.out.println("feeMastrNonElectiveList fee type "
							+ feeMastrNonElectiveList.get(sf).getFeeType());
					if (((isNewAdmission.equals("Y")) && (!feeMastrNonElectiveList
							.get(sf).getFeeType().equals(nonAdmissionFeeType)))
							|| ((isNewAdmission.equals("N")) && (!feeMastrNonElectiveList
									.get(sf).getFeeType()
									.equals(admissionFeeType)))) {
						System.out.println("continue Is new Admission : "
								+ isNewAdmission);
						System.out.println("continue fee type : "
								+ admissionFeeType);

						commonBusiness.changeObject(studentFeeFinal,
								feeMastrNonElectiveList.get(sf));
						studentFeeFinal
								.setCourseVariant(ApplicationConstant.NOT_APPLICABLE);
						studentFeeFinal.setDelFlag("N");
						studentFeeFinal.setrCreId(sessionCache
								.getUserSessionDetails().getUserId());
						studentFeeFinal.setrModId(sessionCache
								.getUserSessionDetails().getUserId());
						studentFeeFinal.setElecticeSpec("");
						studentFeeFinal.setDbTs(1);
						studentFeeFinal.setInstId(sessionCache
								.getUserSessionDetails().getInstId());
						studentFeeFinal.setBranchId(sessionCache
								.getUserSessionDetails().getBranchId());
						studentFeeFinal.setAcTerm(feeGenerationMasterVO
								.getAcTerm());
						studentFeeFinal.setStudentAdmissNo(studentList.get(s)
								.getStudentAdmisNo());
						System.out.println("student fee final: "
								+ studentFeeFinal.toString());

						studentFees.add(studentFeeFinal);
					}
				}
			}
			System.out.println("student detailsss  : " + studentList.get(s));
			// For elective
			if (feeMastrListElective != null) {
				for (int sfe = 0; sfe < feeMastrListElective.size(); sfe++) {
					System.out.println("fee elective : "
							+ feeMastrListElective.get(sfe));
					if (((!studentList.get(s).getElective1().equals("")) && (feeMastrListElective
							.get(sfe).getSubjectId().equals(studentList.get(s)
							.getElective1())))
							|| (!studentList.get(s).getElective2().equals(""))
							&& (feeMastrListElective.get(sfe).getSubjectId()
									.equals(studentList.get(s).getElective2()))) {
						StudentFee studentFeeFinal = new StudentFee();
						commonBusiness.changeObject(studentFeeFinal,
								feeMastrListElective.get(sfe));
						studentFeeFinal
								.setCourseVariant(ApplicationConstant.NOT_APPLICABLE);
						studentFeeFinal.setDelFlag("N");
						studentFeeFinal.setrCreId(sessionCache
								.getUserSessionDetails().getUserId());
						studentFeeFinal.setrModId(sessionCache
								.getUserSessionDetails().getUserId());
						studentFeeFinal.setElecticeSpec("");
						studentFeeFinal.setDbTs(1);
						studentFeeFinal.setInstId(sessionCache
								.getUserSessionDetails().getInstId());
						studentFeeFinal.setBranchId(sessionCache
								.getUserSessionDetails().getBranchId());
						studentFeeFinal.setAcTerm(feeGenerationMasterVO
								.getAcTerm());
						studentFeeFinal.setStudentAdmissNo(studentList.get(s)
								.getStudentAdmisNo());
						if (feeMastrListElective.get(sfe).getSubjectId()
								.equals(studentList.get(s).getElective1())) {
							studentFeeFinal.setElecticeSpec(studentList.get(s)
									.getElective1());
						} else {
							studentFeeFinal.setElecticeSpec(studentList.get(s)
									.getElective2());
						}
						System.out.println("student fee final: "
								+ studentFeeFinal.toString());
						studentFees.add(studentFeeFinal);
					}
				}
			}
			// For Fee master course variant
			if (feeMastrCourseVariantList != null) {
				System.out.println("list variant size  : "
						+ feeMastrCourseVariantList.size());
				for (int sf = 0; sf < feeMastrCourseVariantList.size(); sf++) {
					String courseVar = studentList.get(s).getCourseVariant();
					System.out.println("Course student  : " + courseVar);
					System.out.println("Course fee master  : "
							+ feeMastrCourseVariantList.get(sf)
									.getCourseVariant());
					if ((courseVar != null)
							&& (courseVar != "")
							&& (!courseVar.equals(""))
							&& (feeMastrCourseVariantList.get(sf)
									.getCourseVariant().equals(courseVar))) {
						StudentFee studentFeeFinal = new StudentFee();
						commonBusiness.changeObject(studentFeeFinal,
								feeMastrCourseVariantList.get(sf));
						studentFeeFinal.setDelFlag("N");
						studentFeeFinal.setrCreId(sessionCache
								.getUserSessionDetails().getUserId());
						studentFeeFinal.setrModId(sessionCache
								.getUserSessionDetails().getUserId());
						studentFeeFinal.setElecticeSpec("");
						studentFeeFinal.setDbTs(1);
						studentFeeFinal.setInstId(sessionCache
								.getUserSessionDetails().getInstId());
						studentFeeFinal.setBranchId(sessionCache
								.getUserSessionDetails().getBranchId());
						studentFeeFinal.setAcTerm(feeGenerationMasterVO
								.getAcTerm());
						studentFeeFinal.setStudentAdmissNo(studentList.get(s)
								.getStudentAdmisNo());
						System.out.println("student fee final course variant: "
								+ studentFeeFinal.toString());
						studentFees.add(studentFeeFinal);
					}
				}
			}
		}
}
		return studentFees;

	}

	// Update the fee Status
	@Transactional(rollbackFor = Exception.class)
	void updateFeeStatus(SessionCache sessionCache,
			FeeGenerationMasterVO feeGenerationMasterVO, String threadId) {
		// Set the fee status as Lock
		FeeMasterStatusKey feeMasterStatusKey = new FeeMasterStatusKey();
		feeMasterStatusKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		feeMasterStatusKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		feeMasterStatusKey.setFeeCategory(feeGenerationMasterVO
				.getFeeCategory());
		feeMasterStatusKey.setAcTerm(feeGenerationMasterVO.getAcTerm());
		feeMasterStatusKey.setCourse(feeGenerationMasterVO.getCourseMasterId());
		feeMasterStatusKey.setTerm(feeGenerationMasterVO.getTermId());
		FeeMasterStatus feemasterStatus = null;
		try {
			feemasterStatus = feeStatusDao.selectFeeStatus(feeMasterStatusKey);
		} catch (NoDataFoundException e) {

			e.printStackTrace();
		}
		System.out.println("fee status in daoooooooooooooooo  : "
				+ feemasterStatus.toString());
		feeMasterStatusKey.setDbTs(feemasterStatus.getDbTs());
		feeMasterStatusKey.setCourse(feemasterStatus.getCourse());
		feeMasterStatusKey.setTerm(feemasterStatus.getTerm());
		feemasterStatus.setrModId(sessionCache.getUserSessionDetails()
				.getUserId());
		if ((threadId == "") || (threadId.equals(""))) {
			feemasterStatus
					.setFeeStatus(ApplicationConstant.FEE_STATUS_GENERATED);
		} else {
			feemasterStatus.setFeeGenerationStatus(threadId);
			feemasterStatus.setFeeStatus(ApplicationConstant.FEE_STATUS_LOCKED);
		}
		feemasterStatus.setDbTs(feemasterStatus.getDbTs() + 1);

		try {
			feeStatusDao.updateFeeStatus(feemasterStatus, feeMasterStatusKey);
		} catch (UpdateFailedException e) {

			e.printStackTrace();
		}
	}

	@Override
	public String checkFeeStatus(String threadId, SessionCache sessionCache)
			throws NoDataFoundException {
		String output = "";
		FeeMasterStatusKey feeMasterStatusKey = new FeeMasterStatusKey();
		feeMasterStatusKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		feeMasterStatusKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		feeMasterStatusKey.setFeeGenerationStatus(threadId);
		List<FeeMasterStatus> feeMastreStatu = feeMasterStatusListDAO
				.checkFeeStatus(feeMasterStatusKey);
		Integer result = 0;
		if (feeMastreStatu != null) {
			for (int i = 0; i < feeMastreStatu.size(); i++) {
				if (feeMastreStatu.get(i).getFeeStatus()
						.equals(ApplicationConstant.FEE_STATUS_GENERATED)) {
					result = result + 1;
				}
			}
			if (result == feeMastreStatu.size()) {
				output = ApplicationConstant.FEE_STATUS_GENERATED;
			}
		}
		return output;
	}

}
