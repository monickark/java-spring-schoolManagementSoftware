package com.jaw.common.exceptions.batch;

import com.jaw.common.constants.ErrorCodeConstant;

public class ExtensionNotMatchingException extends Exception {
	private String message = ErrorCodeConstant.EXCEL_EXTN_WRONG ;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	

}
