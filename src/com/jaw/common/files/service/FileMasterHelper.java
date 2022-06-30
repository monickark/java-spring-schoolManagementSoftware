package com.jaw.common.files.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.dao.FileHistory;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.common.files.dao.IFileHistoryDao;
import com.jaw.common.files.dao.IFileMasterDao;
import com.jaw.common.files.dao.IFileMasterListDao;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.controller.FileTypeVO;
import com.jaw.student.admission.controller.AdmissionDetailsVO;

//Helper class for file operations
@Component
public class FileMasterHelper {

	// Logging
	Logger logger = Logger.getLogger(FileMasterHelper.class);

	@Autowired
	IIdGeneratorService simpleIdGenerator;
	@Autowired
	IFileMasterDao fileMasterDao;
	@Autowired
	IFileMasterListDao fileMasterListDao;
	// Helper class to do Auditing
	@Autowired
	DoAudit doAudit;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	IFileHistoryDao fileHistoryDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	FileSaveHelper fileSaveHelper;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	public void fileUpload(ApplicationCache applicationCache,
			MultipartFile imagefile, String fileType, Integer dbtsValue,
			UserSessionDetails userSessionDetails, String rCreId,
			String instId, String branchId, String linkId, String fileSrlNo,String profileGrp,ServletContext servletContext)
			throws DuplicateEntryException, FileNotFoundInDatabase,
			DatabaseException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {
		if (branchId != null) {
			userSessionDetails.setBranchId(branchId);
		}
		if (instId != null) {
			userSessionDetails.setInstId(instId);
		}
		fileUpload(applicationCache, imagefile, fileType, dbtsValue,
				userSessionDetails, rCreId, linkId, fileSrlNo,profileGrp,servletContext);
	}

	// Method to update a single file/image
	public void fileUpload(ApplicationCache applicationCache,
			MultipartFile imagefile, String fileType, Integer dbtsValue,
			UserSessionDetails userSessionDetails, String rCreId,
			String linkId, String fileSrlNo,String profileGrp,ServletContext servletContext) throws DuplicateEntryException,
			FileNotFoundInDatabase, DatabaseException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {
		FileMaster file = null;
		try {

			if (!imagefile.isEmpty()) {
				file = new FileMaster();
				BeanUtils.copyProperties(file, imagefile);
				file.setFileType(fileType);
				Integer seq = simpleIdGenerator.getNextId(
						SequenceConstant.FILE_REF_KEY, 1);
				file.setFileRefno((AlphaSequenceUtil.generateAlphaSequence(
						ApplicationConstant.STRING_FILE_MASTER_SEQ, seq)));
				file.setLinkId(linkId);
				file.setInputStream(imagefile.getInputStream());
				file.setFile(imagefile);
				file.setContentType(imagefile.getContentType());
				file.setSize(imagefile.getSize());
				file.setDbTs(dbtsValue);
				file.setInstId(userSessionDetails.getInstId());
				file.setBranchId(userSessionDetails.getBranchId());
				file.setDelFlg("N");
				file.setrModId(userSessionDetails.getUserId());
				file.setrModTime(dateUtil.getCurrentDateWithTime());
				file.setrCreId(rCreId);
				file.setrCreTime(dateUtil.getCurrentDateWithTime());
				if ((fileSrlNo == null) || (fileSrlNo == "")) {
					file.setFileSrlno(ApplicationConstant.DEFAULT_FILE_SRL_NO);
				} else {
					file.setFileSrlno(fileSrlNo);
				}

			}

		} catch (IllegalAccessException illegalAccessException) {
			logger.error(illegalAccessException);
		} catch (InvocationTargetException invocationTargetException) {
			logger.error(invocationTargetException);
		} catch (IOException e) {
			logger.error("IO Exception in FileMaster", e);
		}	
		doFileInsert(applicationCache, file, userSessionDetails,profileGrp,servletContext);

	}

