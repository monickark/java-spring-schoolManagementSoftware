package com.jaw.student.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.constants.AuditConstant;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.SectionNotAllocatedException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.dao.IAuditDao;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.dao.IStudentMasterListDAO;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.controller.StudentRollSearchVO;

@Service
public class StudentRollGenService implements IStudentRollGenService {
	Logger logger = Logger.getLogger(StudentRollGenService.class);

	@Autowired
	IStudentMasterListDAO studentMasterList;
	@Autowired
	CommonCodeUtil commonCodeUtil;
	// Helper class to do Auditing
	@Autowired
	DoAudit doAudit;
	@Autowired
	IAuditDao auditDao;

	/*
	 * Retrieve StudentList From student Master And Generate RollNo
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<StudentMaster> getStudentList(
			ApplicationCache applicationCache,
			StudentRollSearchVO studentRollSearchVO,
			UserSessionDetails userSessionDetails) throws DatabaseException,
			DuplicateEntryException, NoDataFoundException, BatchUpdateFailedException, SectionNotAllocatedException  {	
		studentRollSearchVO.setInstId(userSessionDetails.getInstId());
		studentRollSearchVO.setBranchId(userSessionDetails.getBranchId());

		List<StudentMaster> studentlist = studentMasterList
				.retrieveStudentMasterListForRollNoList(studentRollSearchVO);		
		int i = 1;
		for (StudentMaster studentmaster : studentlist) {			
			studentmaster.setInstId(userSessionDetails.getInstId());
			studentmaster.setBranchId(userSessionDetails.getBranchId());
			studentmaster.setDbTs(studentmaster.getDbTs() + 1);
			studentmaster.setRollno(i++);
			studentmaster.setRowid(i - 1);
			studentmaster.setrModId(userSessionDetails.getUserId());
		}		
		studentMasterList.updateRollNo(studentlist);

		String auditRemarks = "Academic Year :"
				+ studentRollSearchVO.getAcademicYear() + "," + "Student Group :"
				+ studentRollSearchVO.getStudentGrpId();

		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.ROLL_NO_UPDATED, auditRemarks);
		
		List<StudentMaster> studentlistNew = studentMasterList
				.retrieveStudentMasterListForRollNoList(studentRollSearchVO);
				
		return studentlistNew;

	}

	@Override
	public Integer checkForRollNoGeneration(UserSessionDetails userSessionDetails,String acTrm,String sGrpId) {
		Integer rollNoNullCount = 0;
		StudentRollSearchVO studentRollSearchVO = new StudentRollSearchVO();
		studentRollSearchVO.setAcademicYear(acTrm);
		studentRollSearchVO.setStudentGrpId(sGrpId);
		studentRollSearchVO.setInstId(userSessionDetails.getInstId());
		studentRollSearchVO.setBranchId(userSessionDetails.getBranchId());
		List<StudentMaster> studentlist = null;
		try {
			 studentlist = studentMasterList
					.retrieveStudentMasterListForRollNoList(studentRollSearchVO);
		} catch (SectionNotAllocatedException e) {
			rollNoNullCount = 0;
			e.printStackTrace();
		}
		
		for (StudentMaster studentmaster : studentlist) {			
			if((studentmaster.getRollno()!=0)&&(studentmaster.getRollno()!=null)){				
				rollNoNullCount = rollNoNullCount+1;
			}else{
				rollNoNullCount=0;
			}
		}	
		return rollNoNullCount;
		
	}
	
}
