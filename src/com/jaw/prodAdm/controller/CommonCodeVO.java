package com.jaw.prodAdm.controller;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class CommonCodeVO implements Serializable {
	private String codeType;
	private String commonCode;
	private String codeDescription;
	private String newCodeType;
	private String newCommonCode;
	private String newCodeDescription;
	private String searchCodeType;
	private String pageNo;
	private int rowId;
	private ArrayList<CommonCodeListVO> commonCodeList;

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
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

	public ArrayList<CommonCodeListVO> getCommonCodeList() {
		return commonCodeList;
	}

	public void setCommonCodeList(ArrayList<CommonCodeListVO> commonCodeList) {
		this.commonCodeList = commonCodeList;
	}

}
