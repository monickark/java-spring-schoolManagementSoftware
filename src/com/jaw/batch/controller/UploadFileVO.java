package com.jaw.batch.controller;
import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

//UI VO class for the upload excel file
public class UploadFileVO implements Serializable{
	
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String dataType = "";
	private String fileId = "";
	private String delFlg = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";	
	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	private MultipartFile excelFile;
	private String fileLocation;
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	
	public Integer getDbTs() {
		return dbTs;
	}

	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
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

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getrModId() {
		return rModId;
	}

	public void setrModId(String rModId) {
		this.rModId = rModId;
	}

	public String getrModTime() {
		return rModTime;
	}

	public void setrModTime(String rModTime) {
		this.rModTime = rModTime;
	}

	public String getrCreId() {
		return rCreId;
	}

	public void setrCreId(String rCreId) {
		this.rCreId = rCreId;
	}

	public String getrCreTime() {
		return rCreTime;
	}

	public void setrCreTime(String rCreTime) {
		this.rCreTime = rCreTime;
	}

	public MultipartFile getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(MultipartFile excelFile) {
		this.excelFile = excelFile;
	}
}
