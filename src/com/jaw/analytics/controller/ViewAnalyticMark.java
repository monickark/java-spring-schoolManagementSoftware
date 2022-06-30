package com.jaw.analytics.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ViewAnalyticMark implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(ViewAnalyticMark.class);
	private  List<Integer[]> listOfExamArray =new ArrayList<Integer[]>();;
	private  String[] subjectList ;
	private  String[] shortcodeList ;
	private  String[] examList ;
	public List<Integer[]> getListOfExamArray() {
		return listOfExamArray;
	}
	public void setListOfExamArray(List<Integer[]> listOfExamArray) {
		this.listOfExamArray = listOfExamArray;
	}
	public String[] getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(String[] subjectList) {
		this.subjectList = subjectList;
	}
	public String[] getExamList() {
		return examList;
	}
	public void setExamList(String[] examList) {
		this.examList = examList;
	}
	public String[] getShortcodeList() {
		return shortcodeList;
	}
	public void setShortcodeList(String[] shortcodeList) {
		this.shortcodeList = shortcodeList;
	}
	
	

}
