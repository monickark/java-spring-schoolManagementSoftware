package com.jaw.common.customTag;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.framework.appCache.ApplicationCache;

public class ErrorDescriptionTag extends TagSupport {
	
	@Autowired
	ApplicationCache applicationCache;
	private String description;
	private String code;
	private String type;
	private String result;
	private String refNos = "";
	
	public String getRefNos() {
		return refNos;
	}

	public void setRefNos(String refNos) {
		this.refNos = refNos;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public ApplicationCache getApplicationCache() {
		return applicationCache;
	}
	
	public void setApplicationCache(ApplicationCache applicationCache) {
		this.applicationCache = applicationCache;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	@Override
	public int doStartTag() throws JspException {
		try {
			
			applicationCache = (ApplicationCache) pageContext
					.findAttribute(ApplicationConstant.APPLICATION_CACHE);
			Map<String, String> attribute2 = applicationCache.getErrorcode();
			String description = attribute2.get(code);
			String tag = null;
		
			
			if ((type == "success") && (code != "")) {
				tag = "<div class='row-fluid' style='margin-bottom: 10px;'> "
						+ "<span class='input-success' data-original-title=''Success input!'"
						+ "id='successmsg'> <i class='icon-ok'></i><strong> "
						+ code + " : " + description + refNos
						+ " </strong></span></div>";
			}
		
			if ((type == "error") && (code != "")) {
				tag = "<div class=class='row-fluid' style='margin-bottom: 10px;'>" +
						"<span class='input-success' data-original-title='Success input!'>" +
						" <strong class='error-msg' >"
						+ "<i	class='icon-remove'></i> "
						+ code
						+ " : "
						+ description + refNos 
						+"</strong></span></div>";
				
			}
			type = "";
			pageContext.setAttribute(result, tag, PageContext.REQUEST_SCOPE);
			
		}
		catch (Exception e) {
		}
		
		return SKIP_BODY;
	}
	
}