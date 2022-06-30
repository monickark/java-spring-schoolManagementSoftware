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
import com.jaw.core.dao.ICourseMasterDAO;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.dao.IStudentMasterListDAO;
import com.jaw.student.admission.dao.StudentUpdateList;
import com.jaw.student.controller.StudentUpdateListVO;
import com.jaw.student.controller.StudentUpdateMasterVO;
@Service
public class StudentUpdatesService implements IStudentUpdatesService {
	// Logging
		Logger logger = Logger.getLogger(StudentUpdatesService.class);
	
	@Autowired
	IStudentMasterListDAO stuMasListDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IAllSubListByCIDAndTRM allSubListDao;
	@Autowired
	DoAudit doAudit;
	@Autowired
	ICourseMasterDAO courseMasterDAO;

	//Method to get the List for Update
	@Override	
	public  void getStuListForUpdates(
			UserSessionDetails sessionDetails,
			StudentUpdateMasterVO studentUpdateMasterVO) throws NoDataFoundException, InvalidCategoryForUpdateException {	
		
		Map<String, String> subList = new LinkedHashMap<String,String>();	
		List<StudentUpdateListVO> listOfMasterVOs = new ArrayList<StudentUpdateListVO>();
		studentUpdateMasterVO.getStudentUpSearch().setInstId(sessionDetails.getInstId());
		studentUpdateMasterVO.getStudentUpSearch().setBranchId(sessionDetails.getBranchId());
		String colNameForUpdate = null;		
		
		/*Based on the selected Category,fix column name for DB & Display tag		 
		 *& also code types if applicable*/
		
		STUUPDATECOL colName = STUUPDATECOL.valueOf(studentUpdateMasterVO.getStudentUpSearch().getStuUpdateCat());
		switch(colName){
		case CVAT:{
			//Validation for Course Varinat column is applicable for the given Student Group Id
			Boolean cv = courseMasterDAO.checkForCVForStuUpdate(studentUpdateMasterVO.getStudentUpSearch().getStuGrpId(), sessionDetails.getInstId(), sessionDetails.getBranchId());
			if(!cv){
				throw new InvalidCategoryForUpdateException();
			}
						
			colNameForUpdate = ApplicationConstant.COURSE_VARIANT;			
			studentUpdateMasterVO.setColumnNameForDisTag("Course Variant");		
			studentUpdateMasterVO.setCocdtype(CommonCodeConstant.COURSE_VARIANT);		
			break;
		}
			
		case ELE1:{
			colNameForUpdate = ApplicationConstant.ELE_1;
			studentUpdateMasterVO.setColumnNameForDisTag("Elective 1");
			studentUpdateMasterVO.setSubjectType((ApplicationConstant.SUB_TYPE_ELECTIVE1));			
			break;
		}
			
		case ELE2:{
			colNameForUpdate = ApplicationConstant.ELE_2;
			studentUpdateMasterVO.setColumnNameForDisTag("Elective 2");						
			studentUpdateMasterVO.setSubjectType(ApplicationConstant.SUB_TYPE_ELECTIVE2);
			break;
		}
			
		case HNAM:{
			colNameForUpdate = ApplicationConstant.HOU_NAM;
			studentUpdateMasterVO.setColumnNameForDisTag("House Name");		
			studentUpdateMasterVO.setCocdtype(CommonCodeConstant.HOUSE_NAME);			
			break;
		}
			
		case LBAT:{
			colNameForUpdate = ApplicationConstant.LAB_BAT;
			studentUpdateMasterVO.setColumnNameForDisTag("Lab Batch");				
			studentUpdateMasterVO.setCocdtype(CommonCodeConstant.LAB_BATCH);
			break;
		}
			
		case REGNO:{
			colNameForUpdate = ApplicationConstant.REG_NO;
			studentUpdateMasterVO.setColumnNameForDisTag("Reg No");			
			break;
		}
			
		case SBAT:{
			colNameForUpdate = ApplicationConstant.STU_BAT;
			studentUpdateMasterVO.setColumnNameForDisTag("Student Batch");	
			studentUpdateMasterVO.setCocdtype(CommonCodeConstant.STU_BATCH);
			break;
		}
			
		case SLAN:{
			colNameForUpdate = ApplicationConstant.SEC_LANG;
			studentUpdateMasterVO.setColumnNameForDisTag("Second Language");				
			studentUpdateMasterVO.setSubjectType(ApplicationConstant.SUB_TYPE_LANGUAGE2);
			break;
		}
			
		case SREL:{
			colNameForUpdate = ApplicationConstant.REL_SUB;
			studentUpdateMasterVO.setColumnNameForDisTag("Religious Subject");				
			studentUpdateMasterVO.setSubjectType(ApplicationConstant.SUB_TYPE_RELIGIOUS);
			break;
		}
			
		case TLAN:{
			colNameForUpdate = ApplicationConstant.THIRD_LANG;
			studentUpdateMasterVO.setColumnNameForDisTag("Third Language");					
			studentUpdateMasterVO.setSubjectType(ApplicationConstant.SUB_TYPE_LANGUAGE3);
			break;
		}										
		}	
		
		//validation for Subject column is applicable or not
		if(studentUpdateMasterVO.getSubjectType()!=""){
			try{
				subList = getSubList(studentUpdateMasterVO.getStudentUpSearch().getStuGrpId(),sessionDetails,studentUpdateMasterVO.getSubjectType());
				studentUpdateMasterVO.setSubListMap(subList);
			}catch(NoDataFoundException e){
				throw new InvalidCategoryForUpdateException();
				
			}
		}		
		
		//Get the current academic term if it is not given from user
		if((studentUpdateMasterVO.getStudentUpSearch().getAcademicYear()!=null)&&(studentUpdateMasterVO.getStudentUpSearch().getAcademicYear()!="")){
			studentUpdateMasterVO.getStudentUpSearch().setAcademicYear(sessionDetails.getCurrAcTerm());
		}
						
		studentUpdateMasterVO.setDbColName(colNameForUpdate);	
		List<Map<String,String>> stuMasList = stuMasListDao.getStuListForColumnUpdates(sessionDetails.getInstId(), sessionDetails.getBranchId(), 
				studentUpdateMasterVO.getStudentUpSearch().getAcademicYear(), studentUpdateMasterVO.getStudentUpSearch().getStuGrpId(), colNameForUpdate);

		//After fetching the List from Database convert it into UI list
		Integer rowId = 1;
		for(Map<String,String> stuObj:stuMasList){
			StudentUpdateListVO masterVO = new StudentUpdateListVO();
			masterVO.setDbTs(Integer.valueOf(stuObj.get("DB_TS")));
			masterVO.setStudentAdmisNo(stuObj.get("ADMIS_NO"));
			masterVO.setStudentName(stuObj.get("STU_NAME"));			
			masterVO.setColValue(stuObj.get(colNameForUpdate));		
			masterVO.setRowid(rowId);
			listOfMasterVOs.add(masterVO);
			rowId++;
		}
		studentUpdateMasterVO.setStuList(listOfMasterVOs);		
	}
	
