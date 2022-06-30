package com.jaw.batch.dao;

import java.io.InputStream;

public class MulitpartFileObject {
	
	private InputStream inputStream;
	private String originalFilename;
	private Integer size;
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}	
	public String getOriginalFilename() {
		return originalFilename;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}	
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	private String contentType;	
	

}
