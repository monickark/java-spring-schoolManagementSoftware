package com.jaw.fee.dao;

import com.jaw.student.admission.dao.StudentMasterKey;

public interface IFeeGenerationDAO {
int checkFeeMasterEntered(FeeStatusKey feeStatusKey);
int checkStudentsAvailable(StudentMasterKey studentMasterKey,String courseMasterId,String termId);
FeeMasterListForFeeGen checkCourseVariantExist(FeeMasterKey feeMasterKey);
}
