package com.jaw.framework.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.framework.sessCache.SessionCache;

public class SessionFilter implements Filter {
	private final Logger logger = Logger.getLogger(SessionFilter.class);
	private ArrayList<String> urlList;
	private List<String> allowUrl;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		SessionCache sessionCache = null;
		String url = request.getServletPath();
		boolean ommitRequest = false;

		if (urlList.contains(url)) {
			ommitRequest = true;
		}

		if ((!ommitRequest) && (url.contains(".htm"))) {
			logger.debug("Url :" + request.getRequestURL());
			HttpSession session = request.getSession(false);

			if (null == session) {
				logger.debug("Session time out url :" + url);
				response.sendRedirect("sessionTimeout.htm");
				return;
			} else {
				sessionCache = (SessionCache) session
						.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
				if ((sessionCache != null)) {

					if ((sessionCache.getAllowedUrl() != null)
							&& ((sessionCache.getUserSessionDetails() != null))) {

						MDC.put("instId", sessionCache.getUserSessionDetails()
								.getInstId());
						MDC.put("branchId", sessionCache
								.getUserSessionDetails().getBranchId());
						MDC.put("userId", sessionCache.getUserSessionDetails()
								.getUserId());
						MDC.put("sessionId", sessionCache
								.getUserSessionDetails().getSessionId());
						allowUrl = sessionCache.getAllowedUrl();

						if (!allowUrl.contains(url.substring(1))) {
							logger.info("Invalid url :" + url);
							response.sendRedirect("invalidUrl.htm");
						}
					} else {
						if (!url.contains("forrcechangePassword.htm")) {
							logger.debug("Session time out url :" + url);
							response.sendRedirect("sessionTimeout.htm");
							return;
						}

					}
				} else {
					response.sendRedirect("login.htm");
				}
			}

		}

		chain.doFilter(req, res);

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String urls = config.getInitParameter("avoid-urls");
		StringTokenizer token = new StringTokenizer(urls, ",");

		urlList = new ArrayList<String>();

		while (token.hasMoreTokens()) {
			urlList.add(token.nextToken());

		}
	}

}
