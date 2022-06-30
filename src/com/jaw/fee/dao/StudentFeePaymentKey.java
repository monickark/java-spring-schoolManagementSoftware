package com.jaw.fee.dao;


/**
 * @author Gritz Horizons Ltd 1
 * 
 */
public class StudentFeePaymentKey {

	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String studFeeDDId = "";
	private String feePmtSrlNo = "";
	private String receiptCategory = "";
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
	public String getStudFeeDDId() {
		return studFeeDDId;
	}
	public void setStudFeeDDId(String studFeeDDId) {
		this.studFeeDDId = studFeeDDId;
	}
	public String getFeePmtSrlNo() {
		return feePmtSrlNo;
	}
	public void setFeePmtSrlNo(String feePmtSrlNo) {
		this.feePmtSrlNo = feePmtSrlNo;
	}
	
	public String getReceiptCategory() {
		return receiptCategory;
	}
	public void setReceiptCategory(String receiptCategory) {
		this.receiptCategory = receiptCategory;
	}
	@Override
	public String toString() {
		return "StudentFeePaymentKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", studFeeDDId=" + studFeeDDId
				+ ", feePmtSrlNo=" + feePmtSrlNo + ", receiptCategory="
				+ receiptCategory + "]";
	}
	
	
	

	/*public String stringForDbAudit() {

		StringBuffer result = new StringBuffer();

		result.append("DB_TS=").append(getDbTs())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("BRANCH_ID=").append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("AC_TERM=").append(getAcTerm())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_CATGRY=").append(getFeeCategory())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_TERM=").append(getFeeCategory())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_TYPE=").append(getFeeType())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("COURSE=").append(getCourse())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("TERM=").append(getTerm())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("FEE_AMT=").append(getFeeAmt())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("delFlg=").append(getDelFlag())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_ID=").append(getrCreId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_TIME=").append(getrCreTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_ID=").append(getrModId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_TIME=").append(getrModTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		return result.toString();
	}
*/
}
