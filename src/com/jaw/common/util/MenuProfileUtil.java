package com.jaw.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.MenuProfileOptionLinking;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.login.service.RequestService;

@Component
public class MenuProfileUtil {
	Logger logger = Logger.getLogger(RequestService.class);
	
	public HashMap<String, ArrayList<MenuProfileOptionLinking>> getMenuProfileByMenuId(
			List<MenuProfileOptionLinking> umplList,
			List<MenuProfileOptionLinking> menuProfileList) {
		
		HashMap<String, ArrayList<MenuProfileOptionLinking>> resultMap = new HashMap<String, ArrayList<MenuProfileOptionLinking>>();
		for (MenuProfileOptionLinking menuProfileObject : menuProfileList) {
			ArrayList<MenuProfileOptionLinking> singleMenuProfileList = new ArrayList<MenuProfileOptionLinking>();
			for (MenuProfileOptionLinking umplObject : umplList) {
				if ((menuProfileObject.getInstId().equals(umplObject
						.getInstId()))
						&& (menuProfileObject.getBranchId().equals(umplObject
								.getBranchId()))
						&& (menuProfileObject.getMenuProfile()
								.equals(umplObject.getMenuProfile()))) {
					singleMenuProfileList.add(umplObject);
				}
				String key = menuProfileObject.getInstId() + "-"
						+ menuProfileObject.getBranchId() + "-"
						+ menuProfileObject.getMenuProfile();
				
				resultMap.put(key, singleMenuProfileList);
			}
		}
		
		return resultMap;
		
	}
	
	public HashMap<String, MenuProfileOptionLinking> getMenuIdList(
			List<MenuProfileOptionLinking> umplList) {
		HashMap<String, MenuProfileOptionLinking> resultMap = new HashMap<String, MenuProfileOptionLinking>();
		for (MenuProfileOptionLinking umplObject : umplList) {
			String key = umplObject.getInstId() + "-"
					+ umplObject.getBranchId() + "-"
					+ umplObject.getMenuProfile() + "-"
					+ umplObject.getMenuUrl();
			resultMap.put(key, umplObject);
		}
		return resultMap;
		
	}
	
	public String getMenuOption(UserSessionDetails userSessionDetails,
			String url, ApplicationCache applicationCache) {
		// Find menu option to know which user is login because the request list
		// will be varying for super admin and branch admin
		
		String key = userSessionDetails.getInstId() + "-"
				+ userSessionDetails.getBranchId() + "-"
				+ userSessionDetails.getUserMenuProfile() + "-" + url;
		System.out.println("Key generated to get menuOption:" + key);
		MenuProfileOptionLinking objectForMenuOption = applicationCache
				.getMenuIdList().get(key);
		
		logger.debug("Menu option object get from url :" + objectForMenuOption
				+ " url triggered:" + objectForMenuOption.getMenuUrl()
				+ " menu option :" + objectForMenuOption.getMenuOption());
		
		return objectForMenuOption.getMenuOption();
	}
	
}
