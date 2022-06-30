package com.jaw.fee.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.BatchConstants;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FeeMasterExistException;
import com.jaw.common.exceptions.FeeMasterNotFoundException;
import com.jaw.common.exceptions.FeeMasterNotFoundForIntegratedCourseException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.StudentNotFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.fee.controller.FeeGenerationMasterVO;
import com.jaw.fee.dao.FeeMasterKey;
import com.jaw.fee.dao.FeeMasterListForFeeGen;
import com.jaw.fee.dao.FeeMasterKey;
import com.jaw.fee.dao.FeeStatusKey;
import com.jaw.fee.dao.IFeeGenerationDAO;
import com.jaw.fee.dao.IFeeGenerationListDAO;
import com.jaw.fee.dao.IFeeMasterListDao;
import com.jaw.fee.dao.IStudentFeeDemandListDAO;
import com.jaw.fee.dao.IStudentFeeListDAO;
import com.jaw.fee.dao.StudentFee;
import com.jaw.fee.dao.StudentFeeDemand;
import com.jaw.fee.dao.StudentFeeKey;
import com.jaw.fee.dao.StudentMasterForFeeGen;
import com.jaw.fee.dao.StudentMasterForFeeGenKey;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.student.admission.dao.StudentMaster;

@Service
public class FeeGenerationAdmissionHelper {
	// Logging
	Logger logger = Logger.getLogger(FeeGenerationAdmissionHelper.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	IFeeGenerationDAO feeGenerationDAO;
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

	ArrayList<String> errorList;

	public void feeGenerate(final FeeGenerationMasterVO feeGenerationMasterVO,
			final SessionCache sessionCache, StudentMaster studentMaster)
			throws FeeMasterNotFoundException, StudentNotFoundException,
			DuplicateEntryException, DatabaseException, UpdateFailedException,
			FeeMasterExistException, FeeMasterNotFoundForIntegratedCourseException {

		errorList = new ArrayList<String>();
		FeeStatusKey feeStatusKey = new FeeStatusKey();
		commonBusiness.changeObject(feeStatusKey, feeGenerationMasterVO);
		feeStatusKey
				.setInstId(sessionCache.getUserSessionDetails().getInstId());
		feeStatusKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		feeStatusKey.setFeeStatus(ApplicationConstant.FEE_STATUS_ENTERED);

		System.out.println("b4  term list");
		List<String> termList = new ArrayList<String>();
		termList.add(feeGenerationMasterVO.getTermId());
		System.out.println("b4 fee generation");
		
		FeeMasterKey feeMasterKey=new FeeMasterKey();
		feeMasterKey.setAcTerm(feeGenerationMasterVO.getAcTerm());
		feeMasterKey.setInstId(sessionCache.getUserSessionDetails().getInstId());
		feeMasterKey.setBranchId(sessionCache.getUserSessionDetails().getBranchId());
		feeMasterKey.setCourse(feeGenerationMasterVO.getCourseMasterId());
		feeMasterKey.setTerm(feeGenerationMasterVO.getTermId());
		feeMasterKey.setFeeCategory(feeGenerationMasterVO.getFeeCategory());
		FeeMasterListForFeeGen feeMasterListGen=feeGenerationDAO.checkCourseVariantExist(feeMasterKey);
		if(feeMasterListGen!=null){
			//throw exception
			System.out.println("throw exception");
			throw new FeeMasterNotFoundForIntegratedCourseException();
			
		}
		checkFeeStatus(feeMasterKey);
		feeGeneration(sessionCache, feeGenerationMasterVO, studentMaster);

	}
	
	private void checkFeeStatus(FeeMasterKey feeMasterKey) throws FeeMasterNotFoundException
			 {
		

		 try {
			feeMasterListDao.getFeeMasterList(feeMasterKey);
		} catch (NoDataFoundException e) {
			throw new FeeMasterNotFoundException();
		}
	}
	// Insert Student fee demand details
	void insertStudentFeeDemand(List<StudentFee> stuFeeListForStuDmdInsert,
			SessionCache sessionCache,
			FeeGenerationMasterVO feeGenerationMasterVO) {

		// Insert the Student fee demand
		List<StudentFeeDemand> stuFeeDemandList = new ArrayList<StudentFeeDemand>();
		System.out.println("stuFeeListForStuDmdInsert :"
				+ stuFeeListForStuDmdInsert);
		if (stuFeeListForStuDmdInsert != null) {
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
				stuFeeDemand
						.setFeeDemandStatus(CommonCodeConstant.DEMAND_RAISED);
				// stuFeeDemand.setFeeDemandRemarks("ENTERED");
				stuFeeDemandList.add(stuFeeDemand);
			}
			System.out.println("list of student demand list"
					+ stuFeeListForStuDmdInsert.size());
			System.out.println("list of student demand list"
					+ stuFeeDemandList.toString());
		}

		
		try {
			studentFeeDemandListDAO
					.insertStudentFeeDemandListRecs(stuFeeDemandList);
		} catch (DuplicateEntryException e) {
			System.out.println("DUplicate entry exception in sfdd insert");
			errorList.add("Duplicate Exception");
		}
	}