	// method to do file insert operation for Institute Master Logo
	public void doFileInsert(ApplicationCache applicationCache,
			FileMaster file, UserSessionDetails userSessionDetails,String profileGrp,ServletContext servletContext)
			throws DuplicateEntryException, FileNotFoundInDatabase,
			DatabaseException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {
	
		FileMaster fileMaster = null;
		if (file != null) {
			
			//Check for prpm constant for database or filesystem storage
			String stuFileStorage = null;
			try {
				String prpmConstant = null;
				if(profileGrp.equals(ApplicationConstant.PG_STUDENT)){
					prpmConstant = PropertyManagementConstant.STU_FILE_STORAGE;
				}else if(profileGrp.equals(ApplicationConstant.PG_STAFF)){
					prpmConstant = PropertyManagementConstant.STF_FILE_STORAGE;
				}
				stuFileStorage=	propertyManagementUtil.getPropertyValue(applicationCache, userSessionDetails.getInstId(),
						userSessionDetails.getBranchId(),prpmConstant);
			} catch (PropertyNotFoundException e) {
				logger.error("Prpm value for File storage option not found");
				e.printStackTrace();
			}					
		String fileSrlNo = file.getFileSrlno();
				if (fileSrlNo == "") {
					fileSrlNo = ApplicationConstant.DEFAULT_FILE_SRL_NO;
				}
				fileMaster = fileMasterDao.getSingleFile(file.getInstId(),
						file.getBranchId(), file.getLinkId(),
						file.getFileType(), fileSrlNo);
							
				
				FileHistory fileHis = new FileHistory();
				commonBusiness.changeObject(fileHis, fileMaster);
				fileHis.setFlmtRCreId(fileMaster.getrCreId());
				fileHis.setFlmtRCreTime(fileMaster.getrCreTime());
				fileHis.setrCreId(userSessionDetails.getUserId());

				Integer srlNo = 0;
				srlNo = simpleIdGenerator.getNextId(
						SequenceConstant.FILE_HISTROY_SRL_NO, 1);
				fileHis.setFileHtSrlNo(AlphaSequenceUtil.generateAlphaSequence(
						ApplicationConstant.STRING_FILE_HISTORY_SEQ, srlNo));
				

				fileMasterDao.insertSingleFile(file, 1);
			
			
		}
			}
	
