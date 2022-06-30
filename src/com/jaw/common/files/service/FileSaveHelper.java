package com.jaw.common.files.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.jaw.batch.controller.BatchDataUploadVO;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.FileNotSaveException;

@Component
public class FileSaveHelper {
	static Logger logger = Logger.getLogger(FileSaveHelper.class);
	public String getDestinationDirectory(String instId,
			String branchId,String userType,String linkId,String flmtOrFlht,String fileType) {		
		StringBuffer result = null;
		String type = null;
		if((userType!=null)&&(!userType.trim().equals(""))){			
			type = userType;
		}else{		
			type = fileType;
		}		
		TypeOfFileToSave fileToSave = TypeOfFileToSave.valueOf(type);
		
		switch(fileToSave){
		case BATCH:{
			 result = new StringBuffer("/").append("files").append("/").append(instId).append("/")
						.append(branchId).append("/").append("batch");		
			break;
		}
		
		case STF: case FL_STCR:  case FL_STBD:{
			if(flmtOrFlht.equals(ApplicationConstant.OLD_FILE_STORAGE)){
				result = new StringBuffer("/")
						.append("files").append("/").append(instId).append("/")
						.append(branchId).append("/").append(ApplicationConstant.CONFIGURED_PATH_OLD_FILE_STORAGE_STF).append(linkId);	
			
			}else{
				result = new StringBuffer(("/"))
						.append("files").append("/").append(instId).append("/")
						.append(branchId).append("/").append(ApplicationConstant.CONFIGURED_PATH_PRS_STF).append(linkId);	
			}
				
			break;
		}
			
		case STU: case FL_BIRT: case FL_CAST: case FL_TRAN: case FL_MARK: case FL_MEDI: case FL_SPCR:{		
			if(flmtOrFlht.equals(ApplicationConstant.OLD_FILE_STORAGE)){
				result = new StringBuffer(("/"))
						.append("files").append("/").append(instId).append("/")
						.append(branchId).append("/").append(ApplicationConstant.CONFIGURED_PATH_OLD_FILE_STORAGE_STU).append(linkId);		
			}else{
				result = new StringBuffer(("/"))
						.append("files").append("/").append(instId).append("/")
						.append(branchId).append("/").append(ApplicationConstant.CONFIGURED_PATH_PRS_STU).append(linkId);		
			}
			
			break;
		}
			
		default:
			logger.debug("No file type to save the file has found");
		
		}					
		
		return result.toString();
	}
	
		
	public void saveMultipartToDisk(String directoryStructure,			
			MultipartFile multipartFile1) throws IllegalStateException, IOException, FileNotSaveException {
		
		File dir = new File(directoryStructure);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File multipartFile = new File(directoryStructure);	
		multipartFile1.transferTo(multipartFile);
		
		File file = new File(directoryStructure);
		if(!file.exists()){
			throw new FileNotSaveException();
		}
		
		
	}
	
	//Method to read the File Storage Location from Property file 
	public static String fileLocation( ServletContext servletContext) throws IOException{
		logger.info("Inside file location method");
		String filePath = ApplicationConstant.XML_FILE_LOCATION+ApplicationConstant.PROPERTY_FILE_FOR_FILE_STORAGE_NAME;
		Properties prop = new Properties();
		File file = new File(servletContext.getRealPath(filePath));
		InputStream is = new FileInputStream(file);
		prop.load(is);
		
		String propValue = prop.getProperty(ApplicationConstant.PROPERTY_IN_FILE_LOCATION_PROPFILE);
		logger.info("propvalue :"+propValue);
		return propValue;
		
	}
	

}
enum TypeOfFileToSave{
	STU,STF,BATCH,FL_BIRT,FL_CAST, FL_TRAN,FL_MARK,FL_MEDI,FL_SPCR,FL_STCR,FL_STBD;
}
