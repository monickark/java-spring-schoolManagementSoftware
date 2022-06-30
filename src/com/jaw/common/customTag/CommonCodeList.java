package com.jaw.common.customTag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.sessCache.SessionCache;

public class CommonCodeList extends TagSupport {

	private final Logger logger = Logger.getLogger(CommonCodeList.class);
	@Autowired
	ApplicationCache applicationCache;
	private List<CommonCode> commonCodeList;
	CommonCodeUtil commonCodeUtil = new CommonCodeUtil();
	private String output;
	private String codeType;
	private String branchId;

	public List<CommonCode> getCommonCodeList() {
		return commonCodeList;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public void setCommonCode(List<CommonCode> commonCode) {
		commonCodeList = commonCode;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String type) {
		codeType = type;
	}

	@Override
	public int doStartTag() throws JspException {
		try {

			commonCodeList = new ArrayList<CommonCode>();
			applicationCache = (ApplicationCache) pageContext
					.findAttribute(ApplicationConstant.APPLICATION_CACHE);
			SessionCache sessionCache = (SessionCache) pageContext
					.findAttribute(ApplicationConstant.SESSION_CACHE_KEY);

			String branch = null;

			if ((getBranchId() == null) || (getBranchId().equals(""))) {
				logger.debug("User didnt passed branch id taken from session cache");
				branch = sessionCache.getUserSessionDetails().getBranchId();
			} else {
				logger.debug("User passed branch id :" + getBranchId());
				branch = getBranchId();

			}
			logger.debug("Branch id passed :" + branch);
			commonCodeList = commonCodeUtil.getCommonCodeListByType(
					applicationCache, codeType, sessionCache
							.getUserSessionDetails().getInstId(), branch);

			if (commonCodeList.size() != 0) {
				pageContext.setAttribute(output, commonCodeList,
						PageContext.REQUEST_SCOPE);

			} else {
				logger.debug("No data found in commoncode for code type :"
						+ codeType + " branch :"
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