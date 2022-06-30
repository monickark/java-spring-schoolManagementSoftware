package com.jaw.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class TransportMaster_MasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(TransportMaster_MasterVO.class);

	// Properties
	private String pageSize = "10";
	private int rowid;
	private List<TransportMasterVO> trnsMtrVOList = new ArrayList<TransportMasterVO>();
	private TransportMasterVO transMtrVo = new TransportMasterVO();

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

	public List<TransportMasterVO> getTrnsMtrVOList() {
		return trnsMtrVOList;
	}

	public void setTrnsMtrVOList(List<TransportMasterVO> trnsMtrVOList) {
		this.trnsMtrVOList = trnsMtrVOList;
	}

	public TransportMasterVO getTransMtrVo() {
		return transMtrVo;
	}

	public void setTransMtrVo(TransportMasterVO transMtrVo) {
		this.transMtrVo = transMtrVo;
	}

	

	

}
