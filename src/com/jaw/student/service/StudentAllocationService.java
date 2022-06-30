package com.jaw.student.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.service.ICourseTermLinkListService;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.student.admission.controller.StudentMasterVO;
import com.jaw.student.admission.dao.IStudentMasterDao;
import com.jaw.student.admission.dao.IStudentMasterListDAO;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.admission.service.IAdmissionService;
import com.jaw.student.controller.BatchSectUpdate;
import com.jaw.student.controller.StudentAllocationVO;
import com.jaw.student.controller.StudentSearchVO;

@Service
public class StudentAllocationService implements IStudentAllocationService {
	Logger logger = Logger.getLogger(StudentAllocationService.class);
	
	@Autowired
	private IStudentMasterListDAO studentMasterListDAO;
	
	@Autowired
	private IStudentMasterDao studentMasterDao;
	
	@Autowired
	private ICourseTermLinkListService courseTermLinkListService;
	
	@Autowired
	private CommonBusiness commonBusiness;
	
	@Autowired
	DoAudit doAudit;
	
	@Autowired
	private IAdmissionService admissionService;
	
	@Override
	public List<StudentMaster> studentAllocList(
			StudentAllocationVO studentAllocationVO,UserSessionDetails userSessionDetails) throws NoDataFoundException {
		StudentSearchVO stuSearch = new StudentSearchVO();
		commonBusiness.changeObject(stuSearch, studentAllocationVO);
		stuSearch.setInstId(userSessionDetails.getInstId());
		stuSearch.setBranchId(userSessionDetails.getBranchId());
		List<StudentMaster> studentMasterList = studentMasterListDAO.retrieveStudentMasterList(stuSearch);
		
		/*List<StudentMaster> studentMasterList = studentMasterListDAO
				.retriveStudentMasterList();
		*/
		return studentMasterList;
	}
	
	@Override
	public void sectionAllocation(String[] studentRegNo, String section,UserSessionDetails userSessionDetails,String acy,String[] dbtsList,
			String[] sectionList,String course,String term) throws DuplicateEntryException, DatabaseException {
		List<BatchSectUpdate> batchup = new ArrayList<BatchSectUpdate>();
		for (int i = 0; i < studentRegNo.length; i++) {
			BatchSectUpdate setup = new BatchSectUpdate();		
			setup.setDbts(dbtsList[i]);		
			setup.setStuAdmisNo(studentRegNo[i]);
			setup.setSec(sectionList[i]);
			String stuGrpId = 	admissionService.getStudentGrpId(userSessionDetails, course, term, setup.getSec());		
			setup.setStuGrpId(stuGrpId);
		setup.setrModId(userSessionDetails.getUserId());
			batchup.add(setup);
		}
		 studentMasterListDAO.insertBatch(batchup,userSessionDetails.getInstId(),userSessionDetails.getBranchId(),acy); 
		 String auditRemarks = "Section Alloted For Academic Year :"+acy+", Course :"+course+", Term :"+term;
		 doAudit.doFunctionalAudit(userSessionDetails, AuditConstant.SEC_ALLOC_SUCCESS, auditRemarks);
	}
	
	@Override
	public List<StudentMaster> findStudent(StudentSearchVO studentSearchVO) throws NoDataFoundException {
		
		List<StudentMaster> studentMasterList = studentMasterListDAO
				.retriveStudentMasterList();
		return studentMasterList;
		
	}
	
	@Override
	public List<StudentMaster> retrieveStudent(String studentAdmisNo) throws NoDataFoundException {
		
		StudentMaster studentMaster = studentMasterDao
				.retriveStudentDetails(null);
		List<StudentMaster> list = new ArrayList<StudentMaster>();
		list.add(studentMaster);
		return list;
		
	}
	
	@Override
	public StudentMaster getStudentMaster(String studentAdmisNo,
			AdmissionDetailsVO admissionDetailsVO) throws NoDataFoundException {
		
		StudentMaster studentMaster = studentMasterDao
				.retriveStudentDetails(null);
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
	}

	@Override
	public Map<String,String> termList(String instid, String branchId, String courseId) {
		return courseTermLinkListService.getTrmIdForSectionAlloc(instid, branchId, courseId);
		
	}
	
	
	
	
}
