package com.jaw.batch.controller;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class BatchFileUploadVO {
	 private CommonsMultipartFile [] files;
	 private String instId;
	 private String branchId;
	 private String fileType;

	    public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

		public String getBranchId() {
		return branchId;
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

		public CommonsMultipartFile[] getFiles() {
	        return files;
	    }
	   
		public void setFiles( CommonsMultipartFile[] files ) {
	        this.files = files;
	    }
}
