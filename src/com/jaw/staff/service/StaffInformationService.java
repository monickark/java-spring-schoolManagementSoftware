package com.jaw.staff.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.admin.dao.IRequestListDao;
import com.jaw.admin.dao.RequestList;
import com.jaw.admin.service.InstituteMasterService;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.common.files.service.FileMasterHelper;
import com.jaw.common.files.service.IFileMasterService;
import com.jaw.core.dao.CourseClasses;
import com.jaw.core.dao.CourseClassesList;
import com.jaw.core.dao.ICourseClassesListDAO;
import com.jaw.core.dao.ITeacherSubjectLinkListDAO;
import com.jaw.core.dao.TeacherSubjectLinkList;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.controller.FileTypeVO;
import com.jaw.staff.controller.StaffDetailsVo;
import com.jaw.staff.controller.StaffListSearchVo;
import com.jaw.staff.controller.StaffListVo;
import com.jaw.staff.controller.StaffMasterVo;
import com.jaw.staff.controller.StaffVo;
import com.jaw.staff.dao.IStaffDetailsDAO;
import com.jaw.staff.dao.IStaffInformationListDao;
import com.jaw.staff.dao.IStaffMasterDAO;
import com.jaw.staff.dao.StaffDetails;
import com.jaw.staff.dao.StaffInformationList;
import com.jaw.staff.dao.StaffMaster;
import com.jaw.user.dao.IUserDao;
import com.jaw.user.dao.IUserLinkDao;
import com.jaw.user.dao.User;
import com.jaw.user.dao.UserLink;
import com.jaw.user.util.UserCreation;

//Service class for FileMaster 
@Service
public class StaffInformationService implements IStaffInformationService {

	// Logging
	Logger logger = Logger.getLogger(InstituteMasterService.class);

	@Autowired
	IStaffMasterDAO staffMasterDAO;
	@Autowired
	IStaffDetailsDAO staffDetailsDAO;
	@Autowired
	ICourseClassesListDAO courseClassesListDAO;
	@Autowired
	ITeacherSubjectLinkListDAO teacherSubjectLinkListDAO;
	@Autowired
	IRequestListDao requestListDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	UserCreation userCreation;
	@Autowired
	FileMasterHelper fileMasterHelper;
	@Autowired
	IStaffInformationListDao StaffListVoDao;
	@Autowired
	IUserLinkDao userLinkDao;
	@Autowired
	IUserDao userDao;
	@Autowired
	DoAudit doAudit;
	@Autowired
	FileMasterHelper fileMasHelper;
	@Autowired
	IFileMasterService fileMasterService;

	@Override
	public List<StaffListVo> selectStaff(StaffListSearchVo staffListSearchVo,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		StaffInformationList staffInformationList = new StaffInformationList();
		commonBusiness.changeObject(staffInformationList, staffListSearchVo);
		staffInformationList.setInstId(userSessionDetails.getInstId());
		staffInformationList.setBranchId(userSessionDetails.getBranchId());
		List<StaffInformationList> list = StaffListVoDao
				.getStaffListForInformation(staffInformationList);
		List<StaffListVo> staffListVos = new ArrayList<StaffListVo>();
		int rowId = 0;
		for (StaffInformationList informationList : list) {
			StaffListVo staffListVo = new StaffListVo();
			commonBusiness.changeObject(staffListVo, informationList);
			staffListVo.setRowId(rowId);
			staffListVos.add(staffListVo);
			rowId++;
		}
		return staffListVos;
	}

