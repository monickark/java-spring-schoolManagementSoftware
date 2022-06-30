package com.jaw.common.displayTag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.TableModel;

import com.jaw.attendance.controller.AttendanceListVO;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

public class AttendanceTableDecorator extends TableDecorator {
	private String id = "id";
	private String fieldName = "_chk";
	String key = null;
	String action = null;
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
		action = pageContext.getRequest().getParameter("edit");
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
	
	public String getCheckBox() {

		AttendanceListVO attendanceListVO = (AttendanceListVO) getCurrentRowObject();
		System.out.println("Absent or not :" + attendanceListVO.getIsAbsent());
		StringBuffer buffer = new StringBuffer();
		if ((action != null) && (action.equals("edit")) && (key != null)
				&& (Integer.parseInt(key) == attendanceListVO.getRowid())) {
			buffer.append("<input type=\"checkbox\" name=\"absentList\" class=\"absentList\" value=\"");
			buffer.append(attendanceListVO.getIsAbsent());
			buffer.append("\"");
			if ((attendanceListVO.getIsAbsent() != null)
					&& (attendanceListVO.getIsAbsent().equals("A"))) {
				buffer.append(" checked=\"checked\"");
			}

			buffer.append("/>");
		} else {
			if ((attendanceListVO.getIsAbsent() != null)) {
				
				buffer.append("<span class='label label-important '>Absent</span>");
			} else {
				buffer.append("<span class='label label-success '>Present</span>");
			}

		}
		return buffer.toString();
	}

	public String getRemarkTextBox() {

		AttendanceListVO attendanceListVO = (AttendanceListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if ((action != null) && (action.equals("edit")) && (key != null)
				&& (Integer.parseInt(key) == attendanceListVO.getRowid())) {
			buffer.append("<input type=\"text\" name=\"remarksList\"  class=\"remarksList\" value=\"");
			if (attendanceListVO.getRemark() == null) {
				System.out.println("Inside null");
				buffer.append("");
			} else {
				buffer.append(attendanceListVO.getRemark());
			}
			buffer.append("\"");

			buffer.append("/>");
			buffer.append("<i class='icon-pencil note' style='float: right;'></i>");
		} else {
			System.out.println("remark :"+attendanceListVO.getRemark());
			if (attendanceListVO.getRemark() == null) {
				System.out.println("Inside null");
				buffer.append("");
			} else {
				buffer.append(attendanceListVO.getRemark());
			}
			
		}
		
		return buffer.toString();
	}

	public String getHrefIcon() {

		AttendanceListVO attendanceListVO = (AttendanceListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		if ((action != null) && (action.equals("edit")) && (key != null)
				&& (Integer.parseInt(key) == attendanceListVO.getRowid())) {
			buffer.append("<input type=\"hidden\" class=\"rowId\" value='");
			buffer.append(attendanceListVO.getRowid());
			buffer.append("'/>");
			buffer.append("<i ");
			buffer.append(" id=\"edit\" class=\"icon-signin edLink quick-info\"	title=\"Update\" style=\"float: right;\"></i>");

		} else {

			String url = "attendanceView.htm?data&edit=edit&type=edit1&rowId="
					+ attendanceListVO.getRowid();

			buffer.append("<a href=");
			buffer.append(url);
			buffer.append(" id=\"edit\" class=\"icon-edit  edit-info\"	title=\"Edit\" style=\"float: right;\"></a>");

		}
		return buffer.toString();
	}

}