	// Generate fee record method
	@Transactional(rollbackFor = Exception.class)
	private void feeGeneration(SessionCache sessionCache,
			FeeGenerationMasterVO feeGenerationMasterVO,
			StudentMaster studentMaster) {
System.out.println("inside fee generation");
		FeeMasterKey feeMasterKey = new FeeMasterKey();
		commonBusiness.changeObject(feeMasterKey, feeGenerationMasterVO);
		feeMasterKey
				.setInstId(sessionCache.getUserSessionDetails().getInstId());
		feeMasterKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		feeMasterKey.setCourse(feeGenerationMasterVO.getCourseMasterId());

		feeMasterKey.setTerm(feeGenerationMasterVO.getTermId());
		System.out.println("inside fee feeMastrNonElectiveList");
		// get the fee master details for non elective
		List<FeeMasterListForFeeGen> feeMastrNonElectiveList = getNonElectiveFeeMasterList(feeMasterKey);
		System.out.println("inside fee feeMastrListElective");
		List<FeeMasterListForFeeGen> feeMastrCourseVariantList = getCourseVariantFeeMasterList(feeMasterKey);
		
		// get the fee master details for elective
		List<FeeMasterListForFeeGen> feeMastrListElective = getElectiveFeeMasterList(feeMasterKey);
		System.out.println("inside  studentList");
		// Create Student Fee Record
		List<StudentMasterForFeeGen> studentList = getStudentList(feeMasterKey,
				studentMaster);
		System.out.println("inside  getStudentFeeObjectList");
		// Form the student fee object list
		List<StudentFee> studentFees = getStudentFeeObjectList(studentList,
				feeMastrListElective, feeMastrNonElectiveList,feeMastrCourseVariantList, sessionCache,
				feeGenerationMasterVO);
		System.out.println("inside  insertStudentFeesDetails");
		// Insert the student fee record
		insertStudentFeesDetails(studentFees);
		System.out.println("inside  stuFeeListForStuDmdInsert");
		// Get student fee list for student fee demand insert
		List<StudentFee> stuFeeListForStuDmdInsert = getStudentFeeListForStudentFeeDemand(
				sessionCache, feeGenerationMasterVO, studentMaster);
		System.out.println("student fee demand list:"
				+ stuFeeListForStuDmdInsert);
		// Insert Student fee demand list
		insertStudentFeeDemand(stuFeeListForStuDmdInsert, sessionCache,
				feeGenerationMasterVO);

		if (errorList != null) {
			System.out.println("error list size : " + errorList.size());
			for (int e = 0; e < errorList.size(); e++) {
				System.out.println("error list values :" + errorList.get(e));
			}

		}
	}
	// Get Course Variant fee master details
		List<FeeMasterListForFeeGen> getCourseVariantFeeMasterList(
				FeeMasterKey feeMasterKey) {
			List<FeeMasterListForFeeGen> feeMastrList = null;
			try {
				feeMastrList = feeGenerationListDAO
						.getFeeMasterCourseVariantListForFeeGeneration(feeMasterKey);
			} catch (NoDataFoundException e) {
			}
			return feeMastrList;
		}

	// Get Student fee list for Student fee demand insertion
	List<StudentFee> getStudentFeeListForStudentFeeDemand(
			SessionCache sessionCache,
			FeeGenerationMasterVO feeGenerationMasterVO,
			StudentMaster studentMaster) {
		System.out.println("Fee generation master vo :"
				+ feeGenerationMasterVO.toString());
		StudentFeeKey studentFeeKey = new StudentFeeKey();
		commonBusiness.changeObject(studentFeeKey, feeGenerationMasterVO);
		studentFeeKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		studentFeeKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());

