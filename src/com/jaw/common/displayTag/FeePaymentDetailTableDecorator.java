package com.jaw.common.displayTag;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.decorator.TotalTableDecorator;
import org.displaytag.model.TableModel;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.core.controller.CourseTermLinkingVO;
import com.jaw.fee.controller.FeePaymentDetailVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.sessCache.SessionCache;

public class FeePaymentDetailTableDecorator extends TableDecorator {
	
	private String id = "id";
	String key = null;
	String editKey = null;
	String addKey = null;
	CommonCodeUtil commonCodeUtil = new CommonCodeUtil();
	ApplicationCache applicationCache;
	SessionCache sessionCache;
	String addRow=null;
	Map<String,String> courseMap=null;
	 List<CourseTermLinkingVO> courseTrmVos=null;
	private TotalTableDecorator tableDecorator;

	public TotalTableDecorator getTableDecorator() {
		return tableDecorator;
	}

	public void setTableDecorator(TotalTableDecorator tableDecorator) {
		this.tableDecorator = tableDecorator;
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
		addRow = (String) pageContext.getSession().getAttribute(
				"addAction");
		editKey = pageContext.getRequest().getParameter("edit");
		addKey = pageContext.getRequest().getParameter("add");
		applicationCache = (ApplicationCache) pageContext
				.findAttribute(ApplicationConstant.APPLICATION_CACHE);
		sessionCache = (SessionCache) pageContext
				.findAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		//System.out.println("Course Term Link Table decorator");
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
		FeePaymentDetailVO feePaymentDetailVO = (FeePaymentDetailVO) getCurrentRowObject();		
		
		List<CommonCode> feeCatCodes =null;
		List<CommonCode> feePaymentCode =null;
		if (((addRow != null) && (!addRow.equals("undefined"))
				&& (addRow.equals("AddAction"))&&(isLastRow()))) {
			try {
				 feeCatCodes = getcommonCodeList(CommonCodeConstant.FEE_CATEGORY);
			} catch (CommonCodeNotFoundException e) {
			}
			try {
				feePaymentCode = getcommonCodeList(CommonCodeConstant.FEE_PAYMENT_TERM);
			} catch (CommonCodeNotFoundException e) {
			}
					returnString.append("<tr>").append("<td>").append(getSerialno()+1).append("</td>");
					
					    
					returnString.append("<td>");
					
					
					returnString.append("<select class='span12 feePayement' name='feePayementId'>");
					 returnString.append("<option value=''>--Select--</option>");
					for (CommonCode commonCode : feePaymentCode) {						
						returnString.append("<option  value='")
									.append(commonCode.getCommonCode()).append("'>")
									.append(commonCode.getCodeDescription())
									.append(" </option>");						
					}
					returnString.append("</select>");
					returnString.append("</td>");

					returnString.append("<td>");
					returnString.append(
							"<div class='input-append date date-picker span12' data-date='2012-02-12' data-date-format='yyyy-mm-dd' data-date-viewmode='years' >")
							.append("<input class='m-wrap m-ctrl-medium date-picker dueDate span12' id='dateformat1' data-date-format='yyyy-mm-dd' size='16' type='text' style='width: 97px;'")
							.append("name='examDate' value=''");
					returnString.append("' readonly='true' ><span class='add-on'><i class='icon-calendar'></i></span></div>");
					returnString.append("</td>");
					returnString.append("<td>");
					
					 returnString.append("<select class='span12 feeCategory' name='FeeCategoryID'>");
					 returnString.append("<option value=''>--Select--</option>");
					 for (CommonCode commonCode : feeCatCodes) {						
							returnString.append("<option  value='")
										.append(commonCode.getCommonCode()).append("'>")
										.append(commonCode.getCodeDescription())
										.append(" </option>");						
						}
					
					    returnString.append("</select>");
						
					    returnString.append("</td>");
					
					returnString.append("<td>");
					returnString.append("<a href=");
					returnString.append("#");

					returnString.append(" id=\"addId\" class=\" icon-save quick-info add\"	title=\"Add \" style=\"float: center;\"></a>");
					returnString.append("<span class='help-inline' id='commonerror'></span>");
					returnString.append("</td>");
					returnString.append("</tr>");
			return returnString.toString();
		}
		return null;
	}
	

	
}