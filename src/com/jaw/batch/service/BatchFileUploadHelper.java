package com.jaw.batch.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.jaw.batch.controller.BatchFileUploadVO;
import com.jaw.batch.dao.BatchFileUpload;
import com.jaw.batch.dao.BatchStatus;
import com.jaw.batch.dao.IBatchFileUploadDao;
import com.jaw.batch.dao.MulitpartFileObject;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.BatchConstants;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.dao.FileHistory;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.common.files.dao.IFileMasterDao;
import com.jaw.common.files.service.FileSaveHelper;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
@Component
public class BatchFileUploadHelper {
	
	Logger logger = Logger.getLogger(BatchFileUploadHelper.class);
	
	
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IIdGeneratorService simpleIdGenerator;
	@Autowired
	IBatchFileUploadDao batchFileUploadDao;
	@Autowired
	DoAudit doAudit;
	@Autowired
	BatchHelper batchHelper;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	FileSaveHelper fileSaveHelper;
	
	
	
	public  void uploadBatchOfFiles(final BatchFileUploadVO batchFileUpload,final String batchSerialNo,
			final UserSessionDetails userSessionDetails,final ApplicationCache applicationCache,
			final BatchStatus batchStatus,final String profileGrp,final ServletContext servletContext) throws DatabaseException, IllegalStateException, IOException, FileNotSaveException{
		final List<String> linkIds =  new ArrayList<String>();	
		final List<String> srlNoList =  new ArrayList<String>();	
		
		final ArrayList<String> listOfFileNames= new ArrayList<String>();
		final ArrayList<String> listOfFilePath = new ArrayList<String>();
		MultipartFile[]  files=batchFileUpload.getFiles();	
		final ArrayList<MulitpartFileObject> listOfFiles= new ArrayList<MulitpartFileObject>();
		 final Integer seqStarting = simpleIdGenerator.getNextId(SequenceConstant.FILE_REF_KEY, listOfFiles.size());	
		
	//	seqStarting = 
		// final Integer= seqStarting;
		Integer seqForFileName = seqStarting;
		
		for(MultipartFile file:files){
			MulitpartFileObject multipart = new MulitpartFileObject();
			commonBusiness.changeObject(multipart, file);
			listOfFiles.add(multipart);
			//Forming link Id's
			String[] links=multipart.getOriginalFilename().split("\\.");
			String[] linkIdAndSrlNo = links[0].split("_");
			linkIds.add(linkIdAndSrlNo[0]);
			//Forming File srlNo
			if(linkIdAndSrlNo[1]!=null||(!linkIdAndSrlNo[1].equals(""))){
				srlNoList.add(linkIdAndSrlNo[1]);
			}else{
				srlNoList.add(ApplicationConstant.DEFAULT_FILE_SRL_NO);
			}
			
			String fileNameForDisk =ApplicationConstant.FILE_SEQ_CONSTANT+seqForFileName.toString() ;
			listOfFileNames.add(fileNameForDisk);
			StringBuffer directoryStructure = new StringBuffer(fileSaveHelper.getDestinationDirectory(batchFileUpload.getInstId(), batchFileUpload.getBranchId(),
					ApplicationConstant.PG_STUDENT,links[0],"",""));	
			directoryStructure.append("/").append(fileNameForDisk.trim()).append(".jpeg");		
			logger.debug("File path :"+directoryStructure.toString());
			//String actDir = FileSaveHelper.fileLocation(servletContext)+directoryStructure;
			
			listOfFilePath.add(directoryStructure.toString());
			seqForFileName++;
		}
		
			
		Thread t = new Thread(new Runnable() {
			public void run() {		
				String stuFileStorage = null;
				try {
					String prpmConstant = null;
					if(profileGrp.equals(ApplicationConstant.PG_STUDENT)){
						prpmConstant = PropertyManagementConstant.STU_FILE_STORAGE;
					}else if(profileGrp.equals(ApplicationConstant.PG_STAFF)){
						prpmConstant = PropertyManagementConstant.STF_FILE_STORAGE;
					}
					stuFileStorage=	propertyManagementUtil.getPropertyValue(applicationCache, batchFileUpload.getInstId(),batchFileUpload.getBranchId(), prpmConstant);
				} catch (PropertyNotFoundException e) {
					logger.error("Prpm value for File storage option not found");
					e.printStackTrace();
				}			
				BatchFileUpload batchFileUploadPojo = new BatchFileUpload();	
				commonBusiness.changeObject(batchFileUploadPojo, batchFileUpload);
				batchFileUploadPojo.setFileName(listOfFileNames);
				batchFileUploadPojo.setFilePath(listOfFilePath);												
				
				databaseOperations( batchFileUploadPojo,  listOfFiles,  batchFileUpload, batchSerialNo, 
						userSessionDetails,linkIds,applicationCache,batchStatus,stuFileStorage,
						servletContext,profileGrp,seqStarting,srlNoList);
		
			}
		});
		t.start();				
	
			
	}
	
