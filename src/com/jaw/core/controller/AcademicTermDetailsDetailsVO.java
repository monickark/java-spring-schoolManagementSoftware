package com.jaw.core.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;



public class AcademicTermDetailsDetailsVO implements Serializable{	 
	// Logging
	 Logger logger = Logger.getLogger(AcademicTermDetailsDetailsVO.class);

	// Properties		
	private String acYear;
	private String acTerm;
	private String termStartDate = "";
	private String termEndDate = "";
	private String weeklyHoliday ;
	private String acTermSts;
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
		return "AcademicTermDetailsVO [acYear=" + acYear + ", acTerm=" + acTerm
				+ ", termStartDate=" + termStartDate + ", termEndDate="
				+ termEndDate + ", weeklyHoliday=" + weeklyHoliday
				+ ", acTermSts=" + acTermSts + "]";
	}
	
}
