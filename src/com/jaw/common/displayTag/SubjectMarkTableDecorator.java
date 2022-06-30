package com.jaw.common.displayTag;

import java.util.List;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.decorator.TotalTableDecorator;
import org.displaytag.model.TableModel;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mark.controller.MarkSubjectLinkListVO;

public class SubjectMarkTableDecorator extends TableDecorator {
	private String id = "id";
	String key = null;
	String editKey = null;
	String addKey = null;
	CommonCodeUtil commonCodeUtil = new CommonCodeUtil();
	ApplicationCache applicationCache;
	SessionCache sessionCache;
	private TotalTableDecorator tableDecorator;
	private String type = "";

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
		editKey = pageContext.getRequest().getParameter("edit");
		addKey = pageContext.getRequest().getParameter("add");
		System.out.println("key :" + key + " editKey :" + editKey + " addKey :"
				+ addKey);
		if ((addKey != null) && (addKey.equals("sa"))) {
			System.out.println("Inside add with error");
			key = String.valueOf(Integer.parseInt(key) + 1);
			System.out.println("Key :" + key);
		}
		applicationCache = (ApplicationCache) pageContext
				.findAttribute(ApplicationConstant.APPLICATION_CACHE);
		sessionCache = (SessionCache) pageContext
				.findAttribute(ApplicationConstant.SESSION_CACHE_KEY);
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
		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		System.out.println("To String :" + markSubjectLinkListVO.toString());
		if ((key != null) && (!key.equals("undefined")) && (addKey != null)
				&& (addKey.equals("add"))
				&& (Integer.parseInt(key) == markSubjectLinkListVO.getRowId())) {
			{
				type = "add";
				try {
					System.out.println("inside finish row add" + key + addKey);
					returnString.append("<tr>").append("<td>").append("</td>")
							.append("<td>").append(getSubject());
					returnString.append("<td>");
					returnString
							.append(getExamTypeHelper())
							.append("</td>")

							.append("<td>")
							.append(getSubTestHelper())
							.append("</td>")

							.append("<td>")
							.append(getLabBatchHelper())
							.append("</td>")

							.append("<td>")
							.append(getExamDateBoxHelper())
							.append("</td>")

							.append("<td>")
							.append(getDurationHelper())
							.append("</td>")

							.append("<td>")
							.append(getStartTimeHelper())
							.append("</td>")

							.append("<td>")
							.append(getMinMarkBoxHelper())
							.append("</td>")
							.append("<td>")
							.append(getMaxMarkBoxHelper())
							.append("</td>")
							.append("<td>")
							.append("<a class='save icon-save quick-info' title='Save' href='#' style='float: right;'></a>")
							.append("<input type=\"hidden\" class=\"rowId\" value='")
							.append(markSubjectLinkListVO.getRowId())
							.append("'/>").append("</td>").append("</tr>");
				} catch (CommonCodeNotFoundException e) {

				}
			}
			System.out.println("return string :" + returnString);
			return returnString.toString();
		}
		return null;
	}

	public String getSubject() throws CommonCodeNotFoundException {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		System.out.println(" ");
		buffer.append("<input type='hidden' class='crslId' value=\"")
				.append(markSubjectLinkListVO.getCrslId()).append(" \"")
				.append(" />");
		buffer.append(markSubjectLinkListVO.getSubName());
		return buffer.toString();
	}

	public String getExamType() throws CommonCodeNotFoundException {
		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if (((editKey != null) && (editKey.equals("edit")) && ((key != null) && (Integer
				.parseInt(key) == markSubjectLinkListVO.getRowId())))
				|| (markSubjectLinkListVO.getMarkSubjectLinkId() == null)) {
			buffer = getExamTypeHelper();
		} else {

			buffer.append(getDescriptionByTypeAndCode(
					CommonCodeConstant.CLASS_TYPE,
					markSubjectLinkListVO.getExamType()));

		}
		return buffer.toString();

	}

	public String getSubTest() throws CommonCodeNotFoundException {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		if (((editKey != null) && (editKey.equals("edit")) && ((key != null) && (Integer
				.parseInt(key) == markSubjectLinkListVO.getRowId())))
				|| (markSubjectLinkListVO.getMarkSubjectLinkId() == null)) {
			buffer = getSubTestHelper();
		} else {

			buffer.append(getDescriptionByTypeAndCode(
					CommonCodeConstant.SUB_TEST,
					markSubjectLinkListVO.getSubTestId()));

		}

		return buffer.toString();
	}

	public String getLabBatch() throws CommonCodeNotFoundException {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		if (((editKey != null) && (editKey.equals("edit")) && ((key != null) && (Integer
				.parseInt(key) == markSubjectLinkListVO.getRowId())))
				|| (markSubjectLinkListVO.getMarkSubjectLinkId() == null)) {
			buffer = getLabBatchHelper();
		} else {

			if ((markSubjectLinkListVO.getLabBatch() != null)
					&& (markSubjectLinkListVO.getLabBatch().equals("NA"))) {
				buffer.append("Not Applicable");
			} else {

				buffer.append(getDescriptionByTypeAndCode(
						CommonCodeConstant.LAB_BATCH,
						markSubjectLinkListVO.getLabBatch()));
			}
		}

		return buffer.toString();
	}

	public String getMinMarkBox() {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if (((editKey != null) && (editKey.equals("edit")) && ((key != null) && (Integer
				.parseInt(key) == markSubjectLinkListVO.getRowId())))
				|| (markSubjectLinkListVO.getMarkSubjectLinkId() == null)) {
			buffer = getMinMarkBoxHelper();
		} else {

			buffer.append(markSubjectLinkListVO.getMinMark());

		}

		return buffer.toString();
	}

	public String getDurationBox() {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		if (((editKey != null) && (editKey.equals("edit")) && ((key != null) && (Integer
				.parseInt(key) == markSubjectLinkListVO.getRowId())))
				|| (markSubjectLinkListVO.getDuration() == null)) {

			buffer = getDurationHelper();

		} else {

			buffer.append(markSubjectLinkListVO.getDuration());

		}

		return buffer.toString();
	}

	public String getStartTimeBox() {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		if (((editKey != null) && (editKey.equals("edit")) && ((key != null) && (Integer
				.parseInt(key) == markSubjectLinkListVO.getRowId())))
				|| (markSubjectLinkListVO.getStartTime() == null)) {

			buffer = getStartTimeHelper();

		} else {

			buffer.append(markSubjectLinkListVO.getStartTime());

		}

		return buffer.toString();
	}

	public String getMaxMarkBox() {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if (((editKey != null) && (editKey.equals("edit")) && ((key != null) && (Integer
				.parseInt(key) == markSubjectLinkListVO.getRowId())))
				|| (markSubjectLinkListVO.getMarkSubjectLinkId() == null)) {
			buffer = getMaxMarkBoxHelper();
		} else {

			buffer.append(markSubjectLinkListVO.getMaxMark());
			buffer.append("<i class='icon-exclamation-sign note quick-info' title='Details' style='float: right;'></i>");
		}

		return buffer.toString();
	}

	public String getExamDateBox() {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		if (((editKey != null) && (editKey.equals("edit")) && ((key != null) && (Integer
				.parseInt(key) == markSubjectLinkListVO.getRowId())))
				|| (markSubjectLinkListVO.getMarkSubjectLinkId() == null)) {

			buffer = getExamDateBoxHelper();
		} else {
			if ((markSubjectLinkListVO.getExamDate() != null)
					&& (!markSubjectLinkListVO.getExamDate().equals(""))) {
				buffer.append(markSubjectLinkListVO.getExamDate());
			}
		}

		return buffer.toString();
	}

	private StringBuffer getExamTypeHelper() throws CommonCodeNotFoundException {
		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		List<CommonCode> batchTypeCodes = getcommonCodeList(CommonCodeConstant.CLASS_TYPE);

		if (markSubjectLinkListVO.getRequiresLab().equals("N")) {
			buffer.append("<select class='span12 examType' name='examtype'>");
			for (CommonCode commonCode : batchTypeCodes) {

				if (commonCode.getCommonCode().equals(
						ApplicationConstant.THEORY)) {
					buffer.append("<option  value='")
							.append(commonCode.getCommonCode()).append("'>")
							.append(commonCode.getCodeDescription())
							.append(" </option>");

				}
			}
		} else {
			buffer.append("<select class='span12 examType' name='examType'>");
			buffer.append("<option  value=''>Select Any One</option>");
			for (CommonCode commonCode : batchTypeCodes) {
				if (type.equals("add")) {
					buffer.append("<option  value='")
							.append(commonCode.getCommonCode()).append("'>")
							.append(commonCode.getCodeDescription())
							.append(" </option>");
				} else if (markSubjectLinkListVO.getExamType().equals(
						commonCode.getCommonCode())) {
					buffer.append("<option  value='")
							.append(commonCode.getCommonCode())
							.append("' selected='selected' >")
							.append(commonCode.getCodeDescription())
							.append(" </option>");
				} else {
					buffer.append("<option  value='")
							.append(commonCode.getCommonCode()).append("'>")
							.append(commonCode.getCodeDescription())
							.append(" </option>");
				}
			}
		}
		System.out.println("append value :" + buffer.toString());

		buffer.append("<br/>");
		buffer.append("<span class='errorMessage help-inline examTypeError'></span>");

		return buffer;

	}

	public StringBuffer getSubTestHelper() throws CommonCodeNotFoundException {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		List<CommonCode> batchTypeCodes = getcommonCodeList(CommonCodeConstant.SUB_TEST);
		buffer.append("<select class='span12 subTest'  name='subTest'>");
		for (CommonCode commonCode : batchTypeCodes) {
			if (markSubjectLinkListVO.getSubTestId().equals(
					commonCode.getCommonCode())) {
				buffer.append("<option  value='")
						.append(commonCode.getCommonCode())
						.append("' selected='selected'>")
						.append(commonCode.getCodeDescription())
						.append(" </option>");
			} else {
				buffer.append("<option  value='")
						.append(commonCode.getCommonCode()).append("'>")
						.append(commonCode.getCodeDescription())
						.append(" </option>");
			}
		}

		buffer.append("<br/>");
		buffer.append("<span class='errorMessage help-inline subTestError'></span>");
		return buffer;
	}

	public StringBuffer getLabBatchHelper() throws CommonCodeNotFoundException {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		List<CommonCode> batchTypeCodes = getcommonCodeList(CommonCodeConstant.LAB_BATCH);
		buffer.append("<select class='span12 labBatch' name='labBatch' >");
		System.out.println("Lab batch size :" + batchTypeCodes.size());

		if (markSubjectLinkListVO.getExamType().equals(
				ApplicationConstant.THEORY)) {
			buffer.append("<option value='NA'>Not Applicable</option>");
		}
		buffer.append("<option  value=''>Select Any One</option>");
		for (CommonCode commonCode : batchTypeCodes) {
			System.out.println("Lab batch code :" + commonCode.getCommonCode());
			System.out.println("type in lab batch :" + type);
			if (type.equals("add")) {
				buffer.append("<option  value='")
						.append(commonCode.getCommonCode()).append("' >")
						.append(commonCode.getCodeDescription())
						.append(" </option>");
			} else if (markSubjectLinkListVO.getLabBatch() != null
					&& (markSubjectLinkListVO.getLabBatch().equals(commonCode
							.getCommonCode()))) {
				System.out.println("Inside lab batch != null");
				System.out.println("lab batch :"
						+ markSubjectLinkListVO.getLabBatch());
				buffer.append("<option  value='")
						.append(commonCode.getCommonCode())
						.append("' selected='selected' >")
						.append(commonCode.getCodeDescription())
						.append(" </option>");

			} else {
				System.out.println("Inside lab batch == null");
				buffer.append("<option  value='")
						.append(commonCode.getCommonCode()).append("' >")
						.append(commonCode.getCodeDescription())
						.append(" </option>");
			}
		}

		buffer.append("<br/>");
		buffer.append("<span class='errorMessage help-inline labBatchError'></span>");
		return buffer;
	}

	public StringBuffer getMinMarkBoxHelper() {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		System.out.println("row id in date:" + markSubjectLinkListVO.getRowId()
				+ " key :" + key + "edit key :" + editKey);
		buffer.append("<input type='text' class='span6 minMarks'  name='minMarks'  value='");
		if (markSubjectLinkListVO.getMinMark() == null) {
			buffer.append("0");
		} else {
			buffer.append(markSubjectLinkListVO.getMinMark());
		}
		buffer.append("'");
		if (markSubjectLinkListVO.getMarkOrGrade().equals(
				ApplicationConstant.GRADE)) {
			buffer.append(" readonly='readonly' ");
		}
		buffer.append("/>");

		buffer.append("<br/>");
		buffer.append("<span class='errorMessage help-inline minMarkError'></span>");
		return buffer;
	}

	public StringBuffer getDurationHelper() {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		System.out.println("row id in date:" + markSubjectLinkListVO.getRowId()
				+ " key :" + key + "edit key :" + editKey);
		buffer.append("<input type='text' class='span12 duration' placeholder='Eg: 1:30hrs'  name='duration'  value='");

		if (markSubjectLinkListVO.getDuration() == null) {
			buffer.append("");
		} else {
			buffer.append(markSubjectLinkListVO.getDuration());
		}
		buffer.append("'");
		buffer.append("/>");

		buffer.append("<br/>");
		buffer.append("<span class='errorMessage help-inline durationError'></span>");
		return buffer;
	}

	public StringBuffer getStartTimeHelper() {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		System.out.println("row id in date:" + markSubjectLinkListVO.getRowId()
				+ " key :" + key + "edit key :" + editKey);
		buffer.append("<input type='text' class='span12 startTime'  name='startTime'  value='");

		if (markSubjectLinkListVO.getStartTime() == null) {
			buffer.append("");
		} else {
			buffer.append(markSubjectLinkListVO.getStartTime());
		}

		buffer.append("'");
		buffer.append("/>");

		buffer.append("<br/>");
		buffer.append("<span class='errorMessage help-inline durationError'></span>");
		return buffer;
	}

	public StringBuffer getMaxMarkBoxHelper() {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		buffer.append("<input type='text' class='span6 maxMarks' name='maxMarks'  value='");
		if ((markSubjectLinkListVO.getMaxMark() == null)
				|| (type.equals("add"))) {
			buffer.append("0");
		} else {
			buffer.append(markSubjectLinkListVO.getMaxMark());
		}
		buffer.append("'");
		if (markSubjectLinkListVO.getMarkOrGrade().equals(
				ApplicationConstant.GRADE)) {
			buffer.append(" readonly='readonly' ");
		}
		buffer.append("/>")
				.append("<i class='icon-pencil note edit-info' style='float: right;'> </i>");

		buffer.append("<br/>");
		buffer.append("<span class='errorMessage help-inline maxMarkError'></span>");
		return buffer;
	}

	public StringBuffer getExamDateBoxHelper() {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		buffer.append(
				"<div class='input-append date date-picker span6' data-date='2012-02-12' data-date-format='yyyy-mm-dd' data-date-viewmode='years' >")
				.append("<input class='m-wrap m-ctrl-medium date-picker examDate' id='dateformat1' data-date-format='yyyy-mm-dd' size='16' type='text' style='width: 97px;'")
				.append("name='examDate' value='");
		if (editKey != null) {
			buffer.append(markSubjectLinkListVO.getExamDate());
		} else {
			buffer.append("");
		}

		buffer.append("' readonly='true' ><span class='add-on'><i class='icon-calendar'></i></span></div>");

		buffer.append("<br/> <br/>");
		buffer.append("<span class='errorMessage help-inline examDateError'></span>");
		return buffer;
	}

	public String getHrefIcon() {

		MarkSubjectLinkListVO markSubjectLinkListVO = (MarkSubjectLinkListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		if ((markSubjectLinkListVO.getExamId() != null)
				&& (!markSubjectLinkListVO.getExamId().equals(""))) {
			if ((editKey != null)
					&& (key != null)
					&& (Integer.parseInt(key) == markSubjectLinkListVO
							.getRowId())) {
				if(markSubjectLinkListVO.getStatus().equals(ApplicationConstant.MARK_SUB_LINK_OPEN)) {
					
				
				String url = "markSubjectLink.htm?actionUpdate=update&data";
				buffer.append("<a href=");
				buffer.append("#");

				buffer.append(" id=\"edit\" class=\"icon-signin update quick-info\"	title=\"Update\" style=\"float: right;\"></a>");
				}
			} else {
				if(markSubjectLinkListVO.getStatus().equals(ApplicationConstant.MARK_SUB_LINK_OPEN)) {
					
				String url = "markSubjectLink.htm?data&edit=edit&rowId="
						+ markSubjectLinkListVO.getRowId();
				buffer.append("<a href=");
				buffer.append(url);
				buffer.append(" id=\"edit\" class=\"icon-edit edit-info\"	title=\"Edit\" style=\"float: right;\"></a>");
				}
			}
			if(markSubjectLinkListVO.getStatus().equals(ApplicationConstant.MARK_SUB_LINK_OPEN)) {
				
			String url1 = "markSubjectLink.htm?Get";

			buffer.append("<a href=");
			buffer.append("#");
			buffer.append(" id=\"edit\" class=\"icon-remove  delete delete-info\"	title=\"Delete\" style=\"float: right;\"></a>");
			}
			String url = "markSubjectLink.htm?data&add=add&rowId="
					+ markSubjectLinkListVO.getRowId();

			buffer.append("<a href=");
			buffer.append(url);

			buffer.append(" id=\"edit\" class=\"icon-plus details-info add\"	title=\"Add\" style=\"float: right;\"></a>");
			buffer.append("<input type=\"hidden\" class=\"seqId\" value='");
			buffer.append(markSubjectLinkListVO.getMarkSubjectLinkId());
			buffer.append("'/>");
		} else {
			System.out.println("save started");
			buffer.append("<a class='save icon-save quick-info' title='Save' href='#' style='float: right;'></a>");
		}
		System.out.println("save finished");
		buffer.append("<input type=\"hidden\" class=\"rowId\" value='");
		buffer.append(markSubjectLinkListVO.getRowId());
		buffer.append("'/>");
		System.out.println("row id finished");
		buffer.append("<input type=\"hidden\" class=\"remarksTxt\" value='");
		if (markSubjectLinkListVO.getRemarks() == null) {
			buffer.append("");
		} else {
			buffer.append(markSubjectLinkListVO.getRemarks());
		}
		buffer.append("'/>");
		buffer.append("<input type=\"hidden\" class=\"detailsTxt\" value='");
		if (markSubjectLinkListVO.getSubPortionDetails() == null) {
			buffer.append("");
		} else {
			buffer.append(markSubjectLinkListVO.getSubPortionDetails());
		}

		buffer.append("'/>");
		return buffer.toString();
	}

}