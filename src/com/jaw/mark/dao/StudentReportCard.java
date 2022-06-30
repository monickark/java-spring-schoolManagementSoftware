package com.jaw.mark.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

//CourseClasses Pojo class
public class StudentReportCard implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(StudentReportCard.class);
	
	private String acTerm = "";
	private String studentGrpId = "";
	private String examId = "";
	private String studentAdmisNo = "";
	private String subType = "";
	private String crslId = "";
	private float average;
	private int maxMarks;
	private int minMarks;
	private int maxSum;
	private int minSum;
	private int maxMarkObt;
	private String attendance = "";
	private String subId = "";
	private String examOrder="";
	private String subName="";
	private String shortCode="";
	private int stuMaxMark;
	private int stuMinMark;
	private int stuMarkObt;
	private String stuResult;
	private float avgClassMark;
	private int maxClassMark;
	private int rank;
	private int classRank;
	private int classStrength;
	

	
	
	public String getShortCode() {
		return shortCode;
	}



	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}



	public int getClassRank() {
		return classRank;
	}



	public void setClassRank(int classRank) {
		this.classRank = classRank;
	}



	public int getClassStrength() {
		return classStrength;
	}



	public void setClassStrength(int classStrength) {
		this.classStrength = classStrength;
	}



	public int getMinSum() {
		return minSum;
	}



	public void setMinSum(int minSum) {
		this.minSum = minSum;
	}



	public Logger getLogger() {
		return logger;
	}



	public void setLogger(Logger logger) {
		this.logger = logger;
	}



	public String getAcTerm() {
		return acTerm;
	}



	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}



	public String getStudentGrpId() {
		return studentGrpId;
	}



	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}



	public String getExamId() {
		return examId;
	}



	public void setExamId(String examId) {
		this.examId = examId;
	}



	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}



	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}



	public String getSubType() {
		return subType;
	}



	public void setSubType(String subType) {
		this.subType = subType;
	}



	public String getCrslId() {
		return crslId;
	}



	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}



	public float getAverage() {
		return average;
	}



	public void setAverage(float average) {
		this.average = average;
	}



	public int getMaxMarks() {
		return maxMarks;
	}



	public void setMaxMarks(int maxMarks) {
		this.maxMarks = maxMarks;
	}



	public int getMinMarks() {
		return minMarks;
	}



	public void setMinMarks(int minMarks) {
		this.minMarks = minMarks;
	}



	public int getMaxSum() {
		return maxSum;
	}



	public void setMaxSum(int maxSum) {
		this.maxSum = maxSum;
	}



	public int getMaxMarkObt() {
		return maxMarkObt;
	}



	public void setMaxMarkObt(int maxMarkObt) {
		this.maxMarkObt = maxMarkObt;
	}



	public String getAttendance() {
		return attendance;
	}



	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}



	public String getSubId() {
		return subId;
	}



	public void setSubId(String subId) {
		this.subId = subId;
	}



	public String getExamOrder() {
		return examOrder;
	}



	public void setExamOrder(String examOrder) {
		this.examOrder = examOrder;
	}



	public String getSubName() {
		return subName;
	}



	public void setSubName(String subName) {
		this.subName = subName;
	}



	public int getStuMaxMark() {
		return stuMaxMark;
	}



	public void setStuMaxMark(int stuMaxMark) {
		this.stuMaxMark = stuMaxMark;
	}



	public int getStuMinMark() {
		return stuMinMark;
	}



	public void setStuMinMark(int stuMinMark) {
		this.stuMinMark = stuMinMark;
	}



	public int getStuMarkObt() {
		return stuMarkObt;
	}



	public void setStuMarkObt(int stuMarkObt) {
		this.stuMarkObt = stuMarkObt;
	}



	public String getStuResult() {
		return stuResult;
	}



	public void setStuResult(String stuResult) {
		this.stuResult = stuResult;
	}



	public float getAvgClassMark() {
		return avgClassMark;
	}



	public void setAvgClassMark(float avgClassMark) {
		this.avgClassMark = avgClassMark;
	}



	public int getMaxClassMark() {
		return maxClassMark;
	}



	public void setMaxClassMark(int maxClassMark) {
		this.maxClassMark = maxClassMark;
	}



	public int getRank() {
		return rank;
	}



	public void setRank(int rank) {
		this.rank = rank;
	}



	@Override
	public String toString() {
		return "StudentReportCard [logger=" + logger + ", acTerm=" + acTerm
				+ ", studentGrpId=" + studentGrpId + ", examId=" + examId
				+ ", studentAdmisNo=" + studentAdmisNo + ", subType=" + subType
				+ ", crslId=" + crslId + ", average=" + average + ", maxMarks="
				+ maxMarks + ", minMarks=" + minMarks + ", maxSum=" + maxSum
				+ ", minSum=" + minSum + ", maxMarkObt=" + maxMarkObt
				+ ", attendance=" + attendance + ", subId=" + subId
				+ ", examOrder=" + examOrder + ", subName=" + subName
				+ ", stuMaxMark=" + stuMaxMark + ", stuMinMark=" + stuMinMark
				+ ", stuMarkObt=" + stuMarkObt + ", stuResult=" + stuResult
				+ ", avgClassMark=" + avgClassMark + ", maxClassMark="
				+ maxClassMark + ", rank=" + rank + ", classRank=" + classRank
				+ ", classStrength=" + classStrength + "]";
	}




	
}
