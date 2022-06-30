package com.jaw.communication.controller;

import java.text.ParseException;
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
import com.jaw.common.exceptions.AcadCalFutureDateDeleteFailedException;
import com.jaw.common.exceptions.AcadCalendarDeleteFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForAcaTermHolGenException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.communication.service.INoticeBoardService;
import com.jaw.core.controller.AcademicCalendarMasterVO;
import com.jaw.core.controller.AcademicCalendarSearchVO;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class NoticeBoardController {
	// Logging
		Logger logger = Logger.getLogger(NoticeBoardController.class);
		@Autowired 
		INoticeBoardService noticeBoardService;
	@RequestMapping(value="/noticeBoardList",method = RequestMethod.GET)
	public ModelAndView noticeBoard(
			@ModelAttribute("noticeBoard") NoticeBoardMasterVO noticeBoardMasterVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession) {

		logger.info("Rendering Notice Board List page");
		// Null the session display tag values
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.NOTICE_BOARD_LIST, "noticeBoard",
				noticeBoardMasterVO);
		WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpRequest, "success", null);
		WebUtils.setSessionAttribute(httpRequest, "keepStatus",
				null);
		WebUtils.setSessionAttribute(httpRequest, "searchVo",
				null);
		mav.getModelMap().addAttribute("status", "true");
		return mav;

	}
	
	@RequestMapping(value = "/noticeBoardList", method = RequestMethod.GET, params = { "Get" })
	public String viewNoticeBoardList(
			@ModelAttribute("noticeBoard") NoticeBoardMasterVO noticeBoardMasterVO,
			HttpServletRequest httpRequest,HttpSession session,
			RedirectAttributes redirectAttributes) throws NoDataFoundException {
		// Getting the display tag parameter
				WebUtils.setSessionAttribute(httpRequest, "searchVo",
						noticeBoardMasterVO.getNoticeBoardSearchVO());
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		// Setting model and view for exception handler
		
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.NOTICE_BOARD_LIST, "noticeBoard",
				noticeBoardMasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");
		if (httpRequest.getParameter("Get").equals("Get")) {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					null);
			WebUtils.setSessionAttribute(httpRequest, "success", null);
		}
		else {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					"status");
		}
		// Check whether the list already get or have to fetch from data base
		// using the list size
		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database

			noticeBoardService.selectNoticeBoardList(noticeBoardMasterVO, sessionCache.getUserSessionDetails());

			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					noticeBoardMasterVO.getNoticeBoardVOList());
			noticeBoardMasterVO.setPageSize(noticeBoardMasterVO.getPageSize());
			

		}
		redirectAttributes.addFlashAttribute("noticeBoard",
				noticeBoardMasterVO);
		return "redirect:/noticeBoardList.htm?data";

	}

	@RequestMapping(value = "/noticeBoardList", method = RequestMethod.GET, params = "data")
	public ModelAndView noticeBoardListBack(
			@ModelAttribute("noticeBoard") NoticeBoardMasterVO noticeBoardMasterVO,
			HttpServletRequest httpServletRequest) {
		NoticeBoardSearchVO noticeBoardSearchVO= (NoticeBoardSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");	
		noticeBoardMasterVO.setNoticeBoardSearchVO(noticeBoardSearchVO);				
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.NOTICE_BOARD_LIST, "noticeBoard",
				noticeBoardMasterVO);
		
		return modelAndView;
	}
	
	// Save Method
		@RequestMapping(value = "/noticeBoardList", method = RequestMethod.POST, params = { "actionSave" })
		public String saveNoticeBoard(
				@ModelAttribute("noticeBoard") NoticeBoardMasterVO noticeBoardMasterVO,
				ModelMap model, HttpServletRequest httpServletRequest,
				HttpSession session, RedirectAttributes redirectAttribute)
				throws DuplicateEntryException, DatabaseException,
				 DuplicateEntryForAcaTermHolGenException, NoDataFoundException, UpdateFailedException, DuplicateEntryForHolGenException  {
			NoticeBoardSearchVO noticeBoardSearchVO= (NoticeBoardSearchVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");	
			noticeBoardMasterVO.setNoticeBoardSearchVO(noticeBoardSearchVO);
	httpServletRequest.setAttribute("errors", "erroradd");		
			redirectAttribute.addFlashAttribute("noticeBoard", noticeBoardMasterVO);
			logger.debug("inside save Notice Board method");
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.NOTICE_BOARD_LIST, "noticeBoard",
					noticeBoardMasterVO);
			httpServletRequest.setAttribute("page", modelAndView);
			if (noticeBoardMasterVO.getNoticeBoardVO().getAcTerm().contains(",")) {
				String parts[] = noticeBoardMasterVO.getNoticeBoardVO().getAcTerm()
						.split(",");
				noticeBoardMasterVO.getNoticeBoardVO().setAcTerm(parts[0]); 
			}
			noticeBoardService.insertNoticeBoardDetailsRec(
					noticeBoardMasterVO.getNoticeBoardVO(),
					sessionCache.getUserSessionDetails());		
			WebUtils.setSessionAttribute(httpServletRequest, "success",
					ErrorCodeConstant.ADD_SUCCESS_MESS);
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
			logger.debug("Data's inserted successfully!");
			return "redirect:/noticeBoardList.htm?Get";
		}

		// Get Values from list for popup
		@RequestMapping(value = "/noticeBoardList", method = RequestMethod.GET, params = { "actionGet" })
		public String getNoticeBoard(
				@ModelAttribute("noticeBoard") NoticeBoardMasterVO noticeBoardMasterVO,
				ModelMap model, HttpServletRequest httpServletRequest,
				HttpSession session, RedirectAttributes redirectAttribute) {
			logger.debug("inside Get Notice Board from List method");
			NoticeBoardSearchVO noticeBoardSearchVO= (NoticeBoardSearchVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");	
			noticeBoardMasterVO.setNoticeBoardSearchVO(noticeBoardSearchVO);
			List<NoticeBoardVO> noticeBoardVOs = (List<NoticeBoardVO>) WebUtils
					.getSessionAttribute(httpServletRequest, "display_tbl");			
			
			String id = httpServletRequest.getParameter("rowId");
			noticeBoardMasterVO.setNoticeBoardVO(noticeBoardVOs.get(Integer.parseInt(id)));
			model.addAttribute("popup", "update");
			String keepstat = (String) WebUtils.getSessionAttribute(
					httpServletRequest, "keepStatus");
			System.out.println("Keep status  :" + keepstat);
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.NOTICE_BOARD_LIST, "noticeBoard",
					noticeBoardMasterVO);
			if (keepstat != null) {
				modelAndView.getModelMap().addAttribute("status", "false");
			} else {
				modelAndView.getModelMap().addAttribute("status", "true");
			}
			redirectAttribute.addFlashAttribute("popup", "update");
			redirectAttribute.addFlashAttribute("noticeBoard", noticeBoardMasterVO);
			return "redirect:/noticeBoardList.htm?data";
		}

		// Update method
		@RequestMapping(value = "/noticeBoardList", method = RequestMethod.POST, params = { "actionUpdate" })
		public String updateNoticeBoard(
				@ModelAttribute("noticeBoard") NoticeBoardMasterVO noticeBoardMasterVO,
				ModelMap model, HttpServletRequest httpServletRequest,
				HttpSession session, RedirectAttributes redirectAttribute)
				throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
			NoticeBoardSearchVO noticeBoardSearchVO= (NoticeBoardSearchVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");	
			noticeBoardMasterVO.setNoticeBoardSearchVO(noticeBoardSearchVO);	
	httpServletRequest.setAttribute("errors", "erroradd");		
			redirectAttribute.addFlashAttribute("noticeBoard", noticeBoardMasterVO);
			logger.debug("inside update Notice Board method");
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.NOTICE_BOARD_LIST, "noticeBoard",
					noticeBoardMasterVO);
			httpServletRequest.setAttribute("page", modelAndView);
			noticeBoardService.updateNoticeBoardDetailsRec(noticeBoardMasterVO.getNoticeBoardVO(), sessionCache.getUserSessionDetails(), applicationCache);
			
			
			WebUtils.setSessionAttribute(httpServletRequest, "success",
					ErrorCodeConstant.UPDATE_SUCCESS_MESS);
			logger.debug("Data's updated successfully!");
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
			return "redirect:/noticeBoardList.htm?Get";
		}

		// Delete Method
		@RequestMapping(value = "/noticeBoardList", method = RequestMethod.GET, params = { "actionDelete" })
		public String deleteNoticeBoard(
				@ModelAttribute("noticeBoard") NoticeBoardMasterVO noticeBoardMasterVO,
				ModelMap model, HttpServletRequest httpServletRequest,
				HttpSession session, RedirectAttributes redirectAttribute)
				throws DeleteFailedException, NoDataFoundException, AcadCalendarDeleteFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, AcadCalFutureDateDeleteFailedException, ParseException, DuplicateEntryForHolGenException, UpdateFailedException {
			NoticeBoardSearchVO noticeBoardSearchVO= (NoticeBoardSearchVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");	
			noticeBoardMasterVO.setNoticeBoardSearchVO(noticeBoardSearchVO);	
			redirectAttribute.addFlashAttribute("noticeBoard", noticeBoardMasterVO);
			logger.debug("inside Deleted Notice Board method");
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.NOTICE_BOARD_LIST, "noticeBoard",
					noticeBoardMasterVO);
			httpServletRequest.setAttribute("page", modelAndView);

			httpServletRequest.setAttribute("errors", "erroradd");
			List<NoticeBoardVO> noticeBoardVOs = (List<NoticeBoardVO>) WebUtils
					.getSessionAttribute(httpServletRequest, "display_tbl");			
			
			String id = httpServletRequest.getParameter("rowId");
			noticeBoardMasterVO.setNoticeBoardVO(noticeBoardVOs.get(Integer.parseInt(id)));
			noticeBoardService.deleteNoticeBoardDetailsRec(noticeBoardMasterVO.getNoticeBoardVO(),
					sessionCache.getUserSessionDetails(),applicationCache);			
			WebUtils.setSessionAttribute(httpServletRequest, "success",
					ErrorCodeConstant.DELETE_SUCCESS_MESS);
			logger.debug("Data's deleted successfully!");
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
			return "redirect:/noticeBoardList.htm?Get";
		}
		@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,			
				UpdateFailedException.class, DeleteFailedException.class,
				TableNotSpecifiedForAuditException.class })
		public ModelAndView handleException(Exception ex, HttpSession session,
				HttpServletRequest request) {
			logger.error("Exception Handledddd :" + ex.getMessage());	
			ModelAndView mav = (ModelAndView) request.getAttribute("page");
				String error = (String) request.getAttribute("errors");
			mav.getModelMap().addAttribute(error, ex.getMessage());
			WebUtils.setSessionAttribute(request, "success", null);

			return mav;

		}
		
		
		@ExceptionHandler({ 
			NoDataFoundException.class  })
	public ModelAndView handleNoDataException(Exception ex, HttpSession session,
			HttpServletRequest request) {
			logger.error("Exception Handledddd :" + ex.getMessage());	
			ModelAndView mav = (ModelAndView) request.getAttribute("page");
			String message = (String) WebUtils.getSessionAttribute(request,
					"success");
			if ((message != null)
					&& (message.equals(ErrorCodeConstant.DELETE_SUCCESS_MESS))) {	
			} else {
				System.out.println("inside non  delete message");
				WebUtils.setSessionAttribute(request, "success", null);
				mav.getModelMap().addAttribute("error", ex.getMessage());
			}
			

		WebUtils.setSessionAttribute(request, "display_tbl", null);
		return mav;
	}
	
}
