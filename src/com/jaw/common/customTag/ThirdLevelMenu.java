package com.jaw.common.customTag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.util.AcademicTermUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.MenuProfileOptionLinking;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public class ThirdLevelMenu extends TagSupport {

	private final Logger logger = Logger.getLogger(ThirdLevelMenu.class);
	@Autowired
	ApplicationCache applicationCache;
	private List<MenuProfileOptionLinking> menuProfileOptionLinkings;
	private String tag;
	private String output;
	private String url;
	AcademicTermUtil academicTermUtil = new AcademicTermUtil();

	public ApplicationCache getApplicationCache() {
		return applicationCache;
	}

	public void setApplicationCache(ApplicationCache applicationCache) {
		this.applicationCache = applicationCache;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			applicationCache = (ApplicationCache) pageContext
					.findAttribute(ApplicationConstant.APPLICATION_CACHE);
			SessionCache sessionCache = (SessionCache) pageContext
					.findAttribute(ApplicationConstant.SESSION_CACHE_KEY);

			logger.debug("URL passed :" + url);
			UserSessionDetails userSessionDetails = sessionCache
					.getUserSessionDetails();
			String key = userSessionDetails.getInstId() + "-"
					+ userSessionDetails.getBranchId() + "-"
					+ userSessionDetails.getUserMenuProfile() + "-" + url;
			MenuProfileOptionLinking currentNode = applicationCache
					.getMenuIdList().get(key);
			
			menuProfileOptionLinkings = sessionCache.getOptionLinkings();
			if (menuProfileOptionLinkings.size() != 0) {
				tag = "";
				tag += "<ul class='nav nav-tabs'>";
				for (MenuProfileOptionLinking menuProfileOptionLinking : menuProfileOptionLinkings) {
					
				
				
					if ((menuProfileOptionLinking.getMenuNode() == currentNode
							.getMenuNode())&&(menuProfileOptionLinking.getMenuLevel() == 2)
							&& (menuProfileOptionLinking.getLevelOrder() == currentNode.getLevelOrder())) {
						System.out.println("Contains menu Option :"
								+ menuProfileOptionLinking.toString());

						if (menuProfileOptionLinking.getMenuUrl().equals(url)) {
							tag += "<li class='active'>";
						} else {
							tag += "<li>";
						}
						tag += "<a href='"
								+ menuProfileOptionLinking.getMenuUrl() + "'>"
								+ "	<h5> <i class='"
								+ menuProfileOptionLinking.getMenuIcon() + "'>"
								+ "</i> "
								+ menuProfileOptionLinking.getMenuDescription()
								+ "	</h5> </a></li>";

					}
				}
				
			}
			System.out.println("tag :" + tag);
			tag += "</ul> ";
			pageContext.setAttribute(output, tag, PageContext.REQUEST_SCOPE);

		} catch (Exception e) {
			logger.error("Failed to Load Values from application Cache", e);
		}

		return SKIP_BODY;
	}
}