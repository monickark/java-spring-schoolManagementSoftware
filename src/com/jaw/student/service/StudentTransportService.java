package com.jaw.student.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.dropdown.dao.IAllSubListByCIDAndTRM;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InvalidCategoryForUpdateException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.core.dao.ICourseMasterDAO;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.dao.IStudentMasterListDAO;
import com.jaw.student.admission.dao.IStudentTransportListDAO;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.admission.dao.StudentTransportList;
import com.jaw.student.controller.StudentSearchVO;
import com.jaw.student.controller.StudentTransportListVO;
import com.jaw.student.controller.StudentTransportMasterVO;

@Service
public class StudentTransportService implements IStudentTransportService {
	// Logging
	Logger logger = Logger.getLogger(StudentTransportService.class);

	@Autowired
	IStudentMasterListDAO stuMasListDao;
	@Autowired
	IStudentTransportListDAO studentTransportListDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DoAudit doAudit;
	@Autowired
	ICourseMasterDAO courseMasterDAO;

	// Method to get the List for Transport
	@Override
	public void getStuListForTransport(UserSessionDetails sessionDetails,
			StudentTransportMasterVO StudentTransportMasterVO)
			throws NoDataFoundException, InvalidCategoryForUpdateException {
		StudentSearchVO searchVO = new StudentSearchVO();
		List<StudentTransportListVO> listOfMasterVOs = new ArrayList<StudentTransportListVO>();
		searchVO.setInstId(sessionDetails.getInstId());
		searchVO.setBranchId(sessionDetails.getBranchId());
		searchVO.setAcademicYear(StudentTransportMasterVO
				.getStudentTransportSearch().getAcademicYear());
		searchVO.setStuGrpId(StudentTransportMasterVO
				.getStudentTransportSearch().getStuGrpId());
		List<StudentMaster> stuMasList = stuMasListDao
				.retrieveStudentMasterList(searchVO);
		int rowId = 0;
		for (StudentMaster stuObj : stuMasList) {
			StudentTransportListVO transportVO = new StudentTransportListVO();
			transportVO.setStudentAdmisNo(stuObj.getStudentAdmisNo());
			transportVO.setStudentName(stuObj.getStudentName());
			listOfMasterVOs.add(transportVO);
			transportVO.setRowid(rowId);
			rowId++;
		}
		StudentTransportMasterVO.setStuList(listOfMasterVOs);
	}

	// Method to Transport the Student List
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertStuList(UserSessionDetails sessionDetails,
			StudentTransportMasterVO StudentTransportMasterVO)
			throws BatchUpdateFailedException, DuplicateEntryException,
			DatabaseException, DataIntegrityException, RuntimeExceptionForBatch {

		List<StudentTransportListVO> listOfVOs = StudentTransportMasterVO
				.getStuList();
		List<StudentTransportList> stuTransportList = new ArrayList<StudentTransportList>();
		// Get the current academic term if it is not given from user
		if ((StudentTransportMasterVO.getStudentTransportSearch()
				.getAcademicYear() != null)
				&& (StudentTransportMasterVO.getStudentTransportSearch()
						.getAcademicYear() != "")) {
			StudentTransportMasterVO.getStudentTransportSearch()
					.setAcademicYear(sessionDetails.getCurrAcTerm());
		}
		// Converting UI List into DB list
		for (StudentTransportListVO listVO : listOfVOs) {
			StudentTransportList stuList = new StudentTransportList();
			commonBusiness.changeObject(stuList, listVO);
			stuList.setInstId(sessionDetails.getInstId());
			stuList.setBranchId(sessionDetails.getBranchId());
			stuList.setrModId(sessionDetails.getUserId());
			stuList.setAcademicYear(sessionDetails.getCurrAcTerm());
			stuTransportList.add(stuList);
		}
		System.out.println("before insert Transport");
		studentTransportListDAO.insertStudentTransportList(stuTransportList);

		// Auditing - As it is Batch Transport Functional audit alone is
		// applicable
		String auditRemarks = "Academic Year :"
				+ StudentTransportMasterVO.getStudentTransportSearch()
						.getAcademicYear()
				+ ","
				+ "Student Group :"
				+ StudentTransportMasterVO.getStudentTransportSearch()
						.getStuGrpId() + "," + "Column to be Transportd:";

		doAudit.doFunctionalAudit(sessionDetails,
				AuditConstant.STU_TRANSPORT_ADD, auditRemarks);

		logger.debug("Function Audit for Student Transport has been done.");

	}

}
