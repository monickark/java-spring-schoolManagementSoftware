package com.jaw.common.customTag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.framework.appCache.dao.MenuProfileOptionLinking;
import com.jaw.framework.sessCache.SessionCache;

public class GetBreadCrumbs extends TagSupport {
	
	private final Logger logger = Logger.getLogger(GetBreadCrumbs.class);
	@Autowired
	SessionCache sessionCache;
	private List<MenuProfileOptionLinking> list;
	private String output;
	private String node;
	
	public String getNode() {
		return node;
	}
	
	public void setNode(String node) {
		this.node = node;
	}
	
	public String getOutput() {
		return output;
	}
	
	public void setOutput(String output) {
		this.output = output;
	}
	
	@Override
	public int doStartTag() throws JspException {
		try {
			
			sessionCache = (SessionCache) pageContext
					.findAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			list = sessionCache.getUserBreadCrumbs();
			
			String result = " <ul class='breadcrumb'>";
			if (list.size() == 2) {
				
				if ((list.get(1).getSlmFlg().equals("Y"))) {
					//logger.debug("List size is two and slm flg='Y' remove one object");
					list.remove(1);
				}
			}
			for (int i = 0; i < list.size(); i++) {
				
				MenuProfileOptionLinking menuProfileOptionLinking = list.get(i);
				String url = menuProfileOptionLinking.getMenuUrl();
				if ((i == 0)) {
					/*logger.debug("Inside node 0 and slmflg='Y' :"
							+ menuProfileOptionLinking.getMenuDescription());*/
					result += "<li> <i class="
							+ menuProfileOptionLinking.getMenuIcon()
							+ " ></i><a>"
							+ menuProfileOptionLinking.getMenuDescription()
							+ "</a><i class='icon-angle-right'></i></li>";
				}
				else if (i == list.size() - 1) {
					/*logger.debug("Inside last node :"
							+ menuProfileOptionLinking.getMenuDescription());*/
					result += "<li> <b> <a>"
							+ menuProfileOptionLinking.getMenuDescription()
							+ "</a></b></i></li>";
				}
				else {
					/*logger.debug("Inside except first and last node and slmflg='N' :"
							+ menuProfileOptionLinking.getMenuDescription());*/
					result += "<li><a href='" + url + "'>"
							+ menuProfileOptionLinking.getMenuDescription()
							+ "</a> " + "<i class='icon-angle-right'></i></li>";
				}
			}
			
			result += "</ul>";
			pageContext.setAttribute(output, result, PageContext.REQUEST_SCOPE);
			if ((node != null) && (list.size() != 0)) {
				pageContext.setAttribute(node, list.get(0).getMenuNode(),
						PageContext.REQUEST_SCOPE);
			}
			
		}
		catch (Exception e) {
			logger.error("Failed to Load Values from application Cache", e);
		}
		
		return SKIP_BODY;
	}
	
}