	@Override
	public void selectStaff(StaffVo staffAdmissionVo, String staffId,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		StaffMaster staffMaster = staffMasterDAO.selectStaffRec(staffId,
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId());
		StaffDetails staffDetails = staffDetailsDAO.selectStaffRec(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), staffId);
		StaffMasterVo staffMasterVo = new StaffMasterVo();
		StaffDetailsVo staffDetailsVo = new StaffDetailsVo();
		commonBusiness.changeObject(staffMasterVo, staffMaster);
		commonBusiness.changeObject(staffDetailsVo, staffDetails);
		staffAdmissionVo.setStaffMasterVo(staffMasterVo);
		staffAdmissionVo.setStaffDetailsVo(staffDetailsVo);
		staffAdmissionVo.setDbTs(staffMaster.getDbTs());
	}

	@Override
	public FileTypeVO getFileType(UserSessionDetails userSessionDetails,
			String staffId) throws NoDataFoundException {

		List<FileMaster> fileType = fileMasterService.getListOfFilesFileId(
				userSessionDetails, staffId);
		FileTypeVO fileTypeVO = new FileTypeVO();
		List<String> biodata = new ArrayList<String>();
		List<String> certificates = new ArrayList<String>();
		for (FileMaster files : fileType) {

			if (files.getFileType().equals(
					ApplicationConstant.STAFF_BIODATA_KEY)) {
				biodata.add(files.getFileType());
				fileTypeVO.setBiodata(biodata);
			} else if (files.getFileType().equals(
					ApplicationConstant.STAFF_CERT_KEY)) {
				certificates.add(files.getFileType());
				fileTypeVO.setCerticates(certificates);
			}
		}

		return fileTypeVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateStaff(StaffVo adminVO,
			UserSessionDetails userSessionDetails)
			throws FileNotFoundInDatabase, DuplicateEntryException,
			InvalidUserIdException, UpdateFailedException {

		UserLink link = userLinkDao.getUserDetailsByLinkId(adminVO
				.getStaffMasterVo().getStaffId(), userSessionDetails
				.getInstId(), adminVO.getBranchId());

		link.setUserMenuProfile(adminVO.getMenuProfile());
		link.setrModId(userSessionDetails.getUserId());
		userLinkDao.updateUserLinkRec(link);

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateStaffFullDetails(StaffVo staffVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,ServletContext servletContext) throws FileNotFoundInDatabase,
			DuplicateEntryException, InvalidUserIdException,
			UpdateFailedException, DatabaseException,
			TableNotSpecifiedForAuditException, NoDataFoundException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {

		StaffMaster staffMaster = staffMasterDAO.selectStaffRec(staffVo
				.getStaffMasterVo().getStaffId(), userSessionDetails
				.getInstId(), userSessionDetails.getBranchId());
		String oldrecordSM = staffMaster.stringForDbAudit();

		StaffDetails staffDetails = staffDetailsDAO
				.selectStaffRec(userSessionDetails.getInstId(),
						userSessionDetails.getBranchId(), staffVo
								.getStaffMasterVo().getStaffId());
		String oldrecordSD = staffDetails.toStringForDBAuditRecord();

		commonBusiness.changeObject(staffMaster, staffVo.getStaffMasterVo());
		commonBusiness.changeObject(staffDetails, staffVo.getStaffDetailsVo());

		staffMaster.setDbTs(staffVo.getDbTs());
		staffMaster.setrModId(userSessionDetails.getUserId());
		staffMaster.setInstId(userSessionDetails.getInstId());
		staffMaster.setBranchId(userSessionDetails.getBranchId());
		staffMaster.setDelFlg("N");

		staffDetails.setStaffId(staffVo.getStaffMasterVo().getStaffId());
		staffDetails.setDbTs(staffVo.getDbTs());
		staffDetails.setrModId(userSessionDetails.getUserId());
		staffDetails.setInstId(userSessionDetails.getInstId());
		staffDetails.setBranchId(userSessionDetails.getBranchId());
		staffDetails.setStaffName(staffVo.getStaffMasterVo().getStaffName());
		staffDetails.setDelFlg("N");

		staffMasterDAO.updateStaffMaster(staffMaster);
		staffDetailsDAO.updateStaffDetails(staffDetails);

		fileMasterHelper.fileUpload(applicationCache, staffVo.getStaffPhoto(),
				ApplicationConstant.STAFF_PHOTO, 1, userSessionDetails,
				userSessionDetails.getUserId(), staffVo.getStaffMasterVo()
						.getStaffId(), ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STAFF,servletContext);

		String remarks = "inst_id:" + userSessionDetails.getInstId()
				+ ", branch_id:" + userSessionDetails.getBranchId()
				+ ", StaffId:" + staffVo.getStaffMasterVo().getStaffId();
		String newrecordSM = staffMaster.stringForDbAudit();
		String newrecordSD = staffDetails.toStringForDBAuditRecord();
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.STAFF_UPDATED, remarks);

		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.STAFF_MASTER, staffMaster.toStringForDBKey(),
				oldrecordSM, AuditConstant.TYPE_OF_OPER_UPDATE, newrecordSM, "");
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.STAFF_DETAILS,
				staffDetails.toStringForAuditInstMasterKey(), oldrecordSD,
				AuditConstant.TYPE_OF_OPER_UPDATE, newrecordSD, "");

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteStaff(StaffVo adminVO,
			UserSessionDetails userSessionDetails, ApplicationCache appl)
			throws FileNotFoundInDatabase, DuplicateEntryException,
			UpdateFailedException, DatabaseException, NoDataFoundException,
			InvalidUserIdException, TableNotSpecifiedForAuditException, BatchUpdateFailedException {
		
		staffTransferProcess(adminVO, userSessionDetails);
		StaffMaster staffMaster = staffMasterDAO.selectStaffRec(adminVO
				.getStaffMasterVo().getStaffId(), userSessionDetails
				.getInstId(), userSessionDetails.getBranchId());
		String oldrecordSM = staffMaster.stringForDbAudit();
		commonBusiness.changeObject(staffMaster, adminVO.getStaffMasterVo());
		staffMaster.setDbTs(adminVO.getDbTs());
		staffMaster.setrModId(userSessionDetails.getUserId());
		staffMaster.setInstId(userSessionDetails.getInstId());
		staffMaster.setBranchId(userSessionDetails.getBranchId());
		staffMaster.setDelFlg(ApplicationConstant.DEL_FLG_TRANSFERED);
		String newrecordSM = staffMaster.stringForDbAudit();
		logger.debug("Delete Where class values for staff :- InstId :"
				+ userSessionDetails.getInstId() + " BranchId:"
				+ userSessionDetails.getBranchId() + " StaffId :"
				+ adminVO.getStaffMasterVo().getStaffId());
		StaffDetails staffDetails = staffDetailsDAO
				.selectStaffRec(userSessionDetails.getInstId(),
						userSessionDetails.getBranchId(), adminVO
								.getStaffMasterVo().getStaffId());
		String oldrecordSD = staffDetails.toStringForDBAuditRecord();
		commonBusiness.changeObject(staffDetails, adminVO.getStaffDetailsVo());
		staffDetails.setStaffId(adminVO.getStaffMasterVo().getStaffId());
		staffDetails.setDbTs(adminVO.getDbTs());
		staffDetails.setrModId(userSessionDetails.getUserId());
		staffDetails.setInstId(userSessionDetails.getInstId());
		staffDetails.setBranchId(userSessionDetails.getBranchId());
		staffDetails.setStaffName(adminVO.getStaffMasterVo().getStaffName());
		staffDetails.setDelFlg(ApplicationConstant.DEL_FLG_TRANSFERED);
		String newrecordSD = staffDetails.toStringForDBAuditRecord();
		staffMasterDAO.updateStaffMaster(staffMaster);
		staffDetailsDAO.updateStaffDetails(staffDetails);
		
		UserLink link;
		try {
			link = userLinkDao.getUserDetailsByLinkId(adminVO
					.getStaffMasterVo().getStaffId(), userSessionDetails
					.getInstId(), userSessionDetails.getBranchId());
			link.setDeleteFlag("Y");
			link.setrModId(userSessionDetails.getUserId());
			userLinkDao.updateUserLinkRec(link);
			User user = userDao.validateUserId(link.getUserId());
			user.setDeleteFlag("Y");
			user.setrModId(userSessionDetails.getUserId());
			userDao.updateUser(user);
		} catch (InvalidUserIdException e) {
		}
		
		String remarks = "inst_id:" + userSessionDetails.getInstId()
				+ ", branch_id:" + userSessionDetails.getBranchId()
				+ ", StaffId:" + adminVO.getStaffMasterVo().getStaffId();
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.STAFF_UPDATED, remarks);

		doAudit.doDatabaseAudit(appl, userSessionDetails,
				TableNameConstant.STAFF_MASTER, staffMaster.toStringForDBKey(),
				oldrecordSM, AuditConstant.TYPE_OF_OPER_UPDATE, newrecordSM, "");
		doAudit.doDatabaseAudit(appl, userSessionDetails,
				TableNameConstant.STAFF_DETAILS,
				staffDetails.toStringForAuditInstMasterKey(), oldrecordSD,
				AuditConstant.TYPE_OF_OPER_UPDATE, newrecordSD, "");
	}

	@Override
	public void staffTransferProcess(StaffVo adminVO,
			UserSessionDetails userSessionDetails)
			throws UpdateFailedException, 
			DuplicateEntryException, DatabaseException, BatchUpdateFailedException {
		
		
		
		RequestList requestList = new RequestList();
		requestList.setInstId(userSessionDetails.getInstId());
		requestList.setBranchId(userSessionDetails.getBranchId());
		requestList.setLinkId(adminVO.getStaffMasterVo()
				.getStaffId());
		List<RequestList> requestLists;
		try {
			requestLists = requestListDao.getRequestsListOfTransferredUser(userSessionDetails.getInstId(), userSessionDetails.getBranchId(), adminVO.getStaffMasterVo()
					.getStaffId());
			requestListDao
			.updateTransferredUserRequest(requestLists,userSessionDetails);
		} catch (NoDataFoundException e) {
		}
		
		
		try {
			CourseClassesList courseClassesList = new CourseClassesList();
			courseClassesList.setInstId(userSessionDetails.getInstId());
			courseClassesList.setBranchId(userSessionDetails.getBranchId());
			courseClassesList.setStaffId(adminVO.getStaffMasterVo().getStaffId());
			List<CourseClasses> courseClasses;
			courseClasses = courseClassesListDAO
					.getStaffListForTransferProcess(courseClassesList);
			courseClassesListDAO.updateStaffDataOnTransfer(courseClasses);
		} catch (NoDataFoundException e1) {
		}
		
		
		try {
			TeacherSubjectLinkList teacherSubjectLinkList = new TeacherSubjectLinkList();
			teacherSubjectLinkList.setInstId(userSessionDetails.getInstId());
			teacherSubjectLinkList.setBranchId(userSessionDetails.getBranchId());
			teacherSubjectLinkList.setStaffId(adminVO.getStaffMasterVo()
					.getStaffId());
			List<TeacherSubjectLinkList> teacherSubjectLinkLists;
			teacherSubjectLinkLists = teacherSubjectLinkListDAO
					.getStaffListForTransferProcess(teacherSubjectLinkList);
			teacherSubjectLinkListDAO
			.updateStaffDataOnTransfer(teacherSubjectLinkLists);
			
		} catch (NoDataFoundException e) {
			
		}
		
		
		
		
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.STAFF_TRANSFERED, "");
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateStaffEditProfile(ApplicationCache applicationCache,
			StaffVo adminVO, UserSessionDetails userSessionDetails,ServletContext servletContext)
			throws FileNotFoundInDatabase, DuplicateEntryException,
			InvalidUserIdException, UpdateFailedException, DatabaseException,
			NoDataFoundException, TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {
		StaffMaster staffMaster = staffMasterDAO.selectStaffRec(adminVO
				.getStaffMasterVo().getStaffId(), userSessionDetails
				.getInstId(), userSessionDetails.getBranchId());
		String oldrecordSM = staffMaster.stringForDbAudit();
		commonBusiness.changeObject(staffMaster, adminVO.getStaffMasterVo());
		staffMaster.setDbTs(adminVO.getDbTs());
		staffMaster.setrModId(userSessionDetails.getUserId());
		staffMaster.setInstId(userSessionDetails.getInstId());
		staffMaster.setBranchId(userSessionDetails.getBranchId());
		staffMaster.setDelFlg("N");
		String newrecordSM = staffMaster.stringForDbAudit();
		StaffDetails staffDetails = staffDetailsDAO
				.selectStaffRec(userSessionDetails.getInstId(),
						userSessionDetails.getBranchId(), adminVO
								.getStaffMasterVo().getStaffId());
		String oldrecordSD = staffDetails.toStringForDBAuditRecord();
		commonBusiness.changeObject(staffDetails, adminVO.getStaffDetailsVo());
		staffDetails.setStaffId(adminVO.getStaffMasterVo().getStaffId());
		staffDetails.setDbTs(adminVO.getDbTs());
		staffDetails.setrModId(userSessionDetails.getUserId());
		staffDetails.setInstId(userSessionDetails.getInstId());
		staffDetails.setBranchId(userSessionDetails.getBranchId());
		staffDetails.setStaffName(adminVO.getStaffMasterVo().getStaffName());
		staffDetails.setDelFlg("N");
		String newrecordSD = staffDetails.toStringForDBAuditRecord();
		staffMasterDAO.updateStaffMaster(staffMaster);
		staffDetailsDAO.updateStaffDetails(staffDetails);

		String remarks = "inst_id:" + userSessionDetails.getInstId()
				+ ", branch_id:" + userSessionDetails.getBranchId()
				+ ", StaffId:" + adminVO.getStaffMasterVo().getStaffId();
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.STAFF_UPDATED, remarks);
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.STAFF_MASTER, staffMaster.toStringForDBKey(),
				oldrecordSM, AuditConstant.TYPE_OF_OPER_UPDATE, newrecordSM, "");
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.STAFF_DETAILS,
				staffDetails.toStringForAuditInstMasterKey(), oldrecordSD,
				AuditConstant.TYPE_OF_OPER_UPDATE, newrecordSD, "");
		fileMasterHelper.fileUpload(applicationCache, adminVO.getStaffPhoto(),
				ApplicationConstant.STAFF_PHOTO, 1, userSessionDetails,
				userSessionDetails.getUserId(), adminVO.getStaffMasterVo()
						.getStaffId(), ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STAFF,servletContext);
	}
}
