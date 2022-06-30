package com.jaw.batch.dao;

import java.io.Serializable;
import org.apache.log4j.Logger;
import com.jaw.common.constants.AuditConstant;

//Database VO class for BatchList
public class BatchPgms implements Serializable {

	// Logging
	Logger logger = Logger.getLogger(BatchPgms.class);

	private Integer dbTs;
	private String instId;
	private String branchId;
	private String batchPgmId;
	private String batchPgmName = "";
	private String upldDataType = "";
	private String excelFileName = "";
	private String xmlFileName = "";
	private String xmlFileSrlNo = "";
	private String sheetName = "";
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

	public String getBatchPgmId() {
		return batchPgmId;
	}

	public void setBatchPgmId(String batchPgmId) {
		this.batchPgmId = batchPgmId;
	}

	public String getBatchPgmName() {
		return batchPgmName;
	}

	public void setBatchPgmName(String batchPgmName) {
		this.batchPgmName = batchPgmName;
	}

	public String getUpldDataType() {
		return upldDataType;
	}

	public void setUpldDataType(String upldDataType) {
		this.upldDataType = upldDataType;
	}

	public String getXmlFileName() {
		return xmlFileName;
	}

	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	public String getXmlFileSrlNo() {
		return xmlFileSrlNo;
	}

	public void setXmlFileSrlNo(String xmlFileSrlNo) {
		this.xmlFileSrlNo = xmlFileSrlNo;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
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

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	@Override
	public String toString() {
		return "BatchPgms [logger=" + logger + ", dbTs=" + dbTs + ", instId="
				+ instId + ", branchId=" + branchId + ", batchPgmId="
				+ batchPgmId + ", batchPgmName=" + batchPgmName
				+ ", upldDataType=" + upldDataType + ", excelFileName="
				+ excelFileName + ", xmlFileName=" + xmlFileName
				+ ", xmlFileSrlNo=" + xmlFileSrlNo + ", sheetName=" + sheetName
				+ ", delFlag=" + delFlag + ", rModId=" + rModId + ", rModTime="
				+ rModTime + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ "]";
	}

	// method for create InstituteMaster Record for Audit
	public String toStringForAuditInstMasterRecord() {

		StringBuffer stringBuffer = new StringBuffer().append("DB_TS=")
				.append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_ID=")
				.append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BATCH_PGM_ID=").append(getBatchPgmId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BATCH_NAME=").append(getBatchPgmName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("UPLD_DATA_TYPE=").append(getUpldDataType())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("XML_FILE_NAME=").append(getXmlFileName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("EXCEL_FILE_NAME=").append(getExcelFileName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("XML_FILE_SRL_NO=").append(getXmlFileSrlNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("SHEET_NAME=").append(getSheetName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("DEL_FLG=")
				.append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_ID=").append(getrCreId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_TIME=").append(getrModTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("R_CRE_ID=")
				.append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_CRE_TIME=").append(getrCreTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);

		logger.debug("String formed for audit is :" + stringBuffer.toString());

		return stringBuffer.toString();
	}

	// method for create InstituteMasterKey String for Audit
	public String toStringForAuditInstMasterKey() {

		StringBuffer stringBuffer = new StringBuffer().append("INST_ID=")
				.append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_ID=").append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_NAME=").append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BATCH_PGM_ID=").append(getBatchPgmId());
		return stringBuffer.toString();
	}

}
