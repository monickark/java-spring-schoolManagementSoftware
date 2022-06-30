package com.jaw.batch.dao;

public class DownloadFileKey {
	
	private String instId;
	private String branchId;
	private String linkId;	
	private String fileType = "";

	
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	@Override
	public String toString() {
		return "DownloadFileKey [instId=" + instId + ", branchId=" + branchId
				+ ", linkId=" + linkId + ", fileType=" + fileType + "]";
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}	
	
}
