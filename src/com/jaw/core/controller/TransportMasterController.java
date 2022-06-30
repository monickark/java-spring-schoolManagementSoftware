package com.jaw.core.controller;

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
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.service.ITransportMasterService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class TransportMasterController {
	// Logging
	Logger logger = Logger.getLogger(TransportMasterController.class);
	@Autowired
	ITransportMasterService transportMasterService;

	@RequestMapping(value = "/transportMaster", method = RequestMethod.GET)
	public ModelAndView viewtransportMaster(
			@ModelAttribute("transportMastr_Mtr") TransportMaster_MasterVO TransportMaster_MasterVO,
			HttpServletRequest httpRequest,HttpSession session,
			RedirectAttributes redirectAttributes) throws NoDataFoundException {
		//WebUtils.setSessionAttribute(httpRequest, "success", null);
		// Getting the display tag parameter
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		
		// Setting model and view for exception handler
		
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.TRANSPORT_MASTER_LIST, "transportMastr_Mtr",
				TransportMaster_MasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");
		// Check whether the list already get or have to fetch from data base
		// using the list size
      
		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database

			try {
				transportMasterService
						.selectTransportMasterList(TransportMaster_MasterVO,sessionCache.getUserSessionDetails());
			} catch (NoDataFoundException e) {
				logger.error("Transport Master list No Data found Exception found");
				WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
				throw new NoDataFoundException();
			}

			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					TransportMaster_MasterVO.getTrnsMtrVOList());
			TransportMaster_MasterVO.setPageSize(TransportMaster_MasterVO
					.getPageSize());

		}
		redirectAttributes.addFlashAttribute("transportMastr_Mtr",
				TransportMaster_MasterVO);
		return modelAndView;
	}

	@RequestMapping(value = "/transportMaster", method = RequestMethod.POST, params = { "actionSave" })
	public String saveTransportMaster(
			@ModelAttribute("transportMastr_Mtr") TransportMaster_MasterVO TransportMaster_MasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException {

		logger.debug("inside save Transport Master method");
		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		httpServletRequest.setAttribute("errors", "erroradd");
		
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.TRANSPORT_MASTER_LIST, "transportMastr_Mtr",
				TransportMaster_MasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		transportMasterService.insertTransportMasterRec(
				TransportMaster_MasterVO.getTransMtrVo(),
				sessionCache.getUserSessionDetails());
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		logger.debug("Data's inserted successfully!");
		return "redirect:/transportMaster.htm";
	}

	// Get Values from list for popup
	@RequestMapping(value = "/transportMaster", method = RequestMethod.GET, params = { "actionGet" })
	public String getAcademicCalendar(
			@ModelAttribute("transportMastr_Mtr") TransportMaster_MasterVO TransportMaster_MasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute) {
		logger.debug("inside Get TransportMaster Details from list method");

		List<TransportMasterVO> TransportMasterVOs = (List<TransportMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String id = httpServletRequest.getParameter("rowId");
		TransportMaster_MasterVO.setTransMtrVo(TransportMasterVOs.get(Integer
				.parseInt(id)));
		
		redirectAttribute.addFlashAttribute("popup", "update");
		redirectAttribute.addFlashAttribute("transportMastr_Mtr", TransportMaster_MasterVO);
		return "redirect:/transportMaster.htm";
	}

	// Update method
	@RequestMapping(value = "/transportMaster", method = RequestMethod.POST, params = { "actionUpdate" })
	public String updateTransportMaster(
			@ModelAttribute("transportMastr_Mtr") TransportMaster_MasterVO TransportMaster_MasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {

		logger.debug("inside update TransportMaster method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.TRANSPORT_MASTER_LIST, "transportMastr_Mtr",
				TransportMaster_MasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "erroradd");
		transportMasterService.updateTransportMasterRec(
				TransportMaster_MasterVO.getTransMtrVo(),
				sessionCache.getUserSessionDetails(),applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		logger.debug("Data's updated successfully!");
		return "redirect:/transportMaster.htm";
	}

	// Delete Method
	@RequestMapping(value = "/transportMaster", method = RequestMethod.GET, params = { "actionDelete" })
	public String deleteTransportMaster(
			@ModelAttribute("transportMastr_Mtr") TransportMaster_MasterVO transportMaster_MasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {

		logger.debug("inside Deleted Transport Master method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.TRANSPORT_MASTER_LIST, "transportMastr_Mtr",
				transportMaster_MasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "erroradd");
		List<TransportMasterVO> transportMasterVOs = (List<TransportMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String id = httpServletRequest.getParameter("rowId");
		transportMaster_MasterVO.setTransMtrVo(transportMasterVOs.get(Integer
				.parseInt(id)));
		transportMasterService.deleteTransportMasterRec(
				transportMaster_MasterVO.getTransMtrVo(),
				sessionCache.getUserSessionDetails(),applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		logger.debug("Data's deleted successfully!");
		return "redirect:/transportMaster.htm";
	}

	@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,
		 UpdateFailedException.class,
			DeleteFailedException.class, TableNotSpecifiedForAuditException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		logger.error("inside Exception Handler method :"+ex.getMessage());
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
