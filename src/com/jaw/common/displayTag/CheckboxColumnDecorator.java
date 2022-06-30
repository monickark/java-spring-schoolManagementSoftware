package com.jaw.common.displayTag;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class CheckboxColumnDecorator implements DisplaytagColumnDecorator {

	@Override
	public Object decorate(Object arg0, PageContext arg1, MediaTypeEnum arg2)
			throws DecoratorException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<input type='checkbox' name='checkboxvalue' value='"
				+ arg0.toString() + "' />");
		return buffer;
	}

}
