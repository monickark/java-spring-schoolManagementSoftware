package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class StudentFeeDemand implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(StudentFeeDemand.class);
	// Properties
	    private Integer dbTs;
		private String instId;
		private String branchId;
		private String sFeeDmdSeqId;
		private String acTerm;
		private String studentAdmissNo;
		private String feeCategory;
		private int feeAmt;
		private int feeDueAmt;
		private int concessionAmt;
		private int monthlyFeeAmt;
		private int monthlyFeeDueAmt;
		private int lastYearUnpaidAmt;
		private String feePaymentTerm ;
		private String studentAccNum="" ;
		private String feeDemandStatus="" ;
		private String feeDemandRemarks="" ;
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
		public String getsFeeDmdSeqId() {
			return sFeeDmdSeqId;
		}
		public void setsFeeDmdSeqId(String sFeeDmdSeqId) {
			this.sFeeDmdSeqId = sFeeDmdSeqId;
		}
		public String getAcTerm() {
			return acTerm;
		}
		public void setAcTerm(String acTerm) {
			this.acTerm = acTerm;
		}
		public String getStudentAdmissNo() {
			return studentAdmissNo;
		}
		public void setStudentAdmissNo(String studentAdmissNo) {
			this.studentAdmissNo = studentAdmissNo;
		}
		public String getFeeCategory() {
			return feeCategory;
		}
		public void setFeeCategory(String feeCategory) {
			this.feeCategory = feeCategory;
		}
		public int getFeeAmt() {
			return feeAmt;
		}
		public void setFeeAmt(int feeAmt) {
			this.feeAmt = feeAmt;
		}
		public int getFeeDueAmt() {
			return feeDueAmt;
		}
		public void setFeeDueAmt(int feeDueAmt) {
			this.feeDueAmt = feeDueAmt;
		}
		public int getConcessionAmt() {
			return concessionAmt;
		}
		public void setConcessionAmt(int concessionAmt) {
			this.concessionAmt = concessionAmt;
		}
		public String getFeePaymentTerm() {
			return feePaymentTerm;
		}
		public void setFeePaymentTerm(String feePaymentTerm) {
			this.feePaymentTerm = feePaymentTerm;
		}
		public String getStudentAccNum() {
			return studentAccNum;
		}
		public void setStudentAccNum(String studentAccNum) {
			this.studentAccNum = studentAccNum;
		}
		public String getFeeDemandStatus() {
			return feeDemandStatus;
		}
		public void setFeeDemandStatus(String feeDemandStatus) {
			this.feeDemandStatus = feeDemandStatus;
		}
		public String getFeeDemandRemarks() {
			return feeDemandRemarks;
		}
		public void setFeeDemandRemarks(String feeDemandRemarks) {
			this.feeDemandRemarks = feeDemandRemarks;
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
		
		public int getMonthlyFeeAmt() {
			return monthlyFeeAmt;
		}
		public void setMonthlyFeeAmt(int monthlyFeeAmt) {
			this.monthlyFeeAmt = monthlyFeeAmt;
		}
		public int getMonthlyFeeDueAmt() {
			return monthlyFeeDueAmt;
		}
		public void setMonthlyFeeDueAmt(int monthlyFeeDueAmt) {
			this.monthlyFeeDueAmt = monthlyFeeDueAmt;
		}
		public int getLastYearUnpaidAmt() {
			return lastYearUnpaidAmt;
		}
		public void setLastYearUnpaidAmt(int lastYearUnpaidAmt) {
			this.lastYearUnpaidAmt = lastYearUnpaidAmt;
		}
		@Override
		public String toString() {
			return "StudentFeeDemand [logger=" + logger + ", dbTs=" + dbTs
					+ ", instId=" + instId + ", branchId=" + branchId
					+ ", sFeeDmdSeqId=" + sFeeDmdSeqId + ", acTerm=" + acTerm
					+ ", studentAdmissNo=" + studentAdmissNo + ", feeCategory="
					+ feeCategory + ", feeAmt=" + feeAmt + ", feeDueAmt="
					+ feeDueAmt + ", concessionAmt=" + concessionAmt
					+ ", monthlyFeeAmt=" + monthlyFeeAmt
					+ ", monthlyFeeDueAmt=" + monthlyFeeDueAmt
					+ ", lastYearUnpaidAmt=" + lastYearUnpaidAmt
					+ ", feePaymentTerm=" + feePaymentTerm + ", studentAccNum="
					+ studentAccNum + ", feeDemandStatus=" + feeDemandStatus
					+ ", feeDemandRemarks=" + feeDemandRemarks + ", delFlag="
					+ delFlag + ", rModId=" + rModId + ", rModTime=" + rModTime
					+ ", rCreId=" + rCreId + ", rCreTime=" + rCreTime + "]";
		}
	

}
