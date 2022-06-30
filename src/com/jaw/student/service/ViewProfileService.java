package com.jaw.student.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.common.files.dao.IFileMasterDao;
import com.jaw.common.files.dao.IFileMasterListDao;
import com.jaw.common.files.service.FileMasterHelper;
import com.jaw.common.files.service.IFileMasterService;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.core.dao.ITransportDetails;
import com.jaw.core.dao.TransportDetails;
import com.jaw.core.dao.TransportDetailsKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.student.admission.controller.FileTypeVO;
import com.jaw.student.admission.controller.PreSportParticipationDetailsVO;
import com.jaw.student.admission.controller.StudentMasterVO;
import com.jaw.student.admission.dao.CommunicationDetails;
import com.jaw.student.admission.dao.CommunicationDetailsKey;
import com.jaw.student.admission.dao.ICommunicationDetails;
import com.jaw.student.admission.dao.IParentDetails;
import com.jaw.student.admission.dao.IPreSportsPartDao;
import com.jaw.student.admission.dao.IPreSportspartListDao;
import com.jaw.student.admission.dao.IPrevAcademicDetails;
import com.jaw.student.admission.dao.ISiblingDetails;
import com.jaw.student.admission.dao.IStudentInfo;
import com.jaw.student.admission.dao.IStudentMasterDao;
import com.jaw.student.admission.dao.IStudentMasterListDAO;
import com.jaw.student.admission.dao.ParentDetails;
import com.jaw.student.admission.dao.ParentDetailsKey;
import com.jaw.student.admission.dao.PreSportParticipationDetails;
import com.jaw.student.admission.dao.PreSportsPartDetailsKey;
import com.jaw.student.admission.dao.PrevAcademicDetails;
import com.jaw.student.admission.dao.PrevAcademicDetailsKey;
import com.jaw.student.admission.dao.SiblingDetails;
import com.jaw.student.admission.dao.SiblingDetailsKey;
import com.jaw.student.admission.dao.StudentInfo;
import com.jaw.student.admission.dao.StudentInfoKey;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.admission.dao.StudentMasterKey;
import com.jaw.student.admission.service.AdmissionHelper;
import com.jaw.user.dao.IUserDao;

@Service
public class ViewProfileService implements IViewProfileService {
	
	Logger logger = Logger.getLogger(ViewProfileService.class);
	
	@Autowired
	private IStudentMasterDao studentMasterDAO;
	@Autowired
	private IStudentInfo studentInfoDao;
	@Autowired
	private IParentDetails parentDetailsDAO;
	@Autowired
	private ISiblingDetails siblingDetailsDao;
	@Autowired
	private ICommunicationDetails communicationDetailsDao;
	@Autowired
	private IPrevAcademicDetails prevAcademicDetailsDao;
	@Autowired
	private ITransportDetails transportDetailsDao;
	@Autowired
	IPreSportspartListDao preSportsPartListDao; 
	@Autowired
	IPreSportsPartDao preSportsPartDao; 
	@Autowired
	private IFileMasterDao fileMasterDao;
	@Autowired
	private IFileMasterListDao fileMasterListDao;
	@Autowired
	private AdmissionHelper admissionHelper;
	@Autowired
	private IStudentMasterListDAO studentMasterListDAO;
	
	@Autowired
	private IUserDao userDAO;
	@Autowired
	private CommonBusiness commonBusiness;
	
	@Autowired
	private ViewAdmisHelper admisHelper;
	@Autowired
	FileMasterHelper fileMaserHelper;
	@Autowired
	DoAudit doAudit;
	@Autowired
	IFileMasterService fileMasterService;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	
	@Override
	public StudentMaster getStudentMaster(String studentAdmisNo,
			AdmissionDetailsVO admissionDetailsVO,
			UserSessionDetails userSessionDetails, ApplicationCache applicationCache)
			throws PropertyNotFoundException, NoDataFoundException {		
		StudentMasterKey studentMasterKey = new StudentMasterKey();
		studentMasterKey.setInstId(userSessionDetails.getInstId());
		studentMasterKey.setBranchId(userSessionDetails.getBranchId());
		studentMasterKey.setStudentAdmisNo(studentAdmisNo);
		studentMasterKey.setAcademicYear(admissionDetailsVO.getAcademicYear());
		StudentMaster studentMaster = studentMasterDAO
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
	}
	
