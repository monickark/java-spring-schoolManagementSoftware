package com.jaw.student.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jaw.student.admission.controller.StudentMasterVO;
import com.jaw.student.controller.StudentSearchVO;

public class StudentUpdateMasterVO {
	
	private StudentUpdatesSearch studentUpSearch = new StudentUpdatesSearch();
	private List<StudentUpdateListVO> stuList = new ArrayList<StudentUpdateListVO>();
	private String pageSize = "10";
	private String columnNameForDisTag = "";
	private String cocdtype="";
	private String dbColName = "";
	public Map<String, String> getSubListMap() {
		return subListMap;
	}
	public void setSubListMap(Map<String, String> subListMap) {
		this.subListMap = subListMap;
	}
	private String subjectType = "";
	private  Map<String,String> subListMap = new LinkedHashMap<String,String>();
	
	
	public StudentUpdatesSearch getStudentUpSearch() {
		return studentUpSearch;
	}
	@Override
	public String toString() {
		return "StudentUpdateMasterVO [studentUpSearch=" + studentUpSearch
				+ ", stuList=" + stuList + ", pageSize=" + pageSize
				+ ", columnNameForDisTag=" + columnNameForDisTag
				+ ", cocdtype=" + cocdtype + ", dbColName=" + dbColName
				+ ", checkTheColumnIsValid=" + subjectType
				+ ", subListMap=" + subListMap + "]";
	}
	public String getColumnNameForDisTag() {
		return columnNameForDisTag;
	}
	public String getDbColName() {
		return dbColName;
	}
	public void setDbColName(String dbColName) {
		this.dbColName = dbColName;
	}
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	public String getCocdtype() {
		return cocdtype;
	}
	public void setCocdtype(String cocdtype) {
		this.cocdtype = cocdtype;
	}
	public void setColumnNameForDisTag(String columnNameForDisTag) {
		this.columnNameForDisTag = columnNameForDisTag;
	}
	public String getPageSize() {
		return pageSize;
	}	
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}		
	
	public void setStudentUpSearch(StudentUpdatesSearch studentUpSearch) {
		this.studentUpSearch = studentUpSearch;
	}	
		
	public List<StudentUpdateListVO> getStuList() {
		return stuList;
	}
	public void setStuList(List<StudentUpdateListVO> stuList) {
		this.stuList = stuList;
	}
	

}
