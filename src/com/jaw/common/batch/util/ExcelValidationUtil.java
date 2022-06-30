package com.jaw.common.batch.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.jaw.common.dropdown.dao.IAllSubListByCIDAndTRM;
import com.jaw.common.dropdown.dao.ITermAndSecListDAO;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.core.dao.CourseMaster;
import com.jaw.core.dao.CourseMasterKey;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.core.dao.ICourseMasterDAO;
import com.jaw.core.dao.ICourseMasterListDAO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
@Component
public class ExcelValidationUtil {	
	
	private static final boolean CourseMaster = false;
	@Autowired
	ICourseMasterListDAO courseMasterListDao;
	@Autowired
	ITermAndSecListDAO trmAndSecListDao;
	@Autowired
	IAllSubListByCIDAndTRM allSubListByCIDAndTRM;
	
	public boolean checkForInteger(String value) {

		try {
			Double doubleValue = Double.valueOf(value);
			doubleValue.intValue();

		} catch (NumberFormatException ex) {
			return false;
		}

		return true;
	}

	public boolean validateDateFormat(String date,StringBuffer convertedDate)  {		
		try {
			
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");			
			Date date1=sdf.parse(date);			
			sdf=new SimpleDateFormat("yyyy-MM-dd");			
			 sdf.applyPattern("yyyy-MM-dd");			
			convertedDate.append(sdf.format(date1));			
			return true;
			} catch (ParseException e) {
				
				e.printStackTrace(); 
				return false;
				}		
		
	}

	public boolean checkMobileNumberFormat(String mobileNumber,StringBuffer convertedNo) {												
		boolean flag = false;		
		Pattern pattern1 = Pattern.compile("[+]\\d{2}-\\d{10}");		
		Pattern pattern2 = Pattern.compile("[0]\\d{10}");		
		Pattern pattern3 = Pattern.compile("\\d{10}");			
		try{
			double num = Double.valueOf(mobileNumber);
			DecimalFormat pattern = new DecimalFormat("#,#,#,#,#,#,#,#,#,#");
			NumberFormat testNumberFormat = NumberFormat.getNumberInstance();
			convertedNo.append(testNumberFormat.format(num).toString().replace(",",""));				
				pattern.parse(convertedNo.toString());
				flag = true;						
		}catch(Exception e){
			flag = false;
		}			
		Matcher matcher1 = pattern1.matcher(convertedNo);
		Matcher matcher2 = pattern2.matcher(convertedNo);
		Matcher matcher3 = pattern3.matcher(convertedNo);

		if ( matcher1.matches() || matcher2.matches() || matcher3.matches()){			
			flag = true;			
		}			
		return flag;
	
	

	
		
		
		
		
	}

