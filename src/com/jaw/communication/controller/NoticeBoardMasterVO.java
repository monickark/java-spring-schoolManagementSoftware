package com.jaw.communication.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;



public class NoticeBoardMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(NoticeBoardMasterVO.class);

	// Properties		
	private String pageSize = "10";
	private List<NoticeBoardVO> noticeBoardVOList = new ArrayList<NoticeBoardVO>();
	private NoticeBoardVO noticeBoardVO = new NoticeBoardVO();
	private NoticeBoardSearchVO noticeBoardSearchVO=new NoticeBoardSearchVO();
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public List<NoticeBoardVO> getNoticeBoardVOList() {
		return noticeBoardVOList;
	}
	public void setNoticeBoardVOList(List<NoticeBoardVO> noticeBoardVOList) {
		this.noticeBoardVOList = noticeBoardVOList;
	}
	public NoticeBoardVO getNoticeBoardVO() {
		return noticeBoardVO;
	}
	public void setNoticeBoardVO(NoticeBoardVO noticeBoardVO) {
		this.noticeBoardVO = noticeBoardVO;
	}
	public NoticeBoardSearchVO getNoticeBoardSearchVO() {
		return noticeBoardSearchVO;
	}
	public void setNoticeBoardSearchVO(NoticeBoardSearchVO noticeBoardSearchVO) {
		this.noticeBoardSearchVO = noticeBoardSearchVO;
	}

}
