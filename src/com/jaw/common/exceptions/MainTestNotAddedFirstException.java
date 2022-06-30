package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;



public class MainTestNotAddedFirstException extends Exception {

	String message = ErrorCodeConstant.MAIN_TEST_EXAM_NOT_ADDED;

	public String getMessage() {
		return message;
	}

}