	// Method for List of Files upload-Admission Details
	public List<FileMaster> fileUpload(ApplicationCache applicationCache,
			AdmissionDetailsVO admissioncommonVo,
			UserSessionDetails userSessionDetails,String profileGrp,ServletContext servletContext) throws DatabaseException,
			DuplicateEntryException, IllegalStateException, FileNotSaveException {
		List<FileMaster> fileMaster = new ArrayList<FileMaster>();
		try {

			FileMaster studentPhoto = getFileMasterObject(admissioncommonVo
					.getStudentInfoVO().getStudentPhoto(),
					ApplicationConstant.DEFAULT_FILE_SRL_NO,
					admissioncommonVo.getStudentAdmisNo(), userSessionDetails,
					ApplicationConstant.STUDENT_PHOTO_KEY);
			if (studentPhoto != null) {
				fileMaster.add(studentPhoto);
			}

			if (admissioncommonVo.getStudentInfoVO().getBirthCertificate() != null) {
				FileMaster birthCertificate = getFileMasterObject(
						admissioncommonVo.getStudentInfoVO()
								.getBirthCertificate(),
						ApplicationConstant.DEFAULT_FILE_SRL_NO,
						admissioncommonVo.getStudentAdmisNo(),
						userSessionDetails,
						ApplicationConstant.STUDENT_BIRTHC_KEY);
				if (birthCertificate != null) {
					fileMaster.add(birthCertificate);
				}
			}
			if (admissioncommonVo.getStudentInfoVO().getCasteCertificate() != null) {
				FileMaster studentCasteCertificate = getFileMasterObject(
						admissioncommonVo.getStudentInfoVO()
								.getCasteCertificate(),
						ApplicationConstant.DEFAULT_FILE_SRL_NO,
						admissioncommonVo.getStudentAdmisNo(),
						userSessionDetails,
						ApplicationConstant.STUDENT_CASTEC_KEY);
				if (studentCasteCertificate != null) {
					fileMaster.add(studentCasteCertificate);
				}
			}
			if (admissioncommonVo.getStudentInfoVO().getMedicalCertificate() != null) {
				FileMaster studentCasteCertificate = getFileMasterObject(
						admissioncommonVo.getStudentInfoVO()
								.getMedicalCertificate(),
						ApplicationConstant.DEFAULT_FILE_SRL_NO,
						admissioncommonVo.getStudentAdmisNo(),
						userSessionDetails,
						ApplicationConstant.STUDENT_MEDICALC_KEY);
				if (studentCasteCertificate != null) {
					fileMaster.add(studentCasteCertificate);
				}
			}

			if ((admissioncommonVo.getParentDetailsVO() != null)
					&& (admissioncommonVo.getParentDetailsVO().getFatherPhoto() != null)) {
				FileMaster fatherPhoto = getFileMasterObject(admissioncommonVo
						.getParentDetailsVO().getFatherPhoto(),
						ApplicationConstant.DEFAULT_FILE_SRL_NO,
						admissioncommonVo.getStudentAdmisNo(),
						userSessionDetails,
						ApplicationConstant.FATHER_PHOTO_KEY);
				if (fatherPhoto != null) {
					fileMaster.add(fatherPhoto);
				}
			}
			if ((admissioncommonVo.getParentDetailsVO() != null)
					&& (admissioncommonVo.getParentDetailsVO().getMotherPhoto() != null)) {
				FileMaster motherPhoto = getFileMasterObject(admissioncommonVo
						.getParentDetailsVO().getMotherPhoto(),
						ApplicationConstant.DEFAULT_FILE_SRL_NO,
						admissioncommonVo.getStudentAdmisNo(),
						userSessionDetails,
						ApplicationConstant.MOTHER_PHOTO_KEY);
				if (motherPhoto != null) {
					fileMaster.add(motherPhoto);
				}
			}
			if ((admissioncommonVo.getParentDetailsVO() != null)
					&& (admissioncommonVo.getParentDetailsVO()
							.getGuardianPhoto() != null)) {
				FileMaster guardianphoto = getFileMasterObject(
						admissioncommonVo.getParentDetailsVO()
								.getGuardianPhoto(),
						ApplicationConstant.DEFAULT_FILE_SRL_NO,
						admissioncommonVo.getStudentAdmisNo(),
						userSessionDetails,
						ApplicationConstant.GUARDIAN_PHOTO_KEY);
				if (guardianphoto != null) {
					fileMaster.add(guardianphoto);
				}
			}
			if ((admissioncommonVo.getPrevAcademicDetailsVO() != null)
					&& (admissioncommonVo.getPrevAcademicDetailsVO()
							.getTransferCertificate() != null)) {
				FileMaster transferCertificate = getFileMasterObject(
						admissioncommonVo.getPrevAcademicDetailsVO()
								.getTransferCertificate(),
						ApplicationConstant.DEFAULT_FILE_SRL_NO,
						admissioncommonVo.getStudentAdmisNo(),
						userSessionDetails,
						ApplicationConstant.TRANSFER_CERT_KEY);
				if (transferCertificate != null) {
					fileMaster.add(transferCertificate);
				}
			}
			if ((admissioncommonVo.getPrevAcademicDetailsVO() != null)
					&& (admissioncommonVo.getPrevAcademicDetailsVO()
							.getMarksheet() != null)) {
				FileMaster markSheet = getFileMasterObject(admissioncommonVo
						.getPrevAcademicDetailsVO().getMarksheet(),
						ApplicationConstant.DEFAULT_FILE_SRL_NO,
						admissioncommonVo.getStudentAdmisNo(),
						userSessionDetails,
						ApplicationConstant.STUDENT_MARKS_KEY);
				if (markSheet != null) {
					fileMaster.add(markSheet);
				}
			}
			if (admissioncommonVo.getTransportDetailsVO() != null) {
				FileMaster pickupAssistantPhoto = getFileMasterObject(
						admissioncommonVo.getTransportDetailsVO()
								.getPickupAssPhoto(),
						ApplicationConstant.DEFAULT_FILE_SRL_NO,
						admissioncommonVo.getStudentAdmisNo(),
						userSessionDetails,
						ApplicationConstant.TRASSIT_PHOTO_KEY);
				if (pickupAssistantPhoto != null) {
					fileMaster.add(pickupAssistantPhoto);
				}
			}
			if (admissioncommonVo.getFiles() != null) {
				Integer countIndex = 1;
				for (MultipartFile sportCert : admissioncommonVo.getFiles()) {
					FileMaster sportsCert = getFileMasterObject(sportCert,
							countIndex.toString(),
							admissioncommonVo.getStudentAdmisNo(),
							userSessionDetails,
							ApplicationConstant.STUDENT_SPORTS_CERT_KEY);
					if (sportsCert != null) {
						fileMaster.add(sportsCert);
						countIndex++;
					}

				}

			}
			String stuFileStorage = null;
			try {
				stuFileStorage=	propertyManagementUtil.getPropertyValue(applicationCache, userSessionDetails.getInstId(), userSessionDetails.getBranchId(), PropertyManagementConstant.STU_FILE_STORAGE);
			} catch (PropertyNotFoundException e) {
				logger.error("Prpm value for File storage option not found");
				e.printStackTrace();
			}						
			
			insertBatchOfFiles(fileMaster,stuFileStorage,ApplicationConstant.PG_STUDENT,servletContext);

		} catch (IllegalAccessException illegalAccessException) {
			logger.error(illegalAccessException);
		} catch (InvocationTargetException invocationTargetException) {
			logger.error(invocationTargetException);
		} catch (IOException e) {
			logger.error("IO Exception in FileMaster", e);
		}
		return fileMaster;

	}

