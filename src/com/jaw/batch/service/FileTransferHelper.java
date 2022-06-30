package com.jaw.batch.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.fileupload.DefaultFileItem;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jaw.batch.dao.BatchStatus;
import com.jaw.batch.dao.IFileTransferDBToDiskListDao;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.BatchConstants;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.files.dao.FileHistory;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.common.files.dao.IFileHistoryDao;
import com.jaw.common.files.dao.IFileMasterDao;
import com.jaw.common.files.service.FileSaveHelper;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
@Component
public class FileTransferHelper {
	
	Logger logger = Logger.getLogger(FileTransferHelper.class);
	
	@Autowired
	IFileTransferDBToDiskListDao fileTransferDBToDiskDao;
	@Autowired
	IFileMasterDao fileMasDao;
	@Autowired
	FileSaveHelper fileSaveHelper;
	@Autowired
	BatchHelper batchHelper;
	@Autowired
	DoAudit doAudit;
	@Autowired
	IFileHistoryDao fileHisDao;
	
	public void transferFileFromDBToDisk(final String INSTID,final String BRANCHID,final ServletContext SERVLETCONTEXT,
			final ApplicationCache APPLICATIONCACHE,final UserSessionDetails USERSESSIONDETAILS,final String BATCHSRLNO){
		
		logger.debug("Inside the Helper method,Transfer File From Database to Disk");
		final ArrayList<String> errorList = new ArrayList<String>();
		
		 final BatchStatus batchStatus = new BatchStatus();
		 batchStatus.setInstId(INSTID);
		 batchStatus.setBranchId(BRANCHID);
		Thread t = new Thread(new Runnable() {
			public void run() {		
				 String batchStatusUpdateConstant = "";
			//Step 1: Read the Key values from flmt using FileMaster Object excluding the Photos
			List<FileMaster> fileMasterObj = fileTransferDBToDiskDao.getFileObjectForTransfer(INSTID, BRANCHID,ApplicationConstant.DB_TO_DISK_CONSTANT);
			List<FileHistory> fileHisObj = fileTransferDBToDiskDao.getFileObjectForTransferFlht(INSTID, BRANCHID,ApplicationConstant.DB_TO_DISK_CONSTANT);
					
		
		//Step 2:For each file object the file operations will be done with transaction	
			if(fileMasterObj!=null){
				for(FileMaster fileMas : fileMasterObj){
					fileMas.setInstId(INSTID);				
						fileOpertaions(fileMas,null,SERVLETCONTEXT,USERSESSIONDETAILS);											
						}
			}
			
			if(fileHisObj!=null){
			for(FileHistory fileHis : fileHisObj){
				fileHis.setInstId(INSTID);				
					fileOpertaions(null,fileHis,SERVLETCONTEXT,USERSESSIONDETAILS);											
					}
			}
			
			
			
			
			//Step 6 : Functional Audit
			String remarks = "Batch Program Name :"+ApplicationConstant.BATCH_PGMS_FILE_IMPORT+","+
					"Data type :"+ApplicationConstant.FILE_IMPORT_DATA_TYPE+","+"Batch Serial No:"+BATCHSRLNO;
				
				try {
					doAudit.doFunctionalAudit(
							USERSESSIONDETAILS, AuditConstant.BATCH_PROGRAM_SUCCESS,
								remarks);
				} catch (DuplicateEntryException e1) {
					errorList.add("Unable to do Auditing,Please Contact Admin Immediately.");
					e1.printStackTrace();
				} catch (DatabaseException e1) {
					errorList.add("Auditing Failed,Please Contact Admin Immediately.");
					e1.printStackTrace();
				}	
			
			
			
			//Step 7: Update the Batch Status
			if(errorList.size()==0){
				batchStatusUpdateConstant = BatchConstants.BATCH_SUCCESS;
			}else{
				 batchStatusUpdateConstant = BatchConstants.BATCH_FAILED;
			}
			
			try {
				batchStatus.setDbTs(2);
				batchStatus.setBatchSrlNo(BATCHSRLNO);
				batchHelper.updateBatchStatus(batchStatus, batchStatusUpdateConstant, APPLICATIONCACHE, errorList, fileMasterObj.size(), USERSESSIONDETAILS);
			} catch (DatabaseException e) {			
				e.printStackTrace();
				logger.error("exception has occured,", e);
			}
			
			
			
					
			}		
			//}
		});
			
			
		t.start();		
		
		
	
	}
	
