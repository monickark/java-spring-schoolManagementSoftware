package com.jaw.batch.dao;

import java.util.List;

import com.jaw.common.files.dao.FileHistory;
import com.jaw.common.files.dao.FileMaster;

public interface IFileTransferDBToDiskListDao {
	
	public List<FileMaster> getFileObjectForTransfer(String instId,String branchId,final String typeOfOpt);
	
	public List<FileHistory> getFileObjectForTransferFlht(final String instId,
			final  String branchId,final String typeOfOpt);

}
