package com.jaw.prodAdm.controller;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class CommonCodeListVO implements Serializable {
	private String codeType;
	private String commonCode;
	private String codeDescription;
	private String newCodeType;
	private String newCommonCode;
	private String newCodeDescription;
	private String searchCodeType;
	private int rowId;

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int i) {
		rowId = i;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCommonCode() {
		return commonCode;
	}

	public void setCommonCode(String commonCode) {
		this.commonCode = commonCode;
	}

	public String getCodeDescription() {
		return codeDescription;
	}

	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}

	public String getNewCodeType() {
		return newCodeType;
	}

	public void setNewCodeType(String newCodeType) {
		this.newCodeType = newCodeType;
	}

	public String getNewCommonCode() {
		return newCommonCode;
	}

	public void setNewCommonCode(String newCommonCode) {
		this.newCommonCode = newCommonCode;
	}

	public String getNewCodeDescription() {
		return newCodeDescription;
	}

	public void setNewCodeDescription(String newCodeDescription) {
		this.newCodeDescription = newCodeDescription;
	}

	public String getSearchCodeType() {
		return searchCodeType;
	}

	public void setSearchCodeType(String searchCodeType) {
		this.searchCodeType = searchCodeType;
	}

}
