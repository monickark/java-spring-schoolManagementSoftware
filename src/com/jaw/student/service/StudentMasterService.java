package com.jaw.student.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.service.FileMasterHelper;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.core.dao.IStudentGroupMasterDAO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.dao.StudentGroupMasterKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.student.admission.dao.IStudentMasterDao;
import com.jaw.student.admission.dao.IStudentMasterListDAO;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.admission.dao.StudentMasterKey;
import com.jaw.student.controller.StudentMasterListVO;
import com.jaw.student.controller.StudentSearchVO;

@Service
public class StudentMasterService implements IStudentMasterService {
	Logger logger = Logger.getLogger(StudentMasterService.class);
	
	@Autowired
	private IStudentMasterListDAO studentMasterListDAO;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	private IStudentMasterDao studentMasterDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IStudentGroupMasterDAO studentGrpMasterDao;
	@Autowired
	FileMasterHelper fileMasHelper;
	
	
	@Override
	public List<StudentMasterListVO> findStudent(StudentSearchVO studentSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		
		List<StudentMasterListVO> stuMasList = new ArrayList<StudentMasterListVO>();
		
		studentSearchVO.setInstId(userSessionDetails.getInstId());
		studentSearchVO.setBranchId(userSessionDetails.getBranchId());	
		List<StudentMaster> studentMasterList = studentMasterListDAO
				.retrieveStudentMasterList(studentSearchVO);
		/*System.out.println("Student List"+studentMasterList);*/
		
		int rowId = 1;
		for(StudentMaster studentMaster:studentMasterList){
			
			StudentMasterListVO stuMasListVO = new StudentMasterListVO();
			commonBusiness.changeObject(stuMasListVO, studentMaster);
			stuMasListVO.setRowid(rowId);
			stuMasList.add(stuMasListVO);
			rowId++;
		}
		
		return stuMasList;
		
	}
	
	@Override
	public List<StudentMasterListVO> retrieveStudent(String studentAdmisNo,
			UserSessionDetails userSessionDetails, ApplicationCache applicationCache)
			throws PropertyNotFoundException, NoDataFoundException {
		
		StudentMasterKey studentMasterKey = new StudentMasterKey();
		studentMasterKey.setInstId(userSessionDetails.getInstId());
		studentMasterKey.setBranchId(userSessionDetails.getBranchId());
		studentMasterKey.setStudentAdmisNo(studentAdmisNo);
		studentMasterKey.setAcademicYear(userSessionDetails.getCurrAcTerm());
		StudentMaster studentMaster = studentMasterDao
				.retriveStudentDetails(studentMasterKey);
		List<StudentMasterListVO> list = new ArrayList<StudentMasterListVO>();
		int rowId = 1;
		if (studentMaster.getStudentAdmisNo() != null
				&& studentMaster.getStudentAdmisNo() != "") {		
			StudentMasterListVO listVO = new StudentMasterListVO();
			
			commonBusiness.changeObject(listVO, studentMaster);
			listVO.setRowid(rowId);
			list.add(listVO);
		}
		return list;
		
	}

	@Override
	public void editFiles(ApplicationCache applicationCache,
			AdmissionDetailsVO admisVo, UserSessionDetails sessionDetails,ServletContext servletContext) throws DatabaseException, DuplicateEntryException, IllegalStateException, FileNotSaveException {
		
		fileMasHelper.fileUpload(applicationCache, admisVo,
				sessionDetails,ApplicationConstant.PG_STUDENT,servletContext);
		}
	
	/*@Override
	public StudentMaster getStudentMaster(String studentAdmisNo,
			AdmissionDetailsVO admissionDetailsVO,
			UserSessionDetails userSessionDetails, ApplicationCache applicationCache)
			throws PropertyNotFoundException, NoDataFoundException {
		
		StudentMasterKey studentMasterKey = new StudentMasterKey();
		studentMasterKey.setInstId(userSessionDetails.getInstId());
		studentMasterKey.setBranchId(userSessionDetails.getBranchId());
		studentMasterKey.setStudentAdmisNo(studentAdmisNo);
		studentMasterKey.setAcademicYear(propertyManagementUtil.getPropertyValue(applicationCache,
				userSessionDetails.getInstId(), userSessionDetails.getBranchId(),
				PropertyManagementConstant.CURRENT_ACADEMIC_YEAR));
		StudentMaster studentMaster = studentMasterDao
				.retriveStudentDetails(studentMasterKey);
		StudentMasterVO studentMasterVO = new StudentMasterVO();
		
		try {
			BeanUtils.copyProperties(studentMasterVO, studentMaster);
			BeanUtils.copyProperties(admissionDetailsVO, studentMaster);
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
			
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		admissionDetailsVO.setStudentMasterVO(studentMasterVO);
		
		return studentMaster;
	}*/
	
}
