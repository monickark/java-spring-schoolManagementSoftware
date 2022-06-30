package com.jaw.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

//AcademicTermDetails Pojo class
public class AcademicTermListVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(AcademicTermListVO.class);
	
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acYear;
	private String acTerm;
	private String termStartDate = "";
	private String termEndDate = "";
	private String weeklyHoliday;
	private String acTermSts;
	private String pageNo = "10";
	private int rowid;
	private List<AcademicTermDetailsVO> academicTermDetailsVOList = new ArrayList<AcademicTermDetailsVO>();
	private AcademicTermDetailsVO academicTermDetailsVO = new AcademicTermDetailsVO();
	
	public List<AcademicTermDetailsVO> getAcademicTermDetailsVOList() {
		return academicTermDetailsVOList;
	}
	
	public void setAcademicTermDetailsVOList(List<AcademicTermDetailsVO> academicTermDetailsVOList) {
		this.academicTermDetailsVOList = academicTermDetailsVOList;
	}
	
	public AcademicTermDetailsVO getAcademicTermDetailsVO() {
		return academicTermDetailsVO;
	}
	
	public void setAcademicTermDetailsVO(AcademicTermDetailsVO academicTermDetailsVO) {
		this.academicTermDetailsVO = academicTermDetailsVO;
	}
	
	public String getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getRowid() {
		return rowid;
	}
	
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	
	public Integer getDbTs() {
		return dbTs;
	}
	
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}
	
	public String getInstId() {
		return instId;
	}
	
	public void setInstId(String instId) {
		this.instId = instId;
	}
	
	public String getBranchId() {
		return branchId;
	}
	
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	public String getAcYear() {
		return acYear;
	}
	
	public void setAcYear(String acYear) {
		this.acYear = acYear;
	}
	
	public String getAcTerm() {
		return acTerm;
	}
	
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	
	public String getTermStartDate() {
		return termStartDate;
	}
	
	public void setTermStartDate(String termStartDate) {
		this.termStartDate = termStartDate;
	}
	
	public String getTermEndDate() {
		return termEndDate;
	}
	
	public void setTermEndDate(String termEndDate) {
		this.termEndDate = termEndDate;
	}
	
	public String getWeeklyHoliday() {
		return weeklyHoliday;
	}
	
	public void setWeeklyHoliday(String weeklyHoliday) {
		this.weeklyHoliday = weeklyHoliday;
	}
	
	public String getAcTermSts() {
		return acTermSts;
	}
	
	public void setAcTermSts(String acTermSts) {
		this.acTermSts = acTermSts;
	}
	
	@Override
	public String toString() {
		return "AcademicTermList [getDbTs()=" + getDbTs() + ", getInstId()=" + getInstId()
				+ ", getBranchId()=" + getBranchId() + ", getAcYear()=" + getAcYear()
				+ ", getAcTerm()=" + getAcTerm() + ", getTermStartDate()=" + getTermStartDate()
				+ ", getTermEndDate()=" + getTermEndDate() + ", getWeeklyHoliday()="
				+ getWeeklyHoliday() + ", getAcTermSts()=" + getAcTermSts() + "]";
	}
	
}
