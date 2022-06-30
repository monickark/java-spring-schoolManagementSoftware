package com.jaw.admin.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;



public class SMSConfigurationMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(SMSConfigurationMasterVO.class);

	// Properties	
	private int rowid;
	private String pageSize = "10";
	private List<SMSConfigurationVO> smsConfigListVO = new ArrayList<SMSConfigurationVO>();
	private SMSConfigurationVO smsConfigVO = new SMSConfigurationVO();
	//
	public int getRowid() {
		return rowid;
	}
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public List<SMSConfigurationVO> getSmsConfigListVO() {
		return smsConfigListVO;
	}
	public void setSmsConfigListVO(List<SMSConfigurationVO> smsConfigListVO) {
		this.smsConfigListVO = smsConfigListVO;
	}
	public SMSConfigurationVO getSmsConfigVO() {
		return smsConfigVO;
	}
	public void setSmsConfigVO(SMSConfigurationVO smsConfigVO) {
		this.smsConfigVO = smsConfigVO;
	}
}
