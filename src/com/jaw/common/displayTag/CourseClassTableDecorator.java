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

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.core.controller.CourseClassesVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.sessCache.SessionCache;

public class CourseClassTableDecorator extends TableDecorator {
	private String id = "id";
	private String fieldName = "_chk";
	String action = null;
	String addKey = null;
	String key = null;
	CommonCodeUtil commonCodeUtil = new CommonCodeUtil();
	ApplicationCache applicationCache;
	SessionCache sessionCache;
	private List checkedIds;
	private String type = "";

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
		addKey = pageContext.getRequest().getParameter("add");
		System.out.println("Add key :" + addKey);
		if ((addKey != null) && (addKey.equals("sa"))) {
			System.out.println("Inside add with error");
			key = String.valueOf(Integer.parseInt(key) + 1);
			System.out.println("Key :" + key);
		}
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

	@Override
	public String finishRow() {
		StringBuffer returnString = new StringBuffer();
		CourseClassesVO courseClassesVO = (CourseClassesVO) getCurrentRowObject();

		if ((key != null) && (!key.equals("undefined")) && (addKey != null)
				&& (addKey.equals("add"))
				&& (Integer.parseInt(key) == courseClassesVO.getRowid())) {
			{
				try {
					action = "edit";
					type = "add";
					returnString
							.append("<tr>")
							.append("<td>")
							.append("</td>")
							.append("<td>")
							.append("</td>")
							.append("<td>")
							.append("<input type='hidden' class='subId' value=\"")
							.append(courseClassesVO.getSubId()).append(" \"")
							.append(" />");

					returnString.append(courseClassesVO.getSubName())
							.append("</td>").append("<td>")
							.append(getClassTypeCombo());
					returnString
							.append("</td>")
							.append("<td>")
							.append("<input class='span12 m-wrap classNo' type='text'  name='noOfClasses' value='0'/>")
							.append("<span class='help-inline theoryClassError'></span>")
							.append("</td>")
							.append("<td>")
							.append("<input class='span12 m-wrap labSessNo' type='text'  name='noOfLabClasses' value='0'/>")
							.append("<span class='help-inline labClassError'></span>")
							.append("</td>")
							.append("<td>")
							.append("<input class='span12 m-wrap duration' type='text'  name='duration' value='0'/>")
							.append("<span class='help-inline labClassError'></span>")
							.append("</td>")
							
							.append("<td>")
							.append(getLabBatchCombo());

					returnString
							.append("</td>")
							.append("<td>")
							.append("<input type=' text' name='textbox' readonly='true' class='span12 m-wrap staffVal' value='")
							// .append(courseClassesVO.getStaffName())
							.append("'")
							.append("/>")
							.append("<input type='hidden' name='sstaff' class='staffId' id='rowid'  value='")
							// .append(courseClassesVO.getStaffId())
							.append("'")
							.append("/>")
							.append("<i align='right' class='icon-table select' title='Select'></i>")
							.append("<span class='help-inline staffError'></span>")
							.append("</td>");
					String url = "courseClasses.htm?save&add=sa&rowId="
							+ courseClassesVO.getRowid();

					returnString
							.append("<td>")

							.append("<input type=\"hidden\" class=\"rowId\" value='")
							.append(courseClassesVO.getRowid())
							.append("'/>")
							.append("<a href=")
							.append("#")

							.append(" id=\"edit\" class=\"save icon-save quick-info\"	title=\"Save\" style=\"float: right;\"></a>")
							.append("</td>").append("</tr>");
				} catch (CommonCodeNotFoundException e) {

				}
			}

			return returnString.toString();
		}
		return null;
	}

	public String getCheckBox() {

		String evaluatedId = ObjectUtils.toString(evaluate(id));
		CourseClassesVO courseClassesVO = (CourseClassesVO) getCurrentRowObject();
		boolean checked = checkedIds.contains(evaluatedId);

		StringBuffer buffer = new StringBuffer();
		if ((courseClassesVO.getStaffId() == null)
				|| (courseClassesVO.getStaffId() == "")) {
			buffer.append("<input type=\"checkbox\" name=\"_chk\" class=\"selectableCheckbox\" value=\"");
			buffer.append(evaluatedId);
			buffer.append("\"");
			if (checked) {
				checkedIds.remove(evaluatedId);
				buffer.append(" checked=\"checked\"");
			}
			buffer.append("/>");
			buffer.append("<input type='hidden' name='rowid' value='"
					+ courseClassesVO.getRowid() + "'/>");
		}
		return buffer.toString();
	}

