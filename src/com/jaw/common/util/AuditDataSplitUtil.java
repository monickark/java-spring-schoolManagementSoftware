package com.jaw.common.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.stereotype.Component;
import com.jaw.common.util.dao.ComCodeColumn;
import com.jaw.framework.appCache.ApplicationCache;
@Component
public class AuditDataSplitUtil {

	
	public static LinkedHashMap<String,String> splitAuditRec(String tableKey,
			List<ComCodeColumn> comCodeColList) {
		LinkedHashMap<String, String> mapForColumnAndValue = new LinkedHashMap<String,String>();
		String tableKeys[] = tableKey.split("\\^");
		for (String tableKeyValue : tableKeys) {
			ArrayList<String> valueList = new ArrayList<String>();
			String tableColumnAndValue[] = tableKeyValue.split("=");				
			if (tableColumnAndValue.length > 1) {		
				mapForColumnAndValue.put(tableColumnAndValue[0], tableColumnAndValue[1]);
			}else {
				valueList.add("");
				mapForColumnAndValue.put(tableColumnAndValue[0], "");
			}
			
			
		}	
		return mapForColumnAndValue;
	}
	
	public static String assignValuesFromCCCL(List<ComCodeColumn> comCodeColList,String tableColAndValue){
		String valueFromCCCL = "";
		if ((comCodeColList!=null)&&(comCodeColList.size() != 0)) {
			for (ComCodeColumn comCodeColumn : comCodeColList) {
				if (comCodeColumn.getColName().equals(tableColAndValue)) {
					valueFromCCCL =	comCodeColumn.getCodeType();
					
				}
			}
		}
		
		return valueFromCCCL;
	}
	
	
}
