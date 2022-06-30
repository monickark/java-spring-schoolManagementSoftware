package com.jaw.common.files.dao;

import java.io.InputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileHistory implements Serializable {

	// Logging
	static Logger logger = Logger.getLogger(FileMaster.class);

	// Properties
	private String fileHtSrlNo;
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String fileSrlno = "" ;
	private MultipartFile file ;	
	@Override
	public String toString() {
		return "FileHistory [fileHtSrlNo=" + fileHtSrlNo + ", dbTs=" + dbTs
				+ ", instId=" + instId + ", branchId=" + branchId
				+ ", fileSrlno=" + fileSrlno + ", file=" + file + ", linkId="
				+ linkId + ", fileRefno=" + fileRefno + ", fileType="
				+ fileType + ", contentType=" + contentType + ", inputStream="
				+ inputStream + ", delFlg=" + delFlg + ", flmtRCreId="
				+ flmtRCreId + ", flmtRCreTime=" + flmtRCreTime + ", rCreId="
				+ rCreId + ", rCreTime=" + rCreTime + ", size=" + size
				+ ", fileName=" + fileName + ", filepath=" + filepath + "]";
	}

	private String linkId = "";
	private String fileRefno;
	private String fileType = "";
	private String contentType = "";
	private InputStream inputStream;
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFileSrlno() {
		return fileSrlno;
	}

	public void setFileSrlno(String fileSrlno) {
		this.fileSrlno = fileSrlno;
	}

	private String delFlg = "";
	private String flmtRCreId = "";
	private String flmtRCreTime;
	private String rCreId = "";
	private String rCreTime;
	private Long size;
	private String fileName ="" ;
	private String filepath ="";

	public String getFileHtSrlNo() {
		return fileHtSrlNo;
	}

	public void setFileHtSrlNo(String fileHtSrlNo) {
		this.fileHtSrlNo = fileHtSrlNo;
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

	public String getContentType() {
		return contentType;
	}

	public String getFileRefno() {
		return fileRefno;
	}

	public void setFileRefno(String fileRefno) {
		this.fileRefno = fileRefno;
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

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getFlmtRCreId() {
		return flmtRCreId;
	}

	public void setFlmtRCreId(String flmtRCreId) {
		this.flmtRCreId = flmtRCreId;
	}

	public String getFlmtRCreTime() {
		return flmtRCreTime;
	}

	public void setFlmtRCreTime(String flmtRCreTime) {
		this.flmtRCreTime = flmtRCreTime;
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


	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
