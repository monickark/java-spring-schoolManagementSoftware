package com.jaw.student.admission.dao;

import org.apache.log4j.Logger;
import java.io.Serializable;
import java.math.BigDecimal;

import com.jaw.common.constants.AuditConstant;

public class PrevAcademicDetails implements Serializable{
	Logger logger=Logger.getLogger(PrevAcademicDetails.class);
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String studentAdmisNo;
	private String prevStudiedStd = "";
	private String prevSchoolName = "";
	private String prevSchoolAdd1 = "";
	private String prevSchoolAdd2 = "";
	private String prevSchoolAdd3 = "";
	private String prevStudiedYear = "";
	private Integer markObtPrevStd = null;
	private String transCertificateRefno = "";
	private String marksheetRefno = "";
	private String delFlg = "";
	private String rModId = "";
	private String rModTime;
	private String rCreId = "";
	private String rCreTime;
	
	private String boardExamFlg = "N";
	private String boardExamRegNo = "";
	private String board = "";
	private String mediumOfInst = "";
	private String passedPeriod = "";
	private Integer noOfAttempts;
	
	public Integer getNoOfAttempts() {
		return noOfAttempts;
	}
	public void setNoOfAttempts(Integer noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}
	public Integer getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(Integer maxMarks) {
		this.maxMarks = maxMarks;
	}	


	private String marksObtainedInBoard = "";
	private Integer maxMarks =null;
	public Double getPercentageObtained() {
		return percentageObtained;
	}


	private Double percentageObtained =null;
	public Integer getMarkObtPrevStd() {
		return markObtPrevStd;
	}
	public void setMarkObtPrevStd(Integer markObtPrevStd) {
		this.markObtPrevStd = markObtPrevStd;
	}


	private String appearedForCET = "";	
	public void setSportsEntity(String sportsEntity) {
		this.sportsEntity = sportsEntity;
	}
	public String getSportsDetails() {
		return sportsDetails;
	}
	public String getSportsEntity() {
		return sportsEntity;
	}
	public void setPercentageObtained(Double percentageObtained) {
		this.percentageObtained = percentageObtained;
	}
	public void setSportsDetails(String sportsDetails) {
		this.sportsDetails = sportsDetails;
	}


	private String extraActivities = "";
	private String sportsEntity = "";
	private String sportsDetails = "";
	
	
	
	



	
	
