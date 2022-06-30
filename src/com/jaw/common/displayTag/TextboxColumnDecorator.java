package com.jaw.common.displayTag;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class TextboxColumnDecorator implements DisplaytagColumnDecorator {

	@Override
	public Object decorate(Object arg0, PageContext arg1, MediaTypeEnum arg2)
			throws DecoratorException {

		StringBuffer buffer = new StringBuffer();
		buffer.append("<input type=\"text\" name=\"textboxview\" value=\"");
		buffer.append(arg0.toString());
		buffer.append("\"");
		buffer.append("/>");

		return buffer.toString();
	}

}
