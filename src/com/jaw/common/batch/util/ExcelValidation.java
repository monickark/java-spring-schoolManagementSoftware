package com.jaw.common.batch.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jaw.common.batch.pojo.Field;
import com.jaw.common.batch.pojo.RecordFormat;
import com.jaw.common.batch.util.ExcelUtils.ProductOrCustom;
import com.jaw.common.constants.BatchConstants;
import com.jaw.common.util.dao.CommonCodeColumnBatchSearch;
import com.jaw.common.util.dao.CommonCodeColumnRec;
import com.jaw.common.util.dao.ICommonCodeColumnDao;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
@Component
public class ExcelValidation {
	Logger logger = Logger.getLogger(ExcelValidation.class);
	@Autowired
	ExcelValidationUtil excelDataValidations;		
	@Autowired
	ICommonCodeColumnDao commonCodeColDao;
	
	public enum DataType{
		ADCO,ADPI,ADSI,ADTI,ADUL,ADCC;
	}
	public enum DataTypeForCellValidation{
		Date,MobileNumber,Pincode,Integer,Text,
		SpecialText,EMail,LandLineNumber,Double,Boolean,Year,
		SimpleText,COCD,Name,Address,Phone,CORS
	}
	public enum ClassNames{
		StudentMasterVO,StudentInfoVO,PrevAcademicDetailsVO,ParentDetailsVO,CommunicationDetailsVO,UserLink,UserVO,SiblingDetailsVO,TransportDetailsVO;
	}
	
	public enum TableNames{
		stum,stin,psde,pard,comd,usrl,user,sibd;
	}
	
