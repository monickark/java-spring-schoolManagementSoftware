package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class FileNotSaveException extends Exception {

	String message = ErrorCodeConstant.FILE_NOT_SAVE;

	public String getMessage() {
		return message;
	}
	
	
}
