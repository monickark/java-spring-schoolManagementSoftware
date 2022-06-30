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
import com.jaw.framework.sessCache.SessionCache;

public class AcademicTermStatus extends TagSupport {

	private final Logger logger = Logger.getLogger(AcademicTermStatus.class);
	@Autowired
	ApplicationCache applicationCache;
	private List<AcademicTerm> academicTerms;
	private String status;
	private String output;
	private String acTerm;
	private String branchId;
	AcademicTermUtil academicTermUtil = new AcademicTermUtil();
	public ApplicationCache getApplicationCache() {
		return applicationCache;
	}

	public void setApplicationCache(ApplicationCache applicationCache) {
		this.applicationCache = applicationCache;
	}

	public List<AcademicTerm> getAcademicTerms() {
		return academicTerms;
	}

	public void setAcademicTerms(List<AcademicTerm> academicTerms) {
		this.academicTerms = academicTerms;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAcTerm() {
		return acTerm;
	}

	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
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
				logger.debug("User didnt passed branch id taken from session cache");
				branch = sessionCache.getUserSessionDetails().getBranchId();
			} else {
				logger.debug("User passed branch id :" + getBranchId());
				branch = getBranchId();

			}
			logger.debug("Branch id passed :" + branch);
			academicTerms = academicTermUtil.getAcademicTermList(applicationCache,sessionCache,"");
			
			if (academicTerms.size() != 0) {
				for(AcademicTerm academicTerm:academicTerms) {
					if(academicTerm.getAcTerm().equals(acTerm)) {
						status=academicTerm.getAcTermStatus();
					}
				}
				pageContext.setAttribute(output, status,
						PageContext.REQUEST_SCOPE);

			} else {
				logger.debug("No academic term found :"
						+  " branch :"
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