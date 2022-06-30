package com.jaw.batch.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jaw.batch.controller.BatchDataUploadVO;
import com.jaw.batch.dao.BatchStatus;
import com.jaw.batch.dao.BatchStatusKey;
import com.jaw.batch.dao.IBatchOperationsCommonDao;
import com.jaw.batch.dao.IBatchStatusDao;
import com.jaw.common.batch.pojo.Field;
import com.jaw.common.batch.pojo.RecordFormat;
import com.jaw.common.batch.pojo.XmlConfigurationForUpload;
import com.jaw.common.batch.util.ExcelValidation;
import com.jaw.common.batch.util.ExcelValidation.ClassNames;
import com.jaw.common.batch.util.ExcelValidationUtil;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.BatchConstants;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.core.dao.IStudentGroupMasterListDAO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.dao.StudentGroupMasterKey;
import com.jaw.core.dao.StudentGroupMasterListKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.CommunicationDetailsVO;
import com.jaw.student.admission.controller.ParentDetailsVO;
import com.jaw.student.admission.controller.PrevAcademicDetailsVO;
import com.jaw.student.admission.controller.SiblingDetailsVO;
import com.jaw.student.admission.controller.StudentInfoVO;
import com.jaw.student.admission.controller.StudentMasterVO;
import com.jaw.student.admission.controller.TransportDetailsVO;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.user.controller.UserVO;
import com.jaw.user.dao.UserLink;

@Component
public class BatchHelper {
	Logger logger = Logger.getLogger(BatchHelper.class);
		
	@Autowired
	ExcelValidation excelValidationUtil;
	@Autowired
	ExcelValidationUtil excelDataValidations;	
	@Autowired
	CommonBusiness commonBusiness;				
	@Autowired
	IBatchStatusDao batchStatusDao;	
	@Autowired
	IBatchOperationsCommonDao batchOpCommonDao;
	@Autowired
	BatchInsertHelper batchInsertHelper;
	@Autowired
	XMLHelper xmlHelper;	
	@Autowired
	IStudentGroupMasterListDAO studentGroupMasterListDAO;

