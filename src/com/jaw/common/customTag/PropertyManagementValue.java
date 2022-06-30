package com.jaw.common.customTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

import org.apache.log4j.Logger;
public class PropertyManagementValue extends TagSupport {
	private final Logger logger = Logger.getLogger(GetCommonCodeDesc.class);
	
	@Autowired
	ApplicationCache applicationCache;
	
	PropertyManagementUtil propertyManagementUtil=new PropertyManagementUtil();
	
	private String propertyId;
	private String branchId;
	private String value;
	private String result;
	public String getBranchId() {
		return branchId;
	}
	
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	
	
	
	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public int doStartTag() throws JspException {
	//	System.out.println("property valuesssssssssss tagggggggggggggg"+propertyId.trim());
		try {
			applicationCache = (ApplicationCache) pageContext
					.findAttribute(ApplicationConstant.APPLICATION_CACHE);
			SessionCache sessionCache = (SessionCache) pageContext
					.findAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			String branch = null;
			/*System.out.println("property valuesssssssssss tagggggggggggggg Appn Cache"+applicationCache);
			System.out.println("property valuesssssssssss tagggggggggggggg Session Cache"+sessionCache);*/
			if ((getBranchId() == null) || (getBranchId().equals(""))) {
				logger.debug("User didnt passed branch id taken from session cache");
				branch = sessionCache.getUserSessionDetails().getBranchId();
			}
			else {
				logger.debug("User passed branch id :" + getBranchId());
				branch = getBranchId();
				
			}
			/*System.out.println("property valuesssssssssss tagggggggggggggg"+propertyId.trim());
			System.out.println("property valuesssssssssss tagggggggggggggg"+sessionCache.getUserSessionDetails().getInstId());
			System.out.println("property valuesssssssssss tagggggggggggggg"+sessionCache.getUserSessionDetails().getBranchId());*/
			String propertyValue = propertyManagementUtil.getPropertyValue(
					applicationCache, sessionCache.getUserSessionDetails().getInstId(),
					sessionCache.getUserSessionDetails().getBranchId(),
					propertyId.trim());
			//System.out.println("property valuesssssssssss"+propertyValue);
			pageContext.setAttribute(result, propertyValue,
					PageContext.REQUEST_SCOPE);
			
			
		}
		catch (Exception exception) {
			logger.error("Failed to Load Values from application Cache",
					exception);
		}
		return SKIP_BODY;
	}
}
