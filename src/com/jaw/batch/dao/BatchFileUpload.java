package com.jaw.batch.dao;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class BatchFileUpload {
	private CommonsMultipartFile [] files;
	 private String instId;
	 private String branchId;
	 private String fileType;		 
	 private ArrayList<String> fileName;
	 private ArrayList<String> filePath;
	
	
	public CommonsMultipartFile[] getFiles() {
		return files;
	}	
	public void setFiles(CommonsMultipartFile[] files) {
		this.files = files;
	}
	public String getBranchId() {
		return branchId;
	}
	public String getInstId() {
		return instId;
	}	
	@Override
	public String toString() {
		return "BatchFileUpload [files=" + Arrays.toString(files) + ", instId="
				+ instId + ", branchId=" + branchId + ", fileType=" + fileType
				+ ", fileName=" + fileName + ", filePath=" + filePath + "]";
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public ArrayList<String> getFileName() {
		return fileName;
	}
	public void setFileName(ArrayList<String> fileName) {
		this.fileName = fileName;
	}
	public ArrayList<String> getFilePath() {
		return filePath;
	}
	public void setFilePath(ArrayList<String> filePath) {
		this.filePath = filePath;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
