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
import com.jaw.core.controller.CourseClassesVO;
import com.jaw.core.controller.CourseTermLinkingVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mark.controller.MarkSubjectLinkListVO;

public class CourseTermLinkTableDecorator extends TableDecorator {
	
	private String id = "id";
	String key = null;
	String editKey = null;
	String addKey = null;
	CourseTermLinkingVO crsTrmLinkVo = null;
	String courseSearch = null;
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
		crsTrmLinkVo = (CourseTermLinkingVO) pageContext.getSession().getAttribute(
				"errorobject");
		courseSearch= (String) pageContext.getSession().getAttribute(
				"searchVo");
		editKey = pageContext.getRequest().getParameter("edit");
		addKey = pageContext.getRequest().getParameter("add");
		courseMap=(Map<String,String>)pageContext.getRequest().getAttribute("courseList");
		courseTrmVos=(List<CourseTermLinkingVO>)pageContext.getSession().getAttribute("display_tbl");
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
		CourseTermLinkingVO courseTermLinkingVO = (CourseTermLinkingVO) getCurrentRowObject();		
		System.out.println("key valuessssssssssssss :"+getSerialno());
		
		List<CommonCode> termCodes =null;
		if (((addRow != null) && (!addRow.equals("undefined"))
				&& (addRow.equals("AddAction"))&&(isLastRow()))) {
			try {
				 termCodes = getcommonCodeList(CommonCodeConstant.TERM);
			} catch (CommonCodeNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					returnString.append("<tr>").append("<td>").append(getSerialno()+1).append("</td>")
							.append("<td>");
					
					 returnString.append("<select class='span12 Course' name='CourseMasterID'>");
					
					 returnString.append("<option value=''>--Select--</option>");
					 Iterator it1 = courseMap.entrySet().iterator();
					    while (it1.hasNext()) {
					        Map.Entry pairs = (Map.Entry)it1.next();					       
					        System.out.println("Map"+pairs.getKey() + " = " + pairs.getValue());
							if ((crsTrmLinkVo!=null)&&(crsTrmLinkVo.getCourseMasterId().equals(
									pairs.getKey()))) {
								returnString.append("<option  value='")
										.append(pairs.getKey())
										.append("' selected='selected' >")
										.append(pairs.getValue())
										.append(" </option>");
							}else if((courseSearch!=null)&&(courseSearch.equals(pairs.getKey()))){
								returnString.append("<option  value='")
								.append(pairs.getKey())
								.append("' selected='selected' >")
								.append(pairs.getValue())
								.append(" </option>");
							}
								else {
							
								returnString.append("<option  value='")
										.append(pairs.getKey()).append("'>")
										.append(pairs.getValue())
										.append(" </option>");
							}
			
					    }
					
					    returnString.append("</select>");
						
						
					returnString.append("<td>");
					
					
					returnString.append("<select class='span12 Term' name='termId'>");
					 returnString.append("<option value=''>--Select--</option>");
					 
					for (CommonCode commonCode : termCodes) {		
						if ((crsTrmLinkVo!=null)&&(crsTrmLinkVo.getTermId().equals(
								commonCode.getCommonCode()))) {/*
						if ((courseTermLinkingVO.getTermId().equals(
								commonCode.getCommonCode()))&&((crsTrmLinkVo!=null)&&(crsTrmLinkVo.equals("Duplicate")))) {*/
							returnString.append("<option  value='")
									.append(commonCode.getCommonCode())
									.append("' selected='selected' >")
									.append(commonCode.getCodeDescription())
									.append(" </option>");
						} else {
							returnString.append("<option  value='")
									.append(commonCode.getCommonCode()).append("'>")
									.append(commonCode.getCodeDescription())
									.append(" </option>");
						}					
												
					}
					returnString.append("</select>");
					returnString.append("</td>");

					returnString.append("<td>");
					returnString.append("<input type='text' class='span6 termOrder' name='TERMORDER'  value=\"");
					
                 if(crsTrmLinkVo!=null){
                	 returnString.append(crsTrmLinkVo.getTermSerialOrder()).append(" \"");
					}else{
						returnString.append(" \"");
					}
							
                 returnString.append(" />");
					returnString.append("</td>");
					returnString.append("<td>")
					.append("<input type=\"hidden\" class=\"rowId\" value='")
					.append(courseTermLinkingVO.getRowId())
					.append("'/>");
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