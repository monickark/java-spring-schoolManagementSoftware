package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

//CourseClasses Pojo class
public class StuMrksRmksList implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(StuMrksRmksList.class);
	
	private String acTerm = "";
	private String studentGrpId = "";
	private String examId = "";
	private String studentAdmisNo = "";
	private String subType = "";
	private String crslId = "";
	private String minMark = "";
	private String maxMark = "";
	private String marksObtd = "";
	private String gradeObtd = "";
	private String subRemarks = "";
	private String attendance = "";
	private String subId = "";
	
	public String getSubId() {
		return subId;
	}
	
	public void setSubId(String subId) {
		this.subId = subId;
	}
	
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	
	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	
	public void setExamId(String examId) {
		this.examId = examId;
	}
	
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	
	public void setSubType(String subType) {
		this.subType = subType;
	}
	
	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}
	
	public void setMinMark(String minMark) {
		this.minMark = minMark;
	}
	
	public void setMaxMark(String maxMark) {
		this.maxMark = maxMark;
	}
	
	public void setMarksObtd(String marksObtd) {
		this.marksObtd = marksObtd;
	}
	
	public void setGradeObtd(String gradeObtd) {
		this.gradeObtd = gradeObtd;
	}
	
	public void setSubRemarks(String subRemarks) {
		this.subRemarks = subRemarks;
	}
	
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	
	public String getAcTerm() {
		return acTerm;
	}
	
	public String getStudentGrpId() {
		return studentGrpId;
	}
	
	public String getExamId() {
		return examId;
	}
	
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	
	public String getSubType() {
		return subType;
	}
	
	public String getCrslId() {
		return crslId;
	}
	
	public String getMinMark() {
		return minMark;
	}
	
	public String getMaxMark() {
		return maxMark;
	}
	
	public String getMarksObtd() {
		return marksObtd;
	}
	
	public String getGradeObtd() {
		return gradeObtd;
	}
	
	public String getSubRemarks() {
		return subRemarks;
	}
	
	public String getAttendance() {
		return attendance;
	}
	
}
