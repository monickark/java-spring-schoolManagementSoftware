package com.jaw.framework.listener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.util.MenuProfileUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.ApplicationCacheCommonCodeDao;
import com.jaw.framework.appCache.dao.ApplicationCacheErrorCodeDao;
import com.jaw.framework.appCache.dao.ApplicationCacheProfileOptionLinking;
import com.jaw.framework.appCache.dao.ApplicationCachePropertyMaintenanceDao;
import com.jaw.framework.appCache.dao.ApplicationCacheTableMaintenanceDao;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.appCache.dao.IApplicationCacheAcademicTermDao;
import com.jaw.framework.appCache.dao.IApplicationCacheCommonCodeDao;
import com.jaw.framework.appCache.dao.IApplicationCacheErrorCodeDao;
import com.jaw.framework.appCache.dao.IApplicationCacheProfileOptionLinking;
import com.jaw.framework.appCache.dao.IApplicationCachePropertyMaintenanceDao;
import com.jaw.framework.appCache.dao.IApplicationCacheSMSPropertyDao;
import com.jaw.framework.appCache.dao.IApplicationCacheStandardCombinationListDao;
import com.jaw.framework.appCache.dao.IApplicationCacheStandardSectionDao;
import com.jaw.framework.appCache.dao.IApplicationCacheTableMaintenanceDao;
import com.jaw.framework.appCache.dao.MenuProfileOptionLinking;
import com.jaw.framework.appCache.dao.ApplicationCacheSMSPropertyDao;
@Component
public class AppServletContextListenerHelper {
	
	Logger logger = Logger.getLogger(AppServletContextListenerHelper.class);
	
	IApplicationCacheCommonCodeDao commonCodeDao;
	IApplicationCachePropertyMaintenanceDao propertyMaintenanceDao;
	IApplicationCacheProfileOptionLinking profileOptionLinking;
	MenuProfileUtil menuProfileUtil;
	IApplicationCacheErrorCodeDao applicationCacheErrorCodeDao;
	IApplicationCacheTableMaintenanceDao applicationCacheTableMaintenanceDao;
	ApplicationCache applicationCache;
	IApplicationCacheStandardCombinationListDao standardCombinationListDao;
	IApplicationCacheStandardSectionDao standardSectionDao;
	IApplicationCacheAcademicTermDao academicTermDao;
	IApplicationCacheSMSPropertyDao smsPropertyDao;
	public void contextInitialized(ServletContext context) {
		logger.debug("contextInitialized");
		
		// Load Application Dao from xml
		
		logger.debug("Loading Application Dao beans from applicationContext.xml");
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		
		applicationCache = (ApplicationCache) applicationContext
				.getBean("applicationCache");
		
		// Load CommonCode into Application cache
		try {
			logger.debug("Get commoncode  for application cache");
			
			commonCodeDao = (ApplicationCacheCommonCodeDao) applicationContext
					.getBean("commonCodeDao");
			applicationCache
					.setCommoncode(commonCodeDao.getAllCommonCodeList());
			
			// Load Common code map into application cache
			
			Map<String, String> commonCodeMap = new HashMap<String, String>();
			for (CommonCode commonCode : commonCodeDao.getAllCommonCodeList()) {
				commonCodeMap.put(
						commonCode.getInstId() + "," + commonCode.getBranchId() + ","
								+ commonCode.getCodeType() + ","
								+ commonCode.getCommonCode(),
						commonCode.getCodeDescription());
			}
			applicationCache.setCommoncodeMap(commonCodeMap);
			
			// Load error code into ApplicationCache
			
			applicationCacheErrorCodeDao = (ApplicationCacheErrorCodeDao) applicationContext
					.getBean("errorCodeDao");
			
			logger.debug("Get messages from application dao for application cache");
			
			applicationCache.setErrorcode(applicationCacheErrorCodeDao
					.getAllErrorCode());
			
			// Get constant property from PropertyMaintenanceDao for application
			// cache
			
			propertyMaintenanceDao = (ApplicationCachePropertyMaintenanceDao) applicationContext
					.getBean("propertyMaintenanceDao");
			smsPropertyDao=(ApplicationCacheSMSPropertyDao) applicationContext.getBean("smsMaintenanceDao");
			logger.debug("Get constant property  from PropertyMaintenanceDao for application cache");
			
			applicationCache.setPropertyManagement(propertyMaintenanceDao
					.getPrpmCodes());
			applicationCache.setSmsProperty(smsPropertyDao.getPrpmCodes());
			// Get menus from Menu for application
			// cache
			
			profileOptionLinking = (ApplicationCacheProfileOptionLinking) applicationContext
					.getBean("menuProfile");
			List<MenuProfileOptionLinking> umplList = profileOptionLinking
					.getMenuProfileOption();
			List<MenuProfileOptionLinking> menuProfileList = profileOptionLinking
					.getMenuProfile();
			menuProfileUtil = (MenuProfileUtil) applicationContext
					.getBean("umplUtil");
			applicationCache.setMenuProfileList(menuProfileUtil
					.getMenuProfileByMenuId(umplList, menuProfileList));
			applicationCache.setMenuIdList(menuProfileUtil
					.getMenuIdList(umplList));
			// TBPM application cache loading
			applicationCacheTableMaintenanceDao = (ApplicationCacheTableMaintenanceDao) applicationContext
					.getBean("tableMaintenanceDao");
			
			applicationCache
					.setTableMaintenances(applicationCacheTableMaintenanceDao
							.getTableMaintenanceData());
			academicTermDao = (IApplicationCacheAcademicTermDao) applicationContext
					.getBean("academicTerm");
			
			applicationCache.setAcademicTerms(academicTermDao.getAcademicTermData());
			// Load Property file into application cache
			System.out.println("application cache academic term list :"+applicationCache.getAcademicTerms().size());
			Properties properties = new Properties();
			properties.load(getClass()
					.getResourceAsStream("/sample.properties"));
			
			context.setAttribute(ApplicationConstant.APPLICATION_CACHE,
					applicationCache);
		}
		catch (NoDataFoundException exception) {
			exception.printStackTrace();
			logger.debug("Error occured while loading applicationg cache , Application shut down...");
			System.exit(0);
		}
		catch (IOException e) {
			logger.debug("Error occured while loading property files");
		}
		
	}
}
