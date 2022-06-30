package com.jaw.batch.dao;

import java.util.List;

import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.files.dao.FileMaster;

public interface IDownloadBatchFileDao {
	public List<FileMaster> downloadFile(DownloadFileKey downloadFileKey) throws NoDataFoundException ;

}
