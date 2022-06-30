package com.jaw.mark.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.AllMarksNotEnteredException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mark.service.IMarkMasterService;
import com.jaw.mark.service.IReportCardRemarksService;
import com.jaw.mark.service.IViewReportCardService;

//Mark Master Controller Class
@Controller
public class ReportCardRemarksController {
	// Logging
	Logger logger = Logger.getLogger(ReportCardRemarksController.class);
	@Autowired
	IMarkMasterService markMasterService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IReportCardRemarksService reportCardRemarksService;
	@Autowired
	IViewReportCardService viewReportCardService;

	@RequestMapping(value = { "/rcAddRemarks", "/rcUpdateRemarks.htm" }, method = RequestMethod.GET)
	public ModelAndView getReportCardRemarks(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws NoDataFoundException, AllMarksNotEnteredException {

		logger.debug("inside Get Mark Master Details from List method");
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl1", null);
		List<MarkMasterVO> markMasterVOs = (List<MarkMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		MarkMasterSearchVO markMasterSearchVO = (MarkMasterSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		ReportCardMasterVo.setMarkMasterSearchVo(markMasterSearchVO);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String id = httpServletRequest.getParameter("rowId");
		String process = httpServletRequest.getParameter("process");
		System.out.println("Row In from jsp:" + id);

		ReportCardMasterVo.setMarkMasterVO(markMasterVOs.get(Integer
				.parseInt(id)));
		if (process != null) {
			if (process.equals("add")) {
				WebUtils.setSessionAttribute(httpServletRequest, "process",
						"add");
				viewReportCardService.getStudentAdmisNo(ReportCardMasterVo,
						sessionCache.getUserSessionDetails(), "Add");
			} else if (process.equals("update")) {
				WebUtils.setSessionAttribute(httpServletRequest, "process",
						"update");
				viewReportCardService.getStudentAdmisNo(ReportCardMasterVo,
						sessionCache.getUserSessionDetails(), "update");
			}
		}
		WebUtils.setSessionAttribute(httpServletRequest, "studentAdmisNoList",
				ReportCardMasterVo.getStudentAdmisNoMap());

		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.REPORT_CARD_REMARK, "markMaster",
				ReportCardMasterVo);
		httpServletRequest.setAttribute("page", modelAndView);

		WebUtils.setSessionAttribute(httpServletRequest, "sessObj",
				ReportCardMasterVo.getMarkMasterVO());
		System.out.println("Mark master :"
				+ ReportCardMasterVo.getMarkMasterVO().toString());

		reportCardRemarksService.selectStuMarksStatus(ReportCardMasterVo,
				sessionCache.getUserSessionDetails());
		ReportCardMasterVo.getStuMrksRmksVO().setButton("label");
		ReportCardMasterVo.getStuMrksRmksVO().setAction(process);
		redirectAttribute.addFlashAttribute("markMaster", ReportCardMasterVo);
		return modelAndView;
	}

	@RequestMapping(value = "/rcAddRemarks", method = RequestMethod.GET, params = { "GetRemarks" })
	public ModelAndView getListForRemarks(

	@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			HttpServletRequest httpRequest,
			RedirectAttributes redirectAttributes, HttpSession session)
			throws NoDataFoundException {
		WebUtils.setSessionAttribute(httpRequest, "display_tbl1", null);
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		String var = httpRequest.getParameter("GetRemarks");
		// Setting model and view for exception handler

		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.REPORT_CARD_REMARK, "markMaster",
				ReportCardMasterVo);
		httpRequest.setAttribute("page", modelAndView);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		// if (stockParamMap.size() == 0) {

		logger.info("Table operation has been triggered");
		MarkMasterSearchVO markMasterSearchVO = (MarkMasterSearchVO) WebUtils
				.getSessionAttribute(httpRequest, "searchVo");

		ReportCardMasterVo.setMarkMasterSearchVo(markMasterSearchVO);

		Map<String, String> stuAdmisNoList = (Map<String, String>) WebUtils
				.getSessionAttribute(httpRequest, "studentAdmisNoList");
		List<StuDetailsListForRemarksVO> stuDetailsListForRemarksVO = (List<StuDetailsListForRemarksVO>) WebUtils
				.getSessionAttribute(httpRequest, "admisno");
		ReportCardMasterVo
				.setStuDetailsListForRemarksVOs(stuDetailsListForRemarksVO);
		MarkMasterVO markMasterVO = (MarkMasterVO) WebUtils
				.getSessionAttribute(httpRequest, "sessObj");
		ReportCardMasterVo.setMarkMasterVO(markMasterVO);
		System.out.println("mark master vo :"
				+ ReportCardMasterVo.getMarkMasterVO().toString());
		reportCardRemarksService.selectMarkListForRmksAdd(ReportCardMasterVo,
				sessionCache.getUserSessionDetails(), var);
		// Set the list to session to access in jsp

		WebUtils.setSessionAttribute(httpRequest, "display_tbl1",
				ReportCardMasterVo.getStudentReportCardVOs());
		ReportCardMasterVo.setPageSize(ReportCardMasterVo.getPageSize());

		if ((ReportCardMasterVo.getStuDetailsListForRemarksVOs() != null)
				&& (ReportCardMasterVo.getStuDetailsListForRemarksVOs().size() != 0)) {
			WebUtils.setSessionAttribute(httpRequest, "admisno",
					ReportCardMasterVo.getStuDetailsListForRemarksVOs());
		}
		redirectAttributes.addFlashAttribute("markMaster", ReportCardMasterVo);
		System.out.println("admis no:"
				+ ReportCardMasterVo.getStuMrksRmksVO().getStudentAdmisNo());
		ReportCardMasterVo.setStudentAdmisNoMap(stuAdmisNoList);
		System.out.println("student repordcard vo size:"
				+ ReportCardMasterVo.getStudentReportCardVOs().size());
		// return "redirect:/rcAddRemarks.htm?data";
		return modelAndView;
	}

	

	@RequestMapping(value = "/rcAddRemarks", method = RequestMethod.GET, params = { "SaveRemarks" })
	public String SaveReportCardRemarks(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws NoDataFoundException, AllMarksNotEnteredException,
			DuplicateEntryException, UpdateFailedException, DatabaseException,
			TableNotSpecifiedForAuditException {

		logger.debug("inside Get Mark Master Details from List method");
		System.out.println("admis no :"
				+ ReportCardMasterVo.getStuMrksRmksVO().getStudentAdmisNo());
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.REPORT_CARD_REMARK, "markMaster",
				ReportCardMasterVo);
		httpServletRequest.setAttribute("page", modelAndView);

		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		MarkMasterVO markMasterVO = (MarkMasterVO) WebUtils
				.getSessionAttribute(httpServletRequest, "sessObj");
		ReportCardMasterVo.setMarkMasterVO(markMasterVO);
		List<StuDetailsListForRemarksVO> stuDetailsListForRemarksVO = (List<StuDetailsListForRemarksVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "admisno");
		ReportCardMasterVo
				.setStuDetailsListForRemarksVOs(stuDetailsListForRemarksVO);
		reportCardRemarksService.insertStudentExamRemarks(ReportCardMasterVo,
				sessionCache.getUserSessionDetails(), applicationCache);
		Map<String, String> stuAdmisNoList = (Map<String, String>) WebUtils
				.getSessionAttribute(httpServletRequest, "studentAdmisNoList");
		ReportCardMasterVo.setStudentAdmisNoMap(stuAdmisNoList);
		redirectAttribute.addFlashAttribute("markMaster", ReportCardMasterVo);
		redirectAttribute.addFlashAttribute("success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		return "redirect:/rcAddRemarks.htm?data";
	}

	@RequestMapping(value = "/rcAddRemarks", method = RequestMethod.GET, params = { "UpdateRemarks" })
	public String updateReportCardRemarks(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws NoDataFoundException, AllMarksNotEnteredException,
			DuplicateEntryException, UpdateFailedException, DatabaseException,
			TableNotSpecifiedForAuditException {

		logger.debug("inside Get Mark Master Details from List method");
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.REPORT_CARD_REMARK, "markMaster",
				ReportCardMasterVo);
		httpServletRequest.setAttribute("page", modelAndView);

		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		MarkMasterVO markMasterVO = (MarkMasterVO) WebUtils
				.getSessionAttribute(httpServletRequest, "sessObj");
		ReportCardMasterVo.setMarkMasterVO(markMasterVO);
		Map<String, String> stuAdmisNoList = (Map<String, String>) WebUtils
				.getSessionAttribute(httpServletRequest, "studentAdmisNoList");
		ReportCardMasterVo.setStudentAdmisNoMap(stuAdmisNoList);
		reportCardRemarksService.updateStudentExamRemarks(ReportCardMasterVo,
				sessionCache.getUserSessionDetails(), applicationCache);

		redirectAttribute.addFlashAttribute("markMaster", ReportCardMasterVo);
		redirectAttribute.addFlashAttribute("success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);

		return "redirect:/rcAddRemarks.htm?data";
	}

	@RequestMapping(value = "/rcAddRemarks", method = RequestMethod.GET, params = {
			"data", "!SaveRemarks", "!GetRemarks", "!UpdateRemarks" })
	public ModelAndView addRemarkData(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.REPORT_CARD_REMARK, "markMaster",
				ReportCardMasterVo);

		return modelAndView;
	}

	@ExceptionHandler({ NoDataFoundException.class })
	public ModelAndView handleException1(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		WebUtils.setSessionAttribute(request, "success", null);
		return modelAndView;
	}
}