	public Integer getDbTs() {
		return dbTs;
	}
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public String getBoardExamFlg() {
		return boardExamFlg;
	}
	public void setBoardExamFlg(String boardExamFlg) {
		this.boardExamFlg = boardExamFlg;
	} 
	public String getBoardExamRegNo() {
		return boardExamRegNo;
	}
	public void setBoardExamRegNo(String boardExamRegNo) {
		this.boardExamRegNo = boardExamRegNo;
	}
	public String getBoard() {
		return board;
	}
	@Override
	public String toString() {
		return "PrevAcademicDetails [logger=" + logger + ", dbTs=" + dbTs
				+ ", instId=" + instId + ", branchId=" + branchId
				+ ", studentAdmisNo=" + studentAdmisNo + ", prevStudiedStd="
				+ prevStudiedStd + ", prevSchoolName=" + prevSchoolName
				+ ", prevSchoolAdd1=" + prevSchoolAdd1 + ", prevSchoolAdd2="
				+ prevSchoolAdd2 + ", prevSchoolAdd3=" + prevSchoolAdd3
				+ ", prevStudiedYear=" + prevStudiedYear + ", markObtPrevStd="
				+ markObtPrevStd + ", transCertificateRefno="
				+ transCertificateRefno + ", marksheetRefno=" + marksheetRefno
				+ ", delFlg=" + delFlg + ", rModId=" + rModId + ", rModTime="
				+ rModTime + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ ", boardExamFlg=" + boardExamFlg + ", boardExamRegNo="
				+ boardExamRegNo + ", board=" + board + ", mediumOfInst="
				+ mediumOfInst + ", passedPeriod=" + passedPeriod
				+ ", noOfAttempts=" + noOfAttempts + ", marksObtainedInBoard="
				+ marksObtainedInBoard + ", maxMarks=" + maxMarks
				+ ", percentageObtained=" + percentageObtained
				+ ", appearedForCET=" + appearedForCET + ", extraActivities="
				+ extraActivities + ", sportsEntity=" + sportsEntity
				+ ", sportsDetails=" + sportsDetails + "]";
	}
	public void setBoard(String board) {
		this.board = board;
	}
	public String getMediumOfInst() {
		return mediumOfInst;
	}
	public void setMediumOfInst(String mediumOfInst) {
		this.mediumOfInst = mediumOfInst;
	}	
	public String getPassedPeriod() {
		return passedPeriod;
	}
	public void setPassedPeriod(String passedPeriod) {
		this.passedPeriod = passedPeriod;
	}	
	public String getMarksObtainedInBoard() {
		return marksObtainedInBoard;
	}
	public void setMarksObtainedInBoard(String marksObtainedInBoard) {
		this.marksObtainedInBoard = marksObtainedInBoard;
	}	
	public String getAppearedForCET() {
		return appearedForCET;
	}
	public void setAppearedForCET(String appearedForCET) {
		this.appearedForCET = appearedForCET;
	}
	public String getExtraActivities() {
		return extraActivities;
	}
	public void setExtraActivities(String extraActivities) {
		this.extraActivities = extraActivities;
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
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	public String getPrevStudiedStd() {
		return prevStudiedStd;
	}
	public void setPrevStudiedStd(String prevStudiedStd) {
		this.prevStudiedStd = prevStudiedStd;
	}
	public String getPrevSchoolName() {
		return prevSchoolName;
	}
	public void setPrevSchoolName(String prevSchoolName) {
		this.prevSchoolName = prevSchoolName;
	}
	public String getPrevSchoolAdd1() {
		return prevSchoolAdd1;
	}
	public void setPrevSchoolAdd1(String prevSchoolAdd1) {
		this.prevSchoolAdd1 = prevSchoolAdd1;
	}
	public String getPrevSchoolAdd2() {
		return prevSchoolAdd2;
	}
	public void setPrevSchoolAdd2(String prevSchoolAdd2) {
		this.prevSchoolAdd2 = prevSchoolAdd2;
	}
	public String getPrevSchoolAdd3() {
		return prevSchoolAdd3;
	}
	public void setPrevSchoolAdd3(String prevSchoolAdd3) {
		this.prevSchoolAdd3 = prevSchoolAdd3;
	}
	public String getPrevStudiedYear() {
		return prevStudiedYear;
	}
	public void setPrevStudiedYear(String prevStudiedYear) {
		this.prevStudiedYear = prevStudiedYear;
	}	
	public String getTransCertificateRefno() {
		return transCertificateRefno;
	}
	public void setTransCertificateRefno(String transCertificateRefno) {
		this.transCertificateRefno = transCertificateRefno;
	}
	public String getMarksheetRefno() {
		return marksheetRefno;
	}
	public void setMarksheetRefno(String marksheetRefno) {
		this.marksheetRefno = marksheetRefno;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
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
	
	// method for create InstituteMaster Record for Audit
			public  String toStringForAuditInstMasterRecord()
			{
				
				StringBuffer  stringBuffer=new StringBuffer()		
				.append("DB_TS=")
				.append(getDbTs())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=")
				.append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_ID=")
				.append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("STUDENT_ADMIS_NO=")
				.append(getStudentAdmisNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PREV_STUDIED_STD=")
				.append(getPrevStudiedStd())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PREV_SCHOOL_NAME=")
				.append(getPrevSchoolName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PREV_SCHOOL_ADD1=")
				.append(getPrevSchoolAdd1())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PREV_SCHOOL_ADD2=")
				.append(getPrevSchoolAdd2())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PREV_SCHOOL_ADD3=")
				.append(getPrevSchoolAdd3())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PREV_STUDIED_YEAR=")
				.append(getPrevStudiedYear())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MARK_OBT_PREV_STD=")
				.append(getMarkObtPrevStd())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MAX_MARKS=")
				.append(getMaxMarks())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PERCEN_OBTAINED=")
				.append(getPercentageObtained())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("APPEARED_FOR_CET=")
				.append(getAppearedForCET())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("EXTRA_ACTIVITIES=")
				.append(getExtraActivities())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)				
				.append("SPORTS_ENTITY=")
				.append(getSportsEntity())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("SPORTS_DETAILS=")
				.append(getSportsDetails())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)				
				.append("TRANSFER_CERTIFICATE_REFNO=")
				.append(getTransCertificateRefno())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)				
				.append("MARKSHEET_REFNO=")
				.append(getMarksheetRefno())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("DEL_FLG=")
				.append(getDelFlg())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_ID=")
				.append(getrModId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_TIME=")
				.append(getrModTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_CRE_ID=")
				.append(getrCreId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_CRE_TIME=")
				.append(getrCreTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
				
				
				logger.debug("String formed for audit is :"+stringBuffer.toString());
				return stringBuffer.toString();
			}
			
			
			// method for create InstituteMasterKey String for Audit
			public String toStringForAuditInstMasterKey() {
				
				
				StringBuffer  stringBuffer=new StringBuffer()
				.append("INST_ID=")
				.append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_ID=")
				.append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("STUDENT_ADMIS_NO=")
				.append(getStudentAdmisNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
				
				return stringBuffer.toString();
			}
}
