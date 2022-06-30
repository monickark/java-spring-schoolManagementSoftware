package com.jaw.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.CommonCode;

@Component
public class CommonCodeUtil {
	static Logger logger = Logger.getLogger(CommonCodeUtil.class);
	
	public static String getDescriptionByTypeAndCode(
			ApplicationCache applicationCache, String type, String code, String instId,
			String branchId) {
		System.out.println("Cocd Util values :Application cache :" + applicationCache
				+ "  instid :" + instId + "  branchId :" + branchId
				+ "  type :" + type + " code:" + code);
		return applicationCache.getCommoncodeMap().get(
				instId + "," + branchId + "," + type + "," + code);
	}
	
	public static List<CommonCode> getCommonCodeListByType(
			ApplicationCache applicationCache, String key, String instId,
			String branchId) throws CommonCodeNotFoundException {
		System.out.println("Cocd Util values :Application cache :" + applicationCache
				+ "  instid :" + instId + "  branchId :" + branchId
				+ "  type :" + key);
		List<CommonCode> newList = new ArrayList<CommonCode>();
		for (CommonCode cc : applicationCache.getCommoncode()) {
			if ((cc.getCodeType().equals(key))
					&& (cc.getInstId().equals(instId))
					&& (cc.getBranchId().equals(branchId))) {
				newList.add(cc);
			}
		}
		
		if (newList.size() == 0) {
			logger.error("No common code list returns");
			throw new CommonCodeNotFoundException();
		}
		return newList;
	}
	
	public static String getCodeByDescription(String cocdType,
			ApplicationCache applicationCache, String description,
			String instId, String branchId) {
		// PBM List<CommonCode> codeList = new ArrayList<CommonCode>();
		String code = null;
		for (CommonCode cc : applicationCache.getCommoncode()) {
			if (cc.getCodeType().equals(cocdType)
					&& cc.getCodeDescription().equals(description)
					&& (cc.getInstId().equals(instId))
					&& (cc.getBranchId().equals(branchId))) {
				code = cc.getCommonCode();
			}
			
		}
		if (code == null) {
			logger.error("No code found for type:" + cocdType
					+ "  description :" + description);
		}
		return code;
		
	}
	
	public static String getDescriptionByCode(
			ApplicationCache applicationCache, String code) {
		String desc = null;
		// return applicationCache.getCommoncodeMap().get(type+","+code);
		
		for (CommonCode cc : applicationCache.getCommoncode()) {
			
			if (cc.getCommonCode().equals(code)) {
				desc = cc.getCodeDescription();
			}
			
		}
		if (desc == null) {
			logger.error("No description found for code:" + code);
		}
		return desc;
	}
	
}
