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

public class AddMarksMkslTableDecorator extends TableDecorator {
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
		//key = pageContext.getRequest().getParameter("rowid");
		key = (String) pageContext.getSession().getAttribute(
				"rowIdMarks");
		//key="1";
		editKey = pageContext.getRequest().getParameter("edit");
		addKey = pageContext.getRequest().getParameter("mark");
		selectAction = (String) pageContext.getSession().getAttribute(
				"seletedAction");
		markOrGrade = (String) pageContext.getSession().getAttribute(
				"markOrGrade");
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
	@Override
	public String addRowClass() {
		MarkSubjectLinkListForAddMarksVO markSubjectLinkListVO = (MarkSubjectLinkListForAddMarksVO) getCurrentRowObject();
		System.out.println("view index :" + getViewIndex());
		System.out.println("row Id :" + markSubjectLinkListVO.getRowid());
		System.out.println("key :" + key);
		if ((key != null)
				&& (markSubjectLinkListVO.getRowid() == Integer.parseInt(key))) {
			System.out.println("Inside highlight");
			return "highlight";
		} else {
			System.out.println("no highlight");
			return null;
		}
	}
	public Integer getSerialno() {

		return getListIndex() + 1;
	}
	public String getMaxMarkBox() {

		MarkSubjectLinkListForAddMarksVO markSubjectLinkListVO = (MarkSubjectLinkListForAddMarksVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
	      buffer.append(markSubjectLinkListVO.getMaxMark());
			buffer.append("<i class='icon-exclamation-sign remarksc quick-info' title='Details' style='float: right;'></i>");
			
			buffer.append("<input type=\"hidden\"  class=\"markGradeClass\" id=\"markGradeId\" value=\"");
			buffer.append(markSubjectLinkListVO.getMarkGrade());
			buffer.append("\"");
			buffer.append("/>");
			buffer.append("<input type=\"hidden\"  class=\"remarksListc\" id=\"markGradeId\" value=\"");
			buffer.append(markSubjectLinkListVO.getRemarks());
			buffer.append("\"");
			buffer.append("/>");
			buffer.append("<input type=\"hidden\"  class=\"portionListc\" id=\"markGradeId\" value=\"");
			buffer.append(markSubjectLinkListVO.getSubPortionDetails());
			buffer.append("\"");
			buffer.append("/>");
			
		return buffer.toString();
	}

	
	

	public String getMarkLink() {

		MarkSubjectLinkListForAddMarksVO marSubList = (MarkSubjectLinkListForAddMarksVO) getCurrentRowObject();		
		StringBuffer buffer = new StringBuffer();
		if((marSubList.getStatus().equals(ApplicationConstant.MARKS_STATUS_OPEN))||(marSubList.getStatus().equals(ApplicationConstant.MARKS_STATUS_ENTERED))){
		
		buffer.append("<a href=addMarks.htm?addMarksParam&mark=add&rowid="
				+ marSubList.getRowid())
				.append(" class='icon-plus details-info' title='Add Marks' id='addMarks'></a>");
		}
		if((marSubList.getStatus().equals(ApplicationConstant.MARKS_STATUS_ENTERED))&&(!marSubList.getStatus().equals(ApplicationConstant.MARKS_LOCKED))){
				buffer.append("<status:acterm acTerm=\"${addMarkMaster.addMarkSearch.acTerm }\" output=\"result\" ");
				buffer.append("/>");
				buffer.append("<check:isMenuOptionPresent menuOption=\"STAMAAA\" output=\"result\" ");
				buffer.append("/>");
				buffer.append("<c:if test=\"${result ne 'C'  && result eq'true'}\"" );
				buffer.append(">");
		buffer.append("<a href=addMarks.htm?editMarksParam&rowid=")
				.append(marSubList.getRowid())
				.append(" class='icon-edit edit-info' title='Edit Marks' id='addMarks'></a>");
				buffer.append("</c:if>");
		}
		/*if(marSubList.getStatus().equals(ApplicationConstant.MARKS_STATUS_ENTERED)){
			buffer.append("<a href=updateMarks.htm?rowid=")
					.append(marSubList.getRowid())
					.append(" class='icon-edit edit-info' title='Edit Marks' id='addMarks'></a>");
			}*/
		
		if((marSubList.getStatus().equals(ApplicationConstant.MARKS_STATUS_ENTERED))||(marSubList.getStatus().equals(ApplicationConstant.MARKS_LOCKED))){
		buffer.append("<a href=addMarks.htm?viewMarksParam&rowid=")
				.append(marSubList.getRowid())
				.append(" class='icon-file details-info' title='View Marks' id='addMarks'></a>");
		}
		
		return buffer.toString();
	}

	

}