	public boolean checkPincodeFormat(String cellValue) {
		DecimalFormat pattern = new DecimalFormat("#,#,#,#,#,#");
		NumberFormat testNumberFormat = NumberFormat.getNumberInstance();
		Number pincodeNumber = null;
		try {
			Double pin = Double.valueOf(cellValue);
			String mob = testNumberFormat.format(pin);
			pincodeNumber = pattern.parse(mob);
		} catch (Exception e) {

			return false;
		}
		Pattern pattern1 = Pattern.compile("\\d{6}");
		Matcher matcher1 = pattern1.matcher(pincodeNumber.toString());
		if (matcher1.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isAlphaNumericWithoutSpecialChar(String s) {

		String pattern = "^[A-Za-z-0-9\\s]+$";
		if (s.matches(pattern)) {

			return true;
		}

		return false;
	}

	public boolean simpleText(String s) {

		String pattern = "^[A-Za-z]+$";
		if (s.matches(pattern)) {
			return true;
		}

		return false;
	}

	public boolean isAlphaNumericWithSpecialChar(String s) {

		String pattern = "^[a-zA-Z0-9\\s].*$";

		if (s.matches(pattern)) {
			return true;
		}
		return false;
	}

	public boolean checkEmail(String email) {
		String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (email.matches(pattern)) {
			return true;
		}
		return false;
	}

	public boolean checkLandlineNumber(String landline) {
		String pattern = "^[0-9]\\d{2,4}-\\d{6,8}$";

		if (landline.matches(pattern)) {
			return true;
		}
		return false;
	}

	public boolean checkDoubleValue(String doubleValue) {
		String pattern = "\\d+\\.\\d{1,2}";
		if (doubleValue.matches(pattern)) {
			return true;
		}
		return false;
	}

	public boolean checkBooleanValue(String value) {

		return "true".equalsIgnoreCase(value)
				|| "false".equalsIgnoreCase(value);
	}

	// Year validation
	public boolean checkYearValue(String year) {

		boolean flag = false;
		try {
			Double doubleValue = Double.valueOf(year);
			Integer yearValue = doubleValue.intValue();
			String pattern = "^[12][0-9]{3}$";

			if (yearValue.toString().matches(pattern)) {

				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}

		return flag;
	}

	public boolean sizeValidation(Integer sizeValuefromXml,
			Integer sizeValueFromExcel) {
		if (sizeValueFromExcel <= (sizeValuefromXml)) {
			return true;
		} else {
			return false;
		}

	}

	  public String  cocdValidation(String cocdType,String cocdDescription,
			ApplicationCache applicationCache,String instId,String branchId) {			 
		  
		String codeFromUtil = CommonCodeUtil.getCodeByDescription(cocdType,
				applicationCache, cocdDescription,instId,branchId);		
		return codeFromUtil;

	}
	 

	public boolean nameValidation(String cellValue) {
		String pattern = "^[A-Za-z-\\s']{1,}[\\.]{0,1}[A-Za-z\\s']{0,}$";
		String pattern5 = "^[A-Za-z-\\s']{1,}{0,30}[A-Za-z\\s']{0,}$";
		String pattern1 = "^[A-Za-z-\\s']{1,}[\\.][A-Za-z-\\s']{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
		String pattern2 = "^{1,}[A-Za-z\\s']{0,}[\\.]{0,1}[A-Za-z-\\s']$";
		String pattern3 = "^{1,}[A-Za-z\\s']{0,}[\\.]{0,1}[A-Za-z-\\s'][\\.]{0,1}[A-Za-z-\\s']$";
		String pattern4 = "^[A-Za-z-\\s']{1,}[\\.][A-Za-z-\\s']{1,}[\\.][A-Za-z-\\s']{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";		

		if (cellValue.trim().matches(pattern)||cellValue.trim().matches(pattern1)||cellValue.trim().matches(pattern2)||cellValue.trim().matches(pattern3)||cellValue.trim().matches(pattern4)||cellValue.trim().matches(pattern5)) {
			return true;
		}
		return false;

	}
	public boolean addressValidation(String cellValue) {
		String pattern = "^[a-zA-Z0-9\\s'#].*$";		
		if (cellValue.matches(pattern)) {
			return true;
		}
		return false;

	}
	
	public boolean phoneValidation(String cellValue,StringBuffer convertedNo) {
		
		boolean flag = false;	
		String patternNew = "^[0-9]\\d{2,4}-\\d{6,8}$";
		Pattern pattern1 = Pattern.compile("[+]\\d{2}-\\d{10}");		
		Pattern pattern2 = Pattern.compile("[0]\\d{10}");		
		Pattern pattern3 = Pattern.compile("\\d{10}");
		
		if (cellValue.matches(patternNew) ) {			
			flag = true;
		}else {			
			try{
			double num = Double.valueOf(cellValue);
			DecimalFormat pattern = new DecimalFormat("#,#,#,#,#,#,#,#,#,#");
			NumberFormat testNumberFormat = NumberFormat.getNumberInstance();
			convertedNo.append(testNumberFormat.format(num).toString().replace(",",""));			
			pattern.parse(convertedNo.toString());
				flag = true;						
			}catch(Exception e){
				flag = false;
			}			
		}
		Matcher matcher1 = pattern1.matcher(convertedNo);
		Matcher matcher2 = pattern2.matcher(convertedNo);
		Matcher matcher3 = pattern3.matcher(convertedNo);

		if ( matcher1.matches() || matcher2.matches() || matcher3.matches()){			
			flag = true;			
		}				
		return flag;
	
	

	}		
		
		public ArrayList<String> hasDuplicates(List<String> comparisonList,
				 String columnname,String sheetName) {			
			Set<Object> set = new HashSet<Object>();
			ArrayList<String> errorList = new ArrayList<String>();
			for (int index = 0; index < comparisonList.size(); index++) {
				if (!set.add(comparisonList.get(index))) {
					Integer recordNo = index;
					recordNo++;
					String errorMessage = "Sheet Name :"+ sheetName+ ","+ "Record No:"+recordNo+","+"Column Name :"+columnname+"This Record is already found in this excel;";			
					errorList.add(errorMessage);
				}
			}

			return errorList;

		}
		
		
		public HashMap<String,String> courseMasterCahce(String instId,String branchId) throws NoDataFoundException{
			HashMap<String,String> getCourseMasCache = new HashMap<String,String>();
			CourseMasterKey courseMasKey = new CourseMasterKey();
			courseMasKey.setInstId(instId);
			courseMasKey.setBranchId(branchId);
			List<CourseMaster> courseMasterList = courseMasterListDao.getCourseMasterList(courseMasKey);
			for(CourseMaster courseMas :courseMasterList){				
				getCourseMasCache.put(courseMas.getCourseName(), courseMas.getCourseMasterId());
			}
			return getCourseMasCache;
		}
		
		
		public HashMap<String,String> courseVariantCahce(String instId,String branchId) throws NoDataFoundException{
			HashMap<String,String> getCourseMasCache = new HashMap<String,String>();
			CourseMasterKey courseMasKey = new CourseMasterKey();
			courseMasKey.setInstId(instId);
			courseMasKey.setBranchId(branchId);
			List<CourseMaster> courseMasterList = courseMasterListDao.getCourseMasterList(courseMasKey);
			for(CourseMaster courseMas :courseMasterList){				
				getCourseMasCache.put(courseMas.getCourseMasterId(),courseMas.getCvAppcble());
			}
			return getCourseMasCache;
		}
		
		
		public Map<String,Map<String,String>> sectionCahce(String instId,String branchId) throws NoDataFoundException{
			
			Map<String,Map<String,String>> secListMap = trmAndSecListDao.getsecListForBatch( instId, branchId);												
			return secListMap;
		}

		
		public Map<String,Map<String,Map<String,String>>> subjectCahce(String instId,String branchId) throws NoDataFoundException{
			List<CourseSubLink> listOfCrslRec = allSubListByCIDAndTRM.getAllSubjectListForBatch(instId, branchId);
			
			Map<String,Map<String,Map<String,String>>> allSubRecordsWithCourseAndTerm = new LinkedHashMap<String,Map<String,Map<String,String>>>();
			for(CourseSubLink crslRec:listOfCrslRec){
				if(allSubRecordsWithCourseAndTerm.containsKey(crslRec.getTermId().trim()+crslRec.getCourseMasterId().trim())){
					Map<String,Map<String,String>> subListMapWithSubType = allSubRecordsWithCourseAndTerm.get(crslRec.getTermId().trim()+crslRec.getCourseMasterId().trim());
					if(subListMapWithSubType.containsKey(crslRec.getSubType())){
						Map<String,String> subList = subListMapWithSubType.get(crslRec.getSubType());
						subList.put(crslRec.getSubId(), crslRec.getSubName());							
						
					}else{
						Map<String,String> newsubList = new LinkedHashMap<String,String>();
						newsubList.put(crslRec.getSubId(), crslRec.getSubName());
						subListMapWithSubType.put(crslRec.getSubType(), newsubList);
					}
					
				}else{									
					Map<String,Map<String,String>> newSubListMapWithSubType = new LinkedHashMap<String,Map<String,String>>(); 
					Map<String,String> newSubList = new LinkedHashMap<String,String>();
					newSubList.put(crslRec.getSubId(), crslRec.getSubName());
					newSubListMapWithSubType.put(crslRec.getSubType(), newSubList);
					allSubRecordsWithCourseAndTerm.put(crslRec.getTermId().trim()+crslRec.getCourseMasterId().trim(), newSubListMapWithSubType);			
					
				}
				
			}
												
			return allSubRecordsWithCourseAndTerm;
		}
		
}