	public void fileTypeList(ApplicationCache applicationCache,
			FileTypeVO fileTypeVO, UserSessionDetails userSessionDetails)
			 {		
		List<FileMaster> fileMaster = fileMasterListDao.fileTypeList(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), fileTypeVO.getStaffId());
		List<String> biodata = new ArrayList<String>();
		List<String> certificates = new ArrayList<String>();
		for (FileMaster files : fileMaster) {

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
	}

	public List<FileMaster> fileUpload(ApplicationCache applicationCache,
			FileTypeVO fileTypeVO, UserSessionDetails userSessionDetails,String profileGrp,ServletContext servletContext)
			throws DatabaseException, DuplicateEntryException, IllegalStateException, FileNotSaveException {
		List<FileMaster> fileMaster = new ArrayList<FileMaster>();
		try {
			
			fileTypeList(applicationCache,
					fileTypeVO, userSessionDetails);		
			if (fileTypeVO.getCertificateFiles() != null) {
				Integer countIndex = fileTypeVO.getCerticates().size()+1;			
				for (MultipartFile certificates : fileTypeVO
						.getCertificateFiles()) {
					FileMaster sportsCert = getFileMasterObject(certificates,
							countIndex.toString(), fileTypeVO.getStaffId(),
							userSessionDetails,
							ApplicationConstant.STAFF_CERT_KEY);
					if (sportsCert != null) {
						fileMaster.add(sportsCert);
						countIndex++;
					}

				}

			}
			if (fileTypeVO.getBioDataFiles() != null) {
				Integer countIndex =fileTypeVO.getBiodata().size()+1;
				for (MultipartFile certificates : fileTypeVO.getBioDataFiles()) {
					FileMaster sportsCert = getFileMasterObject(certificates,
							countIndex.toString(), fileTypeVO.getStaffId(),
							userSessionDetails,
							ApplicationConstant.STAFF_BIODATA_KEY);
					if (sportsCert != null) {
						fileMaster.add(sportsCert);
						countIndex++;
					}

				}

			}		
			
			String stuFileStorage = null;
			try {
				stuFileStorage=	propertyManagementUtil.getPropertyValue(applicationCache, userSessionDetails.getInstId(), userSessionDetails.getBranchId(), PropertyManagementConstant.STF_FILE_STORAGE);
			} catch (PropertyNotFoundException e) {
				logger.error("Prpm value for File storage option not found");
				e.printStackTrace();
			}						
		
				insertBatchOfFiles(fileMaster,stuFileStorage,ApplicationConstant.PG_STAFF,servletContext);
			
		} catch (IllegalAccessException illegalAccessException) {
			logger.error(illegalAccessException);
		} catch (InvocationTargetException invocationTargetException) {
			logger.error(invocationTargetException);
		} catch (IOException e) {
			logger.error("IO Exception in FileMaster", e);
		}
		return fileMaster;

	}

