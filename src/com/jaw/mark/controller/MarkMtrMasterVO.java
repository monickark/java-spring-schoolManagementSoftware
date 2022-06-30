package com.jaw.mark.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jaw.student.admission.controller.StudentMasterVO;

public class MarkMtrMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(MarkMtrMasterVO.class);

	// Properties
	private int rowid;
	private List<MarkMasterVO> markMasterVOs = new ArrayList<MarkMasterVO>();
	private MarkMasterVO markMasterVO = new MarkMasterVO();
	MarkMasterSearchVO markMasterSearchVo = new MarkMasterSearchVO();
	private String pageSize = "10";
	public MarkMasterSearchVO getMarkMasterSearchVo() {
		return markMasterSearchVo;
	}

	public void setMarkMasterSearchVo(MarkMasterSearchVO markMasterSearchVo) {
		this.markMasterSearchVo = markMasterSearchVo;
	}



	private StuMrksRmksVO stuMrksRmksVO = new StuMrksRmksVO();

	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

	public List<MarkMasterVO> getMarkMasterVOs() {
		return markMasterVOs;
	}

	public void setMarkMasterVOs(List<MarkMasterVO> markMasterVOs) {
		this.markMasterVOs = markMasterVOs;
	}

	public MarkMasterVO getMarkMasterVO() {
		return markMasterVO;
	}

	public void setMarkMasterVO(MarkMasterVO markMasterVO) {
		this.markMasterVO = markMasterVO;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public StuMrksRmksVO getStuMrksRmksVO() {
		return stuMrksRmksVO;
	}

	public void setStuMrksRmksVO(StuMrksRmksVO stuMrksRmksVO) {
		this.stuMrksRmksVO = stuMrksRmksVO;
	}

}
