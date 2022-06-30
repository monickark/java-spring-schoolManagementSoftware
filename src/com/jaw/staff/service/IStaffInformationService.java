package com.jaw.staff.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.controller.FileTypeVO;
import com.jaw.staff.controller.StaffListSearchVo;
import com.jaw.staff.controller.StaffListVo;
import com.jaw.staff.controller.StaffVo;

public interface IStaffInformationService {
	
	public abstract void updateStaff(StaffVo adminVO,
			UserSessionDetails userSessionDetails)
			throws FileNotFoundInDatabase, DuplicateEntryException,
			InvalidUserIdException, UpdateFailedException;
	
	public abstract List<StaffListVo> selectStaff(
			StaffListSearchVo staffListSearchVo,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;
	
	void deleteStaff(StaffVo adminVO, UserSessionDetails userSessionDetails,
			ApplicationCache appl) throws FileNotFoundInDatabase,
			DuplicateEntryException, InvalidUserIdException,
			UpdateFailedException, DatabaseException, NoDataFoundException,
			TableNotSpecifiedForAuditException, BatchUpdateFailedException;
	
	void updateStaffEditProfile(ApplicationCache applicationCache,
			StaffVo adminVO, UserSessionDetails userSessionDetails,ServletContext servletContext)
			throws FileNotFoundInDatabase, DuplicateEntryException,
			InvalidUserIdException, UpdateFailedException, DatabaseException,
			NoDataFoundException, TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException;
	
	void updateStaffFullDetails(StaffVo adminVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,ServletContext servletContext) throws FileNotFoundInDatabase,
			DuplicateEntryException, InvalidUserIdException,
			UpdateFailedException, DatabaseException, TableNotSpecifiedForAuditException,
			NoDataFoundException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException;
	
	void selectStaff(StaffVo staffAdmissionVo, String staffId, UserSessionDetails userSessionDetails)
			throws NoDataFoundException;

	FileTypeVO getFileType(UserSessionDetails userSessionDetails, String staffId)
			throws NoDataFoundException;

	void staffTransferProcess(StaffVo adminVO,
			UserSessionDetails userSessionDetails) throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, BatchUpdateFailedException;
	
}
