package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class SpecialClassKey implements Serializable{
	// Logging
			 Logger logger = Logger.getLogger(SpecialClassKey.class);
			 
			// Properties
			private Integer dbTs;
			private String instId;
			private String branchId;
			private String acTerm ;
			private String scItemId ;
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
			public String getScItemId() {
				return scItemId;
			}
			public void setScItemId(String scItemId) {
				this.scItemId = scItemId;
			}
			@Override
			public String toString() {
				return "SpecialClassKey [dbTs=" + dbTs + ", instId=" + instId
						+ ", branchId=" + branchId + ", acTerm=" + acTerm
						+ ", scItemId=" + scItemId + "]";
			}
			
			// method for create StudentGroupMasterKey String for Audit
						public String toStringForAuditSpecialClassKey() {

							StringBuffer stringBuffer = new StringBuffer()
							.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
							.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
							.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
							.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
							.append("SC_ITEM_ID=").append(getScItemId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
							

							logger.debug("String formed for audit is :" + stringBuffer.toString());

							return stringBuffer.toString();
						}
}
