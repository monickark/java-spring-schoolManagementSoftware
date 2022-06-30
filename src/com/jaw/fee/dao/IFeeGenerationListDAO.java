package com.jaw.fee.dao;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IFeeGenerationListDAO {
public List<StudentMasterForFeeGen> getStudentListForFeeGeneration(StudentMasterForFeeGenKey studentMasterForFeeGenKey) throws NoDataFoundException;

List<FeeMasterListForFeeGen> getFeeMasterNonElectiveListForFeeGeneration(FeeMasterKey feeMasterKey)
		throws NoDataFoundException;
List<FeeMasterListForFeeGen> getFeeMasterElectiveListForFeeGeneration(FeeMasterKey feeMasterKey)
		throws NoDataFoundException;

List<FeeMasterStatus> getFeeStatusListForValidation(FeeMasterStatus feeMasterStatus,List<String> terms)
		throws NoDataFoundException;

List<FeeMasterListForFeeGen> getFeeMasterCourseVariantListForFeeGeneration(FeeMasterKey feeMasterKey)
		throws NoDataFoundException;

List<StudentFee> getTransportFeeForFeeGeneration(
		FeeMasterKey feeMasterKey) throws NoDataFoundException;
}