	@Override
	public void viewStudentDetails(AdmissionDetailsVO viewAdmis,
			UserSessionDetails userSessionDetails, ApplicationCache applicationCache)
			throws PropertyNotFoundException, NoDataFoundException {
		/*viewAdmis
				.setAcademicYear(PropertyManagementConstant.CURRENT_ACADEMIC_YEAR);	*/
		admisHelper.viewStudentDetails(viewAdmis, parentDetailsDAO,
				studentMasterDAO, studentInfoDao, siblingDetailsDao,
				communicationDetailsDao, prevAcademicDetailsDao,
				transportDetailsDao,preSportsPartListDao, userSessionDetails, applicationCache);
	}
	
	@Override
	public FileTypeVO getFileType(UserSessionDetails userSessionDetails,
			String studentAdmisNo) throws NoDataFoundException {
		
		List<FileMaster> fileType = fileMasterService.getListOfFilesFileId(
				userSessionDetails, studentAdmisNo);
		FileTypeVO fileTypeVO = new FileTypeVO();
		List<String> mapOfSprtCert = new ArrayList<String>();
		for (FileMaster files : fileType) {
			
			if (files.getFileType().equals(ApplicationConstant.STUDENT_PHOTO_KEY)) {
				fileTypeVO.setStudentPhoto(files.getFileType());
			}
			else if (files.getFileType().equals(ApplicationConstant.STUDENT_BIRTHC_KEY)) {
				fileTypeVO.setStudentBirth(files.getFileType());
			}
			else if (files.getFileType().equals(ApplicationConstant.STUDENT_CASTEC_KEY)) {
				fileTypeVO.setStudentCaste(files.getFileType());
			}
			else if (files.getFileType().equals(ApplicationConstant.FATHER_PHOTO_KEY)) {
				fileTypeVO.setFatherPhoto(files.getFileType());
			}
			else if (files.getFileType().equals(ApplicationConstant.MOTHER_PHOTO_KEY)) {
				fileTypeVO.setMotherPhoto(files.getFileType());
			}
			else if (files.getFileType().equals(ApplicationConstant.GUARDIAN_PHOTO_KEY)) {
				fileTypeVO.setGuardianPhoto(files.getFileType());
			}
			else if (files.getFileType().equals(ApplicationConstant.TRANSFER_CERT_KEY)) {
				fileTypeVO.setTransCert(files.getFileType());
			}
			else if (files.getFileType().equals(ApplicationConstant.STUDENT_MARKS_KEY)) {
				fileTypeVO.setStudentMark(files.getFileType());
			}
			else if (files.getFileType().equals(ApplicationConstant.TRASSIT_PHOTO_KEY)) {
				fileTypeVO.setTransAssitPhoto(files.getFileType());
			}else if (files.getFileType().equals(ApplicationConstant.STUDENT_MEDICALC_KEY)) {
				fileTypeVO.setStuMedicalCert(files.getFileType());
			}else if (files.getFileType().equals(ApplicationConstant.STUDENT_SPORTS_CERT_KEY)) {				
				mapOfSprtCert.add(files.getFileType());				
				fileTypeVO.setSportsCert(mapOfSprtCert);
			}
		}
		
		return fileTypeVO;
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void viewProfilEdit(ApplicationCache applicationCache,
			AdmissionDetailsVO admissionDetailsVO,
			UserSessionDetails userSessionDetails, String action,ServletContext servletContext)
			throws IOException, DuplicateEntryException,
			FileNotFoundInDatabase, DatabaseException,
			TableNotSpecifiedForAuditException, PropertyNotFoundException, UpdateFailedException, NoDataFoundException,DeleteFailedException, IllegalStateException, FileNotSaveException {		
		
		// Creating new Objects to store Data and save in DB
		
		if (action.equals(TableNameConstant.STUDENT_MASTER)) {
			logger.info("****studentMaster****");
			StudentMaster studentMaster = new StudentMaster();
			StudentMasterKey studentMasterKey = new StudentMasterKey();
			studentMasterKey.setBranchId(userSessionDetails.getBranchId());
			studentMasterKey.setInstId(userSessionDetails.getInstId());
			studentMasterKey.setStudentAdmisNo(admissionDetailsVO
					.getStudentAdmisNo());
			studentMasterKey.setAcademicYear(admissionDetailsVO.getAcademicYear());
			
			StudentMaster oldRecord = studentMasterDAO
					.retriveStudentDetails(
							studentMasterKey);
			
			admisHelper.studentMaster(admissionDetailsVO, studentMaster);
			studentMaster.setrModId(userSessionDetails.getUserId());
			studentMaster.setBranchId(userSessionDetails.getBranchId());
			studentMaster.setInstId(userSessionDetails.getInstId());
			studentMaster.setStudentAdmisNo(admissionDetailsVO
					.getStudentAdmisNo());
			studentMasterDAO.updateStudentMaster(studentMaster,
					studentMasterKey);
			StudentMaster newRecord = studentMasterDAO
					.retriveStudentDetails(
							studentMasterKey);
			doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
					TableNameConstant.STUDENT_MASTER,
					newRecord.toStringForAuditInstMasterKey(),
					oldRecord.toStringForAuditInstMasterRecord(),
					AuditConstant.TYPE_OF_OPER_UPDATE,
					newRecord.toStringForAuditInstMasterRecord(), "");
			
		}
		else if (action.equals(TableNameConstant.STUDENTINFO)) {
			logger.info("****studentInfo****");
			StudentInfo studentInfo = new StudentInfo();
			
			StudentInfoKey studentInfoKey = new StudentInfoKey();
			studentInfoKey.setBranchId(userSessionDetails.getBranchId());
			studentInfoKey.setInstId(userSessionDetails.getInstId());
			studentInfoKey.setStudentAdmisNo(admissionDetailsVO
					.getStudentAdmisNo());
			StudentInfo oldRecord = studentInfoDao
					.retriveStudentInfo(studentInfoKey);
			admisHelper.studentInfo(admissionDetailsVO, studentInfo);
			studentInfo.setrModId(userSessionDetails.getUserId());
			studentInfo.setBranchId(userSessionDetails.getBranchId());
			studentInfo.setInstId(userSessionDetails.getInstId());
			studentInfo.setStudentAdmisNo(admissionDetailsVO
					.getStudentAdmisNo());
			studentInfoDao.updateStudentInfo(studentInfo, studentInfoKey);
			StudentInfo newRecord = studentInfoDao
					.retriveStudentInfo(studentInfoKey);
			if (admissionDetailsVO.getStudentInfoVO().getStudentPhoto() != null) {
				if (!admissionDetailsVO.getStudentInfoVO().getStudentPhoto()
						.isEmpty()) {
					
						fileMaserHelper.fileUpload(applicationCache,
								admissionDetailsVO.getStudentInfoVO()
										.getStudentPhoto(),
								ApplicationConstant.STUDENT_PHOTO_KEY, 1,
								userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
								admissionDetailsVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
					
				}
			}
			if (admissionDetailsVO.getStudentInfoVO().getBirthCertificate() != null) {
				if (!admissionDetailsVO.getStudentInfoVO()
						.getBirthCertificate().isEmpty()) {
					
					
						fileMaserHelper.fileUpload(applicationCache,
								admissionDetailsVO.getStudentInfoVO()
										.getBirthCertificate(),
								ApplicationConstant.STUDENT_BIRTHC_KEY, 1,
								userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
								admissionDetailsVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
					
				}
			}
			if (admissionDetailsVO.getStudentInfoVO().getCasteCertificate() != null) {
				if (!admissionDetailsVO.getStudentInfoVO()
						.getCasteCertificate().isEmpty()) {				
						fileMaserHelper.fileUpload(applicationCache,
								admissionDetailsVO.getStudentInfoVO()
										.getCasteCertificate(),
								ApplicationConstant.STUDENT_CASTEC_KEY, 1,
								userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
								admissionDetailsVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
					
				}
			}
			if (admissionDetailsVO.getStudentInfoVO().getMedicalCertificate() != null) {
				if (!admissionDetailsVO.getStudentInfoVO().getMedicalCertificate().isEmpty()) {					
						fileMaserHelper.fileUpload(applicationCache,
								admissionDetailsVO.getStudentInfoVO()
										.getMedicalCertificate(),
								ApplicationConstant.STUDENT_MEDICALC_KEY, 1,
								userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
								admissionDetailsVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
					
				}
			}
			
			
			doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
					TableNameConstant.STUDENTINFO,
					newRecord.toStringForAuditInstMasterKey(),
					oldRecord.toStringForAuditInstMasterRecord(),
					AuditConstant.TYPE_OF_OPER_UPDATE,
					newRecord.toStringForAuditInstMasterRecord(), " ");
		}
		else if (action.equals(TableNameConstant.PARENT_DETAILS)) {
			logger.info("****parentDetails****");
			ParentDetails parentDetails = new ParentDetails();
			
			ParentDetailsKey parentDetailsKey = new ParentDetailsKey();
			parentDetailsKey.setBranchId(userSessionDetails.getBranchId());
			parentDetailsKey.setInstId(userSessionDetails.getInstId());
			parentDetailsKey.setStudentAdmisNo(admissionDetailsVO
					.getStudentAdmisNo());
			
			ParentDetails oldRecord = parentDetailsDAO
					.retriveParentDetails(parentDetailsKey);
			parentDetailsKey.setParentId(oldRecord.getParentId());
			admisHelper.parentDetails(admissionDetailsVO, parentDetails);
			parentDetails.setrModId(userSessionDetails.getUserId());
			parentDetails.setBranchId(userSessionDetails.getBranchId());
			parentDetails.setInstId(userSessionDetails.getInstId());
			parentDetails.setStudentAdmisNo(admissionDetailsVO
					.getStudentAdmisNo());
			parentDetailsDAO.updateParentDetails(parentDetails,
					parentDetailsKey);
			ParentDetails newRecord = parentDetailsDAO
					.retriveParentDetails(parentDetailsKey);
			if (admissionDetailsVO.getParentDetailsVO().getFatherPhoto() != null) {
				if (!admissionDetailsVO.getParentDetailsVO().getFatherPhoto()
						.isEmpty()) {
					
					
						fileMaserHelper.fileUpload(applicationCache,
								admissionDetailsVO.getParentDetailsVO()
										.getFatherPhoto(),
								ApplicationConstant.FATHER_PHOTO_KEY, 1,
								userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
								admissionDetailsVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
					
				}
			}
			if (admissionDetailsVO.getParentDetailsVO().getMotherPhoto() != null) {
				if (!admissionDetailsVO.getParentDetailsVO().getMotherPhoto()
						.isEmpty()) {
					
					
						fileMaserHelper.fileUpload(applicationCache,
								admissionDetailsVO.getParentDetailsVO()
										.getMotherPhoto(),
								ApplicationConstant.MOTHER_PHOTO_KEY, 1,
								userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
								admissionDetailsVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
					
				}
			}
			if (admissionDetailsVO.getParentDetailsVO().getGuardianPhoto() != null) {
				if (!admissionDetailsVO.getParentDetailsVO().getGuardianPhoto()
						.isEmpty()) {
					
					
						fileMaserHelper.fileUpload(applicationCache,
								admissionDetailsVO.getParentDetailsVO()
										.getGuardianPhoto(),
								ApplicationConstant.GUARDIAN_PHOTO_KEY, 1,
								userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
								admissionDetailsVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
					
				}
			}
			doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
					TableNameConstant.PARENT_DETAILS,
					newRecord.toStringForAuditInstMasterKey(),
					oldRecord.toStringForAuditInstMasterRecord(),
					AuditConstant.TYPE_OF_OPER_UPDATE,
					newRecord.toStringForAuditInstMasterRecord(), " ");
		}
		else if (action.equals(TableNameConstant.SIBLINGDETAILS)) {
			List<SiblingDetails> siblingDetails = new ArrayList<SiblingDetails>();
			admisHelper.siblingDetails(admissionDetailsVO, siblingDetails);					
			for(SiblingDetails siblingDet:siblingDetails){
				if((!siblingDet.getSiblingName().trim().equals(null))&&(!siblingDet.getSiblingName().trim().equals(""))){
					SiblingDetailsKey siblingDetailsKey = new SiblingDetailsKey();
					siblingDetailsKey.setBranchId(userSessionDetails.getBranchId());
					siblingDetailsKey.setInstId(userSessionDetails.getInstId());
					siblingDetailsKey.setStudentAdmisNo(admissionDetailsVO
							.getStudentAdmisNo());
					siblingDetailsKey.setSiblingNo(Integer.valueOf(siblingDet.getSiblingNo()));					
					siblingDet.setInstId(userSessionDetails.getInstId());
					siblingDet.setBranchId(userSessionDetails.getBranchId());
					siblingDet.setrModId(userSessionDetails.getUserId());
			
					try{				
				
				
				SiblingDetails oldRec =  siblingDetailsDao.retriveSingleSibDetails(siblingDetailsKey);
								
				siblingDetailsDao.updateSibDetails(siblingDet,
						siblingDetailsKey);
				
				SiblingDetails newRec =  siblingDetailsDao.retriveSingleSibDetails(siblingDetailsKey);
				doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
						TableNameConstant.SIBLINGDETAILS,
						newRec.toStringForAuditSibKey(),
						oldRec.toStringForAuditSiblingRecord(),
						AuditConstant.TYPE_OF_OPER_UPDATE,
						newRec.toStringForAuditSiblingRecord(), "");
								
				}catch(NoDataFoundException e){	
					List<SiblingDetails> listOfSib = new ArrayList<SiblingDetails>();
				listOfSib.add(siblingDet);
					siblingDetailsDao.insertSiblingDetails(listOfSib);
					SiblingDetails sibling = siblingDetailsDao.retriveSingleSibDetails(siblingDetailsKey);
				doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
						TableNameConstant.SIBLINGDETAILS,
						
						sibling.toStringForAuditSibKey(),"",
						AuditConstant.TYPE_OF_OPER_INSERT,
						sibling.toStringForAuditSiblingRecord(), "");
				}
			}
			
			}
	
		}
		else if (action.equals(TableNameConstant.COMMDETAILS)) {
			
			logger.info("****communicationDetails****");
			CommunicationDetails communicationDetails = new CommunicationDetails();
			
			CommunicationDetailsKey communicationDetailsKey = new CommunicationDetailsKey();
			communicationDetailsKey.setBranchId(userSessionDetails
					.getBranchId());
			communicationDetailsKey.setInstId(userSessionDetails.getInstId());
			communicationDetailsKey.setStudentAdmisNo(admissionDetailsVO
					.getStudentAdmisNo());
			CommunicationDetails oldRecord = communicationDetailsDao
					.retriveCommunicationDetails(communicationDetailsKey);
			admisHelper.communicationDetails(admissionDetailsVO,
					communicationDetails);
			communicationDetails.setrModId(userSessionDetails.getUserId());
			communicationDetails.setBranchId(userSessionDetails.getBranchId());
			communicationDetails.setInstId(userSessionDetails.getInstId());
			communicationDetails.setStudentAdmisNo(admissionDetailsVO
					.getStudentAdmisNo());
			communicationDetailsDao.updateCommDetails(communicationDetails,
					communicationDetailsKey);
			CommunicationDetails newRecord = communicationDetailsDao
					.retriveCommunicationDetails(communicationDetailsKey);
			doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
					TableNameConstant.COMMDETAILS,
					newRecord.toStringForAuditInstMasterKey(),
					oldRecord.toStringForAuditInstMasterRecord(),
					AuditConstant.TYPE_OF_OPER_UPDATE,
					newRecord.toStringForAuditInstMasterRecord(), " ");
		}
		else if (action.equals(TableNameConstant.PREVSCHOOLDETAILS)) {
			logger.info("****previousSchoolDetails****");
			PrevAcademicDetails prevAcademicDetails = new PrevAcademicDetails();
			
			PrevAcademicDetailsKey prevAcademicDetailsKey = new PrevAcademicDetailsKey();
			prevAcademicDetailsKey
					.setBranchId(userSessionDetails.getBranchId());
			prevAcademicDetailsKey.setInstId(userSessionDetails.getInstId());
			prevAcademicDetailsKey.setStudentAdmisNo(admissionDetailsVO
					.getStudentAdmisNo());
			PrevAcademicDetails oldRecord = prevAcademicDetailsDao
					.retrivePrevAcademicDetails(prevAcademicDetailsKey);
			admisHelper.prevAcademicDetails(admissionDetailsVO,
					prevAcademicDetails);
			prevAcademicDetails.setrModId(userSessionDetails.getUserId());
			prevAcademicDetails.setBranchId(userSessionDetails.getBranchId());
			prevAcademicDetails.setInstId(userSessionDetails.getInstId());
			prevAcademicDetails.setStudentAdmisNo(admissionDetailsVO
					.getStudentAdmisNo());		
			
			prevAcademicDetailsDao.updatePrevAcaDetails(prevAcademicDetails,
					prevAcademicDetailsKey);
			PrevAcademicDetails newRecord = prevAcademicDetailsDao
					.retrivePrevAcademicDetails(prevAcademicDetailsKey);
			if ((admissionDetailsVO.getPrevAcademicDetailsVO()!=null)&&(!admissionDetailsVO.getPrevAcademicDetailsVO()
					.getTransferCertificate().isEmpty())) {
				
				
					fileMaserHelper.fileUpload(applicationCache, admissionDetailsVO
							.getPrevAcademicDetailsVO().getTransferCertificate(),
							ApplicationConstant.TRANSFER_CERT_KEY, 1,
							userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
							admissionDetailsVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
				
			}
			if ((admissionDetailsVO.getPrevAcademicDetailsVO().getMarksheet()
					!=null)&&(!admissionDetailsVO.getPrevAcademicDetailsVO().getMarksheet()
					.isEmpty())) {
				
				
					fileMaserHelper.fileUpload(applicationCache, admissionDetailsVO
							.getPrevAcademicDetailsVO().getMarksheet(),
							ApplicationConstant.STUDENT_MARKS_KEY, 1,
							userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
							admissionDetailsVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
				
			}
			if (admissionDetailsVO.getFiles()!=null) {						
				for(Integer index=1;index<admissionDetailsVO.getFiles().size();index++){					
					if(!admissionDetailsVO.getFiles().get(index).isEmpty()){						
						fileMaserHelper.fileUpload(applicationCache, admissionDetailsVO.getFiles().get(index),
								ApplicationConstant.STUDENT_SPORTS_CERT_KEY,1,
								userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
								admissionDetailsVO.getStudentAdmisNo(),index.toString(),ApplicationConstant.PG_STUDENT,servletContext);
					}
					
				
				}
				
							
			}
			doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
					TableNameConstant.PREVSCHOOLDETAILS,
					newRecord.toStringForAuditInstMasterKey(),
					oldRecord.toStringForAuditInstMasterRecord(),
					AuditConstant.TYPE_OF_OPER_UPDATE,
					newRecord.toStringForAuditInstMasterRecord(), " ");
			
			//Update ion ppspd table-list of objects
			List<PreSportParticipationDetails> preSportPartDetailsList = new ArrayList<PreSportParticipationDetails>();
			admisHelper.preSportsPartDetails(admissionDetailsVO, preSportPartDetailsList, userSessionDetails);
			
			
			for(PreSportParticipationDetails preSportPartDetails:preSportPartDetailsList){
				if(!preSportPartDetails.getSportsLevel().equals("")){
					
				
				PreSportsPartDetailsKey preSportsPartKey = new PreSportsPartDetailsKey();
				preSportsPartKey.setInstId(userSessionDetails.getInstId());
				preSportsPartKey.setBranchId(userSessionDetails.getBranchId());
				preSportsPartKey.setStudentAdmisNo(admissionDetailsVO.getStudentAdmisNo());
				preSportsPartKey.setSportsEntrySerialNo(preSportPartDetails.getSportsEntrySerialNo());
				PreSportParticipationDetails participationDetailsOld= null;
				try{					
					participationDetailsOld = preSportsPartDao.retrivePreSportsPart(preSportsPartKey);
					preSportPartDetails.setDbTs(participationDetailsOld.getDbTs());
					preSportPartDetails.setrCreTime(participationDetailsOld.getrCreTime());
					preSportsPartDao.updatePreSportsPart(preSportPartDetails, preSportsPartKey);					
					PreSportParticipationDetails participationDetailsNew= preSportsPartDao.retrivePreSportsPart(preSportsPartKey);					
					doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
							TableNameConstant.PRE_SPORTS_PART_DETAILS,
							participationDetailsNew.toStringForAuditInstMasterKey(),
							participationDetailsOld.toStringForAudit(),
							AuditConstant.TYPE_OF_OPER_UPDATE,
							participationDetailsNew.toStringForAudit(), "");					
				}catch(NoDataFoundException e){					
					preSportsPartDao.insertPreSportsPart(preSportPartDetails);
					doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
							TableNameConstant.PRE_SPORTS_PART_DETAILS,
							preSportPartDetails.toStringForAuditInstMasterKey(),
							"",
							AuditConstant.TYPE_OF_OPER_INSERT,
							"", "");
				}
				
				}
			}
			
			
			
			
		}
		else if (action.equals(TableNameConstant.TRANSPORTDETAILS)) {
			
			logger.info("****transportDetails****");
			TransportDetailsKey transportDetailsKey = new TransportDetailsKey();
			transportDetailsKey.setBranchId(userSessionDetails.getBranchId());
			transportDetailsKey.setInstId(userSessionDetails.getInstId());
			transportDetailsKey.setStudentAdmisNo(admissionDetailsVO
					.getStudentAdmisNo());
			TransportDetails transportDetails = new TransportDetails();
			TransportDetails oldRecord = transportDetailsDao
					.retriveTransportDetails(transportDetailsKey);
			admisHelper.transportDetails(admissionDetailsVO, transportDetails);
			transportDetails.setBranchId(userSessionDetails.getBranchId());
			transportDetails.setInstId(userSessionDetails.getInstId());
			transportDetails.setStudentAdmisNo(admissionDetailsVO
					.getStudentAdmisNo());
			transportDetailsDao.updateTransportDetails(transportDetails,
					transportDetailsKey);
			TransportDetails newRecord = transportDetailsDao
					.retriveTransportDetails(transportDetailsKey);
			if (!admissionDetailsVO.getTransportDetailsVO().getPickupAssPhoto()
					.isEmpty()) {
				
				fileMaserHelper.fileUpload(applicationCache, admissionDetailsVO
						.getTransportDetailsVO().getPickupAssPhoto(),
						ApplicationConstant.TRASSIT_PHOTO_KEY, 1,
						userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
						admissionDetailsVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
			}
			doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
					TableNameConstant.TRANSPORTDETAILS,
					newRecord.toStringForAuditInstMasterKey(),
					oldRecord.toStringForAuditInstMasterRecord(),
					AuditConstant.TYPE_OF_OPER_UPDATE,
					newRecord.toStringForAuditInstMasterRecord(), " ");
		}
		
		
		
	}
}
