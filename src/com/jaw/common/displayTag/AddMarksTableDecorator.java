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

import com.jaw.attendance.dao.StudentAttendanceList;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mark.controller.MarkSubjectLinkListForAddMarksVO;
import com.jaw.mark.controller.StudentListForAddMarksVO;
import com.jaw.mark.dao.StudentListForAddMarks;

public class AddMarksTableDecorator extends TableDecorator {
	private String id = "id";
	private String fieldName = "_chk";
	String key = null;
	String editKey = null;
	String addKey = null;
	String selectAction = null;
	String markOrGrade = null;
	CommonCodeUtil commonCodeUtil = new CommonCodeUtil();
	ApplicationCache applicationCache;
	SessionCache sessionCache;
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
		editKey = pageContext.getRequest().getParameter("edit");
		addKey = pageContext.getRequest().getParameter("mark");
		selectAction = (String) pageContext.getSession().getAttribute(
				"seletedAction");
		/*markOrGrade = (String) pageContext.getSession().getAttribute(
				"markOrGrade");*/
		MarkSubjectLinkListForAddMarksVO ns = (MarkSubjectLinkListForAddMarksVO) pageContext.getSession().getAttribute(
				"markSubLinkVO");
		if(ns!=null){
		markOrGrade=ns.getMarkGrade();
		}
		
		
		System.out.println("key :" + key + " editKey :" + editKey + " addKey :"
				+ addKey);
		System.out.println("session valueeeeeeeeeee" + selectAction);
		String[] params = pageContext.getRequest()
				.getParameterValues(fieldName);
		checkedIds = params != null ? new ArrayList(Arrays.asList(params))
				: new ArrayList(0);
		applicationCache = (ApplicationCache) pageContext
				.findAttribute(ApplicationConstant.APPLICATION_CACHE);
		sessionCache = (SessionCache) pageContext
				.findAttribute(ApplicationConstant.SESSION_CACHE_KEY);
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
	
