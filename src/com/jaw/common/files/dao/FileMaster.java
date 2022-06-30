package com.jaw.common.files.dao;
import java.io.Serializable;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.jaw.common.constants.AuditConstant;

//Pojo class for Files
public class FileMaster implements Serializable{
		

		// Logging
		static Logger logger = Logger.getLogger(FileMaster.class);
	
	//Properties
	private Integer dbTs;
	private String instId ;
	private String branchId ;
	private String fileSrlno ;
	private String fileName ="" ;
	private String filepath ="";
	
	public String getFileSrlno() {
		return fileSrlno;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public void setFileSrlno(String fileSrlno) {
		this.fileSrlno = fileSrlno;
	}	
	private String linkId ;
	private String fileRefno ="";
	private String fileType ;
	private String contentType = "";
	private InputStream inputStream ;
	private String delFlg = "";
	private String rModId = "";
	private String rModTime ;
	private String rCreId = "";
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}

	private MultipartFile file ;
	public String getFileRefno() {
		return fileRefno;
	}
	public void setFileRefno(String fileRefno) {
		this.fileRefno = fileRefno;
	}

	private String rCreTime ;
	private Long size;				
	
	//Getters and Setters		
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
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
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
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String studentAdmisNo) {
		this.linkId = studentAdmisNo;
	}	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
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
	public String getrCreId() {
		return rCreId;
	}
	public void setrCreId(String rCreId) {
		this.rCreId = rCreId;
	}
	
	// method for create InstituteMaster Record for Audit
		public String toStringForAuditFileMasterRecord() {
			
			StringBuffer stringBuffer = new StringBuffer().append("DB_TS=")						
					.append(getDbTs())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("INST_ID=").append(getInstId())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("BRANCH_ID=").append(getBranchId())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("LINK_ID=").append(getLinkId())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("FILE_REFNO=").append(getFileRefno())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("FILE=").append(getInputStream()+","+getSize())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("FILE_TYPE=").append(getFileType())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("DEL_FLG=").append(getDelFlg())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)				
					.append("R_MOD_ID=").append(getrModId())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_MOD_TIME=").append(getrModTime())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_CRE_ID=").append(getrCreId())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_CRE_TIME=").append(getrCreTime())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("CONTENT_TYPE=").append(getContentType())
					.append(AuditConstant.AUDIT_REC_SEPERATOR);

			logger.debug("String formed for audit is :" + stringBuffer.toString());
			
			return stringBuffer.toString();
		}
		
		@Override
		public String toString() {
			return "FileMaster [dbTs=" + dbTs + ", instId=" + instId
					+ ", branchId=" + branchId + ", fileSrlno=" + fileSrlno
					+ ", fileName=" + fileName + ", filepath=" + filepath
					+ ", linkId=" + linkId + ", fileRefno=" + fileRefno
					+ ", fileType=" + fileType + ", contentType=" + contentType
					+ ", inputStream=" + inputStream + ", delFlg=" + delFlg
					+ ", rModId=" + rModId + ", rModTime=" + rModTime
					+ ", rCreId=" + rCreId + ", file=" + file + ", rCreTime="
					+ rCreTime + ", size=" + size + "]";
		}
		// method for create FileMasterKey String for Audit
		public String toStringForAuditFileMasterKey() {
			
			StringBuffer stringBuffer = new StringBuffer()
			.append("INST_ID=")
			.append(getInstId())
			.append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=")
			.append(getBranchId())
			.append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("LINK_ID=")
			.append(getLinkId())
			.append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("FILE_TYPE=")
			.append(getFileType())
			.append(AuditConstant.AUDIT_REC_SEPERATOR);
			return stringBuffer.toString();
		}
		
		
	
	
}
