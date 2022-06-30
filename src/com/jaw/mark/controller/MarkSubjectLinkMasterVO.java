package com.jaw.mark.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

//AcademicTermDetails Pojo class
public class MarkSubjectLinkMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(MarkSubjectLinkMasterVO.class);
	
	private List<MarkSubjectLinkListVO> markSubjectLinkListVOs = new ArrayList<MarkSubjectLinkListVO>();
	private MarkSubjectLinkVO markSubjectLinkVO = new MarkSubjectLinkVO();
	private MarkSubjectLinkSearchVO markSubjectLinkSearchVO = new MarkSubjectLinkSearchVO();
	MarkSubjectLinkListVO markSubjectLinkListVO = new MarkSubjectLinkListVO();
	private String pageSize = "10";
	
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public MarkSubjectLinkListVO getMarkSubjectLinkListVO() {
		return markSubjectLinkListVO;
	}
	
	public void setMarkSubjectLinkListVO(
			MarkSubjectLinkListVO markSubjectLinkListVO) {
		this.markSubjectLinkListVO = markSubjectLinkListVO;
	}
	
	public List<MarkSubjectLinkListVO> getMarkSubjectLinkListVOs() {
		return markSubjectLinkListVOs;
	}
	
	public void setMarkSubjectLinkListVOs(
			List<MarkSubjectLinkListVO> markSubjectLinkListVOs) {
		this.markSubjectLinkListVOs = markSubjectLinkListVOs;
	}
	
	public MarkSubjectLinkVO getMarkSubjectLinkVO() {
		return markSubjectLinkVO;
	}
	
	public void setMarkSubjectLinkVO(MarkSubjectLinkVO markSubjectLinkVO) {
		this.markSubjectLinkVO = markSubjectLinkVO;
	}
	
	public MarkSubjectLinkSearchVO getMarkSubjectLinkSearchVO() {
		return markSubjectLinkSearchVO;
	}
	
	public void setMarkSubjectLinkSearchVO(
			MarkSubjectLinkSearchVO markSubjectLinkSearchVO) {
		this.markSubjectLinkSearchVO = markSubjectLinkSearchVO;
	}
	
}
