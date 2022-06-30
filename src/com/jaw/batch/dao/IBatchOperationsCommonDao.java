package com.jaw.batch.dao;

import java.util.List;

public interface IBatchOperationsCommonDao {
	
	public List<String> retriveListOfRecForDuplicateCheck(String uniqueProp,
			List<String> valueList,String tblName,String instId,String branchId);
	
	public String getNoOfRec(String tblName);

}
