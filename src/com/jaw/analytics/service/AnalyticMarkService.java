package com.jaw.analytics.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.admin.dao.BranchMaster;
import com.jaw.analytics.controller.ViewAnalyticMark;
import com.jaw.analytics.controller.ViewMarkList;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mark.dao.IStudentReportCardDAO;
import com.jaw.mark.dao.StuMrksRmksListKey;
@Service
public class AnalyticMarkService implements IAnalyticMarkService {
	// Logging
	Logger logger = Logger.getLogger(AnalyticMarkService.class);	
	@Autowired
	IStudentReportCardDAO studentReportCardDAO;
	@Autowired
	CommonCodeUtil commonCodeUtil;
	@Override
	public ViewAnalyticMark selectStudentSubjectMarksForBarChart(
			StuMrksRmksListKey stuMrksRmksListKey,ApplicationCache applicationCache,SessionCache sessionCache) throws NoDataFoundException, CommonCodeNotFoundException {
		List<CommonCode> commonCodes= commonCodeUtil.getCommonCodeListByType(
				applicationCache, CommonCodeConstant.EXAMID, sessionCache
				.getUserSessionDetails().getInstId(), sessionCache.getUserSessionDetails().getBranchId());
		Map<String, String> exmMap = new LinkedHashMap<String, String>();
		for (CommonCode commonCode : commonCodes) {
			exmMap.put(commonCode.getCommonCode(),
					commonCode.getCodeDescription());
		}
		//Get the marks report
		List<ViewMarkList> viewMarksList=studentReportCardDAO.getStudentSubjectMarks(stuMrksRmksListKey);
		//divide the list as exam values pair
		LinkedHashMap<String,List<ViewMarkList>> objectsByExamId =
		        new LinkedHashMap<String,List<ViewMarkList>>();
		 List<String> examList = new ArrayList<String>();
		  Set<String> subjectSet = new LinkedHashSet<String>();
		  Set<String> subjectNameSet = new LinkedHashSet<String>();
		  Set<String> shortCodeSet = new LinkedHashSet<String>();
		    for (ViewMarkList obj : viewMarksList) {
		    	System.out.println("obj.getCrslId() : "+obj.getCrslId());
		    	 subjectSet.add(obj.getCrslId());
		    	 subjectNameSet.add(obj.getSubName());
		    	 shortCodeSet.add(obj.getShortCode());
		        List<ViewMarkList> yearList = objectsByExamId.get(obj.getExamId());
		        if (yearList == null) {
		        	examList.add(exmMap.get(obj.getExamId()));
		            objectsByExamId.put(obj.getExamId(), yearList = new ArrayList<ViewMarkList>());
		        }
		        yearList.add(obj);          
		    }
		    //All subject list(crsl id)
		    List<String> subjectCrslList = new ArrayList<String>(subjectSet);	
		    //All subject list(Subject Name)
		   List<String> subjectNameList = new ArrayList<String>(subjectNameSet);	
		   List<String> shortCodeList = new ArrayList<String>(shortCodeSet);	
		    //list for holding all subject marks by exam 
		    List<List<Integer>> maxmarkObtainedListForAll=new ArrayList<List<Integer>>();
		    Iterator its = subjectNameSet.iterator();
		   
		    
		   for(int j=0;j<subjectCrslList.size();j++){	
			   //list for holding single subject by exam
			   List<Integer> maxmarkObtainedForSingleSubject=new ArrayList<Integer>();
			   //Iterate the map get the one subject marks for each exam
		    	 Iterator it1 = objectsByExamId.entrySet().iterator();
		    while (it1.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it1.next();
		       
		        System.out.println("Map"+pairs.getKey() + " = " + pairs.getValue());
		        List<ViewMarkList> viwMapList=(List<ViewMarkList>) pairs.getValue();
		        boolean isValue=false;
		        for(int as=0;as<viwMapList.size();as++){
		        if(subjectCrslList.get(j).equals(viwMapList.get(as).getCrslId())){		        	
		        	maxmarkObtainedForSingleSubject.add(viwMapList.get(as).getMaxMarkObt());
		        	isValue=true;
		        }		      
		        }
		        //If this subject mark is not there in all subject then we should add to 0
		        if(!isValue){		        	
		        	maxmarkObtainedForSingleSubject.add(0);		        	
		        }
		        
		    }		   
		    maxmarkObtainedListForAll.add(maxmarkObtainedForSingleSubject);
		    }
		    
		 
		    List<Integer[]> valuesList=new ArrayList<Integer[]>();
		    
		   for(int e=0;e<examList.size();e++){
			   List<Integer> maxmarkObtainedo1=new ArrayList<Integer>();
		    for(int r=0;r<maxmarkObtainedListForAll.size();r++){
		    	 List<Integer> maxmarkObtained=new ArrayList<Integer>();
		    	System.out.println("viewwwwwwwwwwwww   : "+maxmarkObtainedListForAll.get(r)  );
		    	 for(int k=0;k<maxmarkObtainedListForAll.get(r).size();k++){
		    		 if(k==e){
		    			 System.out.println("may be exception" );
		    			 maxmarkObtainedo1.add(maxmarkObtainedListForAll.get(r).get(k));
		    		 }
		    	 }
		    	 System.out.println("viewwwwwwwwwwwww   finish" );
		    }
		    System.out.println("to convert array" +maxmarkObtainedo1.size());
		    Integer [] subjectMarks = maxmarkObtainedo1.toArray(new Integer[maxmarkObtainedo1.size()]);
		    valuesList.add(subjectMarks);
		   }
		   System.out.println("finish for loop" );
		   ViewAnalyticMark viewAnalyticMark=new ViewAnalyticMark();
		   viewAnalyticMark.setExamList(examList.toArray(new String[examList.size()]));
		   viewAnalyticMark.setListOfExamArray(valuesList);
		   viewAnalyticMark.setSubjectList(subjectNameList.toArray(new String[subjectNameList.size()]));
		   viewAnalyticMark.setShortcodeList(shortCodeList.toArray(new String[shortCodeList.size()]));
		return viewAnalyticMark;
	}

}
