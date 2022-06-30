package com.jaw.fee.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jaw.student.admission.controller.StudentMasterVO;
import com.jaw.student.controller.StudentSearchVO;

public class StudentFeeDiscountMasterVO {
	
	private StudentFeeDiscountSearch studentFeeDiscountSearch = new StudentFeeDiscountSearch();
	private List<StudentFeeDiscountListVO> stuList = new ArrayList<StudentFeeDiscountListVO>();
	private String pageSize = "10";
	private String columnNameForDisTag = "";
	private String cocdtype="";
	private String selectedOpt = "";
	
	public StudentFeeDiscountSearch getStudentFeeDiscountSearch() {
		return studentFeeDiscountSearch;
	}
	public void setStudentFeeDiscountSearch(
			StudentFeeDiscountSearch studentFeeDiscountSearch) {
		this.studentFeeDiscountSearch = studentFeeDiscountSearch;
	}
	public List<StudentFeeDiscountListVO> getStuList() {
		return stuList;
	}
	public void setStuList(List<StudentFeeDiscountListVO> stuList) {
		this.stuList = stuList;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getColumnNameForDisTag() {
		return columnNameForDisTag;
	}
	public void setColumnNameForDisTag(String columnNameForDisTag) {
		this.columnNameForDisTag = columnNameForDisTag;
	}
	public String getCocdtype() {
		return cocdtype;
	}
	public void setCocdtype(String cocdtype) {
		this.cocdtype = cocdtype;
	}
	public String getSelectedOpt() {
		return selectedOpt;
	}
	public void setSelectedOpt(String selectedOpt) {
		this.selectedOpt = selectedOpt;
	}
	
}
