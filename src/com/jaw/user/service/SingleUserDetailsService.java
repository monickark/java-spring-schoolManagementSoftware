package com.jaw.user.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.ProfileGroup;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.login.controller.LoginController;
import com.jaw.mgmtUser.controller.MgmtUserVO;
import com.jaw.mgmtUser.dao.IMgmtUserDetailsDAO;
import com.jaw.mgmtUser.dao.MgmtUser;
import com.jaw.nonStaff.dao.INonStaffDetailsDAO;
import com.jaw.nonStaff.dao.NonStaff;
import com.jaw.staff.controller.StaffDetailsVo;
import com.jaw.staff.controller.StaffMasterVo;
import com.jaw.staff.controller.StaffVo;
import com.jaw.staff.dao.IStaffDetailsDAO;
import com.jaw.staff.dao.IStaffMasterDAO;
import com.jaw.staff.dao.StaffDetails;
import com.jaw.staff.dao.StaffMaster;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.student.admission.controller.StudentMasterVO;
import com.jaw.student.admission.dao.IParentDetailsListDao;
import com.jaw.student.admission.dao.IStudentMasterDao;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.admission.dao.StudentMasterKey;
import com.jaw.user.controller.BranchAdminVO;
import com.jaw.user.controller.SingleParentDetailsVO;
import com.jaw.user.dao.ISingleUserDetailsDao;
import com.jaw.user.dao.IUserLinkDao;
import com.jaw.user.dao.SingleParentDetails;
import com.jaw.user.dao.UserLink;

@Service
public class SingleUserDetailsService implements ISingleUserDetailsService {
	Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private IUserLinkDao userLinkDao;
	@Autowired
	private IMgmtUserDetailsDAO managementDetailsDAO;
	@Autowired
	private CommonBusiness commonBusiness;
	@Autowired
	private INonStaffDetailsDAO nonStaffDetailsDAO;
	@Autowired
	ISingleUserDetailsDao singleUserDetailsDao;
	@Autowired
	IStaffMasterDAO staffMasterDAO;
	@Autowired
	IStaffDetailsDAO staffDetailsDAO;
	@Autowired
	IStudentMasterDao studentMasterDao;
	@Autowired
	IParentDetailsListDao parentDetailsListDao;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	
	@Override
	public String getSingleUser(ApplicationCache applicationCache,
			String userId, UserSessionDetails userSessionDetails,
			MgmtUserVO managementVO, BranchAdminVO branchAdminVO,
			StaffVo staffAdmissionVo, AdmissionDetailsVO admissionDetailsVO,
			SingleParentDetailsVO singleParentDetailsVO, String profileGroup)
			throws NoDataFoundException, InvalidUserIdException,
			PropertyNotFoundException {
		
		UserLink users = userLinkDao.getUserDetails(userId);
		
		ProfileGroup pg = ProfileGroup.valueOf(users.getProfileGroup());
		System.out.println("User profile group :" + pg);
		switch (pg) {
			case STU: {
				
				StudentMasterKey studentMasterKey = new StudentMasterKey();
				studentMasterKey.setInstId(userSessionDetails.getInstId());
				studentMasterKey.setBranchId(userSessionDetails.getBranchId());
				studentMasterKey.setStudentAdmisNo(users.getLinkId());
				studentMasterKey.setAcademicYear(propertyManagementUtil.getPropertyValue(applicationCache,
						users.getInstId(), users.getBranchId(),
						userSessionDetails.getCurrAcTerm()));
				StudentMaster studentMaster = studentMasterDao
						.retriveStudentDetails(studentMasterKey);
				
				StudentMasterVO master = new StudentMasterVO();
				commonBusiness.changeObject(master, studentMaster);
				admissionDetailsVO.setStudentMasterVO(master);
				
				profileGroup = ApplicationConstant.PG_STUDENT;
				break;
				
			}
			case PAR: {
				
				List<SingleParentDetails> singleParentDetails2 = singleUserDetailsDao
						.selectSingleParentDetails(
								userSessionDetails.getBranchId(),
								userSessionDetails.getInstId(),
								propertyManagementUtil.getPropertyValue(applicationCache,
										users.getInstId(), users.getBranchId(),
										userSessionDetails.getCurrAcTerm()),
								users.getLinkId());
				List<StudentMasterVO> studentDetailsVOs = new ArrayList<StudentMasterVO>();
				for (SingleParentDetails parentDetails : singleParentDetails2) {
					StudentMasterVO singleStudentDetailsVO2 = new StudentMasterVO();
					commonBusiness.changeObject(singleStudentDetailsVO2,
							parentDetails);
					studentDetailsVOs.add(singleStudentDetailsVO2);
					singleParentDetailsVO.setUserId(userId);
					singleParentDetailsVO.setFatherName(parentDetails
							.getFatherName());
					System.out.println("student admis no name :"
							+ singleStudentDetailsVO2.getStudentAdmisNo());
				}
				System.out.println("parent name :" + singleParentDetailsVO.getFatherName());
				
				singleParentDetailsVO.setStudentDetails(studentDetailsVOs);
				profileGroup = ApplicationConstant.PG_PARENT;
				break;
			}
			case MGT: {
				
				MgmtUser management = managementDetailsDAO.selectManagementRec(
						users.getInstId(),
						users.getBranchId(), users.getLinkId());
				System.out.println("After management dao called");
				commonBusiness.changeObject(managementVO, management);
				System.out.println("b4 assign to profile group");
				profileGroup = ApplicationConstant.PG_MGMT;
				System.out.println("Profile group value :" + profileGroup);
				break;
			}
			case NSF: {
				
				NonStaff nonStaff = nonStaffDetailsDAO.selectNonStaffRec(
						users.getInstId(),
						users.getBranchId(), users.getLinkId());
				System.out.println("Non staff data :" + nonStaff.getStaffId());
				commonBusiness.changeObject(branchAdminVO, nonStaff);
				branchAdminVO.setStaffId(nonStaff.getStaffId());
				branchAdminVO.setProfileGroup(ApplicationConstant.PG_NON_STAFF);
				
				profileGroup = ApplicationConstant.PG_NON_STAFF;
				break;
			}
			case STF: {
				System.out.println("Inside staff profile group :" + pg);
				StaffMaster staffMaster = staffMasterDAO.selectStaffRec(
						users.getLinkId(), users.getInstId(), users.getBranchId());
				
				StaffDetails staffDetails = staffDetailsDAO.selectStaffRec(
						users.getInstId(), users.getBranchId(), users.getLinkId());
				
				StaffMasterVo staffMasterVo = new StaffMasterVo();
				StaffDetailsVo staffDetailsVo = new StaffDetailsVo();
				commonBusiness.changeObject(staffMasterVo, staffMaster);
				commonBusiness.changeObject(staffDetailsVo, staffDetails);
				
				staffAdmissionVo.setStaffDetailsVo(staffDetailsVo);
				staffAdmissionVo.setStaffMasterVo(staffMasterVo);
				staffAdmissionVo.setDbTs(staffMaster.getDbTs());
				staffAdmissionVo.setBranchId(staffMaster.getBranchId());
				
				profileGroup = ApplicationConstant.PG_STAFF;
				break;
			}
		}
		System.out.println("Profile group in service :" + profileGroup);
		return profileGroup;
		
	}
}
