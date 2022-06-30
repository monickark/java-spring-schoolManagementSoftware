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
import com.jaw.framework.appCache.dao.AcademicTerm;
import com.jaw.framework.appCache.dao.MenuProfileOptionLinking;
import com.jaw.framework.sessCache.SessionCache;

public class CheckMenuOption extends TagSupport {

	private final Logger logger = Logger.getLogger(CheckMenuOption.class);
	@Autowired
	ApplicationCache applicationCache;
	private List<MenuProfileOptionLinking> menuProfileOptionLinkings;
	private Boolean status = false;
	private String output;
	private String menuOption;
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMenuOption() {
		return menuOption;
	}

	public void setMenuOption(String menuOption) {
		this.menuOption = menuOption;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			applicationCache = (ApplicationCache) pageContext
					.findAttribute(ApplicationConstant.APPLICATION_CACHE);
			SessionCache sessionCache = (SessionCache) pageContext
					.findAttribute(ApplicationConstant.SESSION_CACHE_KEY);

			logger.debug("Menu Option passed :" + menuOption);

			menuProfileOptionLinkings = sessionCache.getOptionLinkings();
			if (menuProfileOptionLinkings.size() != 0) {
				for (MenuProfileOptionLinking menuProfileOptionLinking : menuProfileOptionLinkings) {
					if (menuProfileOptionLinking.getMenuOption().equals(
							menuOption)) {
						System.out.println("Contains menu Option :"+menuProfileOptionLinking.getMenuOption());
						status = true;
					}
				}
				pageContext.setAttribute(output, status,
						PageContext.REQUEST_SCOPE);

			} else {
				logger.debug("No academic term found :" + " branch :"
						+ sessionCache.getUserSessionDetails().getBranchId()
						+ " instid:"
						+ sessionCache.getUserSessionDetails().getInstId());
			}

		} catch (Exception e) {
			logger.error("Failed to Load Values from application Cache", e);
		}

		return SKIP_BODY;
	}
}