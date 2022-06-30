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

import com.jaw.admin.controller.SMSConfigurationVO;
import com.jaw.attendance.dao.StudentAttendanceList;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.core.controller.CourseClassesVO;
import com.jaw.framework.appCache.dao.CommonCode;

public class SMSConfigurationTableDecorator extends TableDecorator {
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
				}
				catch (IOException e) {
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
		String evaluatedId = ObjectUtils.toString(evaluate(id));
		SMSConfigurationVO smsConfigurationVO = (SMSConfigurationVO) getCurrentRowObject();
		boolean checked = checkedIds.contains(evaluatedId);

		StringBuffer buffer = new StringBuffer();
		if ((smsConfigurationVO.getPropertyValue() == null)
				|| (smsConfigurationVO.getPropertyValue().equals(""))) {
			/*buffer.append("<input type=\"checkbox\" name=\"_chk\" class=\"selectableCheckbox\" value=\"");
			buffer.append(evaluatedId);
			buffer.append("\"");
			if (checked) {
				checkedIds.remove(evaluatedId);
				buffer.append(" checked=\"checked\"");
			}
			buffer.append("/>");*/
			buffer.append("<input type=\"checkbox\" name=\"_chk\" class=\"selectableCheckbox\" value=\"");
			buffer.append(smsConfigurationVO.getPropertyName());
			buffer.append("\"");
			if (checked) {
				checkedIds.remove(evaluatedId);
				buffer.append(" checked=\"checked\"");
			}
			buffer.append("/>");
			
			//buffer.append("<input type='hidden' name='rowid' value='"
			//		+ smsConfigurationVO.getRowId() + "'/>");
		}
		return buffer.toString();
	}
	
	public String getPropertyValueTable() {
		SMSConfigurationVO smsConfigurationVO = (SMSConfigurationVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if ((key != null)
				&& (action != null)
				&& (action.equals("edit"))
				&& (Integer.parseInt(key) == smsConfigurationVO.getRowId())
				|| ((smsConfigurationVO.getPropertyValue() == null) || (smsConfigurationVO
						.getPropertyValue().equals("")))){
			buffer.append("<input type='text' class='span12 m-wrap propertyValueclass' name='propertyValuetext'  value='");
			buffer.append(smsConfigurationVO.getPropertyValue());
			buffer.append("'");
			/*if((action== null)||(Integer.parseInt(key) != smsConfigurationVO.getRowId())){
				buffer.append("readonly='true'");
			}*/
			buffer.append("/>");
			buffer.append("<span class='help-inline propertyValErrorClass' id='propertyValError'></span>");
		/*	if( (action == null)
					&&(action!="edit")){*/

				buffer.append("<input type='hidden' class='span12 m-wrap propertyNameclass1' name='propertyNametext'  value='");
				buffer.append(smsConfigurationVO.getPropertyName());
				buffer.append("'");
				buffer.append("/>");
			//}
				buffer.append("<input type=\"hidden\" name=\"propertyValueRows\" class=\"selectableRow\" value=\"");
				buffer.append(smsConfigurationVO.getRowId());
				buffer.append("\"");				
				buffer.append("/>");

		} else {
			buffer.append(smsConfigurationVO.getPropertyValue());
		}

		return buffer.toString();
	}

	public String getHrefIcon() {

		SMSConfigurationVO smsConfigurationVO = (SMSConfigurationVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
System.out.println("to strng :"+smsConfigurationVO.toString());
		
		
			if((smsConfigurationVO.getPropertyValue()!=null)&&(!smsConfigurationVO.getPropertyValue().equals(""))){
			 if (((action != null) && (action.equals("edit")) && (key != null)
					&& (Integer.parseInt(key) == smsConfigurationVO.getRowId()))) {

				String url = "smsConfiguration.htm?actionUpdate=update&data";
				buffer.append("<i ");
				buffer.append(" id=\"edit\" class=\"icon-signin update quick-info editurl\"	title=\"Update\" style=\"float: right;\"></i>");

			} else {

				String url = "smsConfiguration.htm?data&edit=edit&rowId="
						+ smsConfigurationVO.getRowId();
				buffer.append("<a href=");
				buffer.append(url);
				buffer.append(" id=\"edit\" class=\"icon-edit edit-info editurl\"	title=\"Edit\" style=\"float: right;\"></a>");

			}
			
			}
		return buffer.toString();
	}

	
}