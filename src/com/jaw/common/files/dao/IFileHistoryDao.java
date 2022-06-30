package com.jaw.common.files.dao;

import org.springframework.dao.DuplicateKeyException;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IFileHistoryDao {

	public void insertSingleFile(FileHistory fileHistory, final Integer dbts)
			throws  DuplicateEntryException;
	
	public FileHistory getSingleFileForFileTransfer(final String instId, final String branchid,
			final String linkId, final String fileType, final String srlNo)
			throws FileNotFoundInDatabase;
	
	public void updateFlhtForFileTransfer(final FileHistory fileHis) throws UpdateFailedException;

}
