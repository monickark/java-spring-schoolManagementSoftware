package com.jaw.admin.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jaw.admin.dao.DataLog;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.util.AuditDataSplitUtil;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.dao.ComCodeColumn;
import com.jaw.common.util.dao.IComCodeColumnListDao;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;


@Component
public class DataLogUtil {
	
	static Logger logger = Logger.getLogger(DataLogUtil.class);
	@Autowired
	IComCodeColumnListDao comCodeColumnList;
	
	public static LinkedHashMap<String, ArrayList<String>> getDataLogMessage(
			DataLog auditPojoRec, List<ComCodeColumn> comCodeColList,ApplicationCache applicationCache) {
		LinkedHashMap<String,String> iteratorMap = new LinkedHashMap<String, String>();
		
		LinkedHashMap<String, ArrayList<String>> columnAndValuePair = new LinkedHashMap<String, ArrayList<String>>();	
		//For Old Rec
		LinkedHashMap<String,String> auditKeyAndValueOldRec = null;
		if(!auditPojoRec.getOldRecord().equals("")){
				auditKeyAndValueOldRec = AuditDataSplitUtil.splitAuditRec(auditPojoRec.getOldRecord(), comCodeColList);
		}	
		
		//For New Rec
		String splittedStringNewRec[] = auditPojoRec.getNewRecord().split("\\|");
		LinkedHashMap<String,String> auditKeyAndValueNewRec = null;		
		if(splittedStringNewRec.length>1){			
		 auditKeyAndValueNewRec = AuditDataSplitUtil.splitAuditRec(splittedStringNewRec[1], comCodeColList);		
		 iteratorMap = auditKeyAndValueNewRec;
		}else{
			iteratorMap = auditKeyAndValueOldRec;
		}
		
		
		for (Map.Entry<String, String> entry : iteratorMap.entrySet()) {
			ArrayList<String> newAndOldRec = new ArrayList<String>();
			//Adding new REc Value		


			if((splittedStringNewRec[0].equals(AuditConstant.TYPE_OF_OPER_DELETE))){
				//Addming NEw Rec				
					newAndOldRec.add("");			
				//Adding Old REc value			
				newAndOldRec.add(entry.getValue());				
			}else if((splittedStringNewRec[0].equals(AuditConstant.TYPE_OF_OPER_UPDATE))){
				//Addming NEw Rec						
					newAndOldRec.add(iteratorMap.get(entry.getKey()));			
				//Adding Old REc value			
				newAndOldRec.add(auditKeyAndValueOldRec.get(entry.getKey()));
			}else{
				//Adding New REc value		
				newAndOldRec.add(entry.getValue());				
				//Adding old rec				
					newAndOldRec.add("");			
				
			}

			
			
			//Check for CCCL Column 						
			String ccclColumnIfAny = AuditDataSplitUtil.assignValuesFromCCCL(comCodeColList, entry.getKey());			
			if(!ccclColumnIfAny.equals("")){
				newAndOldRec.add(ccclColumnIfAny);
			}
			//Put the Column Name and List containing Old Rec & New Rec in a Map 		
			columnAndValuePair.put(entry.getKey(), newAndOldRec);									
		}	
		
		return columnAndValuePair;
	}
	
	public static LinkedHashMap<String, ArrayList<String>> splitTableKey(String tableKey,
			List<ComCodeColumn> comCodeColList,ApplicationCache applicationCache,UserSessionDetails userSessionDetails) {
		LinkedHashMap<String,ArrayList<String>> tableKeyAndValuePair = new LinkedHashMap<String, ArrayList<String>>();
		Map<String,String> tableKeyAndValue = 	AuditDataSplitUtil.splitAuditRec(tableKey, comCodeColList);
		for (Map.Entry<String, String> entry : tableKeyAndValue.entrySet()) {
			ArrayList<String> newAndOldRec = new ArrayList<String>();
			String codeTypeFromCCCL = AuditDataSplitUtil.assignValuesFromCCCL(comCodeColList, entry.getKey());
			if(!codeTypeFromCCCL.equals("")){
				String description = CommonCodeUtil.getDescriptionByTypeAndCode(applicationCache, codeTypeFromCCCL, entry.getValue(), userSessionDetails.getInstId(), userSessionDetails.getBranchId());
				newAndOldRec.add(description);
			}else{
				newAndOldRec.add(entry.getValue());
			}
			tableKeyAndValuePair.put(entry.getKey(), newAndOldRec);
		}
		return tableKeyAndValuePair;
	}	
}
