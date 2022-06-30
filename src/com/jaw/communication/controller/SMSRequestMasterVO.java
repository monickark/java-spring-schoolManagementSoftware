package com.jaw.communication.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class SMSRequestMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(SMSRequestMasterVO.class);

	// Properties		
	private String pageSize = "10";
	//private List<AlertVO> alertVOList = new ArrayList<AlertVO>();
	private SMSRequestVO smsRestVo = new SMSRequestVO();
//	private AlertSearchVo alertSearchVo=new AlertSearchVo();
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public SMSRequestVO getSmsRestVo() {
		return smsRestVo;
	}
	public void setSmsRestVo(SMSRequestVO smsRestVo) {
		this.smsRestVo = smsRestVo;
	}

}
