package com.jaw.student.admission.dao;

import org.apache.log4j.Logger;


public class AdmissionCountList {
	Logger logger = Logger.getLogger(AdmissionCountList.class);
	
	private String course;
	private int count;
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "AdmissionCountListVO [course=" + course + ", count=" + count
				+ ", getCourse()=" + getCourse() + ", getCount()=" + getCount()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
