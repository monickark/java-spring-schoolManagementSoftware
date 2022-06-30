package com.jaw.student.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.DuplicatesInList;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.dao.IStudentDetainListDao;
import com.jaw.student.admission.dao.IStudentDetainedDAO;
import com.jaw.student.admission.dao.IStudentMasterListDAO;
import com.jaw.student.admission.dao.StudentDetain;
import com.jaw.student.admission.dao.StudentDetainKey;
import com.jaw.student.controller.StudentDetainListVO;
import com.jaw.student.controller.StudentDetainVO;

@Service
public class StudentDetainService implements IStudentDetainService {
	
	Logger logger = Logger.getLogger(StudentDetainService.class);
	
	@Autowired
	IStudentMasterListDAO studentMasterListDAO;
	
	@Autowired
	IStudentDetainListDao studentDetainListDao;
	
	@Autowired
	IStudentDetainedDAO studentDetainedDAO;
	
	@Autowired
	CommonBusiness commonBusiness;
	
	@Autowired
	DoAudit doAudit;

	@Override
	public Map<String, String> getStuList(String stuGrpId, String acterm,UserSessionDetails userSessionDetails) throws NoDataFoundException {
		return studentMasterListDAO.getStuListForDetain(stuGrpId, acterm,userSessionDetails.getInstId(),userSessionDetails.getBranchId());		
	}


	@Override
	public List<StudentDetainListVO> getDetainedStudents(String acyYear,String stuGrpId) throws NoDataFoundException {
		
		 List<StudentDetainListVO> listOfStuDetainListVO = new ArrayList<StudentDetainListVO>(); 
		 List<StudentDetain> listOfStuDEtain = studentDetainListDao.getDetainedStuList(acyYear,stuGrpId);
		 Integer rowId = 1;
		 for(StudentDetain stuDetain : listOfStuDEtain){
			 StudentDetainListVO studentDetainListVO = new StudentDetainListVO();
			 commonBusiness.changeObject(studentDetainListVO, stuDetain);
			 studentDetainListVO.setRowid(rowId);
			 studentDetainListVO.setOldRec("Y");
			 studentDetainListVO.setAcademicYear(acyYear);
			 listOfStuDetainListVO.add(studentDetainListVO);
			 rowId++;
		 }
		
		return listOfStuDetainListVO;
	}


	@Override
	public void updateStudentDetainRec(ApplicationCache applicationcache,
			UserSessionDetails userSessionDetails, 
			StudentDetainListVO studentDetainListVO) throws NoDataFoundException, UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		
		StudentDetainKey stuDetainKey = new StudentDetainKey();
		stuDetainKey.setInstId(userSessionDetails.getInstId());
		stuDetainKey.setBranchId(userSessionDetails.getBranchId());
		stuDetainKey.setAcademicYear(studentDetainListVO.getAcademicYear());
		stuDetainKey.setStuAdmisNo(studentDetainListVO.getStuAdmisNo());		
		StudentDetain stuDetain = studentDetainedDAO.getStuDetainRec(stuDetainKey);
		stuDetain.setDetainRemarks(studentDetainListVO.getDetainRemarks());
		stuDetain.setrModId(userSessionDetails.getUserId());
		stuDetainKey.setDbTs(stuDetain.getDbTs());
		
		studentDetainedDAO.updateStudentDetainedRec(stuDetain, stuDetainKey);
	/*	
		StudentDetain newStuDEtainRec = studentDetainedDAO.getStuDetainRec(stuDetainKey);
		StudentDetainListVO studentDetainListVO = new StudentDetainListVO();
		commonBusiness.changeObject(studentDetainListVO, newStuDEtainRec);*/
		
