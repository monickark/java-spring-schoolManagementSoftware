package com.jaw.student.admission.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jaw.common.constants.RadioButtons;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.student.controller.StudentSearchVO;

public class AdmissionDetailsVO implements Serializable {
	private String instId = "";
	private String branchId = "";
	private String auditFlag = "";
	private String academicYear = "";
	private String studentAdmisNo = "";
	private String userId = "";
	private String studentId = "";
	private String delFlg = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
	private String pageNo = "";	
	private byte[] image;
	private StudentMasterVO studentMasterVO;
	private StudentInfoVO studentInfoVO;
	private ParentDetailsVO parentDetailsVO;
	private List<SiblingDetailsVO> siblingDetailsVO = new ArrayList<SiblingDetailsVO>();
	private CommunicationDetailsVO communicationDetailsVO;
	private PrevAcademicDetailsVO prevAcademicDetailsVO;
	private TransportDetailsVO transportDetailsVO;
	private List<FileMasterVO> fileMasterListVO;
	private List<MultipartFile> files;
	private List<CommonCode> fileSeq;
	private UserAcces userAcces;
	private StudentSearchVO studentSearchVO = new StudentSearchVO();
	private RadioButtons radioButtons;
	private List<PreSportParticipationDetailsVO> preSportPartDetails = new ArrayList<PreSportParticipationDetailsVO>();
	private List<MultipartFile> sportsCert ;
	private String fileType = "";
	/*private String sportsCertSrlNo;*/
	private UserDetailsForSMS userDetailsForSMS=new UserDetailsForSMS();
	
	
	public List<PreSportParticipationDetailsVO> getPreSportPartDetails() {
		return preSportPartDetails;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public void setPreSportPartDetails(
			List<PreSportParticipationDetailsVO> preSportPartDetails) {
		this.preSportPartDetails = preSportPartDetails;
	}


	/*public String getSportsCertSrlNo() {
		return sportsCertSrlNo;
	}


	public void setSportsCertSrlNo(String sportsCertSrlNo) {
		this.sportsCertSrlNo = sportsCertSrlNo;
	}
*/

	public String getPageNo() {
		return pageNo;
	}
		

	public List<MultipartFile> getSportsCert() {
		return sportsCert;
	}


	public void setSportsCert(List<MultipartFile> sportsCert) {
		this.sportsCert = sportsCert;
	}


	public RadioButtons getRadioButtons() {
		return radioButtons;
	}

	public void setRadioButtons(RadioButtons radioButtons) {
		this.radioButtons = radioButtons;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public StudentSearchVO getStudentSearchVO() {
		return studentSearchVO;
	}

	public void setStudentSearchVO(StudentSearchVO studentSearchVO) {
		this.studentSearchVO = studentSearchVO;
	}

	public UserAcces getUserAcces() {
		return userAcces;
	}

	public void setUserAcces(UserAcces userAcces) {
		this.userAcces = userAcces;
	}

	public String getrModTime() {
		return rModTime;
	}

	public void setrModTime(String rModTime) {
		this.rModTime = rModTime;
	}

	public String getrCreTime() {
		return rCreTime;
	}

	public void setrCreTime(String rCreTime) {
		this.rCreTime = rCreTime;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public List<CommonCode> getFileSeq() {
		return fileSeq;
	}

	public void setFileSeq(List<CommonCode> fileSeq) {
		this.fileSeq = fileSeq;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public List<FileMasterVO> getFileMasterListVO() {
		return fileMasterListVO;
	}

	public void setFileMasterListVO(List<FileMasterVO> fileMasterListVO) {
		this.fileMasterListVO = fileMasterListVO;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public TransportDetailsVO getTransportDetailsVO() {
		return transportDetailsVO;
	}

	public void setTransportDetailsVO(TransportDetailsVO transportDetailsVO) {
		this.transportDetailsVO = transportDetailsVO;
	}

	public PrevAcademicDetailsVO getPrevAcademicDetailsVO() {
		return prevAcademicDetailsVO;
	}

	public void setPrevAcademicDetailsVO(
			PrevAcademicDetailsVO prevAcademicDetailsVO) {
		this.prevAcademicDetailsVO = prevAcademicDetailsVO;
	}

	public CommunicationDetailsVO getCommunicationDetailsVO() {
		return communicationDetailsVO;
	}

	public void setCommunicationDetailsVO(
			CommunicationDetailsVO communicationDetailsVO) {
		this.communicationDetailsVO = communicationDetailsVO;
	}

	public List<SiblingDetailsVO> getSiblingDetailsVO() {
		return siblingDetailsVO;
	}

	public void setSiblingDetailsVO(List<SiblingDetailsVO> siblingDetailsVO) {
		this.siblingDetailsVO = siblingDetailsVO;
	}

	public ParentDetailsVO getParentDetailsVO() {
		return parentDetailsVO;
	}

	public void setParentDetailsVO(ParentDetailsVO parentDetailsVO) {
		this.parentDetailsVO = parentDetailsVO;
	}

	public StudentMasterVO getStudentMasterVO() {
		return studentMasterVO;
	}

	public void setStudentMasterVO(StudentMasterVO studentMasterVO) {
		this.studentMasterVO = studentMasterVO;
	}

	public StudentInfoVO getStudentInfoVO() {
		return studentInfoVO;
	}

	public void setStudentInfoVO(StudentInfoVO studentInfoVO) {
		this.studentInfoVO = studentInfoVO;
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

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}

	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlag) {
		delFlg = delFlag;
	}

	public String getrModId() {
		return rModId;
	}

	public void setrModId(String rModId) {
		this.rModId = rModId;
	}

	public String getrCreId() {
		return rCreId;
	}

	public void setrCreId(String rCreId) {
		this.rCreId = rCreId;
	}


	public UserDetailsForSMS getUserDetailsForSMS() {
		return userDetailsForSMS;
	}


	public void setUserDetailsForSMS(UserDetailsForSMS userDetailsForSMS) {
		this.userDetailsForSMS = userDetailsForSMS;
	}
	
}
