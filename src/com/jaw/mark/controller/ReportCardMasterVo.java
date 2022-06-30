package com.jaw.mark.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jaw.student.admission.controller.StudentMasterVO;

public class ReportCardMasterVo implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(ReportCardMasterVo.class);

	// Properties
	
	MarkMasterSearchVO markMasterSearchVo = new MarkMasterSearchVO();
	private List<StuMrksRmksListVO> stuMrksRmksListVOs = new ArrayList<StuMrksRmksListVO>();
	private List<StuDetailsListForRemarksVO> stuDetailsListForRemarksVOs = new ArrayList<StuDetailsListForRemarksVO>();
	private List<StudentReportCardVO> studentReportCardVOs = new ArrayList<StudentReportCardVO>();
	private List<StudentReportCardVO> consolidatedreportCard = new ArrayList<StudentReportCardVO>();
	private StudentMasterVO studentMasterVO = new StudentMasterVO();
	private MarkMasterVO markMasterVO = new MarkMasterVO();
	private List<MarkMasterVO> markMasterVOs = new ArrayList<MarkMasterVO>();
	private Map<String,String> studentAdmisNoMap = new LinkedHashMap<String,String>();
	private List<List<StudentReportCardVO>> studentReportCardVOsList = new ArrayList<List<StudentReportCardVO>>();
	StudentReportCardVO studentReportCardVO=new StudentReportCardVO();
	private List<StudentMasterVO> studentMasterVOs = new ArrayList<StudentMasterVO>();
	
	public List<StudentMasterVO> getStudentMasterVOs() {
		return studentMasterVOs;
	}

	public void setStudentMasterVOs(List<StudentMasterVO> studentMasterVOs) {
		this.studentMasterVOs = studentMasterVOs;
	}

	public StudentReportCardVO getStudentReportCardVO() {
		return studentReportCardVO;
	}

	public void setStudentReportCardVO(StudentReportCardVO studentReportCardVO) {
		this.studentReportCardVO = studentReportCardVO;
	}

	public List<List<StudentReportCardVO>> getStudentReportCardVOsList() {
		return studentReportCardVOsList;
	}

	public void setStudentReportCardVOsList(
			List<List<StudentReportCardVO>> studentReportCardVOsList) {
		this.studentReportCardVOsList = studentReportCardVOsList;
	}

	private String pageSize = "10";
	
	public Map<String, String> getStudentAdmisNoMap() {
		return studentAdmisNoMap;
	}

	public void setStudentAdmisNoMap(Map<String, String> studentAdmisNoMap) {
		this.studentAdmisNoMap = studentAdmisNoMap;
	}

	public List<MarkMasterVO> getMarkMasterVOs() {
		return markMasterVOs;
	}

	public void setMarkMasterVOs(List<MarkMasterVO> markMasterVOs) {
		this.markMasterVOs = markMasterVOs;
	}

	public MarkMasterVO getMarkMasterVO() {
		return markMasterVO; 
	}

	public void setMarkMasterVO(MarkMasterVO markMasterVO) {
		this.markMasterVO = markMasterVO;
	}

	public StudentMasterVO getStudentMasterVO() {
		return studentMasterVO;
	}

	public void setStudentMasterVO(StudentMasterVO studentMasterVO) {
		this.studentMasterVO = studentMasterVO;
	}

	public List<StudentReportCardVO> getConsolidatedreportCard() {
		return consolidatedreportCard;
	}

	public void setConsolidatedreportCard(
			List<StudentReportCardVO> consolidatedreportCard) {
		this.consolidatedreportCard = consolidatedreportCard;
	}

	public List<StudentReportCardVO> getStudentReportCardVOs() {
		return studentReportCardVOs;
	}

	public void setStudentReportCardVOs(
			List<StudentReportCardVO> studentReportCardVOs) {
		this.studentReportCardVOs = studentReportCardVOs;
	}

	private StuMrksRmksVO stuMrksRmksVO = new StuMrksRmksVO();


	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public MarkMasterSearchVO getMarkMasterSearchVo() {
		return markMasterSearchVo;
	}

	public void setMarkMasterSearchVo(MarkMasterSearchVO markMasterSearchVo) {
		this.markMasterSearchVo = markMasterSearchVo;
	}

	public List<StuMrksRmksListVO> getStuMrksRmksListVOs() {
		return stuMrksRmksListVOs;
	}

	public void setStuMrksRmksListVOs(List<StuMrksRmksListVO> stuMrksRmksListVOs) {
		this.stuMrksRmksListVOs = stuMrksRmksListVOs;
	}

	public List<StuDetailsListForRemarksVO> getStuDetailsListForRemarksVOs() {
		return stuDetailsListForRemarksVOs;
	}

	public void setStuDetailsListForRemarksVOs(
			List<StuDetailsListForRemarksVO> stuDetailsListForRemarksVOs) {
		this.stuDetailsListForRemarksVOs = stuDetailsListForRemarksVOs;
	}

	public StuMrksRmksVO getStuMrksRmksVO() {
		return stuMrksRmksVO;
	}

	public void setStuMrksRmksVO(StuMrksRmksVO stuMrksRmksVO) {
		this.stuMrksRmksVO = stuMrksRmksVO;
	}

}
