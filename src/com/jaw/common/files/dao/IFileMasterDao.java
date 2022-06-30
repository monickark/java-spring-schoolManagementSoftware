package com.jaw.common.files.dao;

import org.springframework.dao.DuplicateKeyException;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IFileMasterDao {

	public void insertSingleFile(FileMaster fileMaster, final Integer dbts)
			;

	public FileMaster getSingleFile(String instId, String branchId,
			String studentAdmisNo, String fileType,String srlNo)
			throws FileNotFoundInDatabase;


	public void deleteFile(String instId, String branchId,
			String studentAdmisNo, String fileType,String fileSrlNo)
			throws FileNotFoundInDatabase, DeleteFailedException;
	
	public FileMaster getSingleFileForFileTransfer(final String instId, final String branchid,
			final String linkId, final String fileType, final String srlNo)
			throws FileNotFoundInDatabase ;
	
	
	public void updateFlmtForFileTransfer(FileMaster fileMas) throws UpdateFailedException;

}