		//Auditing
		// functional audit
				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.UPDATE_STU_DETAIN_SUCCESS, stuDetain.getReasonForUpdating());
				logger.debug("Completed Functional Auditing");

				// Database audit
				String oldRecString = stuDetain
						.toStringForAuditStuDetainRecord();
				StudentDetain selectNewRecord = studentDetainedDAO.getStuDetainRec(stuDetainKey);
				String newRecString = selectNewRecord
						.toStringForAuditStuDetainRecord();
				doAudit.doDatabaseAudit(applicationcache, userSessionDetails,
						TableNameConstant.STUDENT_DETAIN_DETAILS,
						stuDetainKey.toStringForStuDetainKey(),
						oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
						"");

				logger.debug("Completed Database Auditing");
			
	}


	@Override
	public void deleteStudentDetainRec(ApplicationCache applicationCache,UserSessionDetails userSessionDetails,
			String admisNo, String acYear) throws NoDataFoundException,
			UpdateFailedException, DeleteFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		StudentDetainKey stuDetainKey = new StudentDetainKey();
		stuDetainKey.setInstId(userSessionDetails.getInstId());
		stuDetainKey.setBranchId(userSessionDetails.getBranchId());
		stuDetainKey.setAcademicYear(acYear);
		stuDetainKey.setStuAdmisNo(admisNo);
		StudentDetain stuDetain = studentDetainedDAO.getStuDetainRec(stuDetainKey);
		stuDetainKey.setDbTs(stuDetain.getDbTs());
		stuDetain.setrModId(userSessionDetails.getUserId());
		studentDetainedDAO.deleteStudentDetainedRec(stuDetainKey, stuDetain);
		
		//Auditing
				// functional audit
						doAudit.doFunctionalAudit(userSessionDetails,
								AuditConstant.DELETE_STU_DETAIN_SUCCESS, stuDetain.getReasonForUpdating());
						logger.debug("Completed Functional Auditing");

						// Database audit
						String oldRecString = stuDetain
								.toStringForAuditStuDetainRecord();
						
						doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
								TableNameConstant.STUDENT_DETAIN_DETAILS,
								stuDetainKey.toStringForStuDetainKey(),
								oldRecString, AuditConstant.TYPE_OF_OPER_DELETE, "",
							"");

						logger.debug("Completed Database Auditing");
	}


	@Override
	public void insertDetainedStudents(UserSessionDetails userSessionDetails , List<StudentDetainListVO> listOfStuDetain)
			throws NoDataFoundException, DataIntegrityException, RuntimeExceptionForBatch, DuplicatesInList, DuplicateEntryException, DatabaseException {
		List<StudentDetain> stuDetainList = new ArrayList<StudentDetain>();
		List<String> listOfStuAdmisNoForDuplicateCheck = new ArrayList<String>();
		for(StudentDetainListVO studentDetainListVO:listOfStuDetain){
			StudentDetain stuDetain = new StudentDetain();
			commonBusiness.changeObject(stuDetain, studentDetainListVO);
			stuDetain.setDbTs("1");
			stuDetain.setInstId(userSessionDetails.getInstId());
			stuDetain.setBranchId(userSessionDetails.getBranchId());
			stuDetain.setrCreId(userSessionDetails.getUserId());
			listOfStuAdmisNoForDuplicateCheck.add(stuDetain.getStuAdmisNo());
			stuDetainList.add(stuDetain);
		}
		
		
		//VALIDATION FOR RECORD DUPLICATION
		//STEP 1 : CHECK WITHIN THE LIST
		Set<String> setForDuplicateCheck = new HashSet<String>(); 
		for(StudentDetain studentDetain:stuDetainList){
			if(!setForDuplicateCheck.add(studentDetain.getStuAdmisNo())){
				throw new DuplicatesInList();
			}
		}
		
		//STEP 2 : CHECK WITH THE DATABASE
	List<String> listOfExistingStu =	studentDetainListDao.retriveListOfRecForDuplicateCheck(stuDetainList.get(0).getAcademicYear(), 
				listOfStuAdmisNoForDuplicateCheck, userSessionDetails.getInstId(), 
				userSessionDetails.getBranchId());
		if((listOfExistingStu.size()!=0)){
			throw new DuplicatesInList();
			}
		
		
		studentDetainListDao.insertStudentMasterList(stuDetainList);		
		
		//Auditing
		// functional audit
				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.BATCH_INSERT_STU_DETAIN_SUCCESS, "");
				logger.debug("Completed Functional Auditing");
				
	}
	
	
	

}
