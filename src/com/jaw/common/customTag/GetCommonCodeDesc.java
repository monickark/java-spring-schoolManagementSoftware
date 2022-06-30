package com.jaw.common.customTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

public class GetCommonCodeDesc extends TagSupport {
	@Autowired
	ApplicationCache applicationCache;
	private final Logger logger = Logger.getLogger(GetCommonCodeDesc.class);
	
	private String output;
	private String code;
	private String type;
	private String branchId;
	
	public String getBranchId() {
		return branchId;
	}
	
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	CommonCodeUtil commonCodeUtil = new CommonCodeUtil();
	
	public String getOutput() {
		return output;
	}
	
	public void setOutput(String output) {
		this.output = output;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public int doStartTag() throws JspException {
		try {
			applicationCache = (ApplicationCache) pageContext
					.findAttribute(ApplicationConstant.APPLICATION_CACHE);
			SessionCache sessionCache = (SessionCache) pageContext
					.findAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			String branch = null;
			
			if ((getBranchId() == null) || (getBranchId().equals(""))) {
				//logger.debug("User didnt passed branch id taken from session cache");
				branch = sessionCache.getUserSessionDetails().getBranchId();
			}
			else {
				//logger.debug("User passed branch id :" + getBranchId());
				branch = getBranchId();
				
			}
			String description = commonCodeUtil.getDescriptionByTypeAndCode(
					applicationCache, type.trim(), code.trim(), sessionCache
							.getUserSessionDetails().getInstId(), branch);
			pageContext.setAttribute(output, description,
					PageContext.REQUEST_SCOPE);
			
		}
		catch (Exception exception) {
			logger.error("Failed to Load Values from application Cache",
					exception);
		}
		return SKIP_BODY;
	}
}
