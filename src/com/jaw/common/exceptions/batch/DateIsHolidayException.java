package com.jaw.common.exceptions.batch;

import com.jaw.common.constants.ErrorCodeConstant;

public class DateIsHolidayException extends Exception {
	
	String message = ErrorCodeConstant.DATE_IS_HOLIDAY;

	public String getMessage() {
		return message;
	}

}
