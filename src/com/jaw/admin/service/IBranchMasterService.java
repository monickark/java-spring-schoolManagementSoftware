package com.jaw.admin.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import com.jaw.admin.controller.BranchMasterListVO;
import com.jaw.admin.controller.BranchMasterVO;
import com.jaw.admin.dao.BranchMaster;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

public interface IBranchMasterService {

	public void insertIntoBranchMaster(ApplicationCache applicationCahce,
			BranchMasterVO branchVO, SessionCache session,ServletContext servletContext)
			throws DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException;

	public BranchMaster selectBranchMasterRecord(
			BranchMasterListVO branchMasterListVO,
			BranchMasterVO branchMasterVORecord, SessionCache sessionCache)
			throws NoDataFoundException;

	List<BranchMasterListVO> selectBranchMasterList(String instId)
			throws NoDataFoundException;

	public void updateBranchMaster(BranchMasterListVO oldRec,
			ApplicationCache applicationCache, BranchMasterVO branchMasterVo,
			SessionCache sessionCache,ServletContext servletContext) throws NoDataFoundException,
			UpdateFailedException, DuplicateEntryException,
			FileNotFoundInDatabase, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException;

	public void deleteBranchMaster(ApplicationCache applicationCache,
			BranchMasterListVO branchMasterVoRecord, SessionCache sessionCache)
			throws NoDataFoundException, DeleteFailedException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException;

	public BranchMasterListVO selectBranchMasterRecordAfterUpdate(
			BranchMasterVO branchMasterVO, SessionCache sessionCache)
			throws NoDataFoundException;

}
