package com.jaw.analytics.controller;

import java.io.Serializable;
import org.apache.log4j.Logger;

import com.jaw.analytics.controller.MarkAnalyticSearchVO;

public class MarkAnalyticMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(MarkAnalyticMasterVO.class);
	
	private MarkAnalyticSearchVO markAnalyticSearchVO = new MarkAnalyticSearchVO();

	public MarkAnalyticSearchVO getMarkAnalyticSearchVO() {
		return markAnalyticSearchVO;
	}

	public void setMarkAnalyticSearchVO(MarkAnalyticSearchVO markAnalyticSearchVO) {
		this.markAnalyticSearchVO = markAnalyticSearchVO;
	}

}
