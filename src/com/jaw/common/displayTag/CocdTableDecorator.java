package com.jaw.common.displayTag;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.TableModel;

import com.jaw.prodAdm.controller.CommonCodeListVO;

/**
 * A table decorator which adds checkboxes for selectable rows.
 * 
 * @author Fabrizio Giustina
 * @version $Id: CheckboxTableDecorator.java 1134 2008-12-27 10:16:33Z fgiust $
 */
public class CocdTableDecorator extends TableDecorator {

	String key = null;

	@Override
	public void init(PageContext pageContext, Object decorated,
			TableModel tableModel) {
		super.init(pageContext, decorated, tableModel);
		key = pageContext.getRequest().getParameter("rowId");

	}

	public Integer getSerialno() {

		return getListIndex() + 1;
	}

	public String getEditBox() {

		CommonCodeListVO cocd = (CommonCodeListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if (key != null) {
			if (Integer.parseInt(key) == cocd.getRowId()) {
				buffer.append("<input type='text' name='textbox' value='");
				buffer.append(cocd.getCodeDescription());
				buffer.append("'");
				buffer.append("/>");
				buffer.append("<input type='hidden' id='rowid' name='rowid' value='");
				buffer.append(key);
				buffer.append("'");
				buffer.append("/>");

			} else {
				buffer.append(cocd.getCodeDescription());
			}
		} else {
			buffer.append(cocd.getCodeDescription());
		}

		return buffer.toString();
	}

}