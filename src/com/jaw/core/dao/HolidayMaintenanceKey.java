package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class HolidayMaintenanceKey implements Serializable{
	// Logging
		 Logger logger = Logger.getLogger(HolidayMaintenanceKey.class);	
		// Properties
		private Integer dbTs;
		private String instId;
		private String branchId;
		private String acTerm ;
		private String holDate ;
		private String studentGrpId ;
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
		public String getAcTerm() {
			return acTerm;
		}
		public void setAcTerm(String acTerm) {
			this.acTerm = acTerm;
		}
		public String getHolDate() {
			return holDate;
		}
		public void setHolDate(String holDate) {
			this.holDate = holDate;
		}
		public String getStudentGrpId() {
			return studentGrpId;
		}
		public void setStudentGrpId(String studentGrpId) {
			this.studentGrpId = studentGrpId;
		}
		@Override
		public String toString() {
			return "HolidayMaintenanceKey [dbTs=" + dbTs + ", instId=" + instId
					+ ", branchId=" + branchId + ", acTerm=" + acTerm
					+ ", holDate=" + holDate + ", studentGrpId=" + studentGrpId
					+ "]";
		}
		
}
