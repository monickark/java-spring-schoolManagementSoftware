package com.jaw.batch.controller;

import java.io.InputStream;

public class DownLoadFileVO {
	private String instId = "";
	private String branchId = "";
	private String fileType = "";
	private String linkId = "";
	private String contentType = "";
	private InputStream inputStream ;
	public String getLinkId() {
		return linkId;
	}
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
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
