package com.jaw.student.controller;

public class StuTranMasterVO {
	
	private StuTranSearchVO stuSearch = new StuTranSearchVO();
	
	public StuTranSearchVO getStuSearch() {
		return stuSearch;
	}

	public void setStuSearch(StuTranSearchVO stuSearch) {
		this.stuSearch = stuSearch;
	}

	public StuTranVO getStuTranVO() {
		return stuTranVO;
	}

	@Override
	public String toString() {
		return "StuTranMasterVO [stuSearch=" + stuSearch + ", stuTranVO="
				+ stuTranVO + "]";
	}

	public void setStuTranVO(StuTranVO stuTranVO) {
		this.stuTranVO = stuTranVO;
	}

	private StuTranVO stuTranVO = new StuTranVO();
	

}