	@Transactional
	public void fileOpertaions(FileMaster fileMas,FileHistory fileHis,ServletContext servletContext,
			UserSessionDetails USERSESSIONDETAILS){
		List<String> errorList = new ArrayList<String>();
		//Step 3: Get the Single Rec details Completely	
		try{
			
			if(fileMas!=null){
		FileMaster fileMaster = fileMasDao.getSingleFileForFileTransfer(fileMas.getInstId(),fileMas.getBranchId(),
					fileMas.getLinkId(), fileMas.getFileType(),fileMas.getFileSrlno());		
		//Step 4: Move the File to Disk

		StringBuffer directoryStructure = new StringBuffer(fileSaveHelper.getDestinationDirectory(fileMaster.getInstId(), fileMaster.getBranchId(),
				"", fileMaster.getLinkId(),"",fileMaster.getFileType()));						
		if(!fileMas.getFileType().split("_")[0].equals(ApplicationConstant.PHOTO_CONSTANT)){
			String relativePath = (directoryStructure.append("/").append(fileMas.getFileRefno()).append(".jpeg")).toString();
			String dirPath = FileSaveHelper.fileLocation(servletContext).concat(relativePath);		
			logger.debug("File Location :"+dirPath);
				
			FileItem fileInt = new DiskFileItem("fileUpload", fileMaster.getContentType(), false, fileMaster.getFileName(), 
					fileMaster.getInputStream().available(), new File(dirPath));
			OutputStream outputStream = fileInt.getOutputStream();
			MultipartFile mulitpartFile = new CommonsMultipartFile(fileInt);
			int read = 0;
	        byte[] bytes = new byte[1024];
	        while ((read = fileMaster.getInputStream().read(bytes)) != -1) {
	            outputStream.write(bytes, 0, read);
	        }
	        File check = new File(dirPath);
	        if(!check.isFile()){
	        	fileSaveHelper.saveMultipartToDisk(dirPath,mulitpartFile);	
	        }
								
			fileMaster.setInputStream(null);	
			fileMaster.setFileName(fileMaster.getFileRefno()+".jpeg");
			fileMaster.setFilepath(relativePath);	
			fileMaster.setrModId(USERSESSIONDETAILS.getUserId());
		}																		
			
		
		//Step 5 :Update the flmt by making file as null & updating file name,file path
		fileMasDao.updateFlmtForFileTransfer(fileMaster);
		
			}else if(fileHis!=null){
				
				FileHistory fileHistory = fileHisDao.getSingleFileForFileTransfer(fileHis.getInstId(),fileHis.getBranchId(),
						fileHis.getLinkId(), fileHis.getFileType(),fileHis.getFileSrlno());
			
			//Step 4: Move the File to Disk

			StringBuffer directoryStructure = new StringBuffer(fileSaveHelper.getDestinationDirectory(fileHistory.getInstId(), fileHistory.getBranchId(),
					"", fileHistory.getLinkId(),ApplicationConstant.OLD_FILE_STORAGE,fileHistory.getFileType()));						
			if(!fileHis.getFileType().split("_")[0].equals(ApplicationConstant.PHOTO_CONSTANT)){
				
				String relativePath = (directoryStructure.append("/").append(fileHistory.getFileRefno()).append(".jpeg")).toString();
				String dirPath = FileSaveHelper.fileLocation(servletContext).concat(relativePath);	
				
				FileItem fileInt = new DiskFileItem("fileUpload", fileHistory.getContentType(), false, fileHistory.getFileName(), 
						fileHistory.getInputStream().available(), new File(dirPath));
				OutputStream outputStream = fileInt.getOutputStream();
				MultipartFile mulitpartFile = new CommonsMultipartFile(fileInt);
				int read = 0;
		        byte[] bytes = new byte[1024];
		        while ((read = fileHistory.getInputStream().read(bytes)) != -1) {
		            outputStream.write(bytes, 0, read);
		        }
		        File check = new File(dirPath);
		        if(!check.isFile()){
		        	fileSaveHelper.saveMultipartToDisk(dirPath,mulitpartFile);					
		        }
				
				
				
				
				
				
				
				/*String relativePath = (directoryStructure.append("/").append(fileHis.getFileRefno()).append(".jpeg")).toString();
				String dirPath = FileSaveHelper.fileLocation(servletContext).concat(relativePath);		
				logger.debug("File Location :"+dirPath);
				fileSaveHelper.saveMultipartToDisk(dirPath, (MultipartFile) fileHistory.getInputStream());		*/					
				fileHistory.setInputStream(null);	
				fileHistory.setFileName(fileHistory.getFileRefno()+".jpeg");
				fileHistory.setFilepath(relativePath);	
				fileHistory.setrCreId(USERSESSIONDETAILS.getUserId());
			}																		
				
			
			//Step 5 :Update the flmt by making file as null & updating file name,file path
			fileHisDao.updateFlhtForFileTransfer(fileHistory);
				}
		}catch (FileNotFoundInDatabase e) {
			errorList.add("File Not Found In Database,Please Contact Admin Immediately.");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			errorList.add("Illegal State Error,Please Contact Admin Immediately.");
		} catch (IOException e) {
			errorList.add("Input Output Error,Please Contact Admin Immediately.");
		} catch (FileNotSaveException e) {
			errorList.add("Unable to save the file,Please Contact Admin Immediately.");
		} catch (UpdateFailedException e) {
			errorList.add("Update of File in Database Failed,Please Contact Admin Immediately.");
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
	public void transferFileFromDiskToDB(final String INSTID,final String BRANCHID,final ServletContext SERVLETCONTEXT,
			final ApplicationCache APPLICATIONCACHE,final UserSessionDetails USERSESSIONDETAILS,final String BATCHSRLNO){
		
		logger.debug("Inside the Helper method,Transfer File From Disk to Database");
		final ArrayList<String> errorList = new ArrayList<String>();
		
		 final BatchStatus batchStatus = new BatchStatus();
		 batchStatus.setInstId(INSTID);
		 batchStatus.setBranchId(BRANCHID);
		Thread t = new Thread(new Runnable() {
			public void run() {		
				 String batchStatusUpdateConstant = "";
			//Step 1: Read the Key values from flmt using FileMaster Object excluding the Photos
			List<FileMaster> fileMasterObj = fileTransferDBToDiskDao.getFileObjectForTransfer(INSTID, BRANCHID,ApplicationConstant.DISK_TO_DB_CONSTANT);		
			List<FileHistory> fileHisObj = fileTransferDBToDiskDao.getFileObjectForTransferFlht(INSTID, BRANCHID,ApplicationConstant.DISK_TO_DB_CONSTANT);
			
		
		//Step 2:For each file object the file operations will be done with transaction		
			for(FileMaster fileMas : fileMasterObj){
				fileMas.setInstId(INSTID);				
				fileOpertaionsDiskToDB(fileMas,null,SERVLETCONTEXT);											
					}
			
			for(FileHistory fileHis : fileHisObj){
				fileHis.setInstId(INSTID);				
				fileOpertaionsDiskToDB(null,fileHis,SERVLETCONTEXT);											
					}
			
			
			
			
			//Step 6 : Functional Audit
			String remarks = "Batch Program Name :"+ApplicationConstant.BATCH_PGMS_FILE_IMPORT+","+
					"Data type :"+ApplicationConstant.FILE_IMPORT_DATA_TYPE+","+"Batch Serial No:"+BATCHSRLNO;
				
				try {
					doAudit.doFunctionalAudit(
							USERSESSIONDETAILS, AuditConstant.BATCH_PROGRAM_SUCCESS,
								remarks);
				
				} catch (DuplicateEntryException e1) {
					errorList.add("Unable to do Auditing,Please Contact Admin Immediately.");
					e1.printStackTrace();
				} catch (DatabaseException e1) {
					errorList.add("Auditing Failed,Please Contact Admin Immediately.");
					e1.printStackTrace();
				}	
			
			
			
			//Step 7: Update the Batch Status
			if(errorList.size()==0){
				batchStatusUpdateConstant = BatchConstants.BATCH_SUCCESS;
			}else{
				 batchStatusUpdateConstant = BatchConstants.BATCH_FAILED;
			}
			
			try {
				batchStatus.setBatchSrlNo(BATCHSRLNO);
				batchStatus.setDbTs(2);
				batchHelper.updateBatchStatus(batchStatus, batchStatusUpdateConstant, APPLICATIONCACHE, errorList, fileMasterObj.size(), USERSESSIONDETAILS);
			} catch (DatabaseException e) {			
				e.printStackTrace();
				logger.error("exception has occured,", e);
			}
			
			
			
					
			}		
			//}
		});
			
			
		t.start();		
		
		
	
	}
	
	@Transactional
	public void fileOpertaionsDiskToDB(FileMaster fileMas,FileHistory fileHis,ServletContext servletContext) /*throws FileNotFoundInDatabase, IOException, IllegalStateException, FileNotSaveException, UpdateFailedException*/{
			
		List<String> errorList = new ArrayList<String>();
		String filePath = null;
		//Step 3: Get the Single Rec details Completely	
		try{
			
			if(fileMas!=null){				
				InputStream imagefile = null;
		FileMaster fileMaster = fileMasDao.getSingleFileForFileTransfer(fileMas.getInstId(),fileMas.getBranchId(),
					fileMas.getLinkId(), fileMas.getFileType(),fileMas.getFileSrlno());	
		//Step 4: Move the File to DB

		/*StringBuffer directoryStructure = new StringBuffer(fileSaveHelper.getDestinationDirectory(fileMaster.getInstId(), fileMaster.getBranchId(),
				"", fileMaster.getLinkId(),"",fileMaster.getFileType()));		*/				
		if(!fileMas.getFileType().split("_")[0].equals(ApplicationConstant.PHOTO_CONSTANT)){
			filePath = FileSaveHelper.fileLocation(servletContext)+fileMaster.getFilepath();
			imagefile = new FileInputStream(filePath);
			fileMaster.setInputStream(imagefile);		
			fileMaster.setFileName("");
			fileMaster.setFilepath("");
			
							
		}																		
			
		
		//Step 5 :Update the flmt by making file as file Object & updating file name,file path as empty
		fileMasDao.updateFlmtForFileTransfer(fileMaster);
		if(imagefile!=null){
			imagefile.close();	
		}
		
		if(filePath!=null){
			File file = new File(filePath);
			file.delete();
		}
		
		
			}else if(fileHis!=null){							
				InputStream imagefile = null;
				FileHistory fileHistory = fileHisDao.getSingleFileForFileTransfer(fileHis.getInstId(),fileHis.getBranchId(),
						fileHis.getLinkId(), fileHis.getFileType(),fileHis.getFileSrlno());			
			//Step 4: Move the File to DB
			
			if(!fileHis.getFileType().split("_")[0].equals(ApplicationConstant.PHOTO_CONSTANT)){
				filePath =FileSaveHelper.fileLocation(servletContext)+fileHistory.getFilepath();
				imagefile = new FileInputStream(filePath);
				fileHistory.setInputStream(imagefile);			
				fileHistory.setFileName("");
				fileHistory.setFilepath("");				
			}																		
				
			
			//Step 5 :Update the flmt by making file as null & updating file name,file path		
			fileHisDao.updateFlhtForFileTransfer(fileHistory);
			
			File file = new File(FileSaveHelper.fileLocation(servletContext)+fileHistory.getFilepath());		
			if(imagefile!=null){
				imagefile.close();	
			}		
			if(filePath!=null){
				File fil = new File(filePath);
				fil.delete();
				
			}			
				}
		}catch (FileNotFoundInDatabase e) {
			errorList.add("File Not Found In Database,Please Contact Admin Immediately.");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			errorList.add("Illegal State Error,Please Contact Admin Immediately.");
			e.printStackTrace();
		} catch (IOException e) {
			errorList.add("Input Output Error,Please Contact Admin Immediately.");
			e.printStackTrace();
		} catch (UpdateFailedException e) {
			errorList.add("Update of File in Database Failed,Please Contact Admin Immediately.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			//errorList.add("Delete of file in Disk Failed,Please Contact Admin Immediately.");
		}
		
		
		
		
		
	}

}
