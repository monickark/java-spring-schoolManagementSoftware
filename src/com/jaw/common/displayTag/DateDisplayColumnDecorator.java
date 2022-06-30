package com.jaw.common.displayTag;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class DateDisplayColumnDecorator implements
		DisplaytagColumnDecorator {

	@Override
	public Object decorate(Object arg0, PageContext arg1, MediaTypeEnum arg2)
			throws DecoratorException {		
		
		String Timein12hourFormat = "";
		if (arg0 != null) {
			Timein12hourFormat = arg0.toString().substring(0, 10);
			return Timein12hourFormat;
		}
		return Timein12hourFormat;
	}

}
