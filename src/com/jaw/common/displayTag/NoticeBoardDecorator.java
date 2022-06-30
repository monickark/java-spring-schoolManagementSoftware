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


import com.jaw.communication.controller.NoticeBoardVO;

public class NoticeBoardDecorator extends TableDecorator {
	private String id = "id";
	private String fieldName = "_chk";
	String key = null;
	
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
public String getNoticeDescription() {
		
	NoticeBoardVO noticeBoardVO = (NoticeBoardVO) getCurrentRowObject();
	System.out.println("desc decoratorrrrrrrr first"+noticeBoardVO.getNoticeDesc());
		StringBuffer buffer = new StringBuffer();
		if(noticeBoardVO.getNoticeDesc().length()>30){
		  buffer.append(noticeBoardVO.getNoticeDesc().substring(0,30));
		  buffer.append("<i class='icon-exclamation-sign moredesc quick-info' title='More Details' style='float: right;'></i>");
			
			buffer.append("<input type=\"hidden\"  class=\"moredeschidden\" id=\"markGradeId\" value=\"");
			buffer.append(noticeBoardVO.getNoticeDesc());
			buffer.append("\"");
		}else{
			 buffer.append(noticeBoardVO.getNoticeDesc());
		}
		System.out.println("desc decoratorrrrrrrr"+buffer.toString());
		
		
		
		
		return buffer.toString();
	}
}
