package com.jaw.analytics.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jaw.attendance.controller.ViewAttendance;
import com.jaw.attendance.dao.IStudentAttendanceListDAO;
import com.jaw.attendance.dao.IViewAttendanceDAO;
import com.jaw.attendance.dao.IViewAttendanceListDAO;
import com.jaw.attendance.dao.StudentAttendanceList;
import com.jaw.attendance.dao.StudentAttendanceListKey;
import com.jaw.attendance.dao.ViewAttendanceKey;
import com.jaw.attendance.service.IViewAttendanceService;
import com.jaw.attendance.service.ViewAttendanceService;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.AttendanceUtil;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
@Service
public class AnalyticAttendanceService implements IAnalyticAttendanceService {
	// Logging
		Logger logger = Logger.getLogger(ViewAttendanceService.class);
		@Autowired
		IStudentAttendanceListDAO studentAttendanceListDAO;
		@Autowired
		CommonBusiness commonBusiness;
		@Autowired
		AttendanceUtil attendanceUtil;
		@Autowired
		IViewAttendanceListDAO ViewAttendanceListDAO;
		@Autowired
		IViewAttendanceDAO viewAttendanceDAO;
		@Autowired
		DateUtil dateUtil;
		@Autowired
		CommonCodeUtil commonCodeUtil;
		@Autowired
		PropertyManagementUtil propertyManagementUtil;
		@Autowired
		@Qualifier(value = "simpleIdGenerator")
		private IIdGeneratorService simpleIdGenerator;
		@Override
		public List<ViewAttendance> selectConsolidateAttendance(
				ViewAttendanceKey viewAttendanceKey)
				throws NoDataFoundException {
			
			return viewAttendanceDAO.getConsolidateAttendance(viewAttendanceKey);
		}
		@Override
		public Set<ViewAttendance> selectSubjecteAttendance(
				ViewAttendanceKey viewAttendanceKey,
				UserSessionDetails userSessionDetails,
				ApplicationCache applicationCache) throws NoDataFoundException,
				PropertyNotFoundException {
			
			/*  String labInclude=propertyManagementUtil.getPropertyValue(
			  applicationCache, userSessionDetails.getInstId(),
			  userSessionDetails.getBranchId(),
			  PropertyManagementConstant.INC_LAB_ATT).toString(); String
			  showLabInclude=propertyManagementUtil.getPropertyValue(
			 applicationCache, userSessionDetails.getInstId(),
			  userSessionDetails.getBranchId(),
			 PropertyManagementConstant.SHOW_LAB_ATT_SEP).toString();*/
			 
			String labInclude = "Y";
			String showLabInclude = "Y";
			List<ViewAttendance> stuAtts=null;
			System.out.println("labbbbbbbbbbbbbb "+labInclude);
			System.out.println("labbbbbbbbbbbbbb "+showLabInclude);
			if (labInclude == "N") {
				viewAttendanceKey.setClassType(ApplicationConstant.CLASS_TYPE_THEORY);
				stuAtts=viewAttendanceDAO.subjectAttendanceWithCheckLab(viewAttendanceKey);
			} else if (showLabInclude == "Y") {
				System.out.println("secondddddddddddd");
				stuAtts=viewAttendanceDAO.subjectAttendanceWithLabSeperatly(viewAttendanceKey);			
			} else {
				viewAttendanceKey.setClassType("");
				stuAtts=viewAttendanceDAO.subjectAttendanceWithCheckLab(viewAttendanceKey);
			}	
			return getViewAttendanceObjects(stuAtts);
		}
		
		Set<ViewAttendance> getViewAttendanceObjects(List<ViewAttendance> stuAtts){
			Set<ViewAttendance> setView = new HashSet<ViewAttendance>();
			List<ViewAttendance> duplicate=new ArrayList<ViewAttendance>();
			for (ViewAttendance vieAtt:stuAtts) {
			  if (setView.contains(vieAtt) ){
				  duplicate.add(vieAtt);   // some magic handler
			  } else {
				  setView.add(vieAtt);
			  }
			}
			Iterator<ViewAttendance> itr = setView.iterator();
			while (itr.hasNext()) {
				ViewAttendance element = itr.next();
				System.out.println("set values  before : "+element.toString());
				for (ViewAttendance vieAtt:duplicate) {
					if(element.equals(vieAtt)){
						if(vieAtt.getNoOfAbsent()!=0){
							element.setNoOfAbsent(vieAtt.getNoOfAbsent());
						}else if(vieAtt.getNoOfPresent()!=0){
							element.setNoOfPresent(vieAtt.getNoOfPresent());
						}
					}
				}
				if(element.getClsType().equals(ApplicationConstant.CLASS_TYPE_THEORY)){
					element.setSubject(element.getSubject() +"Theory");
				}else if(element.getClsType().equals(ApplicationConstant.CLASS_TYPE_PRACTICAL)){
					element.setSubject(element.getSubject() +"Lab");
				}
				System.out.println("set values   : "+element.toString());
			}
			System.out.println("list size   : "+stuAtts.size());
			System.out.println("set size   : "+setView.size());
			System.out.println("duplicate size   : "+duplicate.size());
			return setView;
		}
		@Override
		public Map<String, String> selectStudentListStaffAnalytic(
				UserSessionDetails userSessionDetails, String studentGrp,
				String crslId,String studentBatch) throws NoDataFoundException {
			Map<String, String> studentListMap=new LinkedHashMap<String, String>();
			StudentAttendanceListKey studentAttendanceListKey=new StudentAttendanceListKey();
			studentAttendanceListKey.setInstId(userSessionDetails.getInstId());
			studentAttendanceListKey.setBranchId(userSessionDetails.getBranchId());
			studentAttendanceListKey.setAcTerm(userSessionDetails.getCurrAcTerm());
			studentAttendanceListKey.setStudentGroupId(studentGrp);
			studentAttendanceListKey.setClassType(ApplicationConstant.CLASS_TYPE_THEORY);
			studentAttendanceListKey.setStudentBatch(studentBatch);
			String[] subjects=crslId.split("-");
			studentAttendanceListKey.setCrslId(subjects[0]);
			studentAttendanceListKey.setSubType(subjects[1]);
			List<StudentAttendanceList> studentList=studentAttendanceListDAO.getStudentsForAttendance(studentAttendanceListKey);
			for(int i=0;i<studentList.size();i++){
				studentListMap.put(studentList.get(i).getStudentAdmisNo(), studentList.get(i).getStudentName());
			}
			return studentListMap;
		}
}
