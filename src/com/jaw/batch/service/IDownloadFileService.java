package com.jaw.batch.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import com.jaw.batch.controller.DownLoadFileVO;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IDownloadFileService {
public List<DownLoadFileVO> downloadFile(DownLoadFileVO downloadFile,UserSessionDetails userSessionDetails,
		ApplicationCache applicationCache,ServletContext servletContext,String profileGrp) throws NoDataFoundException, FileNotFoundException, IOException;
}