	public static boolean set(Object object, String fieldName, Object fieldValue) {

		Class<?> clazz = object.getClass();
		while (clazz != null) {
			try {
				java.lang.reflect.Field field = clazz
						.getDeclaredField(fieldName);
				field.setAccessible(true);		
			if((field.getType().getSimpleName().equals("Integer"))&&(!fieldValue.toString().trim().equals(""))){			
				field.set(object, new Integer(Integer.valueOf(fieldValue.toString()))); 
			}else if((field.getType().getSimpleName().equals("Double"))&&(!fieldValue.toString().trim().equals(""))){				
				field.set(object, new Double(Double.valueOf(fieldValue.toString()))); 
			}else  if((field.getType().getSimpleName().equals("String"))){				
				field.set(object, fieldValue); 
			}
			
			
				

				return true;
			} catch (NoSuchFieldException e) {

				clazz = clazz.getSuperclass();
			} catch (Exception e) {

				e.printStackTrace();

				return false;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static <E> E get(Object object, String fieldName) {
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			try {
				java.lang.reflect.Field field = clazz
						.getDeclaredField(fieldName);
				field.setAccessible(true);
				return (E) field.get(object);
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}	

	public HashMap<Integer, String> getColumnName(
			List<RecordFormat> listOfRecordFromat, String className,
			String propertyName) {
		HashMap<Integer, String> sheetAndColumnName = new HashMap<Integer, String>();
		List<Field> fields = null;
		String columnName = null;
		for (int recCount = 0; recCount < listOfRecordFromat.size(); recCount++) {			
			// Getting column names alone from XML
			fields = listOfRecordFromat.get(recCount).getFieldList();			
			if (listOfRecordFromat.get(recCount).getRecordProductOrCustom()
					.equals(ProductOrCustom.PROD.toString())) {
				for (Field field : fields) {

					String[] columnnameArray = field.getField_Class_Name()
							.split(":");
					for (int index = 0; index < columnnameArray.length; index++) {
						if ((columnnameArray[index].equals(className))
								&& (field.getField_Property_Name()
										.equals(propertyName))) {						
							columnName = field.getField_Name_Product();
							sheetAndColumnName.put(recCount, columnName);
						}
					}					
				}
			} else if (listOfRecordFromat.get(recCount)
					.getRecordProductOrCustom().equals(ProductOrCustom.CUST.toString())) {
				for (Field field : fields) {
					String[] columnnameArray = field.getField_Class_Name()
							.split(":");
					for (int index = 0; index < columnnameArray.length; index++) {
						if ((columnnameArray[index].equals(className))
								&& (field.getField_Property_Name()
										.equals(propertyName))) {						
							columnName = field.getFiled_Name_Custom();
							sheetAndColumnName.put(recCount, columnName);
						}
					}
				}
			}

		}
		return sheetAndColumnName;
	}

	public List<String> headerValidation(String sheetName,ArrayList<String> headerRow,
			RecordFormat record, ArrayList<String> columNameFromXml) {		
		List<String> columnHeaderErrorList = new ArrayList<String>();

		List<Field> fieldList = record.getFieldList();
		if (record.getRecordProductOrCustom().equals(ProductOrCustom.PROD.toString())) {
			for (Field field : fieldList) {				
				if ((!(headerRow.contains(field.getField_Name_Product())))
						&& (field.getField_Mandatory_Product().equals("YES"))) {									
						String errorMessage = "Sheet Name :"+ sheetName+ ","+ "Record No:"+""+","+"Column Name :"+field.getField_Name_Product()+" This is Mandatory column,missing;";						
						columnHeaderErrorList.add(errorMessage);				
				}
			}

		} else if (record.getRecordProductOrCustom().equals(ProductOrCustom.CUST.toString())) {
			for (Field field : fieldList) {				
				if (!field.getFiled_Name_Custom().equals("")) {
					if ((!(headerRow.contains(field.getFiled_Name_Custom())))
							&& (field.getField_Mandatory_Custom().equals("YES"))) {				
							String errorMessage = "Sheet Name :"+ sheetName+ ","+ "Record No:"+""+","+"Column Name :"+field.getFiled_Name_Custom()+" This is Mandatory column,missing;";
							columnHeaderErrorList.add(errorMessage);							
					}
				} else {
					if ((!(headerRow.contains(field.getField_Name_Product())))
							&& (field.getField_Mandatory_Product().equals("YES"))) {												
							String errorMessage = "Sheet Name :"+ sheetName+ ","+ "Record No:"+""+","+"Column Name :"+field.getField_Name_Product()+" This is Mandatory column,missing;";
							columnHeaderErrorList.add(errorMessage);							
					}
				}
			}

		}
		// extra column check
				for (String columnName : headerRow) {
					if (!(columNameFromXml.contains(columnName))) {						
							String errorMessage = "Sheet Name :"+ sheetName+ ","+ "Record No:"+""+","+"Column Name :"+columnName+" This is not needed;";
							columnHeaderErrorList.add(errorMessage);						
						
					}

				}

				return columnHeaderErrorList;

	}
	
	public ArrayList<String> getColumnNameFromXML(List<RecordFormat> listOfRecFormat,Integer sheetIndex){
	
		List<Field> fields = listOfRecFormat.get(sheetIndex).getFieldList();
		ArrayList<String> 	columnNameFromXMl = new ArrayList<String>();		
		if (listOfRecFormat.get(sheetIndex)
				.getRecordProductOrCustom().equals(ProductOrCustom.PROD.toString())) {
			for (Field field : fields) {				
				columnNameFromXMl.add(field
						.getField_Name_Product());
			}
		} else if (listOfRecFormat.get(sheetIndex)
				.getRecordProductOrCustom().equals(ProductOrCustom.CUST.toString())) {
			for (Field field : fields) {
				if (!field.getFiled_Name_Custom().equals("")) {
					columnNameFromXMl.add(field
							.getFiled_Name_Custom());
				} else {
					columnNameFromXMl.add(field
							.getField_Name_Product());
				}
			}

		}
		return columnNameFromXMl;
		
	}

	public ArrayList<String> initialSheetValidations(List<RecordFormat> listOfRecFormat,List<List<String>> listOfColumnNameFormXml,String[] sheetNames,
			Workbook workbook,List<List<String>> listOfHeaderRow,ArrayList<Integer> columnCountForSheetFromExcel,Integer noOfSheets){
		
		HSSFSheet sheet = null;
		ArrayList<String> errorListForSheetAndHeader = new ArrayList<String>();
		
		if(workbook.getNumberOfSheets()==sheetNames.length){
		for(int sheetIndex = 0;sheetIndex<noOfSheets;sheetIndex++){
			
			int columnCount = 0;								
			ArrayList<String> 	columnNameFromXMl = getColumnNameFromXML(listOfRecFormat, sheetIndex);
			listOfColumnNameFormXml.add(columnNameFromXMl);
					
		if (sheetNames[sheetIndex].equals(workbook
				.getSheetName(sheetIndex))) {
			logger.info("Sheet Name Validation Success");
			
			// validate sheet name, then get the header cell values and form header list
			ArrayList<String> headerRow = new ArrayList<String>();
			sheet = (HSSFSheet) workbook
					.getSheetAt(sheetIndex);
			HSSFRow startBold = sheet.getRow(0);
			Iterator<Cell> headerValue = startBold.iterator();			
			while (headerValue.hasNext()) {
				String columnName = headerValue.next().toString();				
				if (columnName.charAt(columnName.length() - 1) == '*') {
					columnName = columnName.substring(0,
							columnName.length() - 1);
				}
				headerRow.add(columnName);
				columnCount = columnCount + 1;
			}
						
			listOfHeaderRow.add(headerRow);	
			
			//validate the header for the current sheet
			List<String> headerErrorList = 
					headerValidation(sheetNames[sheetIndex], headerRow,
							listOfRecFormat.get(sheetIndex),
							columnNameFromXMl);
			if (!headerErrorList.isEmpty()) {
				logger.info("Header Validation failed");
				String error = headerErrorList.toString();
				errorListForSheetAndHeader.add(error);
			} 

		} else {
			logger.info("Sheet Name validation failed");
			String errorMessage = "Sheet Name :" + sheetNames[sheetIndex]
					+ "," + "Record No:" + "" + ","
					+ "Column Name :" + ""
					+ " Undefined Sheet Name;";

			errorListForSheetAndHeader.add(errorMessage);
		}														
		columnCountForSheetFromExcel.add(columnCount);
		}		
		}else{
			logger.info("Number of sheets not matching");
			String errorMessage = "Sheet Name :" + " " + ","
					+ "Record No:" + " " + "," + "Column Name :"
					+ " "
					+ " Cannot Proceed,Number of Sheets not matching..!;";	

			errorListForSheetAndHeader.add(errorMessage);
		}
	
		return errorListForSheetAndHeader;
	}
		
	
	
	public void readCellValues(int sheetIndexValue,List<RecordFormat> listOfRecFormat,HSSFSheet sheet,int columnIndex,ArrayList<String> errorList
			,Map<String, ArrayList<Object>> objectMap,ApplicationCache applicationCache,String instId,String branchId,Field field,String columnName,String[] arrayOfClassName,HashMap<String,String> voNameAndTblNameMap,HashMap<String,String> courseMasterCache){
		 Cell cell = null;		
		
		 for (int rowIndex=1; rowIndex<=sheet.getLastRowNum(); rowIndex++) {		
	        Row row = sheet.getRow(rowIndex);
	        cell = row.getCell(columnIndex); //get first cell	        
	       
		String cellValue = "";
		
		try {			
			cellValue = cell.toString().trim();						
			
		} catch (Exception e) {					
			checkMandatoryEmpty(listOfRecFormat,sheetIndexValue,field,sheet,rowIndex,columnName,errorList,objectMap,arrayOfClassName,listOfRecFormat.get(sheetIndexValue).getRecordProductOrCustom());		
			} 
		if((cellValue.length()==0)||(cellValue.equals(""))){
			checkMandatoryEmpty(listOfRecFormat,sheetIndexValue,field,sheet,rowIndex,columnName,errorList,objectMap,arrayOfClassName,listOfRecFormat.get(sheetIndexValue).getRecordProductOrCustom());
		}else if (!cellValue.equals("")) {				
			StringBuffer convertedValue= validationForType(field,
					cellValue,
					arrayOfClassName,				
					sheet,
					rowIndex,
					columnName,
					errorList,applicationCache,instId,branchId,voNameAndTblNameMap,courseMasterCache);		
			if(errorList.size()==0){
					assigningValuesToObject(cellValue,convertedValue,arrayOfClassName,objectMap,
						rowIndex,field);				
			}
			
			
			
			
		}

		}
	}
	
	public void checkMandatoryEmpty(List<RecordFormat> listOfRecFormat,int sheetIndexValue,Field field,
			HSSFSheet sheet,int rowIndex,String columnName,ArrayList<String> errorList,
			Map<String, ArrayList<Object>> objectMap,String[] arrayOfClassName,String customeOrProduct){
		
		if (((customeOrProduct.equals("PROD"))&&(field.getField_Mandatory_Product().equals("YES")))||((customeOrProduct.equals("CUST"))&&((field.getField_Mandatory_Custom().equals("YES"))))) {				
					String errorMessage = "Sheet Name :"
							+ sheet.getSheetName()
							+ ","
							+ "Record No:"
							+ rowIndex
							+ ","
							+ "Column Name :"
							+ columnName
							+ " Mandatory column could not be empty;";
					errorList.add(errorMessage);
					
			} else  {				
				for (int index = 0; index < arrayOfClassName.length; index++) {
					Object object = objectMap
							.get(arrayOfClassName[index]);
					ExcelValidation.set(
							object,
							field.getField_Property_Name(),
							field.getDefault_Value());
				}

			} 

	}
	
	public StringBuffer validationForType(Field field,
			String cellValue,
			String[] arrayOfClassName,
		
			HSSFSheet sheet,
			int rowIndex,
			String columnName,
			ArrayList<String> errorList,ApplicationCache applicationCache,String instId,String branchId,HashMap<String,String> voNameAndTblNameMap,HashMap<String,String> courseMasCache){
		String fieldType = "";
		DataTypeForCellValidation dataTypeForCell = null;
	
		if((field.getField_Type()!=null)&&(!field.getField_Type().equals(""))){
			 dataTypeForCell = DataTypeForCellValidation.valueOf(field.getField_Type());
		}
	
	
		StringBuffer convertedValue = null;
		Integer intValue = 0;
		Boolean needSizeValidation = false;		
		if(dataTypeForCell!=null){
		switch(dataTypeForCell){
		case Date :{
			convertedValue = new StringBuffer();
			if(!cellValue.equals("")){			
				if (!excelDataValidations.validateDateFormat(cellValue,convertedValue)) {
					
					String errorMessage = "Sheet Name :"
							+ sheet.getSheetName()
							+ ","
							+ "Record No:"
							+ rowIndex
							+ ","
							+ "Column Name :"
							+ columnName
							+ " Incompatible Type,Expected Date Format;";
					errorList.add(errorMessage);
				}
				}																				
			break;
		}
		case MobileNumber:{			
			convertedValue=new StringBuffer();
			Boolean mobileNo =excelDataValidations.checkMobileNumberFormat(cellValue,convertedValue); 				
			if(!cellValue.trim().equals("")){
				 if (!mobileNo) {			
						String errorMessage = "Sheet Name :"
								+ sheet.getSheetName()
								+ ","
								+ "Record No:"
								+ rowIndex
								+ ","
								+ "Column Name :"
								+ columnName
								+ " Incompatible Type,Expected Mobile No fromat;";
						errorList.add(errorMessage);
						
				} 
				}

			break;
		}
		
		case Pincode:{				
			
			DecimalFormat pattern = new DecimalFormat(
					"#,#,#,#,#,#");
			NumberFormat testNumberFormat = NumberFormat
					.getNumberInstance();								
			if(!cellValue.trim().equals("")){
				try {
					Double pin = Double
							.valueOf(cellValue);
					convertedValue = new StringBuffer(testNumberFormat
							.format(pin).replace(",",""));
					 				
					pattern.parse(convertedValue.toString());
				} catch (ParseException e1) {			
						String errorMessage = "Sheet Name :"
								+ sheet.getSheetName()
								+ ","
								+ "Record No:"
								+ rowIndex
								+ ","
								+ "Column Name :"
								+ columnName
								+ " Incompatible Type,Expected Pincode Format;";
						errorList.add(errorMessage);
						
				} catch (NumberFormatException e2) {				
						String errorMessage = "Sheet Name :"
								+ sheet.getSheetName()
								+ ","
								+ "Record No:"
								+ rowIndex
								+ ","
								+ "Column Name :"
								+ columnName
								+ " Incompatible Type,Expected Pincode Format;";
						errorList.add(errorMessage);					
				}							
				
				if (!excelDataValidations.checkPincodeFormat(convertedValue.toString())) {					
					String errorMessage = "Sheet Name :"
							+ sheet.getSheetName()
							+ ","
							+ "Record No:"
							+ rowIndex
							+ ","
							+ "Column Name :"
							+ columnName
							+ " Incompatible Type,Expected Pincode Format;";
					errorList.add(errorMessage);					
			
				} 
			
			
			}
			
			
			break;
		}
		
		case Integer:{								
			if(!cellValue.trim().equals("")){
				Integer convertInt = Integer
						.valueOf(cellValue);
				 intValue =convertInt.toString().length();
				 convertedValue = new StringBuffer(convertInt.toString());
				 needSizeValidation = true;
				if (!excelDataValidations.checkForInteger(cellValue)) {			
					String errorMessage = "Sheet Name :"
							+ sheet.getSheetName()
							+ ","
							+ "Record No:"
							+ rowIndex
							+ ","
							+ "Column Name :"
							+ columnName
							+ " Incompatible Type,Expected integer Format;";
					errorList.add(errorMessage);				
			} 
				}			
			break;
		}
		
		case Text:{		
			if(!cellValue.trim().equals("")){
				convertedValue = new StringBuffer(cellValue.toString());
				needSizeValidation = true;
				intValue = cellValue.length();
			if (!excelDataValidations.isAlphaNumericWithoutSpecialChar(convertedValue.toString())) {			
				String errorMessage = "Sheet Name :"
						+ sheet.getSheetName()
						+ ","
						+ "Record No:"
						+ rowIndex
						+ ","
						+ "Column Name :"
						+ columnName
						+ " Incompatible Type,Expected Text;";
				errorList.add(errorMessage);					
		} 
			}
			break;
		}
		case SpecialText :{				
			if(!cellValue.trim().equals("")){
				convertedValue = new StringBuffer(cellValue.toString());			
				needSizeValidation = true;
				intValue = convertedValue.length();				
			if (!excelDataValidations.isAlphaNumericWithSpecialChar(convertedValue.toString())) {				
				String errorMessage = "Sheet Name :"
						+ sheet.getSheetName()
						+ ","
						+ "Record No:"
						+ rowIndex
						+ ","
						+ "Column Name :"
						+ columnName
						+ " Incompatible Type,Expected SpecialText;";
				errorList.add(errorMessage);					
		} 
			}
			break;
		}
		case EMail:{			
			if(!cellValue.trim().equals("")){
				convertedValue = new StringBuffer(cellValue.toString());
			if (!excelDataValidations.checkEmail(convertedValue.toString())) {
				
				String errorMessage = "Sheet Name :"
						+ sheet.getSheetName()
						+ ","
						+ "Record No:"
						+ rowIndex
						+ ","
						+ "Column Name :"
						+ columnName
						+ " Incompatible Type,Expected E-Mail format;";
				errorList.add(errorMessage);					
		} 
			}
			break;
		}
		case LandLineNumber:{	
			if(!cellValue.trim().equals("")){
				convertedValue = new StringBuffer(cellValue.toString());
			if (!excelDataValidations.checkLandlineNumber(convertedValue.toString())) {			
				String errorMessage = "Sheet Name :"
						+ sheet.getSheetName()
						+ ","
						+ "Record No:"
						+ rowIndex
						+ ","
						+ "Column Name :"
						+ columnName
						+ " Incompatible Type,Expected Landline format;";
				errorList.add(errorMessage);					
		} 
			}
			break;
		}
		case Double:{	
			if(!cellValue.trim().equals("")){
				convertedValue = new StringBuffer(cellValue.trim());
			if (!excelDataValidations.checkDoubleValue(convertedValue.toString())) {				
				String errorMessage = "Sheet Name :"
						+ sheet.getSheetName()
						+ ","
						+ "Record No:"
						+ rowIndex
						+ ","
						+ "Column Name :"
						+ columnName
						+ " Incompatible Type,Expected Double Format;";
				errorList.add(errorMessage);
				

		} 
			}
			break;
		}
		case Boolean:{		
			if(!cellValue.trim().equals("")){
				convertedValue = new StringBuffer(cellValue.toString());
			if (!excelDataValidations.checkBooleanValue(cellValue)) {				
				String errorMessage = "Sheet Name :"
						+  sheet.getSheetName()
						+ ","
						+ "Record No:"
						+ rowIndex
						+ ","
						+ "Column Name :"
						+ columnName
						+ " Incompatible Type,Expected Boolean formate;";
				errorList.add(errorMessage);					
		}
			}
			break;
		}
		case Year:{			
			if(!cellValue.trim().equals("")){
				Double doubleValue = Double
						.valueOf(cellValue);
				Integer yearValue = doubleValue
						.intValue();
				convertedValue = new StringBuffer(yearValue.toString());
			if (!excelDataValidations.checkYearValue(cellValue)) {								
				String errorMessage = "Sheet Name :"
						+  sheet.getSheetName()
						+ ","
						+ "Record No:"
						+ rowIndex
						+ ","
						+ "Column Name :"
						+ columnName
						+ " Incompatible Type,Expected Year format;";
				errorList.add(errorMessage);					
			} 
			}
			break;
		}
		case SimpleText :{						
			if(!cellValue.trim().equals("")){
				convertedValue = new StringBuffer(cellValue.toString());
				needSizeValidation = true;				
				intValue = cellValue.length();	
			if (!excelDataValidations.simpleText(cellValue)) {								
				String errorMessage = "Sheet Name :"
						+  sheet.getSheetName()
						+ ","
						+ "Record No:"
						+ rowIndex
						+ ","
						+ "Column Name :"
						+ columnName
						+ " Incompatible Type,Expected only alphabets format;";
				errorList.add(errorMessage);																							
			} 
			}
			break;
		}
			case COCD:{						
				Boolean codeFlag = false;
				if(!cellValue.trim().equals("")){
							
				try{					
					String[] tblNames =field.getField_Class_Name().split(BatchConstants.Class_Name_Seperator_XML);
					String tblName  = voNameAndTblNameMap.get(tblNames[0]);				 				
					String code = cocdValidation(instId, branchId, field.getField_DB_ColumnName(),tblName,cellValue,applicationCache);
					
					/*CommonCodeColumnRec commonCodeColRec = commonCodeColDao.getCommonCodeFromCCCL(commonCodeCol);										
					String code = excelDataValidations.cocdValidation(commonCodeColRec.getCodeType(),cellValue, applicationCache,instId,branchId);*/				
					if ((code==null)||(code.trim().equals(""))) {							
						String errorMessage = "Sheet Name :"
								+  sheet.getSheetName()
								+ ","
								+ "Record No:"
								+ rowIndex
								+ ","
								+ "Column Name :"
								+ columnName
								+ " Incompatible Type,Refer cocd;";
						errorList.add(errorMessage);																						
				}else{
					convertedValue = new StringBuffer(code);
				}
					
				// codeFlag = convertedValue.equals("");
				}catch(Exception e){					
					convertedValue = new StringBuffer("");					
					
				}
				
			}
				break;
			}
		case Name:
		{		
			convertedValue = new StringBuffer(cellValue);
			intValue = Integer.valueOf(field
					.getField_Size());
			needSizeValidation = true;
			System.out.println("name values :"+cellValue);
			if (!excelDataValidations.nameValidation(cellValue.trim())) {				
				String errorMessage = "Sheet Name :"
						+  sheet.getSheetName()
						+ ","
						+ "Record No:"
						+ rowIndex
						+ ","
						+ "Column Name :"
						+ columnName
						+ " Incompatible Type,Expected Name Format;";
				errorList.add(errorMessage);					
		}

			break;			
		}
		
		case Address:{	
			convertedValue = new StringBuffer(cellValue);
			intValue = cellValue.length();
			needSizeValidation = true;
			if (!excelDataValidations.addressValidation(cellValue)) {				
				String errorMessage = "Sheet Name :"
						+  sheet.getSheetName()
						+ ","
						+ "Record No:"
						+ rowIndex
						+ ","
						+ "Column Name :"
						+ columnName
						+ " Incompatible Type,EXpected Address Format;";
				errorList.add(errorMessage);					
		} 

			break;			
		}
		
		case Phone:{			
			StringBuffer convertedNo = new StringBuffer();				
			Boolean phNo = excelDataValidations.phoneValidation(cellValue,convertedNo);	
		
			if (!phNo) {			
				String errorMessage = "Sheet Name :"
						+  sheet.getSheetName()
						+ ","
						+ "Record No:"
						+ rowIndex
						+ ","
						+ "Column Name :"
						+ columnName
						+ " Incompatible Type,EXpected Phone Format;";
				errorList.add(errorMessage);					
		} else{
			convertedValue = new StringBuffer(convertedNo);
		}
			break;			
		}		
			
		case CORS:{		
			String courseId = courseMasCache.get(cellValue);			
			if(courseId!=null){
				convertedValue =  new StringBuffer(courseId);
			}else{
				
				String errorMessage = "Sheet Name :"
						+  sheet.getSheetName()
						+ ","
						+ "Record No:"
						+ rowIndex
						+ ","
						+ "Column Name :"
						+ columnName
						+ " Invalid Course Name;";
				errorList.add(errorMessage);					
		
			}
			
		}	
		
		default :{
			logger.debug("No Data type has been specified.");
		}
		
		}
	}
				
		if(needSizeValidation==true){	
			Boolean sizeValidationFlag = (excelDataValidations.sizeValidation(
					Integer.valueOf(field
							.getField_Size()),
					intValue));					
			if (sizeValidationFlag==false) {				
				String errorMessage = "Sheet Name :"
						+ sheet.getSheetName()
						+ ","
						+ "Record No:"
						+ rowIndex
						+ ","
						+ "Column Name :"
						+ columnName
						+ " Incompatible Size-Check the size,Expected size:"
						+field.getField_Size()
						+";";
				errorList.add(errorMessage);						
		} 
		}			
		return convertedValue;
		

		}
	
	
	public void assigningValuesToObject(String cellValue,StringBuffer convertedValue,String[] arrayOfClassName,Map<String, ArrayList<Object>> objectMap,
			int rowIndex,Field field){
				
		if((cellValue.equals(""))||(convertedValue==null)){
		
			for (int index = 0; index < arrayOfClassName.length; index++) {
				List<Object> object = objectMap
						.get(arrayOfClassName[index]);
			
				ExcelValidation.set(
						object.get(rowIndex-1),
						field.getField_Property_Name(),
						cellValue);
			}						
		}else{
		
			/*Class<?> c;
			try {
				c = Class.forName(convertedValue.toString());
				 java.lang.reflect.Field fields = c.getField(convertedValue.toString());
				;
				 if(fields.getType().equals("java.lang.Integer")){
					 
				 }else if(fields.getType().equals("java.lang.Double")){
					 
				 }
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
						
			
			for (int index = 0; index < arrayOfClassName.length; index++) {
				List<Object> object = objectMap.get(arrayOfClassName[index]);									
				if(convertedValue.toString().equals("")){
					ExcelValidation.set(
							object.get(rowIndex-1),
							field.getField_Property_Name(),
							convertedValue.toString());				
				}else{				
					ExcelValidation.set(
							object.get(rowIndex-1),
							field.getField_Property_Name(),
							convertedValue.toString());				
				}
			}	
			
		}
		
		
	}
	
	public String cocdValidation(String instId,String branchId,String ccclColName,String tblName,String cocdValue,ApplicationCache applicationCache){
		CommonCodeColumnBatchSearch commonCodeCol = new CommonCodeColumnBatchSearch();
		commonCodeCol.setInstId(instId);
		commonCodeCol.setBranchId(branchId);
		commonCodeCol.setColumnName(ccclColName);
		commonCodeCol.setTableName(tblName);	
		CommonCodeColumnRec commonCodeColRec = commonCodeColDao.getCommonCodeFromCCCL(commonCodeCol);										
		String code = excelDataValidations.cocdValidation(commonCodeColRec.getCodeType(),cocdValue, applicationCache,instId,branchId);				
		return code;
	}
		
	}


