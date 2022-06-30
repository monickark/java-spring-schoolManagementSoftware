package com.jaw.staff.dao;
import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.jaw.common.constants.AuditConstant;



public class StaffDetails implements Serializable{
	private Integer dbTs;
	private String instId;
	private String branchId ;
	private String staffId ;
	private String staffName = "";
	private String qualification = "";
	private String noOfYrsExp ;
	private String skills = "";
	private String prevWorkPlace = "";
	private String doj ;
	private String deptId = "";
	private String designation = "";
	private String staffCategory1="";
	private String staffCategory2 = "";
	private String staffRoom = "";
	private String salaryActNo = "";
	private String salary ;
	private String transfered="";	
	private String prevTransferedFrom = "";
	private String transferedDate ;
	private String reasonForLeaving = "";
	private String transferDate ;
	private String transferedTo = "";
	private String delFlg="";
	private String rCreId="";
	private String rCreTime="";
	private String rModId="";
	private String rModTime="";
	
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
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getNoOfYrsExp() {
		return noOfYrsExp;
	}
	public void setNoOfYrsExp(String noOfYrsExp) {
		this.noOfYrsExp = noOfYrsExp;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public String getPrevWorkPlace() {
		return prevWorkPlace;
	}
	public void setPrevWorkPlace(String prevWorkPlace) {
		this.prevWorkPlace = prevWorkPlace;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getStaffCategory1() {
		return staffCategory1;
	}
	public void setStaffCategory1(String staffCategory1) {
		this.staffCategory1 = staffCategory1;
	}
	public String getStaffCategory2() {
		return staffCategory2;
	}
	public void setStaffCategory2(String staffCategory2) {
		this.staffCategory2 = staffCategory2;
	}
	public String getStaffRoom() {
		return staffRoom;
	}
	public void setStaffRoom(String staffRoom) {
		this.staffRoom = staffRoom;
	}
	public String getSalaryActNo() {
		return salaryActNo;
	}
	public void setSalaryActNo(String salaryActNo) {
		this.salaryActNo = salaryActNo;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getTransfered() {
		return transfered;
	}
	public void setTransfered(String transfered) {
		this.transfered = transfered;
	}
	public String getPrevTransferedFrom() {
		return prevTransferedFrom;
	}
	public void setPrevTransferedFrom(String prevTransferedFrom) {
		this.prevTransferedFrom = prevTransferedFrom;
	}
	public String getTransferedDate() {
		return transferedDate;
	}
	public void setTransferedDate(String transferedDate) {
		this.transferedDate = transferedDate;
	}
	
	public String getReasonForLeaving() {
		return reasonForLeaving;
	}
	public void setReasonForLeaving(String reasonForLeaving) {
		this.reasonForLeaving = reasonForLeaving;
	}
	public String getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}
	public String getTransferedTo() {
		return transferedTo;
	}
	public void setTransferedTo(String transferedTo) {
		this.transferedTo = transferedTo;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
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

		// method for create InstituteMaster Record for Audit
		public String toStringForDBAuditRecord() {
			StringBuffer stringBuffer = new StringBuffer()
					.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("STAFF_ID=").append(getStaffId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("STAFF_NAME=").append(getStaffName()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("QUALIFICATION=").append(getQualification()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("NOY_EXP=").append(getNoOfYrsExp()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("SKILLS=").append(getSkills())	.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("PREV_WORKPLACE=").append(getPrevWorkPlace()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("DOJ=").append(getDoj()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("DESIGNATION=").append(getDesignation()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("STAFF_CATEGORY1=").append(getStaffCategory1()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("STAFF_CATEGORY2=").append(getStaffCategory2()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("STAFF_ROOM=").append(getStaffRoom()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("SALARY_ACT_NO=").append(getSalaryActNo()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("SALARY=").append(getSalary()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("TRANSFERED=").append(getTransfered()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("TRANSFERED_FROM=").append(getTransfered()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("TRANSFERED_DATE=").append(getTransferedDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("REASON_FOR_LEAVING=").append(getReasonForLeaving()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("TRANSFER_DATE=").append(getTransferDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("TRANSFER_TO=").append(getTransferedTo()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("DEL_FLG=").append(getDelFlg()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

		

			return stringBuffer.toString();
		}
	
		// method for create InstituteMasterKey String for Audit
		public String toStringForAuditInstMasterKey() {
			StringBuffer stringBuffer = new StringBuffer()
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("STAFF_ID=").append(getStaffId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
			return stringBuffer.toString();
		}
	
}
