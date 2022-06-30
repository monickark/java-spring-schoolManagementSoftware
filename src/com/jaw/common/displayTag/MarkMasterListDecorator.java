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
import com.jaw.mark.controller.MarkMasterVO;
import com.jaw.mark.dao.MarkMaster;

public class MarkMasterListDecorator extends TableDecorator {

	private String id = "id";

	private List checkedIds;

	private String fieldName = "_chk";
	
	String action = null;

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
	CommonCodeUtil commonCodeUtil = new CommonCodeUtil();
	ApplicationCache applicationCache;
	SessionCache sessionCache;

	@Override
	public void init(PageContext pageContext, Object decorated,
			TableModel tableModel) {
		super.init(pageContext, decorated, tableModel);
		key = pageContext.getRequest().getParameter("rowId");
		action = pageContext.getRequest().getParameter("edit");
		String[] params = pageContext.getRequest()
				.getParameterValues(fieldName);
		selectall = pageContext.getRequest().getParameter("selectall");
		checkedIds = params != null ? new ArrayList(Arrays.asList(params))
				: new ArrayList(0);
		applicationCache = (ApplicationCache) pageContext
				.findAttribute(ApplicationConstant.APPLICATION_CACHE);
		sessionCache = (SessionCache) pageContext
				.findAttribute(ApplicationConstant.SESSION_CACHE_KEY);
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
	
	/*public String getStudentGrpName() {
		return null;
	}*/
	
	public String getExpRptDate() {
		
		MarkMasterVO markMastervo = (MarkMasterVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if (key != null) {
			if (Integer.parseInt(key) == markMastervo.getRowid()) {
				buffer.append(
						"<div class='input-append date date-picker span12' data-date='2012-02-12' data-date-format='yyyy-mm-dd' data-date-viewmode='years' >")
						.append("<input class='m-wrap m-ctrl-medium date-picker ' id='dateformat1' data-date-format='yyyy-mm-dd' size='12' type='text' ")
						.append("name='textbox' value=''");
				buffer.append(markMastervo.getExpRptDate());
				buffer.append("' readonly='true' ><span class='add-on'><i class='icon-calendar'></i></span></div>");
				/*buffer.append("<input type=\"text\" name=\"textbox\" value=\"");
				buffer.append(markMastervo.getExpRptDate());
				buffer.append("\" id=\"dateformat1\" data date-format=\"yyyy-mm-dd\"");
				buffer.append("data-date-viewmode=\"years\"  class=\"m-wrap m-ctrl-medium date-picker\"  size=\"12\" tabindex=\"1\"");
				buffer.append("/>");*/
			} else {
				buffer.append(markMastervo.getExpRptDate());
			}
		} else {
			buffer.append(markMastervo.getExpRptDate());
		}

		return buffer.toString();
		
	}
	/*<div class="input-append date date-picker"
			data-date="2012-02-12" data-date-format="yyyy-mm-dd"
			data-date-viewmode="years">
			<form:input path="studentInfoVO.admisDate" id="dateformat1"
				data-date-format="yyyy-mm-dd"
				class="m-wrap m-ctrl-medium date-picker" size="16" value=""
				readonly="true" tabindex="1"/>
			<span class="add-on"><i class="icon-calendar"></i></span> <span
				class="help-inline"></span>
		</div>*/
	public String getExamOrdrWiSG() {

		MarkMasterVO markMastervo = (MarkMasterVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if (key != null) {
			if (Integer.parseInt(key) == markMastervo.getRowid()) {
				buffer.append("<input type=\"text\" name=\"textbox\" value=\"");
				buffer.append(markMastervo.getExamOrdrWiSG());
				buffer.append("\" size=\"4\" ");
				buffer.append("/>");
			} else {
				buffer.append(markMastervo.getExamOrdrWiSG());
			}
		} else {
			buffer.append(markMastervo.getExamOrdrWiSG());
		}

		return buffer.toString();
	}
	
	public String getHrefIcon() {

		MarkMasterVO MarkMasterVO = (MarkMasterVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		if ((action != null) && (action.equals("edit")) && (key != null)
				&& (Integer.parseInt(key) == MarkMasterVO.getRowid())) {
			String url = "markMasterList.htm?data&update=update&rowId="
					+ MarkMasterVO.getRowid();
			buffer.append("<input type=\"hidden\" class=\"rowId\" value='");
			buffer.append(MarkMasterVO.getRowid());
			buffer.append("'/>");
			buffer.append("<a href= ");
			buffer.append("#");
			buffer.append(" id=\"edit\" class=\"icon-signin edLink quick-info\"	title=\"Update\" style=\"float: right;\"></a>");

		} else {
			String url = "markMasterList.htm?data&edit=edit&rowId="
					+ MarkMasterVO.getRowid();
			buffer.append("<a href=");
			buffer.append(url);
			buffer.append(" id=\"edit\" class=\"icon-edit  edit-info\"	title=\"Edit\" style=\"float: right;\"></a>");

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

	/*@Override
	public String finishRow() {
		StringBuffer returnString = new StringBuffer();
		MarkMasterVO markMasterVO = (MarkMasterVO) getCurrentRowObject();

		if ((key != null) && (!key.equals("undefined"))
				&& (Integer.parseInt(key) == markMasterVO.getRowid())) {
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
					.append("</tr>");
				} catch (CommonCodeNotFoundException e) {

				}
			}

			return returnString.toString();
		}
		return null;
	}*/

}