		studentFeeKey.setStudentAdmissNo(studentMaster.getStudentAdmisNo());
		List<StudentFee> stuFeeListForStuDmdInsert = null;
		try {
			stuFeeListForStuDmdInsert = studentFeeListDAO
					.selectStudentFeeListRecsForStuDmd(studentFeeKey,"","");
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
		}
		return feeMastrList;
	}

	// Get Student List for Fee generation
	List<StudentMasterForFeeGen> getStudentList(FeeMasterKey feeMasterKey,
			StudentMaster studentMaster) {
		StudentMasterForFeeGenKey studentMasterForFeeGenKey = new StudentMasterForFeeGenKey();
		commonBusiness.changeObject(studentMasterForFeeGenKey, feeMasterKey);
		studentMasterForFeeGenKey.setStandard(feeMasterKey.getTerm());
		studentMasterForFeeGenKey.setAcademicYear(feeMasterKey.getAcTerm());
		// get the student details
		List<StudentMasterForFeeGen> studentList = null;
		StudentMasterForFeeGen studentMasterForFeeGen = new StudentMasterForFeeGen();
		studentMasterForFeeGen.setElective1(studentMaster.getElective1());
		studentMasterForFeeGen.setStudentAdmisNo(studentMaster
				.getStudentAdmisNo());
		studentMasterForFeeGen.setElective2(studentMaster.getElective2());
		studentMasterForFeeGen.setStudentName(studentMaster.getStudentName());
		studentMasterForFeeGen.setRollno(studentMaster.getRollno());
		studentList = new ArrayList<StudentMasterForFeeGen>();
		studentList.add(studentMasterForFeeGen);

		return studentList;
	}

	// Batch insert Student fees
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
				errorList.add("Duplicate Exception");
			}

		}
	}
	// To form the Student Fee object
	List<StudentFee> getStudentFeeObjectList(
			List<StudentMasterForFeeGen> studentList,
			List<FeeMasterListForFeeGen> feeMastrListElective,
			List<FeeMasterListForFeeGen> feeMastrNonElectiveList,
			List<FeeMasterListForFeeGen> feeMastrCourseVariantList,
			SessionCache sessionCache,
			FeeGenerationMasterVO feeGenerationMasterVO) {
		List<StudentFee> studentFees = new ArrayList<StudentFee>();
		if (feeMastrNonElectiveList != null) {
			System.out.println("Fee Master Non Elective  : "
					+ feeMastrNonElectiveList.size());
		for (int ne = 0; ne < feeMastrNonElectiveList.size(); ne++) {
			System.out.println("non elective values : "
					+ feeMastrNonElectiveList.get(ne));
		}
		}
		if (feeMastrCourseVariantList != null) {
			System.out.println("course variant size  : "
					+ feeMastrCourseVariantList.size());
			for (int ne = 0; ne < feeMastrCourseVariantList.size(); ne++) {
				System.out.println("course variant values : "
						+ feeMastrCourseVariantList.get(ne));
			}
		}
		if (feeMastrListElective != null) {
			System.out.println("elective size  : "
					+ feeMastrListElective.size());
			for (int ne = 0; ne < feeMastrListElective.size(); ne++) {
				System.out.println("elective values : "
						+ feeMastrListElective.get(ne));
			}
		}
		if (studentList != null) {
			System.out.println("student list size  : " + studentList.size());
			for (int ne = 0; ne < studentList.size(); ne++) {
				System.out.println("student list values : "
						+ studentList.get(ne));
			}
		}

		for (int s = 0; s < studentList.size(); s++) {
			if (feeMastrNonElectiveList != null) {
				for (int sf = 0; sf < feeMastrNonElectiveList.size(); sf++) {
					StudentFee studentFeeFinal = new StudentFee();
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
					studentFeeFinal
							.setAcTerm(feeGenerationMasterVO.getAcTerm());
					studentFeeFinal.setStudentAdmissNo(studentList.get(s)
							.getStudentAdmisNo());
					System.out.println("student fee final: "
							+ studentFeeFinal.toString());
					studentFees.add(studentFeeFinal);
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
		return studentFees;

	}

}