	public String getMarkInput() {

		StudentListForAddMarksVO stuListFormarks = (StudentListForAddMarksVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if(selectAction.equals("ADD")||addKey != null||selectAction=="ADD") {		
			if ((markOrGrade != null) && (markOrGrade.equals(ApplicationConstant.MARK))) {
				buffer.append("<input type=\"text\" name=\"marksText\" class=\"marksTextC\" id=\"marktextID\" value=\"");
				buffer.append(stuListFormarks.getMarksObtd());
				buffer.append("\"");
				buffer.append("/>");
				buffer.append("<span class='help-inline markClassErr'></span>");
			}else {
				List<CommonCode> gradeTypes = null;
				try {
					gradeTypes = getcommonCodeList(CommonCodeConstant.GRADE_MARKS);
				} catch (CommonCodeNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				buffer.append("<select class='span12 gradesC' name='grades'>");
				buffer.append("<option  value=''>--Select--</option>");
				for (CommonCode commonCode : gradeTypes) {
					buffer.append("<option  value='"
							+ commonCode.getCommonCode() + "'");
					if (commonCode.getCommonCode().equals(
							stuListFormarks.getGradeObtd().trim())) {
						buffer.append(" selected='selected'");
					}
					buffer.append(">").append(commonCode.getCodeDescription())
							.append("</option>");
				}
				buffer.append("</select>");
			}
			buffer.append("<input type=\"hidden\" name=\"rowids\" class=\"portionListc\" value=\"");
			buffer.append(stuListFormarks.getRowId());
			buffer.append("\"");

			buffer.append("/>");
		} else if (((key != null) && (editKey != null))) {
			if (stuListFormarks.getRowId() == Integer.parseInt(key)) {
				if ((markOrGrade != null) && (markOrGrade.equals(ApplicationConstant.MARK))) {
					buffer.append("<input type=\"text\" name=\"marksText\" class=\"marksTextC\" id=\"marktextID\" value=\"");
					buffer.append(stuListFormarks.getMarksObtd());
					buffer.append("\"");
					buffer.append("/>");
					buffer.append("<span class='help-inline markClassErr'></span>");
				} else {

					List<CommonCode> gradeTypes = null;
					try {
						gradeTypes = getcommonCodeList(CommonCodeConstant.GRADE_MARKS);
					} catch (CommonCodeNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					buffer.append("<select class='span12 gradesC'>");
					buffer.append("<option  value=''>--Select--</option>");
					for (CommonCode commonCode : gradeTypes) {
						buffer.append("<option  value=").append(
								commonCode.getCommonCode());
						if (commonCode.getCommonCode().equals(
								stuListFormarks.getGradeObtd())) {
							buffer.append(" selected='selected'");
						}
						buffer.append(">")
								.append(commonCode.getCodeDescription())
								.append(" </option>");
					}
					buffer.append("</select>");
				}
				buffer.append("<input type=\"hidden\" name=\"rowids\" class=\"portionListc\" value=\"");
				buffer.append(stuListFormarks.getRowId());
				buffer.append("\"");

				buffer.append("/>");
			} else {
				if ((markOrGrade != null) && (markOrGrade.equals(ApplicationConstant.MARK))) {
					buffer.append(stuListFormarks.getMarksObtd());
				} else {
					buffer.append(stuListFormarks.getGradeObtd());
				}
			}

		} else {
			if (markOrGrade != null && markOrGrade.equals(ApplicationConstant.MARK)) {
				buffer.append(stuListFormarks.getMarksObtd());
			} else {
				buffer.append(stuListFormarks.getGradeObtd());
			}

		}
		return buffer.toString();
	}

	public String getSubRemarks() {

		StudentListForAddMarksVO stuListFormarks = (StudentListForAddMarksVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		if ((addKey != null) || (selectAction.equals("ADD"))) {
			buffer.append("<input type=\"text\" name=\"subRemarks\" class=\"subremarksc\" value=\"");
			buffer.append(stuListFormarks.getSubRemarks());
			buffer.append("\"");
			buffer.append("/>");
			buffer.append("<span class='help-inline remarkErr'></span>");
			
		} else if (((key != null) && (editKey != null))) {
			if (stuListFormarks.getRowId() == Integer.parseInt(key)) {
				buffer.append("<input type=\"text\" name=\"subRemarks\" class=\"subremarksc\" value=\"");
				buffer.append(stuListFormarks.getSubRemarks());
				buffer.append("\"");
				buffer.append("/>");
				buffer.append("<span class='help-inline remarkErr'></span>");
				
				buffer.append("<i class='icon-pencil note' ></i>");
			} else {
				buffer.append(stuListFormarks.getSubRemarks());
			}
		} else {

			buffer.append(stuListFormarks.getSubRemarks());
		}
		//Update link
		if ((editKey != null) || (selectAction.equals("EDIT"))) {
			if ((editKey != null)) {
				// String url = "markSubjectLink.htm?actionUpdate=update&data";
				if ((key != null)
						&& (stuListFormarks.getRowId() == Integer.parseInt(key))) {
					buffer.append("<a href=");
					buffer.append("#");

					buffer.append(" id=\"editMark\" class=\"icon-signin quick-info updateMark\"	title=\"Update\" style=\"float: right;\"></a>");
				} else {

					String url = "addMarks.htm?data&edit=edit&rowId="
							+ stuListFormarks.getRowId();
					buffer.append("<a href=");
					buffer.append(url);
					buffer.append(" id=\"edit\" class=\"icon-edit edit-info\"	title=\"Edit\" style=\"float: right;\"></a>");
				}
			}

			if ((editKey == null)) {
				String url = "addMarks.htm?data&edit=edit&rowId="
						+ stuListFormarks.getRowId();
				buffer.append("<a href=");
				buffer.append(url);
				buffer.append(" id=\"edit\" class=\"icon-edit edit-info\"	title=\"Edit\" style=\"float: right;\"></a>");

			}
		}
		return buffer.toString();
	}

	public String getCheckBox() {

		StudentListForAddMarksVO stuListFormarks = (StudentListForAddMarksVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if ((addKey != null) || (selectAction.equals("ADD"))) {
			buffer.append("<input type=\"checkbox\" name=\"attendance\" class=\"absentList\" value=\"");
			buffer.append(stuListFormarks.getRowId());
			buffer.append("\"");
			if ((stuListFormarks.getAttendance() != null)
					&& (stuListFormarks.getAttendance().equals(ApplicationConstant.ATT_ABSENT))) {
				buffer.append(" checked=\"checked\"");
			}
			buffer.append("/>");
		} else if (((key != null) && (editKey != null))) {
			if (stuListFormarks.getRowId() == Integer.parseInt(key)) {
				buffer.append("<input type=\"checkbox\" name=\"attendance\" class=\"absentList\" value=\"");
				buffer.append(stuListFormarks.getRowId());
				buffer.append("\"");
				if ((stuListFormarks.getAttendance() != null)
						&& (stuListFormarks.getAttendance().equals(ApplicationConstant.ATT_ABSENT))) {
					buffer.append(" checked=\"checked\"");
				}
				buffer.append("/>");
			} else {
				
				if ((stuListFormarks.getAttendance().equals(ApplicationConstant.ATT_ABSENT))) {
						buffer.append("<span class='label label-important '>Absent</span>");
				}
				else {
					buffer.append("<span class='label label-success '>Present</span>");
				}
				
			}
		} else {
			if ((stuListFormarks.getAttendance().equals(ApplicationConstant.ATT_ABSENT))) {
					buffer.append("<span class='label label-important '>Absent</span>");
			}
			else {
				buffer.append("<span class='label label-success '>Present</span>");
			}
			
		}
		
		return buffer.toString();
	}



	
	private List<CommonCode> getcommonCodeList(String key)
			throws CommonCodeNotFoundException {
		return commonCodeUtil.getCommonCodeListByType(applicationCache, key,
				sessionCache.getUserSessionDetails().getInstId(), sessionCache
						.getUserSessionDetails().getBranchId());
	}

	private String getDescriptionByTypeAndCode(String type, String code)
			throws CommonCodeNotFoundException {
		return commonCodeUtil.getDescriptionByTypeAndCode(applicationCache,
				type, code, sessionCache.getUserSessionDetails().getInstId(),
				sessionCache.getUserSessionDetails().getBranchId());
	}

}