	public String getEditBox() {

		CourseClassesVO courseClassesVO = (CourseClassesVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if ((key != null)
				&& (action != null)
				&& (action.equals("edit"))
				&& (Integer.parseInt(key) == courseClassesVO.getRowid())
				|| ((courseClassesVO.getStaffId() == null) || (courseClassesVO
						.getStaffId() == ""))
				|| ((addKey != null) && (addKey.equals("sa")))) {
			buffer.append("<input type=\" text\" class='span12 m-wrap staffVal' readonly='true' value=\"");
			buffer.append(courseClassesVO.getStaffName());
			buffer.append("\"");
			buffer.append("/>");
			buffer.append("<input type='hidden' class='staffId' id='rowid' name='textboxview' value='");
			buffer.append(courseClassesVO.getStaffId());
			buffer.append("'");
			buffer.append("/>");
			buffer.append("<input type='hidden'  name='hiddenRowId' value='");
			buffer.append(courseClassesVO.getRowid());
			buffer.append("'");
			buffer.append("/>");
			buffer.append("<i align='right' class='icon-table select' title='Select'></i>");
		} else {

			buffer.append(courseClassesVO.getStaffName());
			/*buffer.append("<input type='hidden'  name='textboxview' value='");
			buffer.append(courseClassesVO.getStaffId());
			buffer.append("'");
			buffer.append("/>");*/
		}

		buffer.append("<br/>");
		buffer.append("<span class='help-inline staffError'></span>");
		return buffer.toString();
	}

	public String getSubject() throws CommonCodeNotFoundException {

		CourseClassesVO courseClassesVO = (CourseClassesVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		buffer.append(courseClassesVO.getSubName());
		buffer.append("<input type=\"hidden\" class=\"subId\" value='");
		buffer.append(courseClassesVO.getSubId());
		buffer.append("'/>");
		return buffer.toString();
	}

	public String getClassTypeCombo() throws CommonCodeNotFoundException {
		CourseClassesVO courseClassesVO = (CourseClassesVO) getCurrentRowObject();
		List<CommonCode> batchTypeCodes = getcommonCodeList(CommonCodeConstant.CLASS_TYPE);
		StringBuffer returnString = new StringBuffer();
		if ((key != null)
				&& (action != null)
				&& (action.equals("edit"))
				&& (Integer.parseInt(key) == courseClassesVO.getRowid())
				|| ((courseClassesVO.getStaffId() == null) || (courseClassesVO
						.getStaffId() == ""))
				|| ((addKey != null) && (addKey.equals("sa")))) {

			if ((courseClassesVO.getRequiresLab().equals("N"))
					|| ((courseClassesVO.getLabBatch() != null) && (courseClassesVO
							.getLabBatch().equals(ApplicationConstant.THEORY)))) {
				returnString
						.append("<select class='span12 wrap clsType' name='clsType'>");
				for (CommonCode commonCode : batchTypeCodes) {

					if (commonCode.getCommonCode().equals(
							ApplicationConstant.THEORY)) {
						returnString.append("<option  value='")
								.append(commonCode.getCommonCode())
								.append("'>")
								.append(commonCode.getCodeDescription())
								.append(" </option>");

					}
				}
			} else {

				returnString
						.append("<select name='clsType' class='span12 wrap clsType'>");
				returnString
						.append("<option  value=''>Select Any One</option>");
				for (CommonCode commonCode : batchTypeCodes) {

					if (type.equals("add")) {

						returnString.append("<option  value='")
								.append(commonCode.getCommonCode())
								.append("'>")
								.append(commonCode.getCodeDescription())
								.append(" </option>");
					} else {

						if (commonCode.getCommonCode().equals(
								courseClassesVO.getClsType())) {

							returnString
									.append("<option selected='selected' value='")
									.append(commonCode.getCommonCode())
									.append("'>")
									.append(commonCode.getCodeDescription())
									.append(" </option>");
						} else {
							returnString.append("<option  value='")
									.append(commonCode.getCommonCode())
									.append("'>")
									.append(commonCode.getCodeDescription())
									.append(" </option>");
						}

					}

				}
				returnString.append("</select>");
			}
		} else {
			returnString
					.append(getDescriptionByTypeAndCode(
							CommonCodeConstant.CLASS_TYPE,
							courseClassesVO.getClsType()));
		}
		returnString.append("<span class='help-inline classTypeError'></span>");
		return returnString.toString();
	}

	public String getLabBatchCombo() throws CommonCodeNotFoundException {
		CourseClassesVO courseClassesVO = (CourseClassesVO) getCurrentRowObject();
		List<CommonCode> batchCodes = getcommonCodeList(CommonCodeConstant.LAB_BATCH);
		StringBuffer returnString = new StringBuffer();

		if ((key != null)
				&& (action != null)
				&& (action.equals("edit"))
				&& (Integer.parseInt(key) == courseClassesVO.getRowid())
				|| ((courseClassesVO.getStaffId() == null) || (courseClassesVO
						.getStaffId() == ""))
				|| ((addKey != null) && (addKey.equals("sa")))) {
			returnString
					.append("<select  name='labBatch' class='span12 wrap labBatch'>");

			if ((courseClassesVO.getClsType() != null)
					&& (courseClassesVO.getClsType()
							.equals(ApplicationConstant.THEORY))) {
				returnString
						.append("<option value='NA'>Not Applicable</option>");
			}
			returnString.append("<option  value=''>Select Any One</option>");

			for (CommonCode commonCode : batchCodes) {
				if (type.equals("add")) {

					returnString.append("<option  value='")
							.append(commonCode.getCommonCode()).append("'>")
							.append(commonCode.getCodeDescription())
							.append(" </option>");
				} else {

					if (commonCode.getCommonCode().equals(
							courseClassesVO.getLabBatch())) {

						returnString
								.append("<option selected='selected' value='")
								.append(commonCode.getCommonCode())
								.append("'>")
								.append(commonCode.getCodeDescription())
								.append(" </option>");
					} else {
						returnString.append("<option  value='")
								.append(commonCode.getCommonCode())
								.append("'>")
								.append(commonCode.getCodeDescription())
								.append(" </option>");
					}

				}

			}
			returnString.append("</select>");
		} else {

			if ((courseClassesVO.getLabBatch().equals("NA"))
					|| ((courseClassesVO.getLabBatch().equals("")))) {
				returnString.append("Not Applicable");
			} else {

				returnString.append(getDescriptionByTypeAndCode(
						CommonCodeConstant.LAB_BATCH,
						courseClassesVO.getLabBatch()));
			}

		}
		returnString.append("<span class='help-inline labBatchError'></span>");
		return returnString.toString();
	}

	public String getNoOfSession() {
		CourseClassesVO courseClassesVO = (CourseClassesVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if ((key != null)
				&& (action != null)
				&& (action.equals("edit"))
				&& (Integer.parseInt(key) == courseClassesVO.getRowid())
				|| ((courseClassesVO.getStaffId() == null) || (courseClassesVO
						.getStaffId() == ""))
				|| ((addKey != null) && (addKey.equals("sa")))) {
			buffer.append("<input type='text' class='span12 m-wrap classNo' name='classNo'  value='");
			buffer.append(courseClassesVO.getNoOfClassesPerWeek());
			buffer.append("'");
			buffer.append("/>");

			buffer.append("<br/>");
			buffer.append("<span class='help-inline theoryClassError'></span>");

		} else {
			buffer.append(courseClassesVO.getNoOfClassesPerWeek());
		}

		return buffer.toString();
	}

	public String getDurationBox() {
		CourseClassesVO courseClassesVO = (CourseClassesVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if ((key != null)
				&& (action != null)
				&& (action.equals("edit"))
				&& (Integer.parseInt(key) == courseClassesVO.getRowid())
				|| ((courseClassesVO.getStaffId() == null) || (courseClassesVO
						.getStaffId() == ""))
				|| ((addKey != null) && (addKey.equals("sa")))) {
			System.out.println("Inside duration box if");
			buffer.append("<input type='text' class='span12 m-wrap duration' name='duration'  value='");
			if(courseClassesVO.getDuration()==null) {
				System.out.println("Inside duration box else if");
				buffer.append("0");
			} else {
			buffer.append(courseClassesVO.getDuration());
			}
			buffer.append("'");
			buffer.append("/>");

			buffer.append("<br/>");
			buffer.append("<span class='help-inline durationClassError'></span>");

		} else {
			if(courseClassesVO.getDuration()==null) {
				System.out.println("Inside duration box else if");
				buffer.append("0");
			} else {
				System.out.println("Inside duration box else else");
				buffer.append(courseClassesVO.getDuration());
			}
			
		}

		return buffer.toString();
	}

	public String getNoOfLabSession() {
		CourseClassesVO courseClassesVO = (CourseClassesVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if ((key != null)
				&& (action != null)
				&& (action.equals("edit"))
				&& (Integer.parseInt(key) == courseClassesVO.getRowid())
				|| ((courseClassesVO.getStaffId() == null) || (courseClassesVO
						.getStaffId() == ""))
				|| ((addKey != null) && (addKey.equals("sa")))) {
			buffer.append("<input type='text' class='span12 m-wrap labSessNo' name='labSessNo'  value='");
			buffer.append(courseClassesVO.getNoOfLabClassesPerWeek());
			buffer.append("'");
			buffer.append("/>");

			buffer.append("<br/>");
			buffer.append("<span class='help-inline labClassError'></span>");

		} else {
			buffer.append(courseClassesVO.getNoOfLabClassesPerWeek());
		}

		return buffer.toString();
	}

	public String getHrefIcon() {

		CourseClassesVO courseClassesVO = (CourseClassesVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		System.out.println("to strng :" + courseClassesVO.toString());

		if ((courseClassesVO.getStaffId() != null)
				&& (courseClassesVO.getStaffId() != "")) {
			System.out.println("Key :" + key + " row id:"
					+ courseClassesVO.getCcId());
			if ((addKey != null) && (addKey.equals("sa"))
					&& (courseClassesVO.getCcId() == null)) {
				buffer.append("<a href=")
						.append("#")

						.append(" id=\"edit\" class=\"save icon-save quick-info\"	title=\"Save\" style=\"float: right;\"></a>");
			} else {

				if (((action != null) && (action.equals("edit"))
						&& (key != null) && (Integer.parseInt(key) == courseClassesVO
						.getRowid()))) {

					String url = "courseClasses.htm?actionUpdate=update&data";
					buffer.append("<i ");
					buffer.append(" id=\"edit\" class=\"icon-signin update quick-info\"	title=\"Update\" style=\"float: right;\"></i>");

				} else {

					String url = "courseClasses.htm?data&edit=edit&rowId="
							+ courseClassesVO.getRowid();
					buffer.append("<a href=");
					buffer.append(url);
					buffer.append(" id=\"edit\" class=\"icon-edit edit-info\"	title=\"Edit\" style=\"float: right;\"></a>");

				}

				String url = "courseClasses.htm?data";

				buffer.append("<a href=");
				buffer.append("#");
				buffer.append(" id=\"edit\" class=\"icon-remove  delete delete-info\"	title=\"Delete\" style=\"float: right;\"></a>");

				String url1 = "courseClasses.htm?data&add=add&rowId="
						+ courseClassesVO.getRowid();

				buffer.append("<a href=");
				buffer.append(url1);

				buffer.append(" id=\"edit\" class=\"icon-plus details-info add\"	title=\"Add\" style=\"float: right;\"></a>");
				buffer.append("<input type=\"hidden\" class=\"seqId\" value='");
				buffer.append(courseClassesVO.getSaNo());
				buffer.append("'/>");
			}
			buffer.append("<input type=\"hidden\" class=\"rowId\" value='");
			buffer.append(courseClassesVO.getRowid());
			buffer.append("'/>");
		}

		return buffer.toString();
	}

}