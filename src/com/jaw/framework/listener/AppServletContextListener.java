package com.jaw.framework.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.dao.ClearSessionDao;
import com.jaw.framework.sessCache.dao.IClearSessionDao;

public class AppServletContextListener implements ServletContextListener {

	Logger logger = Logger.getLogger(AppServletContextListener.class);

	AppServletContextListenerHelper servletContextListenerHelper;

	IClearSessionDao clearSessionDao;
	@Autowired
	SessionCache sessionCache;

	@Override
	public void contextDestroyed(ServletContextEvent event) {

		logger.info("contextDestroyed");
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(event.getServletContext());
		clearSessionDao = (ClearSessionDao) applicationContext
				.getBean("clearSessionDao");
		int users = clearSessionDao.selectLogin();
		logger.debug("active users when application shut down :" + users);
		clearSessionDao.truncateLogin();

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("contextInitialized");
		System.out.println("Application started");
		// Load Application Dao from xml

		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(event.getServletContext());
		servletContextListenerHelper = (AppServletContextListenerHelper) applicationContext
				.getBean("applicationContextHelper");
		servletContextListenerHelper.contextInitialized(event
				.getServletContext());
	}
}