	public String fileToObject(final String fileExtn,
			final BatchDataUploadVO importDataVO,
			final ApplicationCache applicationCache,
			final ServletContext servletContext,
			final UserSessionDetails userSessionDetails, final String dataType,final String[] xmlNames,final String[] sheetNames,final BatchStatus batchStatus,final String batchSerialNo)
			throws IOException, NoRecordFoundException {
		
		final ArrayList<String> errorList = new ArrayList<String>();		
		final InputStream excelInputStream = importDataVO.getUploadFile()
				.getInputStream();

		
		/*************************
		 * Import data steps
		 *  
		 * Step 1: Read the corresponding xmls.
		 * 		
		 * 
		 * Step 2: Read the Excel sheet header and start initial validations like Empty Excel,Sheet Name validation, Header validation,Mandatory column check,Unnecessary column Check, 
		 * 		   proceed only if there is no error.
		 *  
		 * Step 3: Get unique properties map(hashmap of sheet no and volist - volist is again a	 hashmap of voname, propertyname arraylist, columnname arraylist)
		 * 			and also form cache(from crsm table) to validate the 'course name',form cache(from stgm table) for adding studentgrpid for stum records. 
		 * 		  
		 * 'for' loop (sheet index) - start
		 *  
		 * Step 4: Get the unique values of classname property and get the classNameListForComparison(like
		 * 	       studentmastervo, studentinfovo) based on xml
		 *  
		 * Step 5: Create Object equivalent to no. of records in a sheet and form Object List
		 * 
		 * Step 6: Use the Object list formed in the prevoius Step and put it hashmap (Vo name as key and object list as value)
		 *  
		 * Step 7: Validate the default values for the columns which are not given in the Excel(but it takes the values from XML)
		 * 
		 * Step 8: If there is no error in previous step,the process will start to read excel,validate it & 
		 * 			assign the values into the object list formed in the Step 5 with the help of HashMap formed in the Step 6,
   		 *			if there is any error it will add in error List 
   		 *
   		 * Step 8.1: Add Corresponding values for student group id using course,term and section,Logic for parent id and student name has been included.
   		 *		
		 * for loop (sheet index) - end
		 * 		
		 * Step 9: Validate for unique constraints for all sheets,use the unique properties Map formed in step 3
		 * 		1. within excel data - unique constraint check 
		 * 		2. unique constraint check with database - this is required only if the table is not empty
		 * 
		 * Step 10_1: Insert data under transaction scope if there is no error
		 * 		1. Insert data for all sheets from object maps 
		 * 		2. Insert funcational audit for success
		 * 		3. Update the status in Batchstatus table for success
		 * 
		 * Step 10_2: If there is any error,(transaction scope) 		 
		 * 		1. Insert funcational audit for failure
		 * 		2. Update the status in Batchstatus table for failure
		 * 
		 * 
		 * 
		 * 
		 * 
		 * For Adding new DataType,the following has to be changed,
		 * 
		 * 1.Add XML in WEB-INF/properties/ for the new datatype.
		 * 
		 * 2.com.jaw.batch.service.BatchHelper.java:
		 *    -->Before starting of Step 3,initialize the Object which we want to insert.
		 *    -->Step 5 and Step 6-Add necessary Objects.
		 *    
		 * 3.com.jaw.batch.service.XMLHelper.java:
		 * 	  -->Add table name constants for the respective Object in hashMapForTablConstant() method.	
		 * 	
		 * 4.com.jaw.batch.service.BatchInsertHelper.java
		 * 	  -->Include repective database call 'case' in retriveListOfRecForDuplicateCheck() method.
		 * 
		 * 5.com.jaw.common.batch.util.ExcelValidation.java		 
		 * 	  -->Add your corresponding data for the enum DataType,ClassNames,TableNames
		 * 
		 * 6.com.jaw.common.batch.constants.BatchConstants
		 *   -->Add constant for your class and tbl names.
		 *  	
		 * 
		 * 								
		 **************************************/												
		Thread t = new Thread(new Runnable() {
			public void run() {

			
				ArrayList<String> sheetName = new ArrayList<String>();				
				ArrayList<Object> studentMasterList = new ArrayList<Object>();				
				ArrayList<Object> studentInfoVOList = new ArrayList<Object>();
				ArrayList<Object> prevAcademicDetailsList = new ArrayList<Object>();
				ArrayList<Object> parentDetailsList = new ArrayList<Object>();
				ArrayList<Object> communicationDetailsList = new ArrayList<Object>();
				ArrayList<Object> siblingDetailsList = new ArrayList<Object>();
				ArrayList<Object> transDetailsList = new ArrayList<Object>();
				ArrayList<Object> userLinkDetailsList = new ArrayList<Object>();
				ArrayList<Object> userList = new ArrayList<Object>();
				List<List<String>> listOfColumnNameFormXml = new ArrayList<List<String>>();
				List<RecordFormat> listOfXMLRecFormat = new ArrayList<RecordFormat>();
				List<List<String>> listOfHeaderRow = new ArrayList<List<String>>();		
				List<Map<String, ArrayList<Object>>> listOfObjectMaps = new ArrayList<Map<String, ArrayList<Object>>>();
				List<Integer> noOfRecInEachSheet = new ArrayList<Integer>();
				RecordFormat recordFormat = null;
				Map<Integer, Map<String, ArrayList<ArrayList<String>>>> uniquePropertiesWithSheetNo = new HashMap<Integer, Map<String, ArrayList<ArrayList<String>>>>();
				HashMap<String,String> courseMasterCache = new HashMap<String,String>();
				Map<String,Map<String,String>> sectionCache = new LinkedHashMap<String,Map<String,String>>();
				Map<String,Map<String,Map<String,String>>> allSubLists = new LinkedHashMap<String,Map<String,Map<String,String>>>();
				HashMap<String,String> courseVariantCheckCache = new HashMap<String,String>();
				HashMap<String,String> stgmCache = new HashMap<String,String>();
				ArrayList<String> errorListForSheetAndHeader = new ArrayList<String>();
				Integer sheetCount = 0;				
				ArrayList<Integer> columnCountForSheetFromExcel = new ArrayList<Integer>();
				Workbook workbook = null;

				// Thread: Step 1: Read from XML - Start
				logger.info("Started to read XML files");
				try {
					InputStream fileInput = null;
					for (int index = 0; index < xmlNames.length; index++) {
						String xmlName = xmlNames[index];					
						String fileName = ApplicationConstant.XML_FILE_LOCATION
								.concat(xmlName);						
						fileInput = servletContext
								.getResourceAsStream(fileName);
						JAXBContext jaxbContext = JAXBContext
								.newInstance(com.jaw.common.batch.pojo.XmlConfigurationForUpload.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext
								.createUnmarshaller();
						XmlConfigurationForUpload xmlClassObject = (XmlConfigurationForUpload) jaxbUnmarshaller
								.unmarshal(fileInput);
						recordFormat = xmlClassObject.getRecordFormat();
						listOfXMLRecFormat.add(recordFormat);
					}
					//Step 1 >> End.										
					// Thread: Step 2: Read the Excel sheet header and start initial validations 
					//like Empty Excel,Sheet Name validation, Header validation,Mandatory column check,Unnecessary column Check  - Start
					
					workbook = WorkbookFactory.create(excelInputStream);
					HSSFSheet sheet = null;

					logger.info("Started to Validate Excel file");

					// Validation-Checking the Excel is Empty,Appropriate
					// sheet name
					// and for Proper Header
					if (workbook.getSheetAt(0).getPhysicalNumberOfRows() > 0) {
						sheetCount = workbook.getNumberOfSheets();
						errorListForSheetAndHeader = excelValidationUtil
								.initialSheetValidations(listOfXMLRecFormat,
										listOfColumnNameFormXml, sheetNames,
										workbook, listOfHeaderRow,
										columnCountForSheetFromExcel,
										workbook.getNumberOfSheets());
						
					

					} else {
						String errorMessage = "Sheet Name :" + " " + ","
								+ "Record No:" + " " + "," + "Column Name :"
								+ " " + " Cannot Proceed,Empty Excel File..!;";
						errorListForSheetAndHeader.add(errorMessage);
					}

					
					//Step 2 >> End,If error List is empty,it proceed otherwise it won't
					
					// if the error list is not empty the process won't
					// continue,otherwise it will proceed for cell validation
					
					if (!errorListForSheetAndHeader.isEmpty()) {
						
							errorList.addAll(errorListForSheetAndHeader);
							noOfRecInEachSheet.add(0);

					} else {
						
					
						logger.info("Started to validate individual sheet cells");
						
						
						HashMap<String,String> voNameAndTblNameMap = xmlHelper.hashMapForTablConstant();	
						
						
						StudentMasterVO studentMaster = null;					
						StudentInfoVO studentInfo = null;
						PrevAcademicDetailsVO prevAcademicDetails = null;
						ParentDetailsVO parentDetails = null;
						CommunicationDetailsVO communicationDetails = null;
						SiblingDetailsVO siblingDetails = null;
						TransportDetailsVO transportVO = null;
						UserLink userLink = null;
						UserVO uservo = null;
					
						List<String> classNameListForComparison = null;
					
						//Step 3 :Get unique properties map(hashmap of sheet no and volist - volist is again a	 hashmap of voname, propertyname arraylist, columnname arraylist)
						
						uniquePropertiesWithSheetNo = xmlHelper
								.listOfUniquePropListForEachSheet(
										listOfXMLRecFormat, importDataVO,
										sheetCount);
						courseMasterCache =	excelDataValidations.courseMasterCahce(userSessionDetails.getInstId(),importDataVO.getBranchId());
						
						StudentGroupMasterListKey groupMasterKey = new StudentGroupMasterListKey();
						groupMasterKey.setInstId(userSessionDetails.getInstId());						
						groupMasterKey.setBranchId(importDataVO.getBranchId());						
						List<StudentGroupMaster> listOfStGrpMas = studentGroupMasterListDAO.selectGroupMasterList(groupMasterKey);
						for(StudentGroupMaster stuGrpMas:listOfStGrpMas){
							String stgmKey = stuGrpMas.getCourseMasterId().trim()+stuGrpMas.getTermId().trim()+stuGrpMas.getSecId().trim();
							stgmCache.put(stgmKey, stuGrpMas.getStudentGrpId());
						}
						
						
						//Forming cache for sec validation
						sectionCache = excelDataValidations.sectionCahce(userSessionDetails.getInstId(), importDataVO.getBranchId());
						System.out.println("section cache in helper :"+sectionCache);
						
						//Forming cache for Subject Validations
						 allSubLists = excelDataValidations.subjectCahce(userSessionDetails.getInstId(), importDataVO.getBranchId());
						 System.out.println("subject cache in helper :"+allSubLists);
						 
						 //Forming cache for course variant validations
						 courseVariantCheckCache = excelDataValidations.courseVariantCahce(userSessionDetails.getInstId(), importDataVO.getBranchId());
						 System.out.println("courseVariantCheckCache cache in helper :"+courseVariantCheckCache);
						
						
						
						//Step 3 >> End.
						
						//for loop (sheet index) - start
						for (int sheetIndexValue = 0; sheetIndexValue < sheetCount; sheetIndexValue++) {
							
							Map<String, ArrayList<Object>> objectMap = new HashMap<String, ArrayList<Object>>();
							
							// Get the single sheet and noOfRows in it
							sheet = (HSSFSheet) workbook
									.getSheetAt(sheetIndexValue);
							sheetName.add(sheet.getSheetName());
							Integer noOfRecordsInExcel	= sheet.getLastRowNum();
							noOfRecInEachSheet.add(noOfRecordsInExcel);

							// Get the list of classes from Xml when there
							// noOfRows not equal to zero			
							
							//Step 4: Get the unique values of classname property and get the classNameListForComparison(like
							//		  studentmastervo, studentinfovo) based on xml
							
								classNameListForComparison = xmlHelper.getClassNameListFromXML(sheetIndexValue, listOfXMLRecFormat);	
								
								//Step 4 >> End.
								
								
								//Step 5 : Create Object equivalent to no. of records in a sheet and form Object List
										for (String classNameFromList : classNameListForComparison) {
											
												for (int noOfRows = 0; noOfRows < sheet.getLastRowNum(); noOfRows++) {
												
														ClassNames className = ClassNames.valueOf(classNameFromList);
														
														switch (className) {
															case StudentMasterVO: {
																	studentMaster = new StudentMasterVO();
																	studentMasterList.add(studentMaster);
															break;
															}
															case StudentInfoVO: {
																	studentInfo = new StudentInfoVO();
																	studentInfoVOList.add(studentInfo);
															break;
															}
															case PrevAcademicDetailsVO: {
																	prevAcademicDetails = new PrevAcademicDetailsVO();
																	prevAcademicDetailsList.add(prevAcademicDetails);
															break;
															}
															case ParentDetailsVO: {
																	parentDetails = new ParentDetailsVO();
																	parentDetailsList.add(parentDetails);
															break;
															}
															case CommunicationDetailsVO: {
																	communicationDetails = new CommunicationDetailsVO();
																	communicationDetailsList.add(communicationDetails);
															break;
															}
															case SiblingDetailsVO: {
																siblingDetails = new SiblingDetailsVO();
																siblingDetailsList.add(siblingDetails);
															break;
														    }
															case TransportDetailsVO: {
																transportVO = new TransportDetailsVO();
																transDetailsList.add(transportVO);
																break;
														    }
															case UserLink: {
																userLink = new UserLink();
																userLinkDetailsList.add(userLink);
															break;
														    }
															case UserVO: {
																uservo = new UserVO();
																userList.add(uservo);
															break;
														    }
															
		
												}
											}
											
										}
										
									//Step 5 >> End.

								
								//Step 6 : Use the Object list formed in the prevoius Step and put it hashmap (Vo name as key and object list as value) -Start
								for (int index = 0; index < classNameListForComparison.size(); index++) {
									
											ClassNames className = ClassNames.valueOf(classNameListForComparison.get(index));
											
											switch (className) {
													case StudentMasterVO: {
														objectMap.put(BatchConstants.BATCH_STUDENTMASTER_OBJ,studentMasterList);
													break;
													}
													case StudentInfoVO: {
														objectMap.put(BatchConstants.BATCH_STUDENTINFO_OBJ,studentInfoVOList);
													break;
													}
													case PrevAcademicDetailsVO: {
														objectMap.put(BatchConstants.BATCH_PREVACADEMICDETAILS_OBJ,prevAcademicDetailsList);
													break;
													}
													case ParentDetailsVO: {
														objectMap.put(BatchConstants.BATCH_PARENTDETAILS_OBJ,parentDetailsList);
													break;
													}
													case CommunicationDetailsVO: {
														objectMap.put(BatchConstants.BATCH_COMMDETAILS_OBJ,communicationDetailsList);
													break;
													}
													case SiblingDetailsVO: {
														objectMap.put(BatchConstants.BATCH_SIBLINGDETAILS_OBJ,siblingDetailsList);
														break;
												    }
													case TransportDetailsVO: {
														objectMap.put(BatchConstants.BATCH_TRANSDETAILS_OBJ,transDetailsList);
														break;
												    }
													
													case UserLink: {
														objectMap.put(BatchConstants.BATCH_USERLINKDETAILS_OBJ,userLinkDetailsList);
													break;
												    }
													case UserVO: {
														objectMap.put(BatchConstants.BATCH_USER_OBJ,userList);
													break;
												    }
		
											}
		
										}
								//Step 6 >> End.
										
								
								
								//Step 7 : Validate the default values for the columns which are not given in the Excel(but it takes the values from XML) - Start
										ArrayList<String> errorListFromDefaultValueValidation = xmlHelper.validateDefaultValuesinXML(listOfColumnNameFormXml, 
												sheetIndexValue, listOfHeaderRow, listOfXMLRecFormat, noOfRecordsInExcel, 
												sheet, errorList, applicationCache, importDataVO.getInstId(),importDataVO.getBranchId(), voNameAndTblNameMap,objectMap,courseMasterCache);					
								// Step 7 >> End.
										
								
								//Step 8 : If there is no error in previous step,the process will start to read excel,validate it & 
								//assign the values into the object list formed in the Step 5 with the help of HashMap formed in the Step 6,
								//if there is any error it will add in error List - Start
																		System.out
																				.println("sheet values :"+sheet.getRow(1).getCell(15));																											
											if(errorListFromDefaultValueValidation.size()==0){												
													// Started to read the excel column X Row wise											
													for (int columnIndex = 1; columnIndex < columnCountForSheetFromExcel.get(sheetIndexValue); columnIndex++) {
																					
														// read excel values and validate for one
														// sheet at a time
														readExcelAndAssignValuesToObject(
																columnCountForSheetFromExcel,
																sheetIndexValue, listOfHeaderRow,
																listOfColumnNameFormXml,
																listOfXMLRecFormat, sheet,
																columnIndex, errorList, objectMap,
																applicationCache,importDataVO.getInstId(),importDataVO.getBranchId()
																,voNameAndTblNameMap,courseMasterCache);													
													
												    }																				
											}else{
												
													 errorList.addAll(errorListFromDefaultValueValidation);
											}
										
																	
										 listOfObjectMaps.add(objectMap);											
						}						
						//Step 8 >> End.
						
						//Step :8.1
						
						
						
					
					
						
						//Adding StudentGrpId for stum table enteries
						for(Map<String, ArrayList<Object>> objectMaps :listOfObjectMaps){
							
							ArrayList<Object> listOfstum = objectMaps.get(BatchConstants.BATCH_STUDENTMASTER_OBJ);
							ArrayList<Object> listOfpard = objectMaps.get(BatchConstants.BATCH_PARENTDETAILS_OBJ);	
							ArrayList<Object> listOfstin = objectMaps.get(BatchConstants.BATCH_STUDENTINFO_OBJ);	
							
							
							//Validating the section from stgm and cocd table
							if(listOfstum!=null){
								for(Integer index=0;index<listOfstum.size();index++){	
									Integer recIndex = index;
									String section = ExcelValidation.get(listOfstum.get(index), "sec");	
									String term = ExcelValidation.get(listOfstum.get(index), "standard");
									String course = ExcelValidation.get(listOfstum.get(index), "course");	
									
									Map<String,String> scetionList = sectionCache.get(term.trim()+course.trim());
									if(scetionList!=null){
									if(scetionList.containsValue(section)){
										String secId = scetionList.get(section);
										ExcelValidation.set(listOfstum.get(index), "sec", secId.toString().trim());	
									}else{

										String errorMessage = "Section for the record :"
												+ (recIndex+1)
												+ " is Invalid."
												+"Section is not found for Term:"
												+ term
												+ ","
												+ "Course :"
												+ course+";";																						
										errorList.add(errorMessage);
									
									}
									}else{
										String errorMessage = "Section for the record :"
												+ (recIndex+1)
												+ " is Invalid for "
												+"Term:"
												+ term
												+ ","
												+ "Course :"
												+ course+";";																						
										errorList.add(errorMessage);
									}
									
								}
								
							}
							
							
							
							
							
							
							
							//Adding StudentgrpId
							if(listOfstum!=null){
							for(Integer index=0;index<listOfstum.size();index++){	
								Integer recindex = index;
								String secValue = ExcelValidation.get(listOfstum.get(index), "sec");						
								if((!secValue.equals(null))&&(!secValue.trim().equals(""))){
									String courseValue = ExcelValidation.get(listOfstum.get(index), "course");
									String trmValue = ExcelValidation.get(listOfstum.get(index), "standard");									
								String stuGrpIdvalue = 	stgmCache.get(courseValue.trim()+trmValue.trim()+secValue.trim());	
								if((stuGrpIdvalue!=null)&&(!stuGrpIdvalue.trim().equals(""))){
									ExcelValidation.set(listOfstum.get(index), "stuGrpId", stuGrpIdvalue);
								}else{
																	
									String errorMessage = "Student Group for Course :"
											+ courseValue
											+ ", for Record : "
											+(recindex+1)
											+ "Term :"
											+ trmValue
											+ ","
											+ "Section :"
											+ secValue											
											+ " is not found;";	
									errorList.add(errorMessage);
								}
																	
								}
							}
							}
							
						
							//FirstName,Middle Name & LastName,Gender logic					
						if((listOfstum!=null)&&(listOfstin!=null)){
							for(Integer index=0;index<listOfstin.size();index++){										
								String firstName = ExcelValidation.get(listOfstin.get(index), "firstName");		
								String middleName = ExcelValidation.get(listOfstin.get(index), "middleName");
								String lastName = ExcelValidation.get(listOfstin.get(index), "lastName");
							//	String gender = ExcelValidation.get(listOfstum.get(index), "gender");		
								StringBuffer studentName = new StringBuffer(firstName).append(" ").append(middleName).append(" ").append(lastName);
								if((!studentName.toString().trim().equals(null))&&(!studentName.toString().trim().equals(""))){									
									ExcelValidation.set(listOfstum.get(index), "studentName", studentName.toString().trim());																														
								}
							/*	if((!gender.trim().equals(null))&&(!gender.trim().equals(""))){									
									ExcelValidation.set(listOfstin.get(index), "gender", gender.trim());																														
								}*/
							}
							
						}
						
						//Parent id logic
						if((listOfstum!=null)&&(listOfpard!=null)){
							for(Integer index=0;index<listOfstum.size();index++){										
								String stuAdmisNo = ExcelValidation.get(listOfstum.get(index), "studentAdmisNo");						
								if((!stuAdmisNo.equals(null))&&(!stuAdmisNo.trim().equals(""))){									
									ExcelValidation.set(listOfpard.get(index), "parentId", stuAdmisNo);																														
								}
							}
							
						}	
						
						
						
						//Validating Languages
						if(listOfstum!=null){
							for(Integer index=0;index<listOfstum.size();index++){	
								Integer recIndex = index;
								String secLang = ExcelValidation.get(listOfstum.get(index), "secoundLang");	
								System.out.println("secLang :"+secLang);
								String thirdLang = ExcelValidation.get(listOfstum.get(index), "thirdLang");
								String ele1 = ExcelValidation.get(listOfstum.get(index), "elective1");
								String ele2 = ExcelValidation.get(listOfstum.get(index), "elective2");	
								String term = ExcelValidation.get(listOfstum.get(index), "standard");
								String course = ExcelValidation.get(listOfstum.get(index), "course");	
								
								Map<String, Map<String, String>> subListWithType = allSubLists.get(term.trim()+course.trim());
								if((subListWithType!=null)){
									//For Second Lang
									Map<String,String> subListL2 = subListWithType.get(ApplicationConstant.SUB_TYPE_LANGUAGE2);
									Map<String,String> subListL3 = subListWithType.get(ApplicationConstant.SUB_TYPE_LANGUAGE3);
									Map<String,String> subListE1 = subListWithType.get(ApplicationConstant.SUB_TYPE_ELECTIVE1);
									Map<String,String> subListE2 = subListWithType.get(ApplicationConstant.SUB_TYPE_ELECTIVE2);
									
									if(subListL2!=null){
										System.out.println("term and course is theree...");
										Boolean isLangThere = false;
										String langCode = "";								
										if((secLang!=null)&&(!secLang.equals(""))){
											System.out.println("secLang is not null or empty");
										for (Entry<String, String> entry : subListL2.entrySet()) {
									        if (secLang.equals(entry.getValue())) {
									        	System.out
														.println("lang iws there....");
									        	isLangThere = true;
									        	langCode =  entry.getKey();
									        	
									        }
									    }
										if(isLangThere){
											System.out.println("gng to set the values :"+langCode);
											ExcelValidation.set(listOfstum.get(index), "secoundLang",langCode);	
										}else{			
											System.out.println("gng to set error msg");
													String errorMessage = "Second Language Choosen for Record :"
															+ (recIndex+1)
															+" with course : "
															+course
															+ " is Wrong."
															+"Please Change it and Upload;";																															
													errorList.add(errorMessage);																						
										        }
										
										
										
										
										}
										
										/*if((langIsThere==false)){
											
										}*/
										
																	
										
									}else if((subListL2==null)&&(!secLang.equals(""))){

										String errorMessage = "Second Language for the record :"
												+ (recIndex+1)
												+ " is Not Applicable."
												+"Please Remove it and Upload;";																															
										errorList.add(errorMessage);
									
									}
									
									if(subListL3!=null){
									

										
										for (Entry<String, String> entry : subListL3.entrySet()) {
									        if (thirdLang.equals(entry.getValue())) {
									        	ExcelValidation.set(listOfstum.get(index), "thirdLang", thirdLang.trim());	
									        }else if(!thirdLang.equals("")){
												String errorMessage = "Third Language Choosen for Record :"
														+ (recIndex+1)
														+ " is Not Applicable."
														+"Please Change it and Upload;";																															
												errorList.add(errorMessage);																						
									        }
									    }
																												
										
									
										
									}else if((subListL3==null)&&(!thirdLang.equals(""))){

										String errorMessage = "Third Language for the record :"
												+ (recIndex+1)
												+ " is Not Applicable."
												+"Please Remove it and Upload;";																															
										errorList.add(errorMessage);
									
									}
									
									if(subListE1!=null){
										
										for (Entry<String, String> entry : subListE1.entrySet()) {
									        if (ele1.equals(entry.getValue())) {
									        	ExcelValidation.set(listOfstum.get(index), "elective1", ele1.trim());	
									        }else if(!ele1.equals("")){
												String errorMessage = "Elective 1 Choosen for Record :"
														+ (recIndex+1)
														+ " is Not Applicable."
														+"Please Change it and Upload;";																															
												errorList.add(errorMessage);																						
									        }
									    }
																												
										
										
										
									}else if((subListE1==null)&&(!ele1.equals(""))){

										String errorMessage = "Elective 1 for the record :"
												+ (recIndex+1)
												+ " is Not Applicable."
												+"Please Remove it and Upload;";																															
										errorList.add(errorMessage);
									
									}
									
									if(subListE2!=null){

										
										for (Entry<String, String> entry : subListE2.entrySet()) {
									        if (ele2.equals(entry.getValue())) {
									        	ExcelValidation.set(listOfstum.get(index), "elective2", ele2.trim());	
									        }else if(!ele2.equals("")){
												String errorMessage = "Elective 2 Choosen for Record :"
														+ (recIndex+1)
														+ " is Not Applicable."
														+"Please Change it and Upload;";																															
												errorList.add(errorMessage);																						
									        }
									    }
																																																										
									
										
									}else if((subListE2==null)&&(!ele2.equals(""))){

										String errorMessage = "Elective 2 for the record :"
												+ (recIndex+1)
												+ " is Not Applicable."
												+"Please Remove it and Upload;";																															
										errorList.add(errorMessage);
									
									}
																
								}/*else{
									String errorMessage ="Course or Term for the record :"
											+ (recIndex+1)
											+ " is Invalid, "
											+"Term:"
											+ term
											+ ","
											+ "Course :"
											+ course+";";																						
									errorList.add(errorMessage);
								}*/
								
							}
							
						}
						
						//Validating the course variant
						if(listOfstum!=null){							
							for(Integer index=0;index<listOfstum.size();index++){	
								Integer recIndex = index;
								//String section = ExcelValidation.get(listOfstum.get(index), "sec");	
								String courseVariant = ExcelValidation.get(listOfstum.get(index), "courseVariant");
								String course = ExcelValidation.get(listOfstum.get(index), "course");	
								
								if((courseVariantCheckCache.get(course)!=null)&&((courseVariant!=null)&&(!courseVariant.equals("")))){
									/*	if(courseVariantCheckCache.get(course).equals(ApplicationConstant.COURSE_VARIANT_APPLICABLE_YES)){
										
										try{
											System.out.println("inst id :"+importDataVO.getInstId());
											System.out.println("branch id :"+importDataVO.getBranchId());
											System.out.println("TableNameConstant.COURSE_VARIANT_COL_NAME :"+TableNameConstant.COURSE_VARIANT_COL_NAME);
											System.out.println("TableNameConstant.STUDENT_MASTER :"+TableNameConstant.STUDENT_MASTER);
											System.out.println("courseVariant :"+courseVariant);
											
									String code = 	excelValidationUtil.cocdValidation(importDataVO.getInstId(),
												importDataVO.getBranchId(), TableNameConstant.COURSE_VARIANT_COL_NAME, TableNameConstant.STUDENT_MASTER, courseVariant, applicationCache);
									System.out.println("code :"+code);
									if ((code==null)||(code.trim().equals(""))) {							
										String errorMessage ="Record No:"
												+ (recIndex+1)
												+","																						
												+ " Incompatible Type for Course Varaint,Refer cocd;";
										errorList.add(errorMessage);																						
								}else{
									ExcelValidation.set(listOfstum.get(index), "courseVariant", code.toString().trim());	
								}
										}catch(Exception e){
											ExcelValidation.set(listOfstum.get(index), "courseVariant", "");	
										}
									
																		
									
									}else*/ if(/*(!courseVariant.equals(""))&&(courseVariantCheckCache.get(course).equals("")||courseVariantCheckCache.get(course).equals(ApplicationConstant.COURSE_VARIANT_APPLICABLE_NO))*/
											courseVariantCheckCache.get(course).equals(ApplicationConstant.COURSE_VARIANT_APPLICABLE_NO)||(courseVariantCheckCache.get(course).equals(""))){
										String errorMessage = "Course variatant is Not Applicable for the record :"
												+ (recIndex+1)
												+"with Course :"
												+course
												+".Please Remove it and Upload;";																						
												errorList.add(errorMessage);
									
									}																	
								}/*else {
									String errorMessage = "Course for the record :"
											+ (recIndex+1)
											+ " is Invalid;";											
											errorList.add(errorMessage);
								}*/
								
							}
							
						}
						
						
						
						
							
						}
						
						
						//End Step:8.1
						
						
						// step 9: If the Excel validation finished successfully,then start unique key properties validation with
						// Database and within excel data,use the unique properties Map formed in step 3 - Start
						if(errorList.size()==0){								
							
							
							//Validating for each sheet
							for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {								
								Map<String, ArrayList<ArrayList<String>>> uniqueListForASheet = uniquePropertiesWithSheetNo.get(sheetIndex);							
								Iterator<Map.Entry<String,ArrayList<ArrayList<String>>>> it = uniqueListForASheet.entrySet().iterator();
								
											while (it.hasNext()) {
												Map.Entry<String,ArrayList<ArrayList<String>>> pairs = (Map.Entry<String,ArrayList<ArrayList<String>>>) it.next();
												ArrayList<ArrayList<String>> listOfUniPropAndColName = (ArrayList<ArrayList<String>>) pairs
														.getValue();
												ArrayList<String> uniquePropList = listOfUniPropAndColName
														.get(0);
												ArrayList<String> uniqueColList = listOfUniPropAndColName
														.get(1);										
											
												Map<String, ArrayList<Object>> objectMap = null;
																									
												for (int index = 0; index < uniqueColList
														.size(); index++) {													
													
													HashMap<Integer,String> propValuesInDBMap = new HashMap<Integer,String>();													
													objectMap = listOfObjectMaps.get(
															sheetIndex);														
													// Form a list of unique values from Object List and also form a hashmap value-key pair as (value , "N") 
													int indexForHashMap = 0;																												
																	ArrayList<String> listForEachUniColWithListOfPropValue = new ArrayList<String>();																	
																	List<Object> listOfObj = objectMap.get(pairs.getKey());																	
																	for (Object studentVO : listOfObj) {
																																				
																		String propValue = ExcelValidation
																				.get(studentVO, uniquePropList
																						.get(index));																				
																		listForEachUniColWithListOfPropValue
																				.add(propValue);
																		propValuesInDBMap.put(indexForHashMap, "N");
																		
																		indexForHashMap++;
																}															
																	//Within Excel-duplicates
																	List<String> errorListWithinExcel = excelDataValidations
																			.hasDuplicates(
																					listForEachUniColWithListOfPropValue,
																					uniqueColList.get(index),
																					sheetNames[sheetIndex]);
																	errorList.addAll(errorListWithinExcel);		
																	
																	
																
													// With Database Duplicates Check,here the value list won't be send as whole,but batch of value list.
													List<String> duplicatesInDB = new ArrayList<String>();		
																				
														String tblName = voNameAndTblNameMap.get(pairs.getKey());															
														if (Integer.valueOf(batchOpCommonDao.getNoOfRec(tblName)) != 0) {																																		
															
															//Loop for Batch - DB Call(no of times)
																for (int count = 0, indexCount=0; count < (listForEachUniColWithListOfPropValue.size()); count=count+Integer
																		.valueOf(BatchConstants.BATCH_SIZE_FOR_DB_OPERATION)) {																												
																		ArrayList<String> batchOfRec = new ArrayList<String>();
																		batchOfRec.add(listForEachUniColWithListOfPropValue
																						.get(count));
																		
																		for (indexCount = count+1; (((indexCount % Integer
																				.valueOf(BatchConstants.BATCH_SIZE_FOR_DB_OPERATION)) != 0)&&(indexCount<listForEachUniColWithListOfPropValue.size())); indexCount++) {																			
																				batchOfRec.add(listForEachUniColWithListOfPropValue.get(indexCount));																																	
																				
																		}																		
																		//List Splitted as batch-passing into DB																			
																			List<String> duplicateBatchRecFromDB = batchOpCommonDao
																				.retriveListOfRecForDuplicateCheck(
																						uniqueColList
																								.get(index),
																						batchOfRec,
																						tblName,importDataVO.getInstId(),importDataVO.getBranchId());
																			duplicatesInDB
																				.addAll(duplicateBatchRecFromDB);																						
																}																
															
														}
																					
													// Update the HashMap formed,if the data is in database it will be updated as "Y"
													int indexOfExistingRec = -1;
													if ((duplicatesInDB != null)
															&& (duplicatesInDB.size() != 0)) {
														for (int indexValue = 0; indexValue < duplicatesInDB.size(); indexValue++) {
															
															indexOfExistingRec = listForEachUniColWithListOfPropValue.indexOf(duplicatesInDB.get(indexValue));
															if(indexOfExistingRec!=-1){
															propValuesInDBMap.put(indexOfExistingRec, "Y");
															}
														
														
														}
														
													}													
													
													//Make error List for the duplicate records in database using the HashMap marked with "Y"
													if ((duplicatesInDB != null)
															&& (duplicatesInDB.size() != 0)) {																							
													
															for(int hashMapIndex = 0 ;hashMapIndex<propValuesInDBMap.size();hashMapIndex++){
															
														if(propValuesInDBMap.get(hashMapIndex).equals("Y")){																																														
																	String errorMessage = "Sheet Name :"
																			+ sheetNames[sheetIndex]
																			+ ","
																			+ "Record No:"
																			+ (hashMapIndex + 1)
																			+ ","
																			+ "Column Name :"
																			+ uniqueColList.get(index)
																			+ ","
																			+ "This Record is already in database;";																	
																	errorList.add(errorMessage);															
																
															}																															
													}
													}
																					
												}
			
												it.remove(); // avoids a
																// ConcurrentModificationException
											}									
							}
						}
					//Step 9 >> End.
						
						
					
						

					}
					//Step 10 : Send the error list with  or without error,if there is no error,then the data will be inserted into the DB & 
					//Batch Status and auditing will be  done for success or the batch status & auditing will be  done for the failure - Start
						String batchStatusUpdateConstant =null;
						if(errorList.size()==0){												
							batchStatusUpdateConstant = BatchConstants.BATCH_SUCCESS;
							batchInsertHelper.dataBaseInsertCall(listOfObjectMaps, importDataVO, userSessionDetails, batchSerialNo, applicationCache);
							
						}else{
							 batchStatusUpdateConstant = BatchConstants.BATCH_FAILED;
						}
						
					//Step 10 >> End.S						
						updateBatchStatus(batchStatus,batchStatusUpdateConstant,
												 applicationCache,
												 errorList,
												 noOfRecInEachSheet.get(0),
											userSessionDetails);
																				
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (FileNotFoundException fileNotFound) {
					logger.error("File not Found", fileNotFound);
				} catch (IOException ioException) {
					logger.error("Input Output Exception", ioException);
				} catch (OfficeXmlFileException e) {
					logger.error("File not Found", e);
				} catch (JAXBException e3) {

					e3.printStackTrace();
				} catch (InvalidFormatException e3) {
					e3.printStackTrace();
				}catch (Exception e) {					
					e.printStackTrace();					
				}

								

			}
		});
		t.start();
		return batchSerialNo;
	}

	
	
	public void initializeBatch(BatchStatus batchStatus,						
			String serialNo, 
			UserSessionDetails userSessionDetails,String batchName)throws DatabaseException{			
		
		batchStatus.setDbTs(1);
		batchStatus.setBatchStatus(ApplicationConstant.BATCH_PGMS_INITIALIZATION);
		/*batchStatus.setInstId(batchStatus.getInstId());
		batchStatus.setBranchId(batchStatus.getBranchId());*/
		batchStatus.setBatchSrlNo(serialNo);
		batchStatus.setUpldDataType(batchStatus.getUpldDataType());		
		batchStatus.setBatchName(batchName);
		batchStatus.setTotalNoOfrec("0");
		batchStatus.setrCreId(userSessionDetails.getUserId());
		batchStatus.setrModId(userSessionDetails.getUserId());	
		batchStatusDao.insertBatchStatus(batchStatus);
	}

	public void updateBatchStatus(BatchStatus batchStatus,String statusType,
			ApplicationCache applicationCache, ArrayList<String> errorList, Integer noOfRec,
			UserSessionDetails userSessionDetails)throws DatabaseException{	
	
						Set<String> errorSet = new LinkedHashSet<String>();
						errorSet.addAll(errorList);
				BatchStatusKey batchStatusKey = new BatchStatusKey();		
				commonBusiness.changeObject(batchStatusKey, batchStatus);
				
				if (statusType.equals("SUSPEND")) {				
					
							batchStatus.setBatchStatus(ApplicationConstant.BATCH_PGMS_SUSPENDED);
							batchStatus.setReasonForFailure(errorSet.toString());
							batchStatus.setTotalNoOfrec(noOfRec.toString());
					
					batchStatusDao.updateBatchStatus(batchStatus, batchStatusKey);
					
				} else if (statusType.equals("SUCCESS")) {	
			
							batchStatus.setBatchStatus(ApplicationConstant.BATCH_PGMS_SUCCESS);
							
					batchStatusDao.updateBatchStatus(batchStatus, batchStatusKey);
					
				}

		
	}
			
	public void readExcelAndAssignValuesToObject(
			ArrayList<Integer> columnCountForSheetFromXml,
			Integer sheetIndexValue, List<List<String>> listOfHeaderRow,
			List<List<String>> listOfColumnNameFormXml,
			List<RecordFormat> listOfXMLRecFormat, HSSFSheet sheet,
			Integer columnIndex, ArrayList<String> errorList,
			Map<String, ArrayList<Object>> objectMap,
			ApplicationCache applicationCache,String instId,String branchId,
			HashMap<String,String> voNameAndTblNameMap,HashMap<String,String> courseMasterCache) {
		
					String columnNameFromExcel = listOfHeaderRow.get(sheetIndexValue).get(
							columnIndex);
					int columnIndexNoFromExcel = listOfColumnNameFormXml.get(
							sheetIndexValue).indexOf(columnNameFromExcel);
					Field field1 = listOfXMLRecFormat.get(sheetIndexValue).getFieldList()
							.get(columnIndexNoFromExcel);
					String classNameFormXml1 = field1.getField_Class_Name();
					String[] arrayOfClassName1 = classNameFormXml1.split(":");

		excelValidationUtil.readCellValues(sheetIndexValue, listOfXMLRecFormat,
				sheet, columnIndex, errorList, objectMap, applicationCache,
				instId,branchId, field1, columnNameFromExcel,
				arrayOfClassName1,voNameAndTblNameMap,courseMasterCache);

	}

}
