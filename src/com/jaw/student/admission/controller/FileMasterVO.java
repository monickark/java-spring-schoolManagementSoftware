package com.jaw.student.admission.controller;

import java.io.InputStream;
import java.io.Serializable;
public class FileMasterVO implements Serializable{
	private Integer dbTs;
	private String fileRefno = "";
	private InputStream inputStreams ;
	private String contentType = "";

	public Integer getDbTs() {
		return dbTs;
	}

	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileRefno() {
		return fileRefno;
	}

	public void setFileRefno(String fileRefno) {
		this.fileRefno = fileRefno;
	}

	public InputStream getInputStreams() {
		return inputStreams;
	}

	public void setInputStreams(InputStream inputStreams) {
		this.inputStreams = inputStreams;
	}

}
