package com.jaw.common.displayTag;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.TableModel;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.mark.controller.MarkSubjectLinkListVO;
import com.jaw.student.admission.dao.IStudentMasterListDAO;

import com.jaw.student.admission.dao.StudentMasterListDAO;
import com.jaw.student.controller.StudentDetainListVO;


public class StudentDetainDecorator extends TableDecorator {
	

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	private String id = "id";
	private String param = null;
	private String param1 = null;
	private String rowId = null;
	String addRow = null;
	String addNewRow = null;
	private Integer lastRow = null;

	private Map<String, String> mapOfStuNames;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void init(PageContext pageContext, Object decorated,
			TableModel tableModel) {
		param = pageContext.getRequest().getParameter("Data");
		addNewRow = pageContext.getRequest().getParameter("addRow");
		param1 = pageContext.getRequest().getParameter("stgGrpList");
		rowId = pageContext.getRequest().getParameter("rowId");
		addRow = (String) pageContext.getSession().getAttribute("addAction");
		lastRow = (Integer) pageContext.getSession().getAttribute("listSize");

		mapOfStuNames = (Map<String, String>) pageContext.getSession()
				.getAttribute("display_tbl1");

		ServletContext servletContext = pageContext.getServletContext();
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);	

	}

	public StringBuffer getRemarks() {

		StudentDetainListVO studentDetainListVO = (StudentDetainListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
	

	System.out.println("Param value :"+param);
		System.out.println("Row Id :"+rowId);
		System.out.println("Row Id in Student Detain List :"+studentDetainListVO.getRowid());
		
	
		if ((param != null) && (param.equals("Edit"))) {
			if (Integer.valueOf(studentDetainListVO.getRowid()) == Integer
					.valueOf(rowId)) {
					System.out.println("Inside row id equal method....");
				buffer.append("<input type='text' class='span6 remarksStuDetain'  name='remark'  value='");
				buffer.append(studentDetainListVO.getDetainRemarks());
				buffer.append("'");
				buffer.append("/>");
		//		buffer.append("<span class='errorMessage help-inline remarkStuDetainError'></span>");
				buffer.append("<br>");
			}else{
				System.out.println("Inside row id not equal imme method....");
				buffer.append("<input type='text' class='span6 remarksStuDetain'  name='remark'  value='");
				buffer.append(studentDetainListVO.getDetainRemarks());
				buffer.append("' readOnly='true'");
				buffer.append("/>");
		//		buffer.append("<span class='errorMessage help-inline remarkStuDetainError'></span>");
				buffer.append("<br>");
			
			}

		}else {
			System.out.println("Inside row id not equal method....");
			if (studentDetainListVO.getOldRec().equals("Y")) {
				buffer.append("<input type='text' class='span6 remarksStuDetain'  name='remark'  value='");
				buffer.append(studentDetainListVO.getDetainRemarks());
				buffer.append("' readOnly='true'");
				buffer.append("/>");
			//	buffer.append("<span class='errorMessage help-inline remarkStuDetainError'></span>");
				buffer.append("<br>");
			}
		}

		
		buffer.append("<span class='errorMessage help-inline remarkStuDetainError'></span>");
		return buffer;
	}

	public StringBuffer getAddOrDelete() {		

		StudentDetainListVO studentDetainListVO = (StudentDetainListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		
		if (((param != null) && (param.equals("Edit")))) {			
			if (Integer.valueOf(studentDetainListVO.getRowid()) == Integer
					.valueOf(rowId)) {
				buffer.append("<a href=");
				buffer.append("#");

				buffer.append(" id=\"editMark\" class=\"icon-signin quick-info updateStuDetain\"	title=\"Update\" style=\"float: center;\"></a>");
			}else{
				
				buffer.append("<a href=#");				

				buffer.append(" id=\"deleteStuDetain\" class=\"icon-remove edLink\" title=\"Delete\" style=\"float: center;\"></a>");
				buffer.append("<a href=");
				buffer.append("stuDetain.htm?Data=Edit&rowId=");
				buffer.append(studentDetainListVO.getRowid());

				buffer.append(" id=\"editInDetain\" class=\"icon-edit edit-info\" title=\"Edit\" style=\"float: center;\"></a>");
				
			}
		} else /*
				 * if(((param1!=null)&&(param1.equals("Get")))||((param1!=null)&&
				 * (param1.equals("")))||(((param1 != null) &&
				 * (param1.equals("addRow")))))
				 */{
		
			if (studentDetainListVO.getOldRec().equals("Y")) {			

				buffer.append("<a href=#");				

				buffer.append(" id=\"deleteStuDetain\" class=\"icon-remove edLink\" title=\"Delete\" style=\"float: center;\"></a>");
				buffer.append("<a href=");
				buffer.append("stuDetain.htm?Data=Edit&rowId=");
				buffer.append(studentDetainListVO.getRowid());

				buffer.append(" id=\"editInDetain\" class=\"icon-edit edit-info\" title=\"Edit\" style=\"float: center;\"></a>");
				
			}
		}

		buffer.append("<br/>");
		buffer.append("<span class='errorMessage help-inline minMarkError'></span>");
		return buffer;
	}

	public Integer getSerialno() {

		return getListIndex() + 1;
	}

	@Override
	public String finishRow() {

		StringBuffer returnString = new StringBuffer();
		StudentDetainListVO studentDetainListVO = (StudentDetainListVO) getCurrentRowObject();		

		if (((addRow != null) && (!addRow.equals("undefined"))
				&& (addRow.equals("AddAction")) && (studentDetainListVO
					.getRowid() == (lastRow)))) {			

			if ((studentDetainListVO.getOldRec().equals("N"))) {
			
				returnString.append("<tr>").append("<td><center>")
						.append(getSerialno() + 1).append("</center></td>");
				returnString.append("<td>");
				returnString
				.append("<select class='span12 m-wrap stuNameSelect' name='nameDropList' data-placeholder='Choose a Category' tabindex='1'");
				if (mapOfStuNames != null) {
				
					returnString.append("<option value=''>--Select--</option>");
					Iterator it1 = mapOfStuNames.entrySet().iterator();
					while (it1.hasNext()) {
						Map.Entry pairs = (Map.Entry) it1.next();
						if (studentDetainListVO.getStuAdmisNo().equals(
								pairs.getKey())) {
							returnString
									.append("<option selected='selected' value='");

						} else {
							returnString.append("<option  value='");

						}
						returnString.append(pairs.getKey()).append("'>")
								.append(pairs.getValue()).append(" </option>");

					}					
				}

				returnString.append("</select>");			
				returnString
						.append("</td>")
						.append("<td>")
						.append("<input type='text' class='span6 remarksStuDetain'  name='remarkForStuDetain'  value='")

						.append(studentDetainListVO.getDetainRemarks())
						.append("'/>").append("<span class='errorMessage help-inline remarkStuDetainError'></span>").append("</td>");
				returnString.append("<td>").append("</td>");
				returnString.append("</tr>");
				
				
				
				

				returnString.append("<tr>").append("<td><center>")
				.append(getSerialno() + 2).append("</center></td>");
				returnString.append("<td>");			
				if (mapOfStuNames != null) {
					
					returnString
					.append("<select class='span12 m-wrap stuNameSelect' name='nameDropList' data-placeholder='Choose a Category' tabindex='1'");					
					returnString.append("<option value=''>--Select--</option>");
					Iterator it1 = mapOfStuNames.entrySet().iterator();
					while (it1.hasNext()) {
						Map.Entry pairs = (Map.Entry) it1.next();
						
						returnString.append("<option  value='")
								.append(pairs.getKey()).append("'>")
								.append(pairs.getValue()).append(" </option>");

					}

					returnString.append("</select>");
					returnString
							.append("</td>")
							.append("<td>")
							.append("<input type='text' class='span6 remarksStuDetain'  name='remarkForStuDetain'  value=''/>")
							.append("<span class='errorMessage help-inline remarkStuDetainError'></span>")
							.append("</td>");
					returnString.append("<td>").append("</td>");
					returnString.append("</tr>");

				}

			

			} else {
				returnString.append("<tr>").append("<td><center>")
				.append(getSerialno() + 1).append("</center></td>");
				returnString.append("<td>");			
				if (mapOfStuNames != null) {
					
					returnString
					.append("<select class='span12 m-wrap stuNameSelect' name='nameDropList' data-placeholder='Choose a Category' tabindex='1'");					
					returnString.append("<option value=''>--Select--</option>");
					Iterator it1 = mapOfStuNames.entrySet().iterator();
					while (it1.hasNext()) {
						Map.Entry pairs = (Map.Entry) it1.next();
						
						returnString.append("<option  value='")
								.append(pairs.getKey()).append("'>")
								.append(pairs.getValue()).append(" </option>");

					}

					returnString.append("</select>");
					returnString
							.append("</td>")
							.append("<td>")
							.append("<input type='text' class='span6 remarksStuDetain'  name='remarkForStuDetain'  value=''/>")
							.append("<span class='errorMessage help-inline remarkStuDetainError'></span>")
							.append("</td>");
					returnString.append("<td>").append("</td>");
					returnString.append("</tr>");

				}

			}
		
			return returnString.toString();

		}else{
			if(studentDetainListVO.getNewRecMoved().equals("Y")){			
				returnString.append("<tr>").append("<td><center>")
				.append(studentDetainListVO.getRowid()).append("</center></td>");
		returnString.append("<td>");
		returnString
		.append("<select class='span12 m-wrap stuNameSelect' name='nameDropList' data-placeholder='Choose a Category' tabindex='1'");
		if (mapOfStuNames != null) {
		
			returnString.append("<option value=''>--Select--</option>");
			Iterator it1 = mapOfStuNames.entrySet().iterator();
			while (it1.hasNext()) {
				Map.Entry pairs = (Map.Entry) it1.next();
				if (studentDetainListVO.getStuAdmisNo().equals(
						pairs.getKey())) {
					returnString
							.append("<option selected='selected' value='");

				} else {
					returnString.append("<option  value='");

				}
				returnString.append(pairs.getKey()).append("'>")
						.append(pairs.getValue()).append(" </option>");

			}					
		}

		returnString.append("</select>");	
		returnString
				.append("</td>")
				.append("<td>")
				.append("<input type='text' class='span6 remarksStuDetain'  name='remarkForStuDetain'  value='")

				.append(studentDetainListVO.getDetainRemarks())
				.append("'/>").append("<span class='errorMessage help-inline remarkStuDetainError'></span>").append("</td>");
		returnString.append("<td>").append("</td>");
		returnString.append("</tr>");
			}
		}

		return null;
	}

}
