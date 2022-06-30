package com.jaw.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.AcademicTerm;
import com.jaw.framework.sessCache.SessionCache;

@Component
public class AcademicTermUtil {
	static Logger logger = Logger.getLogger(AcademicTermUtil.class);

	public static List<AcademicTerm> getAcademicTermList(
			ApplicationCache applicationCache, SessionCache sessionCache,String isIncludeClosed)
			throws CommonCodeNotFoundException {
		System.out.println("Cocd Util values :Application cache :"
				+ applicationCache + "  instid :" + sessionCache.getUserSessionDetails().getInstId() + "  branchId :"
				+ sessionCache.getUserSessionDetails().getBranchId());
		List<AcademicTerm> newList = new ArrayList<AcademicTerm>();
		for (AcademicTerm cc : applicationCache.getAcademicTerms()) {
			System.out.println("checked value=:"+cc.toString());
			if ((cc.getInstId().equals(sessionCache.getUserSessionDetails().getInstId()))
					&& (cc.getBranchId().equals(sessionCache.getUserSessionDetails().getBranchId()))) {
				System.out.println("added value=:"+cc.toString());
				if((sessionCache.getUserSessionDetails().getUserMenuProfile().equals("TSF"))||(isIncludeClosed.equals("false"))) {
					if(!cc.getAcTermStatus().equals("C")) {
						newList.add(cc);
					}
				} else {

					newList.add(cc);
				}
			}
		}

		if (newList.size() == 0) {
			logger.error("No common code list returns");
			throw new CommonCodeNotFoundException();
		}
		return newList;
	}

}
