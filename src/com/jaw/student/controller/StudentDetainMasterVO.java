package com.jaw.student.controller;

import java.util.ArrayList;
import java.util.List;

public class StudentDetainMasterVO {
	
	
	private String pageNo = "10";
	public String getReasonForUpdate() {
		return reasonForUpdate;
	}
	@Override
	public String toString() {
		return "StudentDetainMasterVO [pageNo=" + pageNo + ", rowid=" + rowid
				+ ", stuSearchVO=" + stuSearchVO + ", stuDetList=" + stuDetList
				+ ", reasonForUpdate=" + reasonForUpdate + "]";
	}
	public void setReasonForUpdate(String reasonForUpdate) {
		this.reasonForUpdate = reasonForUpdate;
	}
	private int rowid;
	private StudentDetainSearchVO stuSearchVO = new StudentDetainSearchVO();
	private List<StudentDetainListVO> stuDetList = new ArrayList<StudentDetainListVO>();	
	private String reasonForUpdate = "";
	public int getRowid() {
		return rowid;
	}
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}	
	public StudentDetainSearchVO getStuSearchVO() {
		return stuSearchVO;
	}
	
	public void setStuSearchVO(StudentDetainSearchVO stuSearchVO) {
		this.stuSearchVO = stuSearchVO;
	}
	public List<StudentDetainListVO> getStuDetList() {
		return stuDetList;
	}
	public void setStuDetList(List<StudentDetainListVO> stuDetList) {
		this.stuDetList = stuDetList;
	} 
	
	

}
