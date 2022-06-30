package com.jaw.common.files.dao;

import java.util.HashMap;
import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IFileMasterListDao {

	public void fileBatch(final List<FileMaster> files, Integer startingSeq) throws DuplicateEntryException;

	List<FileMaster> retriveFileMaster(String studentAdmisNo) throws NoDataFoundException;

	public void deleteFile(final List<FileMaster> files)
			throws FileNotFoundInDatabase;

	public List<FileMaster> fileTypeList(String instId, String branchId,
			String studentAdmisNo) ;

}
