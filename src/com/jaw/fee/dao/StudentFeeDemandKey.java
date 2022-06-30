package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class StudentFeeDemandKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(StudentFeeDemandKey.class);
	// Properties
	    private Integer dbTs;
		private String instId;
		private String branchId;
		private String sFeeDmdSeqId;
		private String acTerm;
		private String feeCategory;
		private String studentAdmisNo;
		private String stGroup;
		private String feePaymentTerm;
		
		
		
		public String getAcTerm() {
			return acTerm;
		}
		public void setAcTerm(String acTerm) {
			this.acTerm = acTerm;
		}
		public String getFeeCategory() {
			return feeCategory;
		}
		public void setFeeCategory(String feeCategory) {
			this.feeCategory = feeCategory;
		}
	
		public String getStudentAdmisNo() {
			return studentAdmisNo;
		}
		public void setStudentAdmisNo(String studentAdmisNo) {
			this.studentAdmisNo = studentAdmisNo;
		}
		public String getStGroup() {
			return stGroup;
		}
		public void setStGroup(String stGroup) {
			this.stGroup = stGroup;
		}
		public String getFeePaymentTerm() {
			return feePaymentTerm;
		}
		public void setFeePaymentTerm(String feePaymentTerm) {
			this.feePaymentTerm = feePaymentTerm;
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
		public String getsFeeDmdSeqId() {
			return sFeeDmdSeqId;
		}
		public void setsFeeDmdSeqId(String sFeeDmdSeqId) {
			this.sFeeDmdSeqId = sFeeDmdSeqId;
		}
		@Override
		public String toString() {
			return "StudentFeeDemandKey [logger=" + logger + ", dbTs=" + dbTs
					+ ", instId=" + instId + ", branchId=" + branchId
					+ ", sFeeDmdSeqId=" + sFeeDmdSeqId + ", acTerm=" + acTerm
					+ ", feeCategory=" + feeCategory + ", studentAdmisNo="
					+ studentAdmisNo + ", stGroup=" + stGroup
					+ ", feePaymentTerm=" + feePaymentTerm + "]";
		}
	
		
		

}
