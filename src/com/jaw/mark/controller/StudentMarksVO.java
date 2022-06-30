package com.jaw.mark.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.mark.dao.StudentMarks;

public class StudentMarksVO implements Serializable {

	// Logging
	Logger logger = Logger.getLogger(StudentMarksVO.class);

	// Properties		
	private String mkslSeqId;
	private String studentAdmisNo ;		
	private String rollNumber;
	private String studentName;
	private int marksObtd;
	private String gradeObtd=" ";
	private String subRemarks=" ";
	private String attendance="";
	private String updateReason;
	public String getMkslSeqId() {
		return mkslSeqId;
	}
	public void setMkslSeqId(String mkslSeqId) {
		this.mkslSeqId = mkslSeqId;
	}
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getMarksObtd() {
		return marksObtd;
	}
	public void setMarksObtd(int marksObtd) {
		this.marksObtd = marksObtd;
	}
	public String getGradeObtd() {
		return gradeObtd;
	}
	public void setGradeObtd(String gradeObtd) {
		this.gradeObtd = gradeObtd;
	}
	public String getSubRemarks() {
		return subRemarks;
	}
	public void setSubRemarks(String subRemarks) {
		this.subRemarks = subRemarks;
	}
	public String getAttendance() {
		return attendance;
	}
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	@Override
	public String toString() {
		return "StudentMarksVO [mkslSeqId=" + mkslSeqId + ", studentAdmisNo="
				+ studentAdmisNo + ", rollNumber=" + rollNumber
				+ ", studentName=" + studentName + ", marksObtd=" + marksObtd
				+ ", gradeObtd=" + gradeObtd + ", subRemarks=" + subRemarks
				+ ", attendance=" + attendance + ", updateReason="
				+ updateReason + "]";
	}
	


}
