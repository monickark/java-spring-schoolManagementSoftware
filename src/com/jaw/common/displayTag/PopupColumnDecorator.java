package com.jaw.common.displayTag;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class PopupColumnDecorator implements DisplaytagColumnDecorator {

	@Override
	public Object decorate(Object arg0, PageContext arg1, MediaTypeEnum arg2)
			throws DecoratorException {
		StringBuffer buffer = new StringBuffer();
		/*
		 * buffer.append("<div class='control-group'>");
		 * 
		 * buffer.append("<div class='controls'>");
		 * buffer.append("<input type='text' class='span10 m-wrap' />");
		 * buffer.append(" </div>"); buffer.append(
		 * "<input type='checkbox' value='' />Absent &nbsp; &nbsp; &nbsp; &nbsp;<i class='icon-pencil' id='note'> Note</i>"
		 * ); buffer.append("</div>");
		 */
		return buffer;
	}
}
