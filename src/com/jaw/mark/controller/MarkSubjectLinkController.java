package com.jaw.mark.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.jaw.common.exceptions.CustomDuplicateEntryException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.MainTestNotAddedFirstException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mark.service.IMarkSubjectLinkService;

//Mark Subject Details Controller Class
@Controller
public class MarkSubjectLinkController {
	// Logging
	Logger logger = Logger.getLogger(MarkSubjectLinkController.class);
	@Autowired
	IMarkSubjectLinkService markSubjectLinkService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;

	@RequestMapping(value = "/markSubjectLink", method = RequestMethod.GET)
	public ModelAndView getMarkSubjectLink(
			@ModelAttribute("markSubjectLink") MarkSubjectLinkMasterVO markSubjectLinkMasterVO,
			ModelMap modelMap, HttpServletRequest httpServletRequest) {

		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.MARK_SUBJECT_LINK, "markSubjectLink",
				markSubjectLinkMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);

		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", null);
		return mav;
	}

	@RequestMapping(value = "/markSubjectLink", method = RequestMethod.GET, params = "Get")
	public String viewMarkSubjectDetailsList(
			@ModelAttribute("markSubjectLink") MarkSubjectLinkMasterVO markSubjectLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException,
			NoDataFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		// Getting the display tag parameter
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo",
				markSubjectLinkMasterVO.getMarkSubjectLinkSearchVO());
		Map stockParamMap = WebUtils.getParametersStartingWith(
				httpServletRequest, "d-");

		// Setting model and view for exception handler

		httpServletRequest.setAttribute("markSubjectLink",
				markSubjectLinkMasterVO); // Check whether the
											// list already get
											// or have to fetch
											// from data base
		// using the list size

		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database

			try {
				markSubjectLinkService.selectMarkSubjectLinkingData(
						markSubjectLinkMasterVO,
						sessionCache.getUserSessionDetails());
			} catch (NoDataFoundException e) {
				WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
						null);
				throw new NoDataFoundException();
			}

			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
					markSubjectLinkMasterVO.getMarkSubjectLinkListVOs());
			/*
			 * MarkSubjectLinkMasterVO.getMarkSubjectLinkSearchVO().setPageSize(
			 * MarkSubjectLinkMasterVO.getMarkSubjectLinkSearchVO()
			 * .getPageSize());
			 */

		}
		if (httpServletRequest.getParameter("Get").equals("Get")) {
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", null);
		} else {
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus",
					"status");
		}
		redirectAttribute.addFlashAttribute("markSubjectLink",
				markSubjectLinkMasterVO);
		return "redirect:/markSubjectLink.htm?data";
	}

	@RequestMapping(value = "/markSubjectLink", method = RequestMethod.GET, params = {
			"data", "!actionSave", "!actionUpdate", "!actionDelete" })
	public ModelAndView getMarkSubjectDetailsList(
			@ModelAttribute("markSubjectLink") MarkSubjectLinkMasterVO markSubjectLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException,
			NoDataFoundException {
		MarkSubjectLinkSearchVO markSubjectLinkSearchVO = (MarkSubjectLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		markSubjectLinkMasterVO
				.setMarkSubjectLinkSearchVO(markSubjectLinkSearchVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.MARK_SUBJECT_LINK, "markSubjectLink",
				markSubjectLinkMasterVO);
		return modelAndView;
	}

	@RequestMapping(value = "/markSubjectLink", method = RequestMethod.GET, params = { "actionSave" })
	public String addMarkSubjectLink(
			@ModelAttribute("markSubjectLink") MarkSubjectLinkMasterVO markSubjectLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DatabaseException, CustomDuplicateEntryException,
			MainTestNotAddedFirstException {

		logger.debug("inside save Mark Subject Linking method");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		httpServletRequest.setAttribute("markSubjectLink",
				markSubjectLinkMasterVO);
		MarkSubjectLinkSearchVO markSubjectLinkSearchVO = (MarkSubjectLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		markSubjectLinkMasterVO
				.setMarkSubjectLinkSearchVO(markSubjectLinkSearchVO);
		List<MarkSubjectLinkListVO> markSubjectLinkListVOs = (List<MarkSubjectLinkListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		logger.debug("term Id :"
				+ markSubjectLinkMasterVO.getMarkSubjectLinkVO().getAcTerm());
		String subTest = httpServletRequest.getParameter("subTest");
		String labBatch = httpServletRequest.getParameter("labBatch");
		String examDate = httpServletRequest.getParameter("examDate");
		String minMarks = httpServletRequest.getParameter("minMarks");
		String maxMarks = httpServletRequest.getParameter("maxMarks");
		String remarks = httpServletRequest.getParameter("remarks");
		String details = httpServletRequest.getParameter("details");
		String crslId = httpServletRequest.getParameter("crslId");
		String examType = httpServletRequest.getParameter("examType");

		String duration = httpServletRequest.getParameter("duration");
		String startTime = httpServletRequest.getParameter("startTime");
		MarkSubjectLinkVO markSubjectLinkVO = markSubjectLinkMasterVO
				.getMarkSubjectLinkVO();
		markSubjectLinkVO.setSubTestId(subTest);
		markSubjectLinkVO.setLabBatch(labBatch);
		markSubjectLinkVO.setExamDate(examDate);
		markSubjectLinkVO.setMinMark(minMarks);
		markSubjectLinkVO.setMaxMark(maxMarks);
		markSubjectLinkVO.setRemarks(remarks);
		markSubjectLinkVO.setSubPortionsDetails(details);
		markSubjectLinkVO.setCrslId(crslId);
		markSubjectLinkVO.setExamType(examType);

		markSubjectLinkVO.setDuration(duration);
		markSubjectLinkVO.setStartTime(startTime);
		markSubjectLinkMasterVO.setMarkSubjectLinkVO(markSubjectLinkVO);

		System.out.println("markSubjectLinkMasterVO to string :"
				+ markSubjectLinkMasterVO.getMarkSubjectLinkVO().toString());

		if (subTest != null && (!subTest.equals("undefined"))) {
			System.out.println("b4 calling method");
			try {
				markSubjectLinkService.insertMarkSubjectLinkRec(
						markSubjectLinkMasterVO,
						sessionCache.getUserSessionDetails());
			} catch (DuplicateEntryException e) {
				/*
				 * System.out.println("inside catch"); String id =
				 * httpServletRequest.getParameter("rowId");
				 * System.out.println("Id in conytroller :"+id);
				 * markSubjectLinkListVOs.add(Integer.parseInt(id)+1,
				 * markSubjectLinkMasterVO.getMarkSubjectLinkListVO());
				 */
				httpServletRequest.setAttribute("parameter",
						markSubjectLinkSearchVO.getAcTerm() + ","
								+ markSubjectLinkSearchVO.getStudentGrpId()
								+ "," + markSubjectLinkSearchVO.getExamId()
								+ "," + markSubjectLinkVO.getCrslId() + ","
								+ markSubjectLinkVO.getExamType() + ","
								+ markSubjectLinkVO.getSubTestId() + ","
								+ markSubjectLinkVO.getLabBatch());
				throw new CustomDuplicateEntryException();
			}
		}

		markSubjectLinkMasterVO.setMarkSubjectLinkVO(null);

		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		logger.debug("Data's inserted successfully!");
		redirectAttribute.addFlashAttribute("markSubjectLink",
				markSubjectLinkMasterVO);
		return "redirect:/markSubjectLink.htm?Get";
	}

	@RequestMapping(value = "/markSubjectLink", method = RequestMethod.GET, params = { "actionDelete" })
	public String deleteMarkSubjectLink(
			@ModelAttribute("markSubjectLink") MarkSubjectLinkMasterVO markSubjectLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		logger.debug("inside Delete Mark subject liking method");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		List<MarkSubjectLinkListVO> MarkSubjectLinkVOs = (List<MarkSubjectLinkListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		MarkSubjectLinkSearchVO markSubjectLinkSearchVO = (MarkSubjectLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		markSubjectLinkMasterVO
				.setMarkSubjectLinkSearchVO(markSubjectLinkSearchVO);
		String id = httpServletRequest.getParameter("rowId");
		MarkSubjectLinkListVO markSubjectLinkListVO = MarkSubjectLinkVOs
				.get(Integer.parseInt(id));

		httpServletRequest.setAttribute("markSubjectLink",
				markSubjectLinkMasterVO);

		markSubjectLinkService.deleteMarkSubjectLinkDAORec(
				markSubjectLinkListVO, sessionCache.getUserSessionDetails(),
				applicationCache);

		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		logger.debug("Data's deleted successfully!");
		redirectAttribute.addFlashAttribute("markSubjectLink",
				markSubjectLinkMasterVO);
		return "redirect:/markSubjectLink.htm?Get";
	}

	@RequestMapping(value = "/markSubjectLink", method = RequestMethod.GET, params = { "actionUpdate" })
	public String updateAcademicTerDetails(
			@ModelAttribute("markSubjectLink") MarkSubjectLinkMasterVO markSubjectLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, MainTestNotAddedFirstException {

		logger.debug("inside update Mark subject liking method");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		httpServletRequest.setAttribute("markSubjectLink",
				markSubjectLinkMasterVO);
		List<MarkSubjectLinkListVO> MarkSubjectLinkVOs = (List<MarkSubjectLinkListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		MarkSubjectLinkSearchVO markSubjectLinkSearchVO = (MarkSubjectLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		markSubjectLinkMasterVO

		.setMarkSubjectLinkSearchVO(markSubjectLinkSearchVO);
		String subTest = httpServletRequest.getParameter("subTest");
		String labBatch = httpServletRequest.getParameter("labBatch");
		String examDate = httpServletRequest.getParameter("examDate");
		String minMarks = httpServletRequest.getParameter("minMarks");
		String maxMarks = httpServletRequest.getParameter("maxMarks");
		String remarks = httpServletRequest.getParameter("remarks");
		String details = httpServletRequest.getParameter("details");
		String crslId = httpServletRequest.getParameter("crslId");
		String examType = httpServletRequest.getParameter("examType");
		String duration = httpServletRequest.getParameter("duration");
		String startTime = httpServletRequest.getParameter("startTime");
		String id = httpServletRequest.getParameter("rowId");
		MarkSubjectLinkListVO markSubjectLinkListVO = MarkSubjectLinkVOs
				.get(Integer.parseInt(id));
		MarkSubjectLinkVO markSubjectLinkVO = new MarkSubjectLinkVO();
		markSubjectLinkVO.setExamDate(examDate);
		markSubjectLinkVO.setMinMark(minMarks);
		markSubjectLinkVO.setMaxMark(maxMarks);
		markSubjectLinkVO.setRemarks(remarks);
		markSubjectLinkVO.setSubPortionsDetails(details);
		markSubjectLinkVO.setSubTestId(subTest);
		markSubjectLinkVO.setLabBatch(labBatch);
		markSubjectLinkVO.setCrslId(crslId);
		markSubjectLinkVO.setExamType(examType);
		markSubjectLinkVO.setDuration(duration);
		markSubjectLinkVO.setStartTime(startTime);
		markSubjectLinkMasterVO.setMarkSubjectLinkVO(markSubjectLinkVO);
		markSubjectLinkMasterVO.setMarkSubjectLinkListVO(markSubjectLinkListVO);
		System.out.println("MarkSubjectLinkVO to string :"
				+ markSubjectLinkVO.toString());
		markSubjectLinkService.updateMarkSubjectLinking(
				markSubjectLinkMasterVO, sessionCache.getUserSessionDetails(),
				applicationCache);
		redirectAttribute.addFlashAttribute("markSubjectLink",
				markSubjectLinkMasterVO);

		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		logger.debug("Data's updated successfully!");

		return "redirect:/markSubjectLink.htm?Get";
	}

	@ModelAttribute("acTermList")
	public Map<String, String> gerAcademicTermList(
			HttpSession session,
			@ModelAttribute("markSubjectLink") MarkSubjectLinkMasterVO markSubjectLinkMasterVO,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException, NoDataFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		httpSevletRequest.setAttribute("markSubjectLink",
				markSubjectLinkMasterVO);
		Map<String, String> map = dropDownListService
				.getAcademicTermListTag(sessionCache.getUserSessionDetails());
		httpSevletRequest.setAttribute("acTermList", map);
		return map;

	}

	@ModelAttribute("studentGrpList")
	public Map<String, String> getStudentGroupList(
			HttpSession session,

			@ModelAttribute("markSubjectLink") MarkSubjectLinkMasterVO markSubjectLinkMasterVO,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException, NoDataFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		httpSevletRequest.setAttribute("markSubjectLink",
				markSubjectLinkMasterVO);
		Map<String, String> map = dropDownListService
				.getStGroupModelAttr(sessionCache.getUserSessionDetails());
		httpSevletRequest.setAttribute("studentGrpList", map);
		return map;

	}

	@ExceptionHandler({ DuplicateEntryException.class,
			DeleteFailedException.class, UpdateFailedException.class,
			NoDataFoundException.class, CommonCodeNotFoundException.class,
			DatabaseException.class, TableNotSpecifiedForAuditException.class,
			MainTestNotAddedFirstException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		WebUtils.setSessionAttribute(request, "success", null);
		MarkSubjectLinkMasterVO markSubjectLinkMasterVO = (MarkSubjectLinkMasterVO) request
				.getAttribute("markSubjectLink");
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.MARK_SUBJECT_LINK, "markSubjectLink",
				markSubjectLinkMasterVO);
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		return modelAndView;

	}

	@ExceptionHandler({ CustomDuplicateEntryException.class })
	public ModelAndView handleException1(Exception ex, HttpSession session,
			HttpServletRequest request) {
		WebUtils.setSessionAttribute(request, "success", null);
		MarkSubjectLinkMasterVO markSubjectLinkMasterVO = (MarkSubjectLinkMasterVO) request
				.getAttribute("markSubjectLink");
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.MARK_SUBJECT_LINK, "markSubjectLink",
				markSubjectLinkMasterVO);
		String errorParam = (String) request.getAttribute("parameter");
		modelAndView.getModelMap().addAttribute("errorParam", errorParam);
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		return modelAndView;

	}

}
