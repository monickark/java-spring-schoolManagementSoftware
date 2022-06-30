package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class FeeMasterNotFoundForIntegratedCourseException extends Exception {
	String message = ErrorCodeConstant.FEE_MASTER_NOT_FOUND_FOR_INTEGRATED_COURSE;

	public String getMessage() {
		return message;
	}

	


}