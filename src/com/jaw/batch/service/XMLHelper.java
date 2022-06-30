package com.jaw.batch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jaw.batch.controller.BatchDataUploadVO;
import com.jaw.common.batch.pojo.Field;
import com.jaw.common.batch.pojo.RecordFormat;
import com.jaw.common.batch.util.ExcelValidation;
import com.jaw.common.constants.BatchConstants;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;


@Component
public class XMLHelper {
		
	@Autowired
	ExcelValidation excelValidationUtil;
	
	public Map<Integer, Map<String, ArrayList<ArrayList<String>>>> listOfUniquePropListForEachSheet(List<RecordFormat> listOfRecFormat,BatchDataUploadVO importTemplateVO,
			Integer noOfSheets){
	
		List<Field> fields = null;
		Map<String, ArrayList<ArrayList<String>>> uniquePropertiesWithObjForSheet0 = null;	
		Map<Integer, Map<String, ArrayList<ArrayList<String>>>> uniquePropertiesWithSheetNo = new HashMap<Integer, Map<String, ArrayList<ArrayList<String>>>>();		
		
			for(int sheetNo = 0;sheetNo<noOfSheets;sheetNo++){			
				fields = listOfRecFormat.get(sheetNo).getFieldList();				
				for (Field field : fields) {
					if (field.getField_Unique_key().equals("Y")) {
						String[] classes = field.getField_Class_Name()
								.split(BatchConstants.Class_Name_Seperator_XML);					
						List<String> listOfMoreThanOneClassForAField = Arrays.asList(classes);
						for(String className : listOfMoreThanOneClassForAField){
							uniquePropertiesWithObjForSheet0 = new HashMap<String, ArrayList<ArrayList<String>>> ();	
							ArrayList<String> uniqueColList = new ArrayList<String>();
							ArrayList<String> uniquePropList = new ArrayList<String>();
								uniquePropList.add(field
										.getField_Property_Name());				
								uniqueColList.add(field
										.getField_DB_ColumnName());		
								ArrayList<ArrayList<String>> listWithUniquePropAndColumnName = new ArrayList<ArrayList<String>>();
								listWithUniquePropAndColumnName.add(uniquePropList);
								listWithUniquePropAndColumnName.add(uniqueColList);
								uniquePropertiesWithObjForSheet0.put(
										className,listWithUniquePropAndColumnName);
								
								
								
							
					}					
				}				
			}
								
				uniquePropertiesWithSheetNo.put(sheetNo,
						uniquePropertiesWithObjForSheet0);
			}
		
				
	return uniquePropertiesWithSheetNo;
	}
		
	
	public List<String> getClassNameListFromXML(int SheetIndexValue,List<RecordFormat> listOfXMLRecFormat){
		List<String> classNameListForComparison = new ArrayList<String>();
		RecordFormat recordFormatFromXml = listOfXMLRecFormat
				.get(SheetIndexValue);
		HashSet<String> setOfClassesFromXml = new HashSet<String>();
		for (Field field : recordFormatFromXml
				.getFieldList()) {
			String className = field
					.getField_Class_Name();
			if (!className
					.contains(BatchConstants.Class_Name_Seperator_XML)) {
				setOfClassesFromXml.add(className);
			}
		}

		classNameListForComparison = new ArrayList<String>(
				setOfClassesFromXml);
		return classNameListForComparison;
		
	}
	
	
	public ArrayList<String> validateDefaultValuesinXML(List<List<String>> listOfColumnNameFormXml,int sheetIndexValue,List<List<String>> listOfHeaderRow,
			List<RecordFormat> listOfXMLRecFormat,int noOfRecordsInExcel,HSSFSheet sheet,ArrayList<String> errorList,ApplicationCache applicationCache,
			String instId,String branchId,HashMap<String,String> voNameAndTblNameMap,Map<String, ArrayList<Object>> objectMap,HashMap<String,String> courseMasterCache){
		StringBuffer convertedValue = null;
		for (String columnName : listOfColumnNameFormXml
					.get(sheetIndexValue)) {
			
				if (!(listOfHeaderRow.get(sheetIndexValue)
						.contains(columnName))) {
					int columnIndexNo = listOfColumnNameFormXml
							.get(sheetIndexValue).indexOf(
									columnName);

					Field field = listOfXMLRecFormat
							.get(sheetIndexValue)
							.getFieldList()
							.get(columnIndexNo);
					String classNameFormXml = field
							.getField_Class_Name();
					String[] arrayOfClassName = classNameFormXml
							.split(BatchConstants.Class_Name_Seperator_XML);

				
				 convertedValue = 	excelValidationUtil.validationForType(
										field,
										field.getDefault_Value(),
										arrayOfClassName,									
										sheet,
										0, columnName,
										errorList,
										applicationCache,
										instId,branchId,voNameAndTblNameMap,courseMasterCache);	
				 if(errorList.size()==0){					
					 
					 for (int index = 1; index <= noOfRecordsInExcel; index++) {					
							excelValidationUtil.assigningValuesToObject(field.getDefault_Value(),convertedValue,
									arrayOfClassName,objectMap,
									index,field);
								
							}					
				 }
				 
					}
				
				
			}
			return errorList;
	}
	
	
	
	public HashMap<String,String> hashMapForTablConstant(){
		
		HashMap<String,String> hashMapForTbl = new HashMap<String, String>();
		//For StudentMaster VO
		hashMapForTbl.put(BatchConstants.BATCH_STUDENTMASTER_OBJ,BatchConstants.BATCH_STUDENTMASTER_TBL_NAME );
		//For StudentMasterInfo VO
		hashMapForTbl.put(BatchConstants.BATCH_STUDENTINFO_OBJ,BatchConstants.BATCH_STUDENTINFO_TBL_NAME );
		//For Communication Details VO
		hashMapForTbl.put(BatchConstants.BATCH_COMMDETAILS_OBJ,BatchConstants.BATCH_COMMDETAILS_TBL_NAME );
		//For Parent Details VO
		hashMapForTbl.put(BatchConstants.BATCH_PARENTDETAILS_OBJ,BatchConstants.BATCH_PARENTDETAILS_TBL_NAME );
		//For Previous school Details VO
		hashMapForTbl.put(BatchConstants.BATCH_PREVACADEMICDETAILS_OBJ,BatchConstants.BATCH_PREVACADEMICDETAILS_TBL_NAME);
		//For Sibling Details VO
		hashMapForTbl.put(BatchConstants.BATCH_SIBLINGDETAILS_OBJ,BatchConstants.BATCH_SIBLINGDETAILS_TBL_NAME);
		//For Transport Detais VO
		hashMapForTbl.put(BatchConstants.BATCH_TRANSDETAILS_OBJ,BatchConstants.BATCH_TRANSDETAILS_TBL_NAME);	
		//For userlink 
		hashMapForTbl.put(BatchConstants.BATCH_USERLINKDETAILS_OBJ,BatchConstants.BATCH_USERLINKDETAILS_TBL_NAME);
		//For UserVo
		hashMapForTbl.put(BatchConstants.BATCH_USER_OBJ,BatchConstants.BATCH_USER_TBL_NAME);

		
		return hashMapForTbl;
	}

}
