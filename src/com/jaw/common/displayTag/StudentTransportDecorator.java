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

import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.controller.StudentTransportListVO;
import com.jaw.student.controller.StudentUpdateListVO;

public class StudentTransportDecorator extends TableDecorator {

	private String id = "id";

	private List checkedIds;

	private String fieldName = "_chk";

	/**
	 * Setter for <code>id</code>.
	 * 
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Setter for <code>fieldName</code>.
	 * 
	 * @param fieldName
	 *            The fieldName to set.
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @see org.displaytag.decorator.Decorator#init(javax.servlet.jsp.PageContext,
	 *      java.lang.Object, org.displaytag.model.TableModel)
	 */
	String key = null;
	String selectall = null;

	@Override
	public void init(PageContext pageContext, Object decorated,
			TableModel tableModel) {
		super.init(pageContext, decorated, tableModel);
		String[] params = pageContext.getRequest()
				.getParameterValues(fieldName);
		selectall = pageContext.getRequest().getParameter("selectall");
		key = pageContext.getRequest().getParameter("rollno");
		checkedIds = params != null ? new ArrayList(Arrays.asList(params))
				: new ArrayList(0);
	}

	/**
	 * @see org.displaytag.decorator.TableDecorator#finish()
	 */
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

	public String getPopup() {
		return "";
	}

	public String getEditbox() {

		StudentMaster studentMaster = (StudentMaster) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if (key != null) {
			if (Integer.parseInt(key) == studentMaster.getRowid()) {
				buffer.append("<input type=\"text\" name=\"textbox\" value=\"");
				buffer.append(studentMaster.getStudentName());
				buffer.append("\"");
				buffer.append("/>");
			} else {
				buffer.append(studentMaster.getStudentName());
			}
		} else {
			buffer.append(studentMaster.getStudentName());
		}

		return buffer.toString();
	}

	public String getCheckbox() {

		String evaluatedId = ObjectUtils.toString(evaluate(id));
		StudentTransportListVO studentMaster = (StudentTransportListVO) getCurrentRowObject();
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
				+ studentMaster.getRowid() + "'/>");
		return buffer.toString();
	}

}