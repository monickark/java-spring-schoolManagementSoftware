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

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.fee.controller.FeeMasterVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.sessCache.SessionCache;

public class FeeMasterDecorator extends TableDecorator {
	private String id = "id";
	private String fieldName = "_chk";
	String key = null;
	String action = null;
	String addKey;
	private List checkedIds;
	CommonCodeUtil commonCodeUtil = new CommonCodeUtil();
	ApplicationCache applicationCache;
	SessionCache sessionCache;

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
		FeeMasterVO feeMasterVO = (FeeMasterVO) getCurrentRowObject();

		if ((key != null) && (!key.equals("undefined")) && (addKey != null)
				&& (addKey.equals("add"))
				&& (Integer.parseInt(key) == feeMasterVO.getRowid())) {
			{
				try {
					List<CommonCode> courseVariants = getcommonCodeList(CommonCodeConstant.COURSE_VARIANT);

					returnString
							.append("<tr>")
							.append("<td>")

							.append(getDescriptionByTypeAndCode(
									CommonCodeConstant.FEE_TERM,
									feeMasterVO.getFeeTerm()))
							.append("</td>")
							.append("<td>")
							.append(getDescriptionByTypeAndCode(
									CommonCodeConstant.FEE_CATEGORY,
									feeMasterVO.getFeeCategory()))
							.append("</td>")

							.append("<td>")
							.append(getDescriptionByTypeAndCode(
									CommonCodeConstant.FEE_TYPE,
									feeMasterVO.getFeeType())).append("</td>")
							.append("<td>");
					returnString
							.append("<select class='span6 wrap courseVariantAdd' id='courseVariantAdd' name='courseVariantAdd'>");
					for (CommonCode commonCode : courseVariants) {

						returnString.append("<option  value='")
								.append(commonCode.getCommonCode())
								.append("'>")
								.append(commonCode.getCodeDescription())
								.append(" </option>");

					}
					returnString.append("</td>");
					returnString
							.append("<td>")
							.append("</select>")

							.append("<input type=\"text\" class=\"amount\" name=\"amount\"  class=\"amount\" value=\"")

							.append("\"")

							.append("/>")
							.append("</td>")
							.append("<td>")
							.append("<a href=")
							.append("#")

							.append(" id=\"edit\" class=\"save icon-save quick-info save\"	title=\"Save\" style=\"float: right;\"></a>")

							.append("<input type='hidden' class='hiddenRowId'  name='hiddenRowId' value='")
							.append(feeMasterVO.getRowid()).append("'")
							.append("/>").append("</td>").append("</tr>");
				} catch (CommonCodeNotFoundException e) {

				}
			}

			return returnString.toString();
		}
		return null;
	}

	public String getAmount() {

		FeeMasterVO FeeMasterVO = (FeeMasterVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if ((action != null) && (action.equals("edit")) && (key != null)
				&& (Integer.parseInt(key) == FeeMasterVO.getRowid())) {
			System.out.println("remark");
			buffer.append("<input type=\"text\" class=\"amount\" name=\"remarksList\"  class=\"remarksList\" value=\"");

			buffer.append(FeeMasterVO.getFeeAmt());

			buffer.append("\"");

			buffer.append("/>");

		} else {
			System.out.println("remark :" + FeeMasterVO.getFeeAmt());

			buffer.append(FeeMasterVO.getFeeAmt());

		}

		return buffer.toString();
	}

	public String getHrefIcon() {

		FeeMasterVO FeeMasterVO = (FeeMasterVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		if ((action != null) && (action.equals("edit")) && (key != null)
				&& (Integer.parseInt(key) == FeeMasterVO.getRowid())) {
			String url = "feeMaster.htm?data&update=update&rowId="
					+ FeeMasterVO.getRowid();
			buffer.append("<input type=\"hidden\" class=\"rowId\" value='");
			buffer.append(FeeMasterVO.getRowid());
			buffer.append("'/>");
			buffer.append("<i href= ");
			buffer.append("#");
			buffer.append(" id=\"edit\" class=\"icon-signin edLink quick-info\"	title=\"Update\" style=\"float: right;\"></i>");

		} else {
			String url = "feeMaster.htm?data&edit=edit&rowId="
					+ FeeMasterVO.getRowid();
			buffer.append("<a href=");
			buffer.append(url);
			buffer.append(" id=\"edit\" class=\"icon-edit  edit-info\"	title=\"Edit\" style=\"float: right;\"></a>");

			String url1 = "feeMaster.htm?data&delete=delete&rowId="
					+ FeeMasterVO.getRowid();
			buffer.append("<a href=");
			buffer.append(url1);
			buffer.append(" id=\"edit\" class=\"icon-remove  delete delete-info\"	title=\"Delete\" style=\"float: right;\"></a>");

		}
		/*if (!FeeMasterVO.getCourseVariant().equals("NA")) {
			String url1 = "feeMaster.htm?data&add=add&rowId="
					+ FeeMasterVO.getRowid();

			buffer.append("<a href=");
			buffer.append(url1);

			buffer.append(" id=\"edit\" class=\"icon-plus details-info add\"	title=\"Add\" style=\"float: right;\"></a>");
		}*/

		return buffer.toString();
	}
}