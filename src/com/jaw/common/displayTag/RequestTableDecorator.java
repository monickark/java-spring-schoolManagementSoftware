package com.jaw.common.displayTag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.ObjectUtils;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.TableModel;

import com.jaw.admin.controller.RequestListVo;

/**
 * A table decorator which adds checkboxes for selectable rows.
 * 
 * @author Fabrizio Giustina
 * @version $Id: CheckboxTableDecorator.java 1134 2008-12-27 10:16:33Z fgiust $
 */
public class RequestTableDecorator extends TableDecorator {
	private String id = "id";
	private String fieldName = "_chk";
	String key = null;
	private List checkedIds;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void init(PageContext pageContext, Object decorated,
			TableModel tableModel) {
		super.init(pageContext, decorated, tableModel);
		key = pageContext.getRequest().getParameter("rowId");
		String[] params = pageContext.getRequest()
				.getParameterValues(fieldName);
		checkedIds = params != null ? new ArrayList(Arrays.asList(params))
				: new ArrayList(0);

	}

	@Override
	public void finish() {

		if (!checkedIds.isEmpty()) {
			JspWriter writer = getPageContext().getOut();
			for (Iterator it = checkedIds.iterator(); it.hasNext();) {

				String name = (String) it.next();
				StringBuffer buffer = new StringBuffer();
				buffer.append("<input type=\"hidden\" name=\"");
				buffer.append(fieldName);
				buffer.append("\" value=\"");
				buffer.append(name);
				buffer.append("\">");
				try {
					writer.write(buffer.toString());
				} catch (IOException e) {
					// should never happen
				}
			}
		}

		super.finish();

	}

	public Integer getSerialno() {

		return getListIndex() + 1;
	}

	public String getCheckbox() {

		String evaluatedId = ObjectUtils.toString(evaluate(id));
		RequestListVo requestVo = (RequestListVo) getCurrentRowObject();
		boolean checked = checkedIds.contains(evaluatedId);

		StringBuffer buffer = new StringBuffer();

		buffer.append("<input type=\"checkbox\" name=\"_chk\" class=\"selectableCheckbox\" value=\"");
		buffer.append(evaluatedId);
		buffer.append("\"");
		if (checked) {
			checkedIds.remove(evaluatedId);
			buffer.append(" checked=\"checked\"");
		}
		buffer.append("/>");
		buffer.append("<input type='hidden' name='rowid' value='"
				+ requestVo.getRowid() + "'/>");
		return buffer.toString();
	}

}