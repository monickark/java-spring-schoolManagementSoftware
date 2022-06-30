package com.jaw.student.admission.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.common.files.service.FileMasterHelper;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionDetailsVO;


@Service
public class ViewAndEditFileService implements IViewAndEditFileService{
	Logger logger = Logger.getLogger(ViewAndEditFileService.class);
	@Autowired
	FileMasterHelper fileMasterHelper;
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveFile(AdmissionDetailsVO admisVO,UserSessionDetails userSessionDetails,ApplicationCache applicationCache,ServletContext servletContext) throws IOException, IllegalAccessException, InvocationTargetException, DatabaseException, DuplicateEntryException, FileNotFoundInDatabase,DeleteFailedException,FileNotSaveException{		
		Integer fileSrlNo = 1;
		List<FileMaster> fileMasObj = new ArrayList<FileMaster>();		
		if (admisVO.getStudentInfoVO().getStudentPhoto() != null) {
			if (!admisVO.getStudentInfoVO().getStudentPhoto()
					.isEmpty()) {
							
				fileMasterHelper.fileUpload(applicationCache,
						admisVO.getStudentInfoVO()
								.getStudentPhoto(),
						ApplicationConstant.STUDENT_PHOTO_KEY, 1,
						userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
						admisVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
			}
		}
		if (admisVO.getStudentInfoVO().getBirthCertificate() != null) {
			if (!admisVO.getStudentInfoVO()
					.getBirthCertificate().isEmpty()) {				
				fileMasterHelper.fileUpload(applicationCache,
						admisVO.getStudentInfoVO()
								.getBirthCertificate(),
						ApplicationConstant.STUDENT_BIRTHC_KEY, 1,
						userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
						admisVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
			}
		}
		if (admisVO.getStudentInfoVO().getCasteCertificate() != null) {
			if (!admisVO.getStudentInfoVO()
					.getCasteCertificate().isEmpty()) {
				
				fileMasterHelper.fileUpload(applicationCache,
						admisVO.getStudentInfoVO()
								.getCasteCertificate(),
						ApplicationConstant.STUDENT_CASTEC_KEY, 1,
						userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
						admisVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
			}
		}
		if (admisVO.getStudentInfoVO().getMedicalCertificate() != null) {
			if (!admisVO.getStudentInfoVO().getMedicalCertificate().isEmpty()) {
				
				fileMasterHelper.fileUpload(applicationCache,
						admisVO.getStudentInfoVO()
								.getMedicalCertificate(),
						ApplicationConstant.STUDENT_MEDICALC_KEY, 1,
						userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
						admisVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
			}
		}
		
		if ((admisVO.getParentDetailsVO()!=null)&&(admisVO.getParentDetailsVO().getFatherPhoto() != null)) {
			if (!admisVO.getParentDetailsVO().getFatherPhoto()
					.isEmpty()) {
				
				fileMasterHelper.fileUpload(applicationCache,
						admisVO.getParentDetailsVO()
								.getFatherPhoto(),
						ApplicationConstant.FATHER_PHOTO_KEY, 1,
						userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
						admisVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
			}
		}
		if ((admisVO.getParentDetailsVO()!=null)&&(admisVO.getParentDetailsVO().getMotherPhoto() != null)) {
			if (!admisVO.getParentDetailsVO().getMotherPhoto()
					.isEmpty()) {
				fileMasterHelper.fileUpload(applicationCache,
						admisVO.getParentDetailsVO()
								.getMotherPhoto(),
						ApplicationConstant.MOTHER_PHOTO_KEY, 1,
						userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
						admisVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
			}
		}
		if ((admisVO.getParentDetailsVO()!=null)&&(admisVO.getParentDetailsVO().getGuardianPhoto() != null)) {
			if (!admisVO.getParentDetailsVO().getGuardianPhoto()
					.isEmpty()) {
				
				fileMasterHelper.fileUpload(applicationCache,
						admisVO.getParentDetailsVO()
								.getGuardianPhoto(),
						ApplicationConstant.GUARDIAN_PHOTO_KEY, 1,
						userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
						admisVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
			}
		}
		
		if ((admisVO.getPrevAcademicDetailsVO()!=null)&&(!admisVO.getPrevAcademicDetailsVO()
				.getTransferCertificate().isEmpty())) {
			
			fileMasterHelper.fileUpload(applicationCache, admisVO
					.getPrevAcademicDetailsVO().getTransferCertificate(),
					ApplicationConstant.TRANSFER_CERT_KEY, 1,
					userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
					admisVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
		}
		if ((admisVO.getPrevAcademicDetailsVO()!=null)&&(!admisVO.getPrevAcademicDetailsVO().getMarksheet()
				.isEmpty())) {
			
			fileMasterHelper.fileUpload(applicationCache, admisVO
					.getPrevAcademicDetailsVO().getMarksheet(),
					ApplicationConstant.STUDENT_MARKS_KEY, 1,
					userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
					admisVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
		}	
		if (!admisVO.getFiles().isEmpty()) {		
			//Integer fileSrlNo =< 0;			
			for(Integer index=1;index<admisVO.getFiles().size();index++){				
				if(!admisVO.getFiles().get(index).isEmpty()){								
					fileMasterHelper.fileUpload(applicationCache, admisVO.getFiles().get(index),
							ApplicationConstant.STUDENT_SPORTS_CERT_KEY,1,
							userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
							admisVO.getStudentAdmisNo(),index.toString(),ApplicationConstant.PG_STUDENT,servletContext);
				}
				
			
			}
			
				
		
		
		
		}
		
		if ((admisVO.getTransportDetailsVO()!=null)&&(!admisVO.getTransportDetailsVO().getPickupAssPhoto()
				.isEmpty())) {
			
			fileMasterHelper.fileUpload(applicationCache, admisVO
					.getTransportDetailsVO().getPickupAssPhoto(),
					ApplicationConstant.TRASSIT_PHOTO_KEY, 1,
					userSessionDetails, userSessionDetails.getUserId(),userSessionDetails.getInstId(),userSessionDetails.getBranchId(),
					admisVO.getStudentAdmisNo(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STUDENT,servletContext);
		}
		
				
		
	}

}
