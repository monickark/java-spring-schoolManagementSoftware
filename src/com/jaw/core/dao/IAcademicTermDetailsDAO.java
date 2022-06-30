package com.jaw.core.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.fee.dao.FeeMasterStatusKey;

public interface IAcademicTermDetailsDAO {
	void insertAcademicTermDetailsRec(
			final AcademicTermDetails academicTermDetailsRecord)
			throws DuplicateEntryException;
	
	void updateAcademicTermDetailsRec(
			final AcademicTermDetails academicTermDetailsRecord,
			final AcademicTermDetailsKey academicTermDetailsKey)
			throws UpdateFailedException;
	
	void deleteAcademicTermDetailsRec(
			final AcademicTermDetails academicTermDetailsRecord,
			final AcademicTermDetailsKey academicTermDetailsKey)
			throws DeleteFailedException;
	
	AcademicTermDetails selectAcademicTermDetailsRec(
			final AcademicTermDetailsKey academicTermDetailsKey)
			throws NoDataFoundException;
	AcademicTermDetails selectAcademicTermDetailsDelFlgRec(
			final AcademicTermDetailsKey academicTermDetailsKey)
			;
	boolean checkAcademicTermStatusRec(
			final AcademicTermDetailsKey academicTermDetailsKey,String action);
	
	public AcademicTermDetails getAcaTrmRecForHolidayGeneration(
			final AcademicTermDetailsKey academicTermDetailsKey);
	
	public AcademicTermDetails checkAcTermValidation(
			final AcademicTermDetailsKey academicTermDetailsKey) throws NoDataFoundException;
	
	public String getCurrentAcademicTerm(String instId,String branchId,String status) throws NoDataFoundException;
	
	void updateAcademicTermToDelFlgNRecs(
			final AcademicTermDetails academicTermDetailsRecord,
			final AcademicTermDetailsKey academicTermDetailsKey)
			throws UpdateFailedException;
	int checkAcademicTermStatusForStudentPromotion(final AcademicTermDetailsKey academicTermDetailsKey);
	void updateAcademicTermDetailsStatusRec(
			final AcademicTermDetails academicTermDetailsRecord,
			final AcademicTermDetailsKey academicTermDetailsKey)
			throws UpdateFailedException;
	
	 String checkAcademicTermPromoteStatus(AcademicTermDetailsKey academicTermDetailsKey)
			throws NoDataFoundException;
	 
	 int checkAcademicTermPromotionStatus(final AcademicTermDetailsKey academicTermDetailsKey);
}
