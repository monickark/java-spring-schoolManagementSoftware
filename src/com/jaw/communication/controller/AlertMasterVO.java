package com.jaw.communication.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class AlertMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(AlertMasterVO.class);

	// Properties		
	private String pageSize = "10";
	private List<AlertVO> alertVOList = new ArrayList<AlertVO>();
	private AlertVO alertVO = new AlertVO();
	private AlertSearchVo alertSearchVo=new AlertSearchVo();
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public List<AlertVO> getAlertVOList() {
		return alertVOList;
	}
	public void setAlertVOList(List<AlertVO> alertVOList) {
		this.alertVOList = alertVOList;
	}
	public AlertVO getAlertVO() {
		return alertVO;
	}
	public void setAlertVO(AlertVO alertVO) {
		this.alertVO = alertVO;
	}
	public AlertSearchVo getAlertSearchVo() {
		return alertSearchVo;
	}
	public void setAlertSearchVo(AlertSearchVo alertSearchVo) {
		this.alertSearchVo = alertSearchVo;
	}

}
