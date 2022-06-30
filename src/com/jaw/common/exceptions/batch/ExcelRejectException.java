package com.jaw.common.exceptions.batch;

import java.util.ArrayList;
import java.util.List;

//User defined Exception class for Validation errors in excel upload
public class ExcelRejectException extends Exception {

	List<String> errorList = new ArrayList<String>();
	String errorMessage;

	public ExcelRejectException(List<String> errorList) {
		this.errorList = errorList;
	}

	public ExcelRejectException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

}
