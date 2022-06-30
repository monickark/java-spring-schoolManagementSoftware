package com.jaw.student.admission.controller;

import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
public class PrevAcademicDetailsVO implements Serializable{	
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String studentAdmisNo = "";
	private String prevStudiedStd = "";	
	private String prevSchoolName = "";
	private String prevSchoolAdd1 = "";
	private String prevSchoolAdd2 = "";
	private String prevSchoolAdd3 = "";
	private String prevStudiedYear = "";
	private Integer markObtPrevStd = null;
	private String transCertificateRefno = "";
	private String marksheetRefno = "";	
	private String serialNoForExcelUpdate = "";	
	private MultipartFile transferCertificate;
	private MultipartFile marksheet;
	
	private String boardExamFlg = "";
	private String boardExamRegNo = "";
	private String board = "";
	private String mediumOfInst = "";
	private String passedPeriod = "";
	private Integer noOfAttempts ;
	private String marksObtainedInBoard = "";
	private Integer maxMarks = null;
	private Double percentageObtained = null;
	private String appearedForCET = "";
	private String extraActivities = "";
	private String sportsEntity = "";
	private String sportsDetails = "";
	
	
	public Integer getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(Integer maxMarks) {
		this.maxMarks = maxMarks;
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

	public void setBoard(String board) {
		this.board = board;
	}

	public String getMediumOfInst() {
		return mediumOfInst;
	}

	public void setMediumOfInst(String mediumOfInst) {
		this.mediumOfInst = mediumOfInst;
	}

	public String getMarksObtainedInBoard() {
		return marksObtainedInBoard;
	}

	public void setMarksObtainedInBoard(String marksObtainedInBoard) {
		this.marksObtainedInBoard = marksObtainedInBoard;
	}

	

	public String getPassedPeriod() {
		return passedPeriod;
	}

	public String getSportsEntity() {
		return sportsEntity;
	}

	public void setSportsEntity(String sportsEntity) {
		this.sportsEntity = sportsEntity;
	}

	public String getSportsDetails() {
		return sportsDetails;
	}

	public void setSportsDetails(String sportsDetails) {
		this.sportsDetails = sportsDetails;
	}

	public void setPassedPeriod(String passedPeriod) {
		this.passedPeriod = passedPeriod;
	}

		
	public Integer getNoOfAttempts() {
		return noOfAttempts;
	}

	public void setNoOfAttempts(Integer noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}



	public Double getPercentageObtained() {
		return percentageObtained;
	}

	public void setPercentageObtained(Double percentageObtained) {
		this.percentageObtained = percentageObtained;
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
	
	

		
	
	
	public String getSerialNoForExcelUpdate() {
		return serialNoForExcelUpdate;
	}

	public void setSerialNoForExcelUpdate(String serialNoForExcelUpdate) {
		this.serialNoForExcelUpdate = serialNoForExcelUpdate;
	}

	public Integer getDbTs() {
		return dbTs;
	}

	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}

	public MultipartFile getTransferCertificate() {
		return transferCertificate;
	}

	public void setTransferCertificate(MultipartFile transferCertificate) {
		this.transferCertificate = transferCertificate;
	}

	public MultipartFile getMarksheet() {
		return marksheet;
	}

	public void setMarksheet(MultipartFile marksheet) {
		this.marksheet = marksheet;
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

	public Integer getMarkObtPrevStd() {
		return markObtPrevStd;
	}

	public void setMarkObtPrevStd(Integer markObtPrevStd) {
		this.markObtPrevStd = markObtPrevStd;
	}

	public String getMarksheetRefno() {
		return marksheetRefno;
	}

	public void setMarksheetRefno(String marksheetRefno) {
		this.marksheetRefno = marksheetRefno;
	}
	
	@Override
	public String toString() {
		return "PrevAcademicDetailsVO [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", studentAdmisNo="
				+ studentAdmisNo + ", prevStudiedStd=" + prevStudiedStd
				+ ", prevSchoolName=" + prevSchoolName + ", prevSchoolAdd1="
				+ prevSchoolAdd1 + ", prevSchoolAdd2=" + prevSchoolAdd2
				+ ", prevSchoolAdd3=" + prevSchoolAdd3 + ", prevStudiedYear="
				+ prevStudiedYear + ", markObtPrevStd=" + markObtPrevStd
				+ ", transCertificateRefno=" + transCertificateRefno
				+ ", marksheetRefno=" + marksheetRefno
				+ ", serialNoForExcelUpdate=" + serialNoForExcelUpdate
				+ ", transferCertificate=" + transferCertificate
				+ ", marksheet=" + marksheet + ", boardExamFlg=" + boardExamFlg
				+ ", boardExamRegNo=" + boardExamRegNo + ", board=" + board
				+ ", mediumOfInst=" + mediumOfInst + ", passedPeriod="
				+ passedPeriod + ", noOfAttempts=" + noOfAttempts
				+ ", marksObtainedInBoard=" + marksObtainedInBoard
				+ ", maxMarks=" + maxMarks + ", percentageObtained="
				+ percentageObtained + ", appearedForCET=" + appearedForCET
				+ ", extraActivities=" + extraActivities + ", sportsEntity="
				+ sportsEntity + ", sportsDetails=" + sportsDetails + "]";
	}
	
}
