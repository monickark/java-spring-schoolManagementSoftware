package com.jaw.attendance.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;
//StudentAttendance Pojo class
public class StudentAttendance implements Serializable{
	// Logging
			 Logger logger = Logger.getLogger(StudentAttendance.class);
			// Properties			 
			private Integer dbTs;
			private String instId;
			private String branchId;
			private String sattSeqNo;
			private String acTerm ;
			private String attDate ;
			private String studentAdmisNo ;		
			private String shiftId = "" ;
			private String subId= "" ;		
			private String optSubId = "" ;
			private int noOfSessions;			
			private String crslId;
			private String clsType;
			private String isPresent ;
			private String absenteeRmks = "";
			private String delFlag = "";
			private String rModId = "";
			private String rModTime = "";
			private String rCreId = "";
			private String rCreTime = "";
			
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
			public String getSattSeqNo() {
				return sattSeqNo;
			}
			public void setSattSeqNo(String sattSeqNo) {
				this.sattSeqNo = sattSeqNo;
			}
			public String getAcTerm() {
				return acTerm;
			}
			public void setAcTerm(String acTerm) {
				this.acTerm = acTerm;
			}
			public String getAttDate() {
				return attDate;
			}
			public void setAttDate(String attDate) {
				this.attDate = attDate;
			}
			public String getStudentAdmisNo() {
				return studentAdmisNo;
			}
			public void setStudentAdmisNo(String studentAdmisNo) {
				this.studentAdmisNo = studentAdmisNo;
			}
			public String getShiftId() {
				return shiftId;
			}
			public void setShiftId(String shiftId) {
				this.shiftId = shiftId;
			}
			public String getSubId() {
				return subId;
			}
			public void setSubId(String subId) {
				this.subId = subId;
			}
			public String getOptSubId() {
				return optSubId;
			}
			public void setOptSubId(String optSubId) {
				this.optSubId = optSubId;
			}
			public int getNoOfSessions() {
				return noOfSessions;
			}
			public void setNoOfSessions(int noOfSessions) {
				this.noOfSessions = noOfSessions;
			}
			public String getCrslId() {
				return crslId;
			}
			public void setCrslId(String crslId) {
				this.crslId = crslId;
			}
			public String getIsPresent() {
				return isPresent;
			}
			public void setIsPresent(String isPresent) {
				this.isPresent = isPresent;
			}
			public String getAbsenteeRmks() {
				return absenteeRmks;
			}
			public void setAbsenteeRmks(String absenteeRmks) {
				this.absenteeRmks = absenteeRmks;
			}
			public String getDelFlag() {
				return delFlag;
			}
			public void setDelFlag(String delFlag) {
				this.delFlag = delFlag;
			}
			public String getrModId() {
				return rModId;
			}
			public void setrModId(String rModId) {
				this.rModId = rModId;
			}
			public String getrModTime() {
				return rModTime;
			}
			public void setrModTime(String rModTime) {
				this.rModTime = rModTime;
			}
			public String getrCreId() {
				return rCreId;
			}
			public void setrCreId(String rCreId) {
				this.rCreId = rCreId;
			}
			public String getrCreTime() {
				return rCreTime;
			}
			public void setrCreTime(String rCreTime) {
				this.rCreTime = rCreTime;
			}
			public String getClsType() {
				return clsType;
			}
			public void setClsType(String clsType) {
				this.clsType = clsType;
			}
			@Override
			public String toString() {
				return "StudentAttendance [dbTs=" + dbTs + ", instId=" + instId
						+ ", branchId=" + branchId + ", sattSeqNo=" + sattSeqNo
						+ ", acTerm=" + acTerm + ", attDate=" + attDate
						+ ", studentAdmisNo=" + studentAdmisNo + ", shiftId="
						+ shiftId + ", subId=" + subId + ", optSubId="
						+ optSubId + ", noOfSessions=" + noOfSessions
						+ ", crslId=" + crslId + ", clsType=" + clsType
						+ ", isPresent=" + isPresent + ", absenteeRmks="
						+ absenteeRmks + ", delFlag=" + delFlag + ", rModId="
						+ rModId + ", rModTime=" + rModTime + ", rCreId="
						+ rCreId + ", rCreTime=" + rCreTime + "]";
			}
			
			
			
			
}
