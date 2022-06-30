package com.jaw.framework.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.MenuProfileOptionLinking;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public class MenuProfileFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession(false);
		SessionCache sessionCache = null;
		String url = request.getServletPath().substring(1);

		if ((url.contains(".htm"))) {
			if (session != null) {
				sessionCache = (SessionCache) session
						.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
				ApplicationCache applicationCache = (ApplicationCache) session
						.getServletContext().getAttribute(
								ApplicationConstant.APPLICATION_CACHE);
				if (sessionCache != null) {
					List<MenuProfileOptionLinking> sessionCacheMenuList = sessionCache
							.getOptionLinkings();
					List<MenuProfileOptionLinking> userBreadcrumbsList = sessionCache
							.getUserBreadCrumbs();
					if (sessionCacheMenuList != null) {
						System.out.println("Available umpl :"+sessionCacheMenuList.size());
						UserSessionDetails userSessionDetails = sessionCache
								.getUserSessionDetails();
						String key = userSessionDetails.getInstId() + "-"
								+ userSessionDetails.getBranchId() + "-"
								+ userSessionDetails.getUserMenuProfile() + "-"
								+ url;
						MenuProfileOptionLinking currentBreadcrumbObject = applicationCache
								.getMenuIdList().get(key);
						if (currentBreadcrumbObject != null) {
							System.out.println("current breadcrumb :"+currentBreadcrumbObject.toString());
							if (userBreadcrumbsList == null) {
								System.out.println("breadcrumbs in session :"+sessionCacheMenuList.size());
								userBreadcrumbsList = getObject(
										userBreadcrumbsList,
										currentBreadcrumbObject,
										sessionCacheMenuList);

							} else {
								MenuProfileOptionLinking lastBreadCrumb = userBreadcrumbsList
										.get(userBreadcrumbsList.size() - 1);
								
								System.out.println("Last breadcrumb :"+lastBreadCrumb.toString());
								if (lastBreadCrumb.getMenuNode() == currentBreadcrumbObject
										.getMenuNode()) {
									if (lastBreadCrumb.getMenuLevel() >= currentBreadcrumbObject
											.getMenuLevel()) {
										int level = lastBreadCrumb
												.getMenuLevel()
												- currentBreadcrumbObject
														.getMenuLevel();
										System.out.println("Last breadcrumb level :"+lastBreadCrumb
												.getMenuLevel()+" current breadcrumb level :"+currentBreadcrumbObject
														.getMenuLevel());
										for (int i = 0; i <= level; i++) {
											userBreadcrumbsList
													.remove(userBreadcrumbsList
															.size() - 1);
										}
									}
								} else {
									userBreadcrumbsList
											.removeAll(userBreadcrumbsList);
									userBreadcrumbsList = getObject(
											userBreadcrumbsList,
											currentBreadcrumbObject,
											sessionCacheMenuList);

								}
							}

							userBreadcrumbsList.add(currentBreadcrumbObject);

							sessionCache
									.setUserBreadCrumbs(userBreadcrumbsList);
						}
					}
				}

			}
		}

		chain.doFilter(req, res);

	}

	private List<MenuProfileOptionLinking> getObject(
			List<MenuProfileOptionLinking> userBreadcrumbsList,
			MenuProfileOptionLinking currentBreadcrumbObject,
			List<MenuProfileOptionLinking> sessionCacheMenuList) {
		userBreadcrumbsList = new ArrayList<MenuProfileOptionLinking>();
		int node = currentBreadcrumbObject.getMenuNode();
		for (MenuProfileOptionLinking linking2 : sessionCacheMenuList) {
			if ((linking2.getMenuNode() == node)
					&& (linking2.getMenuLevel() == 0)) {

				userBreadcrumbsList.add(linking2);

			}
		}
		return userBreadcrumbsList;

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("init");

	}

}
