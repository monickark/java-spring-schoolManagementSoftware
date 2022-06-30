package com.jaw.batch.controller;
import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

//UI VO class for ExportData
public class BatchDataUploadVO implements Serializable{
	
	private String instId = "";
	private String branchId = "";
	private String dataType = "";	
	private String batchName = "";
	private MultipartFile uploadFile; 
	

	public String getBatchName() {
		return batchName;
	}
	@Override
	public String toString() {
		return "BatchDataUploadVO [instId=" + instId + ", branchId=" + branchId
				+ ", dataType=" + dataType + ", batchName=" + batchName
				+ ", uploadFile=" + uploadFile + "]";
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	

}
