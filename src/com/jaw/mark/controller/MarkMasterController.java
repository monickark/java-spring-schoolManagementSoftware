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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.AcadCalendarDeleteFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.ExamOrderExistException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.AcademicCalendarMasterVO;
import com.jaw.core.controller.AcademicCalendarSearchVO;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.controller.SpecialClassMasterVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mark.service.IMarkMasterService;
//Mark Master Controller Class
@Controller
public class MarkMasterController {
	// Logging
		Logger logger = Logger.getLogger(MarkMasterController.class);
		@Autowired
		IMarkMasterService markMasterService;
		@Autowired
		IDropDownListService dropDownListService;

		// Academic Calendar List Get method

		@RequestMapping(value = "/markMasterList", method = RequestMethod.GET)
		public ModelAndView markMaster(
				@ModelAttribute("markMaster") MarkMtrMasterVO markMtrMasterVO,
				ModelMap model, HttpServletRequest httpRequest,
				HttpSession httpSession) {

			logger.info("Rendering Mark Master List page");
			ModelAndView mav = new ModelAndView(
					ModelAndViewConstant.MARK_MASTER_LIST, "markMaster",
					markMtrMasterVO);
			// Null the session display tag values

			WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
			WebUtils.setSessionAttribute(httpRequest, "success", null);
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					null);
			WebUtils.setSessionAttribute(httpRequest, "searchVo",
					null);
			mav.getModelMap().addAttribute("status", "true");
			return mav;

		}
		@RequestMapping(value = "/markMasterList", method = RequestMethod.GET, params = { "Get" })
		public String viewMarkMasterList(
				@ModelAttribute("markMaster") MarkMtrMasterVO markMtrMasterVO,
				HttpServletRequest httpRequest,HttpSession session,RedirectAttributes redirectAttributes) throws NoDataFoundException {
			// Getting the display tag parameter
				/*	WebUtils.setSessionAttribute(httpRequest, "searchVo",
							specialClassMasterVO);*/
			Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
					"d-");
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			// Setting model and view for exception handler

			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.MARK_MASTER_LIST, "markMaster",
					markMtrMasterVO);
			httpRequest.setAttribute("page", modelAndView);
			httpRequest.setAttribute("errors", "error");
			// Check whether the list already get or have to fetch from data base
			// using the list size
			if (httpRequest.getParameter("Get").equals("Get")) {
				WebUtils.setSessionAttribute(httpRequest, "keepStatus",
						null);
				WebUtils.setSessionAttribute(httpRequest, "success", null);
			}
			else {
				WebUtils.setSessionAttribute(httpRequest, "keepStatus",
						"status");
			}
			if (stockParamMap.size() == 0) {

				logger.info("Table operation has been triggered");

				// Get list from database				
				markMasterService.selectmarkMasterList(markMtrMasterVO,sessionCache.getUserSessionDetails());
				// Set the list to session to access in jsp

				WebUtils.setSessionAttribute(httpRequest, "display_tbl",
						markMtrMasterVO.getMarkMasterVOs());
				WebUtils.setSessionAttribute(httpRequest, "searchVo",
						markMtrMasterVO.getMarkMasterSearchVo());
				markMtrMasterVO.setPageSize(markMtrMasterVO.getPageSize());

			}
			redirectAttributes.addFlashAttribute("markMaster",
					markMtrMasterVO);
			return "redirect:/markMasterList.htm?data";

		}

		@RequestMapping(value = "/markMasterList", method = RequestMethod.GET, params = "data")
		public ModelAndView markMasterBack(
				@ModelAttribute("markMaster") MarkMtrMasterVO markMtrMasterVO,
				HttpServletRequest httpServletRequest) {
			MarkMasterSearchVO markMasterSearchVO= (MarkMasterSearchVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");		
			markMtrMasterVO.setMarkMasterSearchVo(markMasterSearchVO)	;
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.MARK_MASTER_LIST, "markMaster",
					markMtrMasterVO);
			return modelAndView;
		}
		
		
		
		
		// Save Method
		@RequestMapping(value = "/markMasterList", method = RequestMethod.POST, params = { "actionSave" })
		public String saveMarkMaster(
				@ModelAttribute("markMaster") MarkMtrMasterVO markMtrMasterVO,
				ModelMap model, HttpServletRequest httpServletRequest,
				HttpSession session, RedirectAttributes redirectAttribute)
				throws DuplicateEntryException, DatabaseException, ExamOrderExistException
				{
			
			logger.debug("inside save mark Master method");
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.MARK_MASTER_LIST, "markMaster",
					markMtrMasterVO);
			httpServletRequest.setAttribute("page", modelAndView);
			System.out.println("values in save"+markMtrMasterVO.getMarkMasterVO().getmMSeqId());
			/*if (academicCalendarMasterVO.getAcadClVO().getAcTerm().contains(",")) {
				String parts[] = academicCalendarMasterVO.getAcadClVO().getAcTerm()
						.split(",");
				academicCalendarMasterVO.getAcadClVO().setAcTerm(parts[0]); 
			}*/
			if (markMtrMasterVO.getMarkMasterVO().getStudentGrpId().contains(",")) {
				String parts[] = markMtrMasterVO.getMarkMasterVO().getStudentGrpId()
						.split(",");
				markMtrMasterVO.getMarkMasterVO().setStudentGrpId(parts[0]); 
			}
			httpServletRequest.setAttribute("errors", "erroradd");
			markMasterService.insertMarkMasterRec(markMtrMasterVO.getMarkMasterVO(), sessionCache.getUserSessionDetails());			
			WebUtils.setSessionAttribute(httpServletRequest, "success",
					ErrorCodeConstant.ADD_SUCCESS_MESS);
			logger.debug("Data's inserted successfully!");
			MarkMasterSearchVO markMasterSearchVO= (MarkMasterSearchVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");		
			markMtrMasterVO.setMarkMasterSearchVo(markMasterSearchVO)	;
			redirectAttribute.addFlashAttribute("markMaster", markMtrMasterVO);
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
			return "redirect:/markMasterList.htm?Get";
		}
		
		
		// Get Values from list for popup
		@RequestMapping(value = "/markMasterList", method = RequestMethod.GET, params = { "actionGet" })
		public String getAcademicCalendar(
				@ModelAttribute("markMaster") MarkMtrMasterVO markMtrMasterVO,
				ModelMap model, HttpServletRequest httpServletRequest,
				HttpSession session, RedirectAttributes redirectAttribute) {
			logger.debug("inside Get Mark Master Details from List method");
			List<MarkMasterVO> markMasterVOs = (List<MarkMasterVO>) WebUtils
					.getSessionAttribute(httpServletRequest, "display_tbl");
			/*aCalMtrVO.setAcadClSeaVo((AcademicCalendarSearchVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo"));*/
			
			String id = httpServletRequest.getParameter("rowid");
			System.out.println("rowid"+id);
			markMtrMasterVO.setMarkMasterVO(markMasterVOs.get(Integer.parseInt(id)));
			model.addAttribute("popup", "update");
			String keepstat = (String) WebUtils.getSessionAttribute(
					httpServletRequest, "keepStatus");
			System.out.println("Keep status  :" + keepstat);
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.MARK_MASTER_LIST);
			if (keepstat != null) {
				modelAndView.getModelMap().addAttribute("status", "false");
			} else {
				modelAndView.getModelMap().addAttribute("status", "true");
			}
			redirectAttribute.addFlashAttribute("popup", "update");
			redirectAttribute.addFlashAttribute("markMaster", markMtrMasterVO);
			return "redirect:/markMasterList.htm?data";	
		}
		
		
		// Update method
		@RequestMapping(value = "/markMasterList", method = RequestMethod.POST, params = { "actionUpdate" })
		public String updateAcademicCalendar(
				@ModelAttribute("markMaster") MarkMtrMasterVO markMtrMasterVO,
				ModelMap model, HttpServletRequest httpServletRequest,
				HttpSession session, RedirectAttributes redirectAttribute)
				throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		
			logger.debug("inside update Mark Master method");
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.MARK_MASTER_LIST, "markMaster",
					markMtrMasterVO);
			httpServletRequest.setAttribute("page", modelAndView);
			markMasterService.updateMarkMasterRec(markMtrMasterVO.getMarkMasterVO(), sessionCache.getUserSessionDetails(),applicationCache);			
			httpServletRequest.setAttribute("errors", "erroradd");
			WebUtils.setSessionAttribute(httpServletRequest, "success",
					ErrorCodeConstant.UPDATE_SUCCESS_MESS);
			logger.debug("Data's updated successfully!");
			MarkMasterSearchVO markMasterSearchVO= (MarkMasterSearchVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");		
			markMtrMasterVO.setMarkMasterSearchVo(markMasterSearchVO)	;
			redirectAttribute.addFlashAttribute("markMaster", markMtrMasterVO);
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
			return "redirect:/markMasterList.htm?Get";
		}

		// Delete Method
		@RequestMapping(value = "/markMasterList", method = RequestMethod.GET, params = { "actionDelete" })
		public String deleteAcademicCalendar(
				@ModelAttribute("markMaster") MarkMtrMasterVO markMtrMasterVO,
				ModelMap model, HttpServletRequest httpServletRequest,
				HttpSession session, RedirectAttributes redirectAttribute)
				throws DeleteFailedException, NoDataFoundException, AcadCalendarDeleteFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {

			logger.debug("inside Deleted Mark Master method");
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.MARK_MASTER_LIST, "markMaster",
					markMtrMasterVO);
			httpServletRequest.setAttribute("page", modelAndView);
			httpServletRequest.setAttribute("errors", "erroradd");
			List<MarkMasterVO> markMasterVOs = (List<MarkMasterVO>) WebUtils
					.getSessionAttribute(httpServletRequest, "display_tbl");
			String id = httpServletRequest.getParameter("rowid");
			markMtrMasterVO.setMarkMasterVO(markMasterVOs.get(Integer.parseInt(id)));			
			markMasterService.deleteMarkMasterRec(markMtrMasterVO.getMarkMasterVO(),sessionCache.getUserSessionDetails(),applicationCache);	
			
			/*academicCalendarMasterVO.setAcadClSeaVo((AcademicCalendarSearchVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo"));*/
			WebUtils.setSessionAttribute(httpServletRequest, "success",
					ErrorCodeConstant.DELETE_SUCCESS_MESS);
			logger.debug("Data's deleted successfully!");
			MarkMasterSearchVO markMasterSearchVO= (MarkMasterSearchVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");		
			markMtrMasterVO.setMarkMasterSearchVo(markMasterSearchVO)	;
			redirectAttribute.addFlashAttribute("markMaster", markMtrMasterVO);
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
			return "redirect:/markMasterList.htm?Get";
		}
		
		//For dropDown
		
		@ModelAttribute("acTermList")
		public Map<String, String> gerAcaTrmList(HttpSession session,
				HttpServletRequest httpSevletRequest, HttpServletResponse response,
				ModelMap model) throws IOException {
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			Map<String, String> map = null;
			try {
				map = dropDownListService
						.getAcademicTermListTag(sessionCache.getUserSessionDetails());
			} catch (NoDataFoundException e) {
				logger.error("Error occured in Academic Term Dropdown :" + e.getMessage());			
			}
			httpSevletRequest.setAttribute("acTermList", map);
			return map;

		}

		

		
		@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,
			UpdateFailedException.class, DeleteFailedException.class,TableNotSpecifiedForAuditException.class,
			ExamOrderExistException.class})
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
