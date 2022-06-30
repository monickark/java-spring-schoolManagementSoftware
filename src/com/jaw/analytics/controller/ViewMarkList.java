package com.jaw.analytics.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.mark.dao.StudentReportCard;

public class ViewMarkList implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(ViewMarkList.class);
	private String examId = "";
	private String studentAdmisNo = "";
	private String crslId = "";
	private float average;
	private int maxMarks;
	private int minMarks;
	private int maxSum;
	private int minSum;
	private String examOrder="";
	private String subName="";
	private String shortCode="";
	private String subId="";
	private int maxMarkObt;
	
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
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
	public int getMinSum() {
		return minSum;
	}
	public void setMinSum(int minSum) {
		this.minSum = minSum;
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
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public int getMaxMarkObt() {
		return maxMarkObt;
	}
	public void setMaxMarkObt(int maxMarkObt) {
		this.maxMarkObt = maxMarkObt;
	}
	@Override
	public String toString() {
		return "ViewMarkList [examId=" + examId + ", studentAdmisNo="
				+ studentAdmisNo + ", crslId=" + crslId + ", average="
				+ average + ", maxMarks=" + maxMarks + ", minMarks=" + minMarks
				+ ", maxSum=" + maxSum + ", minSum=" + minSum + ", examOrder="
				+ examOrder + ", subName=" + subName + ", subId=" + subId
				+ ", maxMarkObt=" + maxMarkObt + "]";
	}

}
