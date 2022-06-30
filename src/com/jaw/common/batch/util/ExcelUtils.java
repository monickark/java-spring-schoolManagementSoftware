
package com.jaw.common.batch.util;

import java.awt.font.TextAttribute;
import java.io.IOException;
import java.io.InputStream;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Component;

import com.jaw.common.batch.pojo.Field;
import com.jaw.common.batch.pojo.RecordFormat;
import com.jaw.common.batch.pojo.XmlConfigurationForUpload;
import com.jaw.common.batch.util.ExcelUtils.ProductOrCustom;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.BatchConstants;
import com.jaw.framework.appCache.ApplicationCache;


@Component
public class ExcelUtils {
	
	Logger logger = Logger.getLogger(ExcelUtils.class);		
	
	public enum ProductOrCustom{
		CUST,PROD;
	}
	public enum FlagForMandatoryAndExcColumns{
		YES,NO;
	}

	
	//Method to process the excel
	public static List<List<String>> processExcel(InputStream inputStream)
			throws IOException {

		List<List<String>> sheetData = new ArrayList<List<String>>();
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);		
		// Get the first sheet on the workbook.
		HSSFSheet sheet = workbook.getSheetAt(0);		
	
		//
		// When we have a sheet object in hand we can iterator on
		// each sheet's rows and on each row's cells. We store the
		// data read on an ArrayList so that we can printed the
		// content of the excel to the console.
		//
		Iterator rows = sheet.rowIterator();
		while (rows.hasNext()) {
			HSSFRow row = (HSSFRow) rows.next();			
			List<String> rowData = new ArrayList<String>();		
			for (int cn = 0; cn < row.getLastCellNum(); cn++) {
				// If the cell is missing from the file, generate a blank one
				Cell cell = row.getCell(cn,
						org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
				// Print the cell for debugging
				if (null == cell) {
					
					rowData.add(null);
				} else {
					
					rowData.add(cell.toString());
				}
			}
			sheetData.add(rowData);
		}		
		return sheetData;
	}		

	//Method to prepare the Excel Workbook
	public static HSSFWorkbook prepareWorkBook(HttpServletResponse response,					
					List<List<String>> listOfColumnList ,List<RecordFormat> xmlList, ApplicationCache applicationCache,String[] sheetName) {													
			HSSFWorkbook workBook = new HSSFWorkbook();		
			for(int index = 0;index<listOfColumnList.size();index++){						
				String workSheetName = sheetName[index];	
			
				listOfColumnList.get(index).add(0,"S.No");			
				int lengthOfColumns = listOfColumnList.get(index).size();
				
		// Do the POI Things to generate the Excel File create a new workbook	
		HSSFSheet sheet = workBook.createSheet(workSheetName);
			
		// Create a Font For Header
		HSSFFont headerFont = workBook.createFont();
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontName(BatchConstants.BATCH_EXCEL_DEFAULT_FONT_FACE);

		// create a style for the header Columns
		HSSFCellStyle columnHeaderStyle = workBook.createCellStyle();
		columnHeaderStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		 
		// Call This method before calling below one.
		columnHeaderStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		columnHeaderStyle.setFont(headerFont);

		// Create a Font For Data Rows
		HSSFFont rowFont = workBook.createFont();
		rowFont.setFontName(BatchConstants.BATCH_EXCEL_DEFAULT_FONT_FACE);

		// create a style for the Records In Next Rows
		HSSFCellStyle rowCellStyle = workBook.createCellStyle();
		rowCellStyle.setFont(rowFont);		
		HSSFRow[] headerRow = new HSSFRow[1];
		ListIterator<String> outerListIterator =	listOfColumnList.get(index).listIterator();		
		headerRow[0] = sheet.createRow(0);	
		HSSFCell[] headerColumns = new HSSFCell[lengthOfColumns];
	
		// Create Header Row				
		while (outerListIterator.hasNext()) {	
			int columnIndex = outerListIterator.nextIndex();	
			// create header rows: Write the column header in Very First(0th)
			// Row Of Excel File								
			headerRow[0].createCell(columnIndex);
			
			// Create Data for the Header Row		
				headerColumns[columnIndex] = headerRow[0]
						.createCell(columnIndex);		
				String cellValue = outerListIterator.next();	
				
				headerColumns[columnIndex].setCellValue(cellValue);						
				headerColumns[columnIndex].setCellStyle(columnHeaderStyle);		
		}
		
		for(int rowIndex = 1;rowIndex<=10;rowIndex++){
			HSSFRow row = 	sheet.createRow((short) rowIndex);	
			String rowValue = Integer.valueOf(rowIndex).toString();			
			row.createCell(0).setCellValue(rowValue);
		}		
			}
			return workBook;
	}
	
