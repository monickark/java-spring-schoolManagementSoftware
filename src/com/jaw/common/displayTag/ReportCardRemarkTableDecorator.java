package com.jaw.common.displayTag;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.decorator.TotalTableDecorator;
import org.displaytag.model.TableModel;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.MenuOptionConstant;
import com.jaw.common.constants.UrlConstant;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.MenuProfileUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mark.controller.MarkMasterVO;

public class ReportCardRemarkTableDecorator extends TableDecorator {
	private String id = "id";
	String key = null;
	String editKey = null;
	String addKey = null;
	CommonCodeUtil commonCodeUtil = new CommonCodeUtil();
	ApplicationCache applicationCache;
	SessionCache sessionCache;
	private TotalTableDecorator tableDecorator;
	MenuProfileUtil menuProfileUtil = new MenuProfileUtil();

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
		applicationCache = (ApplicationCache) pageContext
				.findAttribute(ApplicationConstant.APPLICATION_CACHE);
		sessionCache = (SessionCache) pageContext
				.findAttribute(ApplicationConstant.SESSION_CACHE_KEY);
	}

	public Integer getSerialno() {

		return getListIndex() + 1;
	}

	public String getHrefIcon() {

		MarkMasterVO markMasterVO = (MarkMasterVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();

		if (markMasterVO.getStatus().equals("R")) {

			String url2 = "rcMaintenance.htm?action="
					+ ApplicationConstant.MARKS_LOCKED + "&rowId="
					+ markMasterVO.getRowid();
			buffer.append("<a href=");
			buffer.append(url2);
			buffer.append(" id=\"edit\" class=\"icon-lock delete-info marklock\"	title=\"Close Marks\" style=\"float: right;\"></a>");
			String menuOption1 = menuProfileUtil.getMenuOption(
					sessionCache.getUserSessionDetails(),
					UrlConstant.REPORT_CARD_MAINTENANCE, applicationCache);

			System.out.println("Menu option for :"
					+ UrlConstant.REPORT_CARD_MAINTENANCE + " is :"
					+ menuOption1);
			if (menuOption1.equals(MenuOptionConstant.REPORT_CARD_MAINTENANCE)) {
				String url1 = "rcMaintenance.htm?process=Generate&rowId="
						+ markMasterVO.getRowid();
				buffer.append("<a href=");
				buffer.append(url1);
				buffer.append(" id=\"edit\" class=\"icon-list-alt quick-info generate\"	title=\"Generate Report card\" style=\"float: right;\"></a>");
			}
			// User started to add remarks if he have
			// permission allow him to update the remarks or just allow them to
			// view all marks
			// Check whether menu option is applicable

			String menuOption = menuProfileUtil.getMenuOption(
					sessionCache.getUserSessionDetails(),
					UrlConstant.REPORT_CARD_UPDATE_REMARKS, applicationCache);

			System.out.println("Menu option for :"
					+ UrlConstant.REPORT_CARD_UPDATE_REMARKS + " is :"
					+ menuOption);

			String url = "rcUpdateRemarks.htm?data&rowId="
					+ markMasterVO.getRowid() + "&process=update";
			if (menuOption
					.equals(MenuOptionConstant.REPORT_CARD_UPDATE_REMARKS)) {

				buffer.append("<a href=");
				buffer.append(url);
				buffer.append(" id=\"edit\" class=\"icon-signin quick-info\"	title=\"Modify\" style=\"float: right;\"></a>");
			} else {
				buffer.append("<a href=");
				buffer.append(url);
				buffer.append(" id=\"edit\" class=\"icon-bar-chart quick-info\"	title=\"View All marks\" style=\"float: right;\"></a>");
			}

			
			
		} 

		if ((markMasterVO.getStatus().equals("E"))
				|| (markMasterVO.getStatus().equals("R"))) {

			// If all marks entered allow user to add remarks if he have
			// permission or just allow them to view all marks
			// Check whether menu option is applicable

			String menuOption = menuProfileUtil.getMenuOption(
					sessionCache.getUserSessionDetails(),
					UrlConstant.REPORT_CARD_ADD_REMARKS, applicationCache);

			System.out.println("Menu option for :"
					+ UrlConstant.REPORT_CARD_ADD_REMARKS + " is :"
					+ menuOption);

			// For both case page will be same only jsp changes so get all
			// common things for that url
			String url = "rcAddRemarks.htm?rowId=" + markMasterVO.getRowid()
					+ "&process=add";
			;
			buffer.append("<input type=\"hidden\" class=\"rowId\" value='");
			buffer.append(markMasterVO.getRowid());
			buffer.append("'/>");

			if (menuOption.equals(MenuOptionConstant.REPORT_CARD_ADD_REMARKS)) {
				// Now the user have the permission so allow to add remarks
				buffer.append("<a href=");
				buffer.append(url);
				buffer.append(" id=\"edit\" class=\"icon-plus details-info add \"	title=\"Add\" style=\"float: right;\"></a>");
			} else {
				// User not having permission to add remarks so just simply view
				// all marks
				buffer.append("<a href=");
				buffer.append(url);
				buffer.append(" id=\"edit\" class=\"icon-bar-chart details-info add\"	title=\"View All marks\" style=\"float: right;\"></a>");
			}

		}
		
		else if (markMasterVO.getStatus().equals("G")) {
			String url = "rcMaintenance.htm?action="
					+ ApplicationConstant.MARKS_CLOSED + "&rowId="
					+ markMasterVO.getRowid();
			buffer.append("<a href=");
			buffer.append(url);
			buffer.append(" id=\"edit\" class=\"icon-lock delete-info markclose\"	title=\"Close Marks\" style=\"float: right;\"></a>");
			String url1 = "viewReportCard.htm?data&rcard=Get&rowId="
					+ markMasterVO.getRowid();
			buffer.append("<a href=");
			buffer.append(url1);
			buffer.append(" id=\"edit\" class=\"icon-bar-chart edit-info\"	title=\"View Report Card\" style=\"float: right;\"></a>");
			
			
			String url2 = "rcMaintenance.htm?process=Publish&rowId="
					+ markMasterVO.getRowid();
			buffer.append("<a href=");
			buffer.append(url2);
			buffer.append(" id=\"edit\" class=\"icon-bullhorn quick-info publish\"	title=\"Publish Report card\" style=\"float: right;\"></a>");
		} else if (markMasterVO.getStatus().equals("P")) {

			String url = "rcMaintenance.htm?action="
					+ ApplicationConstant.MARKS_CLOSED + "&rowId="
					+ markMasterVO.getRowid();
			buffer.append("<a href=");
			buffer.append(url);
			buffer.append(" id=\"edit\" class=\"icon-lock delete-info markclock\"	title=\"Close Marks\" style=\"float: right;\"></a>");
			String url1 = "viewReportCard.htm?data&rcard=Get&rowId="
					+ markMasterVO.getRowid();
			buffer.append("<a href=");
			buffer.append(url1);
			buffer.append(" id=\"edit\" class=\"icon-bar-chart edit-info\"	title=\"View Report Card\" style=\"float: right;\"></a>");
		} else if (markMasterVO.getStatus().equals("L")) {

			String url1 = "viewReportCard.htm?data&rcard=Get&rowId="
					+ markMasterVO.getRowid();
			buffer.append("<a href=");
			buffer.append(url1);
			buffer.append(" id=\"edit\" class=\"icon-bar-chart edit-info\"	title=\"View Report Card\" style=\"float: right;\"></a>");
		}

		return buffer.toString();
	}
}