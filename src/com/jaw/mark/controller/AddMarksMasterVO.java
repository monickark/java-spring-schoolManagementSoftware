package com.jaw.mark.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jaw.mark.dao.StudentListForAddMarks;

public class AddMarksMasterVO implements Serializable {

	// Logging
	Logger logger = Logger.getLogger(AddMarksMasterVO.class);

	// Properties	
	private AddMarksSearchVO addMarkSearch=new AddMarksSearchVO();
	private List<MarkSubjectLinkListForAddMarksVO> markSubListVo=new ArrayList<MarkSubjectLinkListForAddMarksVO>();
	private String pageSize="10";
	private String pageSize1="10";
	private List<StudentListForAddMarksVO> stuListForMark=new ArrayList<StudentListForAddMarksVO>();
	private String reasonForUpdate;
	public AddMarksSearchVO getAddMarkSearch() {
		return addMarkSearch;
	}

	public void setAddMarkSearch(AddMarksSearchVO addMarkSearch) {
		this.addMarkSearch = addMarkSearch;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public List<MarkSubjectLinkListForAddMarksVO> getMarkSubListVo() {
		return markSubListVo;
	}

	public void setMarkSubListVo(
			List<MarkSubjectLinkListForAddMarksVO> markSubListVo) {
		this.markSubListVo = markSubListVo;
	}

	public List<StudentListForAddMarksVO> getStuListForMark() {
		return stuListForMark;
	}

	public void setStuListForMark(List<StudentListForAddMarksVO> stuListForMark) {
		this.stuListForMark = stuListForMark;
	}

	public String getReasonForUpdate() {
		return reasonForUpdate;
	}

	public void setReasonForUpdate(String reasonForUpdate) {
		this.reasonForUpdate = reasonForUpdate;
	}

	public String getPageSize1() {
		return pageSize1;
	}

	public void setPageSize1(String pageSize1) {
		this.pageSize1 = pageSize1;
	}

	

}