	// Method to get FileMaster Object for Batch of Files used in Admission
	public FileMaster getFileMasterObject(MultipartFile image,
			String fileSrlNo, String studentAdmisNo,
			UserSessionDetails userSessionDetails, String fileType)
			throws IOException, IllegalAccessException,
			InvocationTargetException, DatabaseException {
		FileMaster file = null;
		if ((image!=null)&&(!image.isEmpty())){
			file = new FileMaster();
			BeanUtils.copyProperties(file, image);
			file.setFileType(fileType);
			Integer seq = simpleIdGenerator.getNextId(
					SequenceConstant.FILE_REF_KEY, 1);
			file.setFileRefno((AlphaSequenceUtil.generateAlphaSequence(
					ApplicationConstant.STRING_FILE_MASTER_SEQ, seq)));
			file.setLinkId(studentAdmisNo);
			file.setInputStream(image.getInputStream());
			file.setFile(image);
			file.setContentType(image.getContentType());
			file.setSize(image.getSize());
			file.setInstId(userSessionDetails.getInstId());
			file.setBranchId(userSessionDetails.getBranchId());
			file.setDelFlg("N");
			file.setrModId(userSessionDetails.getUserId());
			file.setrModTime(dateUtil.getCurrentDateWithTime());
			file.setrCreId(userSessionDetails.getUserId());
			file.setrCreTime(dateUtil.getCurrentDateWithTime());
			file.setFileSrlno(fileSrlNo);
		}
		return file;

	}

	// Method to insert List of files in FileMaster as a batch used in Admission
	public void insertBatchOfFiles(List<FileMaster> fileMaster,String stuFileStorage,String profileGrp,ServletContext servletContext)
			throws DatabaseException, IllegalStateException, IOException, FileNotSaveException {	
	
			 Integer seqStarting = simpleIdGenerator.getNextId(
				SequenceConstant.FILE_REF_KEY, fileMaster.size());
		try {
			if (fileMaster != null && fileMaster.size() > 0) {
				Integer seq = seqStarting;
				seq=seq+1;			
				if(!stuFileStorage.equals(ApplicationConstant.FILE_IN_DB)){
					
					for(FileMaster fileMas:fileMaster){
						StringBuffer directoryStructure = new StringBuffer(fileSaveHelper.getDestinationDirectory(fileMaster.get(0).getInstId(), fileMaster.get(0).getBranchId(),
								profileGrp, fileMaster.get(0).getLinkId(),"",""));						
						if(!fileMas.getFileType().split("_")[0].equals(ApplicationConstant.PHOTO_CONSTANT)){
							String relativePath = (directoryStructure.append("/").append(ApplicationConstant.FILE_SEQ_CONSTANT+seq.toString()).append(".jpeg")).toString();
							String dirPath = FileSaveHelper.fileLocation(servletContext).concat(relativePath);		
							logger.debug("File Location :"+dirPath);
							fileSaveHelper.saveMultipartToDisk(dirPath, fileMas.getFile());							
							fileMas.setInputStream(null);	
							fileMas.setFileName(seq.toString()+".jpeg");
							fileMas.setFilepath(relativePath);
							seq++;
							
						}																		
					}
				}					
				fileMasterListDao.fileBatch(fileMaster, seqStarting);
			}
		} catch (DuplicateEntryException e) {

		}		

	}
	
		
}
