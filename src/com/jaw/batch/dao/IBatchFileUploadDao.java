package com.jaw.batch.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.common.files.dao.FileHistory;
import com.jaw.common.files.dao.FileMaster;

public interface IBatchFileUploadDao {
public void batchFileUpload(BatchFileUpload batchFileUpload,ArrayList<MulitpartFileObject> listOfFiles,Integer startingSeq,final List<String> LISTOFLINKID,final List<String> LISTOFSRLNO) throws RuntimeExceptionForBatch;
public List<String> getLinkId(String instId,String branchId,String fileType) throws NoDataFoundException;
public List<FileMaster> getEntireExistingRec(String instId,String branchId,String fileType,List<String> linkId) throws DatabaseException;
public void insertIntoFlhtbeforeDelExistingRec(List<FileHistory> fileHistory,Integer startingSeq) throws RuntimeExceptionForBatch;
public void batchDeleteExistingFiles(String instId,String branchId,String fileType,List<String> linkId) throws RuntimeExceptionForBatch;
}