	public static void generateWorkbook(HttpServletResponse response,
			HSSFWorkbook workBook, String workSheetName) throws IOException {

		// set specific response content type for xls files.
		response.setContentType("application/vnd.ms-excel; charset=utf-8");
		//response.setContentType("application/excel; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename="		
		+ workSheetName
		+ ".XLS");

		/*
		 * With this we let the browser know what the default name should be for
		 * the user to save this file.
		 */
		ServletOutputStream servletOutputStream = response.getOutputStream();
		workBook.write(servletOutputStream);
	}
	

	// Method to get the header list
		public static List<List<String>> getHeader(HttpSession session,
				String fileId, String[] xmlNames) {
		//	logger.info("Inside the method to the header list");

			List<List<String>> listOfheaderList = new ArrayList<List<String>>(15);
			List<RecordFormat> listOfXmlRecords = readXML(session, fileId, xmlNames);
			for (RecordFormat rec : listOfXmlRecords) {
				
				List<String> headerList = new ArrayList<String>();
				List<Field> field = rec.getFieldList();			
				for (Field fieldFromRecFormat : field) {				
					FlagForMandatoryAndExcColumns excludeFromDwnload = FlagForMandatoryAndExcColumns.valueOf(fieldFromRecFormat.getExclude_From_Dwnld());	
					switch(excludeFromDwnload){
					case NO:{					
						if(((rec.getRecordProductOrCustom().equals(ProductOrCustom.CUST.toString()))&&(fieldFromRecFormat.getFiled_Name_Custom().equals("")))||(rec.getRecordProductOrCustom().equals(ProductOrCustom.PROD.toString()))){
							
							FlagForMandatoryAndExcColumns yOrNInside = FlagForMandatoryAndExcColumns.valueOf(fieldFromRecFormat.getField_Mandatory_Product());
							switch(yOrNInside){
							case YES:{
							String columnName = fieldFromRecFormat.getField_Name_Product();														
								headerList.add(getMandatoryColumnWithSuperScript(columnName));	
								break;
							}
							case NO:{
								headerList.add(fieldFromRecFormat.getField_Name_Product());
								break;
							
							}
							
							}
							
						
						}else{

							FlagForMandatoryAndExcColumns yOrNInside = FlagForMandatoryAndExcColumns.valueOf(fieldFromRecFormat.getField_Mandatory_Custom());
							switch(yOrNInside){
							case YES:{

								String columnName = fieldFromRecFormat.getFiled_Name_Custom();																		
								headerList.add(getMandatoryColumnWithSuperScript(columnName));
								break;

							
							}
							case NO:{
								headerList.add(fieldFromRecFormat.getFiled_Name_Custom());	
								break;
							}
							
							}
							
						
						}
					}
					
					}
					

					
																													
				}			
				listOfheaderList.add(headerList);

			}

			return listOfheaderList;
		}

		// Method to read the Xml file
		public static List<RecordFormat> readXML(HttpSession session, String fileId,
				String[] xmlFileName) {
		//	logger.info("Inside the method to read the XMl");		
			List<RecordFormat> listOfFormats = new ArrayList<RecordFormat>();
			RecordFormat recordFormat = null;
			try {
				InputStream fileInput = null;
				for (int index = 0; index < xmlFileName.length; index++) {
					String xmlName = xmlFileName[index];				
					String fileName = ApplicationConstant.XML_FILE_LOCATION
							.concat(xmlName);
					fileInput = session.getServletContext().getResourceAsStream(
							fileName);				
					JAXBContext jaxbContext = JAXBContext
							.newInstance(com.jaw.common.batch.pojo.XmlConfigurationForUpload.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext
							.createUnmarshaller();
					XmlConfigurationForUpload xmlClassObject = (XmlConfigurationForUpload) jaxbUnmarshaller
							.unmarshal(fileInput);
					recordFormat = xmlClassObject.getRecordFormat();				
					listOfFormats.add(recordFormat);

				}

			}catch(Exception e){
				e.printStackTrace();
			}		
			return listOfFormats;

		}
		
		public static String getMandatoryColumnWithSuperScript(String ColNameFromXML){
			String columnName = ColNameFromXML
					.concat("*");

			AttributedString as1 = new AttributedString(
					columnName);
			as1.addAttribute(TextAttribute.SUPERSCRIPT,
					TextAttribute.SUPERSCRIPT_SUPER,
					ColNameFromXML.length() - 1,ColNameFromXML.length());
			return columnName;
			
		}
		
	
}