	@Transactional(rollbackFor = Exception.class)
	
	public void databaseOperations(BatchFileUpload batchFileUploadPojo,ArrayList<MulitpartFileObject> listOfFiles,BatchFileUploadVO batchFileUpload,
			String batchSerialNo,UserSessionDetails userSessionDetails,List<String> listOfLinkId,
			ApplicationCache applicationCache,BatchStatus batchStatus,String stuFileStorage,ServletContext servletContext,
			String profileGrp,Integer seqStarting,List<String> listOfSrlNo) {	

		 List<FileMaster> existingRecords = null;	
		 List<FileHistory> listOfFileHisRec = new ArrayList<FileHistory>() ;		
		 List<String> existingFilesLinkId = new ArrayList<String>();
		 Integer startingSeq = 0;
		 String batchStatusUpdateConstant = "";
		 Integer noOfFiles =listOfFiles.size();
		 ArrayList<String> errorList = new ArrayList<String>();
		
		 
		try{		
					 			
			existingRecords = batchFileUploadDao.getEntireExistingRec(batchFileUploadPojo.getInstId(), batchFileUploadPojo.getBranchId(), batchFileUploadPojo.getFileType(),listOfLinkId);
			if(existingRecords.size()!=0){
			for(FileMaster fileMaster:existingRecords){
				FileHistory fileHistory = new FileHistory();
				commonBusiness.changeObject(fileHistory, fileMaster);
				fileHistory.setFlmtRCreId(fileMaster.getrCreId());
				fileHistory.setFlmtRCreTime(fileMaster.getrCreTime());
				fileHistory.setFileName(fileMaster.getFileName());
				fileHistory.setFilepath(fileMaster.getFilepath());
				listOfFileHisRec.add(fileHistory);
				existingFilesLinkId.add(fileMaster.getLinkId());
				
				
				
				if((!fileHistory.getFileType().split("_")[0].equals(ApplicationConstant.PHOTO_CONSTANT))&&(!stuFileStorage.equals(ApplicationConstant.FILE_IN_DB))){
					
					//Get the existing file and converting into mulipart to store in old files directory
					File fileInFileSys = new File(FileSaveHelper.fileLocation(servletContext)+fileHistory.getFilepath());									
					StringBuffer directoryStructure = new StringBuffer(fileSaveHelper.getDestinationDirectory(fileHistory.getInstId(), fileHistory.getBranchId(),
								profileGrp, fileHistory.getLinkId(),ApplicationConstant.OLD_FILE_STORAGE,""));
					directoryStructure.append("/").append(fileHistory.getFileName()+".jpeg");	
					String actDir = FileSaveHelper.fileLocation(servletContext)+directoryStructure;							
					//Moving the file to old files directory
								String operatingSys = System.getProperties().getProperty("os.name");							
								if(operatingSys.equals(ApplicationConstant.LINUX_OS)){
									fileInFileSys.renameTo(new File(actDir))	;
								}else{									
									
									InputStream inputStream = 
										      new FileInputStream(FileSaveHelper.fileLocation(servletContext)+fileHistory.getFilepath());
									
									OutputStream outputStream =  new FileOutputStream(new File(actDir));
									
									int read = 0;
									byte[] bytes = new byte[1024];
							 
									while ((read = inputStream.read(bytes)) != -1) {
										outputStream.write(bytes, 0, read);
									}
							 
									inputStream.close();
									outputStream.close();									
									fileInFileSys.delete();
								}						
					File file = new File(actDir);
					if(!file.exists()){					
						throw new FileNotSaveException();
					}
					fileHistory.setInputStream(null);					
					fileHistory.setFilepath(directoryStructure.toString());						
					//After Moving, insert the new file in file system with the same seq(that is same name)	
					Integer indexOfRemovingFile = listOfLinkId.indexOf(fileMaster.getLinkId());
					logger.debug("File path for new insert after history:"+FileSaveHelper.fileLocation(servletContext)+batchFileUploadPojo.getFilePath().get(indexOfRemovingFile));
					fileSaveHelper.saveMultipartToDisk(FileSaveHelper.fileLocation(servletContext)+batchFileUploadPojo.getFilePath().get(indexOfRemovingFile),batchFileUploadPojo.getFiles()[indexOfRemovingFile]);	
					
					listOfFiles.set(indexOfRemovingFile, null);			
				}	
	
			}	
			}else{				
				if((!batchFileUploadPojo.getFileType().split("_")[0].equals(ApplicationConstant.PHOTO_CONSTANT))&&
						(!stuFileStorage.equals(ApplicationConstant.FILE_IN_DB))){
					for(Integer index = 0 ;index<listOfFiles.size();index++){				
					String dirPath = FileSaveHelper.fileLocation(servletContext).concat(batchFileUploadPojo.getFilePath().get(index));		
					logger.debug("File Location :"+dirPath);
					fileSaveHelper.saveMultipartToDisk(dirPath, batchFileUploadPojo.getFiles()[index]);							
					listOfFiles.set(index, null);				
					}
					
				}
				
			}
			
		
			startingSeq = simpleIdGenerator.getNextId(SequenceConstant.FILE_HISTROY_SRL_NO, existingRecords.size());						
			batchFileUploadDao.insertIntoFlhtbeforeDelExistingRec(listOfFileHisRec,startingSeq);			
			batchFileUploadDao.batchDeleteExistingFiles(batchFileUploadPojo.getInstId(), batchFileUploadPojo.getBranchId(), batchFileUploadPojo.getFileType(),existingFilesLinkId);							
			
			batchFileUploadDao.batchFileUpload(batchFileUploadPojo, listOfFiles, seqStarting,listOfLinkId,listOfSrlNo);	
			String remarks = "Batch Program Name :"+ApplicationConstant.BATCH_PGMS_FILE_IMPORT+","+
				"Data type :"+ApplicationConstant.FILE_IMPORT_DATA_TYPE+","+"Batch Serial No:"+batchSerialNo;
			
			doAudit.doFunctionalAudit(
						userSessionDetails, AuditConstant.BATCH_PROGRAM_SUCCESS,
						remarks);			
		}catch(DatabaseException e){

			String errorInsert = "Database Exception occured,Please contact Admin Immediately.";
			errorList.add(errorInsert);			
		
		}catch (RuntimeExceptionForBatch e) {			
			String errorInsert = "Database operation Failed,Please contact Admin Immediately.";
			errorList.add(errorInsert);			
		}catch (DuplicateEntryException e) {
			String errorInsert = "Inserting Data into Database failed,Please contact Admin.";
			errorList.add(errorInsert);			
		} catch (IOException e) {
			String errorInsert = "Unable to Proceed,Error in File Operation.";
			errorList.add(errorInsert);			
		} catch (IllegalStateException e) {
			String errorInsert = "Unable to Proceed,IllegalState.";
			errorList.add(errorInsert);		
		} catch (FileNotSaveException e) {
			String errorInsert = "Unable to Proceed,File Not Save in Disk.";
			errorList.add(errorInsert);	
		}
		
		if(errorList.size()==0){
			batchStatusUpdateConstant = BatchConstants.BATCH_SUCCESS;
		}else{
			 batchStatusUpdateConstant = BatchConstants.BATCH_FAILED;
		}
		
		try {
			batchHelper.updateBatchStatus(batchStatus, batchStatusUpdateConstant, applicationCache, errorList, noOfFiles, userSessionDetails);
		} catch (DatabaseException e) {			
			e.printStackTrace();
			logger.error("exception has occured,", e);
		}
	
	}
	
}
