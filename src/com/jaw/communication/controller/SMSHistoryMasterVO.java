package com.jaw.communication.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class SMSHistoryMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(SMSHistoryMasterVO.class);

	// Properties	
	private String pageSize = "10";
    private List<SMSRequestListVO> smsRequestListVOs = new ArrayList<SMSRequestListVO>();
    private List<SMSDetailsVO> smsDetailsListVOs = new ArrayList<SMSDetailsVO>();
    
	private SMSRequestListVO smsReqstListVO = new SMSRequestListVO();
	private SMSHistorySearchVO smsHistorySearchVO=new SMSHistorySearchVO();
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public SMSHistorySearchVO getSmsHistorySearchVO() {
		return smsHistorySearchVO;
	}
	public void setSmsHistorySearchVO(SMSHistorySearchVO smsHistorySearchVO) {
		this.smsHistorySearchVO = smsHistorySearchVO;
	}
	public List<SMSRequestListVO> getSmsRequestListVOs() {
		return smsRequestListVOs;
	}
	public void setSmsRequestListVOs(List<SMSRequestListVO> smsRequestListVOs) {
		this.smsRequestListVOs = smsRequestListVOs;
	}
	public List<SMSDetailsVO> getSmsDetailsListVOs() {
		return smsDetailsListVOs;
	}
	public void setSmsDetailsListVOs(List<SMSDetailsVO> smsDetailsListVOs) {
		this.smsDetailsListVOs = smsDetailsListVOs;
	}
	public SMSRequestListVO getSmsReqstListVO() {
		return smsReqstListVO;
	}
	public void setSmsReqstListVO(SMSRequestListVO smsReqstListVO) {
		this.smsReqstListVO = smsReqstListVO;
	}
	
}
