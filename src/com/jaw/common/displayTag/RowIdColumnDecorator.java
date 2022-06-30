package com.jaw.common.displayTag;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class RowIdColumnDecorator implements DisplaytagColumnDecorator {

	@Override
	public String decorate(Object arg0, PageContext arg1, MediaTypeEnum arg2)
			throws DecoratorException {

		return arg0.toString() + "<input type='hidden' name='rowid' value='"
				+ arg0.toString() + "'/>";
	}

}
