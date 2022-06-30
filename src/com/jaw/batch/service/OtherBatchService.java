package com.jaw.batch.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.student.admission.dao.IPrevAcademicDetailsList;
import com.jaw.student.admission.dao.PrevAcademicDetails;

@Service
public class OtherBatchService implements IOtherBatchService {
	@Autowired
	IPrevAcademicDetailsList prevAcademicDetailsList;

	@Override
	public void updatePsdeTableColumns() throws NoDataFoundException, BatchUpdateFailedException {	
		
		//select Existing list
		List<PrevAcademicDetails> listOFPrevDetails = prevAcademicDetailsList.getListForColumnUpdate();
		
		//Creating New List
		List<PrevAcademicDetails> listOFPrevDetailsnew = new ArrayList<PrevAcademicDetails>();
		
		//Actual Conversion-add into new list
		for(PrevAcademicDetails prev :listOFPrevDetails){
			if((prev.getMarkObtPrevStd()!=null)&&(!prev.getMarkObtPrevStd().equals(""))){								
				
				prev.setPercentageObtained(Double.valueOf(prev.getMarkObtPrevStd()));			
				prev.setMarkObtPrevStd(null);
				listOFPrevDetailsnew.add(prev);
			}
			
		}
				
		//Update the new list in DB
		prevAcademicDetailsList.batchUpdateForColumns(listOFPrevDetailsnew);		
	}

}