	//Enum for Category
	public enum STUUPDATECOL {
		CVAT,ELE1,ELE2,HNAM,LBAT,REGNO,SBAT,SLAN,SREL,TLAN;
		}

	//Method to update the Student List
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void updatedStuList(
			UserSessionDetails sessionDetails,
			StudentUpdateMasterVO studentUpdateMasterVO) throws BatchUpdateFailedException, DuplicateEntryException, DatabaseException  {
		
		List<StudentUpdateListVO> listOfVOs = studentUpdateMasterVO.getStuList();	
		List<StudentUpdateList> stuUpdateList = new ArrayList<StudentUpdateList>();
		//Get the current academic term if it is not given from user
				if((studentUpdateMasterVO.getStudentUpSearch().getAcademicYear()!=null)&&(studentUpdateMasterVO.getStudentUpSearch().getAcademicYear()!="")){
					studentUpdateMasterVO.getStudentUpSearch().setAcademicYear(sessionDetails.getCurrAcTerm());
				}
		//Converting UI List into DB list
		for(StudentUpdateListVO listVO:listOfVOs){
			StudentUpdateList stuList = new StudentUpdateList();
			commonBusiness.changeObject(stuList, listVO);
			stuList.setInstId(sessionDetails.getInstId());
			stuList.setBranchId(sessionDetails.getBranchId());
			stuList.setrModId(sessionDetails.getUserId());	
			stuUpdateList.add(stuList);
		}			
		stuMasListDao.updateStuList(stuUpdateList, studentUpdateMasterVO.getDbColName());
		
		//Auditing - As it is Batch update Functional audit alone is applicable
		String auditRemarks = "Academic Year :"
				+ studentUpdateMasterVO.getStudentUpSearch().getAcademicYear() + "," + "Student Group :"
				+ studentUpdateMasterVO.getStudentUpSearch().getStuGrpId()+","+"Column to be Updated:"
				+studentUpdateMasterVO.getStudentUpSearch().getStuUpdateCat();

		doAudit.doFunctionalAudit(sessionDetails, AuditConstant.STU_UPDATE_SUCCESS, auditRemarks);
	
		logger.debug("Function Audit for Student Update has been done.");
	
		
				
	}
	
	//Helper Method to get the Sublject List from CRSM & SBJM table
	@Override
	public Map<String, String> getSubList(
			String stuGrpId,UserSessionDetails userSessionDetails,String subType) throws NoDataFoundException {				
		 Map<String, String> subList = null;		
			subList = allSubListDao.getSubList(userSessionDetails, stuGrpId,subType);						
		return subList;
	}

	
}



